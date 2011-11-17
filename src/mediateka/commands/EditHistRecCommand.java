package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования записи истории
 * @author Alexandr
 */
public class EditHistRecCommand implements EditCommand {

    public boolean Execute(int id, Record record) {
        return MediatekaView.managers.getHistManager().edit(id, record);
    }

    public String ToString() {
        return "Изменить запись истории";
    }
}