package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования персональных данных
 * @author DeKaN
 */
public class EditPersonCommand implements EditCommand {

    public boolean execute(Record record) {
        try {
            return Managers.getInstance().getPersManager().edit(record);
        } catch (Exception ex) {
            return false;
        }
    }
}