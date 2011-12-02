/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.datamanagers;

import java.util.List;
import mediateka.db.Record;
import mediateka.db.Records;
import mediateka.db.Table;

/**
 *
 * @author DeKaN
 */
public class Manager implements RecordsManager {
    
    private Table table;
    
    public Manager(Table t, String filename){
        table = t;
        table.load(filename);
    }

    public boolean add(Record record) {
        return table.add(record);
    }

    public boolean delete(int id) {
        return table.delete(find(id));
    }

    public boolean edit(Record newData) {
        return table.update(newData);
    }

    public Record find(int id) {
        return table.find(id);
    }

    public Records find(Record record) {
        return table.find(record);
    }

    public List<Record> getRecords() {
        return table.toList();
    }
    
}
