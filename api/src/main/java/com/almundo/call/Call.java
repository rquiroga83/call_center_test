/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call;

import com.almundo.call.receiver.CallExecutor;
import com.almundo.people.Director;
import com.almundo.people.Employee;
import com.almundo.people.Operator;
import com.almundo.people.Supervisor;
import com.almundo.people.repository.DirectorRepository;
import com.almundo.people.repository.OperatorRepository;
import com.almundo.people.repository.SupervisorRepository;
import com.almundo.util.BeanUtil;
import java.util.Optional;
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
        freeCall();
        
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
     * Funcion que libera operador
     * y retira la llamada de la central
     */
    public void freeCall(){
        // Coloca el operado como disponible
        log.debug("********* Establece empleado empleado diponible " );
        if(employee instanceof Operator){
            log.debug("********* Id de operador " + employee.getId());
            OperatorRepository o_repository = BeanUtil.getBean(OperatorRepository.class);
            Optional<Operator> operator = o_repository.findById(employee.getId());
            operator.get().setAvailable("Y");
            o_repository.save(operator.get());
            
        }
        else if(employee instanceof Supervisor){
            
            log.debug("********* Id de supervisor " + employee.getId());
            SupervisorRepository s_repository = BeanUtil.getBean(SupervisorRepository.class);
            Optional<Supervisor> supervisor = s_repository.findById(employee.getId());
            supervisor.get().setAvailable("Y");
            s_repository.save(supervisor.get());
            
        }
        else if(employee instanceof Director){
            
            log.debug("********* Id de director " + employee.getId());
            DirectorRepository d_repository = BeanUtil.getBean(DirectorRepository.class);
            Optional<Director> director = d_repository.findById(employee.getId());
            director.get().setAvailable("Y");
            d_repository.save(director.get());
            
        }
        
        // Libera ca cetral
        Central.getSingletonInstance().deleteCall(this);
        
        // Si hay llamadas en cola la asigna
        Call call = Central.getSingletonInstance().getFifo().get();
        
        if(call != null){
            log.debug("********* Despacha llamada desde la cola " + call.getId());
            CallExecutor executor = BeanUtil.getBean(CallExecutor.class);
            executor.dispatchCall(call);
        }
        
        
        // Interrumpe el Hilo
        /*if(!isInterrupted() && isAlive() ){
            this.interrupt();
        }*/
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
