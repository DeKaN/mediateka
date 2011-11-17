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
        return MediatekaView.managers.getBlListManager().find(id);
    }

    public Records Execute(Record record) {
        return MediatekaView.managers.getBlListManager().find(record);
    }

    public String ToString() {
        return "Найти запись в черном списке";
    }
}