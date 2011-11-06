package mediateka.db;

/**
 * 
 * @author Alexandr
 */
public interface Records {

	/**
	 * 
	 * @param record
	 */
	void Add(Record record);

	/**
	 * 
	 * @param record
	 */
	void Delete(Record record);

	/**
	 * 
	 * @param oldRecord
	 * @param newRecord
	 */
	void Update(Record oldRecord, Record newRecord);

        /**
         * 
         */
        void Save();

        /**
         * 
         */
        void Load();

	/**
	 * 
	 * @param records
	 */
	void Import(Records records);

        /**
         * 
         * @return
         */
        Records Export();

	/**
	 * 
         * @param record
         * @return  
	 */
	Records Find(Record record);

        /**
         * 
         * @return
         */
        String ToXmlElement();

}