package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления диска в коллекцию
 * @author Alexandr
 */
public class AddDiscCommand implements AddCommand {

    public boolean Execute(Record record) {
        try {
            return MediatekaView.managers.getDiscsManager().add(record);
        } catch (Exception ex) {
            return false;
        }
    }
}
