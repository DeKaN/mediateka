package mediateka.db;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mediateka.MediatekaView;
import mediateka.datamanagers.Condition;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * Класс, представляющий таблицу черного списка
 * @author DeKaN
 */
public class Blacklist implements Records {

    private int autoIndex;
    private ArrayList<BlackListRecord> blackListRecs;

    public Blacklist() {
        autoIndex = 1;
        blackListRecs = new ArrayList<BlackListRecord>();
    }

    /**
     * Возвращает запись черного списка, которая хранится на указанной позиции
     * @param index Индекс записи
     * @return Запись черного списка, которая хранится на указанной позиции
     * @throws IndexOutOfBoundsException Если индекс вышел за пределы (index < 0 || index >= size()) 
     */
    public Record getRecord(int index) throws IndexOutOfBoundsException {
        return blackListRecs.get(index);
    }

    /**
     * Возвращает количество записей черного списка
     * @return Количество записей черного списка
     */
    public int size() {
        return blackListRecs.size();
    }

    /**
     * Добавляет запись в таблицу, если она еще не существует
     * @param record Запись для добавления
     * @return true, если добавление успешно, иначе false
     */
    public boolean add(Record record) {
        if (find(record) == null) {
            try {
                BlackListRecord rec = (BlackListRecord) record;
                if (rec.getID() == 0) {
                    rec = new BlackListRecord(autoIndex, rec.getPerson(), rec.getComment());
                    autoIndex++;
                    blackListRecs.add(rec);
                    return true;
                } else if (find(new BlackListRecord(rec.getID(), null, "")) == null) {
                    blackListRecs.add(rec);
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Удаляет запись из таблицы, если она существует
     * @param record Запись для удаления
     * @return true, если удаление успешно, иначе false
     */
    public boolean delete(Record record) {
        Blacklist bl = null;
        if ((bl = (Blacklist) find(record)) != null) {
            blackListRecs.remove(bl.getRecord(0));
            return true;
        }
        return false;
    }

    /**
     * Обновляет запись в таблице
     * @param oldRecord Текущая запись
     * @param newRecord Новая запись
     * @return true, если обновление успешно, иначе false
     */
    public boolean update(Record oldRecord, Record newRecord) {
        Blacklist bl = null;
        if ((bl = (Blacklist) find(oldRecord)) != null) {
            BlackListRecord br = blackListRecs.get(blackListRecs.indexOf(bl.getRecord(0))),
                    newRec = (BlackListRecord) newRecord;
            br.setPerson(newRec.getPerson());
            br.setComment(newRec.getComment());
            return true;
        }
        return false;
    }

    /**
     * Сохранение в XML
     * @param fileName Имя файла, в который будет сохранен XML
     * @return true, если сохранение успешно, иначе false
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
     * Загрузка из XML с валидацией
     * @param fileName Имя файла, из которого будет загружен XML
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
            DOMElement root = (DOMElement) (reader.read(new File(fileName)).getRootElement());
            autoIndex = Integer.parseInt(root.getAttribute("autoIndex"));
            blackListRecs = new ArrayList<BlackListRecord>();
            for (Iterator<Element> it = root.elements().iterator(); it.hasNext();) {
                DOMElement elem = (DOMElement) it.next();
                blackListRecs.add(new BlackListRecord(
                        Integer.parseInt(elem.getAttribute("recordID")),
                        (Person) MediatekaView.managers.getPersManager().find(Integer.parseInt(elem.getFirstChild().getNodeValue())),
                        elem.getLastChild().getNodeValue()));
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
        Blacklist retVal = new Blacklist();
        HashMap<String, String> map = new HashMap<String, String>();
        BlackListRecord rec;
        try {
            rec = (BlackListRecord) record;
        } catch (Exception e) {
            return null;
        }
        if (rec.getID() > 0) {
            map.put("filmID", Integer.toString(rec.getID()));
        } else {
            if (rec.getPerson() != null) {
                map.put("personID", Integer.toString(rec.getPerson().getID()));
            }
            if (!rec.getComment().equals("")) {
                map.put("comment", rec.getComment());
            }
        }
        Condition cond = new Condition(map);
        for (BlackListRecord blackListRecord : blackListRecs) {
            if (cond.isEquals(blackListRecord)) {
                retVal.add(blackListRecord);
            }
        }
        return retVal.size() > 0 ? retVal : null;
    }

    /**
     * Записывает таблицу в XML Element
     * @return Строка с таблицей, сериализованной в XML element
     */
    public Element toXmlElement() {
        Element elem = new DOMElement("blackList", Namespace.get("mediateka"));
        elem.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        elem.addAttribute("xsi:schemaLocation", "mediateka blackList.xsd");
        elem.addAttribute("autoIndex", Integer.toString(autoIndex));
        for (Iterator<BlackListRecord> it = blackListRecs.iterator(); it.hasNext();) {
            BlackListRecord blackListRecord = it.next();
            elem.addText(blackListRecord.ToXmlElement().asXML());
        }
        return elem;
    }
}