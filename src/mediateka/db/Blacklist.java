package mediateka.db;


import mediateka.db.Records;
import mediateka.db.Record;
import mediateka.db.Person;
import java.util.List;

/**
 * 
 * @author Alexandr
 */
public class Blacklist implements Records {

	private int autoIndex;
	private List<BlackListRecord> blackListRecs;

	/**
	 * 
	 * @param person
         * @param comment
         * @return  
	 */
	public Blacklist Find(Person person, String comment) {
		throw new UnsupportedOperationException();
	}

    public void Add(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Delete(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Update(Record oldRecord, Record newRecord) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     */
    public void Save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     */
    public void Load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Import(Records records) {
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
    public String ToXmlElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}