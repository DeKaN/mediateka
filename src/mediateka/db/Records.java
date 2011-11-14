package mediateka.db;

import org.dom4j.Element;

/**
 * Интерфейс, представляющий таблицу в базу
 * @author DeKaN
 */
public interface Records {

	/**
	 * Добавить запись в таблицу
	 * @param record Запись для добавления в таблицу
	 */
	void Add(Record record);

	/**
	 * Удалить запись из таблицы
	 * @param record Запись для удаления из таблицы
	 */
	void Delete(Record record);

	/**
	 * Заменить запись в таблице новой записью
	 * @param oldRecord Старая запись (уже имеется в таблице)
	 * @param newRecord Новая запись (будет записана поверх старой)
	 */
	void Update(Record oldRecord, Record newRecord);

        /**
         * Сохранить таблицу в базу
         */
	void Save();

        /**
         * Загрузить таблицу из базы
         */
	void Load();

	/**
	 * Импортировать данные таблицы в текущую
	 * @param records Таблица для импорта
	 */
	void Import(Records records);

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