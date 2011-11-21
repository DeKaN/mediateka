package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования диска
 * @author Alexandr
 */
public class EditDiscCommand implements EditCommand {

    public boolean Execute(int id, Record record) {
        try {
            return MediatekaView.managers.getDiscsManager().edit(id, record);
        } catch (Exception ex) {
            return false;
        }
    }
}
