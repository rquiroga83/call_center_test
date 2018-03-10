/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call;

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
public class CallTests {
    
    @Test
    public void ramdomTime(){
        System.out.println("***** Test de obtencion de tiempo aleatorio");
        Call call = new Call();
       
        for(int i=0;i<10;i++){
            int time = call.getRandomTime();
            
            if(time < 5 || time > 10){
                fail("Timepo de llamada fuera del rango");
            }
        }
    }
    
    
    @Test
    public void callTest(){
        System.out.println("***** Test de creacion y destruccion del hilo de la llamada");
        
        Call call = new Call();
        
        // Inicia la llamada con un timepo aleatorio
        call.start();
        // Espera por almenos un segundo
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            fail("No se pudo crear el intervalo de espera para la llamada");
        }
        //Valida si la llamada esta en curso
        if(!call.isAlive()){
            fail("No se ejecuto correctamente el hilo de la llamada");
        }
        //Detiene la llamada
        call.stopCall();
        if(!call.isInterrupted()){
            fail("No se detuvo la ejecucion de la llamada");
        }
        
    }
    
}
