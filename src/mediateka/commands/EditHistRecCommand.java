package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования записи истории
 * @author Alexandr
 */
public class EditHistRecCommand implements EditCommand {

    public boolean Execute(int id, Record record) {
        try {
            return MediatekaView.managers.getHistManager().edit(id, record);
        } catch (Exception ex) {
            return false;
        }
    }
}