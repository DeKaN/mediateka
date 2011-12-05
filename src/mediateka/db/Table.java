/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.db;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

/**
 *
 * @author DeKaN
 */
public abstract class Table implements Records {

    protected int autoIndex;
    protected ArrayList<Record> recordsList;
    protected String tableName = "table";

    public Table() {
        autoIndex = 1;
        recordsList = new ArrayList<Record>();
    }

    protected abstract Record createRecord(int id);

    public Record getRecord(int index) throws IndexOutOfBoundsException {
        return recordsList.get(index);
    }

    public int size() {
        return recordsList.size();
    }

    public boolean isUnique(Record record) {
        return (find(record) == null);
    }

    public boolean add(Record record) {
        if (!isUnique(record)) {
            return false;
        }
        if (record.getID() == 0) {
            record.setID(autoIndex++);
        }
        recordsList.add(record);
        return true;
    }

    public boolean delete(Record record) {
        Records recs = null;
        if ((recs = find(record)) != null) {
            recordsList.remove(recs.getRecord(0));
            return true;
        }
        return false;
    }

    public boolean update(Record record) {
        Records recs = null;
        if ((recs = find(record)) != null) {
            recordsList.set(recordsList.indexOf(recs.getRecord(0)), record);
            return true;
        }
        return false;
    }

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

    protected DefaultElement getRootElement(String fileName) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            SAXParser parser = factory.newSAXParser();
            parser.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");
            SAXReader reader = new SAXReader(parser.getXMLReader(), true);
            Document document = reader.read(new File(fileName));
            DefaultElement root = (DefaultElement) document.getRootElement();
            autoIndex = Integer.parseInt(root.attribute("autoIndex").getValue());
            return root;
        } catch (Exception ex) {
            return null;
        }
    }

    public abstract boolean load(String fileName) throws LoadException;

    public abstract Records find(Record record);

    public Record find(int id) {
        return find(createRecord(id)).getRecord(0);
    }

    public Element toXmlElement() {
        Element elem = new DefaultElement(tableName, Namespace.get("mediateka"));
        elem.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        elem.addAttribute("xsi:schemaLocation", "mediateka " + tableName + ".xsd");
        elem.addAttribute("autoIndex", Integer.toString(autoIndex));
        for (Iterator<Record> it = recordsList.iterator(); it.hasNext();) {
            Record record = it.next();
            elem.add(record.toXmlElement());
        }
        return elem;
    }

    public List<Record> toList() {
        return (List<Record>) recordsList.clone();
    }
}
