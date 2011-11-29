package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска записи в черном списке
 * @author Alexandr
 */
public class FindBlRecordCommand implements FindCommand {

    public Record execute(int id) {
        try {
            return Managers.getInstance().getBlListManager().find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Records execute(Record record) {
        try {
            return Managers.getInstance().getBlListManager().find(record);
        } catch (Exception ex) {
            return null;
        }
    }
}