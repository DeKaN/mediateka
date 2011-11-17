package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду редактирования фильма
 * @author Alexandr
 */
public class EditFilmCommand implements EditCommand {

    public boolean Execute(int id, Record record) {
        return MediatekaView.managers.getFilmsManager().edit(id, record);
    }

    public String ToString() {
        return "Изменить фильм";
    }
}