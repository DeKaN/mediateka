package mediateka.commands;

import mediateka.MediatekaView;

/**
 * Класс, представляющий команду удаления диска
 * @author DeKaN
 */
public class DeleteDiscCommand implements DeleteCommand {

    public boolean Execute(int id) {
        try {
            return MediatekaView.managers.getDiscsManager().delete(id);
        } catch (Exception ex) {
            return false;
        }
    }

    public String ToString() {
        return "Удалить диск";
    }
}
