/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.people;

import org.springframework.data.annotation.Id;

/**
 *
 * @author andres
 */
public class Employee  {
    
    @Id
    String id;
    String firstName;
    String lastName;
    String available; 
    
    public Employee() {}

    public Employee(String firstName, String lastName, String available) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.available = available;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }




}
