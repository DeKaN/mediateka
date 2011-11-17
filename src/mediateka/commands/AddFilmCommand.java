package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления фильма в коллекцию
 * @author Alexandr
 */
public class AddFilmCommand implements AddCommand {

    public boolean Execute(Record record) {
        return MediatekaView.managers.getFilmsManager().add(record);
    }

    public String ToString() {
        return "Добавить фильм в коллекцию";
    }
}
