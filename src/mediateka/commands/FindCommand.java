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
public interface FindCommand extends Command {
    
    /**
     * Выполнить команду поиска
     * @param id ID записи, передаваемое команде
     * @return Запись, с данным ID
     */
    public Record Execute(int id);
    
    /**
     * Выполнить команду поиска
     * @param record Запись-шаблон, передаваемая команде
     * @return Записи, которые подходят под шаблон
     */
    public Records Execute(Record record);
    
}
