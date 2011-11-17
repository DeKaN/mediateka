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
        if (record == null) {
            throw new NullPointerException();
        }
        return history.add(record);
    }

    public boolean delete(int id) {
       return history.delete(find(id));
    }

    public boolean edit(int id, Record newData) {
        if (newData == null) {
            throw new NullPointerException();
        }
        return history.update(find(id), newData);
    }

    public Record find(int id) {
        return history.find(new BlackListRecord(id)).getRecord(0);
    }

    public Records find(Record record) {
        if (record == null) {
            throw new NullPointerException();
        }
        return history.find(record);
    
    }
}
