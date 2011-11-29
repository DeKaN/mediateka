package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления записи в историю
 * @author DeKaN
 */
public class AddHistRecCommand implements AddCommand {

    public boolean execute(Record record) {
        try {
            return Managers.getInstance().getHistManager().add(record);
        } catch (Exception ex) {
            return false;
        }
    }
}
