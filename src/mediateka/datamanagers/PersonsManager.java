package mediateka.datamanagers;

import mediateka.db.*;

/**
 * Менеджер персональных данных
 * @author DeKaN
 */
public class PersonsManager implements RecordsManager {

    private Persons persons = null;

    PersonsManager(String fileName) throws Exception {
        persons = new Persons();
        if (!persons.load(fileName)) {
            throw new Exception("Персональные данные не загружены!");
        }
    }

    public boolean add(Record record) {
        if (record == null) {
            throw new NullPointerException();
        }
        return persons.add(record);
    }

    public boolean delete(int id) {
        return persons.delete(find(id));
    }

    public boolean edit(int id, Record newData) {
        if (newData == null) {
            throw new NullPointerException();
        }
        return persons.update(find(id), newData);
    }

    public Record find(int id) {
        return persons.find(new BlackListRecord(id)).getRecord(0);
    }

    public Records find(Record record) {
        if (record == null) {
            throw new NullPointerException();
        }
        return persons.find(record);
    }
}
