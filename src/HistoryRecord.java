
import java.util.Date;

public class HistoryRecord implements Record {

	private int recordID;
	private Disc disc;
	private Person person;
	private Date giveDate;
	private Date promisedDate;
	private Date returnDate = null;
	private String comment = "";

	public Disc getDisc() {
		return this.disc;
	}

	public void setDisc(Disc disc) {
		this.disc = disc;
	}

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Date getGiveDate() {
		return this.giveDate;
	}

	public Date getPromisedDate() {
		return this.promisedDate;
	}

	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}

	public Date getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 
	 * @param disc
	 * @param person
	 * @param give
	 * @param promise
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
	 */
	public HistoryRecord HistoryRecord(Disc disc, Person person, Date give, Date promise, Date returned, String comment) {
		throw new UnsupportedOperationException();
	}

    public String ToXmlElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}