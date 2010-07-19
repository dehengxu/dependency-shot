/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.ncontainr;

/**
 *
 * @author Mathieu ANCELIN
 */
public interface Account {

    long getAmount();

    long spend(long price);

}
