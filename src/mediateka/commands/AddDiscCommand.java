package mediateka.commands;

import mediateka.MediatekaView;
import mediateka.db.Record;

/**
 * Класс, представляющий команду добавления диска в коллекцию
 * @author Alexandr
 */
public class AddDiscCommand implements AddCommand {

    public boolean Execute(Record record) {
        return MediatekaView.managers.getDiscsManager().add(record);
    }

    public String ToString() {
        return "Добавить диск в коллекцию";
    }

}
