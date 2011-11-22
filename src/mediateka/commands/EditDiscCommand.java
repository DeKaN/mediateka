package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования диска
 * @author Alexandr
 */
public class EditDiscCommand implements EditCommand {

    public boolean Execute(Record record) {
        try {
            return MediatekaView.managers.getDiscsManager().edit(record);
        } catch (Exception ex) {
            return false;
        }
    }
}
