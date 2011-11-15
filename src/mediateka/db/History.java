package mediateka.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import mediateka.datamanagers.Condition;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMElement;

/**
 * Класс, представляющий таблицу истории
 * @author Alexandr
 */
public class History implements Records {

    private int autoIndex;
    private List<HistoryRecord> historyList;

    private History() {
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
                } else if (find(new HistoryRecord(rec.getID(), null, null, null, null, null, "")) == null) {
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
     * 
     */
    public boolean save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     */
    public boolean load() {
        throw new UnsupportedOperationException("Not supported yet.");
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
}
