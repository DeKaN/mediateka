package mediateka.db;


import mediateka.db.Record;

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

	public String getRussianTitle() {
		return this.russianTitle;
	}

	public void setRussianTitle(String russianTitle) {
		this.russianTitle = russianTitle;
	}

	public String getEnglishTitle() {
		return this.englishTitle;
	}

	public void setEnglishTitle(String englishTitle) {
		this.englishTitle = englishTitle;
	}

	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String[] getGenres() {
		return this.genres;
	}

	public void setGenres(String[] genres) {
		this.genres = genres;
	}

	public String[] getCountries() {
		return this.countries;
	}

	public void setCountries(String[] countries) {
		this.countries = countries;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String[] getSubtitles() {
		return this.subtitles;
	}

	public void setSubtitles(String[] subtitles) {
		this.subtitles = subtitles;
	}

	public byte[] getCover() {
		return this.cover;
	}

	public void setCover(byte[] cover) {
		this.cover = cover;
	}

	public String[] getSoundLanguages() {
		return this.soundLanguages;
	}

	public void setSoundLanguages(String[] soundLanguages) {
		this.soundLanguages = soundLanguages;
	}

	public boolean isIsSeen() {
		return this.isSeen;
	}

	public void setIsSeen(boolean isSeen) {
		this.isSeen = isSeen;
	}

	/**
	 * 
	 * @param russianName
	 */
	public Film Film(String russianName) {
		throw new UnsupportedOperationException();
	}

    public String ToXmlElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}