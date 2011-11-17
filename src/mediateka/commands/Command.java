package mediateka.commands;

/**
 * Интерфейс для команд
 * @author DeKaN
 */
public interface Command {

    /**
     * Получить имя команды, предназначенное для пользователя
     * @return Имя команды, предназначенное для пользователя
     */
    String ToString();
}
