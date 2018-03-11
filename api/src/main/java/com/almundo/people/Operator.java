/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.people;

/**
 *
 * @author andres
 */
public class Operator extends Employee {

    public Operator() {
    }

    public Operator(String firstName, String lastName, String available) {
        super(firstName, lastName, available);
    }

    @Override
    public String toString() {
        return String.format(
                "Operator[id=%s, firstName='%s', lastName='%s' , available='%s']",
                id, firstName, lastName, available);
    }
    
}
