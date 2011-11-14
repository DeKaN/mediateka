package mediateka.db;

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
     * Добавить запись в таблицу
     * @param record Запись для добавления в таблицу
     */
    boolean Add(Record record);

    /**
     * Удалить запись из таблицы
     * @param record Запись для удаления из таблицы
     */
    boolean Delete(Record record);

    /**
     * Заменить запись в таблице новой записью
     * @param oldRecord Старая запись (уже имеется в таблице)
     * @param newRecord Новая запись (будет записана поверх старой)
     */
    boolean Update(Record oldRecord, Record newRecord);

    /**
     * Сохранить таблицу в базу
     */
    boolean Save();

    /**
     * Загрузить таблицу из базы
     */
    boolean Load();

    /**
     * Проверяет на валидность таблицу
     * @return true, если таблица валидна, иначе false
     */
    boolean Validate();

    /**
     * Импортировать данные таблицы в текущую
     * @param records Таблица для импорта
     */
    boolean Import(Records records);

    /**
     * Экспортировать таблицу
     * @return Таблица с данными
     */
    Records Export();

    /**
     * Найти записи в таблице, подходящие под шаблон
     * @param record Запись-шаблон, по котоорой будет проводиться поиск
     * @return Виртуальная таблица с записями, подходящими под шаблон
     */
    Records Find(Record record);

    /**
     * Сериализует таблицу в XML
     * @return Строка с таблицей, сериализованной в XML element 
     */
    Element ToXmlElement();
}