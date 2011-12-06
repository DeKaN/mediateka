package mediateka.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import mediateka.datamanagers.Managers;
import mediateka.db.BlackListRecord;
import mediateka.db.Blacklist;
import mediateka.db.Disc;
import mediateka.db.Disc.Format;
import mediateka.db.Film;
import mediateka.db.Films;
import mediateka.db.HistoryRecord;
import mediateka.db.Person;
import mediateka.db.Record;
import mediateka.db.Records;
import org.jdesktop.application.Action;

/**
 *
 * @author DeKaN
 */
public class FindView extends javax.swing.JDialog {

    private static final String[] columnNamesFilms = new String[]{"ID", "Русское название", "Жанр", "Продолжительность", "Год", "Субтитры", "Оценка", "Просмотрен"};
    private static final String[] columnNamesDiscs = new String[]{"ID", "Список фильмов", "Формат", "Регион", "В наличии"};
    private static final String[] columnNamesPersons = new String[]{"ID", "Фамилия", "Имя", "Отчество", "Телефон", "Комментарий"};
    private static final String[] columnNamesBLRecords = new String[]{"ID", "ФИО", "Комментарий"};
    private static final String[] columnNamesHistoty = new String[]{"ID", "Диск", "Человек", "Дата выдачи", "Обещанная дата", "Дата возврата", "Комментарий"};
    private HashMap<Integer, Integer> personsMap = null, discMap = null;
    String[] personsStrs = null, discsStrs = null, columnNames = new String[]{"ID", "Русское название"}, formats = null, genres = new String[]{"", "Анимационный", "Аниме", "Биография", "Боевик", "Вестерн", "Военный", "Детектив", "Детский", "Для взрослых", "Документальный", "Драма", "Игра", "Исторический", "История", "Комедия", "Концерт", "Короткометражка", "Криминал", "Любовный роман", "Мелодрама", "Мистика", "Музыка", "Мультфильм", "Мюзикл", "Отечественный", "Пародия", "Приключения", "Реальное ТВ", "Романтика", "Семейный", "Спорт", "Триллер", "Ужасы", "Фантастика", "Фильм-нуар", "Фэнтези"}, countries = new String[]{"", "Австралия", "Австрия", "Азербайджан", "Албания", "Алжир", "Американское Самоа", "Ангилья", "Ангола", "Андорра", "Антигуа и Барбуда", "Аргентина", "Армения", "Аруба", "Афганистан", "Багамы", "Бангладеш", "Барбадос", "Бахрейн", "Беларусь,Белиз", "Бельгия", "Бенин", "Бермуды", "Болгария", "Боливия", "Босния и Герцеговина", "Ботсвана", "Бразилия", "Бруней-Даруссалам", "Буркина-Фасо", "Бурунди", "Бутан", "Вануату", "Великобритания", "Венгрия", "Венесуэла", "Виргинские острова, Британские", "Виргинские острова, США", "Восточный Тимор", "Вьетнам", "Габон", "Гаити", "Гайана", "Гамбия", "Гана", "Гваделупа", "Гватемала", "Гвинея", "Гвинея-Бисау", "Германия", "Гибралтар", "Гондурас", "Гонконг", "Гренада", "Гренландия", "Греция", "Грузия", "Гуам", "Дания", "Джибути", "Доминика", "Доминиканская Республика", "Египет", "Замбия", "Западная Сахара", "Зимбабве", "Израиль", "Индия", "Индонезия", "Иордания", "Ирак", "Иран", "Ирландия", "Исландия", "Испания", "Италия", "Йемен", "Кабо-Верде", "Казахстан", "Камбоджа", "Камерун", "Канада", "Катар", "Кения", "Кипр", "Кирибати", "Китай", "Колумбия", "Коморы", "Конго", "Конго, демократическая республика", "Коста-Рика", "Кот д`Ивуар", "Куба", "Кувейт", "Кыргызстан", "Лаос", "Латвия", "Лесото", "Либерия", "Ливан", "Ливийская Арабская Джамахирия", "Литва", "Лихтенштейн", "Люксембург", "Маврикий", "Мавритания", "Мадагаскар", "Макао", "Македония", "Малави", "Малайзия", "Мали", "Мальдивы", "Мальта", "Марокко", "Мартиника", "Маршалловы Острова", "Мексика", "Микронезия, федеративные штаты", "Мозамбик", "Молдова", "Монако", "Монголия", "Монтсеррат", "Мьянма", "Намибия", "Науру", "Непал", "Нигер", "Нигерия", "Нидерландские Антилы", "Нидерланды", "Никарагуа", "Ниуэ", "Новая Зеландия", "Новая Каледония", "Норвегия", "Объединенные Арабские Эмираты", "Оман", "Остров Мэн", "Остров Норфолк", "Острова Кайман", "Острова Кука", "Острова Теркс и Кайкос", "Пакистан", "Палау", "Палестинская автономия", "Панама", "Папуа - Новая Гвинея", "Парагвай", "Перу", "Питкерн", "Польша", "Португалия", "Пуэрто-Рико", "Реюньон", "Россия", "Руанда", "Румыния", "США", "Сальвадор", "Самоа", "Сан-Марино", "Сан-Томе и Принсипи", "Саудовская Аравия", "Свазиленд", "Святая Елена", "Северная Корея", "Северные Марианские острова", "Сейшелы", "Сенегал", "Сент-Винсент", "Сент-Китс и Невис", "Сент-Люсия", "Сент-Пьер и Микелон", "Сербия", "Сингапур", "Сирийская Арабская Республика", "Словакия", "Словения", "Соломоновы Острова", "Сомали", "Судан", "Суринам", "Сьерра-Леоне", "Таджикистан", "Таиланд", "Тайвань", "Танзания", "Того", "Токелау", "Тонга", "Тринидад и Тобаго", "Тувалу", "Тунис", "Туркмения", "Турция", "Уганда", "Узбекистан", "Украина", "Уоллис и Футуна", "Уругвай", "Фарерские острова", "Фиджи", "Филиппины", "Финляндия", "Фолклендские острова", "Франция", "Французская Гвиана", "Французская Полинезия", "Хорватия", "Центрально-Африканская Республика", "Чад", "Черногория", "Чехия", "Чили", "Швейцария", "Швеция", "Шпицберген и Ян Майен", "Шри-Ланка", "Эквадор", "Экваториальная Гвинея", "Эритрея", "Эстония", "Эфиопия", "Южная Корея", "Южно-Африканская Республика", "Ямайка", "Япония"}, languages = new String[]{"", "английский", "арабский", "болгарский", "венгерский", "вьетнамский", "голландский", "греческий", "датский", "иврит", "индонезийский", "испанский (Испания)", "испанский (Латинская Америка)", "итальянский", "каталанский", "китайский (Традиционная китайская)", "китайский (Упрощенная китайская)", "корейский", "латышский", "литовский", "малайский", "немецкий", "норвежский", "персидский", "польский", "португальский (Бразилия)", "португальский (Португалия)", "румынский", "русский", "сербский", "словацкий", "словенский", "тайский", "турецкий", "украинский", "филиппинский", "финский", "французский", "хинди", "хорватский", "чешский", "шведский", "эстонский", "японский"};
    int _rating = 0;
    private ActionListener ratingListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            String temp = ((AbstractButton) (e.getSource())).getText();
            _rating = Integer.parseInt(temp);
        }
    };
    private List<Record> filmRecords = null;
    private HashMap<Record, Boolean> filmsMap = null;

    private ArrayList<Integer> getListOfFilmIDs(List<Record> films) {
        ArrayList<Integer> retVal = new ArrayList<Integer>();
        for (int i = 0; i < films.size(); i++) {
            retVal.add(films.get(i).getID());
        }
        return retVal;
    }

    /** Creates new form FindView */
    public FindView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        List<Record> recs;
        initComponents();        
        try {
            //films
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
            jRadioButton1.addActionListener(ratingListener);
            jRadioButton2.addActionListener(ratingListener);
            jRadioButton3.addActionListener(ratingListener);
            jRadioButton4.addActionListener(ratingListener);
            jRadioButton5.addActionListener(ratingListener);
            jRadioButton6.addActionListener(ratingListener);
            //discs
            filmsMap = new HashMap<Record, Boolean>();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            List<Record> films = Managers.getInstance().getFilmsManager().getRecords();
            if (films != null) {
                ids = getListOfFilmIDs(films);
            }
            filmsMap = new HashMap<Record, Boolean>();
            for (Record rec : films) {
                filmsMap.put(rec, false);
            }
            filmRecords = Managers.getInstance().getFilmsManager().getRecords();
            updateTables(jTable1, jTable2);

            Format[] discFormats = Format.values();
            formats = new String[discFormats.length];
            for (int i = 0; i < formats.length; i++) {
                formats[i] = discFormats[i].name();
            }

            jComboBox13.setModel(new javax.swing.DefaultComboBoxModel(formats));
            jComboBox13.setSelectedItem("любой");
            ListSelectionModel selModel1 = jTable1.getSelectionModel();
            selModel1.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    delButton.setEnabled((jTable1.getSelectedRow() != -1) && (jTable1.getRowCount() != 0));
                }
            });
            jTabbedPane1.addChangeListener(new ChangeListener() {

                public void stateChanged(ChangeEvent evt) {
                    findButton.setVisible(jTabbedPane1.getSelectedIndex() != 5);
                }
            });
            ListSelectionModel selModel2 = jTable2.getSelectionModel();
            selModel2.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent e) {
                    addButton.setEnabled((jTable2.getSelectedRow() != -1) && (jTable2.getRowCount() != 0));
                }
            });
            recs = Managers.getInstance().getFilmsManager().getRecords();
            Object[][] data2 = new Object[recs.size()][];

            for (int i = 0; i < recs.size(); i++) {
                data2[i] = new Object[2];
                Record rec = recs.get(i);
                data2[i][0] = rec.getID();
                data2[i][1] = ((Film) rec).getRussianTitle();
            }
            jTable2.setModel(new DefaultTableModel(data2, columnNames));
            //blacklist
            recs = Managers.getInstance().getPersManager().getRecords();
            personsMap = new HashMap<Integer, Integer>();
            personsStrs = new String[recs.size()+1];
            int i = 1;
            Person p = null;
            personsMap.put(0, 0);
            personsStrs[0] = "-";
            for (Record rec : recs) {
                try {
                    p = (Person) rec;
                    personsMap.put(i, p.getID());
                    personsStrs[i] = p.toString();
                    i++;
                } catch (Exception e) {
                }
            }
            jComboBox14.setModel(new javax.swing.DefaultComboBoxModel(personsStrs));
            //history
            jComboBox15.setModel(new javax.swing.DefaultComboBoxModel(personsStrs));
            recs = Managers.getInstance().getDiscsManager().getRecords();
            discMap = new HashMap<Integer, Integer>();
            discsStrs = new String[recs.size()+1];
            i = 1;
            discMap.put(0, 0);
            discsStrs[0] = "-";
            for (Record rec : recs) {
                try {
                    Disc d = (Disc) rec;
                    discMap.put(i, d.getID());
                    discsStrs[i] = d.toString();
                    i++;
                } catch (Exception e) {
                }
            }
            jComboBox16.setModel(new javax.swing.DefaultComboBoxModel(discsStrs));
        } catch (Exception ex) {
            Logger.getLogger(FindView.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTabbedPane1.setSelectedIndex(0);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        findButton = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        filmPanel = new javax.swing.JPanel();
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
        jPanel8 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        discPanel = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jComboBox13 = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        delButton = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        personPanel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jTextField10 = new javax.swing.JTextField();
        blPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jComboBox14 = new javax.swing.JComboBox();
        historyPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel27 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jComboBox15 = new javax.swing.JComboBox();
        jLabel30 = new javax.swing.JLabel();
        jComboBox16 = new javax.swing.JComboBox();
        resultsPanel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N
        setResizable(false);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(mediateka.MediatekaApp.class).getContext().getResourceMap(FindView.class);
        mainPanel.setFont(resourceMap.getFont("mainPanel.font")); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(mediateka.MediatekaApp.class).getContext().getActionMap(FindView.class, this);
        findButton.setAction(actionMap.get("find")); // NOI18N
        findButton.setText(resourceMap.getString("findButton.text")); // NOI18N
        findButton.setName("findButton"); // NOI18N

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(32767, 100));
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        filmPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("filmPanel.border.title"))); // NOI18N
        filmPanel.setMaximumSize(new java.awt.Dimension(32767, 160));
        filmPanel.setMinimumSize(new java.awt.Dimension(0, 160));
        filmPanel.setName("filmPanel"); // NOI18N
        filmPanel.setPreferredSize(new java.awt.Dimension(1157, 160));

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

        jTextField1.setName("jTextField1"); // NOI18N

        jTextField2.setName("jTextField2"); // NOI18N

        jTextField3.setName("jTextField3"); // NOI18N
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField3KeyTyped(evt);
            }
        });

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

        jPanel8.setName("jPanel8"); // NOI18N

        jTextField5.setName("jTextField5"); // NOI18N

        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setFont(resourceMap.getFont("jTextArea1.font")); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane6.setViewportView(jTextArea1);

        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jRadioButton5.setText(resourceMap.getString("jRadioButton5.text")); // NOI18N
        jRadioButton5.setName("jRadioButton5"); // NOI18N

        jRadioButton4.setText(resourceMap.getString("jRadioButton4.text")); // NOI18N
        jRadioButton4.setName("jRadioButton4"); // NOI18N

        jRadioButton6.setText(resourceMap.getString("jRadioButton6.text")); // NOI18N
        jRadioButton6.setName("jRadioButton6"); // NOI18N

        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jRadioButton1.setSelected(true);
        jRadioButton1.setText(resourceMap.getString("jRadioButton1.text")); // NOI18N
        jRadioButton1.setName("jRadioButton1"); // NOI18N

        jRadioButton2.setText(resourceMap.getString("jRadioButton2.text")); // NOI18N
        jRadioButton2.setName("jRadioButton2"); // NOI18N

        jRadioButton3.setText(resourceMap.getString("jRadioButton3.text")); // NOI18N
        jRadioButton3.setName("jRadioButton3"); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(32, 32, 32)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton4)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton5)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton6))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jTextField5)
                            .addGap(282, 282, 282))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(175, 175, 175))
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jLabel11)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4)
                    .addComponent(jRadioButton5)
                    .addComponent(jRadioButton6))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout filmPanelLayout = new javax.swing.GroupLayout(filmPanel);
        filmPanel.setLayout(filmPanelLayout);
        filmPanelLayout.setHorizontalGroup(
            filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filmPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(filmPanelLayout.createSequentialGroup()
                        .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(filmPanelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(61, 61, 61)
                                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, filmPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(24, 24, 24)
                                    .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, filmPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(12, 12, 12)
                                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(filmPanelLayout.createSequentialGroup()
                                .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, filmPanelLayout.createSequentialGroup()
                                        .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, filmPanelLayout.createSequentialGroup()
                                        .addGap(104, 104, 104)
                                        .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, filmPanelLayout.createSequentialGroup()
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jComboBox6, 0, 200, Short.MAX_VALUE)
                                                    .addComponent(jComboBox9, 0, 200, Short.MAX_VALUE)
                                                    .addComponent(jComboBox3, 0, 200, Short.MAX_VALUE)
                                                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addGap(92, 92, 92)))
                        .addGap(392, 392, 392))
                    .addGroup(filmPanelLayout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(473, Short.MAX_VALUE))))
        );
        filmPanelLayout.setVerticalGroup(
            filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filmPanelLayout.createSequentialGroup()
                .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(filmPanelLayout.createSequentialGroup()
                        .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(filmPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBox10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(filmPanelLayout.createSequentialGroup()
                        .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("filmPanel.TabConstraints.tabTitle"), filmPanel); // NOI18N

        discPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("discPanel.border.title"))); // NOI18N
        discPanel.setMaximumSize(new java.awt.Dimension(32767, 160));
        discPanel.setMinimumSize(new java.awt.Dimension(0, 160));
        discPanel.setName("discPanel"); // NOI18N
        discPanel.setPreferredSize(new java.awt.Dimension(1157, 160));

        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jTextField6.setName("jTextField6"); // NOI18N
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6KeyTyped(evt);
            }
        });

        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox13.setName("jComboBox13"); // NOI18N

        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel7.border.title"))); // NOI18N
        jPanel7.setName("jPanel7"); // NOI18N

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Название"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane4.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setMinWidth(40);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(40);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N

        addButton.setAction(actionMap.get("addButton")); // NOI18N
        addButton.setText(resourceMap.getString("addButton.text")); // NOI18N
        addButton.setName("addButton"); // NOI18N

        delButton.setAction(actionMap.get("removeButton")); // NOI18N
        delButton.setText(resourceMap.getString("delButton.text")); // NOI18N
        delButton.setName("delButton"); // NOI18N

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Название"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setName("jTable2"); // NOI18N
        jScrollPane5.setViewportView(jTable2);
        jTable2.getColumnModel().getColumn(0).setMinWidth(40);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable2.getColumnModel().getColumn(0).setMaxWidth(40);
        jTable2.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable2.columnModel.title0")); // NOI18N
        jTable2.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable2.columnModel.title1")); // NOI18N

        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(delButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jScrollPane4, jScrollPane5});

        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addComponent(addButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(delButton))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout discPanelLayout = new javax.swing.GroupLayout(discPanel);
        discPanel.setLayout(discPanelLayout);
        discPanelLayout.setHorizontalGroup(
            discPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(discPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(discPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(discPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(553, Short.MAX_VALUE))
        );
        discPanelLayout.setVerticalGroup(
            discPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(discPanelLayout.createSequentialGroup()
                .addGroup(discPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(264, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("discPanel.TabConstraints.tabTitle"), discPanel); // NOI18N

        personPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("personPanel.border.title"))); // NOI18N
        personPanel.setMaximumSize(new java.awt.Dimension(32767, 160));
        personPanel.setMinimumSize(new java.awt.Dimension(0, 160));
        personPanel.setName("personPanel"); // NOI18N
        personPanel.setPreferredSize(new java.awt.Dimension(1157, 160));

        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N

        jTextField7.setName("jTextField7"); // NOI18N
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldKeyTyped(evt);
            }
        });

        jTextField8.setName("jTextField8"); // NOI18N
        jTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldKeyTyped(evt);
            }
        });

        jTextField9.setText(resourceMap.getString("jTextField10.text")); // NOI18N
        jTextField9.setName("jTextField9"); // NOI18N
        jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldKeyTyped(evt);
            }
        });

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTextArea5.setColumns(20);
        jTextArea5.setRows(5);
        jTextArea5.setName("jTextArea5"); // NOI18N
        jScrollPane3.setViewportView(jTextArea5);

        jTextField10.setText(resourceMap.getString("jTextField10.text")); // NOI18N
        jTextField10.setName("jTextField10"); // NOI18N
        jTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField10KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout personPanelLayout = new javax.swing.GroupLayout(personPanel);
        personPanel.setLayout(personPanelLayout);
        personPanelLayout.setHorizontalGroup(
            personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addGap(6, 6, 6)
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(558, 558, 558))
        );
        personPanelLayout.setVerticalGroup(
            personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(personPanelLayout.createSequentialGroup()
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(275, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("personPanel.TabConstraints.tabTitle"), personPanel); // NOI18N

        blPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("blPanel.border.title"))); // NOI18N
        blPanel.setMaximumSize(new java.awt.Dimension(32767, 160));
        blPanel.setMinimumSize(new java.awt.Dimension(0, 160));
        blPanel.setName("blPanel"); // NOI18N
        blPanel.setPreferredSize(new java.awt.Dimension(1157, 160));

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jTextArea3.setName("jTextArea3"); // NOI18N
        jScrollPane2.setViewportView(jTextArea3);

        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N

        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N

        jComboBox14.setName("jComboBox14"); // NOI18N

        javax.swing.GroupLayout blPanelLayout = new javax.swing.GroupLayout(blPanel);
        blPanel.setLayout(blPanelLayout);
        blPanelLayout.setHorizontalGroup(
            blPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(blPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(blPanelLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(27, 27, 27)
                        .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(blPanelLayout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap(709, Short.MAX_VALUE))
        );
        blPanelLayout.setVerticalGroup(
            blPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(blPanelLayout.createSequentialGroup()
                .addGroup(blPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(blPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(303, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("blPanel.TabConstraints.tabTitle"), blPanel); // NOI18N

        historyPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("historyPanel.border.title"))); // NOI18N
        historyPanel.setMaximumSize(new java.awt.Dimension(32767, 160));
        historyPanel.setMinimumSize(new java.awt.Dimension(0, 160));
        historyPanel.setName("historyPanel"); // NOI18N
        historyPanel.setPreferredSize(new java.awt.Dimension(1157, 160));

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jTextArea4.setName("jTextArea4"); // NOI18N
        jScrollPane1.setViewportView(jTextArea4);

        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        jDateChooser3.setName("jDateChooser3"); // NOI18N
        jDateChooser3.getDateEditor().setEnabled(false);

        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        jDateChooser2.setName("jDateChooser2"); // NOI18N
        jDateChooser2.getDateEditor().setEnabled(false);

        jDateChooser1.setName("jDateChooser1"); // NOI18N
        jDateChooser1.getDateEditor().setEnabled(false);

        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        jComboBox15.setName("jComboBox15"); // NOI18N

        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N

        jComboBox16.setName("jComboBox16"); // NOI18N

        javax.swing.GroupLayout historyPanelLayout = new javax.swing.GroupLayout(historyPanel);
        historyPanel.setLayout(historyPanelLayout);
        historyPanelLayout.setHorizontalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29)
                    .addComponent(jLabel28)
                    .addComponent(jLabel26)
                    .addComponent(jLabel27)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.Alignment.TRAILING, 0, 427, Short.MAX_VALUE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                    .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(702, 702, 702))
        );
        historyPanelLayout.setVerticalGroup(
            historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(historyPanelLayout.createSequentialGroup()
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(historyPanelLayout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jLabel27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, historyPanelLayout.createSequentialGroup()
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(historyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(247, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("historyPanel.TabConstraints.tabTitle"), historyPanel); // NOI18N

        resultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("resultsPanel.border.title"))); // NOI18N
        resultsPanel.setMaximumSize(new java.awt.Dimension(32767, 160));
        resultsPanel.setMinimumSize(new java.awt.Dimension(0, 160));
        resultsPanel.setName("resultsPanel"); // NOI18N
        resultsPanel.setPreferredSize(new java.awt.Dimension(1157, 160));

        jScrollPane7.setBackground(resourceMap.getColor("jScrollPane7.background")); // NOI18N
        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane7.setName("jScrollPane7"); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Русское название", "Жанр", "Продолжительность", "Год", "Субтитры", "Оценка", "Просмотрен"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable3.setMaximumSize(new java.awt.Dimension(2147483647, 85));
        jTable3.setName("jTable3"); // NOI18N
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(jTable3);

        javax.swing.GroupLayout resultsPanelLayout = new javax.swing.GroupLayout(resultsPanel);
        resultsPanel.setLayout(resultsPanelLayout);
        resultsPanelLayout.setHorizontalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1214, Short.MAX_VALUE)
                .addContainerGap())
        );
        resultsPanelLayout.setVerticalGroup(
            resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab(resourceMap.getString("resultsPanel.TabConstraints.tabTitle"), resultsPanel); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(723, 723, 723)
                        .addComponent(findButton))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1251, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(findButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyTyped
        if (!Character.isLetter(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField7KeyTyped

    private void jTextField10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField10KeyTyped
        if (jTextField10.getText().length() == 20) {
            evt.consume();
        }
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField10KeyTyped

    private void jTextField6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6KeyTyped
        if ((jTextField1.getText().length() >= 2) || (!Character.isDigit(evt.getKeyChar()))) {
            {
                evt.consume();
            }
        }
    }//GEN-LAST:event_jTextField6KeyTyped

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

    private String stringArrayToString(String[] arr) {
        String retStr = "";
        if ((arr != null) && (arr.length != 0)) {
            for (int i = 0; i < arr.length; i++) {
                retStr += arr[i];
                if (i + 1 != arr.length) {
                    retStr += ", ";
                }
            }
        } else {
            retStr = "-";
        }
        return retStr;
    }

    private void writeResults(int type, Records records) {
        List<Record> recs = records.toList();
        ArrayList<Object[]> listOfRows = new ArrayList<Object[]>();
        Film f = null;
        Disc disc = null;
        Person pers = null;
        BlackListRecord blRecord = null;
        HistoryRecord historyRecord = null;
        Object[] row = null;
        Integer[] widths = null;
        String temp;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for (Record rec : recs) {
            switch (type) {
                case 0: // фильм
                    f = (Film) rec;
                    row = new Object[columnNamesFilms.length];
                    row[0] = rec.getID();
                    row[1] = f.getRussianTitle();
                    row[2] = (f.getGenres() != null) ? stringArrayToString(f.getGenres()) : "-";
                    row[3] = (f.getLength() != 0) ? Integer.toString(f.getLength()) : "-";
                    row[4] = (f.getYear() != 0) ? Integer.toString(f.getYear()) : "-";
                    row[5] = (f.getSubtitles() != null) ? stringArrayToString(f.getSubtitles()) : "-";
                    row[6] = (f.getRating() != 0) ? Integer.toString(f.getRating()) : "-";
                    row[7] = (f.isSeen()) ? "да" : "нет";
                    break;
                case 1: // диск
                    disc = (Disc) rec;
                    row = new Object[columnNamesDiscs.length];
                    row[0] = rec.getID();
                    List<Record> films = disc.getFilms().toList();
                    String tmp = "";
                    if (!films.isEmpty()) {
                        tmp += ((Film) films.get(0)).toString();
                    }
                    for (int i = 1; i < films.size(); i++) {
                        tmp += ", " + ((Film) films.get(i)).toString();
                    }
                    row[1] = tmp;
                    row[2] = disc.getFormat().toString();
                    row[3] = (disc.getRegionCode() != 0) ? Integer.toString(disc.getRegionCode()) : "-";
                    row[4] = (disc.isPresented()) ? "да" : "нет";
                    break;
                case 2: // человек
                    pers = (Person) rec;
                    row = new Object[6];
                    row[0] = rec.getID();
                    row[1] = (pers.getLastName().isEmpty()) ? "-" : pers.getLastName();
                    row[2] = (pers.getFirstName().isEmpty()) ? "-" : pers.getFirstName();
                    row[3] = (pers.getSecondName().isEmpty()) ? "-" : pers.getSecondName();
                    row[4] = (pers.getPhone().isEmpty()) ? "-" : pers.getPhone();
                    row[5] = (pers.getComment().isEmpty()) ? "-" : pers.getComment();
                    break;
                case 3: // черный список
                    blRecord = (BlackListRecord) rec;
                    row = new Object[columnNamesBLRecords.length];
                    row[0] = rec.getID();
                    row[1] = blRecord.getPerson().toString();
                    row[2] = (blRecord.getComment().isEmpty()) ? "-" : blRecord.getComment();
                    break;
                case 4: // история
                    historyRecord = (HistoryRecord) rec;
                    row = new Object[columnNamesHistoty.length];
                    row[0] = rec.getID();
                    row[1] = historyRecord.getDisc().toString();
                    row[2] = historyRecord.getPerson().toString();
                    temp = formatter.format(historyRecord.getGiveDate());
                    row[3] = (!temp.equals("1970-01-01")) ? temp : "-";
                    temp = formatter.format(historyRecord.getPromisedDate());
                    row[4] = (!temp.equals("1970-01-01")) ? temp : "-";
                    temp = formatter.format(historyRecord.getReturnDate());
                    row[5] = (!temp.equals("1970-01-01")) ? temp : "-";
                    row[6] = historyRecord.getComment().toString();
                    break;
            }
            listOfRows.add(row);
        }
        Object[][] data = new Object[listOfRows.size()][];
        for (int i = 0; i < listOfRows.size(); i++) {
            data[i] = listOfRows.get(i);
        }
        switch (type) {
            case 0: // фильм
                jTable3.setModel(new DefaultTableModel(data, columnNamesFilms) {

                    Class[] types = new Class[]{
                        java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false, false, false, false
                    };

                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                });
                widths = new Integer[]{40, 150, 350, 115, 50, 180, 60, 80};
                for (int i = 0; i < widths.length; i++) {
                    if (i != 1) {
                        jTable3.getColumnModel().getColumn(i).setMinWidth(widths[i]);
                        jTable3.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
                        jTable3.getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                    }
                }
                jTable3.getColumnModel().getColumn(1).setPreferredWidth(150);
                jTable3.getColumnModel().getColumn(2).setPreferredWidth(350);
                jTable3.getColumnModel().getColumn(2).setMaxWidth(450);
                break;
            case 1: // диск
                jTable3.setModel(new DefaultTableModel(data, columnNamesDiscs) {

                    Class[] types = new Class[]{
                        java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false
                    };

                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                });
                widths = new Integer[]{40, 0, 100, 75, 100};
                for (int i = 0; i < widths.length; i++) {
                    if (i != 1) {
                        jTable3.getColumnModel().getColumn(i).setMinWidth(widths[i]);
                        jTable3.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
                        jTable3.getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                    }
                }
                break;
            case 2: // человек
                jTable3.setModel(new DefaultTableModel(data, columnNamesPersons) {

                    Class[] types = new Class[]{
                        java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false, false
                    };

                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                });
                widths = new Integer[]{40, 200, 200, 200, 150};
                for (int i = 0; i < widths.length; i++) {
                    jTable3.getColumnModel().getColumn(i).setMinWidth(widths[i]);
                    jTable3.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
                    jTable3.getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                }
                break;
            case 3: // черный список
                jTable3.setModel(new DefaultTableModel(data, columnNamesBLRecords) {

                    Class[] types = new Class[]{
                        java.lang.Integer.class, java.lang.String.class, java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false
                    };

                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                });
                jTable3.getColumnModel().getColumn(0).setMinWidth(40);
                jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
                jTable3.getColumnModel().getColumn(0).setMaxWidth(40);
                jTable3.getColumnModel().getColumn(1).setMinWidth(250);
                jTable3.getColumnModel().getColumn(1).setPreferredWidth(350);
                jTable3.getColumnModel().getColumn(1).setMaxWidth(650);
                break;
            case 4: // история
                jTable3.setModel(new DefaultTableModel(data, columnNamesHistoty) {

                    Class[] types = new Class[]{
                        java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
                    };
                    boolean[] canEdit = new boolean[]{
                        false, false, false, false, false, false, false
                    };

                    public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                    }

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit[columnIndex];
                    }
                });
                widths = new Integer[]{40, 200, 200, 100, 100, 100, 100};
                for (int i = 0; i < widths.length; i++) {
                    if (i != 1 && i != 2) {
                        jTable3.getColumnModel().getColumn(i).setMinWidth(widths[i]);
                        jTable3.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
                        jTable3.getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                    }
                }
                jTable3.getColumnModel().getColumn(1).setMaxWidth(400);
                jTable3.getColumnModel().getColumn(2).setMaxWidth(200);
                jTable3.getColumnModel().getColumn(6).setMaxWidth(500);
                break;
        }
    }

    @Action
    public void find() {
        Records records = null;
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0: { // Фильмы
                try {
                    String _russianTitle = jTextField1.getText().trim();
                    String _englishTitle = jTextField2.getText().trim();
                    int _year = 0;
                    String tmpStr = jTextField3.getText().trim();
                    try {
                        if (tmpStr.length() != 0) {
                            _year = Integer.parseInt(tmpStr);
                        }
                    } catch (Exception e) {
                    }
                    int _length = 0;
                    tmpStr = jTextField4.getText().trim();
                    try {
                        if (tmpStr.length() != 0) {
                            _length = Integer.parseInt(tmpStr);
                        }
                    } catch (Exception e) {
                    }
                    String _comment = jTextField5.getText().trim();
                    String _description = jTextArea1.getText().trim();

                    String[] _genres = getDataFromComboboxsAsStrings(jComboBox1, jComboBox2, jComboBox3);
                    String[] _countries = getDataFromComboboxsAsStrings(jComboBox4, jComboBox5, jComboBox6);
                    String[] _subtitles = getDataFromComboboxsAsStrings(jComboBox7, jComboBox8, jComboBox9);
                    String[] _soundLanguages = getDataFromComboboxsAsStrings(jComboBox10, jComboBox11, jComboBox12);

                    Film film = new Film(_russianTitle, _englishTitle, _year, _description, _genres, _countries,
                            _comment, _length, _rating, _subtitles, _soundLanguages, true);

                    records = Managers.getInstance().getFilmsManager().find(film);
                } catch (Exception ex) {
                }
                break;
            }
            case 1: { // Диски
                try {
                    Films films = new Films();
                    for (Record rec : filmRecords) {
                        if (filmsMap.get(rec)) {
                            films.add(rec);
                        }
                    }
                    Format format = Format.valueOf((String) jComboBox13.getSelectedItem());
                    String s = jTextField6.getText().trim();
                    int region = (s.isEmpty()) ? 0 : Integer.parseInt(s);
                    records = Managers.getInstance().getDiscsManager().find(new Disc(films, -1, format, region, true));
                } catch (Exception ex) {
                }
                break;
            }
            case 2: { // Люди
                try {
                    String lastName = jTextField7.getText().trim();
                    String firstName = jTextField8.getText().trim();
                    String secondName = jTextField9.getText().trim();
                    String phone = jTextField10.getText().trim();
                    String comment = jTextArea5.getText().trim();
                    records = Managers.getInstance().getPersManager().find(new Person(lastName, firstName, secondName, phone, comment));
                } catch (Exception ex) {
                }
                break;
            }
            case 3: { // Блэклист
                try {
                    int id = personsMap.get(jComboBox14.getSelectedIndex());
                    String comment = jTextArea3.getText().trim();
                    records = Managers.getInstance().getBlListManager().find(new BlackListRecord(new Person(id), comment));
                } catch (Exception ex) {
                }
                break;
            }
            case 4: { // Журнал
                try {
                    Disc disc = new Disc(discMap.get(jComboBox16.getSelectedIndex()));
                    Person person = new Person(personsMap.get(jComboBox15.getSelectedIndex()));
                    Date date1 = jDateChooser1.getDate();
                    Date date2 = jDateChooser2.getDate();
                    Date date3 = jDateChooser3.getDate();
                    String comment = jTextArea4.getText().trim();
                    records = Managers.getInstance().getHistManager().find(new HistoryRecord(disc, person, date1, date2, date3, comment));
                } catch (Exception ex) {
                    System.out.print(ex.getStackTrace().toString());
                }
                break;
            }
        }
        if ((records != null) && (records.size() > 0)) {
            writeResults(jTabbedPane1.getSelectedIndex(), records);
            jTabbedPane1.setSelectedIndex(5);
        } else {
            jTable3.setModel(new DefaultTableModel(new Object[0][0], new Object[0]));
            JOptionPane.showMessageDialog(null, "Не найдено ни одной записи, удовлетворяющей условиям", "Результат поиска", JOptionPane.OK_OPTION);
        }
    }

    @Action
    public void addButton() {
        try {
            changeMap(jTable2, true);
            updateTables(jTable1, jTable2);




        } catch (Exception ex) {
            Logger.getLogger(DiscView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Action
    public void removeButton() {
        try {
            changeMap(jTable1, false);
            updateTables(jTable1, jTable2);




        } catch (Exception ex) {
            Logger.getLogger(DiscView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void changeMap(JTable table, boolean flag) {
        int id = (Integer) table.getValueAt(table.getSelectedRow(), 0);
        for (Record rec : filmRecords) {
            if (rec.getID() == id) {
                filmsMap.put(rec, flag);
            }
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

    private void updateTables(JTable table1, JTable table2) {
        DefaultTableModel model;
        ArrayList<Object[]> list1 = new ArrayList<Object[]>();
        ArrayList<Object[]> list2 = new ArrayList<Object[]>();
        Object[] row = null;
        for (Record rec : filmRecords) {
            row = new Object[2];
            row[0] = rec.getID();
            row[1] = ((Film) rec).getRussianTitle();
            if (filmsMap.get(rec)) {
                list1.add(row);
            } else {
                list2.add(row);
            }
        }
        Object[][] data1 = new Object[list1.size()][];
        Object[][] data2 = new Object[list2.size()][];
        for (int i = 0; i < list1.size(); i++) {
            data1[i] = list1.get(i);
        }
        for (int i = 0; i < list2.size(); i++) {
            data2[i] = list2.get(i);
        }
        model = new DefaultTableModel(data1, columnNames);
        table1.setModel(model);
        model = new DefaultTableModel(data2, columnNames);
        table2.setModel(model);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JPanel blPanel;
    private javax.swing.JButton delButton;
    private javax.swing.JPanel discPanel;
    private javax.swing.JPanel filmPanel;
    private javax.swing.JButton findButton;
    private javax.swing.JPanel historyPanel;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox10;
    private javax.swing.JComboBox jComboBox11;
    private javax.swing.JComboBox jComboBox12;
    private javax.swing.JComboBox jComboBox13;
    private javax.swing.JComboBox jComboBox14;
    private javax.swing.JComboBox jComboBox15;
    private javax.swing.JComboBox jComboBox16;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JComboBox jComboBox7;
    private javax.swing.JComboBox jComboBox8;
    private javax.swing.JComboBox jComboBox9;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel personPanel;
    private javax.swing.JPanel resultsPanel;
    // End of variables declaration//GEN-END:variables
}
