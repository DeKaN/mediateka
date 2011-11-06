package mediateka.db;


import mediateka.db.Record;

/**
 * 
 * @author Alexandr
 */
public class Disc implements Record {

	private int discID;
	private Films films;
	private int ownerID = 0;
	private String format;
	private int regionCode = 0;
	private boolean isPresented = true;

        /**
         * 
         * @return
         */
        public Films getFilms() {
		return this.films;
	}

        /**
         * 
         * @param films
         */
        public void setFilms(Films films) {
		this.films = films;
	}

        /**
         * 
         * @return
         */
        public int getOwnerID() {
		return this.ownerID;
	}

        /**
         * 
         * @return
         */
        public String getFormat() {
		return this.format;
	}

        /**
         * 
         * @param format
         */
        public void setFormat(String format) {
		this.format = format;
	}

        /**
         * 
         * @return
         */
        public int getRegionCode() {
		return this.regionCode;
	}

        /**
         * 
         * @param regionCode
         */
        public void setRegionCode(int regionCode) {
		this.regionCode = regionCode;
	}

        /**
         * 
         * @return
         */
        public boolean isIsPresented() {
		return this.isPresented;
	}

        /**
         * 
         * @param isPresented
         */
        public void setIsPresented(boolean isPresented) {
		this.isPresented = isPresented;
	}

	/**
	 * 
         * @param discFormat
         * @return  
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
         * @return  
	 */
	public Disc Disc(int owner, String discFormat, int discRegion, boolean presented) {
		throw new UnsupportedOperationException();
	}

        /**
         * 
         * @return
         */
        public String ToXmlElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}