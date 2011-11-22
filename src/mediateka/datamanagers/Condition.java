/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.datamanagers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import mediateka.db.Record;
import org.apache.commons.lang3.StringUtils;
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

    //TODO необходимо протестить работу
    public boolean isEquals(Record rec) {
        DOMElement elem = (DOMElement) rec.toXmlElement();
        Set<String> keys = conds.keySet();
        boolean retVal = true;
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next();
            String val = elem.getAttribute(key);
            if (val.isEmpty()) {
                return false;
            }
            key = conds.get(key);
            String[] ids = StringUtils.split(val, ',');
            if (ids.length > 1) {
                for (String str : ids) {
                    if (key.equals(str)) break;
                    retVal = false;
                }                
            } else {
                if (!val.contains(key)) {
                    retVal = false;
                    break;
                }
            }
        }
        return retVal;
    }
}
