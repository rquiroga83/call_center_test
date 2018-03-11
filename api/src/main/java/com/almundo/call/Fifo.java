/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almundo.call;

import java.util.LinkedList;

/**
 * Pila de tipo FIFO la que almacenara la llamadas 
 * cuando no existan agentes o la central este llena
 * 
 * @author andres
 * @param <E>
 */
public class Fifo<E> {

    private LinkedList<E> list = new LinkedList<E>();

    /**
     * Pone el objeto en la cola.
     * @param o
     */
    public void put(E o) {
        list.addLast(o);
    }

    /**
     * Devuelve un elemento de la cola.
     *
     * @return elemento de la cola <code>null</code> if la cola es vacia
     */
    public E get() {
        if (list.isEmpty()) {
            return null;
        }
        return list.removeFirst();
    }

    /**
     * Devuelve todos los elementos de la cola y los borra.
     * @return 
     */
    public Object[] getAll() {
        Object[] res = new Object[list.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = list.get(i);
        }
        list.clear();
        return res;
    }

    /**
     * Peeks an element in the queue. Returned elements is not removed from the
     * queue.
     * @return 
     */
    public E peek() {
        return list.getFirst();
    }

    /**
     * Retirna <code>true</code> si cola es vacia, en otros casos <code>false</code>
     * @return 
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Retirna el tamaño de la cola.
     * @return 
     */
    public int size() {
        return list.size();
    }
}
