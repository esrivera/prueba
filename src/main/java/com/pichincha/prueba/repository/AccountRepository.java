package com.pichincha.prueba.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pichincha.prueba.model.entities.Account;
import com.pichincha.prueba.model.entities.Client;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByClient(Client client);

    Optional<Account> findByAccountNumber(String accountNumber);

}