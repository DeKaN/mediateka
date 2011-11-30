package mediateka.db;

import java.util.HashMap;
import java.util.Iterator;
import mediateka.datamanagers.Condition;
import mediateka.datamanagers.Managers;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultElement;

/**
 * Класс, представляющий коллекцию дисков
 * @author Alexandr
 */
public class Discs extends Table {

    public Discs() {
        tableName = "discs";
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
                    DefaultElement elem = (DefaultElement) it.next(), elem2 = (DefaultElement) elem.element("films");
                    Films films = new Films();
                    for (Iterator<Node> it1 = elem2.iterator(); it1.hasNext();) {
                        Node node = it1.next();
                        films.add(Managers.getInstance().getFilmsManager().find(Integer.parseInt(node.getText())));
                    }
                    recordsList.add(new Disc(
                            Integer.parseInt(elem.attribute("discID").getValue()),
                            films,
                            Integer.parseInt(elem.node(0).getText()),
                            Disc.Format.valueOf(elem.node(1).getText()),
                            Integer.parseInt(elem.node(2).getText()),
                            elem.node(3).getText().equals("true")));
                } catch (Exception exc) {
                }
            }
            return true;
        } catch (Exception ex) {
            throw new LoadException("Диски не загружены!");
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
        if (disc.getID() > 0) {
            map.put("discID", Integer.toString(disc.getID()));
        } else {
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
            map.put("isPresent", Boolean.toString(disc.isPresented()));
        }
        Condition cond = new Condition(map);
        for (Record d : recordsList) {
            if (cond.isEquals(d)) {
                retVal.add(d);
            }
        }
        return retVal.size() > 0 ? retVal : null;
    }
    
    protected Record createRecord(int id) {
        return new Disc(id);
    }
}