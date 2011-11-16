/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.datamanagers;

/**
 * Класс, хранящий и предоставляющий доступ к менеджерам
 * @author DeKaN
 */
public class Managers {

    BlackListManager blListManager = null;
    DiscsManager discsManager = null;
    FilmsManager filmsManager = null;
    HistoryManager histManager = null;
    PersonsManager persManager = null;
    static Managers instance = null;

    /**
     * Возвращает объект класса
     * @param blListFile XML файл, с таблицей BlackList
     * @param discFile XML файл, с таблицей Discs
     * @param filmFile XML файл, с таблицей Films
     * @param histFile XML файл, с таблицей History
     * @param persFile XML файл, с таблицей Persons
     * @throws Exception Если хотя бы одна из таблиц не загрузилась
     */
    public static Managers getInstance(String blListFile, String discFile, String filmFile, String histFile, String persFile) throws Exception {
        if (instance == null) {
            instance = new Managers(blListFile, discFile, filmFile, histFile, persFile);
        }
        return instance;
    }
    
    private Managers(String blListFile, String discFile, String filmFile, String histFile, String persFile) throws Exception {
        StringBuilder errors = new StringBuilder();
        try {
            blListManager = new BlackListManager(blListFile);
        } catch (Exception exc) {
            errors.append(exc.getMessage()).append("\n");
        }
        try {
            discsManager = new DiscsManager(discFile);
        } catch (Exception exc) {
            errors.append(exc.getMessage()).append("\n");
        }
        try {
            filmsManager = new FilmsManager(filmFile);
        } catch (Exception exc) {
            errors.append(exc.getMessage()).append("\n");
        }
        try {
            histManager = new HistoryManager(histFile);
        } catch (Exception exc) {
            errors.append(exc.getMessage()).append("\n");
        }
        try {
            persManager = new PersonsManager(persFile);
        } catch (Exception exc) {
            errors.append(exc.getMessage()).append("\n");
        }
        if (errors.length() > 0) {
            throw new Exception(errors.toString());
        }

    }

    public BlackListManager getBlListManager() {
        return blListManager;
    }

    public DiscsManager getDiscsManager() {
        return discsManager;
    }

    public FilmsManager getFilmsManager() {
        return filmsManager;
    }

    public HistoryManager getHistManager() {
        return histManager;
    }

    public PersonsManager getPersManager() {
        return persManager;
    }
}
