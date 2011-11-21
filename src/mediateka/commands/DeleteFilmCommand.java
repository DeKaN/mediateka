package mediateka.commands;

import mediateka.MediatekaView;

/**
 * Класс, представляющий команду удаления фильма
 * @author Alexandr
 */
public class DeleteFilmCommand implements DeleteCommand {

    public boolean Execute(int id) {
        try {
            return MediatekaView.managers.getFilmsManager().delete(id);
        } catch (Exception ex) {
            return false;
        }
    }
}