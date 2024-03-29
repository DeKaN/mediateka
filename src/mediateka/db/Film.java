package mediateka.db;

import java.util.Iterator;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.tree.DefaultElement;

/**
 * Класс, представляющий фильм
 * @author Il'ya
 */
public final class Film implements Record {

    private int filmID = 0;
    private String russianTitle = "";
    private String englishTitle = "";
    private int year = 0;
    private String description = "";
    private String[] genres = null;
    private String[] countries = null;
    private String comment = "";
    private int length = 0;
    private int rating = 0;
    private String[] subtitles = null;
    private String[] soundLanguages = null;
    private boolean seen = false;

    /**
     * Получить русское название фильма
     * @return Русское название фильма
     */
    public String getRussianTitle() {
        return this.russianTitle;
    }

    /**
     * Получить ID фильма
     * @return ID фильма
     */
    public int getID() {
        return this.filmID;
    }

    /**
     * Записать ID записи
     * @param value 
     */
    public void setID(int value) {
        this.filmID = value;
    }

    /**
     * Установить русское название фильма
     * @param russianTitle Устанавливаемое название фильма на русском языке
     */
    public void setRussianTitle(String russianTitle) {
        this.russianTitle = russianTitle;
    }

    /**
     * Получить английское название фильма
     * @return английское название фильма
     */
    public String getEnglishTitle() {
        return this.englishTitle;
    }

