/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.db;

/**
 *
 * @author DeKaN
 */
public class ChangeDataException extends IllegalStateException {

    public ChangeDataException(String message) {
        super(message);
    }

    public ChangeDataException() {
    }
    
}
