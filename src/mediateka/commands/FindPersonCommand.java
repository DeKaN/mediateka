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
        try {
            return MediatekaView.managers.getPersManager().find(id);
        } catch (Exception ex) {
            return null;
        }
    }

    public Records Execute(Record record) {
        try {
            return MediatekaView.managers.getPersManager().find(record);
        } catch (Exception ex) {
            return null;
        }
    }
}