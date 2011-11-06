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
         */
        Records Execute(int id);

        /**
         * 
         * @param record
         */
        Records Execute(Record record);

        Records ToString();

        /**
         * 
         * @param id
         * @param record
         */
        Records Execute(int id, Record record);
    }
