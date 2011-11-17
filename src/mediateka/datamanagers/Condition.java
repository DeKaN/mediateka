/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.datamanagers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import mediateka.db.Record;
import org.dom4j.dom.DOMElement;

/**
 * Класс условий для поиска
 * @author DeKaN
 */
public class Condition {

    HashMap<String, String> conds;

    enum CombineMode {

        AND, OR
    };

    public Condition(HashMap<String, String> conds) {
        if (conds == null) {
            throw new NullPointerException();
        }
        this.conds = conds;
    }

    //TODO необходимо протестить и потом переделать 
    //(contains надо заменить на что-то типа regex, чтобы избежать true при val=1, conds.get(key)=2,10)
    public boolean isEquals(Record rec) {
        DOMElement elem = (DOMElement) rec.ToXmlElement();
        Set<String> keys = conds.keySet();
        boolean retVal = true;
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next();
            String val = elem.getAttribute(key);
            if ((val.isEmpty()) || (!conds.get(key).contains(val))) {
                retVal = false;
                break;
            }
        }
        return retVal;
    }
}
