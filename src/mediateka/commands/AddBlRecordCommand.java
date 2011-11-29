package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления записи в таблицу черный список
 * @author Alexandr
 */
public class AddBlRecordCommand implements AddCommand {

    public boolean execute(Record record) {
        try {
            return Managers.getInstance().getBlListManager().add(record);
        } catch (Exception ex) {
            return false;
        }
    }
}