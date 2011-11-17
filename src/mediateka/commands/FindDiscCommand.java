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
        return MediatekaView.managers.getDiscsManager().find(id);
    }

    public Records Execute(Record record) {
        return MediatekaView.managers.getDiscsManager().find(record);
    }

    public String ToString() {
        return "Найти диск";
    }

}