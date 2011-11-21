package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования персональных данных
 * @author DeKaN
 */
public class EditPersonCommand implements EditCommand {

    public boolean Execute(int id, Record record) {
        try {
            return MediatekaView.managers.getPersManager().edit(id, record);
        } catch (Exception ex) {
            return false;
        }
    }
}