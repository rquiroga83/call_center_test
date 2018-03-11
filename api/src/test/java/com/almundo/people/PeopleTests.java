/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.people;

import com.almundo.people.repository.DirectorRepository;
import com.almundo.people.repository.OperatorRepository;
import com.almundo.people.repository.SupervisorRepository;
import java.util.List;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author andres
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PeopleTests {
    
    @Autowired
    private OperatorRepository opRepository;
    
    @Autowired
    private SupervisorRepository suRepository;
    
    @Autowired
    private DirectorRepository diRepository;
    
    /**
     * Crea operadores en la base de datos mongo
     */
    @Test
    public void createPeopleMongo() {
        
        
        // Prueba repositorio de operadores
        opRepository.deleteAll();
        
        // Guarda algunos operadores en base de datos
        opRepository.save(new Operator("Andres", "Mercado", "N"));
        opRepository.save(new Operator("Alejandro", "Chitiva", "Y"));
        opRepository.save(new Operator("Pedro", "Suarez", "Y"));
        opRepository.save(new Operator("Juan", "Vergara", "Y"));
        opRepository.save(new Operator("Alberto", "Medina", "Y"));
        
        // Valida el guardado
	System.out.println("*** Lista todos los operadores findAll():");
	System.out.println("-------------------------------");
        List<Operator> operators = opRepository.findAll();
        if(operators.isEmpty()){
            fail("Error al crear operadores en la base de datos");
        }
        
	for (Operator operator : operators) {
            System.out.println(operator);
	}
	System.out.println();
        
        // Prueba repositorio de supervisores
        suRepository.deleteAll();
        suRepository.save(new Supervisor("Rodrigo", "Macias", "Y"));
        suRepository.save(new Supervisor("Mauricio", "Melendez", "Y"));

        // Valida el guardado
	System.out.println("*** Lista todos los supervisores findAll():");
	System.out.println("-------------------------------");
        List<Supervisor> supervisors = suRepository.findAll();
        if(supervisors.isEmpty()){
            fail("Error al crear supervisores en la base de datos");
        }
        
	for (Supervisor supervisor : supervisors) {
            System.out.println(supervisor);
	}
	System.out.println();
        
        
        // Prueba repositorio de directores
        diRepository.deleteAll();
        diRepository.save(new Director("Andres", "Quiroga", "Y"));
        
        // Valida el guardado
	System.out.println("*** Lista todos los directores findAll():");
	System.out.println("-------------------------------");
        List<Director> directors = diRepository.findAll();
        if(directors.isEmpty()){
            fail("Error al crear directores en la base de datos");
        }
        
	for (Director director : directors) {
            System.out.println(director);
	}
	System.out.println();
        
    }
}
