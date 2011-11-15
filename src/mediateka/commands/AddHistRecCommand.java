/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.commands;

import mediateka.db.Record;

/**
 *
 * @author DeKaN
 */
public class AddHistRecCommand implements AddCommand {

    public boolean Execute(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String ToString() {
        return "Добавить запись в историю";
    }

}
