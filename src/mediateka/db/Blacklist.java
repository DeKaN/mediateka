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
     * 
     * @return 
     */
    public boolean save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean validate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return 
     */
    public boolean load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Найти записи в таблице, подходящие под шаблон
     * @param record Запись-шаблон, по котоорой будет проводиться поиск
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
        if (rec.getPerson() != null) {
            map.put("personID", Integer.toString(rec.getPerson().getID()));
        }
        if (rec.getComment() != null) {
            map.put("comment", rec.getComment());
        }
        Condition cond = new Condition(map);
        for (BlackListRecord blackListRecord : blackListRecs) {
            if (cond.isEquals(blackListRecord)) {
                retVal.add(blackListRecord);
            }
        }
        return retVal;
    }

    /**
     * Сериализует таблицу в XML
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