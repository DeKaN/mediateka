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
        try {
            return MediatekaView.managers.getHistManager().find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Records Execute(Record record) {
        try {
            return MediatekaView.managers.getHistManager().find(record);
        } catch (Exception ex) {
            return null;
        }
    }

    public String ToString() {
        return "Найти запись в таблице истории";
    }
}