package mediateka.db;

import java.util.List;
import org.dom4j.Element;

/**
 * Интерфейс, представляющий таблицу в базу
 * @author DeKaN
 */
public interface Records {

    /**
     * Возвращает запись, которая хранится на указанной позиции
     * @param index Индекс записи
     * @return Запись, которая хранится на указанной позиции
     * @throws IndexOutOfBoundsException Если индекс вышел за пределы (index < 0 || index >= size())
     */
    Record getRecord(int index) throws IndexOutOfBoundsException;

    /**
     * Возвращает количество записей
     * @return Количество записей
     */
    int size();
    
    /**
     * Проверяет уникальность записи
     * @param record Запись для проверки
     * @return true, если запись отсутствует в базе, иначе false
     */
    boolean isUnique(Record record);

    /**
     * Добавить запись в таблицу
     * @param record Запись для добавления в таблицу
     * @return true, если добавление успешно, иначе false
     */
    boolean add(Record record);

    /**
     * Удалить запись из таблицы
     * @param record Запись для удаления из таблицы
     * @return true, если удаление успешно, иначе false
     */
    boolean delete(Record record);

    /**
     * Заменить запись в таблице новой записью
     * @param record Запись, которой будет заменена имеющаяся
     * @return true, если обновление успешно, иначе false
     */
    boolean update(Record record);

    /**
     * Сохранение в XML
     * @param fileName Имя файла, в который будет сохранен XML
     * @return true, если сохранение успешно, иначе false
     */
    boolean save(String fileName);

    /**
     * Загрузка из XML с валидацией
     * @param fileName Имя файла, из которого будет загружен XML
     * @return true, если загрузка завершилась успешно, иначе false
     * @throws LoadException Если не удалось загрузить файл 
     */
    boolean load(String fileName) throws LoadException;

    /**
     * Найти записи в таблице, подходящие под шаблон
     * @param record Запись-шаблон, по котоорой будет проводиться поиск
     * @return Виртуальная таблица с записями, подходящими под шаблон
     */
    Records find(Record record);
    
    /**
     * Найти запись в таблице с заданным ID
     * @param id ID записи
     * @return Запись с нужным ID
     */
    Record find(int id);

    /**
     * Записывает таблицу в XML Element
     * @return Строка с таблицей, сериализованной в XML element
     */
    Element toXmlElement();

    /**
     * Преобразует в список
     * @return Список из записей
     */
    List<Record> toList();
}