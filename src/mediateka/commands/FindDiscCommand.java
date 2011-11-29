package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска диска
 * @author Alexandr
 */
public class FindDiscCommand implements FindCommand {

    public Record execute(int id) {
        try {
            return Managers.getInstance().getDiscsManager().find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Records execute(Record record) {
        try {
            return Managers.getInstance().getDiscsManager().find(record);
        } catch (Exception ex) {
            return null;
        }
    }
}