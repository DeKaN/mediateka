
public class Controller {

    class FilmsManager implements RecordsManager {

        private Films films = null;

        public boolean Add(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean Delete(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Edit(int id, Record newData) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Find(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Find(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    class DiscsManager implements RecordsManager {

        private Discs discs = null;

        public boolean Add(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean Delete(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Edit(int id, Record newData) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Find(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Find(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    class PersonsManager implements RecordsManager {

        private Persons persons = null;

        public boolean Add(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean Delete(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Edit(int id, Record newData) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Find(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Find(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    class HistoryManager implements RecordsManager {

        private History history = null;

        public boolean Add(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean Delete(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Edit(int id, Record newData) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Find(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Find(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    class BlackListManager implements RecordsManager {

        private Blacklist blackList = null;

        public boolean Add(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean Delete(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Edit(int id, Record newData) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Find(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Find(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    interface RecordsManager {

        /**
         * 
         * @param record
         */
        boolean Add(Record record);

        /**
         * 
         * @param id
         */
        boolean Delete(int id);

        /**
         * 
         * @param id
         * @param newData
         */
        Records Edit(int id, Record newData);

        /**
         * 
         * @param id
         */
        Records Find(int id);

        /**
         * 
         * @param record
         */
        Records Find(Record record);
    }

    public interface Command {

        /**
         * 
         * @param id
         */
        Records Execute(int id);

        /**
         * 
         * @param record
         */
        Records Execute(Record record);

        Records ToString();

        /**
         * 
         * @param id
         * @param record
         */
        Records Execute(int id, Record record);
    }

    public class AddFilmCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class DeleteFilmCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class EditFilmCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class FindFilmCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class AddDiscCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class DeleteDiscCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class EditDiscCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class FindDiscCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class AddBlRecordCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class DeleteBlRecordCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class EditBlRecordCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class FindBlRecordCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class AddPersonCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class DeletePersonCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class EditPersonCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class FindPersonCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class AddHistRecCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class DeleteHistRecCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class EditHistRecCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public class FindHistRecCommand implements Command {

        public Records Execute(int id) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records ToString() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Records Execute(int id, Record record) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}