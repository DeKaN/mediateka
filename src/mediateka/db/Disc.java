package mediateka.db;


import mediateka.db.Record;

public class Disc implements Record {

	private int discID;
	private Films films;
	private int ownerID = 0;
	private String format;
	private int regionCode = 0;
	private boolean isPresented = true;

	public Films getFilms() {
		return this.films;
	}

	public void setFilms(Films films) {
		this.films = films;
	}

	public int getOwnerID() {
		return this.ownerID;
	}

	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}

	public boolean isIsPresented() {
		return this.isPresented;
	}

	public void setIsPresented(boolean isPresented) {
		this.isPresented = isPresented;
	}

	/**
	 * 
	 * @param discFormat
	 */
	public Disc Disc(String discFormat) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param owner
	 * @param discFormat
	 * @param discRegion
	 * @param presented
	 */
	public Disc Disc(int owner, String discFormat, int discRegion, boolean presented) {
		throw new UnsupportedOperationException();
	}

    public String ToXmlElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}