package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска записи в черном списке
 * @author Alexandr
 */
public class FindBlRecordCommand implements FindCommand {

    public Record Execute(int id) {
        try {
            return MediatekaView.managers.getBlListManager().find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Records Execute(Record record) {
        try {
            return MediatekaView.managers.getBlListManager().find(record);
        } catch (Exception ex) {
            return null;
        }
    }
}