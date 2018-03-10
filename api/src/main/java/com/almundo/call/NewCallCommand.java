/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call;

import com.almundo.call.receiver.CallExecutor;
import com.almundo.dto.CommandResultDto;

/**
 *
 * Command
 * Comando de ejecicion de llamada
 * @author andres
 */
public class NewCallCommand implements Command {
    
    private final CallExecutor executor;

    public NewCallCommand(CallExecutor executor) {
        this.executor = executor;
    }

    @Override
    public CommandResultDto execute() {
        return executor.makeCall();
    }
    
}
