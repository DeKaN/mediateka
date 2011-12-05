package mediateka.db;

import biz.source_code.base64Coder.Base64Coder;
import java.util.HashMap;
import java.util.Iterator;
import mediateka.datamanagers.Condition;
import mediateka.datamanagers.Manager;
import mediateka.datamanagers.Managers;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultElement;

/**
 * Класс, представляющий коллекцию фильмов
 * @author DeKaN
 */
public class Films extends Table {

    public Films() {
        tableName = "films";
    }

    /**
     * Загрузка из XML с валидацией
     * @param fileName Имя файла, из которого будет загружен XML
     * @return true, если загрузка завершилась успешно, иначе false
     */
    public boolean load(String fileName) throws LoadException {
        try {
            DefaultElement root = super.getRootElement(fileName);
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
                    recordsList.add(new Film(Integer.parseInt(elem.attribute("filmID").getValue()),
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
            throw new LoadException("Фильмы не загружены!");
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
        for (Record film : recordsList) {
            if (cond.isEquals(film)) {
                retVal.add(film);
            }
        }
        return retVal.size() > 0 ? retVal : null;
    }

    protected Record createRecord(int id) {
        return new Film(id);
    }

    @Override
    public boolean delete(Record record) {
        if (!super.delete(record)) {
            return false;
        }
        try {
            Manager discManager = Managers.getInstance().getDiscsManager();
            Films f = new Films();
            f.add(record);
            Records recs = discManager.find(new Disc(Disc.Format.CD, f));
            for (int i = 0; i < recs.size(); i++) {
                Disc d = (Disc) recs.getRecord(i);
                f = d.getFilms();
                if (!f.delete(record)) {
                    return false;
                }
                if (f.size() > 0) {
                    d.setFilms(f);
                    discManager.edit(d);
                } else {
                    discManager.delete(d.getID());
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
