/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.receiver;

import com.almundo.constants.AppConstants;
import com.almundo.dto.CommandResultDto;

/**
 * Command 
 * Calse que realiza la accion de la llamada
 * @author andres
 */
public class CallExecutor {
    
    public CallExecutor() {  
        
    }
    
    public CommandResultDto dispatchCall() {
        CommandResultDto result = new CommandResultDto();
        result.setResult(AppConstants.RESULT_SUCESS);
        
        return result;
    }
    
}
