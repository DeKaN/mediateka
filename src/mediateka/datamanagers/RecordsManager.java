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
         */
        boolean Add(Record record);

        /**
         * 
         * @param id
         */
        boolean Delete(int id);

        /**
         * 
         * @param id
         * @param newData
         */
        Records Edit(int id, Record newData);

        /**
         * 
         * @param id
         */
        Records Find(int id);

        /**
         * 
         * @param record
         */
        Records Find(Record record);
    }