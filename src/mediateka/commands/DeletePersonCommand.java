package mediateka.commands;

import mediateka.datamanagers.Managers;

/**
 * Класс, представляющий команду удаления записи из таблицы персональных данных
 * @author Alexandr
 */
public class DeletePersonCommand implements DeleteCommand {

    public boolean execute(int id) {
        try {
            return Managers.getInstance().getPersManager().delete(id);
        } catch (Exception ex) {
            return false;
        }
    }
}