/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call;

import com.almundo.constants.AppConstants;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase singleton que simula una central telefonica con una 
 * Capacidad Maxima de 10 llamadas la case funciona almacenando
 * un array de instalcias de la clase llamada
 * 
 * @author andres
 */
public class Central {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    private static Central central;
    private final  ArrayList<Call> CALLS;
    
    private Central() {
        CALLS = new ArrayList();
    }

    /**
     * Funcion que obtiene la instancia de la clase
     * 
     * @return 
     */
    public static Central getSingletonInstance() {
        if (central == null){
            central = new Central();
        }
        return central;
    }
    
    /**
     * Funcion que crea una nueva llamada
     * 
     * @param call
     * @return 
     */
    public int setNewCall(Call call){
        
        if(CALLS.size()>9){
            log.debug("Llamada rechazada en la central: " + CALLS.size());
            return AppConstants.ERROR_NEW_CALL;
        }
        else{
            CALLS.add(call);
            call.start();
            log.debug("Llamada establecida llamadas en la centra: " + CALLS.size());
            return AppConstants.RESULT_SUCESS;
        }
    }
    
    /**
     * Funcion que borra una llamada si esta existe
     * 
     * @param call
     * @return 
     */
    public int deleteCall(Call call){
        if(CALLS.contains(call)){
            CALLS.remove(call);
            log.debug("Llamada eliminada de la central id: " + call.getId());
            return AppConstants.RESULT_SUCESS;
        }
        else{
            return AppConstants.ERROR_DELETE_CALL_NOT_EXIST;
        }
            
    }
    
    
    /**
     * Funcion que retorna la cantidad de llamadas en curso
     * 
     * @return 
     */
    public int getCallNumber(){
        return CALLS.size();
    }
    
    
    /**
     * Funcion que detiene y borra todas las llamadas
     * 
     */
    public void resetCentral(){
        CALLS.forEach((call) -> {
            call.interrupt();
        });
        
        CALLS.clear();
    }
    
}
