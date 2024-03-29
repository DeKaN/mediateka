package mediateka.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import mediateka.datamanagers.Condition;
import mediateka.datamanagers.Manager;
import mediateka.datamanagers.Managers;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
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
                    recordsList.add(new Film((DefaultElement) it.next()));
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
            map.put("id", Integer.toString(rec.getID()));
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
                map.put("genres/genre", StringUtils.join(rec.getGenres(), '\uFFFC'));
            }
            if (rec.getCountries() != null) {
                map.put("countries/country", StringUtils.join(rec.getCountries(), '\uFFFC'));
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
                map.put("subtitles/subtitle", StringUtils.join(rec.getSubtitles(), '\uFFFC'));
            }
            if (rec.getSoundLanguages() != null) {
                map.put("soundLanguages/soundLanguage", StringUtils.join(rec.getSoundLanguages(), '\uFFFC'));
            }
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
            Records recs = discManager.find(new Disc(Disc.Format.любой, f));
            del(recs, record);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    private boolean del(Records recs, Record record) {
        if (recs == null) {
            return false;
        }
        try {
            Manager discManager = Managers.getInstance().getDiscsManager();
            for (int i = 0; i < recs.size(); i++) {
                Disc d = (Disc) recs.getRecord(i);
                Films f = d.getFilms();
                f.delete(record);
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

    public ArrayList<Integer> getListOfFilmIDs() {
        ArrayList<Integer> retVal = new ArrayList<Integer>();
        for (int i = 0; i < recordsList.size(); i++) {
            retVal.add(recordsList.get(i).getID());
        }
        return retVal;
    }
}
