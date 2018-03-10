/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call;

import com.almundo.people.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase que contiene la llamada
 * 
 * @author andres
 */
public class Call extends Thread {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    // Tiempo total del hilo
    int time;
    // Tiempo transcurrido
    int counter;
    // Empleado que esta en la llamada
    Employee employee;
    
    /**
     * Funcion que ejecuta simulacion del proceso de llamada
     * Con un timepo aleatorio entre 5 y 10 segundos
     */
    @Override
    public void run() {
        log.debug("Begin call id= " + this.getId());
        time = getRandomTime();
        try{
            for(counter = 0;counter<time && !isInterrupted() ;counter++){
                Thread.sleep(1000);
            }
        }
        catch(InterruptedException e){
            log.error("Sleep interrupt -> " + e.getMessage());
        }
        stopCall();
        
        log.debug("End call id= " + this.getId());
    }
    
    
    /**
     * Funcion que genera un numero aleatorio entre 5 y 10
     * 
     * @return 
     */
    public int getRandomTime(){
        return (int) (Math.random() * 5) + 5;
    }
    
    
    /**
     * Funcion que detiene el proceso de llamada
     * y retira la llamada de la central
     */
    public void stopCall(){
        if(!isInterrupted() && isAlive() ){
            this.interrupt();
        }
        Central.getSingletonInstance().deleteCall(this);
    }

    /*
     * Get & Set 
     */
    
    
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
 
    
}
