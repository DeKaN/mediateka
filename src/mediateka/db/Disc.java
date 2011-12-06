package mediateka.db;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;

/**
 * Класс, представляющий диск с набором фильмов
 * @author Alexandr
 */
public class Disc implements Record {

    /**
     * Формат диска
     */
    public enum Format {

        любой,
        /**
         * CD диск
         */
        CD,
        /**
         * DVD диск
         */
        DVD,
        /**
         * Blue-Ray диск
         */
        BD
    };
    private int discID = 0;
    private Films films = null;
    private int ownerID = 0;
    private Format format = null;
    private int regionCode = 0;
    private boolean presented = true;

    /**
     * Получить ID диска
     * @return ID диска
     */
    public int getID() {
        return this.discID;
    }

    /**
     * Записать ID записи
     * @param value 
     */
    public void setID(int value) {
        this.discID = value;
    }

    /**
     * Получить все фильмы на диске
     * @return Фильмы
     */
    public Films getFilms() {
        return this.films;
    }

    /**
     * Установить список фильмов
     * @param films Фильмы которые будут установлены
     */
    public void setFilms(Films films) {
        this.films = films;
    }

    /**
     * Получить владельца диска
     * @return Владелец диска
     */
    public int getOwnerID() {
        return this.ownerID;
    }

    /**
     * Получить формат диска
     * @return Формат диска
     */
    public Format getFormat() {
        return this.format;
    }

    /**
     * Установить формат диска
     * @param format Новый формат диска
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * Получить регион диска
     * @return Регион диска
     */
    public int getRegionCode() {
        return this.regionCode;
    }

    /**
     * Установить регион диска
     * @param regionCode Новый регион диска
     */
    public void setRegionCode(int regionCode) {
        this.regionCode = regionCode;
    }

    /**
     * В наличии ли диск
     * @return Возвращает true если диск в наличии
     */
    public boolean isPresented() {
        return this.presented;
    }

    /**
     * Устанавливает наличие диска в коллекции
     * @param isPresented Если диск в наличии - true, иначе false
     */
    public void setPresented(boolean isPresented) {
        this.presented = isPresented;
    }

    /**
     * Внутренний конструктор по обязательным параметрам
     * @param discID ID диска
     * @param format Формат диска
     * @param films Список фильмов
     */
    public Disc(int discID, Format format, Films films) {
        this(discID, films, 0, format, 0, true);
    }

    /**
     * Конструктор по обязательным параметрам
     * @param format Формат диска
     * @param films Список фильмов
     */
    public Disc(Format format, Films films) {
        this(films, 0, format, 0, true);
    }

    /**
     * Полный конструктор
     * @param films Список фильмов
     * @param ownerID Владелец диска
     * @param format Формат диска
     * @param discRegion Регион диска
     * @param presented Наличие диска
     */
    public Disc(Films films, int ownerID, Format format, int discRegion, boolean presented) {
        this(0, films, ownerID, format, discRegion, presented);
    }

    /**
     * Внутренний конструктор
     * @param discID ID диска
     */
    public Disc(int discID) {
        this.discID = discID;
    }

    /**
     * Внутренний полный конструктор
     * @param discID ID диска
     * @param films Список фильмов
     * @param ownerID Владелец диска
     * @param format Формат диска
     * @param discRegion Регион диска
     * @param presented Наличие диска
     */
    public Disc(int discID, Films films, int ownerID, Format format, int discRegion, boolean presented) {
        this.discID = discID;
        this.films = films;
        this.ownerID = ownerID;
        this.format = format;
        this.regionCode = discRegion;
        this.presented = presented;
    }

    /**
     * Сериализует диск в XML
     * @return Строка с диском, сериализованным в XML element
     */
    public Element toXmlElement() {
        Element elem = new DefaultElement("disc");
        elem.addAttribute("id", Integer.toString(this.discID));
        elem.addElement("ownerID").addText(Integer.toString(this.ownerID));
        elem.addElement("format").addText(this.format.toString());
        elem.addElement("regionCode").addText(Integer.toString(regionCode));
        elem.addElement("isPresent").addText(Boolean.toString(presented));
        Element temp = new DefaultElement("films");
        for (Iterator<Record> it = films.toList().iterator(); it.hasNext();) {
            Record record = it.next();
            temp.addElement("filmID").addText(Integer.toString(record.getID()));
        }
        elem.add(temp);
        return elem;
    }

    @Override
    public String toString() {
        List<Record> fis = films.toList();
        String[] str = new String[fis.size()];
        int i = 0;
        for (Record record : fis) {
            str[i++] = ((Film) record).toString();
        }
        return format + " №" + discID + " (" + StringUtils.join(str, ", ") + ")";
    }

    public static String[] getFormats() {
        Format[] formats = Format.values();
        String[] retVal = new String[formats.length-1];
        for (int i = 0; i < retVal.length; i++) {
            retVal[i] = formats[i+1].name();
        }
        return retVal;
    }

    public ArrayList<Integer> getListOfFilmIDs() {
        ArrayList<Integer> retVal = new ArrayList<Integer>();
        for (int i = 0; i < films.size(); i++) {
            retVal.add(films.getRecord(i).getID());
        }
        return retVal;
    }
}
