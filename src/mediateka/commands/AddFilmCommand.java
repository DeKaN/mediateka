package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления фильма в коллекцию
 * @author Alexandr
 */
public class AddFilmCommand implements AddCommand {

    public boolean execute(Record record) {
        try {
            return Managers.getInstance().getFilmsManager().add(record);
        } catch (Exception ex) {
            return false;
        }
    }
}
