package mediateka.db;

/**
 * 
 * @author Alexandr
 */
public class Person implements Record {

	private int personID;
	private String surname;
	private String name;
	private String secondName;
	private String phone;

        /**
         * 
         * @return
         */
        public String getSurname() {
		return this.surname;
	}

        /**
         * 
         * @param surname
         */
        public void setSurname(String surname) {
		this.surname = surname;
	}

        /**
         * 
         * @return
         */
        public String getName() {
		return this.name;
	}

        /**
         * 
         * @param name
         */
        public void setName(String name) {
		this.name = name;
	}

        /**
         * 
         * @return
         */
        public String getSecondName() {
		return this.secondName;
	}

        /**
         * 
         * @param secondName
         */
        public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

        /**
         * 
         * @return
         */
        public String getPhone() {
		return this.phone;
	}

        /**
         * 
         * @param phone
         */
        public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 
	 * @param surname
	 * @param name
	 * @param secondName
         * @param phone
         * @return  
	 */
	public Person Person(String surname, String name, String secondName, String phone) {
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