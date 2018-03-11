/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.people.repository;

import com.almundo.people.Operator;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author andres
 */
public interface OperatorRepository extends MongoRepository<Operator, String> {
    
    public Operator findByFirstName(String firstName);
    public List<Operator> findByLastName(String lastName);
    public List<Operator> findByAvailable(String available);
}
