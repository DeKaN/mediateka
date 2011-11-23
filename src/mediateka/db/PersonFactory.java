/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.db;

/**
 * Фабрика для создания персональных данных
 * @author DeKaN
 */
public class PersonFactory implements RecordFactory {

    public Record createInstance(int id) {
        return new Person(id);
    }
}
