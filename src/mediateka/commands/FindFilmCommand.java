package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска фильма
 * @author DeKaN
 */
public class FindFilmCommand implements FindCommand {

    public Record execute(int id) {
        try {
            return Managers.getInstance().getFilmsManager().find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Records execute(Record record) {
        try {
            return Managers.getInstance().getFilmsManager().find(record);
        } catch (Exception ex) {
            return null;
        }
    }
}