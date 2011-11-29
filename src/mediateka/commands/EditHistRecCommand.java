package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования записи истории
 * @author Alexandr
 */
public class EditHistRecCommand implements EditCommand {

    public boolean execute(Record record) {
        try {
            return Managers.getInstance().getHistManager().edit(record);
        } catch (Exception ex) {
            return false;
        }
    }
}