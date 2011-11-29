package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования фильма
 * @author Alexandr
 */
public class EditFilmCommand implements EditCommand {

    public boolean execute(Record record) {
        try {
            return Managers.getInstance().getFilmsManager().edit(record);
        } catch (Exception ex) {
            return false;
        }
    }
}