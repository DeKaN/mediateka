package mediateka.db;


import mediateka.db.Record;
import mediateka.db.Person;

public class BlackListRecord implements Record {

	private int recordID;
	private Person person;
	private String comment;

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 
	 * @param person
	 */
	public BlackListRecord BlackList(Person person) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param person
	 * @param comment
	 */
	public BlackListRecord BlackList(Person person, String comment) {
		throw new UnsupportedOperationException();
	}

    public String ToXmlElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}