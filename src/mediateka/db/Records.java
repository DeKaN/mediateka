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
     * @param oldRecord Старая запись (уже имеется в таблице)
     * @param newRecord Новая запись (будет записана поверх старой)
     * @return true, если обновление успешно, иначе false
     */
    boolean update(Record oldRecord, Record newRecord);

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
     */
    boolean load(String fileName);

    /**
     * Найти записи в таблице, подходящие под шаблон
     * @param record Запись-шаблон, по котоорой будет проводиться поиск
     * @return Виртуальная таблица с записями, подходящими под шаблон
     */
    Records find(Record record);

    /**
     * Записывает таблицу в XML Element
     * @return Строка с таблицей, сериализованной в XML element
     */
    Element toXmlElement();
    
    /**
     * Преобразует в массив
     * @return Массив из записей
     */
    Record[] ToArray();
}