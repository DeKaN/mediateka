package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления записи в историю
 * @author DeKaN
 */
public class AddHistRecCommand implements AddCommand {

    public boolean Execute(Record record) {
        try {
            return MediatekaView.managers.getHistManager().add(record);
        } catch (Exception ex) {
            return false;
        }
    }

    public String ToString() {
        return "Добавить запись в историю";
    }
}
