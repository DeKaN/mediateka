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
        return MediatekaView.managers.getFilmsManager().find(id);
    }

    public Records Execute(Record record) {
        return MediatekaView.managers.getFilmsManager().find(record);
    }

    public String ToString() {
        return "Найти фильм";
    }
}