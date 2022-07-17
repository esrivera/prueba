package com.pichincha.prueba.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.pichincha.prueba.model.dtos.NewMovement;
import com.pichincha.prueba.model.dtos.UpdateMovement;
import com.pichincha.prueba.model.entities.Movement;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface IMovementService {

    /**
     * Crear nuevo movimiento
     * 
     * @param newAccount
     */
    public Movement creaMovement(@RequestBody NewMovement newAccount);

    /**
     * Editar movimiento
     * 
     * @param updateMovement
     */
    public Movement updatMovement(@PathVariable("id") Long id, @RequestBody UpdateMovement updateMovement);

    /**
     * Eliminar movimiento
     * 
     * @pathVar idMovement
     */
    public void deleteMovement(@PathVariable("id") Long idMovement);

    /**
     * Obtener todas los movimientos de una cuenta
     * 
     * @pathVar idAccount
     * 
     */
    public List<Movement> getAllMovementsByAccount(@PathVariable("idAccount") Long idAccount);
}
