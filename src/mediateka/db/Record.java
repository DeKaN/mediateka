package mediateka.db;

import org.dom4j.Element;

/**
 * Интерфейс, представляющий запись в базе
 * @author DeKaN
 */
public interface Record {

    /**
     * Сериализует запись в XML
     * @return Строка с записью, сериализованной в XML element
     */
	Element ToXmlElement();

}