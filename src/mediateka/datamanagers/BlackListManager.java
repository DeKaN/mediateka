package mediateka.datamanagers;

import mediateka.db.*;

/**
 * Менеджер черного списка
 * @author DeKaN
 */
public class BlackListManager extends Manager {

    BlackListManager(String fileName) throws LoadException {
        table = new Blacklist();
        table.load(fileName);
    }
}