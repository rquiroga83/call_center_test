/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.people.repository;

import com.almundo.people.Supervisor;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author andres
 */
public interface SupervisorRepository extends MongoRepository<Supervisor, String> {
    
    @Query("{ '_id' : ?0 }")
    public Supervisor findBy_Id(String id);
    
    public Supervisor findByFirstName(String firstName);
    public List<Supervisor> findByLastName(String lastName);
    public List<Supervisor> findByAvailable(String available);
}
