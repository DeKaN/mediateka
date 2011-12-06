package mediateka.db;

import java.util.HashMap;
import java.util.Iterator;
import mediateka.datamanagers.Condition;
import mediateka.datamanagers.Manager;
import mediateka.datamanagers.Managers;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * Класс, представляющий таблицу с персональными данными
 * @author Alexandr
 */
public class Persons extends Table {

    public Persons() {
        tableName = "persons";
    }

    /**
     * Загрузка из XML
     * @param fileName Имя файла
     * @return true, если загрузка завершилась успешно, иначе false
     */
    public boolean load(String fileName) throws LoadException {
        try {
            DefaultElement root = super.getRootElement(fileName);
            for (Iterator<Element> it = root.elements().iterator(); it.hasNext();) {
                DefaultElement elem = (DefaultElement) it.next();
                recordsList.add(new Person(
                        Integer.parseInt(elem.attribute("id").getValue()),
                        elem.node(1).getText(),
                        elem.node(0).getText(),
                        elem.node(2).getText(),
                        elem.node(3).getText(),
                        elem.node(4).getText()));
            }
            return true;
        } catch (Exception ex) {
            throw new LoadException("Персональные данные не загружены!");
        }
    }

    /**
     * Найти записи в таблице, подходящие под шаблон
     * @param record Запись-шаблон, по которой будет проводиться поиск
     * @return Виртуальная таблица с записями, подходящими под шаблон
     */
    public Records find(Record record) {
        Persons retVal = new Persons();
        HashMap<String, String> map = new HashMap<String, String>();
        Person pers;
        try {
            pers = (Person) record;
        } catch (Exception e) {
            return null;
        }
        if (pers.getID() > 0) {
            map.put("id", Integer.toString(pers.getID()));
        } else {
            if (!pers.getFirstName().equals("")) {
                map.put("firstName", pers.getFirstName());
            }
            if (!pers.getLastName().equals("")) {
                map.put("lastName", pers.getLastName());
            }
            if (!pers.getSecondName().equals("")) {
                map.put("secondName", pers.getSecondName());
            }
            if (!pers.getPhone().equals("")) {
                map.put("phoneNumber", pers.getPhone());
            }
            if (!pers.getComment().equals("")) {
                map.put("comment", pers.getComment());
            }
        }
        Condition cond = new Condition(map);
        for (Record person : recordsList) {
            if (cond.isEquals(person)) {
                retVal.add(person);
            }
        }
        return retVal.size() > 0 ? retVal : null;
    }

    protected Record createRecord(int id) {
        return new Person(id);
    }

    @Override
    public boolean delete(Record record) {
        if (!super.delete(record)) {
            return false;
        }
        try {
            Manager blManager = Managers.getInstance().getBlListManager();
            Records recs = blManager.find(new BlackListRecord((Person) record));
            if (recs != null) {
                for (int i = 0; i < recs.size(); i++) {
                    blManager.delete(recs.getRecord(i).getID());
                }
            }
            Manager histManager = Managers.getInstance().getHistManager();
            recs = histManager.find(new HistoryRecord(null, (Person) record, null, null));
            if (recs != null) {
                for (int i = 0; i < recs.size(); i++) {
                    histManager.delete(recs.getRecord(i).getID());
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}