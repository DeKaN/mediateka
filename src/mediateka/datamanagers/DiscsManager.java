package mediateka.datamanagers;

import mediateka.db.*;

/**
 * Менеджер дисков
 * @author DeKaN
 */
public class DiscsManager implements RecordsManager {

    private Discs discs = null;

    DiscsManager(String fileName) throws Exception {
        discs = new Discs();
        if (!discs.load(fileName)) {
            throw new Exception("Диски не загружены!");
        }
    }

    public boolean add(Record record) {
        if (record == null) {
            throw new NullPointerException();
        }
        return discs.add(record);
    }

    public boolean delete(int id) {
        return discs.delete(find(id));
    }

    public boolean edit(int id, Record newData) {
        if (newData == null) {
            throw new NullPointerException();
        }
        return discs.update(find(id), newData);
    }

    public Record find(int id) {
        return discs.find(new Disc(id)).getRecord(0);
    }

    public Records find(Record record) {
        if (record == null) {
            throw new NullPointerException();
        }
        return discs.find(record);
    }
}
