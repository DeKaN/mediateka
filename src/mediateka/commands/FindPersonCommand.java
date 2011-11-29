package mediateka.commands;

import mediateka.datamanagers.Managers;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска персональных данных
 * @author Alexandr
 */
public class FindPersonCommand implements FindCommand {

    public Record execute(int id) {
        try {
            return Managers.getInstance().getPersManager().find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Records execute(Record record) {
        try {
            return Managers.getInstance().getPersManager().find(record);
        } catch (Exception ex) {
            return null;
        }
    }
}