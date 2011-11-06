package mediateka.db;


import mediateka.db.Records;
import mediateka.db.Record;
import java.util.List;

public class Films implements Records {

	private int autoIndex;
	private List<Film> filmsList;

	/**
	 * 
	 * @param russianName
	 * @param englishName
	 * @param yearOfOutput
	 * @param desc
	 * @param filmGenres
	 * @param filmCountries
	 * @param comm
	 * @param len
	 * @param rate
	 * @param filmSubtitles
	 * @param filmCover
	 * @param soundLang
	 * @param seen
	 */
	public Films Find(String russianName, String englishName, int yearOfOutput, String desc, String[] filmGenres, String[] filmCountries, String comm, int len, int rate, String[] filmSubtitles, byte[] filmCover, String soundLang, boolean seen) {
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