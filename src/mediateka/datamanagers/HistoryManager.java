package mediateka.datamanagers;

import mediateka.db.*;

/**
 * Менеджер истории
 * @author Il'ya
 */
public class HistoryManager extends Manager {

    HistoryManager(String fileName) throws LoadException {
        table = new History();
        table.load(fileName);
    }
}
