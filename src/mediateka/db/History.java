package mediateka.db;


import java.util.Date;
import java.util.List;

public class History implements Records {

	private int autoIndex;
	private List<HistoryRecord> historyList;

	/**
	 * 
	 * @param disc
	 * @param person
	 * @param give
	 * @param promise
	 * @param returned
	 * @param comment
	 */
	public History Find(Disc disc, Person person, Date give, Date promise, Date returned, String comment) {
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

    public void Save() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Load() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void Import(Records records) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Records Export() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Records Find(Record record) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String ToXmlElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}