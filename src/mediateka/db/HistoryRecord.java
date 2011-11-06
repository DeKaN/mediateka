package mediateka.db;


import java.util.Date;

/**
 * 
 * @author Alexandr
 */
public class HistoryRecord implements Record {

	private int recordID;
	private Disc disc;
	private Person person;
	private Date giveDate;
	private Date promisedDate;
	private Date returnDate = null;
	private String comment = "";

        /**
         * 
         * @return
         */
        public Disc getDisc() {
		return this.disc;
	}

        /**
         * 
         * @param disc
         */
        public void setDisc(Disc disc) {
		this.disc = disc;
	}

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
        public Date getGiveDate() {
		return this.giveDate;
	}

        /**
         * 
         * @return
         */
        public Date getPromisedDate() {
		return this.promisedDate;
	}

        /**
         * 
         * @param promisedDate
         */
        public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}

        /**
         * 
         * @return
         */
        public Date getReturnDate() {
		return this.returnDate;
	}

        /**
         * 
         * @param returnDate
         */
        public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
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
	 * @param disc
	 * @param person
	 * @param give
         * @param promise
         * @return  
	 */
	public HistoryRecord HistoryRecord(Disc disc, Person person, Date give, Date promise) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param disc
	 * @param person
	 * @param give
	 * @param promise
	 * @param returned
         * @param comment
         * @return  
	 */
	public HistoryRecord HistoryRecord(Disc disc, Person person, Date give, Date promise, Date returned, String comment) {
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