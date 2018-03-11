/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.assignor;

import com.almundo.call.Call;
import com.almundo.constants.AppConstants;
import com.almundo.people.Operator;
import com.almundo.people.repository.OperatorRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Patron estategia
 * Asigna un operador a la llamada
 * 
 * @author andres
 */
@Service
public class EmployeeAllocatorOperatorService extends EmployeeAllocatorService {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private OperatorRepository repository;

    @Override
    public  int allocateEmployee(Call call) {
        // Busca operadores diponibles
        log.debug("****  Busca operadores disponibles"); 
        List<Operator> operators = repository.findByAvailable("Y");
        log.debug("****  Operadores recuperados: " + operators.size());
        
        if(operators.size()>0){
            // Asigna un operador
            call.setEmployee(operators.get(0));
            // Coloca el operador como no diponible
            Optional<Operator> operator = repository.findById(operators.get(0).getId());
            operator.get().setAvailable("N");
            repository.save(operator.get());
            return AppConstants.RESULT_SUCESS;
        }
        else{
            return AppConstants.ERROR_ALLOCATE_OPERATOR;
        }
    }
    
}
