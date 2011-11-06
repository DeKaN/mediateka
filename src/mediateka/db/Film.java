package mediateka.db;


import mediateka.db.Record;

/**
 * 
 * @author Alexandr
 */
public final class Film implements Record {

	private int filmID;
	private String russianTitle;
	private String englishTitle = "";
	private int year = 0;
	private String description = "";
	private String[] genres = null;
	private String[] countries = null;
	private String comment = "";
	private int length = 0;
	private int rating = 0;
	private String[] subtitles = null;
	private byte[] cover = null;
	private String[] soundLanguages = null;
	private boolean isSeen = false;

        /**
         * 
         * @return
         */
        public String getRussianTitle() {
		return this.russianTitle;
	}

        /**
         * 
         * @param russianTitle
         */
        public void setRussianTitle(String russianTitle) {
		this.russianTitle = russianTitle;
	}

        /**
         * 
         * @return
         */
        public String getEnglishTitle() {
		return this.englishTitle;
	}

        /**
         * 
         * @param englishTitle
         */
        public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

        /**
         * 
         * @return
         */
        public int getYear() {
		return this.year;
	}

        /**
         * 
         * @param year
         */
        public void setYear(int year) {
		this.year = year;
	}

        /**
         * 
         * @return
         */
        public String getDescription() {
		return this.description;
	}

        /**
         * 
         * @param description
         */
        public void setDescription(String description) {
		this.description = description;
	}

        /**
         * 
         * @return
         */
        public String[] getGenres() {
		return this.genres;
	}

        /**
         * 
         * @param genres
         */
        public void setGenres(String[] genres) {
		this.genres = genres;
	}

        /**
         * 
         * @return
         */
        public String[] getCountries() {
		return this.countries;
	}

        /**
         * 
         * @param countries
         */
        public void setCountries(String[] countries) {
		this.countries = countries;
	}

        /**
         * 
         * @return
         */
        public String getComment() {
		return this.comment;
	}

        /**
         * 
         * @param comment
         */
        public void setComment(String comment) {
		this.comment = comment;
	}

        /**
         * 
         * @return
         */
        public int getLength() {
		return this.length;
	}

        /**
         * 
         * @param length
         */
        public void setLength(int length) {
		this.length = length;
	}

        /**
         * 
         * @return
         */
        public int getRating() {
		return this.rating;
	}

        /**
         * 
         * @param rating
         */
        public void setRating(int rating) {
		this.rating = rating;
	}

        /**
         * 
         * @return
         */
        public String[] getSubtitles() {
		return this.subtitles;
	}

        /**
         * 
         * @param subtitles
         */
        public void setSubtitles(String[] subtitles) {
		this.subtitles = subtitles;
	}

        /**
         * 
         * @return
         */
        public byte[] getCover() {
		return this.cover;
	}

        /**
         * 
         * @param cover
         */
        public void setCover(byte[] cover) {
		this.cover = cover;
	}

        /**
         * 
         * @return
         */
        public String[] getSoundLanguages() {
		return this.soundLanguages;
	}

        /**
         * 
         * @param soundLanguages
         */
        public void setSoundLanguages(String[] soundLanguages) {
		this.soundLanguages = soundLanguages;
	}

        /**
         * 
         * @return
         */
        public boolean isIsSeen() {
		return this.isSeen;
	}

        /**
         * 
         * @param isSeen
         */
        public void setIsSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}

	/**
	 * 
         * @param russianName
         * @return  
	 */
	public Film Film(String russianName) {
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