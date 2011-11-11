package mediateka.db;
/**
 * Интерфейс для фабрики по созданию записей
 * @author DeKaN
 */
public interface RecordFactory {

    /**
     * Создать запись
     * @return Объект типа Record согласно используемой фабрике
     */
	Record CreateRecord();

}