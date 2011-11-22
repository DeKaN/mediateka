package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования записи таблицы черного списка
 * @author Alexandr
 */
public class EditBlRecordCommand implements EditCommand {

    public boolean Execute(Record record) {
        try {
            return MediatekaView.managers.getBlListManager().edit(record);
        } catch (Exception ex) {
            return false;
        }
    }
}
