/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.dto;

/**
 *
 * @author andres
 */
public class NewCallInfoDto {
    String idEmploy;
    String idCall;

    public String getIdCall() {
        return idCall;
    }

    public void setIdCall(String idCall) {
        this.idCall = idCall;
    }
    String typeEmploy;
    String nameEmploy;

    public NewCallInfoDto() {
    }

    public String getIdEmploy() {
        return idEmploy;
    }

    public void setIdEmploy(String idEmploy) {
        this.idEmploy = idEmploy;
    }

    public String getTypeEmploy() {
        return typeEmploy;
    }

    public void setTypeEmploy(String typeEmploy) {
        this.typeEmploy = typeEmploy;
    }

    public String getNameEmploy() {
        return nameEmploy;
    }

    public void setNameEmploy(String nameEmploy) {
        this.nameEmploy = nameEmploy;
    }
    
    
}
