/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.commands;

import mediateka.db.Record;
import mediateka.db.Records;

/**
 *
 * @author DeKaN
 */
public interface Command {

        /**
         * 
     * @param id
     * @return  
         */
        Records Execute(int id);

        /**
         * 
         * @param record
         * @return  
         */
        Records Execute(Record record);

        /**
         * 
         * @return
         */
        Records ToString();

        /**
         * 
         * @param id
         * @param record
         * @return  
         */
        Records Execute(int id, Record record);
    }
