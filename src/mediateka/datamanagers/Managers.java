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
    public static Managers getInstance(String blListFile, String discFile,
            String filmFile, String histFile, String persFile) throws Exception {
        if (instance == null) {
            instance = new Managers(blListFile, discFile, filmFile, histFile, persFile);
        }
        return instance;
    }

    private Managers(String blListFile, String discFile, String filmFile,
            String histFile, String persFile) throws Exception {
        persManager = new PersonsManager(persFile);
        filmsManager = new FilmsManager(filmFile);
        blListManager = new BlackListManager(blListFile);
        discsManager = new DiscsManager(discFile);
        histManager = new HistoryManager(histFile);
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
