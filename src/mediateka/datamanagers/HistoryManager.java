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
public class HistoryManager implements RecordsManager {

    private History history = null;

    HistoryManager(String fileName) throws Exception {
        history = new History();
        if (!history.load(fileName)) {
            throw new Exception("История не загружена!");
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
