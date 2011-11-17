package mediateka.commands;

import mediateka.MediatekaView;

/**
 * Класс, представляющий команду удаления диска
 * @author DeKaN
 */
public class DeleteDiscCommand implements DeleteCommand {

    public boolean Execute(int id) {
        return MediatekaView.managers.getDiscsManager().delete(id);
    }

    public String ToString() {
        return "Удалить диск";
    }
}
