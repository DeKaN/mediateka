package mediateka.commands;

import mediateka.datamanagers.Managers;

/**
 * Класс, представляющий команду удаления фильма
 * @author Alexandr
 */
public class DeleteFilmCommand implements DeleteCommand {

    public boolean execute(int id) {
        try {
            return Managers.getInstance().getFilmsManager().delete(id);
        } catch (Exception ex) {
            return false;
        }
    }
}