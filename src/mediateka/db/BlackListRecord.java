package mediateka.db;


import mediateka.db.Record;
import mediateka.db.Person;

/**
 * 
 * @author Alexandr
 */
public class BlackListRecord implements Record {

	private int recordID;
	private Person person;
	private String comment;

        /**
         * 
         * @return
         */
        public Person getPerson() {
		return this.person;
	}

        /**
         * 
         * @param person
         */
        public void setPerson(Person person) {
		this.person = person;
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
         * @param person
         * @return  
	 */
	public BlackListRecord BlackList(Person person) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param person
         * @param comment
         * @return  
	 */
	public BlackListRecord BlackList(Person person, String comment) {
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