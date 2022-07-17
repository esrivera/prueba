package com.pichincha.prueba.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.pichincha.prueba.model.dtos.NewAccount;
import com.pichincha.prueba.model.dtos.UpdateAccount;
import com.pichincha.prueba.model.entities.Account;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface IAccountService {

    /**
     * Crear nueva cuenta
     * 
     * @param newAccount
     */
    public Account createAccount(@RequestBody NewAccount newAccount);

    /**
     * Editar cuenta
     * 
     * @param updateClient
     */
    public Account updateAccount(@PathVariable("id") Long id, @RequestBody UpdateAccount updateAccount);

    /**
     * Eliminar cuenta
     * 
     * @pathVar idAccount
     */
    public void deleteAccount(@PathVariable("id") Long idAccount);

    /**
     * Obtener todas las cuentas
     * 
     */
    public List<Account> getAllAccounts();

    /**
     * Obtener todas las cuentas de un cliente
     * 
     * @pathVar idClient
     * 
     */
    public List<Account> getAllAccountsByClient(@PathVariable("idClient") Long idClient);

}
