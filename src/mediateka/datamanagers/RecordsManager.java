package mediateka.datamanagers;

import mediateka.db.Record;
import mediateka.db.Records;

/**
 * Интерфейс менеджеров
 * @author DeKaN
 */
public interface RecordsManager {

    /**
     * Добавить запись
     * @param record Запись для добавления
     * @return true, если добавление успешно, иначе false
     */
    boolean add(Record record);

    /**
     * Удалить запись
     * @param id ID записи
     * @return true, если удаление успешно, иначе false
     */
    boolean delete(int id);

    /**
     * Обновить запись
     * @param id ID старой записи
     * @param newData Новая запись
     * @return true, если обновление успешно, иначе false 
     */
    boolean edit(int id, Record newData);

    /**
     * Найти записи по ID
     * @param id ID записи
     * @return Запись с нужным ID
     */
    Record find(int id);

    /**
     * Найти записи, подходящие под шаблон
     * @param record Запись-шаблон, по которой будет проводиться поиск
     * @return Список записей, подходящих под шаблон
     */
    Records find(Record record);
    
    /**
     * Получить массив имеющихся записей
     * @return Массив имеющихся записей
     */
    Record[] getRecords();
}