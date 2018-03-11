/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.command;

/**
 *
 * @author andres
 */
import com.almundo.call.Central;
import com.almundo.call.command.NewCallCommand;
import com.almundo.call.command.Command;
import com.almundo.call.invoker.CommandInvoker;
import com.almundo.call.receiver.CallExecutor;
import com.almundo.constants.AppConstants;
import com.almundo.dto.CommandResultDto;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewCallCommandTests {
    
    @Autowired
    private CallExecutor callExecutor;
    
    @Test
    public void createCall() {
        System.out.println("***** Test de creacion de nueva llamada");
        
        Command newCallCommand = new NewCallCommand(callExecutor);
        
        CommandResultDto result = new CommandInvoker().invoke(newCallCommand);
        
        if(result.getResult() != AppConstants.RESULT_SUCESS) {
            fail("La ejecuacion de la llamada retorno error");
        }
        
        // Espera a que todas las llamasdas se terminen
        int max_time = 0; // Contador de tiempo maximo de espera
        while(Central.getSingletonInstance().getCallNumber() > 0){
            System.out.println("***** Cantidad de llamadas en curso: " + Central.getSingletonInstance().getCallNumber() );
            
            try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                fail("No se pudo crear el intervalo de espera para la llamada");
            }
            if(++max_time > 15){
                fail("No se finalizaron la llamadas automaticamente");
                break;
            }
        }
    }
}
