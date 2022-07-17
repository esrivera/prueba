package com.pichincha.prueba.model.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

import com.pichincha.prueba.model.enums.TypeMovement;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NewMovement {

    @NotEmpty
    @ApiModelProperty(notes = "tipo de movimiento", required = true)
    private TypeMovement typeMovement;

    @NotEmpty
    @ApiModelProperty(notes = "Valor del movimiento", required = true)
    private BigDecimal value;

    @NotEmpty
    @ApiModelProperty(notes = "Número de cuenta", required = true)
    private String accountNumber;
}
