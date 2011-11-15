/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.commands;

import mediateka.db.Record;

/**
 *
 * @author DeKaN
 */
public interface AddCommand extends Command {
    
    /**
     * Выполнить команду добавления
     * @param record Запись, передаваемая команде
     * @return true, если выполнение завершилось успешно, иначе false
     */
    public boolean Execute(Record record);
    
}
