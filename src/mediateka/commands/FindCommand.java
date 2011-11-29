package mediateka.commands;

import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Интерфейс для команды поиска
 * @author DeKaN
 */
public interface FindCommand extends Command {
    
    /**
     * Выполнить команду поиска
     * @param id ID записи, передаваемое команде
     * @return Запись, с данным ID
     */
    public Record execute(int id);
    
    /**
     * Выполнить команду поиска
     * @param record Запись-шаблон, передаваемая команде
     * @return Записи, которые подходят под шаблон
     */
    public Records execute(Record record);
    
}
