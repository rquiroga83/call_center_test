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
import com.almundo.dto.NewCallInfoDto;
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
        // Crea una nueva llamada
        Call call = new Call();
        return dispatchCall(call);
    }
    
    /**
     * Funcion que realiza la ejecucion de la asignacion de la llamada
     * 
     * @param call
     * @return 
     */
    public CommandResultDto dispatchCall(Call call) {
        CommandResultDto result = new CommandResultDto();
        NewCallInfoDto info = new NewCallInfoDto();
        
        // Asigna un empleado
        log.debug("*** Asignacion de empleado");
        int response = 0;
        if(employeeAllocatorOperatorService.allocateEmployee(call) == AppConstants.RESULT_SUCESS){
            response = Central.getSingletonInstance().setNewCall(call);
            info.setTypeEmploy("operator");
            log.debug("Operator has assigned to call " + call.getId());
        }
        else if(employeeAllocatorSupervisorService.allocateEmployee(call) == AppConstants.RESULT_SUCESS){
            response = Central.getSingletonInstance().setNewCall(call);
            info.setTypeEmploy("supervisor");
            log.debug("Supervisor has assigned to call "  + call.getId());
        }
        else if(employeeAllocatorDirectorService.allocateEmployee(call) == AppConstants.RESULT_SUCESS){
            response = Central.getSingletonInstance().setNewCall(call);
            info.setTypeEmploy("director");
            log.debug("Director has assigned to call " + call.getId());
        }
        else{
            // No hay personal disponible para atender la llamada
            log.debug("**** Empleado no disponible para atender llamada, llamada en espera " + call.getId());
            result.setResult(AppConstants.ERROR_EMPLOY_NON_DIPONIBLE);
            result.setMessage("Employ non disponible to assign call, call await" + call.getId());
            // Coloca llamada en espera
            Central.getSingletonInstance().getFifo().put(call);
            return result;
        }
        
        // La llamada no pudo ser establecida en la central hay mas de 10
        // llamadas concurrentes
        if(response == AppConstants.ERROR_NEW_CALL){
            log.debug("**** Central rechazo llamada llamada en espera " + call.getId());
            result.setResult(AppConstants.ERROR_REJECT_CALL);
            result.setMessage("Central reject call, call await" + call.getId());
            // Libera empleado
            call.freeCall();
            // Coloca llamada en espera
            Central.getSingletonInstance().getFifo().put(call);
            return result;
        }

        
        info.setIdCall(String.valueOf(call.getId()));
        info.setIdEmploy(call.getEmployee().getId());
        info.setNameEmploy(call.getEmployee().getFirstName() + " " + call.getEmployee().getLastName());
        result.setInfo(info);
        result.setResult(AppConstants.RESULT_SUCESS);
        
        return result;
    }
    
}
