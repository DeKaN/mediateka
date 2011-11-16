/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.datamanagers;

import mediateka.db.Record;
import mediateka.db.Records;

/**
 *
 * @author DeKaN
 */
public interface RecordsManager {

    /**
     * 
     * @param record
     * @return  
     */
    boolean add(Record record);

    /**
     * 
     * @param id
     * @return  
     */
    boolean delete(int id);

    /**
     * 
     * @param id
     * @param newData
     * @return  
     */
    boolean edit(int id, Record newData);

    /**
     * 
     * @param id
     * @return  
     */
    Record find(int id);

    /**
     * 
     * @param record
     * @return  
     */
    Records find(Record record);
}