    /**
     * Установить английское название фильма
     * @param englishTitle Устанавливаемое английское название фильма
     */
    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }

    /**
     * Получить год выхода фидьма
     * @return год выхода фильма
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Установить год выхода фильма
     * @param year Устанавливаемый год выхода фильма
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Получение описания
     * @return описание фильма
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Установление описания фильма
     * @param description Устанавливаемое описание фильма
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Получить список жанров
     * @return список жанров
     */
    public String[] getGenres() {
        return this.genres;
    }

    /**
     * Установить список жанров
     * @param genres Устанавливаемый список жанров
     */
    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    /**
     * Получить список стран выбранного фильма
     * @return список стран выбранного фильма
     */
    public String[] getCountries() {
        return this.countries;
    }

    /**
     * Установить список стран выбранного фильма
     * @param countries список стран выбранного фильма
     */
    public void setCountries(String[] countries) {
        this.countries = countries;
    }

    /**
     * Получить комментарий
     * @return комментарий
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * Установить комментарий
     * @param comment устанавливаемый комментарий
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Получить длительность фильма
     * @return длительность фильма
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Установить длину фильма
     * @param length длина фильма
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Получить оценку фильма
     * @return оценка фильма
     */
    public int getRating() {
        return this.rating;
    }

    /**
     * Установить оценку фильма
     * @param rating Устанавливаемая оценка фильма
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Получить список доступных субтитров
     * @return список доступных субтитров
     */
    public String[] getSubtitles() {
        return this.subtitles;
    }

    /**
     * Установить список доступных субтитров
     * @param subtitles список доступных субтитров
     */
    public void setSubtitles(String[] subtitles) {
        this.subtitles = subtitles;
    }

    /**
     * Получить список языков озвучки
     * @return список языков озвучки
     */
    public String[] getSoundLanguages() {
        return this.soundLanguages;
    }

    /**
     * Установить список языков озвучки
     * @param soundLanguages список языков озвучки
     */
    public void setSoundLanguages(String[] soundLanguages) {
        this.soundLanguages = soundLanguages;
    }

    /**
     * Просмотрен ли фильм
     * @return true если фильм просмотрен
     */
    public boolean isSeen() {
        return this.seen;
    }

    /**
     * Установить факт просмотра
     * @param isSeen Если фильм просмотрен true, иначе - false
     */
    public void setSeen(boolean isSeen) {
        this.seen = isSeen;
    }

    /**
     * Внутренний конструктор
     * @param filmID ID фильма
     */
    Film(int filmID) {
        this.filmID = filmID;
    }

    /**
     * Внутренний полный конструктор
     * @param filmId ID фильма
     * @param russianTitle Русское название фильма
     * @param englishTitle Английское название фильма
     * @param year Год выхода
     * @param description Описание
     * @param genres Жанры
     * @param countries Страны
     * @param comment Комментарий
     * @param length Длительность
     * @param rating Оценка
     * @param subtitles Субтитры
     * @param soundLanguages Языки озвучки
     * @param isSeen Факт просмотра
     */
    public Film(int filmId, String russianTitle, String englishTitle, int year, String description, String[] genres, String[] countries, String comment, int length, int rating, String[] subtitles, String[] soundLanguages, boolean isSeen) {
        this.filmID = filmId;
        this.russianTitle = russianTitle;
        this.englishTitle = englishTitle;
        this.year = year;
        this.description = description;
        this.genres = genres;
        this.countries = countries;
        this.comment = comment;
        this.length = length;
        this.rating = rating;
        this.subtitles = subtitles;
        this.soundLanguages = soundLanguages;
        this.seen = isSeen;
    }

    /**
     * Внутренний конструктор по обязательным параметрам
     * @param filmId ID фильма
     * @param russianTitle Русское название фильма
     */
    public Film(int filmId, String russianTitle) {
        this(filmId, russianTitle, "", 0, "", null, null, "", 0, 0, null, null, false);
    }

    /**
     * Полный конструктор
     * @param russianTitle Русское название фильма
     * @param englishTitle Английское название фильма
     * @param year Год выхода
     * @param description Описание
     * @param genres Жанры
     * @param countries Страны
     * @param comment Комментарий
     * @param length Длительность
     * @param rating Оценка
     * @param subtitles Субтитры
     * @param soundLanguages Языки озвучки
     * @param isSeen Факт просмотра
     */
    public Film(String russianTitle, String englishTitle, int year, String description, String[] genres, String[] countries, String comment, int length, int rating, String[] subtitles, String[] soundLanguages, boolean isSeen) {
        this(0, russianTitle, englishTitle, year, description, genres, countries, comment, length, rating, subtitles, soundLanguages, isSeen);
    }

    /**
     * Конструктор по обязательным параметрам
     * @param russianTitle Русское название фильма
     */
    public Film(String russianTitle) {
        this(0, russianTitle);
    }

    /**
     * Конструктор фильма из XML element
     * 
     * @param elem
     *            XML element
     */
    public Film(DefaultElement elem) {
        DefaultElement elem2 = (DefaultElement) elem.element("genres");
        String[] genres2 = new String[elem2.nodeCount()];
        int i = 0;
        for (Iterator<Node> it1 = elem2.iterator(); it1.hasNext();) {
            Node node = it1.next();
            genres2[i] = node.getText();
            i++;
        }
        elem2 = (DefaultElement) elem.element("countries");
        String[] countries2 = new String[elem2.nodeCount()];
        i = 0;
        for (Iterator<Node> it1 = elem2.iterator(); it1.hasNext();) {
            Node node = it1.next();
            countries2[i] = node.getText();
            i++;
        }
        elem2 = (DefaultElement) elem.element("subtitles");
        String[] subtitles2 = new String[elem2.nodeCount()];
        i = 0;
        for (Iterator<Node> it1 = elem2.iterator(); it1.hasNext();) {
            Node node = it1.next();
            subtitles2[i] = node.getText();
            i++;
        }
        elem2 = (DefaultElement) elem.element("soundLanguages");
        String[] soundLanguages2 = new String[elem2.nodeCount()];
        i = 0;
        for (Iterator<Node> it1 = elem2.iterator(); it1.hasNext();) {
            Node node = it1.next();
            soundLanguages2[i] = node.getText();
            i++;
        }
        filmID = Integer.parseInt(elem.attribute("id").getValue());
        russianTitle = elem.node(0).getText();
        englishTitle = elem.node(1).getText();
        year = Integer.parseInt(elem.node(2).getText());
        description = elem.node(3).getText();
        genres = genres2;
        countries = countries2;
        comment = elem.node(6).getText();
        length = Integer.parseInt(elem.node(7).getText());
        rating = Integer.parseInt(elem.node(8).getText());
        subtitles = subtitles2;
        soundLanguages = soundLanguages2;
        seen = elem.node(11).getText().equals("true");
    }

    /**
     * Сериализует фильм в XML
     * @return Строка с фильмом, сериализованным в XML element
     */
    public Element toXmlElement() {
        Element elem = new DefaultElement("film");
        Element tempElem;
        elem.addAttribute("id", Integer.toString(filmID));
        elem.addElement("russianTitle").addText(russianTitle);
        elem.addElement("englishTitle").addText(englishTitle);
        elem.addElement("year").addText(Integer.toString(year));
        elem.addElement("description").addText(description);
        tempElem = new DefaultElement("genres");
        if (genres == null || genres.length == 0) {
            tempElem.addElement("genre");
        } else {
            for (int i = 0; i < genres.length; i++) {
                tempElem.addElement("genre").addText(genres[i]);
            }
        }
        elem.add(tempElem);
        tempElem = new DefaultElement("countries");
        if (countries == null || countries.length == 0) {
            tempElem.addElement("country");
        } else {
            for (int i = 0; i < countries.length; i++) {
                tempElem.addElement("country").addText(countries[i]);
            }
        }
        elem.add(tempElem);
        elem.addElement("comment").addText(comment);
        elem.addElement("length").addText(Integer.toString(length));
        elem.addElement("rating").addText(Integer.toString(rating));
        tempElem = new DefaultElement("subtitles");
        if (subtitles == null || subtitles.length == 0) {
            tempElem.addElement("subtitle");
        } else {
            for (int i = 0; i < subtitles.length; i++) {
                tempElem.addElement("subtitle").addText(subtitles[i]);
            }
        }
        elem.add(tempElem);
        tempElem = new DefaultElement("soundLanguages");
        if (soundLanguages == null || soundLanguages.length == 0) {
            tempElem.addElement("soundLanguage");
        } else {
            for (int i = 0; i < soundLanguages.length; i++) {
                tempElem.addElement("soundLanguage").addText(soundLanguages[i]);
            }
        }
        elem.add(tempElem);
        elem.addElement("isSeen").addText(Boolean.toString(seen));
        return elem;

    }

    @Override
    public String toString() {
        return russianTitle;
    }
}