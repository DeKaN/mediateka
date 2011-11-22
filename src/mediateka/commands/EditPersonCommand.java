package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования персональных данных
 * @author DeKaN
 */
public class EditPersonCommand implements EditCommand {

    public boolean Execute(Record record) {
        try {
            return MediatekaView.managers.getPersManager().edit(record);
        } catch (Exception ex) {
            return false;
        }
    }
}