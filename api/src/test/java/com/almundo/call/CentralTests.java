/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call;

import com.almundo.constants.AppConstants;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author andres
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CentralTests {
    
    /**
     * Test que tealiza prueba de ubicacion de 
     * llamadas en la central ubicando hasta 10 llamadas
     */
    @Test
    public void centralTest(){
        System.out.println("***** Test de ubicacion de llamada en la central ");
        
        System.out.println("***** Validacion de colocacion de una llamada ");
        Call call = new Call();
        Central.getSingletonInstance().setNewCall(call);
        int call_number = Central.getSingletonInstance().getCallNumber();
        
        if(call_number != 1){
            fail("Colocacion incorrecta de llamadas en la central");
        }
        
        System.out.println("***** Validacion de reset de la central ");
        Central.getSingletonInstance().resetCentral();
        call_number = Central.getSingletonInstance().getCallNumber();
        if(call_number != 0){
            fail("Fallo en reset de la central");
        }
        
        
        System.out.println("***** Validacion de sobrepaso de capacidad ");
        
        for (int i = 0; i < 11; i++) {
            if(i<10){
                // Coloca las primeras 10 llamadas
                int response = Central.getSingletonInstance().setNewCall(new Call());
                if(response != AppConstants.RESULT_SUCESS){
                    fail("Fallo al crear multiples llamadas en la central");
                }
            }
            else{
                int response = Central.getSingletonInstance().setNewCall(new Call());
                if(response != AppConstants.ERROR_NEW_CALL){
                    fail("Fallo la rechazar maximo de llamadas");
                }
            }
        }
        
        System.out.println("***** Reset de la central ");
        
        Central.getSingletonInstance().resetCentral();
        call_number = Central.getSingletonInstance().getCallNumber();
        if(call_number != 0){
            fail("Fallo en reset de la central");
        }
        
        System.out.println("***** Prueba de concurrencia en la central ");
        // Se crean 10 llamadas concirrentes en la central 
        // cuando todas la lladas terminene se retiran automaticamente 
        // la espera por el fin de la llamadas es de maximo 15 segundos
        for (int i = 0; i < 10; i++) {

            // Coloca las primeras 10 llamadas
            Call call_f = new Call();
            int response = Central.getSingletonInstance().setNewCall(call_f);
            if (response != AppConstants.RESULT_SUCESS) {
                fail("Fallo al crear multiples llamadas en la central");
            }
        }
        
        
        System.out.println("***** Espera a que las llamadas se terminen automaticamente ");
        
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
