package mediateka.db;

import java.util.HashMap;
import java.util.Iterator;
import mediateka.datamanagers.Condition;
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
                        Integer.parseInt(elem.attribute("personID").getValue()),
                        elem.node(0).getText(),
                        elem.node(1).getText(),
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
            map.put("personID", Integer.toString(pers.getID()));
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
            if (pers.getComment() != null) {
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
    
    @Override
    protected Record createRecord(int id) {
        return new Person(id);
    }
}