package mediateka.db;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMElement;

/**
 * Класс, представляющий запись в таблице "Черный список"
 * @author Alexandr
 */
public class BlackListRecord implements Record {

    private int recordID;
    private Person person;
    private String comment;

    /**
     * Получить персональные данные
     * @return Персональные данные
     */
    public Person getPerson() {
        return this.person;
    }

    /**
     * Установить персональные данные
     * @param person Персональные данные, которые будет установлены
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Получить комментарий к записи
     * @return Комментарий к записи
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Установить комментарий к записи
     * @param comment Комментарий, который будет установлен
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Конструктор с обязательным параметром
     * @param person Персональные данные
     */
    public BlackListRecord(Person person) {
        this.person = person;
    }

    /**
     * Полный конструктор
     * @param person Персональные данные
     * @param comment Комментарий
     */
    public BlackListRecord(Person person, String comment) {
        this.person = person;
        this.comment = comment;
    }

    /**
     * Сериализует запись в XML
     * @return Строка с записью, сериализованной в XML element
     */
    public String ToXmlElement() {
        Element elem = new DOMElement("record");
        elem.addAttribute("recordID", Integer.toString(this.recordID));
        elem.addElement("personID").addText(Integer.toString(this.person.getID()));
        elem.addElement("comment").addText(this.comment);
        return elem.asXML();
    }
}