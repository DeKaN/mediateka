package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска фильма
 * @author DeKaN
 */
public class FindFilmCommand implements FindCommand {

    public Record Execute(int id) {
        try {
            return MediatekaView.managers.getFilmsManager().find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Records Execute(Record record) {
        try {
            return MediatekaView.managers.getFilmsManager().find(record);
        } catch (Exception ex) {
            return null;
        }
    }

    public String ToString() {
        return "Найти фильм";
    }
}