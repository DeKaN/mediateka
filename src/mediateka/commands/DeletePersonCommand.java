package mediateka.commands;

import mediateka.MediatekaView;

/**
 * Класс, представляющий команду удаления записи из таблицы персональных данных
 * @author Alexandr
 */
public class DeletePersonCommand implements DeleteCommand {

    public boolean Execute(int id) {
        try {
            return MediatekaView.managers.getPersManager().delete(id);
        } catch (Exception ex) {
            return false;
        }
    }
}