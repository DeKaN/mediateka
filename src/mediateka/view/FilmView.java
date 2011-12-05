/*
 * FilmView.java
 *
 * Created on Nov 20, 2011, 9:29:27 PM
 */
package mediateka.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import mediateka.datamanagers.Managers;
import mediateka.db.ChangeDataException;
import mediateka.db.Film;
import org.jdesktop.application.Action;

/**
 *
 * @author Alexandr
 */
public class FilmView extends javax.swing.JDialog {

    private int _rating;
    private HashMap componentMap;
    private ActionListener ratingListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            String temp = ((AbstractButton) (e.getSource())).getText();
            _rating = Integer.parseInt(temp);
        }
    };
    private Image image;
    private Film film = null;
    private String[] genres = new String[]{
        "", "Анимационный", "Аниме", "Биография", "Боевик",
        "Вестерн", "Военный", "Детектив", "Детский", "Для взрослых",
        "Документальный", "Драма", "Игра", "Исторический", "История",
        "Комедия", "Концерт", "Короткометражка", "Криминал", "Любовный роман",
        "Мелодрама", "Мистика", "Музыка", "Мультфильм", "Мюзикл",
        "Отечественный", "Пародия", "Приключения", "Реальное ТВ", "Романтика",
        "Семейный", "Спорт", "Триллер", "Ужасы", "Фантастика", "Фильм-нуар",
        "Фэнтези"
    };
    private String[] countries = new String[]{
        "", "Австралия", "Австрия", "Азербайджан", "Албания",
        "Алжир", "Американское Самоа", "Ангилья", "Ангола", "Андорра",
        "Антигуа и Барбуда", "Аргентина", "Армения", "Аруба", "Афганистан",
        "Багамы", "Бангладеш", "Барбадос", "Бахрейн", "Беларусь,Белиз",
        "Бельгия", "Бенин", "Бермуды", "Болгария", "Боливия",
        "Босния и Герцеговина", "Ботсвана", "Бразилия", "Бруней-Даруссалам",
        "Буркина-Фасо", "Бурунди", "Бутан", "Вануату", "Великобритания",
        "Венгрия", "Венесуэла", "Виргинские острова, Британские",
        "Виргинские острова, США", "Восточный Тимор", "Вьетнам", "Габон",
        "Гаити", "Гайана", "Гамбия", "Гана", "Гваделупа", "Гватемала",
        "Гвинея", "Гвинея-Бисау", "Германия", "Гибралтар", "Гондурас",
        "Гонконг", "Гренада", "Гренландия", "Греция", "Грузия", "Гуам",
        "Дания", "Джибути", "Доминика", "Доминиканская Республика", "Египет",
        "Замбия", "Западная Сахара", "Зимбабве", "Израиль", "Индия",
        "Индонезия", "Иордания", "Ирак", "Иран", "Ирландия", "Исландия",
        "Испания", "Италия", "Йемен", "Кабо-Верде", "Казахстан", "Камбоджа",
        "Камерун", "Канада", "Катар", "Кения", "Кипр", "Кирибати", "Китай",
        "Колумбия", "Коморы", "Конго", "Конго, демократическая республика",
        "Коста-Рика", "Кот д`Ивуар", "Куба", "Кувейт", "Кыргызстан", "Лаос",
        "Латвия", "Лесото", "Либерия", "Ливан", "Ливийская Арабская Джамахирия",
        "Литва", "Лихтенштейн", "Люксембург", "Маврикий", "Мавритания",
        "Мадагаскар", "Макао", "Македония", "Малави", "Малайзия", "Мали",
        "Мальдивы", "Мальта", "Марокко", "Мартиника", "Маршалловы Острова",
        "Мексика", "Микронезия, федеративные штаты", "Мозамбик", "Молдова",
        "Монако", "Монголия", "Монтсеррат", "Мьянма", "Намибия", "Науру",
        "Непал", "Нигер", "Нигерия", "Нидерландские Антилы", "Нидерланды",
        "Никарагуа", "Ниуэ", "Новая Зеландия", "Новая Каледония", "Норвегия",
        "Объединенные Арабские Эмираты", "Оман", "Остров Мэн", "Остров Норфолк",
        "Острова Кайман", "Острова Кука", "Острова Теркс и Кайкос", "Пакистан",
        "Палау", "Палестинская автономия", "Панама", "Папуа - Новая Гвинея",
        "Парагвай", "Перу", "Питкерн", "Польша", "Португалия", "Пуэрто-Рико",
        "Реюньон", "Россия", "Руанда", "Румыния", "США", "Сальвадор", "Самоа",
        "Сан-Марино", "Сан-Томе и Принсипи", "Саудовская Аравия", "Свазиленд",
        "Святая Елена", "Северная Корея", "Северные Марианские острова",
        "Сейшелы", "Сенегал", "Сент-Винсент", "Сент-Китс и Невис", "Сент-Люсия",
        "Сент-Пьер и Микелон", "Сербия", "Сингапур",
        "Сирийская Арабская Республика", "Словакия", "Словения",
        "Соломоновы Острова", "Сомали", "Судан", "Суринам", "Сьерра-Леоне",
        "Таджикистан", "Таиланд", "Тайвань", "Танзания", "Того", "Токелау",
        "Тонга", "Тринидад и Тобаго", "Тувалу", "Тунис", "Туркмения", "Турция",
        "Уганда", "Узбекистан", "Украина", "Уоллис и Футуна", "Уругвай",
        "Фарерские острова", "Фиджи", "Филиппины", "Финляндия",
        "Фолклендские острова", "Франция", "Французская Гвиана",
        "Французская Полинезия", "Хорватия", "Центрально-Африканская Республика",
        "Чад", "Черногория", "Чехия", "Чили", "Швейцария", "Швеция",
        "Шпицберген и Ян Майен", "Шри-Ланка", "Эквадор", "Экваториальная Гвинея",
        "Эритрея", "Эстония", "Эфиопия", "Южная Корея",
        "Южно-Африканская Республика", "Ямайка", "Япония"};
    private String[] languages = new String[]{
        "", "английский", "арабский", "болгарский", "венгерский", "вьетнамский",
        "голландский", "греческий", "датский", "иврит", "индонезийский",
        "испанский (Испания)", "испанский (Латинская Америка)", "итальянский",
        "каталанский", "китайский (Традиционная китайская)",
        "китайский (Упрощенная китайская)", "корейский", "латышский",
        "литовский", "малайский", "немецкий",
        "норвежский", "персидский", "польский", "португальский (Бразилия)",
        "португальский (Португалия)", "румынский", "русский", "сербский",
        "словацкий", "словенский", "тайский", "турецкий", "украинский",
        "филиппинский", "финский", "французский", "хинди", "хорватский",
        "чешский", "шведский", "эстонский", "японский"
    };

    private void createComponentMap() {
        componentMap = new HashMap<String, Component>();
        addChildComponents(getContentPane());
    }

    private void addChildComponents(Container panel) {
        Component[] components = panel.getComponents();
        for (int i = 0; i < components.length; i++) {
            componentMap.put(components[i].getName(), components[i]);
            if (components[i] instanceof Container) {
                addChildComponents((Container) components[i]);
            }
        }
    }

    /** Creates new form FilmView */
    public FilmView(java.awt.Frame parent, boolean modal, Film flm) {
        super(parent, modal);
        film = flm;
        initComponents();
        createComponentMap();
        jComboBox1.setModel(new DefaultComboBoxModel(genres));
        jComboBox2.setModel(new DefaultComboBoxModel(genres));
        jComboBox3.setModel(new DefaultComboBoxModel(genres));
        jComboBox4.setModel(new DefaultComboBoxModel(countries));
        jComboBox5.setModel(new DefaultComboBoxModel(countries));
        jComboBox6.setModel(new DefaultComboBoxModel(countries));
        jComboBox7.setModel(new DefaultComboBoxModel(languages));
        jComboBox8.setModel(new DefaultComboBoxModel(languages));
        jComboBox9.setModel(new DefaultComboBoxModel(languages));
        jComboBox10.setModel(new DefaultComboBoxModel(languages));
        jComboBox11.setModel(new DefaultComboBoxModel(languages));
        jComboBox12.setModel(new DefaultComboBoxModel(languages));
        if (film == null) {
            this.setTitle("Добавить фильм...");
            jButton2.setText("Добавить");
            _rating = 0;
        } else {
            this.setTitle("Изменить фильм...");
            jButton2.setText("Сохранить");
            jTextField1.setText(film.getRussianTitle());
            jTextField2.setText(film.getEnglishTitle());
            jTextField3.setText(Integer.toString(film.getYear()));
            jTextField4.setText(Integer.toString(film.getLength()));
            jTextArea1.setText(film.getDescription());
            comboBoxPrepare(film.getGenres(), jComboBox1, jComboBox2, jComboBox3);
            comboBoxPrepare(film.getCountries(), jComboBox4, jComboBox5, jComboBox6);
            comboBoxPrepare(film.getSubtitles(), jComboBox7, jComboBox8, jComboBox9);
            comboBoxPrepare(film.getSoundLanguages(), jComboBox10, jComboBox11, jComboBox12);
            jTextField5.setText(film.getComment());
            _rating = film.getRating();
            ((JRadioButton) (componentMap.get("jRadioButton" + Integer.toString(_rating + 1)))).setSelected(true);
            jRadioButton8.setSelected((film).isSeen());
            for (int i = 1; i < 7; i++) {
                ((JRadioButton) (componentMap.get("jRadioButton" + i))).addActionListener(ratingListener);
            }

        }
    }

    private static void comboBoxPrepare(String[] tmp, JComboBox cb1, JComboBox cb2, JComboBox cb3) {
        if (tmp != null) {
            cb1.setSelectedItem(tmp[0]);
            cb2.setSelectedItem((tmp.length > 1) ? tmp[1] : "");
            cb3.setSelectedItem((tmp.length > 2) ? tmp[2] : "");

        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox5 = new javax.swing.JComboBox();
        jComboBox6 = new javax.swing.JComboBox();
        jComboBox7 = new javax.swing.JComboBox();
        jComboBox8 = new javax.swing.JComboBox();
        jComboBox9 = new javax.swing.JComboBox();
        jComboBox10 = new javax.swing.JComboBox();
        jComboBox11 = new javax.swing.JComboBox();
        jComboBox12 = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(mediateka.MediatekaApp.class).getContext().getResourceMap(FilmView.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jTextField2.setFont(resourceMap.getFont("jTextField2.font")); // NOI18N
        jTextField2.setText(resourceMap.getString("jTextField2.text")); // NOI18N
        jTextField2.setName("jTextField2"); // NOI18N

        jTextField3.setText(resourceMap.getString("jTextField3.text")); // NOI18N
        jTextField3.setName("jTextField3"); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

        jTextField4.setText(resourceMap.getString("jTextField4.text")); // NOI18N
        jTextField4.setName("jTextField4"); // NOI18N
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField4KeyTyped(evt);
            }
        });

        jComboBox1.setName("jComboBox1"); // NOI18N

        jComboBox2.setName("jComboBox2"); // NOI18N

        jComboBox3.setName("jComboBox3"); // NOI18N

        jComboBox4.setName("jComboBox4"); // NOI18N

        jComboBox5.setName("jComboBox5"); // NOI18N

        jComboBox6.setName("jComboBox6"); // NOI18N

        jComboBox7.setName("jComboBox7"); // NOI18N

        jComboBox8.setName("jComboBox8"); // NOI18N

        jComboBox9.setName("jComboBox9"); // NOI18N

        jComboBox10.setName("jComboBox10"); // NOI18N

        jComboBox11.setName("jComboBox11"); // NOI18N

        jComboBox12.setName("jComboBox12"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(mediateka.MediatekaApp.class).getContext().getActionMap(FilmView.class, this);
        jButton2.setAction(actionMap.get("okButton")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        jButton3.setAction(actionMap.get("closeFilmView")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N

        jPanel3.setName("jPanel3"); // NOI18N

        jTextField5.setText(resourceMap.getString("jTextField5.text")); // NOI18N
        jTextField5.setName("jTextField5"); // NOI18N

        buttonGroup2.add(jRadioButton8);
        jRadioButton8.setText(resourceMap.getString("jRadioButton8.text")); // NOI18N
        jRadioButton8.setName("jRadioButton8"); // NOI18N

        buttonGroup2.add(jRadioButton7);
        jRadioButton7.setSelected(true);
        jRadioButton7.setText(resourceMap.getString("jRadioButton7.text")); // NOI18N
        jRadioButton7.setName("jRadioButton7"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setFont(resourceMap.getFont("jTextArea1.font")); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        buttonGroup1.add(jRadioButton5);
        jRadioButton5.setText(resourceMap.getString("jRadioButton5.text")); // NOI18N
        jRadioButton5.setName("jRadioButton5"); // NOI18N

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText(resourceMap.getString("jRadioButton4.text")); // NOI18N
        jRadioButton4.setName("jRadioButton4"); // NOI18N

        buttonGroup1.add(jRadioButton6);
        jRadioButton6.setText(resourceMap.getString("jRadioButton6.text")); // NOI18N
        jRadioButton6.setName("jRadioButton6"); // NOI18N

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setSelected(true);
        jRadioButton1.setText(resourceMap.getString("jRadioButton1.text")); // NOI18N
        jRadioButton1.setActionCommand(resourceMap.getString("jRadioButton1.actionCommand")); // NOI18N
        jRadioButton1.setName("jRadioButton1"); // NOI18N

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText(resourceMap.getString("jRadioButton2.text")); // NOI18N
        jRadioButton2.setName("jRadioButton2"); // NOI18N

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText(resourceMap.getString("jRadioButton3.text")); // NOI18N
        jRadioButton3.setName("jRadioButton3"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE))
                        .addGap(10, 10, 10))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton8)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jRadioButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton5)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton6)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton4)
                            .addComponent(jRadioButton5)
                            .addComponent(jRadioButton6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButton8))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10)))
                            .addComponent(jLabel9)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(202, 202, 202)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jRadioButton1)
                                    .addComponent(jLabel12))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton7)
                            .addComponent(jLabel13))))
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(61, 61, 61)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(12, 12, 12)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(24, 24, 24)
                            .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(247, 247, 247))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jComboBox6, 0, 200, Short.MAX_VALUE)
                                        .addComponent(jComboBox9, 0, 200, Short.MAX_VALUE)
                                        .addComponent(jComboBox3, 0, 200, Short.MAX_VALUE)
                                        .addComponent(jComboBox12, 0, 200, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jButton2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(jButton3))))
                                .addComponent(jTextField4)
                                .addComponent(jTextField3)
                                .addComponent(jTextField2)
                                .addComponent(jTextField1))))))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBox1, jComboBox10, jComboBox11, jComboBox12, jComboBox2, jComboBox3, jComboBox4, jComboBox5, jComboBox6, jComboBox7, jComboBox8, jComboBox9});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField3KeyTyped
        if ((jTextField3.getText().length() > 3) || (!Character.isDigit(evt.getKeyChar()))) {
            {
                evt.consume();
            }
        }
    }//GEN-LAST:event_jTextField3KeyTyped

    private void jTextField4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyTyped
        if ((jTextField4.getText().length() > 2) || (!Character.isDigit(evt.getKeyChar()))) {
            {
                evt.consume();
            }
        }
    }//GEN-LAST:event_jTextField4KeyTyped

    @Action
    public void closeFilmView() {
        this.dispose();
    }

    @Action
    public void choiseFile() {
//        JFileChooser fc = new JFileChooser();
//        fc.addChoosableFileFilter(new FileNameExtensionFilter("JPEG Picture", new String[]{"jpg", "jpeg"}));
//        int returnVal = fc.showOpenDialog(this);
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            try {
//                File file = fc.getSelectedFile();
//                BufferedImage img = ImageIO.read(file);
//                jPanel4 = new ImagePanel(img);
//            } catch (IOException e) {
//            }
//        }
    }

    @Action
    public void okButton() {
        try {
            String _russianTitle = jTextField1.getText().trim();

            if (_russianTitle.equals("")) {
                JOptionPane.showMessageDialog(this, "Не заполнено обязательное поле (Русское зазвание)", "Ошибка!", JOptionPane.ERROR_MESSAGE);
            } else {
                String _englishTitle = jTextField2.getText().trim();
                int _year = 0;
                String tmpStr = jTextField3.getText();
                try {
                    if (jTextField3.getText().length() != 0) {
                        _year = Integer.parseInt(tmpStr);
                    }
                } catch (Exception e) {
                }
                int _length = 0;
                tmpStr = jTextField4.getText();
                try {
                    if (jTextField4.getText().length() != 0) {
                        _length = Integer.parseInt(tmpStr);
                    }
                } catch (Exception e) {
                }
                boolean _isSeen = (jRadioButton8.isSelected());
                String _comment = jTextField5.getText().trim();
                String _description = jTextArea1.getText().trim();

                String[] _genres = getDataFromComboboxsAsStrings(jComboBox1, jComboBox2, jComboBox3);
                String[] _countries = getDataFromComboboxsAsStrings(jComboBox4, jComboBox5, jComboBox6);
                String[] _subtitles = getDataFromComboboxsAsStrings(jComboBox7, jComboBox8, jComboBox9);
                String[] _soundLanguages = getDataFromComboboxsAsStrings(jComboBox10, jComboBox11, jComboBox12);

                if (film != null) {

                    film.setRussianTitle(_russianTitle);
                    film.setEnglishTitle(_englishTitle);
                    film.setYear(_year);
                    film.setDescription(_description);
                    film.setGenres(_genres);
                    film.setCountries(_countries);
                    film.setComment(_comment);
                    film.setLength(_length);
                    film.setRating(_rating);
                    film.setSubtitles(_subtitles);
                    film.setSoundLanguages(_soundLanguages);
                    film.setSeen(_isSeen);
                    if (!Managers.getInstance().getFilmsManager().edit(film)) {
                        throw new ChangeDataException("Ошибка при сохранении");
                    }

                } else {
                    film = new Film(_russianTitle, _englishTitle, _year, _description, _genres, _countries,
                            _comment, _length, _rating, _subtitles, _soundLanguages, _isSeen);
                    if (!Managers.getInstance().getFilmsManager().add(film)) {
                        throw new ChangeDataException("Ошибка при добавлении");
                    }
                }
                this.dispose();
            }
        } catch (Exception ex) {
            Logger.getLogger(FilmView.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private String[] getDataFromComboboxsAsStrings(JComboBox cb1, JComboBox cb2, JComboBox cb3) {
        ArrayList<String> list = new ArrayList<String>();
        if ((cb1.getSelectedIndex() != 0) && (!list.contains((String) cb1.getSelectedItem()))) {
            list.add((String) (cb1.getSelectedItem()));
        }
        if ((cb2.getSelectedIndex() != 0) && (!list.contains((String) cb2.getSelectedItem()))) {
            list.add((String) (cb2.getSelectedItem()));
        }
        if ((cb3.getSelectedIndex() != 0) && (!list.contains((String) cb3.getSelectedItem()))) {
            list.add((String) (cb3.getSelectedItem()));
        }
        return (list.isEmpty()) ? null : list.toArray(new String[1]);

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox10;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox12;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
