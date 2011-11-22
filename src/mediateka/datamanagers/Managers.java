package mediateka.datamanagers;

import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            if (!ValidateSchema(blFile.replace(".xml", ".xsd"), "0123456789abcdef0123456789abcdef")) {
                throw new Exception("Схема черного списка повреждена!");
            }
            blListManager = new BlackListManager(blFile);
        }
        return blListManager;
    }

    public DiscsManager getDiscsManager() throws Exception {
        if (discsManager == null) {
            getFilmsManager();
            if (!ValidateSchema(dFile.replace(".xml", ".xsd"), "0123456789abcdef0123456789abcdef")) {
                throw new Exception("Схема дисков повреждена!");
            }
            discsManager = new DiscsManager(dFile);
        }
        return discsManager;
    }

    public FilmsManager getFilmsManager() throws Exception {
        if (filmsManager == null) {
            if (!ValidateSchema(fFile.replace(".xml", ".xsd"), "0123456789abcdef0123456789abcdef")) {
                throw new Exception("Схема фильмов повреждена!");
            }
            filmsManager = new FilmsManager(fFile);
        }
        return filmsManager;
    }

    public HistoryManager getHistManager() throws Exception {
        if (histManager == null) {
            getDiscsManager();
            getPersManager();
            if (!ValidateSchema(hFile.replace(".xml", ".xsd"), "0123456789abcdef0123456789abcdef")) {
                throw new Exception("Схема истории повреждена!");
            }
            histManager = new HistoryManager(hFile);
        }
        return histManager;
    }

    public PersonsManager getPersManager() throws Exception {
        if (persManager == null) {
            if (!ValidateSchema(pFile.replace(".xml", ".xsd"), "0123456789abcdef0123456789abcdef")) {
                throw new Exception("Схема персональных данных повреждена!");
            }
            persManager = new PersonsManager(pFile);
        }
        return persManager;
    }

    private boolean ValidateSchema(String fileName, String md5) {
        try {
            MessageDigest dig = MessageDigest.getInstance("MD5");
            FileInputStream fs = new FileInputStream(fileName);

            byte[] data = new byte[1024];
            int readed = 0;
            while ((readed = fs.read(data)) != -1) {
                dig.update(data, 0, readed);
            }
            byte[] digBytes = dig.digest();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digBytes.length; i++) {
                sb.append(Integer.toString((digBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString().equals(md5);
        } catch (Exception ex) {
            return false;
        }
    }
}
