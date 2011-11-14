package mediateka.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import mediateka.datamanagers.Condition;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMElement;

/**
 * 
 * @author Alexandr
 */
public class Blacklist implements Records {

    private int autoIndex;
    private ArrayList<BlackListRecord> blackListRecs;

    private Blacklist() {
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
     */
    public boolean Add(Record record) {
        if (Find(record) == null) {
            try {
                BlackListRecord rec = (BlackListRecord) record;
                if (rec.getID() == 0) {
                    rec = new BlackListRecord(autoIndex, rec.getPerson(), rec.getComment());
                    autoIndex++;
                    blackListRecs.add(rec);
                    return true;
                } else if (Find(new BlackListRecord(rec.getID(), null, "")) == null) {
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
     * 
     * @param record 
     */
    public boolean Delete(Record record) {
        Blacklist bl = null;
        if ((bl = (Blacklist) Find(record)) != null) {
            blackListRecs.remove(bl.getRecord(0));
            return true;
        }
        return false;
    }

    /**
     * 
     * @param oldRecord
     * @param newRecord 
     */
    public boolean Update(Record oldRecord, Record newRecord) {
        Blacklist bl = null;
        if ((bl = (Blacklist) Find(oldRecord)) != null) {
            BlackListRecord br = blackListRecs.get(blackListRecs.indexOf(bl.getRecord(0))),
                    newRec = (BlackListRecord) newRecord;
            br.setPerson(newRec.getPerson());
            br.setComment(newRec.getComment());
            return true;
        }
        return false;
    }

    /**
     * 
     */
    public boolean Save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean Validate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     */
    public boolean Load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean Import(Records records) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public Records Export() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Найти записи в таблице, подходящие под шаблон
     * @param record Запись-шаблон, по котоорой будет проводиться поиск
     * @return Виртуальная таблица с записями, подходящими под шаблон
     */
    public Records Find(Record record) {
        Blacklist retVal = new Blacklist();
        HashMap<String, String> map = new HashMap<String, String>();
        BlackListRecord rec;
        try {
            rec = (BlackListRecord) record;
        } catch (Exception e) {
            return null;
        }
        if (rec.getPerson() != null) {
            map.put("personID", Integer.toString(rec.getPerson().getID()));
        }
        if (rec.getComment() != null) {
            map.put("comment", rec.getComment());
        }
        Condition cond = new Condition(map);
        for (BlackListRecord blackListRecord : blackListRecs) {
            if (cond.isEquals(blackListRecord)) {
                retVal.Add(blackListRecord);
            }
        }
        return retVal;
    }

    /**
     * Сериализует таблицу в XML
     * @return Строка с таблицей, сериализованной в XML element
     */
    public Element ToXmlElement() {
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