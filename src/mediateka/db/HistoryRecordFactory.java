/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.db;

/**
 * Фабрика для создания записей истории
 * @author DeKaN
 */
public class HistoryRecordFactory implements RecordFactory {

    public Record createInstance(int id) {
        return new HistoryRecord(id);
    }
}
