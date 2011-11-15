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
    boolean add(Record record);

    /**
* Удалить запись из таблицы
* @param record Запись для удаления из таблицы
*/
    boolean delete(Record record);

    /**
* Заменить запись в таблице новой записью
* @param oldRecord Старая запись (уже имеется в таблице)
* @param newRecord Новая запись (будет записана поверх старой)
*/
    boolean update(Record oldRecord, Record newRecord);

    /**
* Сохранить таблицу в базу
*/
    boolean save();

    /**
* Загрузить таблицу из базы
*/
    boolean load();

    /**
* Проверяет на валидность таблицу
* @return true, если таблица валидна, иначе false
*/
    boolean validate();

    /**
* Найти записи в таблице, подходящие под шаблон
* @param record Запись-шаблон, по котоорой будет проводиться поиск
* @return Виртуальная таблица с записями, подходящими под шаблон
*/
    Records find(Record record);

    /**
* Сериализует таблицу в XML
* @return Строка с таблицей, сериализованной в XML element
*/
    Element toXmlElement();
}