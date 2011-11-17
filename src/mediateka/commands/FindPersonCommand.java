package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Класс, представляющий команду поиска персональных данных
 * @author Alexandr
 */
public class FindPersonCommand implements FindCommand {

    public Record Execute(int id) {
        return MediatekaView.managers.getPersManager().find(id);
    }

    public Records Execute(Record record) {
        return MediatekaView.managers.getPersManager().find(record);
    }

    public String ToString() {
        return "Найти персональные данные";
    }
}