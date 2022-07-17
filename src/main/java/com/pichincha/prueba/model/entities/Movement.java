package com.pichincha.prueba.model.entities;

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pichincha.prueba.model.enums.TypeMovement;

import lombok.Data;

@Data
@Entity
@Table(name = "movement")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movement")
    private Long id;

    @Column(name = "date_movement", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateMovement;

    @Column(name = "type_movement", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeMovement typeMovement;

    @Column(name = "balance", nullable = false)
    @Digits(integer = 14, fraction = 2)
    private BigDecimal balance;

    @Column(name = "value", nullable = false)
    @Digits(integer = 14, fraction = 2)
    private BigDecimal value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @JsonIgnore
    @JoinColumn(name = "id_account", foreignKey = @ForeignKey(name = "FK_MOVEMENT_TO_ACCOUNT"))
    private Account account;

}
