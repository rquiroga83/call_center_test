/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.assignor;

import com.almundo.call.Call;
import com.almundo.constants.AppConstants;
import com.almundo.people.Director;
import com.almundo.people.repository.DirectorRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Patron estategia
 * Asigna un director a la llamada
 * 
 * @author andres
 */
@Service
public class EmployeeAllocatorDirectorService extends EmployeeAllocatorService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private DirectorRepository repository;

    
    @Override
    public int allocateEmployee(Call call) {
        //Employee director = new Director();
        // Busca directores diponibles
        log.debug("****  Busca directores disponibles"); 
        List<Director> directors = repository.findByAvailable("Y");
        log.debug("****  Directores recuperados: " + directors.size());
        
        if(directors.size()>0){
            // Asigna un operador
            call.setEmployee(directors.get(0));
            // Coloca el supervisor como no diponible
            Optional<Director> director = repository.findById(directors.get(0).getId());
            director.get().setAvailable("N");
            repository.save(director.get());
            
            return AppConstants.RESULT_SUCESS;
        }
        else{
            return AppConstants.ERROR_ALLOCATE_OPERATOR;
        }
    }
    
}
