
import java.util.List;

public class Persons implements Records {

	private int autoIndex;
	private List<Person> personsList;

	/**
	 * 
	 * @param surname
	 * @param name
	 * @param secondName
	 * @param phone
	 */
	public Persons Find(String surname, String name, String secondName, String phone) {
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