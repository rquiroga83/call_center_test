/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.receiver;

import com.almundo.call.Call;
import com.almundo.call.Central;
import com.almundo.call.assignor.EmployeeAllocatorDirectorService;
import com.almundo.call.assignor.EmployeeAllocatorOperatorService;
import com.almundo.call.assignor.EmployeeAllocatorSupervisorService;
import com.almundo.constants.AppConstants;
import com.almundo.dto.CommandResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Patron de diseño Command
 * Calse que realiza la accion de la llamada
 * @author andres
 */
@Service
public class CallExecutor {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    EmployeeAllocatorOperatorService employeeAllocatorOperatorService;
    
    @Autowired
    EmployeeAllocatorSupervisorService employeeAllocatorSupervisorService;
    
    @Autowired
    EmployeeAllocatorDirectorService employeeAllocatorDirectorService;
    
    public CallExecutor() {  
        
    }
    
    /**
     * Funcion que realiza la ejecucion de la asignacion de la llamada
     * 
     * @return 
     */
    public CommandResultDto dispatchCall() {
        CommandResultDto result = new CommandResultDto();
        
        // Crea una nueva llamada
        Call call = new Call();
        // Asigna un empleado
        log.debug("*** Asignacion de empleado");
        int response = 0;
        if(employeeAllocatorOperatorService.allocateEmployee(call) == AppConstants.RESULT_SUCESS){
            response = Central.getSingletonInstance().setNewCall(call);
            log.debug("Operator has assigned to call " + call.getId());
        }
        else if(employeeAllocatorSupervisorService.allocateEmployee(call) == AppConstants.RESULT_SUCESS){
            response = Central.getSingletonInstance().setNewCall(call);
            log.debug("Supervisor has assigned to call "  + call.getId());
        }
        else if(employeeAllocatorDirectorService.allocateEmployee(call) == AppConstants.RESULT_SUCESS){
            response = Central.getSingletonInstance().setNewCall(call);
            log.debug("Director has assigned to call " + call.getId());
        }
        else{
            // No hay personal disponible para atender la llamada
            log.debug("Employ non disponible to assign call " + call.getId());
            result.setResult(AppConstants.ERROR_EMPLOY_NON_DIPONIBLE);
            return result;
        }
        
        // La llamada no pudo ser establecida en la central
        if(response == AppConstants.ERROR_NEW_CALL){
            result.setResult(AppConstants.ERROR_REJECT_CALL);
            return result;
        }

        result.setResult(AppConstants.RESULT_SUCESS);
        return result;
    }
    
}
