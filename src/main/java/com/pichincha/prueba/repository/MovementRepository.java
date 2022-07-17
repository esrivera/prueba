package com.pichincha.prueba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pichincha.prueba.model.entities.Account;
import com.pichincha.prueba.model.entities.Movement;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByAccountOrderByIdDesc(Account account);

}
