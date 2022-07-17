package com.pichincha.prueba.model.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

import com.pichincha.prueba.model.enums.TypeAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NewAccount {

    @NotEmpty
    @ApiModelProperty(notes = "número de cuenta", required = true)
    private String accountNumber;

    @NotEmpty
    @ApiModelProperty(notes = "tipo de cuenta", required = true)
    private TypeAccount typeAccount;

    @NotEmpty
    @ApiModelProperty(notes = "Saldo inicial", required = true)
    private BigDecimal initialBalance;

    @NotEmpty
    @ApiModelProperty(notes = "Estado de la cuenta", required = true)
    private Boolean status;

    @NotEmpty
    @ApiModelProperty(notes = "Id del cliente de la cuenta", required = true)
    private Long idClient;

}
