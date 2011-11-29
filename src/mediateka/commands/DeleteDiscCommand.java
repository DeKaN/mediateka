package mediateka.commands;

import mediateka.datamanagers.Managers;

/**
 * Класс, представляющий команду удаления диска
 * @author DeKaN
 */
public class DeleteDiscCommand implements DeleteCommand {

    public boolean execute(int id) {
        try {
            return Managers.getInstance().getDiscsManager().delete(id);
        } catch (Exception ex) {
            return false;
        }
    }
}
