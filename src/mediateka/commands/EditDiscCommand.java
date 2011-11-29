package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования диска
 * @author Alexandr
 */
public class EditDiscCommand implements EditCommand {

    public boolean execute(Record record) {
        try {
            return Managers.getInstance().getDiscsManager().edit(record);
        } catch (Exception ex) {
            return false;
        }
    }
}
