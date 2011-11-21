package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления персональных данных в таблицу
 * @author Alexandr
 */
public class AddPersonCommand implements AddCommand {

    public boolean Execute(Record record) {
        try {
            return MediatekaView.managers.getPersManager().add(record);
        } catch (Exception ex) {
            return false;
        }
    }
}
