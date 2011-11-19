package mediateka.db;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
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
import org.w3c.dom.NodeList;

/**
 * Класс, представляющий таблицу истории
 * @author Alexandr
 */
public class History implements Records {

    private int autoIndex;
    private ArrayList<HistoryRecord> historyList;

    public History() {
        autoIndex = 1;
        historyList = new ArrayList<HistoryRecord>();
    }

    /**
     * Возвращает запись таблицы истории, которая хранится на указанной позиции
     * @param index Индекс записи
     * @return Запись таблицы истории, которая хранится на указанной позиции
     * @throws IndexOutOfBoundsException Если индекс вышел за пределы (index < 0 || index >= size()) 
     */
    public Record getRecord(int index) throws IndexOutOfBoundsException {
        return historyList.get(index);
    }

    /**
     * Возвращает количество записей таблицы истории
     * @return Количество записей таблицы истории
     */
    public int size() {
        return historyList.size();
    }

    /**
     * Добавляет запись в таблицу истории, если запись еще не существует
     * @param record Запись для добавления
     * @return true, если добавление прошло успешно, иначе - false
     */
    public boolean add(Record record) {
        if (find(record) == null) {
            try {
                HistoryRecord rec = (HistoryRecord) record;
                if (rec.getID() == 0) {
                    rec = new HistoryRecord(
                            autoIndex,
                            rec.getDisc(),
                            rec.getPerson(),
                            rec.getGiveDate(),
                            rec.getPromisedDate(),
                            rec.getReturnDate(),
                            rec.getComment());
                    autoIndex++;
                    historyList.add(rec);
                    return true;
                } else if (find(new HistoryRecord(rec.getID())) == null) {
                    historyList.add(rec);
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Удалить запись из таблицы истории
     * @param record Запись для удаления из таблицы истории
     */
    public boolean delete(Record record) {
        History hist = null;
        if ((hist = (History) find(record)) != null) {
            historyList.remove(hist.getRecord(0));
            return true;
        }
        return false;
    }

    /**
     * Обновляет запись в таблице истории
     * @param oldRecord Старая запись в таблице
     * @param newRecord Новая запись в таблице
     */
    public boolean update(Record oldRecord, Record newRecord) {
        History hist = null;
        if ((hist = (History) find(oldRecord)) != null) {
            HistoryRecord histRec = historyList.get(historyList.indexOf(hist.getRecord(0))),
                    newRec = (HistoryRecord) newRecord;
            histRec.setDisc(newRec.getDisc());
            histRec.setPerson(newRec.getPerson());
            histRec.setGiveDate(newRec.getGiveDate());
            histRec.setReturnDate(newRec.getReturnDate());
            histRec.setPromisedDate(newRec.getPromisedDate());
            histRec.setComment(newRec.getComment());
            return true;
        }
        return false;
    }

    /**
     * Сохранение в XML
     * @param fileName Имя файла
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
            DOMElement root = (DOMElement) (reader.read(new File(fileName)).getRootElement());
            SimpleDateFormat format = new SimpleDateFormat("");
            autoIndex = Integer.parseInt(root.getAttribute("autoIndex"));
            historyList = new ArrayList<HistoryRecord>();
            for (Iterator<Element> it = root.elements().iterator(); it.hasNext();) {
                DOMElement elem = (DOMElement) it.next();
                NodeList nodes = elem.getChildNodes();
                historyList.add(new HistoryRecord(
                        Integer.parseInt(elem.getAttribute("recordID")),
                        (Disc) MediatekaView.managers.getDiscsManager().find(Integer.parseInt(nodes.item(0).getNodeValue())),
                        (Person) MediatekaView.managers.getPersManager().find(Integer.parseInt(nodes.item(0).getNodeValue())),
                        format.parse(nodes.item(2).getNodeValue()),
                        format.parse(nodes.item(3).getNodeValue()),
                        format.parse(nodes.item(4).getNodeValue()),
                        nodes.item(5).getNodeValue()));
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Найти записи в таблице истории, подходящие под шаблон
     * @param record Запись-шаблон, по которой будет проводиться поиск
     * @return Виртуальная таблица с записями, подходящими под шаблон
     */
    public Records find(Record record) {
        History retVal = new History();
        HashMap<String, String> map = new HashMap<String, String>();
        HistoryRecord rec;
        try {
            rec = (HistoryRecord) record;
        } catch (Exception e) {
            return null;
        }
        if (rec.getID() > 0) {
            map.put("personID", Integer.toString(rec.getID()));
        } else {
            if (rec.getDisc() != null) {
                map.put("discID", Integer.toString(rec.getDisc().getID()));
            }
            if (rec.getPerson() != null) {
                map.put("personID", Integer.toString(rec.getPerson().getID()));
            }
            if (rec.getComment() != "") {
                map.put("comment", rec.getComment());
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (rec.getGiveDate() != null) {
                map.put("dateOfIssue", formatter.format(rec.getGiveDate()));
            }
            if (rec.getGiveDate() != null) {
                map.put("dateOfReturn", formatter.format(rec.getReturnDate()));
            }
            if (rec.getGiveDate() != null) {
                map.put("promisingDate", formatter.format(rec.getPromisedDate()));
            }
        }
        Condition cond = new Condition(map);
        for (HistoryRecord historyRecord : historyList) {
            if (cond.isEquals(historyRecord)) {
                retVal.add(historyRecord);
            }
        }
        return retVal;
    }

    /**
     * Сериализует таблицу в XML
     * @return Строка с таблицей, сериализованной в XML element
     */
    public Element toXmlElement() {
        Element elem = new DOMElement("history", Namespace.get("mediateka"));
        elem.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        elem.addAttribute("xsi:schemaLocation", "mediateka history.xsd");
        elem.addAttribute("autoIndex", Integer.toString(autoIndex));
        for (Iterator<HistoryRecord> it = historyList.iterator(); it.hasNext();) {
            HistoryRecord historyRecord = it.next();
            elem.addText(historyRecord.ToXmlElement().asXML());
        }
        return elem;
    }
    
    public Record[] ToArray() {
        return (Record[])historyList.toArray();
    }
}
