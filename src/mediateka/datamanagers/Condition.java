/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.datamanagers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import mediateka.db.Record;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

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
        Element elem = rec.toXmlElement(), elem2 = elem;
        Set<String> keys = conds.keySet();
        boolean retVal = true, isAttr = false;
        for (Iterator<String> it = keys.iterator(); it.hasNext();) {
            String key = it.next(), key2, val = "";
            List<Element> elems;
            isAttr = key.contains("id");
            key2 = conds.get(key);
            String[] elemNames = StringUtils.split(key, "/");
            for (int i = 0; i < elemNames.length - 1; i++) {
                elem2 = elem2.element(elemNames[i]);
                key = elemNames[i + 1];
            }
            if (isAttr) {
                val = elem2.attributeValue(key, "");
                if (val.isEmpty() || !check(val, key2)) {
                    return false;
                }
            } else {
                elems = elem2.elements(key);
                if (elems == null) {
                    return false;
                }
                for (Element element : elems) {
                    if (check(element.getText(), key2)) {
                        break;
                    }
                    retVal = false;
                }
            }
        }
        return retVal;
    }

    private boolean check(String val, String searchVals) {
        boolean retVal = true;
        String[] ids = StringUtils.split(searchVals, '\uFFFC');
        if (ids.length > 1) {
            for (String str : ids) {
                if (val.contains(str)) {
                    break;
                }
                retVal = false;
            }
        } else {
            if (!val.contains(searchVals)) {
                retVal = false;
            }
        }
        return retVal;
    }
}
