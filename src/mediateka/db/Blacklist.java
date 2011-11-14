package mediateka.db;

import java.util.Iterator;
import java.util.List;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMElement;

/**
 * 
 * @author Alexandr
 */
public class Blacklist implements Records {

    private int autoIndex;
    private List<BlackListRecord> blackListRecs;

    /**
     * 
     * @param person
     * @param comment
     * @return  
     */
    public Blacklist Find(Person person, String comment) {
        throw new UnsupportedOperationException();
    }

    public void Add(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Delete(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Update(Record oldRecord, Record newRecord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     */
    public void Save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     */
    public void Load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Import(Records records) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public Records Export() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Records Find(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
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