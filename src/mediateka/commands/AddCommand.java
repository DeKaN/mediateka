package mediateka.commands;

import mediateka.db.Record;

/**
 * Интерфейс для команды добавления
 * @author DeKaN
 */
public interface AddCommand {
    
    /**
     * Выполнить команду добавления
     * @param record Запись, передаваемая команде
     * @return true, если выполнение завершилось успешно, иначе false
     */
    public boolean execute(Record record);
    
}
