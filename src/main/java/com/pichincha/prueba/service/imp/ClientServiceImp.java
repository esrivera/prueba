package com.pichincha.prueba.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pichincha.prueba.config.AES256;
import com.pichincha.prueba.exception.DataAccessCustomException;
import com.pichincha.prueba.exception.ModelNotFoundException;
import com.pichincha.prueba.exception.NotFoundException;
import com.pichincha.prueba.model.dtos.NewClient;
import com.pichincha.prueba.model.dtos.UpdateClient;
import com.pichincha.prueba.model.entities.Client;
import com.pichincha.prueba.model.entities.Person;
import com.pichincha.prueba.repository.ClientRepository;
import com.pichincha.prueba.repository.PersonRepository;
import com.pichincha.prueba.service.IClientService;

@Service
public class ClientServiceImp implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LoggerService logger;

    @Autowired
    private AES256 aes256;

    @Override
    public Client registerClient(NewClient newClient) {
        Client clientRegister = new Client();
        Client response = new Client();
        Person person = new Person();
        String passwordEncrypt = "";
        person.setAddress(newClient.getAddress());
        person.setAge(newClient.getAge());
        if (newClient.getGender() != null && !newClient.getGender().equals("")) {
            person.setGender(newClient.getGender());
        }
        person.setIdentification(newClient.getIdentification());
        person.setName(newClient.getName());
        if (newClient.getPhone() != null && !newClient.getPhone().equals("")) {
            person.setPhone(newClient.getPhone());
        }
        passwordEncrypt = aes256.toAES256(newClient.getPassword());
        clientRegister.setPassword(passwordEncrypt);
        clientRegister.setStatus(true);
        Person newPerson = personRepository.save(person);
        clientRegister.setPerson(newPerson);
        try {
            response = clientRepository.save(clientRegister);
            logger.msgInfo(null, "Cliente registrado", null, null);
        } catch (DataAccessException e) {
            logger.buildError(getClass().getName(), "save", "Error en el registro del cliente", e.getMessage(),
                    HttpStatus.BAD_REQUEST.toString());
            throw new DataAccessCustomException("Error en el registro del cliente. DataAccess", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @Override
    public Client updateClient(Long id, UpdateClient updateClient) {
        Client clientTemp = this.getById(id);
        if (clientTemp == null) {
            throw new ModelNotFoundException(String.format("Cliente con id: %d no encontrado", id),
                    "El cliente no existe", HttpStatus.NOT_FOUND);
        }
        Client response = new Client();
        Person person = clientTemp.getPerson();
        String passwordEncrypt = "";
        if (clientTemp != null) {
            if (updateClient.getAddress() != null && !updateClient.getAddress().equals("")) {
                person.setAddress(updateClient.getAddress());
            }
            if (updateClient.getAge() != null) {
                person.setAge(updateClient.getAge());
            }
            if (updateClient.getGender() != null && !updateClient.getGender().equals("")) {
                person.setGender(updateClient.getGender());
            }
            if (updateClient.getName() != null && !updateClient.getName().equals("")) {
                person.setName(updateClient.getName());
            }
            if (updateClient.getPhone() != null && !updateClient.getPhone().equals("")) {
                person.setPhone(updateClient.getPhone());
            }
            Person newPerson = personRepository.save(person);
            if (updateClient.getPassword() != null && !updateClient.getPassword().equals("")) {
                passwordEncrypt = aes256.toAES256(updateClient.getPassword());
                clientTemp.setPassword(passwordEncrypt);
            }
            if (updateClient.getStatus() != null) {
                clientTemp.setStatus(updateClient.getStatus());
            }
            clientTemp.setPerson(newPerson);
        }

        try {
            response = clientRepository.save(clientTemp);
            logger.msgInfo(null, "Cliente editado", null, null);
        } catch (DataAccessException e) {
            logger.buildError(getClass().getName(), "update", "Error al editar el cliente", e.getMessage(),
                    HttpStatus.BAD_REQUEST.toString());
            throw new DataAccessCustomException("Error al modificar el cliente. DataAccess", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @Override
    public void deleteClient(Long idClient) {
        Client optionalClient = this.getById(idClient);
        if (optionalClient == null) {
            throw new ModelNotFoundException(String.format("Cliente con id: %d no encontrado", idClient),
                    "El cliente que desea eliminar no existe", HttpStatus.NOT_FOUND);
        }
        try {
            clientRepository.deleteById(idClient);
            logger.msgInfo(null, "Cliente elimninado", null, null);
        } catch (DataAccessException e) {
            logger.buildError(getClass().getName(), "delete", "Error al eliminar el cliente", e.getMessage(),
                    HttpStatus.BAD_REQUEST.toString());
            throw new DataAccessCustomException("Error al eliminar el cliente. DataAccess", e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients;
    }

    public Client getById(Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (!optionalClient.isPresent()) {
            throw new NotFoundException("Cliente con Id: " + id + " no encontrado",
                    " cliente no encontrado, proporcione un id correcto", HttpStatus.BAD_REQUEST);
        }
        return optionalClient.get();
    }

}
