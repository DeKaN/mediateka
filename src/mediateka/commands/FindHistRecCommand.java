package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска записи в таблице истории
 * @author Alexandr
 */
public class FindHistRecCommand implements FindCommand {

    public Record execute(int id) {
        try {
            return Managers.getInstance().getHistManager().find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Records execute(Record record) {
        try {
            return Managers.getInstance().getHistManager().find(record);
        } catch (Exception ex) {
            return null;
        }
    }
}