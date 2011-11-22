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
    private static String blFile = "", dFile = "", fFile = "", hFile = "", pFile = "";
    private static Managers instance = null;

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
            String filmFile, String histFile, String persFile) {
        if (instance == null) {
            blFile = blListFile;
            dFile = discFile;
            fFile = filmFile;
            hFile = histFile;
            pFile = persFile;
            instance = new Managers();
        }
        return instance;
    }

    private Managers() {
    }

    public BlackListManager getBlListManager() throws Exception {
        if (blListManager == null) {
            getPersManager();
            blListManager = new BlackListManager(blFile);
        }
        return blListManager;
    }

    public DiscsManager getDiscsManager() throws Exception {
        if (discsManager == null) {
            getFilmsManager();
            discsManager = new DiscsManager(dFile);
        }
        return discsManager;
    }

    public FilmsManager getFilmsManager() throws Exception {
        if (filmsManager == null) {
            filmsManager = new FilmsManager(fFile);
        }
        return filmsManager;
    }

    public HistoryManager getHistManager() throws Exception {
        if (histManager == null) {
            getDiscsManager();
            getPersManager();
            histManager = new HistoryManager(hFile);
        }
        return histManager;
    }

    public PersonsManager getPersManager() throws Exception {
        if (persManager == null) {
            persManager = new PersonsManager(pFile);
        }
        return persManager;
    }
}
