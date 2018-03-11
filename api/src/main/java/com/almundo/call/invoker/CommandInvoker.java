/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.invoker;

import com.almundo.call.command.Command;
import com.almundo.dto.CommandResultDto;

/**
 * Patron de diseño Command
 * Clase encargada de realizar la invocaion 
 * de la accion de crear llamada
 * 
 * @author andres
 */
public class CommandInvoker {
    
    public CommandResultDto invoke(Command cmd){
        return cmd.execute();
    }
    
}
