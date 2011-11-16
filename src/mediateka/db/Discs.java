package mediateka.db;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mediateka.datamanagers.Condition;
import org.apache.commons.lang.StringUtils;
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
 * Класс, представляющий коллекцию дисков
 * @author Alexandr
 */
public class Discs implements Records {

    private int autoIndex = 1;
    private ArrayList<Disc> discsList = null;

    /**
     * Добавить диск в коллекцию
     * @param record Диск, который будет добавлен
     * @return true, если добавление успешно, иначе false
     */
    public boolean add(Record record) {
        if (find(record) == null) {
            try {
                Disc rec = (Disc) record;
                if (rec.getID() == 0) {
                    rec = new Disc(
                            autoIndex,
                            rec.getFilms(),
                            rec.getOwnerID(),
                            rec.getFormat(),
                            rec.getRegionCode(),
                            rec.isIsPresented());

                    autoIndex++;
                    discsList.add(rec);
                    return true;
                } else if (find(new Disc(rec.getOwnerID(), null, null)) == null) {
                    discsList.add(rec);
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Удалить диск из коллекции
     * @param record Диск, который будет удален
     * @return true, если удаление успешно, иначе false
     */
    public boolean delete(Record record) {
        Discs discs = null;
        if ((discs = (Discs) find(record)) != null) {
            discsList.remove(discs.getRecord(0));
            return true;
        }
        return false;
    }

    /**
     * Обновляет диск в коллекции
     * @param oldDisc Старое описание диска
     * @param newDisc Новое описание диска
     * @return true, если обновление успешно, иначе false
     */
    public boolean update(Record oldDisc, Record newDisc) {
        Discs discs = null;
        if ((discs = (Discs) find(oldDisc)) != null) {
            Disc disc = discsList.get(discsList.indexOf(discs.getRecord(0))),
                    newDsk = (Disc) newDisc;
            disc.setFilms(newDsk.getFilms());
            disc.setFormat(newDsk.getFormat());
            disc.setIsPresented(newDsk.isIsPresented());
            disc.setRegionCode(newDsk.getRegionCode());
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
            discsList = new ArrayList<Disc>();
            for (Iterator<Element> it = root.elements().iterator(); it.hasNext();) {
                try {
                    //TODO исправить!!!
//                    DOMElement elem = (DOMElement) it.next();
//                    NodeList nodes = elem.getChildNodes(),
//                            nodes2 = nodes.item(4).getChildNodes();
//                    Films films = new Films();
//                    String[] films2 = new String[nodes2.getLength()];
//                    for (int i = 0; i < films2.length; i++) {
//                        films2[i] = nodes2.item(i).getNodeValue();
//                    }
//                    discsList.add(new Disc(
//                            films2,
//                            Integer.getInteger(elem.getAttribute("discID")),
//                            nodes.item(0).getNodeValue(),
//                            Disc.Format.valueOf(nodes.item(1).getNodeValue()),
//                            Integer.parseInt(nodes.item(2).getNodeValue()),
//                            nodes.item(3).getNodeValue().equals("true"));
                } catch (Exception exc) {
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Найти диски в коллекции, подходящие под шаблон
     * @param record диск-шаблон, по которому будет проводиться поиск
     * @return Виртуальная коллекция с дисками, подходящими под шаблон
     */
    public Records find(Record record) {
        Discs retVal = new Discs();
        HashMap<String, String> map = new HashMap<String, String>();
        Disc disc;
        try {
            disc = (Disc) record;
        } catch (Exception e) {
            return null;
        }
        //TODO добавить поиск по id
        Films filmsCollection = disc.getFilms();
        if (filmsCollection != null) {
            String[] films = new String[filmsCollection.size()];
            for (int i = 0; i < films.length; i++) {
                films[i] = ((Film) filmsCollection.getRecord(i)).getRussianTitle();
            }
            map.put("films", StringUtils.join(films, ','));
        }
        Disc.Format format = disc.getFormat();
        if (format != null) {
            map.put("format", format.toString());
        }
        int region = disc.getRegionCode();
        if (region != 0) {
            map.put("regionCode", Integer.toString(region));
        }
        map.put("isPresent", Boolean.toString(disc.isIsPresented()));
        Condition cond = new Condition(map);
        for (Disc d : discsList) {
            if (cond.isEquals(d)) {
                retVal.add(d);
            }
        }
        return retVal.size() > 0 ? retVal : null;
    }

    /**
     * Записывает коллекцию дисков в XML Element
     * @return Коллекция, сериализованная в XML element
     */
    public Element toXmlElement() {
        Element elem = new DOMElement("films", Namespace.get("mediateka"));
        elem.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        elem.addAttribute("xsi:schemaLocation", "mediateka films.xsd");
        elem.addAttribute("autoIndex", Integer.toString(autoIndex));
        for (Iterator<Disc> it = discsList.iterator(); it.hasNext();) {
            Disc disc = it.next();
            elem.addText(disc.ToXmlElement().asXML());
        }
        return elem;
    }

    /**
     * Получает диск по индексу в коллекции
     * @param index Индекс диска
     * @return Диск хранившийся по данному индексу
     * @throws IndexOutOfBoundsException Индекс вышел за границы массива (index < 0 || index >= size()) 
     */
    public Record getRecord(int index) throws IndexOutOfBoundsException {
        return discsList.get(index);
    }

    /**
     * Получить количество дисков в коллекции
     * @return Количество дисков в коллекции
     */
    public int size() {
        return discsList.size();
    }
}