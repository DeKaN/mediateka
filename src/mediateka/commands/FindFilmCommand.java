/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска фильмов
 * @author DeKaN
 */
public class FindFilmCommand implements FindCommand {

    public Record Execute(int id) {
        return MediatekaView.managers.getFilmsManager().find(id);
    }

    public Records Execute(Record record) {
        return MediatekaView.managers.getFilmsManager().find(record);
    }

    public String ToString() {
        return "Найти фильм";
    }
}