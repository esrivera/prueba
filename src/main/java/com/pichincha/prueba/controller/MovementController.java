package com.pichincha.prueba.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pichincha.prueba.model.dtos.NewMovement;
import com.pichincha.prueba.model.dtos.UpdateMovement;
import com.pichincha.prueba.model.entities.Movement;
import com.pichincha.prueba.service.IMovementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/movement")
@Api(tags = "Movimiento")
public class MovementController {

    @Autowired
    private IMovementService movementService;

    @PostMapping("/create")
    @ApiOperation(value = "Crear un movimiento", notes = "<b>Ejemplo de envio</b><br> URL: http://localhost:9012/movement/create"
            + "<br><b>Se debe ingresar todos los atributos pertinenetes al movimiento (tipo de mMovimiento, valor, cuenta)</b>")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Cliente registrado con exito"),
            @ApiResponse(code = 404, message = "No se puede registrar el cliente") })
    public Movement register(@RequestBody NewMovement newMovement) {
        return movementService.creaMovement(newMovement);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Editar un movimiento", notes = "<b>Ejemplo de envio</b><br> URL: http://localhost:9012/movement/update/{id}"
            + "<br><b>El parametro id corresponde al id del movimiento")
    public Movement updateUserById(@PathVariable("id") Long id, @RequestBody UpdateMovement account) {
        return movementService.updatMovement(id, account);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Eliminar un movimiento por su id", notes = "<b>Ejemplo de envio</b><br> URL: http://localhost:9012/account/movement/{id}"
            + "<br><b>El parametro id corresponde al id del movimiento")
    public ResponseEntity<Object> delete(@PathVariable("id") Long idMovement) {
        movementService.deleteMovement(idMovement);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/idAccount/{idAccount}")
    @ApiOperation(value = "Obtener todos los movimeintos de una cuenta", notes = "<b>Ejemplo de envio</b><br> URL: http://localhost:9012/movement/idAccount/{idAccount}")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "lista de cuentas por cliente recuperada con exito ") })
    public List<Movement> getAllByClient(@PathVariable("idAccount") Long idAccount) {
        return movementService.getAllMovementsByAccount(idAccount);
    }

}
