package mediateka.commands;

import mediateka.MediatekaView;

/**
 * Класс, представляющий команду удаления записи из таблицы истории
 * @author Alexandr
 */
public class DeleteHistRecCommand implements DeleteCommand {

    public boolean Execute(int id) {
        return MediatekaView.managers.getHistManager().delete(id);
    }

    public String ToString() {
        return "Удалить запись из истории";
    }
}
