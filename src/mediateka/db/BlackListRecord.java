package mediateka.db;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

/**
 * Класс, представляющий запись в таблице "Черный список"
 * @author Alexandr
 */
public class BlackListRecord implements Record {

    private int recordID = 0;
    private Person person = null;
    private String comment = "";

    /**
     * Получить ID записи
     * @return ID записи
     */
    public int getID() {
        return this.recordID;
    }

    /**
     * Записать ID записи
     * @param value 
     */
    public void setID(int value) {
        this.recordID = value;
    }

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
        this(person, "");
    }

    /**
     * Полный конструктор
     * @param person Персональные данные
     * @param comment Комментарий
     */
    public BlackListRecord(Person person, String comment) {
        this(0, person, comment);
    }

    /**
     * Внутренний конструктор
     * @param recordID ID записи
     * @param person Персональные данные
     * @param comment Комментарий к записи
     */
    public BlackListRecord(int recordID, Person person, String comment) {
        this.recordID = recordID;
        this.person = person;
        this.comment = comment;
    }

    /**
     * Внутренний конструктор
     * @param recordID ID записи
     */
    BlackListRecord(int recordID) {
        this.recordID = recordID;
    }

    /**
     * Сериализует запись в XML
     * @return Строка с записью, сериализованной в XML element
     */
    public Element toXmlElement() {
        Element elem = new DOMElement("record");
        elem.addAttribute("recordID", Integer.toString(this.recordID));
        elem.addElement("personID").addText(Integer.toString(this.person.getID()));
        elem.addElement("comment").addText(this.comment);
        return elem;
    }
}