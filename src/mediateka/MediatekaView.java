/*
 * MediatekaView.java
 */
package mediateka;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionListener;
import mediateka.commands.FindFilmCommand;
import mediateka.datamanagers.Managers;
import mediateka.db.Film;
import mediateka.db.Record;

/**
 * The application's main frame.
 */
public class MediatekaView extends FrameView {

    private static final String appPath = (new File("")).getAbsolutePath();

    private void updateInfo(Film film) {
        if (film != null) {
            String result = "";
            result = film.getRussianTitle();
            jTextField1.setText((result.length() == 0) ? "-" : result);
            jTextField1.setCaretPosition(0);
            result = film.getEnglishTitle();
            jTextField2.setText((result.length() == 0) ? "-" : result);
            jTextField2.setCaretPosition(0);
            result = Integer.toString(film.getLength());
            jTextField3.setText((result.length() == 0) ? "-" : result);
            jTextField3.setCaretPosition(0);
            result = Integer.toString(film.getYear());
            jTextField4.setText((result.length() == 0) ? "-" : result);
            jTextField4.setCaretPosition(0);
            result = film.getComment();
            jTextField5.setText((result.length() == 0) ? "-" : result);
            jTextField5.setCaretPosition(0);
            result = StringArrayToString(film.getGenres());
            jTextField6.setText(result);
            jTextField6.setCaretPosition(0);
            result = StringArrayToString(film.getCountries());
            jTextField7.setText(result);
            jTextField7.setCaretPosition(0);
            result = StringArrayToString(film.getSubtitles());
            jTextField8.setText(result);
            jTextField8.setCaretPosition(0);
            result = StringArrayToString(film.getSoundLanguages());
            jTextField9.setText(result);
            jTextField9.setCaretPosition(0);
            jLabel12.setText(Integer.toString(film.getRating()));
            jLabel14.setText((film.isSeen()) ? "да" : "нет");
            result = film.getDescription();
            jTextArea1.setText((result.length() == 0) ? "-" : result);
            jTextArea1.setCaretPosition(0);
        } else {
            jTextField1.setText("");
            jTextField2.setText("");
            jTextField3.setText("");
            jTextField4.setText("");
            jTextField5.setText("");
            jTextField6.setText("");
            jTextField7.setText("");
            jTextField8.setText("");
            jTextField9.setText("");
            jTextArea1.setText("");
            jLabel12.setText("");
            jLabel14.setText("");
        }
    }

