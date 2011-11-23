/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.db;

/**
 * Фабрика для создания записей 
 * @author DeKaN
 */
public class BlackListRecordFactory implements RecordFactory {

    public Record createInstance(int id) {
        return new BlackListRecord(id);
    }
}
