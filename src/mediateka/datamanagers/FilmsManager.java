/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.datamanagers;

import mediateka.db.*;

/**
 *
 * @author DeKaN
 */
public class FilmsManager implements RecordsManager {

    private Films films = null;

    FilmsManager(String fileName) throws Exception {
        films = new Films();
        if (!films.load(fileName)) {
            throw new Exception("Фильмы не загружены!");
        }
    }

    public boolean add(Record record) {
        if (record == null) {
            throw new NullPointerException();
        }
        return films.add(record);
    }

    public boolean delete(int id) {
        return films.delete(find(id));
    }

    public boolean edit(int id, Record newData) {
        if (newData == null) {
            throw new NullPointerException();
        }
        return films.update(find(id), newData);
    }

    public Record find(int id) {
        return films.find(new BlackListRecord(id)).getRecord(0);
    }

    public Records find(Record record) {
        if (record == null) {
            throw new NullPointerException();
        }
        return films.find(record);
    }
}
