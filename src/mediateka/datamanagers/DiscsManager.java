package mediateka.datamanagers;

import mediateka.db.*;

/**
 * Менеджер дисков
 * @author DeKaN
 */
public class DiscsManager extends Manager {

    DiscsManager(String fileName) throws LoadException {
        table = new Discs();
        table.load(fileName);
    }
}
