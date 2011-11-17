package mediateka.commands;

import mediateka.MediatekaView;

/**
 * Класс, представляющий команду удаления фильма
 * @author Alexandr
 */
public class DeleteFilmCommand implements DeleteCommand {

    public boolean Execute(int id) {
        return MediatekaView.managers.getFilmsManager().delete(id);
    }

    public String ToString() {
        return "Удалить фильм";
    }
}