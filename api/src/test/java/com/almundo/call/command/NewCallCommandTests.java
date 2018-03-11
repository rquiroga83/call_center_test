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
import com.almundo.call.invoker.CommandInvoker;
import com.almundo.call.receiver.CallExecutor;
import com.almundo.constants.AppConstants;
import com.almundo.dto.CommandResultDto;
import com.almundo.people.Director;
import com.almundo.people.Operator;
import com.almundo.people.Supervisor;
import com.almundo.people.repository.DirectorRepository;
import com.almundo.people.repository.OperatorRepository;
import com.almundo.people.repository.SupervisorRepository;
import java.util.List;
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
    
    @Autowired
    private OperatorRepository opRepository;
    
    @Autowired
    private SupervisorRepository suRepository;
    
    @Autowired
    private DirectorRepository diRepository;
    
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
    
    
    /**
     * 
     * Pruba la fifo cuando la llamada es recazada por que no hay empleados
     */
    @Test
    public void createFifoNoEmployCall() {
        System.out.println("***** Test de llamadas en espera por falta de empleados");
        createEmploy1();
        
        for(int i=0;i<10;i++){
            Command newCallCommand = new NewCallCommand(callExecutor);
            CommandResultDto result = new CommandInvoker().invoke(newCallCommand);
        }
        
        System.out.println("***** Llamadas en curso: " + Central.getSingletonInstance().getCallNumber());
        System.out.println("***** Llamadas en espera: " + Central.getSingletonInstance().getFifo().size());
        if(Central.getSingletonInstance().getFifo().size() == 0){
            fail("No hay llamadas en espera");
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
            if(++max_time > 20){
                fail("No se finalizaron la llamadas automaticamente");
                break;
            }
        }
        
    }
    
    
    /**
     * 
     * Pruba la fifo cuando la llamada es recazada por que no hay empleados
     */
    @Test
    public void createFifoRejectCall() {
        System.out.println("***** Test de llamadas en espera por central saturada");
        createEmploy2();
        
        for(int i=0;i<12;i++){
            Command newCallCommand = new NewCallCommand(callExecutor);
            CommandResultDto result = new CommandInvoker().invoke(newCallCommand);
        }
        
        System.out.println("***** Llamadas en curso: " + Central.getSingletonInstance().getCallNumber());
        System.out.println("***** Llamadas en espera: " + Central.getSingletonInstance().getFifo().size());
        if(Central.getSingletonInstance().getFifo().size() == 0){
            fail("No hay llamadas en espera");
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
            if(++max_time > 20){
                fail("No se finalizaron la llamadas automaticamente");
                break;
            }
        }
        
    }
    
    
    public void createEmploy1(){
                // Prueba repositorio de operadores
        opRepository.deleteAll();
        
        // Guarda algunos operadores en base de datos
        opRepository.save(new Operator("Andres", "Mercado", "Y"));
        opRepository.save(new Operator("Alejandro", "Chitiva", "Y"));
        opRepository.save(new Operator("Pedro", "Suarez", "Y"));
        //opRepository.save(new Operator("Juan", "Vergara", "Y"));
        //opRepository.save(new Operator("Alberto", "Medina", "Y"));
        
        // Valida el guardado
	System.out.println("*** Lista todos los operadores findAll():");
	System.out.println("-------------------------------");
        List<Operator> operators = opRepository.findAll();
 
	for (Operator operator : operators) {
            System.out.println(operator);
	}
	System.out.println();
        
        // Prueba repositorio de supervisores
        suRepository.deleteAll();
        suRepository.save(new Supervisor("Rodrigo", "Macias", "Y"));
        suRepository.save(new Supervisor("Mauricio", "Melendez", "Y"));

        // Valida el guardado
	System.out.println("*** Lista todos los supervisores findAll():");
	System.out.println("-------------------------------");
        List<Supervisor> supervisors = suRepository.findAll();

        
	for (Supervisor supervisor : supervisors) {
            System.out.println(supervisor);
	}
	System.out.println();
        
        // Prueba repositorio de directores
        diRepository.deleteAll();
        diRepository.save(new Director("Andres", "Quiroga", "Y"));
        
        // Valida el guardado
	System.out.println("*** Lista todos los directores findAll():");
	System.out.println("-------------------------------");
        List<Director> directors = diRepository.findAll();
        
	for (Director director : directors) {
            System.out.println(director);
	}
	System.out.println();
    }
    
    
    public void createEmploy2(){
                // Prueba repositorio de operadores
        opRepository.deleteAll();
        
        // Guarda algunos operadores en base de datos
        opRepository.save(new Operator("Andres", "Mercado", "Y"));
        opRepository.save(new Operator("Alejandro", "Chitiva", "Y"));
        opRepository.save(new Operator("Pedro", "Suarez", "Y"));
        opRepository.save(new Operator("Juan", "Vergara", "Y"));
        opRepository.save(new Operator("Alberto", "Medina", "Y"));
        opRepository.save(new Operator("Pedro", "Ladino", "Y"));
        opRepository.save(new Operator("Ricardo", "Rodriguez", "Y"));
        opRepository.save(new Operator("Sebastian", "Cruz", "Y"));
        
        // Valida el guardado
	System.out.println("*** Lista todos los operadores findAll():");
	System.out.println("-------------------------------");
        List<Operator> operators = opRepository.findAll();
 
	for (Operator operator : operators) {
            System.out.println(operator);
	}
	System.out.println();
        
        // Prueba repositorio de supervisores
        suRepository.deleteAll();
        suRepository.save(new Supervisor("Rodrigo", "Macias", "Y"));
        suRepository.save(new Supervisor("Mauricio", "Melendez", "Y"));
        suRepository.save(new Supervisor("Pedro", "Medina", "Y"));

        // Valida el guardado
	System.out.println("*** Lista todos los supervisores findAll():");
	System.out.println("-------------------------------");
        List<Supervisor> supervisors = suRepository.findAll();

        
	for (Supervisor supervisor : supervisors) {
            System.out.println(supervisor);
	}
	System.out.println();
        
        // Prueba repositorio de directores
        diRepository.deleteAll();
        diRepository.save(new Director("Andres", "Quiroga", "Y"));
        
        // Valida el guardado
	System.out.println("*** Lista todos los directores findAll():");
	System.out.println("-------------------------------");
        List<Director> directors = diRepository.findAll();
        
	for (Director director : directors) {
            System.out.println(director);
	}
	System.out.println();
    }
}
