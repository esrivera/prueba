package com.pichincha.prueba.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pichincha.prueba.exception.DataAccessCustomException;
import com.pichincha.prueba.exception.ModelNotFoundException;
import com.pichincha.prueba.exception.NotFoundException;
import com.pichincha.prueba.model.dtos.NewAccount;
import com.pichincha.prueba.model.dtos.UpdateAccount;
import com.pichincha.prueba.model.entities.Account;
import com.pichincha.prueba.model.entities.Client;
import com.pichincha.prueba.model.entities.Movement;
import com.pichincha.prueba.model.enums.TypeMovement;
import com.pichincha.prueba.repository.AccountRepository;
import com.pichincha.prueba.repository.MovementRepository;
import com.pichincha.prueba.service.IAccountService;

@Service
public class AccountServiceImp implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientServiceImp clientServiceImp;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private LoggerService logger;

    @Override
    public Account createAccount(NewAccount newAccount) {
        Account registerAccount = new Account();
        Account response = new Account();
        Movement movement = new Movement();
        Client client = clientServiceImp.getById(newAccount.getIdClient());
        if (client == null) {
            throw new ModelNotFoundException(
                    String.format("Cliente con id: %d no encontrado", newAccount.getIdClient()),
                    "El cliente no existe", HttpStatus.NOT_FOUND);
        }
        registerAccount.setAccountNumber(newAccount.getAccountNumber());
        registerAccount.setInitialBalance(newAccount.getInitialBalance());
        registerAccount.setStatus(newAccount.getStatus());
        registerAccount.setTypeAccount(newAccount.getTypeAccount());
        movement.setBalance(newAccount.getInitialBalance());
        movement.setDateMovement(new Date());
        movement.setTypeMovement(TypeMovement.CREDITO);
        movement.setValue(newAccount.getInitialBalance());
        registerAccount.setClient(client);
        try {
            response = accountRepository.save(registerAccount);
            movement.setAccount(response);
            movementRepository.save(movement);
            logger.msgInfo(null, "Cuenta registrada", null, null);
        } catch (DataAccessException e) {
            logger.buildError(getClass().getName(), "save", "Error en el registro de la cuenta", e.getMessage(),
                    HttpStatus.BAD_REQUEST.toString());
            throw new DataAccessCustomException("Error en el registro de la cuenta. DataAccess", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public Account updateAccount(Long id, UpdateAccount updateAccount) {
        Account accountTemp = this.getById(id);
        if (accountTemp == null) {
            throw new ModelNotFoundException(String.format("Cuenta con id: %d no encontrado", id),
                    "La cuenta no existe", HttpStatus.NOT_FOUND);
        }
        Account response = new Account();
        if (accountTemp != null) {
            if (updateAccount.getAccountNumber() != null && !updateAccount.getAccountNumber().equals("")) {
                accountTemp.setAccountNumber(updateAccount.getAccountNumber());
            }
            if (updateAccount.getStatus() != null) {
                accountTemp.setStatus(updateAccount.getStatus());
            }
            if (updateAccount.getTypeAccount() != null && !updateAccount.getTypeAccount().equals("")) {
                accountTemp.setTypeAccount(updateAccount.getTypeAccount());
            }
            if (updateAccount.getIdClient() != null) {
                Client client = clientServiceImp.getById(updateAccount.getIdClient());
                if (client == null) {
                    throw new ModelNotFoundException(
                            String.format("Cliente con id: %d no encontrado", updateAccount.getIdClient()),
                            "El cliente no existe", HttpStatus.NOT_FOUND);
                }
                accountTemp.setClient(client);
            }
        }

        try {
            response = accountRepository.save(accountTemp);
            logger.msgInfo(null, "Cuenta editado", null, null);
        } catch (DataAccessException e) {
            logger.buildError(getClass().getName(), "update", "Error al editar la cuenta", e.getMessage(),
                    HttpStatus.BAD_REQUEST.toString());
            throw new DataAccessCustomException("Error al modificar la cuenta. DataAccess", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public void deleteAccount(Long idAccount) {
        Account optionalAccount = this.getById(idAccount);
        if (optionalAccount == null) {
            throw new ModelNotFoundException(String.format("Cuenta con id: %d no encontrada", idAccount),
                    "La cuenta que desea eliminar no existe", HttpStatus.NOT_FOUND);
        }
        try {
            accountRepository.deleteById(idAccount);
            logger.msgInfo(null, "Cuenta elimninada", null, null);
        } catch (DataAccessException e) {
            logger.buildError(getClass().getName(), "delete", "Error al eliminar la cuenta", e.getMessage(),
                    HttpStatus.BAD_REQUEST.toString());
            throw new DataAccessCustomException("Error al eliminar la cuenta. DataAccess", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts;
    }

    @Override
    public List<Account> getAllAccountsByClient(Long idClient) {
        Client client = clientServiceImp.getById(idClient);
        if (client == null) {
            throw new ModelNotFoundException(
                    String.format("Cliente con id: %d no encontrado", idClient),
                    "El cliente no existe", HttpStatus.NOT_FOUND);
        }
        List<Account> accounts = accountRepository.findByClient(client);
        return accounts;
    }

    public Account getById(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (!optionalAccount.isPresent()) {
            throw new NotFoundException("Cuenta con Id: " + id + " no encontrada",
                    " cuenta no encontrada, proporcione un id correcto", HttpStatus.BAD_REQUEST);
        }
        return optionalAccount.get();
    }

}
