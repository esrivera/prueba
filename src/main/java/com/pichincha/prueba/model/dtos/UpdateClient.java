package com.pichincha.prueba.model.dtos;

import javax.validation.constraints.NotEmpty;

import com.pichincha.prueba.model.enums.GenderType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateClient {

    @NotEmpty
    @ApiModelProperty(notes = "nombre del cliente a registrar", required = false)
    private String name;

    @ApiModelProperty(notes = "género del cliente a registrar", required = false)
    private GenderType gender;

    @ApiModelProperty(notes = "edad del cliente a registrar", required = false)
    private Integer age;

    @NotEmpty
    @ApiModelProperty(notes = "dirección del cliente a registrar", required = false)
    private String address;

    @ApiModelProperty(notes = "teléfono del cliente a registrar", required = false)
    private String phone;

    @NotEmpty
    @ApiModelProperty(notes = "contraseña del cliente a registrar", required = false)
    private String password;

    @NotEmpty
    @ApiModelProperty(notes = "estado del cliente", required = false)
    private Boolean status;

}
