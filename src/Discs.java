
import java.util.List;

public class Discs implements Records {

	private int autoIndex;
	private List<Disc> discsList;

	/**
	 * 
	 * @param discOwner
	 * @param discFormat
	 * @param region
	 * @param presented
	 */
	public Discs Find(int discOwner, String discFormat, int region, boolean presented) {
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