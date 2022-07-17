package com.pichincha.prueba.model.dtos;

import javax.validation.constraints.NotEmpty;

import com.pichincha.prueba.model.enums.GenderType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NewClient {

    @NotEmpty
    @ApiModelProperty(notes = "nombre del cliente a registrar", required = true)
    private String name;

    @ApiModelProperty(notes = "género del cliente a registrar", required = false)
    private GenderType gender;

    @ApiModelProperty(notes = "edad del cliente a registrar", required = true)
    private Integer age;

    @NotEmpty
    @ApiModelProperty(notes = "identificación del cliente a registrar", required = true)
    private String identification;

    @NotEmpty
    @ApiModelProperty(notes = "dirección del cliente a registrar", required = true)
    private String address;

    @ApiModelProperty(notes = "teléfono del cliente a registrar", required = false)
    private String phone;

    @NotEmpty
    @ApiModelProperty(notes = "contraseña del cliente a registrar", required = true)
    private String password;

}
