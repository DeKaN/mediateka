package mediateka.db;

import mediateka.db.Record;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.dom.DOMElement;

/**
 * Класс, представляющий диск с набором фильмов
 * @author Alexandr
 */
public class Disc implements Record {

    /**
     * Формат диска
     */
    public enum Format {

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
    private int discID;
    private Films films;
    private int ownerID = 0;
    private Format format;
    private int regionCode = 0;
    private boolean isPresented = true;

    /**
     * Получить ID диска
     * @return ID диска
     */
    public int getID() {
        return this.discID;
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
    public boolean isIsPresented() {
        return this.isPresented;
    }

    /**
     * Устанавливает наличие диска в коллекции
     * @param isPresented Если диск в наличии - true, иначе false
     */
    public void setIsPresented(boolean isPresented) {
        this.isPresented = isPresented;
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
        if ((format == null) || (films == null)) {
            throw new NullPointerException();
        }
        this.films = films;
        this.ownerID = ownerID;
        this.format = format;
        this.regionCode = discRegion;
        this.isPresented = presented;
    }

    /**
     * Сериализует диск в XML
     * @return Строка с диском, сериализованным в XML element
     */
    public String ToXmlElement() {
        Element elem = new DOMElement("disc");
        elem.addAttribute("discID", Integer.toString(this.getID()));
        elem.addElement("ownerID").addText(Integer.toString(this.getOwnerID()));
        elem.addElement("format").addText(this.getFormat().toString());
        elem.addElement("regionCode").addText(Integer.toString(this.getRegionCode()));
        elem.addElement("isPresent").addText(Boolean.toString(this.isIsPresented()));
        elem.addElement("films").addText(this.getFilms().ToXmlElement());
        return elem.asXML();
    }
}