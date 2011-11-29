package mediateka.commands;

import mediateka.datamanagers.Managers;

/**
 * Класс, представляющий команду удаления записи из черного списка
 * @author Alexandr
 */
public class DeleteBlRecordCommand implements DeleteCommand {

    public boolean execute(int id) {
        try {
            return Managers.getInstance().getBlListManager().delete(id);
        } catch (Exception ex) {
            return false;
        }
    }
}
