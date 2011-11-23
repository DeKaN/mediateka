package mediateka.datamanagers;

import mediateka.db.*;

/**
 * Менеджер фильмов
 * @author Il'ya
 */
public class FilmsManager extends Manager {

    FilmsManager(String fileName) throws LoadException {
        table = new Films();
        table.load(fileName);
    }
}
