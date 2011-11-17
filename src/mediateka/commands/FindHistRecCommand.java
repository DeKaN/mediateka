package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска записи в таблице истории
 * @author Alexandr
 */
public class FindHistRecCommand implements FindCommand {

    public Record Execute(int id) {
        return MediatekaView.managers.getHistManager().find(id);
    }

    public Records Execute(Record record) {
        return MediatekaView.managers.getHistManager().find(record);
    }

    public String ToString() {
        return "Найти запись в таблице истории";
    }
}