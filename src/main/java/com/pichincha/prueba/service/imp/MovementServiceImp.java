package com.pichincha.prueba.service.imp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pichincha.prueba.exception.BusinessLogicException;
import com.pichincha.prueba.exception.DataAccessCustomException;
import com.pichincha.prueba.exception.ModelNotFoundException;
import com.pichincha.prueba.exception.NotFoundException;
import com.pichincha.prueba.model.dtos.NewMovement;
import com.pichincha.prueba.model.dtos.UpdateMovement;
import com.pichincha.prueba.model.entities.Account;
import com.pichincha.prueba.model.entities.Movement;
import com.pichincha.prueba.model.enums.TypeMovement;
import com.pichincha.prueba.repository.AccountRepository;
import com.pichincha.prueba.repository.MovementRepository;
import com.pichincha.prueba.service.IMovementService;

@Service
public class MovementServiceImp implements IMovementService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private LoggerService logger;

    @Override
    public Movement creaMovement(NewMovement newMovement) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(newMovement.getAccountNumber());
        if (!optionalAccount.isPresent()) {
            throw new NotFoundException("Número de cuenta: " + newMovement.getAccountNumber() + " no encontrada",
                    " cuenta no encontrada, proporcione una cuenta correcta", HttpStatus.BAD_REQUEST);
        }
        Movement movement = new Movement();
        Movement response = new Movement();
        movement.setAccount(optionalAccount.get());
        movement.setTypeMovement(newMovement.getTypeMovement());
        List<Movement> movements = movementRepository.findByAccountOrderByIdDesc(optionalAccount.get());
        if (newMovement.getTypeMovement().equals(TypeMovement.CREDITO)) {
            if (newMovement.getValue().compareTo(BigDecimal.ZERO) == 0 || newMovement.getValue().signum() == -1) {
                throw new BusinessLogicException(null, "El valor del movimiento no puede ser cero o negativo",
                        "Valor del movimiento invalido", null, null);
            }
            movement.setValue(newMovement.getValue());
            movement.setBalance(newMovement.getValue().add(movements.get(0).getBalance()));
            movement.setDateMovement(new Date());
        } else {
            if (movements.get(0).getBalance().subtract(newMovement.getValue()).signum() == -1) {
                throw new BusinessLogicException(null,
                        "Saldo no Disponible. El valor del movimiento no puede ser superior al del saldo actual",
                        "Valor del movimiento invalido", null, null);
            }
            if (newMovement.getValue().subtract(new BigDecimal("1000")).signum() == 1) {
                throw new BusinessLogicException(null,
                        "El valor del movimiento no puede ser superior a 1000",
                        "Valor del movimiento invalido", null, null);
            }
            movement.setValue(newMovement.getValue());
            movement.setBalance(movements.get(0).getBalance().subtract(newMovement.getValue()));
            movement.setDateMovement(new Date());
        }
        try {
            response = movementRepository.save(movement);
            logger.msgInfo(null, "Movimiento registrado", null, null);
        } catch (DataAccessException e) {
            logger.buildError(getClass().getName(), "save", "Error en el registro del movimiento", e.getMessage(),
                    HttpStatus.BAD_REQUEST.toString());
            throw new DataAccessCustomException("Error en el registro del movimiento. DataAccess", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Movement updatMovement(Long id, UpdateMovement updateMovement) {
        Movement movementTemp = this.getById(id);
        if (movementTemp == null) {
            throw new ModelNotFoundException(String.format("Movimiento con id: %d no encontrado", id),
                    "El movimiento no existe", HttpStatus.NOT_FOUND);
        }
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(updateMovement.getAccountNumber());
        if (!optionalAccount.isPresent()) {
            throw new NotFoundException("Número de cuenta: " + updateMovement.getAccountNumber() + " no encontrada",
                    " cuenta no encontrada, proporcione una cuenta correcta", HttpStatus.BAD_REQUEST);
        }
        Movement response = new Movement();
        movementTemp.setAccount(optionalAccount.get());
        if (updateMovement.getTypeMovement() != null || updateMovement.getTypeMovement().equals("")) {
            movementTemp.setTypeMovement(updateMovement.getTypeMovement());
        }
        if (updateMovement.getDateMovement() != null) {
            movementTemp.setDateMovement(updateMovement.getDateMovement());
        }
        List<Movement> movements = movementRepository.findByAccountOrderByIdDesc(optionalAccount.get());
        if (updateMovement.getTypeMovement().equals(TypeMovement.CREDITO)) {
            if (updateMovement.getValue() != null) {
                if (updateMovement.getValue().compareTo(BigDecimal.ZERO) == 0
                        || updateMovement.getValue().signum() == -1) {
                    throw new BusinessLogicException(null, "El valor del movimiento no puede ser cero o negativo",
                            "Valor del movimiento invalido", null, null);
                }
                movementTemp.setValue(updateMovement.getValue());
                movementTemp.setBalance(updateMovement.getValue().add(movements.get(0).getBalance()));
            }

        } else {
            if (updateMovement.getValue() != null) {
                if (movements.get(0).getBalance().subtract(updateMovement.getValue()).signum() == -1) {
                    throw new BusinessLogicException(null,
                            "Saldo no Disponible. El valor del movimiento no puede ser superior al del saldo actual",
                            "Valor del movimiento invalido", null, null);
                }
                if (updateMovement.getValue().subtract(new BigDecimal("1000")).signum() == 1) {
                    throw new BusinessLogicException(null,
                            "El valor del movimiento no puede ser superior a 1000",
                            "Valor del movimiento invalido", null, null);
                }
                movementTemp.setValue(updateMovement.getValue());
                movementTemp.setBalance(movements.get(0).getBalance().subtract(updateMovement.getValue()));
            }
        }
        try {
            response = movementRepository.save(movementTemp);
            logger.msgInfo(null, "Movimiento editado", null, null);
        } catch (DataAccessException e) {
            logger.buildError(getClass().getName(), "save", "Error al editar el movimiento", e.getMessage(),
                    HttpStatus.BAD_REQUEST.toString());
            throw new DataAccessCustomException("Error al editar el movimiento. DataAccess", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public void deleteMovement(Long idMovement) {
        Movement optionalMovement = this.getById(idMovement);
        if (optionalMovement == null) {
            throw new ModelNotFoundException(String.format("Movimiento con id: %d no encontrado", idMovement),
                    "El movimiento que desea eliminar no existe", HttpStatus.NOT_FOUND);
        }
        try {
            movementRepository.deleteById(idMovement);
            logger.msgInfo(null, "Movimiento elimninado", null, null);
        } catch (DataAccessException e) {
            logger.buildError(getClass().getName(), "delete", "Error al eliminar el movimiento", e.getMessage(),
                    HttpStatus.BAD_REQUEST.toString());
            throw new DataAccessCustomException("Error al eliminar el movimiento. DataAccess", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public List<Movement> getAllMovementsByAccount(Long idAccount) {
        Optional<Account> optionalAccount = accountRepository.findById(idAccount);
        if (!optionalAccount.isPresent()) {
            throw new ModelNotFoundException(
                    String.format("Cuenta con id: %d no encontrada", idAccount),
                    "La cuenta no existe", HttpStatus.NOT_FOUND);
        }
        List<Movement> movements = movementRepository.findByAccountOrderByIdDesc(optionalAccount.get());
        return movements;
    }

    public Movement getById(Long id) {
        Optional<Movement> optionalMovement = movementRepository.findById(id);
        if (!optionalMovement.isPresent()) {
            throw new NotFoundException("Cuenta con Id: " + id + " no encontrada",
                    " cuenta no encontrada, proporcione un id correcto", HttpStatus.BAD_REQUEST);
        }
        return optionalMovement.get();
    }

}
