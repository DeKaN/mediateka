package mediateka.commands;

import mediateka.datamanagers.Managers;

/**
 * Класс, представляющий команду удаления записи из таблицы истории
 * @author Alexandr
 */
public class DeleteHistRecCommand implements DeleteCommand {

    public boolean execute(int id) {
        try {
            return Managers.getInstance().getHistManager().delete(id);
        } catch (Exception ex) {
            return false;
        }
    }
}
