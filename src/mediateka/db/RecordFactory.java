package mediateka.db;

/**
 * Интерфейс абстрактной фабрики
 * @author DeKaN
 */
public interface RecordFactory {

    public Record createInstance(int id);
}
