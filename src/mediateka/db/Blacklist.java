package mediateka.db;

import java.util.HashMap;
import java.util.Iterator;
import mediateka.datamanagers.Condition;
import mediateka.datamanagers.Managers;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * Класс, представляющий таблицу черного списка
 * @author DeKaN
 */
public class Blacklist extends Table {

    public Blacklist() {
        tableName = "blackList";
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
                DefaultElement elem = (DefaultElement) it.next();
                recordsList.add(new BlackListRecord(
                        Integer.parseInt(elem.attribute("id").getValue()),
                        (Person) Managers.getInstance().getPersManager().find(Integer.parseInt(elem.node(0).getText())),
                        elem.node(1).getText()));
            }
            return true;
        } catch (Exception ex) {
            throw new LoadException("Черный список не загружен!");
        }
    }

    /**
     * Найти записи в таблице, подходящие под шаблон
     * @param record Запись-шаблон, по которой будет проводиться поиск
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
        if (rec.getID() > 0) {
            map.put("id", Integer.toString(rec.getID()));
        } else {
            if (rec.getPerson() != null) {
                map.put("personID", Integer.toString(rec.getPerson().getID()));
            }
            if (!rec.getComment().equals("")) {
                map.put("comment", rec.getComment());
            }
        }
        Condition cond = new Condition(map);
        for (Record blackListRecord : recordsList) {
            if (cond.isEquals(blackListRecord)) {
                retVal.add(blackListRecord);
            }
        }
        return retVal.size() > 0 ? retVal : null;
    }

    protected Record createRecord(int id) {
        return new BlackListRecord(id);
    }
}