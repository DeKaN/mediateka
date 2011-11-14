package mediateka.db;


import java.util.List;
import org.dom4j.Element;

/**
 * 
 * @author Alexandr
 */
public class Films implements Records {

	private int autoIndex;
	private List<Film> filmsList;

    public boolean Add(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean Delete(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean Update(Record oldRecord, Record newRecord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     */
    public boolean Save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     */
    public boolean Load() {
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

    public Records Find(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public Element ToXmlElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Record getRecord(int index) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int size() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean Validate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}