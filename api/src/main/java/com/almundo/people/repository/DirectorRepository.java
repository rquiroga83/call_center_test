/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.people.repository;

import com.almundo.people.Director;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author andres
 */
public interface DirectorRepository extends MongoRepository<Director, String> {
    
    public Director findByFirstName(String firstName);
    public List<Director> findByLastName(String lastName);
    public List<Director> findByAvailable(String available);
}
