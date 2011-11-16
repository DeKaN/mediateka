/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления записи в историю
 * @author DeKaN
 */
public class AddHistRecCommand implements AddCommand {

    public boolean Execute(Record record) {
        return MediatekaView.managers.getHistManager().add(record);
    }

    public String ToString() {
        return "Добавить запись в историю";
    }

}
