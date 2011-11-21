package mediateka.commands;

import mediateka.MediatekaView;

/**
 * Класс, представляющий команду удаления записи из таблицы истории
 * @author Alexandr
 */
public class DeleteHistRecCommand implements DeleteCommand {

    public boolean Execute(int id) {
        try {
            return MediatekaView.managers.getHistManager().delete(id);
        } catch (Exception ex) {
            return false;
        }
    }
}
