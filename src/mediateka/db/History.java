package mediateka.db;


import java.util.Date;
import java.util.List;
import org.dom4j.Element;

/**
 * 
 * @author Alexandr
 */
public class History implements Records {

	private int autoIndex;
	private List<HistoryRecord> historyList;

    public boolean add(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean delete(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean update(Record oldRecord, Record newRecord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     */
    public boolean save(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     */
    public boolean load(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean Import(Records records) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public Records Export() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Records find(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public Element toXmlElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Record getRecord(int index) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean validate(String fileName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}