/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.assignor;

import com.almundo.call.Call;
import com.almundo.constants.AppConstants;
import com.almundo.people.Supervisor;
import com.almundo.people.repository.SupervisorRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Patron estategia
 * Asigna un supervisor a la llamada
 * 
 * @author andres
 */
@Service
public class EmployeeAllocatorSupervisorService extends EmployeeAllocatorService {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private SupervisorRepository repository;

    @Override
    public int allocateEmployee(Call call) {
        // Busca supervisores diponibles
        log.debug("****  Busca Supervisores disponibles"); 
        List<Supervisor> supervisors = repository.findByAvailable("Y");
        log.debug("****  Supervisores recuperados: " + supervisors.size());
        
        if(supervisors.size()>0){
            // Asigna un operador
            call.setEmployee(supervisors.get(0));
            // Coloca el supervisor como no diponible
            Optional<Supervisor> supervisor = repository.findById(supervisors.get(0).getId());
            supervisor.get().setAvailable("N");
            repository.save(supervisor.get());
            
            return AppConstants.RESULT_SUCESS;
        }
        else{
            return AppConstants.ERROR_ALLOCATE_OPERATOR;
        }
    }
    
}
