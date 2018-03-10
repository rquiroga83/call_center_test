/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call;

/**
 *
 * @author andres
 */
import com.almundo.call.invoker.CallDispatcher;
import com.almundo.call.receiver.CallExecutor;
import com.almundo.constants.AppConstants;
import com.almundo.dto.CommandResultDto;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewCallCommandTests {
    
    @Test
    public void createCall() {
        System.out.println("Test de creacion de nueva llamada");
        
        CallExecutor callExecutor = new CallExecutor();
        Command newCallCommand = new NewCallCommand(callExecutor);
        
        CommandResultDto result = new CallDispatcher().dispatchCall(newCallCommand);
        
        if(result.getResult() != AppConstants.RESULT_FAIL) {
            fail("La ejecuacion de la llamada retorno error");
        }
    }
}
