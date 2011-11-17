package mediateka.commands;

import mediateka.MediatekaView;

/**
 * Класс, представляющий команду удаления записи из черного списка
 * @author Alexandr
 */
public class DeleteBlRecordCommand implements DeleteCommand {

    public boolean Execute(int id) {
        return MediatekaView.managers.getBlListManager().delete(id);
    }

    public String ToString() {
        return "Удалить запись из черного списка";
    }
}