    private String StringArrayToString(String[] arr) {
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

    public MediatekaView(SingleFrameApplication app) {
        super(app);

        initComponents();
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                int filmID = Integer.parseInt((String) (jTable1.getValueAt(jTable1.getSelectedRow(), 0)));
                Film f = (Film) (new FindFilmCommand().execute(filmID));
                //FilmViewNew fv = new FilmViewNew(null, true, f);
                updateInfo(f);
            }
        });


        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
        updateTableFilms();
    }

    private void updateTableFilms() {
        Record[] recs = null;
        try {
            recs = Managers.getInstance().getFilmsManager().getRecords();
        } catch (Exception ex) {
            Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (recs != null) {
//            jTable1.setModel(new javax.swing.table.DefaultTableModel(
//            new Object [][] { {"1", null, null, null, null, null, null, null} },
//            new String [] {
//                "ID", "Русское название", "Жанр", "Продолжительность", "Год", "Субтитры", "Оценка", "Просмотрен"
//            }
//        )
//            DefaultTableModel dtm = (DefaultTableModel) (jTable1.getModel());
//            for (Record rec : recs) {
//                dtm.addRow(new String[]{"2", "3", "4", "5", "6"});
//            }
//            jTable1.setModel(dtm);
        }
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = MediatekaApp.getApplication().getMainFrame();
            aboutBox = new MediatekaAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        MediatekaApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        mainPanel = new javax.swing.JPanel();
        standartToolBar = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        filmPane = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        diskPane = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        personPane = new javax.swing.JSplitPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jTextField40 = new javax.swing.JTextField();
        jTextField41 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jTextField42 = new javax.swing.JTextField();
        jTextField43 = new javax.swing.JTextField();
        jTextField44 = new javax.swing.JTextField();
        jTextField45 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        blackListPane = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jTextField20 = new javax.swing.JTextField();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField23 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTextField24 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        historyPane = new javax.swing.JSplitPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jTextField28 = new javax.swing.JTextField();
        jTextField29 = new javax.swing.JTextField();
        jTextField30 = new javax.swing.JTextField();
        jTextField31 = new javax.swing.JTextField();
        jTextField32 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jTextField33 = new javax.swing.JTextField();
        jTextField34 = new javax.swing.JTextField();
        jTextField35 = new javax.swing.JTextField();
        jTextField36 = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenuItem = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        createMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        importMenuItem = new javax.swing.JMenuItem();
        exportMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        printMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        viewMenuItem = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        searchMenu = new javax.swing.JMenu();
        findMenuItem = new javax.swing.JMenuItem();
        serviceMenuItem = new javax.swing.JMenu();
        preferencesMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(mediateka.MediatekaApp.class).getContext().getResourceMap(MediatekaView.class);
        mainPanel.setFont(resourceMap.getFont("mainPanel.font")); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N

        standartToolBar.setRollover(true);
        standartToolBar.setName("standartToolBar"); // NOI18N

        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(mediateka.MediatekaApp.class).getContext().getActionMap(MediatekaView.class, this);
        jButton2.setAction(actionMap.get("addFilm")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton2);

        jButton3.setAction(actionMap.get("update")); // NOI18N
        jButton3.setIcon(null);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton3);

        jTabbedPane1.setMaximumSize(new java.awt.Dimension(32767, 100));
        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        filmPane.setDividerLocation(800);
        filmPane.setDividerSize(3);
        filmPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        filmPane.setResizeWeight(1.0);
        filmPane.setName("filmPane"); // NOI18N

        jScrollPane1.setBackground(resourceMap.getColor("jScrollPane1.background")); // NOI18N
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Русское название", "Жанр", "Продолжительность", "Год", "Субтитры", "Оценка", "Просмотрен"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
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
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable1.setComponentPopupMenu(jPopupMenu1);
        jTable1.setMaximumSize(new java.awt.Dimension(2147483647, 85));
        jTable1.setName("jTable1"); // NOI18N
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jTable1, org.jdesktop.beansbinding.ELProperty.create("true"), jTable1, org.jdesktop.beansbinding.BeanProperty.create("autoCreateRowSorter"));
        bindingGroup.addBinding(binding);

        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setMinWidth(40);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(40);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(2).setMinWidth(150);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(220);
        jTable1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
        jTable1.getColumnModel().getColumn(3).setMinWidth(115);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(115);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(115);
        jTable1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title7")); // NOI18N
        jTable1.getColumnModel().getColumn(4).setMinWidth(50);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable1.getColumnModel().getColumn(5).setMinWidth(150);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title9")); // NOI18N
        jTable1.getColumnModel().getColumn(6).setMinWidth(40);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(40);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(40);
        jTable1.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable1.columnModel.title8")); // NOI18N
        jTable1.getColumnModel().getColumn(7).setMinWidth(80);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(7).setMaxWidth(80);
        jTable1.getColumnModel().getColumn(7).setHeaderValue(resourceMap.getString("jTable1.columnModel.title11")); // NOI18N

        filmPane.setLeftComponent(jScrollPane1);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setMaximumSize(new java.awt.Dimension(32767, 160));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 160));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1157, 160));

        jLabel1.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jTextField1.setBackground(resourceMap.getColor("jTextField5.background")); // NOI18N
        jTextField1.setEditable(false);
        jTextField1.setText(resourceMap.getString("jTextField1.text")); // NOI18N
        jTextField1.setName("jTextField1"); // NOI18N

        jTextField2.setBackground(resourceMap.getColor("jTextField5.background")); // NOI18N
        jTextField2.setEditable(false);
        jTextField2.setText(resourceMap.getString("jTextField2.text")); // NOI18N
        jTextField2.setName("jTextField2"); // NOI18N

        jTextField3.setBackground(resourceMap.getColor("jTextField5.background")); // NOI18N
        jTextField3.setEditable(false);
        jTextField3.setText(resourceMap.getString("jTextField3.text")); // NOI18N
        jTextField3.setName("jTextField3"); // NOI18N

        jTextField4.setBackground(resourceMap.getColor("jTextField5.background")); // NOI18N
        jTextField4.setEditable(false);
        jTextField4.setText(resourceMap.getString("jTextField4.text")); // NOI18N
        jTextField4.setName("jTextField4"); // NOI18N

        jTextField5.setBackground(resourceMap.getColor("jTextField5.background")); // NOI18N
        jTextField5.setEditable(false);
        jTextField5.setText(resourceMap.getString("jTextField5.text")); // NOI18N
        jTextField5.setName("jTextField5"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel7.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jLabel10.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel10.setText(resourceMap.getString("jLabel10.text")); // NOI18N
        jLabel10.setName("jLabel10"); // NOI18N

        jTextField6.setBackground(resourceMap.getColor("jTextField5.background")); // NOI18N
        jTextField6.setEditable(false);
        jTextField6.setText(resourceMap.getString("jTextField6.text")); // NOI18N
        jTextField6.setName("jTextField6"); // NOI18N

        jTextField7.setBackground(resourceMap.getColor("jTextField5.background")); // NOI18N
        jTextField7.setEditable(false);
        jTextField7.setText(resourceMap.getString("jTextField7.text")); // NOI18N
        jTextField7.setName("jTextField7"); // NOI18N

        jTextField8.setBackground(resourceMap.getColor("jTextField5.background")); // NOI18N
        jTextField8.setEditable(false);
        jTextField8.setText(resourceMap.getString("jTextField8.text")); // NOI18N
        jTextField8.setName("jTextField8"); // NOI18N

        jTextField9.setBackground(resourceMap.getColor("jTextField5.background")); // NOI18N
        jTextField9.setEditable(false);
        jTextField9.setText(resourceMap.getString("jTextField9.text")); // NOI18N
        jTextField9.setName("jTextField9"); // NOI18N

        jLabel11.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel11.setText(resourceMap.getString("jLabel11.text")); // NOI18N
        jLabel11.setName("jLabel11"); // NOI18N

        jScrollPane6.setName("jScrollPane6"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(resourceMap.getFont("jTextArea1.font")); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setMaximumSize(new java.awt.Dimension(2147483647, 74));
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane6.setViewportView(jTextArea1);

        jLabel12.setText(resourceMap.getString("jLabel12.text")); // NOI18N
        jLabel12.setName("jLabel12"); // NOI18N

        jLabel13.setFont(resourceMap.getFont("jLabel10.font")); // NOI18N
        jLabel13.setText(resourceMap.getString("jLabel13.text")); // NOI18N
        jLabel13.setName("jLabel13"); // NOI18N

        jLabel14.setText(resourceMap.getString("jLabel14.text")); // NOI18N
        jLabel14.setName("jLabel14"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)))
                    .addComponent(jScrollPane6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        filmPane.setBottomComponent(jPanel1);

        jTabbedPane1.addTab(resourceMap.getString("filmPane.TabConstraints.tabTitle"), filmPane); // NOI18N

        diskPane.setDividerLocation(800);
        diskPane.setDividerSize(3);
        diskPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        diskPane.setResizeWeight(1.0);
        diskPane.setName("diskPane"); // NOI18N

        jScrollPane2.setBackground(resourceMap.getColor("jScrollPane2.background")); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "2012", "2012", "2009", "Согласно календарю индейцев Майя, в 2012 году планеты солнечной системы окажутся на одной линии друг с другом, что приведет к глобальным природным катаклизмам: сильнейшие землетрясения, цунами и извержения вулканов превратят страны и целые континенты в руины. Недавно ученые подтвердили, что этот миф может стать реальностью.", "фантастика, боевик, триллер, драма, приключения", "США", "Мой любимый фильм", "158 мин", "9/10", "нет", new Boolean(true)},
                {"2", "От заката до рассвета", "fhj", "f", "jhg", null, null, null, null, null, null, null},
                {"3", "Послезавтра", "cf", "f", "jhg", null, null, null, null, null, null, null},
                {"4", "Война миров", "g", "hjgjh", "fg", null, null, null, null, null, null, null},
                {"5", "Приключения шурика", "f", "jhg", "kg", null, null, null, null, null, null, null},
                {"6", "Карты, деньги, два ствола", null, null, null, null, null, null, null, null, null, null},
                {"7", "Матрица (1 часть)", null, null, null, null, null, null, null, null, null, null},
                {"8", "Знамение", null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Русское название", "Англ. название", "Год", "Описание", "Жанр", "Страна(ы)", "Комментарий", "Продолжительность", "Оценка", "Субтитры", "Просмотрен"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable2.setComponentPopupMenu(jPopupMenu1);
        jTable2.setMaximumSize(new java.awt.Dimension(2147483647, 85));
        jTable2.setName("jTable2"); // NOI18N
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getColumn(0).setMinWidth(40);
        jTable2.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable2.getColumnModel().getColumn(0).setMaxWidth(40);
        jTable2.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable2.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable2.getColumnModel().getColumn(3).setMinWidth(50);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(50);
        jTable2.getColumnModel().getColumn(3).setMaxWidth(50);
        jTable2.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable2.getColumnModel().getColumn(5).setMinWidth(150);
        jTable2.getColumnModel().getColumn(5).setPreferredWidth(220);
        jTable2.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
        jTable2.getColumnModel().getColumn(8).setMinWidth(115);
        jTable2.getColumnModel().getColumn(8).setPreferredWidth(115);
        jTable2.getColumnModel().getColumn(8).setMaxWidth(115);
        jTable2.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTable1.columnModel.title7")); // NOI18N
        jTable2.getColumnModel().getColumn(9).setMinWidth(40);
        jTable2.getColumnModel().getColumn(9).setPreferredWidth(40);
        jTable2.getColumnModel().getColumn(9).setMaxWidth(40);
        jTable2.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTable1.columnModel.title8")); // NOI18N
        jTable2.getColumnModel().getColumn(10).setMinWidth(150);
        jTable2.getColumnModel().getColumn(10).setPreferredWidth(200);
        jTable2.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("jTable1.columnModel.title9")); // NOI18N
        jTable2.getColumnModel().getColumn(11).setMinWidth(80);
        jTable2.getColumnModel().getColumn(11).setPreferredWidth(80);
        jTable2.getColumnModel().getColumn(11).setMaxWidth(80);
        jTable2.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("jTable1.columnModel.title11")); // NOI18N

        diskPane.setLeftComponent(jScrollPane2);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setMaximumSize(new java.awt.Dimension(32767, 160));
        jPanel2.setMinimumSize(new java.awt.Dimension(0, 160));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(1157, 160));

        jLabel15.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel15.setText(resourceMap.getString("jLabel15.text")); // NOI18N
        jLabel15.setName("jLabel15"); // NOI18N

        jLabel16.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel16.setText(resourceMap.getString("jLabel16.text")); // NOI18N
        jLabel16.setName("jLabel16"); // NOI18N

        jLabel17.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel17.setText(resourceMap.getString("jLabel17.text")); // NOI18N
        jLabel17.setName("jLabel17"); // NOI18N

        jLabel18.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel18.setText(resourceMap.getString("jLabel18.text")); // NOI18N
        jLabel18.setName("jLabel18"); // NOI18N

        jLabel19.setFont(resourceMap.getFont("jLabel15.font")); // NOI18N
        jLabel19.setText(resourceMap.getString("jLabel19.text")); // NOI18N
        jLabel19.setName("jLabel19"); // NOI18N

        jTextField10.setBackground(resourceMap.getColor("jTextField14.background")); // NOI18N
        jTextField10.setEditable(false);
        jTextField10.setName("jTextField10"); // NOI18N

        jTextField11.setBackground(resourceMap.getColor("jTextField14.background")); // NOI18N
        jTextField11.setEditable(false);
        jTextField11.setName("jTextField11"); // NOI18N

        jTextField12.setBackground(resourceMap.getColor("jTextField14.background")); // NOI18N
        jTextField12.setEditable(false);
        jTextField12.setName("jTextField12"); // NOI18N

        jTextField13.setBackground(resourceMap.getColor("jTextField14.background")); // NOI18N
        jTextField13.setEditable(false);
        jTextField13.setName("jTextField13"); // NOI18N

        jTextField14.setBackground(resourceMap.getColor("jTextField14.background")); // NOI18N
        jTextField14.setEditable(false);
        jTextField14.setName("jTextField14"); // NOI18N

        jLabel20.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel20.setText(resourceMap.getString("jLabel20.text")); // NOI18N
        jLabel20.setName("jLabel20"); // NOI18N

        jLabel21.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel21.setText(resourceMap.getString("jLabel21.text")); // NOI18N
        jLabel21.setName("jLabel21"); // NOI18N

        jLabel22.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel22.setText(resourceMap.getString("jLabel22.text")); // NOI18N
        jLabel22.setName("jLabel22"); // NOI18N

        jLabel23.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel23.setText(resourceMap.getString("jLabel23.text")); // NOI18N
        jLabel23.setName("jLabel23"); // NOI18N

        jLabel24.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel24.setText(resourceMap.getString("jLabel24.text")); // NOI18N
        jLabel24.setName("jLabel24"); // NOI18N

        jTextField15.setBackground(resourceMap.getColor("jTextField14.background")); // NOI18N
        jTextField15.setEditable(false);
        jTextField15.setName("jTextField15"); // NOI18N

        jTextField16.setBackground(resourceMap.getColor("jTextField14.background")); // NOI18N
        jTextField16.setEditable(false);
        jTextField16.setName("jTextField16"); // NOI18N

        jTextField17.setBackground(resourceMap.getColor("jTextField14.background")); // NOI18N
        jTextField17.setEditable(false);
        jTextField17.setName("jTextField17"); // NOI18N

        jTextField18.setBackground(resourceMap.getColor("jTextField14.background")); // NOI18N
        jTextField18.setEditable(false);
        jTextField18.setName("jTextField18"); // NOI18N

        jLabel25.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel25.setText(resourceMap.getString("jLabel25.text")); // NOI18N
        jLabel25.setName("jLabel25"); // NOI18N

        jScrollPane7.setName("jScrollPane7"); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setEditable(false);
        jTextArea2.setFont(resourceMap.getFont("jTextArea2.font")); // NOI18N
        jTextArea2.setRows(5);
        jTextArea2.setMaximumSize(new java.awt.Dimension(2147483647, 74));
        jTextArea2.setName("jTextArea2"); // NOI18N
        jScrollPane7.setViewportView(jTextArea2);

        jLabel26.setText(resourceMap.getString("jLabel26.text")); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        jLabel27.setFont(resourceMap.getFont("jLabel20.font")); // NOI18N
        jLabel27.setText(resourceMap.getString("jLabel27.text")); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        jLabel28.setText(resourceMap.getString("jLabel28.text")); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel23)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel25))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel28)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22)
                            .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23)
                            .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)))
                    .addComponent(jScrollPane7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        diskPane.setBottomComponent(jPanel2);
        jPanel2.getAccessibleContext().setAccessibleName(resourceMap.getString("jPanel2.AccessibleContext.accessibleName")); // NOI18N

        jTabbedPane1.addTab(resourceMap.getString("diskPane.TabConstraints.tabTitle"), diskPane); // NOI18N

        personPane.setDividerLocation(800);
        personPane.setDividerSize(3);
        personPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        personPane.setResizeWeight(1.0);
        personPane.setName("personPane"); // NOI18N

        jScrollPane5.setBackground(resourceMap.getColor("jScrollPane5.background")); // NOI18N
        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "2012", "2012", "2009", "Согласно календарю индейцев Майя, в 2012 году планеты солнечной системы окажутся на одной линии друг с другом, что приведет к глобальным природным катаклизмам: сильнейшие землетрясения, цунами и извержения вулканов превратят страны и целые континенты в руины. Недавно ученые подтвердили, что этот миф может стать реальностью.", "фантастика, боевик, триллер, драма, приключения", "США", "Мой любимый фильм", "158 мин", "9/10", "нет", new Boolean(true)},
                {"2", "От заката до рассвета", "fhj", "f", "jhg", null, null, null, null, null, null, null},
                {"3", "Послезавтра", "cf", "f", "jhg", null, null, null, null, null, null, null},
                {"4", "Война миров", "g", "hjgjh", "fg", null, null, null, null, null, null, null},
                {"5", "Приключения шурика", "f", "jhg", "kg", null, null, null, null, null, null, null},
                {"6", "Карты, деньги, два ствола", null, null, null, null, null, null, null, null, null, null},
                {"7", "Матрица (1 часть)", null, null, null, null, null, null, null, null, null, null},
                {"8", "Знамение", null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Русское название", "Англ. название", "Год", "Описание", "Жанр", "Страна(ы)", "Комментарий", "Продолжительность", "Оценка", "Субтитры", "Просмотрен"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable5.setComponentPopupMenu(jPopupMenu1);
        jTable5.setMaximumSize(new java.awt.Dimension(2147483647, 85));
        jTable5.setName("jTable5"); // NOI18N
        jTable5.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable5.getTableHeader().setReorderingAllowed(false);
        jTable5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable5MouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jTable5);
        jTable5.getColumnModel().getColumn(0).setMinWidth(40);
        jTable5.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable5.getColumnModel().getColumn(0).setMaxWidth(40);
        jTable5.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable5.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable5.getColumnModel().getColumn(3).setMinWidth(50);
        jTable5.getColumnModel().getColumn(3).setPreferredWidth(50);
        jTable5.getColumnModel().getColumn(3).setMaxWidth(50);
        jTable5.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable5.getColumnModel().getColumn(5).setMinWidth(150);
        jTable5.getColumnModel().getColumn(5).setPreferredWidth(220);
        jTable5.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
        jTable5.getColumnModel().getColumn(8).setMinWidth(115);
        jTable5.getColumnModel().getColumn(8).setPreferredWidth(115);
        jTable5.getColumnModel().getColumn(8).setMaxWidth(115);
        jTable5.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTable1.columnModel.title7")); // NOI18N
        jTable5.getColumnModel().getColumn(9).setMinWidth(40);
        jTable5.getColumnModel().getColumn(9).setPreferredWidth(40);
        jTable5.getColumnModel().getColumn(9).setMaxWidth(40);
        jTable5.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTable1.columnModel.title8")); // NOI18N
        jTable5.getColumnModel().getColumn(10).setMinWidth(150);
        jTable5.getColumnModel().getColumn(10).setPreferredWidth(200);
        jTable5.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("jTable1.columnModel.title9")); // NOI18N
        jTable5.getColumnModel().getColumn(11).setMinWidth(80);
        jTable5.getColumnModel().getColumn(11).setPreferredWidth(80);
        jTable5.getColumnModel().getColumn(11).setMaxWidth(80);
        jTable5.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("jTable1.columnModel.title11")); // NOI18N

        personPane.setLeftComponent(jScrollPane5);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel5.border.title"))); // NOI18N
        jPanel5.setMaximumSize(new java.awt.Dimension(32767, 160));
        jPanel5.setMinimumSize(new java.awt.Dimension(0, 160));
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(1157, 160));

        jLabel57.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel57.setText(resourceMap.getString("jLabel57.text")); // NOI18N
        jLabel57.setName("jLabel57"); // NOI18N

        jLabel58.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel58.setText(resourceMap.getString("jLabel58.text")); // NOI18N
        jLabel58.setName("jLabel58"); // NOI18N

        jLabel59.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel59.setText(resourceMap.getString("jLabel59.text")); // NOI18N
        jLabel59.setName("jLabel59"); // NOI18N

        jLabel60.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel60.setText(resourceMap.getString("jLabel60.text")); // NOI18N
        jLabel60.setName("jLabel60"); // NOI18N

        jLabel61.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel61.setText(resourceMap.getString("jLabel61.text")); // NOI18N
        jLabel61.setName("jLabel61"); // NOI18N

        jTextField37.setBackground(resourceMap.getColor("jTextField45.background")); // NOI18N
        jTextField37.setEditable(false);
        jTextField37.setName("jTextField37"); // NOI18N

        jTextField38.setBackground(resourceMap.getColor("jTextField45.background")); // NOI18N
        jTextField38.setEditable(false);
        jTextField38.setName("jTextField38"); // NOI18N

        jTextField39.setBackground(resourceMap.getColor("jTextField45.background")); // NOI18N
        jTextField39.setEditable(false);
        jTextField39.setName("jTextField39"); // NOI18N

        jTextField40.setBackground(resourceMap.getColor("jTextField45.background")); // NOI18N
        jTextField40.setEditable(false);
        jTextField40.setName("jTextField40"); // NOI18N

        jTextField41.setBackground(resourceMap.getColor("jTextField45.background")); // NOI18N
        jTextField41.setEditable(false);
        jTextField41.setName("jTextField41"); // NOI18N

        jLabel62.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel62.setText(resourceMap.getString("jLabel62.text")); // NOI18N
        jLabel62.setName("jLabel62"); // NOI18N

        jLabel63.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel63.setText(resourceMap.getString("jLabel63.text")); // NOI18N
        jLabel63.setName("jLabel63"); // NOI18N

        jLabel64.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel64.setText(resourceMap.getString("jLabel64.text")); // NOI18N
        jLabel64.setName("jLabel64"); // NOI18N

        jLabel65.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel65.setText(resourceMap.getString("jLabel65.text")); // NOI18N
        jLabel65.setName("jLabel65"); // NOI18N

        jLabel66.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel66.setText(resourceMap.getString("jLabel66.text")); // NOI18N
        jLabel66.setName("jLabel66"); // NOI18N

        jTextField42.setBackground(resourceMap.getColor("jTextField45.background")); // NOI18N
        jTextField42.setEditable(false);
        jTextField42.setName("jTextField42"); // NOI18N

        jTextField43.setBackground(resourceMap.getColor("jTextField45.background")); // NOI18N
        jTextField43.setEditable(false);
        jTextField43.setName("jTextField43"); // NOI18N

        jTextField44.setBackground(resourceMap.getColor("jTextField45.background")); // NOI18N
        jTextField44.setEditable(false);
        jTextField44.setName("jTextField44"); // NOI18N

        jTextField45.setBackground(resourceMap.getColor("jTextField45.background")); // NOI18N
        jTextField45.setEditable(false);
        jTextField45.setName("jTextField45"); // NOI18N

        jLabel67.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel67.setText(resourceMap.getString("jLabel67.text")); // NOI18N
        jLabel67.setName("jLabel67"); // NOI18N

        jScrollPane10.setName("jScrollPane10"); // NOI18N

        jTextArea5.setColumns(20);
        jTextArea5.setEditable(false);
        jTextArea5.setFont(resourceMap.getFont("jTextArea5.font")); // NOI18N
        jTextArea5.setRows(5);
        jTextArea5.setMaximumSize(new java.awt.Dimension(2147483647, 74));
        jTextArea5.setName("jTextArea5"); // NOI18N
        jScrollPane10.setViewportView(jTextArea5);

        jLabel68.setText(resourceMap.getString("jLabel68.text")); // NOI18N
        jLabel68.setName("jLabel68"); // NOI18N

        jLabel69.setFont(resourceMap.getFont("jLabel57.font")); // NOI18N
        jLabel69.setText(resourceMap.getString("jLabel69.text")); // NOI18N
        jLabel69.setName("jLabel69"); // NOI18N

        jLabel70.setText(resourceMap.getString("jLabel70.text")); // NOI18N
        jLabel70.setName("jLabel70"); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel61)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel59)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel58)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel57)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64)
                    .addComponent(jLabel62)
                    .addComponent(jLabel63)
                    .addComponent(jLabel65)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel68)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel67))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel69)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel70)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel62)
                    .addComponent(jTextField42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58)
                            .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel63)
                            .addComponent(jTextField43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel59)
                            .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel64)
                            .addComponent(jTextField44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel60)
                            .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel65)
                            .addComponent(jTextField45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(jTextField41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel66)
                            .addComponent(jLabel68)
                            .addComponent(jLabel69)
                            .addComponent(jLabel70)))
                    .addComponent(jScrollPane10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        personPane.setBottomComponent(jPanel5);
        jPanel5.getAccessibleContext().setAccessibleName(resourceMap.getString("jPanel5.AccessibleContext.accessibleName")); // NOI18N

        jTabbedPane1.addTab(resourceMap.getString("personPane.TabConstraints.tabTitle"), personPane); // NOI18N

        blackListPane.setDividerLocation(800);
        blackListPane.setDividerSize(3);
        blackListPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        blackListPane.setResizeWeight(1.0);
        blackListPane.setName("blackListPane"); // NOI18N

        jScrollPane3.setBackground(resourceMap.getColor("jScrollPane3.background")); // NOI18N
        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "2012", "2012", "2009", "Согласно календарю индейцев Майя, в 2012 году планеты солнечной системы окажутся на одной линии друг с другом, что приведет к глобальным природным катаклизмам: сильнейшие землетрясения, цунами и извержения вулканов превратят страны и целые континенты в руины. Недавно ученые подтвердили, что этот миф может стать реальностью.", "фантастика, боевик, триллер, драма, приключения", "США", "Мой любимый фильм", "158 мин", "9/10", "нет", new Boolean(true)},
                {"2", "От заката до рассвета", "fhj", "f", "jhg", null, null, null, null, null, null, null},
                {"3", "Послезавтра", "cf", "f", "jhg", null, null, null, null, null, null, null},
                {"4", "Война миров", "g", "hjgjh", "fg", null, null, null, null, null, null, null},
                {"5", "Приключения шурика", "f", "jhg", "kg", null, null, null, null, null, null, null},
                {"6", "Карты, деньги, два ствола", null, null, null, null, null, null, null, null, null, null},
                {"7", "Матрица (1 часть)", null, null, null, null, null, null, null, null, null, null},
                {"8", "Знамение", null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Русское название", "Англ. название", "Год", "Описание", "Жанр", "Страна(ы)", "Комментарий", "Продолжительность", "Оценка", "Субтитры", "Просмотрен"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable3.setComponentPopupMenu(jPopupMenu1);
        jTable3.setMaximumSize(new java.awt.Dimension(2147483647, 85));
        jTable3.setName("jTable3"); // NOI18N
        jTable3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable3.getTableHeader().setReorderingAllowed(false);
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);
        jTable3.getColumnModel().getColumn(0).setMinWidth(40);
        jTable3.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable3.getColumnModel().getColumn(0).setMaxWidth(40);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable3.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable3.getColumnModel().getColumn(3).setMinWidth(50);
        jTable3.getColumnModel().getColumn(3).setPreferredWidth(50);
        jTable3.getColumnModel().getColumn(3).setMaxWidth(50);
        jTable3.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable3.getColumnModel().getColumn(5).setMinWidth(150);
        jTable3.getColumnModel().getColumn(5).setPreferredWidth(220);
        jTable3.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
        jTable3.getColumnModel().getColumn(8).setMinWidth(115);
        jTable3.getColumnModel().getColumn(8).setPreferredWidth(115);
        jTable3.getColumnModel().getColumn(8).setMaxWidth(115);
        jTable3.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTable1.columnModel.title7")); // NOI18N
        jTable3.getColumnModel().getColumn(9).setMinWidth(40);
        jTable3.getColumnModel().getColumn(9).setPreferredWidth(40);
        jTable3.getColumnModel().getColumn(9).setMaxWidth(40);
        jTable3.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTable1.columnModel.title8")); // NOI18N
        jTable3.getColumnModel().getColumn(10).setMinWidth(150);
        jTable3.getColumnModel().getColumn(10).setPreferredWidth(200);
        jTable3.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("jTable1.columnModel.title9")); // NOI18N
        jTable3.getColumnModel().getColumn(11).setMinWidth(80);
        jTable3.getColumnModel().getColumn(11).setPreferredWidth(80);
        jTable3.getColumnModel().getColumn(11).setMaxWidth(80);
        jTable3.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("jTable1.columnModel.title11")); // NOI18N

        blackListPane.setLeftComponent(jScrollPane3);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel3.border.title"))); // NOI18N
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 160));
        jPanel3.setMinimumSize(new java.awt.Dimension(0, 160));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(1157, 160));

        jLabel29.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel29.setText(resourceMap.getString("jLabel29.text")); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        jLabel30.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel30.setText(resourceMap.getString("jLabel30.text")); // NOI18N
        jLabel30.setName("jLabel30"); // NOI18N

        jLabel31.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel31.setText(resourceMap.getString("jLabel31.text")); // NOI18N
        jLabel31.setName("jLabel31"); // NOI18N

        jLabel32.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel32.setText(resourceMap.getString("jLabel32.text")); // NOI18N
        jLabel32.setName("jLabel32"); // NOI18N

        jLabel33.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel33.setText(resourceMap.getString("jLabel33.text")); // NOI18N
        jLabel33.setName("jLabel33"); // NOI18N

        jTextField19.setBackground(resourceMap.getColor("jTextField23.background")); // NOI18N
        jTextField19.setEditable(false);
        jTextField19.setName("jTextField19"); // NOI18N

        jTextField20.setBackground(resourceMap.getColor("jTextField23.background")); // NOI18N
        jTextField20.setEditable(false);
        jTextField20.setName("jTextField20"); // NOI18N

        jTextField21.setBackground(resourceMap.getColor("jTextField23.background")); // NOI18N
        jTextField21.setEditable(false);
        jTextField21.setName("jTextField21"); // NOI18N

        jTextField22.setBackground(resourceMap.getColor("jTextField23.background")); // NOI18N
        jTextField22.setEditable(false);
        jTextField22.setName("jTextField22"); // NOI18N

        jTextField23.setBackground(resourceMap.getColor("jTextField23.background")); // NOI18N
        jTextField23.setEditable(false);
        jTextField23.setName("jTextField23"); // NOI18N

        jLabel34.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel34.setText(resourceMap.getString("jLabel34.text")); // NOI18N
        jLabel34.setName("jLabel34"); // NOI18N

        jLabel35.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel35.setText(resourceMap.getString("jLabel35.text")); // NOI18N
        jLabel35.setName("jLabel35"); // NOI18N

        jLabel36.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel36.setText(resourceMap.getString("jLabel36.text")); // NOI18N
        jLabel36.setName("jLabel36"); // NOI18N

        jLabel37.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel37.setText(resourceMap.getString("jLabel37.text")); // NOI18N
        jLabel37.setName("jLabel37"); // NOI18N

        jLabel38.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel38.setText(resourceMap.getString("jLabel38.text")); // NOI18N
        jLabel38.setName("jLabel38"); // NOI18N

        jTextField24.setBackground(resourceMap.getColor("jTextField23.background")); // NOI18N
        jTextField24.setEditable(false);
        jTextField24.setName("jTextField24"); // NOI18N

        jTextField25.setBackground(resourceMap.getColor("jTextField23.background")); // NOI18N
        jTextField25.setEditable(false);
        jTextField25.setName("jTextField25"); // NOI18N

        jTextField26.setBackground(resourceMap.getColor("jTextField23.background")); // NOI18N
        jTextField26.setEditable(false);
        jTextField26.setName("jTextField26"); // NOI18N

        jTextField27.setBackground(resourceMap.getColor("jTextField23.background")); // NOI18N
        jTextField27.setEditable(false);
        jTextField27.setName("jTextField27"); // NOI18N

        jLabel39.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel39.setText(resourceMap.getString("jLabel39.text")); // NOI18N
        jLabel39.setName("jLabel39"); // NOI18N

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        jTextArea3.setColumns(20);
        jTextArea3.setEditable(false);
        jTextArea3.setFont(resourceMap.getFont("jTextArea3.font")); // NOI18N
        jTextArea3.setRows(5);
        jTextArea3.setMaximumSize(new java.awt.Dimension(2147483647, 74));
        jTextArea3.setName("jTextArea3"); // NOI18N
        jScrollPane8.setViewportView(jTextArea3);

        jLabel40.setText(resourceMap.getString("jLabel40.text")); // NOI18N
        jLabel40.setName("jLabel40"); // NOI18N

        jLabel41.setFont(resourceMap.getFont("jLabel29.font")); // NOI18N
        jLabel41.setText(resourceMap.getString("jLabel41.text")); // NOI18N
        jLabel41.setName("jLabel41"); // NOI18N

        jLabel42.setText(resourceMap.getString("jLabel42.text")); // NOI18N
        jLabel42.setName("jLabel42"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35)
                    .addComponent(jLabel37)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel40)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel39))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel42)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36)
                            .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel37)
                            .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(jTextField23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel38)
                            .addComponent(jLabel40)
                            .addComponent(jLabel41)
                            .addComponent(jLabel42)))
                    .addComponent(jScrollPane8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        blackListPane.setBottomComponent(jPanel3);

        jTabbedPane1.addTab(resourceMap.getString("blackListPane.TabConstraints.tabTitle"), blackListPane); // NOI18N

        historyPane.setDividerLocation(800);
        historyPane.setDividerSize(3);
        historyPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        historyPane.setResizeWeight(1.0);
        historyPane.setName("historyPane"); // NOI18N

        jScrollPane4.setBackground(resourceMap.getColor("jScrollPane4.background")); // NOI18N
        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "2012", "2012", "2009", "Согласно календарю индейцев Майя, в 2012 году планеты солнечной системы окажутся на одной линии друг с другом, что приведет к глобальным природным катаклизмам: сильнейшие землетрясения, цунами и извержения вулканов превратят страны и целые континенты в руины. Недавно ученые подтвердили, что этот миф может стать реальностью.", "фантастика, боевик, триллер, драма, приключения", "США", "Мой любимый фильм", "158 мин", "9/10", "нет", new Boolean(true)},
                {"2", "От заката до рассвета", "fhj", "f", "jhg", null, null, null, null, null, null, null},
                {"3", "Послезавтра", "cf", "f", "jhg", null, null, null, null, null, null, null},
                {"4", "Война миров", "g", "hjgjh", "fg", null, null, null, null, null, null, null},
                {"5", "Приключения шурика", "f", "jhg", "kg", null, null, null, null, null, null, null},
                {"6", "Карты, деньги, два ствола", null, null, null, null, null, null, null, null, null, null},
                {"7", "Матрица (1 часть)", null, null, null, null, null, null, null, null, null, null},
                {"8", "Знамение", null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Русское название", "Англ. название", "Год", "Описание", "Жанр", "Страна(ы)", "Комментарий", "Продолжительность", "Оценка", "Субтитры", "Просмотрен"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        jTable4.setComponentPopupMenu(jPopupMenu1);
        jTable4.setMaximumSize(new java.awt.Dimension(2147483647, 85));
        jTable4.setName("jTable4"); // NOI18N
        jTable4.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable4.getTableHeader().setReorderingAllowed(false);
        jTable4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4);
        jTable4.getColumnModel().getColumn(0).setMinWidth(40);
        jTable4.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable4.getColumnModel().getColumn(0).setMaxWidth(40);
        jTable4.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable4.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable4.getColumnModel().getColumn(3).setMinWidth(50);
        jTable4.getColumnModel().getColumn(3).setPreferredWidth(50);
        jTable4.getColumnModel().getColumn(3).setMaxWidth(50);
        jTable4.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable4.getColumnModel().getColumn(5).setMinWidth(150);
        jTable4.getColumnModel().getColumn(5).setPreferredWidth(220);
        jTable4.getColumnModel().getColumn(5).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N
        jTable4.getColumnModel().getColumn(8).setMinWidth(115);
        jTable4.getColumnModel().getColumn(8).setPreferredWidth(115);
        jTable4.getColumnModel().getColumn(8).setMaxWidth(115);
        jTable4.getColumnModel().getColumn(8).setHeaderValue(resourceMap.getString("jTable1.columnModel.title7")); // NOI18N
        jTable4.getColumnModel().getColumn(9).setMinWidth(40);
        jTable4.getColumnModel().getColumn(9).setPreferredWidth(40);
        jTable4.getColumnModel().getColumn(9).setMaxWidth(40);
        jTable4.getColumnModel().getColumn(9).setHeaderValue(resourceMap.getString("jTable1.columnModel.title8")); // NOI18N
        jTable4.getColumnModel().getColumn(10).setMinWidth(150);
        jTable4.getColumnModel().getColumn(10).setPreferredWidth(200);
        jTable4.getColumnModel().getColumn(10).setHeaderValue(resourceMap.getString("jTable1.columnModel.title9")); // NOI18N
        jTable4.getColumnModel().getColumn(11).setMinWidth(80);
        jTable4.getColumnModel().getColumn(11).setPreferredWidth(80);
        jTable4.getColumnModel().getColumn(11).setMaxWidth(80);
        jTable4.getColumnModel().getColumn(11).setHeaderValue(resourceMap.getString("jTable1.columnModel.title11")); // NOI18N

        historyPane.setLeftComponent(jScrollPane4);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setMaximumSize(new java.awt.Dimension(32767, 160));
        jPanel4.setMinimumSize(new java.awt.Dimension(0, 160));
        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(1157, 160));

        jLabel43.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel43.setText(resourceMap.getString("jLabel43.text")); // NOI18N
        jLabel43.setName("jLabel43"); // NOI18N

        jLabel44.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel44.setText(resourceMap.getString("jLabel44.text")); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N

        jLabel45.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel45.setText(resourceMap.getString("jLabel45.text")); // NOI18N
        jLabel45.setName("jLabel45"); // NOI18N

        jLabel46.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel46.setText(resourceMap.getString("jLabel46.text")); // NOI18N
        jLabel46.setName("jLabel46"); // NOI18N

        jLabel47.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel47.setText(resourceMap.getString("jLabel47.text")); // NOI18N
        jLabel47.setName("jLabel47"); // NOI18N

        jTextField28.setBackground(resourceMap.getColor("jTextField28.background")); // NOI18N
        jTextField28.setEditable(false);
        jTextField28.setName("jTextField28"); // NOI18N

        jTextField29.setBackground(resourceMap.getColor("jTextField28.background")); // NOI18N
        jTextField29.setEditable(false);
        jTextField29.setName("jTextField29"); // NOI18N

        jTextField30.setBackground(resourceMap.getColor("jTextField28.background")); // NOI18N
        jTextField30.setEditable(false);
        jTextField30.setName("jTextField30"); // NOI18N

        jTextField31.setBackground(resourceMap.getColor("jTextField28.background")); // NOI18N
        jTextField31.setEditable(false);
        jTextField31.setName("jTextField31"); // NOI18N

        jTextField32.setBackground(resourceMap.getColor("jTextField28.background")); // NOI18N
        jTextField32.setEditable(false);
        jTextField32.setName("jTextField32"); // NOI18N

        jLabel48.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel48.setText(resourceMap.getString("jLabel48.text")); // NOI18N
        jLabel48.setName("jLabel48"); // NOI18N

        jLabel49.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel49.setText(resourceMap.getString("jLabel49.text")); // NOI18N
        jLabel49.setName("jLabel49"); // NOI18N

        jLabel50.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel50.setText(resourceMap.getString("jLabel50.text")); // NOI18N
        jLabel50.setName("jLabel50"); // NOI18N

        jLabel51.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel51.setText(resourceMap.getString("jLabel51.text")); // NOI18N
        jLabel51.setName("jLabel51"); // NOI18N

        jLabel52.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel52.setText(resourceMap.getString("jLabel52.text")); // NOI18N
        jLabel52.setName("jLabel52"); // NOI18N

        jTextField33.setBackground(resourceMap.getColor("jTextField28.background")); // NOI18N
        jTextField33.setEditable(false);
        jTextField33.setName("jTextField33"); // NOI18N

        jTextField34.setBackground(resourceMap.getColor("jTextField28.background")); // NOI18N
        jTextField34.setEditable(false);
        jTextField34.setName("jTextField34"); // NOI18N

        jTextField35.setBackground(resourceMap.getColor("jTextField28.background")); // NOI18N
        jTextField35.setEditable(false);
        jTextField35.setName("jTextField35"); // NOI18N

        jTextField36.setBackground(resourceMap.getColor("jTextField28.background")); // NOI18N
        jTextField36.setEditable(false);
        jTextField36.setName("jTextField36"); // NOI18N

        jLabel53.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel53.setText(resourceMap.getString("jLabel53.text")); // NOI18N
        jLabel53.setName("jLabel53"); // NOI18N

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        jTextArea4.setColumns(20);
        jTextArea4.setEditable(false);
        jTextArea4.setFont(resourceMap.getFont("jTextArea4.font")); // NOI18N
        jTextArea4.setRows(5);
        jTextArea4.setMaximumSize(new java.awt.Dimension(2147483647, 74));
        jTextArea4.setName("jTextArea4"); // NOI18N
        jScrollPane9.setViewportView(jTextArea4);

        jLabel54.setText(resourceMap.getString("jLabel54.text")); // NOI18N
        jLabel54.setName("jLabel54"); // NOI18N

        jLabel55.setFont(resourceMap.getFont("jLabel44.font")); // NOI18N
        jLabel55.setText(resourceMap.getString("jLabel55.text")); // NOI18N
        jLabel55.setName("jLabel55"); // NOI18N

        jLabel56.setText(resourceMap.getString("jLabel56.text")); // NOI18N
        jLabel56.setName("jLabel56"); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel46)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel44)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(jLabel48)
                    .addComponent(jLabel49)
                    .addComponent(jLabel51)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel52)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel54)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel53))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(jLabel55)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel56)))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(jTextField33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel49)
                            .addComponent(jTextField34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50)
                            .addComponent(jTextField35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel51)
                            .addComponent(jTextField36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel52)
                            .addComponent(jLabel54)
                            .addComponent(jLabel55)
                            .addComponent(jLabel56)))
                    .addComponent(jScrollPane9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        historyPane.setBottomComponent(jPanel4);

        jTabbedPane1.addTab(resourceMap.getString("historyPane.TabConstraints.tabTitle"), historyPane); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(standartToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1159, Short.MAX_VALUE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(standartToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName(resourceMap.getString("jTabbedPane1.AccessibleContext.accessibleName")); // NOI18N

        menuBar.setName("menuBar"); // NOI18N

        fileMenuItem.setText(resourceMap.getString("fileMenuItem.text")); // NOI18N
        fileMenuItem.setName("fileMenuItem"); // NOI18N

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setIcon(resourceMap.getIcon("openMenuItem.icon")); // NOI18N
        openMenuItem.setText(resourceMap.getString("openMenuItem.text")); // NOI18N
        openMenuItem.setName("openMenuItem"); // NOI18N
        fileMenuItem.add(openMenuItem);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenuItem.add(jSeparator1);

        createMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        createMenuItem.setIcon(resourceMap.getIcon("createMenuItem.icon")); // NOI18N
        createMenuItem.setText(resourceMap.getString("createMenuItem.text")); // NOI18N
        createMenuItem.setName("createMenuItem"); // NOI18N
        fileMenuItem.add(createMenuItem);

        jSeparator2.setName("jSeparator2"); // NOI18N
        fileMenuItem.add(jSeparator2);

        importMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        importMenuItem.setIcon(resourceMap.getIcon("importMenuItem.icon")); // NOI18N
        importMenuItem.setText(resourceMap.getString("importMenuItem.text")); // NOI18N
        importMenuItem.setName("importMenuItem"); // NOI18N
        fileMenuItem.add(importMenuItem);

        exportMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        exportMenuItem.setIcon(resourceMap.getIcon("exportMenuItem.icon")); // NOI18N
        exportMenuItem.setText(resourceMap.getString("exportMenuItem.text")); // NOI18N
        exportMenuItem.setName("exportMenuItem"); // NOI18N
        fileMenuItem.add(exportMenuItem);

        jSeparator3.setName("jSeparator3"); // NOI18N
        fileMenuItem.add(jSeparator3);

        printMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printMenuItem.setText(resourceMap.getString("printMenuItem.text")); // NOI18N
        printMenuItem.setName("printMenuItem"); // NOI18N
        fileMenuItem.add(printMenuItem);

        jSeparator4.setName("jSeparator4"); // NOI18N
        fileMenuItem.add(jSeparator4);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setText(resourceMap.getString("exitMenuItem.text")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenuItem.add(exitMenuItem);

        menuBar.add(fileMenuItem);

        editMenuItem.setText(resourceMap.getString("editMenuItem.text")); // NOI18N
        editMenuItem.setName("editMenuItem"); // NOI18N

        undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoMenuItem.setText(resourceMap.getString("undoMenuItem.text")); // NOI18N
        undoMenuItem.setName("undoMenuItem"); // NOI18N
        editMenuItem.add(undoMenuItem);

        jMenuItem1.setAction(actionMap.get("showFilmView")); // NOI18N
        jMenuItem1.setText(resourceMap.getString("jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseClicked(evt);
            }
        });
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        editMenuItem.add(jMenuItem1);

        menuBar.add(editMenuItem);

        viewMenuItem.setText(resourceMap.getString("viewMenuItem.text")); // NOI18N
        viewMenuItem.setName("viewMenuItem"); // NOI18N

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem5.setAction(actionMap.get("showRussianName")); // NOI18N
        jMenuItem5.setText(resourceMap.getString("jMenuItem5.text")); // NOI18N
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        jMenu1.add(jMenuItem5);

        viewMenuItem.add(jMenu1);

        menuBar.add(viewMenuItem);

        searchMenu.setText(resourceMap.getString("searchMenu.text")); // NOI18N
        searchMenu.setName("searchMenu"); // NOI18N

        findMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        findMenuItem.setText(resourceMap.getString("findMenuItem.text")); // NOI18N
        findMenuItem.setName("findMenuItem"); // NOI18N
        searchMenu.add(findMenuItem);

        menuBar.add(searchMenu);

        serviceMenuItem.setText(resourceMap.getString("serviceMenuItem.text")); // NOI18N
        serviceMenuItem.setName("serviceMenuItem"); // NOI18N

        preferencesMenuItem.setText(resourceMap.getString("preferencesMenuItem.text")); // NOI18N
        preferencesMenuItem.setName("preferencesMenuItem"); // NOI18N
        serviceMenuItem.add(preferencesMenuItem);

        menuBar.add(serviceMenuItem);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setText(resourceMap.getString("aboutMenuItem.text")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1179, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1009, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        jMenuItem2.setText(resourceMap.getString("jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText(resourceMap.getString("jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jPopupMenu1.add(jMenuItem3);

        jMenuItem4.setText(resourceMap.getString("jMenuItem4.text")); // NOI18N
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jPopupMenu1.add(jMenuItem4);

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
        setToolBar(standartToolBar);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseClicked
    }//GEN-LAST:event_jMenuItem1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            int row = jTable1.rowAtPoint(evt.getPoint());
            int filmID = Integer.parseInt((String) jTable1.getValueAt(row, 0));
            Film film = (Film) ((new FindFilmCommand()).execute(filmID));
            FilmViewNew fv = new FilmViewNew(null, true, film);
            fv.setLocationRelativeTo(MediatekaApp.getApplication().getMainFrame());
            MediatekaApp.getApplication().show(fv);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable4MouseClicked

    private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable5MouseClicked

    @Action
    public void showFilmView() {
        JFrame mainFrame = MediatekaApp.getApplication().getMainFrame();
        filmView = new FilmView(mainFrame, true, null);
        filmView.setLocationRelativeTo(mainFrame);

        MediatekaApp.getApplication().show(filmView);
    }

    @Action
    public void addFilm() {
        FilmViewNew fv = new FilmViewNew(null, true, null);
        fv.setLocationRelativeTo(MediatekaApp.getApplication().getMainFrame());
        MediatekaApp.getApplication().show(fv);
    }

    @Action
    public void showRussianName() {
        jTable1.getColumnModel().removeColumn(jTable1.getColumnModel().getColumns().nextElement());
    }

    @Action
    public void update() {
        updateTableFilms();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane blackListPane;
    private javax.swing.JMenuItem createMenuItem;
    private javax.swing.JSplitPane diskPane;
    private javax.swing.JMenu editMenuItem;
    private javax.swing.JMenuItem exportMenuItem;
    private javax.swing.JSplitPane filmPane;
    private javax.swing.JMenuItem findMenuItem;
    private javax.swing.JSplitPane historyPane;
    private javax.swing.JMenuItem importMenuItem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField23;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField33;
    private javax.swing.JTextField jTextField34;
    private javax.swing.JTextField jTextField35;
    private javax.swing.JTextField jTextField36;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField41;
    private javax.swing.JTextField jTextField42;
    private javax.swing.JTextField jTextField43;
    private javax.swing.JTextField jTextField44;
    private javax.swing.JTextField jTextField45;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JSplitPane personPane;
    private javax.swing.JMenuItem preferencesMenuItem;
    private javax.swing.JMenuItem printMenuItem;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenu searchMenu;
    private javax.swing.JMenu serviceMenuItem;
    private javax.swing.JToolBar standartToolBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenuItem undoMenuItem;
    private javax.swing.JMenu viewMenuItem;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private JDialog filmView;
}
