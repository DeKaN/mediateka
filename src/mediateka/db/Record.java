package mediateka.db;

import org.dom4j.Element;

/**
 * Интерфейс, представляющий запись в базе
 * @author DeKaN
 */
public interface Record {

    /**
     * Получить ID записи
     * @return ID записи
     */
    public int getID();

    /**
     * Записать ID записи
     * @param value 
     */
    void setID(int value);

    /**
     * Сериализует запись в XML
     * @return Строка с записью, сериализованной в XML element
     */
    Element toXmlElement();
}