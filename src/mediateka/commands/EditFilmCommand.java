package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования фильма
 * @author Alexandr
 */
public class EditFilmCommand implements EditCommand {

    public boolean Execute(Record record) {
        try {
            return MediatekaView.managers.getFilmsManager().edit(record);
        } catch (Exception ex) {
            return false;
        }
    }
}