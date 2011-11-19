package mediateka.db;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

/**
 * Класс, представляющий персональные данные человека
 * @author Alexandr
 */
public class Person implements Record {

    private int personID = 0;
    private String lastName = "";
    private String firstName = "";
    private String secondName = "";
    private String phone = "";
    private String comment = "";

    /**
     * Получить ID человека
     * @return ID человека
     */
    public int getID() {
        return this.personID;
    }

    /**
     * Получить фамилию человека
     * @return Фамилия человека
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Установить фамилию человека
     * @param lastName Фамилия, которая будет установлена
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Получить имя человека
     * @return Имя человека
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Установить имя человека
     * @param firstName Имя, которое будет установлено
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Получить отчество человека
     * @return Отчество человека
     */
    public String getSecondName() {
        return this.secondName;
    }

    /**
     * Установить отчество человека
     * @param secondName Отчество, которое будет установлено
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * Получить телефон человека
     * @return Телефон человека
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * Установить телефон человека
     * @param phone Телефон, который будет установлен
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Получить комментарий к персональным данным человека
     * @return Комментарий к персональным данным
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Установить комментарий к персональным данным человека
     * @param comment Комментарий, который будет установлен
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Полный конструктор
     * @param lastName Фамилия человека
     * @param firstName Имя человека
     * @param secondName Отчество человека
     * @param phone Телефон человека
     * @param comment Комментарий к персональным данным
     */
    public Person(String lastName, String firstName, String secondName, String phone, String comment) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.comment = comment;
    }

    /**
     * Внутренний полный конструктор
     * @param personID ID человека
     * @param lastName Фамилия человека
     * @param firstName Имя человека
     * @param secondName Отчество человека
     * @param phone Телефон человека
     * @param comment Комментарий к персональным данным
     */
    Person(int personID, String lastName, String firstName, String secondName, String phone, String comment) {
        this.personID = personID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.comment = comment;
    }

    /**
     * Внутренний конструктор
     * @param personID ID человека
     */
    public Person(int personID) {
    }    

    /**
     * Сериализует запись с персональными данными в XML
     * @return Строка с записью, сериализованной в XML element
     */
    public Element ToXmlElement() {
        Element elem = new DOMElement("person");
        elem.addAttribute("personID", Integer.toString(this.personID));
        elem.addElement("firstfirstName").addText(this.firstName);
        elem.addElement("lastName").addText(this.lastName);
        elem.addElement("secondName").addText(this.secondName);
        elem.addElement("phoneNumber").addText(this.phone);
        elem.addElement("comment").addText(this.comment);
        return elem;
    }
}