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
        boolean Add(Record record);

        /**
         * 
         * @param id
         * @return  
         */
        boolean Delete(int id);

        /**
         * 
         * @param id
         * @param newData
         * @return  
         */
        Records Edit(int id, Record newData);

        /**
         * 
         * @param id
         * @return  
         */
        Records Find(int id);

        /**
         * 
         * @param record
         * @return  
         */
        Records Find(Record record);
    }