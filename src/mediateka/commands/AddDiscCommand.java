package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления диска в коллекцию
 * @author Alexandr
 */
public class AddDiscCommand implements AddCommand {

    public boolean execute(Record record) {
        try {
            return Managers.getInstance().getDiscsManager().add(record);
        } catch (Exception ex) {
            return false;
        }
    }
}
