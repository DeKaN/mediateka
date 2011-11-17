package mediateka.commands;

import mediateka.MediatekaView;

/**
 * Класс, представляющий команду удаления записи из таблицы персональных данных
 * @author Alexandr
 */
public class DeletePersonCommand implements DeleteCommand {

    public boolean Execute(int id) {
        return MediatekaView.managers.getPersManager().delete(id);
    }

    public String ToString() {
        return "Удалить персональные данные";
    }
}