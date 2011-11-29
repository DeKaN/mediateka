package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления персональных данных в таблицу
 * @author Alexandr
 */
public class AddPersonCommand implements AddCommand {

    public boolean execute(Record record) {
        try {
            return Managers.getInstance().getPersManager().add(record);
        } catch (Exception ex) {
            return false;
        }
    }
}
