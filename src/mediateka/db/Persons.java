package mediateka.db;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mediateka.datamanagers.Condition;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

/**
 * Класс, представляющий таблицу с персональными данными
 * @author Alexandr
 */
public class Persons implements Records {

    private int autoIndex;
    private ArrayList<Person> personsList;

    public Persons() {
        autoIndex = 1;
        personsList = new ArrayList<Person>();
    }

    /**
     * Возвращает запись таблицы с персональными данными, которая хранится на указанной позиции
     * @param index Индекс записи
     * @return Запись таблицы с персональными данными, которая хранится на указанной позиции
     * @throws IndexOutOfBoundsException Если индекс вышел за пределы (index < 0 || index >= size()) 
     */
    public Record getRecord(int index) throws IndexOutOfBoundsException {
        return personsList.get(index);
    }

    /**
     * Возвращает количество записей таблицы с персональными данными
     * @return Количество записей в таблице с персональными данными
     */
    public int size() {
        return personsList.size();
    }

    /**
     * Добавляет запись в таблицу с персональными данными, если запись еще не существует
     * @param record Запись для добавления
     * @return true, если добавление прошло успешно, иначе - false
     */
    public boolean add(Record record) {
        if (find(record) == null) {
            try {
                Person rec = (Person) record;
                if (rec.getID() == 0) {
                    rec = new Person(
                            autoIndex,
                            rec.getLastName(),
                            rec.getFirstName(),
                            rec.getSecondName(),
                            rec.getPhone(),
                            rec.getComment());
                    autoIndex++;
                    personsList.add(rec);
                    return true;
                } else if (find(new HistoryRecord(rec.getID(), null, null, null, null, null, "")) == null) {
                    personsList.add(rec);
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Удалить запись из таблицы с персональными данными
     * @param record Запись для удаления из таблицы истории
     * @return true, если удаление прошло успешно, иначе - false
     */
    public boolean delete(Record record) {
        Persons pers = null;
        if ((pers = (Persons) find(record)) != null) {
            personsList.remove(pers.getRecord(0));
            return true;
        }
        return false;
    }

    /**
     * Обновляет запись в таблице с персональными данными
     * @param oldRecord Старая запись в таблице
     * @param newRecord Новая запись в таблице
     * @return true, если обновление прошло успешно, иначе - false
     */
    public boolean update(Record oldRecord, Record newRecord) {
        Persons pers = null;
        if ((pers = (Persons) find(oldRecord)) != null) {
            Person persRec = personsList.get(personsList.indexOf(pers.getRecord(0))),
                    newRec = (Person) newRecord;
            persRec.setFirstName(newRec.getFirstName());
            persRec.setSecondName(newRec.getSecondName());
            persRec.setLastName(newRec.getFirstName());
            persRec.setPhone(newRec.getPhone());
            persRec.setComment(newRec.getComment());
            return true;
        }
        return false;
    }

    /**
     * Сохранение в XML
     * @param fileName Имя файла
     * @return true, если сохранение прошло успешно, иначе false
     */
    public boolean save(String fileName) {
        try {
            Document doc = DocumentHelper.createDocument(this.toXmlElement());
            FileOutputStream fos = new FileOutputStream(fileName);
            OutputFormat format = OutputFormat.createPrettyPrint();
            XMLWriter writer = new XMLWriter(fos, format);
            writer.write(doc);
            writer.flush();
            fos.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Загрузка из XML
     * @param fileName Имя файла
     * @return true, если загрузка завершилась успешно, иначе false
     */
    public boolean load(String fileName) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            SAXParser parser = factory.newSAXParser();
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");
            SAXReader reader = new SAXReader(parser.getXMLReader(), true);
            DefaultElement root = (DefaultElement) (reader.read(new File(fileName)).getRootElement());
            autoIndex = Integer.parseInt(root.attribute("autoIndex").getValue());
            personsList = new ArrayList<Person>();
            for (Iterator<Element> it = root.elements().iterator(); it.hasNext();) {
                DefaultElement elem = (DefaultElement) it.next();
                personsList.add(new Person(
                        Integer.parseInt(elem.attribute("personID").getValue()),
                        elem.node(0).getText(),
                        elem.node(1).getText(),
                        elem.node(2).getText(),
                        elem.node(3).getText(),
                        elem.node(4).getText()));
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Найти записи в таблице, подходящие под шаблон
     * @param record Запись-шаблон, по которой будет проводиться поиск
     * @return Виртуальная таблица с записями, подходящими под шаблон
     */
    public Records find(Record record) {
        Persons retVal = new Persons();
        HashMap<String, String> map = new HashMap<String, String>();
        Person pers;
        try {
            pers = (Person) record;
        } catch (Exception e) {
            return null;
        }
        if (pers.getID() > 0) {
            map.put("personID", Integer.toString(pers.getID()));
        } else {
            if (!pers.getFirstName().equals("")) {
                map.put("firstName", pers.getFirstName());
            }
            if (!pers.getLastName().equals("")) {
                map.put("lastName", pers.getLastName());
            }
            if (!pers.getSecondName().equals("")) {
                map.put("secondName", pers.getSecondName());
            }
            if (!pers.getPhone().equals("")) {
                map.put("phoneNumber", pers.getPhone());
            }
            if (pers.getComment() != null) {
                map.put("comment", pers.getComment());
            }
        }
        Condition cond = new Condition(map);
        for (Person person : personsList) {
            if (cond.isEquals(person)) {
                retVal.add(person);
            }
        }
        return retVal.size() > 0 ? retVal : null;
    }

    /**
     * Записывает таблицу в XML Element
     * @return Строка с таблицей, сериализованной в XML element
     */
    public Element toXmlElement() {
        Element elem = new DOMElement("persons", Namespace.get("mediateka"));
        elem.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        elem.addAttribute("xsi:schemaLocation", "mediateka persons.xsd");
        elem.addAttribute("autoIndex", Integer.toString(autoIndex));
        for (Iterator<Person> it = personsList.iterator(); it.hasNext();) {
            Person person = it.next();
            elem.addText(person.ToXmlElement().asXML());
        }
        return elem;
    }

    public Record[] ToArray() {
        return (Record[]) personsList.toArray();
    }
}