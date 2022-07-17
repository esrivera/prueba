package com.pichincha.prueba.model.dtos;

import javax.validation.constraints.NotEmpty;

import com.pichincha.prueba.model.enums.TypeAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateAccount {

    @NotEmpty
    @ApiModelProperty(notes = "número de cuenta", required = false)
    private String accountNumber;

    @NotEmpty
    @ApiModelProperty(notes = "tipo de cuenta", required = false)
    private TypeAccount typeAccount;

    @NotEmpty
    @ApiModelProperty(notes = "Estado de la cuenta", required = false)
    private Boolean status;

    @NotEmpty
    @ApiModelProperty(notes = "Id del cliente de la cuenta", required = false)
    private Long idClient;

}
