package mediateka.commands;

import mediateka.db.Record;

/**
 * Интерфейс для команды редактирования
 * @author DeKaN
 */
public interface EditCommand extends Command {
    
    /**
     * Выполнить команду редактирования
     * @param id ID записи, передаваемое команде
     * @param record Запись, передаваемая команде
     * @return true, если выполнение завершилось успешно, иначе false
     */
    public boolean execute(Record record);
    
}
