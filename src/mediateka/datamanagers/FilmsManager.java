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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean delete(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean edit(int id, Record newData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Record find(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Records find(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
