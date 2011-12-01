package mediateka.datamanagers;

import java.io.FileInputStream;
import java.security.MessageDigest;
import mediateka.db.Blacklist;
import mediateka.db.Discs;
import mediateka.db.Films;
import mediateka.db.History;
import mediateka.db.LoadException;
import mediateka.db.Persons;

/**
 * Класс, хранящий и предоставляющий доступ к менеджерам
 * @author DeKaN
 */
public class Managers {

    private class ManagerInfo {

        String xmlFile = "",
                xsdFile = "",
                xsdHash = "";

        ManagerInfo(String fileXML, String hashXSD) {
            xmlFile = fileXML;
            xsdFile = fileXML.replace(".xml", ".xsd");
            xsdHash = hashXSD;
        }
    }
    Manager blListManager = null, discsManager = null, filmsManager = null, histManager = null, persManager = null;
    private static final String dir = ".\\XML\\",//TODO after debug change to %APPDATA%\mediateka
            blFile = dir + "blacklist.xml",
            dFile = dir + "discs.xml",
            fFile = dir + "films.xml",
            hFile = dir + "history.xml",
            pFile = dir + "persons.xml";
    ManagerInfo blInfo = new ManagerInfo(blFile, "1af05a1e6032f60e51dcb81661de3ddb"),
            dInfo = new ManagerInfo(dFile, "b22bccb075b5a73231007d3c6adbe20b"),
            fInfo = new ManagerInfo(fFile, "b831cbb576a79477f1ac358ff93f9dca"),
            hInfo = new ManagerInfo(hFile, "e0723e6d32d83f3b293d03c262432e48"),
            pInfo = new ManagerInfo(pFile, "1aa533d2d9c64cb0c8458a956e647316");
    private static Managers instance = null;

    /**
     * Возвращает объект класса
     */
    public static Managers getInstance() {
        if (instance == null) {
            instance = new Managers();
        }
        return instance;
    }

    private Managers() {
    }

    public Manager getBlListManager() throws Exception {
        if (blListManager == null) {
            getPersManager();
            if (!validateSchema(blInfo)) {
                throw new LoadException("Схема черного списка повреждена!");
            }
            blListManager = new Manager(new Blacklist(), blFile);
        }
        return blListManager;
    }

    public Manager getDiscsManager() throws Exception {
        if (discsManager == null) {
            getFilmsManager();
            if (!validateSchema(dInfo)) {
                throw new LoadException("Схема дисков повреждена!");
            }
            discsManager = new Manager(new Discs(), dFile);
        }
        return discsManager;
    }

    public Manager getFilmsManager() throws Exception {
        if (filmsManager == null) {
            if (!validateSchema(fInfo)) {
                throw new LoadException("Схема фильмов повреждена!");
            }
            filmsManager = new Manager(new Films(), fFile);
        }
        return filmsManager;
    }

    public Manager getHistManager() throws Exception {
        if (histManager == null) {
            getDiscsManager();
            getPersManager();
            if (!validateSchema(hInfo)) {
                throw new LoadException("Схема истории повреждена!");
            }
            histManager = new Manager(new History(), hFile);
        }
        return histManager;
    }

    public Manager getPersManager() throws Exception {
        if (persManager == null) {
            if (!validateSchema(pInfo)) {
                throw new LoadException("Схема персональных данных повреждена!");
            }
            persManager = new Manager(new Persons(), pFile);
        }
        return persManager;
    }

    private boolean validateSchema(ManagerInfo info) {
        try {
            MessageDigest dig = MessageDigest.getInstance("MD5");
            FileInputStream fs = new FileInputStream(info.xsdFile);

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
            return sb.toString().equals(info.xsdHash);
        } catch (Exception ex) {
            return false;
        }
    }
}
