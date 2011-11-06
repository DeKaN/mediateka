package mediateka.db;

/**
 * Интерфейс, представляющий запись в базе
 * @author DeKaN
 */
public interface Record {

    /**
     * Сериализует запись в XML
     * @return Строка с записью, сериализованной в XML element
     */
	String ToXmlElement();

}