/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.datamanagers;

import java.util.List;
import mediateka.db.Record;
import mediateka.db.Records;
import mediateka.db.Table;

/**
 *
 * @author DeKaN
 */
public class Manager {

    private Table table;

    public Manager(Table t) {
        table = t;
    }

    /**
     * Добавить запись
     * @param record Запись для добавления
     * @return true, если добавление успешно, иначе false
     */
    public boolean add(Record record) {
        return table.add(record);
    }

    /**
     * Удалить запись
     * @param id ID записи
     * @return true, если удаление успешно, иначе false
     */
    public boolean delete(int id) {
        return table.delete(find(id));
    }

    /**
     * Обновить запись
     * @param newData Новая запись
     * @return true, если обновление успешно, иначе false 
     */
    public boolean edit(Record newData) {
        return table.update(newData);
    }

    /**
     * Найти записи по ID
     * @param id ID записи
     * @return Запись с нужным ID
     */
    public Record find(int id) {
        return table.find(id);
    }

    /**
     * Найти записи, подходящие под шаблон
     * @param record Запись-шаблон, по которой будет проводиться поиск
     * @return Список записей, подходящих под шаблон
     */
    public Records find(Record record) {
        return table.find(record);
    }

    /**
     * Получить имеющиеся записей
     * @return Список имеющихся записей
     */
    public List<Record> getRecords() {
        return table.toList();
    }

    /**
     * Сохранение в XML
     * @param fileName Имя файла, в который будет сохранен XML
     * @return true, если сохранение успешно, иначе false
     */
    public boolean save(String fileName) {
        return table.save(fileName);
    }

    /**
     * Загрузить из XML
     * @param fileName Имя файла, из которого будет загружен XML
     * @return true, если загрузка успешна, иначе false
     */
    public boolean load(String fileName) {
        return table.load(fileName);
    }
}
