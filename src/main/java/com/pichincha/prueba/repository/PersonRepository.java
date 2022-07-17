package com.pichincha.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pichincha.prueba.model.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
