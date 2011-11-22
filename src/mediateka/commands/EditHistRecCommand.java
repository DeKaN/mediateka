package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования записи истории
 * @author Alexandr
 */
public class EditHistRecCommand implements EditCommand {

    public boolean Execute(Record record) {
        try {
            return MediatekaView.managers.getHistManager().edit(record);
        } catch (Exception ex) {
            return false;
        }
    }
}