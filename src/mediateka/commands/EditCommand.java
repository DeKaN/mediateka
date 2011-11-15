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
public interface EditCommand extends Command {
    
    /**
     * Выполнить команду редактирования
     * @param id ID записи, передаваемое команде
     * @param record Запись, передаваемая команде
     * @return true, если выполнение завершилось успешно, иначе false
     */
    public boolean Execute(int id, Record record);
    
}
