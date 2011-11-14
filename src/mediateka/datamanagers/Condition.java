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

    public Condition(HashMap<String, String> conds) {
        if (conds == null) {
            throw new NullPointerException();
        }
        this.conds = conds;
    }

    public boolean isEquals(Record rec) {
        DOMElement elem = (DOMElement) rec.ToXmlElement();
        Set<String> keys = conds.keySet();
        boolean retVal = true;
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next();
            String val = elem.getAttribute(key);
            if ((val.isEmpty()) || (!conds.get(key).equals(val))) {
                retVal = false;
                break;
            }
        }
        return retVal;
    }
}
