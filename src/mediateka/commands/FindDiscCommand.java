package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска диска
 * @author Alexandr
 */
public class FindDiscCommand implements FindCommand {

    public Record Execute(int id) {
        try {
            return MediatekaView.managers.getDiscsManager().find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Records Execute(Record record) {
        try {
            return MediatekaView.managers.getDiscsManager().find(record);
        } catch (Exception ex) {
            return null;
        }
    }

    public String ToString() {
        return "Найти диск";
    }
}