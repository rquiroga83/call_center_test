/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.assignor;

import com.almundo.call.Call;

/**
 *  Patron estrategia
 *  Esta clase asigna un empleado a la llamada 
 *  De diferentes formas
 * 
 * @author andres
 */
public abstract class EmployeeAllocatorService {
    
    public EmployeeAllocatorService() {
        
    }
    
    /**
     * Funcion que recibe llamada y le asigna un empleado
     * ojo el parametro se utiliza por referencia
     * 
     * @param call
     * @return 
     */
    
    public abstract int allocateEmployee(Call call);
}
