/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.rest;

import com.almundo.call.command.Command;
import com.almundo.call.command.NewCallCommand;
import com.almundo.call.invoker.CommandInvoker;
import com.almundo.call.receiver.CallExecutor;
import com.almundo.dto.CommandResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author andres
 */
@RestController
@RequestMapping("/callservice")
public class CallService {
    
    @Autowired
    private CallExecutor callExecutor;
    
    @RequestMapping(value = "/newcall", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity newCall(){
        
        Command newCallCommand = new NewCallCommand(callExecutor);
        CommandResultDto result = new CommandInvoker().invoke(newCallCommand);
        
        return new ResponseEntity<>(
                result , 
                HttpStatus.OK);
    }
    
}
