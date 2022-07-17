package com.pichincha.prueba.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.pichincha.prueba.model.dtos.NewClient;
import com.pichincha.prueba.model.dtos.UpdateClient;
import com.pichincha.prueba.model.entities.Client;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface IClientService {

    /**
     * Registrar nuevo cliente
     * 
     * @param newClient
     */
    public Client registerClient(@RequestBody NewClient newClient);

    /**
     * Editar cliente
     * 
     * @param updateClient
     */
    public Client updateClient(@PathVariable("id") Long id, @RequestBody UpdateClient updateClient);

    /**
     * Eliminar cliente
     * 
     * @pathVar idClient
     */
    public void deleteClient(@PathVariable("id") Long idClient);

    /**
     * Obtener todos los clientes
     * 
     * @pathVar idClient
     */
    public List<Client> getAllClients();
}
