/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.db;

/**
 *
 * @author DeKaN
 */
public class LoadException extends IllegalStateException {

    public LoadException(String message) {
        super(message);
    }

    public LoadException() {
    }
    
}
