/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.db;

/**
 * Фабрика для создания дисков
 * @author DeKaN
 */
public class DiscFactory implements RecordFactory {

    public Record createInstance(int id) {
        return new Disc(id);
    }
}
