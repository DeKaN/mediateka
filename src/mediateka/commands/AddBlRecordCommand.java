package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления записи в таблицу черный список
 * @author Alexandr
 */
public class AddBlRecordCommand implements AddCommand {

    public boolean Execute(Record record) {
        return MediatekaView.managers.getBlListManager().add(record);
    }

    public String ToString() {
        return "Добавить запись в историю";
    }

}