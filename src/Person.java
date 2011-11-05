public class Person implements Record {

	private int personID;
	private String surname;
	private String name;
	private String secondName;
	private String phone;

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return this.secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 
	 * @param surname
	 * @param name
	 * @param secondName
	 * @param phone
	 */
	public Person Person(String surname, String name, String secondName, String phone) {
		throw new UnsupportedOperationException();
	}

    public String ToXmlElement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}