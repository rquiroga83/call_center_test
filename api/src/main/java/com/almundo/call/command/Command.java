/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call.command;

import com.almundo.dto.CommandResultDto;

/**
 * Command
 * La interface del comando
 * @author andres
 */
public interface Command {
    CommandResultDto execute();
}
