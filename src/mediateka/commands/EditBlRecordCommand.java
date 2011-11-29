package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования записи таблицы черного списка
 * @author Alexandr
 */
public class EditBlRecordCommand implements EditCommand {

    public boolean execute(Record record) {
        try {
            return Managers.getInstance().getBlListManager().edit(record);
        } catch (Exception ex) {
            return false;
        }
    }
}
