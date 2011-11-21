package mediateka.commands;

import mediateka.MediatekaView;

/**
 * Класс, представляющий команду удаления записи из черного списка
 * @author Alexandr
 */
public class DeleteBlRecordCommand implements DeleteCommand {

    public boolean Execute(int id) {
        try {
            return MediatekaView.managers.getBlListManager().delete(id);
        } catch (Exception ex) {
            return false;
        }
    }

    public String ToString() {
        return "Удалить запись из черного списка";
    }
}
