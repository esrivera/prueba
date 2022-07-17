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

import com.pichincha.prueba.model.dtos.NewAccount;
import com.pichincha.prueba.model.dtos.UpdateAccount;
import com.pichincha.prueba.model.entities.Account;
import com.pichincha.prueba.service.IAccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/account")
@Api(tags = "Cuenta")
public class AccountController {

    @Autowired
    IAccountService accountService;

    @PostMapping("/create")
    @ApiOperation(value = "Crear una cuenta para un cliente", notes = "<b>Ejemplo de envio</b><br> URL: http://localhost:9012/account/create"
            + "<br><b>Se debe ingresar todos los atributos pertinenetes a la cuenta (nombre, genero, edad, identificación, dirección, teléfono, contraseña y estado)</b>")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Cliente registrado con exito"),
            @ApiResponse(code = 404, message = "No se puede registrar el cliente") })
    public Account register(@RequestBody NewAccount newAccount) {
        return accountService.createAccount(newAccount);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "Editar una cuenta por su id", notes = "<b>Ejemplo de envio</b><br> URL: http://localhost:9012/account/update/{id}"
            + "<br><b>El parametro id corresponde al id de la cuenta")
    public Account updateUserById(@PathVariable("id") Long id, @RequestBody UpdateAccount account) {
        return accountService.updateAccount(id, account);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "Eliminar una cuenta por su id", notes = "<b>Ejemplo de envio</b><br> URL: http://localhost:9012/account/delete/{id}"
            + "<br><b>El parametro id corresponde al id de la cuenta")
    public ResponseEntity<Object> delete(@PathVariable("id") Long idAccount) {
        accountService.deleteAccount(idAccount);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/all")
    @ApiOperation(value = "Obtener todas las cuentas", notes = "<b>Ejemplo de envio</b><br> URL: http://localhost:9012/account/all")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "lista de cuentas recuperada con exito ") })
    public List<Account> getAll() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/get/idClient/{idClient}")
    @ApiOperation(value = "Obtener todas las cuentas de un cliente", notes = "<b>Ejemplo de envio</b><br> URL: http://localhost:9012/account/idClient/{idClient}")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "lista de cuentas por cliente recuperada con exito ") })
    public List<Account> getAllByClient(@PathVariable("idClient") Long idClient) {
        return accountService.getAllAccountsByClient(idClient);
    }
}
