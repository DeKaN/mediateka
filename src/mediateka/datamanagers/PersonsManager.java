package mediateka.datamanagers;

import mediateka.db.*;

/**
 * Менеджер персональных данных
 * @author DeKaN
 */
public class PersonsManager extends Manager {

    PersonsManager(String fileName) throws LoadException {
        table = new Persons();
        table.load(fileName);
    }
}
