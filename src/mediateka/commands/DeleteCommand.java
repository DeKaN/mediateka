/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.commands;

/**
 *
 * @author DeKaN
 */
public interface DeleteCommand extends Command {
    
    /**
     * Выполнить команду
     * @param id ID записи, передаваемое команде
     * @return true, если выполнение завершилось успешно, иначе false
     */
    public boolean Execute(int id);
    
}
