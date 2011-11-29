package mediateka.commands;

/**
 * Интерфейс для команды удаления
 * @author DeKaN
 */
public interface DeleteCommand {
    
    /**
     * Выполнить команду
     * @param id ID записи, передаваемое команде
     * @return true, если выполнение завершилось успешно, иначе false
     */
    public boolean execute(int id);
    
}
