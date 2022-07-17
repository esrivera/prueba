package com.pichincha.prueba.model.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pichincha.prueba.model.enums.TypeAccount;

import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account")
    private Long id;

    @Column(name = "account_number", length = 10, nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "initial_balance", nullable = false)
    @Digits(integer = 14, fraction = 2)
    private BigDecimal initialBalance;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "type_account", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeAccount typeAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", foreignKey = @ForeignKey(name = "FK_ACCOUNT_TO_CLIENT"))
    @JsonIgnore
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Client client;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Movement> movements;

}
