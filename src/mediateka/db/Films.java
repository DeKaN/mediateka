package mediateka.db;

import biz.source_code.base64Coder.Base64Coder;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mediateka.datamanagers.Condition;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

/**
 * Класс, представляющий коллекцию фильмов
 * @author DeKaN
 */
public class Films implements Records {

    private int autoIndex;
    private ArrayList<Record> filmsList;

    public Films() {
        autoIndex = 1;
        filmsList = new ArrayList<Record>();
    }

    /**
     * Добавить фильм в таблицу
     * @param record Фильм, который будет добавлен
     * @return true, если добавление успешно, иначе false
     */
    public boolean add(Record record) {
        //TODO make isUnique
        if (find(record) == null) {
            try {
                Film rec = (Film) record;
                if (rec.getID() == 0) {
                    //TODO default visible for setId
                    rec = new Film(autoIndex, rec.getRussianTitle(),
                            rec.getEnglishTitle(), rec.getYear(), rec.getDescription(),
                            rec.getGenres(), rec.getCountries(), rec.getComment(),
                            rec.getLength(), rec.getRating(), rec.getSubtitles(),
                            rec.getCover(), rec.getSoundLanguages(), rec.isSeen());
                    autoIndex++;
                    filmsList.add(rec);
                    return true;
                } else if (find(new Film(rec.getID(), "")) == null) {
                    filmsList.add(rec);
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Удалить фильм из таблицы
     * @param record Фильм, который будет удален
     * @return true, если удаление успешно, иначе false
     */
    public boolean delete(Record record) {
        Records f = null;
        if ((f = find(record)) != null) {
            filmsList.remove(f.getRecord(0));
            return true;
        }
        return false;
    }

    public boolean update(Record record) {
        Records f = null;
        if ((f = find(record)) != null) {
            filmsList.set(filmsList.indexOf(f.getRecord(0)), record);
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
            DefaultElement root = (DefaultElement) (reader.read(new File(fileName)).getRootElement());
            autoIndex = Integer.parseInt(root.attribute("autoIndex").getValue());
            filmsList = new ArrayList<Record>();
            for (Iterator<Element> it = root.elements().iterator(); it.hasNext();) {
                try {

                    DefaultElement elem = (DefaultElement) it.next(), elem2 = (DefaultElement) elem.element("genres");
                    String[] genres = new String[elem2.nodeCount()];
                    int i = 0;
                    for (Iterator<Node> it1 = elem2.iterator(); it1.hasNext();) {
                        Node node = it1.next();
                        genres[i] = node.getText();
                        i++;
                    }
                    elem2 = (DefaultElement) elem.element("countries");
                    String[] countries = new String[elem2.nodeCount()];
                    i = 0;
                    for (Iterator<Node> it1 = elem2.iterator(); it1.hasNext();) {
                        Node node = it1.next();
                        countries[i] = node.getText();
                        i++;
                    }
                    elem2 = (DefaultElement) elem.element("subtitles");
                    String[] subtitles = new String[elem2.nodeCount()];
                    i = 0;
                    for (Iterator<Node> it1 = elem2.iterator(); it1.hasNext();) {
                        Node node = it1.next();
                        subtitles[i] = node.getText();
                        i++;
                    }
                    elem2 = (DefaultElement) elem.element("soundLanguages");
                    String[] soundLanguages = new String[elem2.nodeCount()];
                    i = 0;
                    for (Iterator<Node> it1 = elem2.iterator(); it1.hasNext();) {
                        Node node = it1.next();
                        soundLanguages[i] = node.getText();
                        i++;
                    }
                    filmsList.add(new Film(Integer.parseInt(elem.attribute("filmID").getValue()),
                            elem.node(0).getText(),
                            elem.node(1).getText(),
                            Integer.parseInt(elem.node(2).getText()),
                            elem.node(3).getText(),
                            genres,
                            countries,
                            elem.node(6).getText(),
                            Integer.parseInt(elem.node(7).getText()),
                            Integer.parseInt(elem.node(8).getText()),
                            subtitles,
                            Base64Coder.decodeLines(elem.node(10).getText()),
                            soundLanguages,
                            elem.node(12).getText().equals("true")));
                } catch (Exception exc) {
                }
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
        Films retVal = new Films();
        HashMap<String, String> map = new HashMap<String, String>();
        Film rec;
        try {
            rec = (Film) record;
        } catch (Exception e) {
            return null;
        }
        if (rec.getID() > 0) {
            map.put("filmID", Integer.toString(rec.getID()));
        } else {
            if (!rec.getRussianTitle().equals("")) {
                map.put("russianTitle", rec.getRussianTitle());
            }
            if (!rec.getEnglishTitle().equals("")) {
                map.put("englishTitle", rec.getEnglishTitle());
            }
            if (rec.getYear() != 0) {
                map.put("year", Integer.toString(rec.getYear()));
            }
            if (!rec.getDescription().equals("")) {
                map.put("description", rec.getDescription());
            }
            if (rec.getGenres() != null) {
                map.put("genre", StringUtils.join(rec.getGenres(), ','));
            }
            if (rec.getCountries() != null) {
                map.put("country", StringUtils.join(rec.getCountries(), ','));
            }
            if (!rec.getComment().equals("")) {
                map.put("comment", rec.getComment());
            }
            if (rec.getLength() != 0) {
                map.put("length", Integer.toString(rec.getLength()));
            }
            if (rec.getRating() != 0) {
                map.put("rating", Integer.toString(rec.getRating()));
            }
            if (rec.getSubtitles() != null) {
                map.put("subtitle", StringUtils.join(rec.getSubtitles(), ','));
            }
            if (rec.getCover() != null) {
                map.put("cover", Base64Coder.encodeLines(rec.getCover()));
            }
            if (rec.getSoundLanguages() != null) {
                map.put("soundLanguage", StringUtils.join(rec.getSoundLanguages(), ','));
            }
            map.put("isSeen", Boolean.toString(rec.isSeen()));
        }
        Condition cond = new Condition(map);
        for (Record film : filmsList) {
            if (cond.isEquals(film)) {
                retVal.add(film);
            }
        }
        return retVal.size() > 0 ? retVal : null;
    }

    /**
     * Записывает таблицу в XML Element
     * @return Строка с таблицей, сериализованной в XML element
     */
    public Element toXmlElement() {
        Element elem = new DOMElement("films", Namespace.get("mediateka"));
        elem.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        elem.addAttribute("xsi:schemaLocation", "mediateka films.xsd");
        elem.addAttribute("autoIndex", Integer.toString(autoIndex));
        for (Iterator<Record> it = filmsList.iterator(); it.hasNext();) {
            Record film = it.next();
            elem.add(film.toXmlElement());
        }
        return elem;
    }

    public Record getRecord(int index) throws IndexOutOfBoundsException {
        return filmsList.get(index);
    }

    public int size() {
        return filmsList.size();
    }

    public Record[] toArray() {
        return filmsList.toArray(new Film[1]);
    }
    
    public boolean IsUnique(Record record) {
        return (find(record) == null);
    }
}
