/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.db;

/**
 * Фабрика для создания фильмов
 * @author DeKaN
 */
public class FilmFactory implements RecordFactory {

    public Record createInstance(int id) {
        return new Film(id);
    }
}
