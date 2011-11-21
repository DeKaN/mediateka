package mediateka.commands;

import java.util.logging.Level;
import java.util.logging.Logger;
import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления записи в таблицу черный список
 * @author Alexandr
 */
public class AddBlRecordCommand implements AddCommand {

    public boolean Execute(Record record) {
        try {
            return MediatekaView.managers.getBlListManager().add(record);
        } catch (Exception ex) {
            return false;
        }
    }

    public String ToString() {
        return "Добавить запись в историю";
    }

}