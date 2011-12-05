/*
 * MediatekaView.java
 */
package mediateka.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import mediateka.MediatekaApp;
import mediateka.datamanagers.Manager;
import mediateka.datamanagers.Managers;
import mediateka.db.BlackListRecord;
import mediateka.db.Disc;
import mediateka.db.Film;
import mediateka.db.HistoryRecord;
import mediateka.db.Person;
import mediateka.db.Record;

/**
 * The application's main frame.
 */
public class MediatekaView extends FrameView {

    private static final String[] columnNamesFilms = new String[]{"ID", "Русское название", "Жанр", "Продолжительность", "Год", "Субтитры", "Оценка", "Просмотрен"};
    private static final String[] columnNamesDiscs = new String[]{"ID", "Список фильмов", "Формат", "Регион", "В наличии"};
    private static final String[] columnNamesPersons = new String[]{"ID", "Фамилия", "Имя", "Отчество", "Телефон", "Комментарий"};
    private static final String[] columnNamesBLRecords = new String[]{"ID", "ФИО", "Комментарий"};
    private static final String[] columnNamesHistoty = new String[]{"ID", "Диск", "Человек", "Дата выдачи", "Обещанная дата", "Дата возврата", "Комментарий"};
    private HashMap componentMap;

    private void createComponentMap() {
        componentMap = new HashMap<String, Component>();
        addChildComponents(getFrame().getContentPane());
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

    private void updateFilmInfo(Film film) {
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
            result = stringArrayToString(film.getGenres());
            jTextField6.setText(result);
            jTextField6.setCaretPosition(0);
            result = stringArrayToString(film.getCountries());
            jTextField7.setText(result);
            jTextField7.setCaretPosition(0);
            result = stringArrayToString(film.getSubtitles());
            jTextField8.setText(result);
            jTextField8.setCaretPosition(0);
            result = stringArrayToString(film.getSoundLanguages());
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

    private void updateDiscInfo(Disc disc) {
        List<Record> films = disc.getFilms().toList();
        String tmp = "";
        if (!films.isEmpty()) {
            tmp += ((Film) films.get(0)).toString();
        }
        for (int i = 1; i < films.size(); i++) {
            tmp += ", " + ((Film) films.get(i)).toString();
        }
        jTextField10.setText(tmp);
        jTextField11.setText(disc.getFormat().toString());
        jTextField12.setText(Integer.toString(disc.getRegionCode()));
        jTextField13.setText((disc.isPresented()) ? "да" : "нет");
    }

    private void updatePersonInfo(Person person) {
        if (person != null) {
            String result = "";
            result = person.getLastName();
            jTextField37.setText((result.length() == 0) ? "-" : result);
            jTextField37.setCaretPosition(0);
            result = person.getFirstName();
            jTextField38.setText((result.length() == 0) ? "-" : result);
            jTextField38.setCaretPosition(0);
            result = person.getSecondName();
            jTextField39.setText((result.length() == 0) ? "-" : result);
            jTextField39.setCaretPosition(0);
            result = person.getPhone();
            jTextField40.setText((result.length() == 0) ? "-" : result);
            jTextField40.setCaretPosition(0);
            result = person.getComment();
            jTextArea5.setText((result.length() == 0) ? "-" : result);
            jTextArea5.setCaretPosition(0);
        } else {
            jTextField37.setText("");
            jTextField38.setText("");
            jTextField39.setText("");
            jTextField40.setText("");
            jTextArea5.setText("");
        }
    }

    private void updateBlackListInfo(BlackListRecord blackListRecord) {
        if (blackListRecord != null) {
            String result = "";
            result = blackListRecord.getPerson().getLastName();
            jTextField19.setText((result.length() == 0) ? "-" : result);
            jTextField19.setCaretPosition(0);
            result = blackListRecord.getPerson().getFirstName();
            jTextField20.setText((result.length() == 0) ? "-" : result);
            jTextField20.setCaretPosition(0);
            result = blackListRecord.getPerson().getSecondName();
            jTextField21.setText((result.length() == 0) ? "-" : result);
            jTextField21.setCaretPosition(0);
            result = blackListRecord.getPerson().getPhone();
            jTextField22.setText((result.length() == 0) ? "-" : result);
            jTextField22.setCaretPosition(0);
            result = blackListRecord.getComment();
            jTextArea3.setText((result.length() == 0) ? "-" : result);
            jTextArea3.setCaretPosition(0);
        } else {
            jTextField19.setText("");
            jTextField20.setText("");
            jTextField21.setText("");
            jTextField22.setText("");
            jTextArea3.setText("");
        }
    }

    private void updateHistoryInfo(HistoryRecord historyRecord) {
        if (historyRecord != null) {
            String result = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            result = historyRecord.getDisc().toString();
            jTextField28.setText((result.length() == 0) ? "-" : result);
            jTextField28.setCaretPosition(0);
            result = historyRecord.getPerson().toString();
            jTextField30.setText((result.length() == 0) ? "-" : result);
            jTextField30.setCaretPosition(0);
            result = formatter.format(historyRecord.getGiveDate());
            jTextField31.setText((result.equals("1970-01-01")) ? "-" : result);
            jTextField31.setCaretPosition(0);
            result = formatter.format(historyRecord.getPromisedDate());
            jTextField32.setText((result.equals("1970-01-01")) ? "-" : result);
            jTextField32.setCaretPosition(0);
            result = formatter.format(historyRecord.getReturnDate());
            jTextField29.setText((result.equals("1970-01-01")) ? "-" : result);
            jTextField29.setCaretPosition(0);
            result = historyRecord.getComment();
            jTextArea4.setText((result.length() == 0) ? "-" : result);
            jTextArea4.setCaretPosition(0);
        } else {
            jTextField28.setText("");
            jTextField30.setText("");
            jTextField31.setText("");
            jTextField32.setText("");
            jTextField29.setText("");
            jTextArea4.setText("");
        }
    }

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

    public MediatekaView(SingleFrameApplication app) {
        super(app);
        initComponents();
        getFrame().addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(getFrame(), "Сохранить изменения?", "Выход", JOptionPane.YES_NO_OPTION) == 0) {
                    Managers.getInstance().saveDB();
                }
            }

            public void windowClosed(WindowEvent e) {
                getFrame().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        });
        createComponentMap();
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    Film f = null;
                    int rowIndex = jTable1.getSelectedRow();
                    if (rowIndex >= 0) {
                        int filmID = (Integer) jTable1.getValueAt(rowIndex, 0);
                        f = (Film) (Managers.getInstance().getFilmsManager().find(filmID));
                    }
                    updateFilmInfo(f);
                } catch (Exception ex) {
                    Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    Disc disc = null;
                    int rowIndex = jTable2.getSelectedRow();
                    if (rowIndex >= 0) {
                        int discID = (Integer) jTable2.getValueAt(rowIndex, 0);
                        disc = (Disc) (Managers.getInstance().getDiscsManager().find(discID));
                        updateDiscInfo(disc);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jTable5.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    Person p = null;
                    int rowIndex = jTable5.getSelectedRow();
                    if (rowIndex >= 0) {
                        int personID = (Integer) jTable5.getValueAt(rowIndex, 0);
                        p = (Person) (Managers.getInstance().getPersManager().find(personID));
                    }
                    updatePersonInfo(p);
                } catch (Exception ex) {
                    Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jTable3.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    BlackListRecord bl = null;
                    int rowIndex = jTable3.getSelectedRow();
                    if (rowIndex >= 0) {
                        int blID = (Integer) jTable3.getValueAt(rowIndex, 0);
                        bl = (BlackListRecord) (Managers.getInstance().getBlListManager().find(blID));
                    }
                    updateBlackListInfo(bl);
                } catch (Exception ex) {
                    Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jTable4.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    HistoryRecord hist = null;
                    int rowIndex = jTable4.getSelectedRow();
                    if (rowIndex >= 0) {
                        int histID = (Integer) jTable4.getValueAt(rowIndex, 0);
                        hist = (HistoryRecord) (Managers.getInstance().getHistManager().find(histID));
                    }
                    updateHistoryInfo(hist);
                } catch (Exception ex) {
                    Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jTabbedPane1.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent evt) {
                prepareView();
            }
        });
        update();
        prepareView();

    }

    private void prepareView() {
        int enabledIndex = -1;
        switch (jTabbedPane1.getSelectedIndex()) {
            case 0:
                if (jTable1.getModel().getRowCount() > 0) {
                    jTable1.setRowSelectionInterval(0, 0);
                    enabledIndex = 1;
                }
                break;
            case 1:
                if (jTable2.getModel().getRowCount() > 0) {
                    jTable2.setRowSelectionInterval(0, 0);
                    enabledIndex = 2;
                }
                break;
            case 2:
                if (jTable5.getModel().getRowCount() > 0) {
                    jTable5.setRowSelectionInterval(0, 0);
                    enabledIndex = 3;
                }
                break;
            case 3:
                if (jTable3.getModel().getRowCount() > 0) {
                    jTable3.setRowSelectionInterval(0, 0);
                    enabledIndex = 4;
                }
                break;
            case 4:
                if (jTable4.getModel().getRowCount() > 0) {
                    jTable4.setRowSelectionInterval(0, 0);
                    enabledIndex = 5;
                }
                break;
            default:
                break;
        }
        for (int i = 1; i < 6; i++) {
            ((JButton) (componentMap.get("jButton" + i * 2))).setEnabled(i == enabledIndex);
        }
    }

    private void updateTableFilms() {
        try {
            List<Record> recs = Managers.getInstance().getFilmsManager().getRecords();
            ArrayList<Object[]> listOfRows = new ArrayList<Object[]>();
            Film film = null;
            Object[] row = null;
            for (Record rec : recs) {
                film = (Film) rec;
                row = new Object[columnNamesFilms.length];
                row[0] = rec.getID();
                row[1] = film.getRussianTitle();
                row[2] = (film.getGenres() != null) ? stringArrayToString(film.getGenres()) : "-";
                row[3] = (film.getLength() != 0) ? Integer.toString(film.getLength()) : "-";
                row[4] = (film.getYear() != 0) ? Integer.toString(film.getYear()) : "-";
                row[5] = (film.getSubtitles() != null) ? stringArrayToString(film.getSubtitles()) : "-";
                row[6] = (film.getRating() != 0) ? Integer.toString(film.getRating()) : "-";
                row[7] = (film.isSeen()) ? "да" : "нет";
                listOfRows.add(row);
            }
            Object[][] data = new Object[listOfRows.size()][];
            for (int i = 0; i < listOfRows.size(); i++) {
                data[i] = listOfRows.get(i);
            }

            jTable1.setModel(new DefaultTableModel(data, columnNamesFilms) {

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
            Integer[] widths = new Integer[]{40, 150, 350, 115, 50, 180, 60, 80};
            for (int i = 0; i < widths.length; i++) {
                if (i != 1) {
                    jTable1.getColumnModel().getColumn(i).setMinWidth(widths[i]);
                    jTable1.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
                    jTable1.getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                }
            }
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(350);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(450);

        } catch (Exception ex) {
            Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateTableDiscs() {
        try {
            List<Record> recs = Managers.getInstance().getDiscsManager().getRecords();
            ArrayList<Object[]> listOfRows = new ArrayList<Object[]>();
            Disc disc = null;
            Object[] row = null;
            for (Record rec : recs) {
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
                listOfRows.add(row);
            }
            Object[][] data = new Object[listOfRows.size()][];
            for (int i = 0; i < listOfRows.size(); i++) {
                data[i] = listOfRows.get(i);
            }

            jTable2.setModel(new DefaultTableModel(data, columnNamesDiscs) {

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
            Integer[] widths = new Integer[]{40, 0, 100, 75, 100};
            for (int i = 0; i < widths.length; i++) {
                if (i != 1) {
                    jTable2.getColumnModel().getColumn(i).setMinWidth(widths[i]);
                    jTable2.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
                    jTable2.getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateTablePersons() {
        try {
            List<Record> recs = Managers.getInstance().getPersManager().getRecords();
            ArrayList<Object[]> listOfRows = new ArrayList<Object[]>();
            Person pers = null;
            Object[] row = null;
            for (Record rec : recs) {
                pers = (Person) rec;
                row = new Object[6];
                row[0] = rec.getID();
                row[1] = (pers.getLastName().isEmpty()) ? "-" : pers.getLastName();
                row[2] = (pers.getFirstName().isEmpty()) ? "-" : pers.getFirstName();
                row[3] = (pers.getSecondName().isEmpty()) ? "-" : pers.getSecondName();
                row[4] = (pers.getPhone().isEmpty()) ? "-" : pers.getPhone();
                row[5] = (pers.getComment().isEmpty()) ? "-" : pers.getComment();
                listOfRows.add(row);
            }
            Object[][] data = new Object[listOfRows.size()][];
            for (int i = 0; i < listOfRows.size(); i++) {
                data[i] = listOfRows.get(i);
            }

            jTable5.setModel(new DefaultTableModel(data, columnNamesPersons) {

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
            Integer[] widths = new Integer[]{40, 200, 200, 200, 150};
            for (int i = 0; i < widths.length; i++) {
                jTable5.getColumnModel().getColumn(i).setMinWidth(widths[i]);
                jTable5.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
                jTable5.getColumnModel().getColumn(i).setMaxWidth(widths[i]);
            }
        } catch (Exception ex) {
            Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateTableBlackList() {
        try {
            List<Record> recs = Managers.getInstance().getBlListManager().getRecords();
            ArrayList<Object[]> listOfRows = new ArrayList<Object[]>();
            BlackListRecord blRecord = null;
            Object[] row = null;
            for (Record rec : recs) {
                blRecord = (BlackListRecord) rec;
                row = new Object[columnNamesBLRecords.length];
                row[0] = rec.getID();
                row[1] = blRecord.getPerson().toString();
                row[2] = (blRecord.getComment().isEmpty()) ? "-" : blRecord.getComment();
                listOfRows.add(row);
            }
            Object[][] data = new Object[listOfRows.size()][];
            for (int i = 0; i < listOfRows.size(); i++) {
                data[i] = listOfRows.get(i);
            }

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
        } catch (Exception ex) {
            Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateTableHistory() {
        try {
            List<Record> recs = Managers.getInstance().getHistManager().getRecords();
            ArrayList<Object[]> listOfRows = new ArrayList<Object[]>();
            HistoryRecord historyRecord = null;
            Object[] row = null;
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String temp;
            for (Record rec : recs) {
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
                listOfRows.add(row);
            }
            Object[][] data = new Object[listOfRows.size()][];
            for (int i = 0; i < listOfRows.size(); i++) {
                data[i] = listOfRows.get(i);
            }

            jTable4.setModel(new DefaultTableModel(data, columnNamesHistoty) {

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
            Integer[] widths = new Integer[]{40, 200, 200, 100, 100, 100, 100};
            for (int i = 0; i < widths.length; i++) {
                if (i != 1 && i != 2) {
                    jTable4.getColumnModel().getColumn(i).setMinWidth(widths[i]);
                    jTable4.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
                    jTable4.getColumnModel().getColumn(i).setMaxWidth(widths[i]);
                }
            }
            jTable4.getColumnModel().getColumn(1).setMaxWidth(400);
            jTable4.getColumnModel().getColumn(2).setMaxWidth(200);
            jTable4.getColumnModel().getColumn(6).setMaxWidth(500);
        } catch (Exception ex) {
            Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
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
        jSeparator7 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JToolBar.Separator();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JToolBar.Separator();
        jButton11 = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(100, 0), new java.awt.Dimension(150, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 32767));
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
        discPane = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        personPane = new javax.swing.JSplitPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jTextField37 = new javax.swing.JTextField();
        jTextField38 = new javax.swing.JTextField();
        jTextField39 = new javax.swing.JTextField();
        jTextField40 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
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
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
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
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(mediateka.MediatekaApp.class).getContext().getResourceMap(MediatekaView.class);
        mainPanel.setFont(resourceMap.getFont("mainPanel.font")); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N

        standartToolBar.setFloatable(false);
        standartToolBar.setRollover(true);
        standartToolBar.setName("standartToolBar"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(mediateka.MediatekaApp.class).getContext().getActionMap(MediatekaView.class, this);
        jButton1.setAction(actionMap.get("showFilmView")); // NOI18N
        jButton1.setIcon(resourceMap.getIcon("jButton1.icon")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton1);

        jButton2.setAction(actionMap.get("deleteFilm")); // NOI18N
        jButton2.setIcon(resourceMap.getIcon("jButton2.icon")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton2);

        jSeparator7.setName("jSeparator7"); // NOI18N
        standartToolBar.add(jSeparator7);

        jButton3.setAction(actionMap.get("showDiscView")); // NOI18N
        jButton3.setIcon(resourceMap.getIcon("jButton3.icon")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton3);

        jButton4.setAction(actionMap.get("deleteDisc")); // NOI18N
        jButton4.setIcon(resourceMap.getIcon("jButton4.icon")); // NOI18N
        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton4);

        jSeparator6.setName("jSeparator6"); // NOI18N
        standartToolBar.add(jSeparator6);

        jButton5.setAction(actionMap.get("showPersonView")); // NOI18N
        jButton5.setIcon(resourceMap.getIcon("jButton5.icon")); // NOI18N
        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setName("jButton5"); // NOI18N
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton5);

        jButton6.setAction(actionMap.get("deletePerson")); // NOI18N
        jButton6.setIcon(resourceMap.getIcon("jButton6.icon")); // NOI18N
        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setName("jButton6"); // NOI18N
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton6);

        jSeparator5.setName("jSeparator5"); // NOI18N
        standartToolBar.add(jSeparator5);

        jButton7.setAction(actionMap.get("showBlackListRecordView")); // NOI18N
        jButton7.setIcon(resourceMap.getIcon("jButton7.icon")); // NOI18N
        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setName("jButton7"); // NOI18N
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton7);

        jButton8.setAction(actionMap.get("deleteBlackListRecord")); // NOI18N
        jButton8.setIcon(resourceMap.getIcon("jButton8.icon")); // NOI18N
        jButton8.setText(resourceMap.getString("jButton8.text")); // NOI18N
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setName("jButton8"); // NOI18N
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton8);

        jSeparator8.setName("jSeparator8"); // NOI18N
        standartToolBar.add(jSeparator8);

        jButton9.setAction(actionMap.get("showHistoryView")); // NOI18N
        jButton9.setIcon(resourceMap.getIcon("jButton9.icon")); // NOI18N
        jButton9.setText(resourceMap.getString("jButton9.text")); // NOI18N
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setName("jButton9"); // NOI18N
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton9);

        jButton10.setAction(actionMap.get("deleteHistoryRecord")); // NOI18N
        jButton10.setIcon(resourceMap.getIcon("jButton10.icon")); // NOI18N
        jButton10.setText(resourceMap.getString("jButton10.text")); // NOI18N
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setName("jButton10"); // NOI18N
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton10);

        jSeparator9.setName("jSeparator9"); // NOI18N
        standartToolBar.add(jSeparator9);

        jButton11.setAction(actionMap.get("showFindView")); // NOI18N
        jButton11.setIcon(resourceMap.getIcon("jButton11.icon")); // NOI18N
        jButton11.setText(resourceMap.getString("jButton11.text")); // NOI18N
        jButton11.setFocusable(false);
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setName("jButton11"); // NOI18N
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        standartToolBar.add(jButton11);

        filler1.setName("filler1"); // NOI18N
        standartToolBar.add(filler1);

        filler2.setName("filler2"); // NOI18N
        standartToolBar.add(filler2);

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
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
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
        jTable1.getColumnModel().getColumn(3).setMinWidth(115);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(115);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(115);
        jTable1.getColumnModel().getColumn(4).setMinWidth(50);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable1.getColumnModel().getColumn(5).setMinWidth(180);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(180);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(180);
        jTable1.getColumnModel().getColumn(6).setMinWidth(60);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(60);
        jTable1.getColumnModel().getColumn(7).setMinWidth(80);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(7).setMaxWidth(80);

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
        jTextArea1.setLineWrap(true);
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
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)))
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

        discPane.setDividerLocation(800);
        discPane.setDividerSize(3);
        discPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        discPane.setResizeWeight(1.0);
        discPane.setName("discPane"); // NOI18N

        jScrollPane2.setBackground(resourceMap.getColor("jScrollPane2.background")); // NOI18N
        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Список фильмов", "Формат", "Регион", "В наличии"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
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
        jTable2.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable2.getColumnModel().getColumn(2).setMinWidth(100);
        jTable2.getColumnModel().getColumn(2).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(2).setMaxWidth(100);
        jTable2.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable2.columnModel.title2")); // NOI18N
        jTable2.getColumnModel().getColumn(3).setMinWidth(75);
        jTable2.getColumnModel().getColumn(3).setPreferredWidth(75);
        jTable2.getColumnModel().getColumn(3).setMaxWidth(75);
        jTable2.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable2.getColumnModel().getColumn(4).setMinWidth(100);
        jTable2.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable2.getColumnModel().getColumn(4).setMaxWidth(100);
        jTable2.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable2.columnModel.title4")); // NOI18N

        discPane.setLeftComponent(jScrollPane2);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                    .addComponent(jTextField11)
                    .addComponent(jTextField12)
                    .addComponent(jTextField13))
                .addGap(663, 663, 663))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        discPane.setBottomComponent(jPanel2);
        jPanel2.getAccessibleContext().setAccessibleName(resourceMap.getString("jPanel2.AccessibleContext.accessibleName")); // NOI18N

        jTabbedPane1.addTab(resourceMap.getString("discPane.TabConstraints.tabTitle"), discPane); // NOI18N

        personPane.setDividerLocation(800);
        personPane.setDividerSize(3);
        personPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        personPane.setResizeWeight(1.0);
        personPane.setName("personPane"); // NOI18N

        jScrollPane5.setBackground(resourceMap.getColor("jScrollPane5.background")); // NOI18N
        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Фамилия", "Имя", "Отчество", "Телефон", "Комментарий"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable5.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
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
        jTable5.getColumnModel().getColumn(1).setMinWidth(200);
        jTable5.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable5.getColumnModel().getColumn(1).setMaxWidth(200);
        jTable5.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable5.getColumnModel().getColumn(2).setMinWidth(200);
        jTable5.getColumnModel().getColumn(2).setPreferredWidth(200);
        jTable5.getColumnModel().getColumn(2).setMaxWidth(200);
        jTable5.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable5.columnModel.title2")); // NOI18N
        jTable5.getColumnModel().getColumn(3).setMinWidth(200);
        jTable5.getColumnModel().getColumn(3).setPreferredWidth(200);
        jTable5.getColumnModel().getColumn(3).setMaxWidth(200);
        jTable5.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable5.getColumnModel().getColumn(4).setMinWidth(150);
        jTable5.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTable5.getColumnModel().getColumn(4).setMaxWidth(150);
        jTable5.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable5.columnModel.title4")); // NOI18N

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel57)
                    .addComponent(jLabel58)
                    .addComponent(jLabel59)
                    .addComponent(jLabel60))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField38, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                    .addComponent(jTextField39, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField40, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel67, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField37, jTextField38, jTextField39, jTextField40});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel57)
                    .addComponent(jLabel67)
                    .addComponent(jTextField37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58)
                            .addComponent(jTextField38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel59)
                            .addComponent(jTextField39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60)))
                    .addComponent(jScrollPane10, 0, 0, Short.MAX_VALUE))
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

            },
            new String [] {
                "ID", "ФИО", "Комментарий"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
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
        jTable3.getColumnModel().getColumn(1).setMinWidth(250);
        jTable3.getColumnModel().getColumn(1).setPreferredWidth(350);
        jTable3.getColumnModel().getColumn(1).setMaxWidth(650);
        jTable3.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable3.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable3.columnModel.title2")); // NOI18N

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
        jTextField19.setFont(resourceMap.getFont("jTextField19.font")); // NOI18N
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

        jScrollPane8.setName("jScrollPane8"); // NOI18N

        jTextArea3.setColumns(20);
        jTextArea3.setEditable(false);
        jTextArea3.setFont(resourceMap.getFont("jTextArea3.font")); // NOI18N
        jTextArea3.setRows(5);
        jTextArea3.setMaximumSize(new java.awt.Dimension(2147483647, 74));
        jTextArea3.setName("jTextArea3"); // NOI18N
        jScrollPane8.setViewportView(jTextArea3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel29)
                    .addComponent(jLabel30))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 823, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jLabel33))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)))
                    .addComponent(jScrollPane8, 0, 0, Short.MAX_VALUE))
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

            },
            new String [] {
                "ID", "Диск", "Человек", "Дата выдачи", "Обещанная дата", "Дата возврата", "Комментарий"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
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
        jTable4.getColumnModel().getColumn(2).setMinWidth(200);
        jTable4.getColumnModel().getColumn(2).setPreferredWidth(200);
        jTable4.getColumnModel().getColumn(2).setMaxWidth(200);
        jTable4.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable4.columnModel.title2")); // NOI18N
        jTable4.getColumnModel().getColumn(3).setMinWidth(100);
        jTable4.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable4.getColumnModel().getColumn(3).setMaxWidth(100);
        jTable4.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable4.getColumnModel().getColumn(4).setMinWidth(100);
        jTable4.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable4.getColumnModel().getColumn(4).setMaxWidth(100);
        jTable4.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable4.columnModel.title4")); // NOI18N
        jTable4.getColumnModel().getColumn(5).setMinWidth(100);
        jTable4.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable4.getColumnModel().getColumn(5).setMaxWidth(100);
        jTable4.getColumnModel().getColumn(6).setHeaderValue(resourceMap.getString("jTable4.columnModel.title7")); // NOI18N

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
        jTextField28.setFont(resourceMap.getFont("jTextField28.font")); // NOI18N
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

        jLabel48.setFont(resourceMap.getFont("jLabel48.font")); // NOI18N
        jLabel48.setText(resourceMap.getString("jLabel48.text")); // NOI18N
        jLabel48.setName("jLabel48"); // NOI18N

        jScrollPane9.setName("jScrollPane9"); // NOI18N

        jTextArea4.setColumns(20);
        jTextArea4.setEditable(false);
        jTextArea4.setFont(resourceMap.getFont("jTextArea4.font")); // NOI18N
        jTextArea4.setRows(5);
        jTextArea4.setWrapStyleWord(true);
        jTextArea4.setName("jTextArea4"); // NOI18N
        jScrollPane9.setViewportView(jTextArea4);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(jLabel44)
                            .addComponent(jLabel43)
                            .addComponent(jLabel46))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField28, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jTextField30, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jTextField31, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jTextField32, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(jTextField29)))
                    .addComponent(jLabel47))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(353, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43)
                    .addComponent(jLabel48))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45)
                            .addComponent(jTextField31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel46)
                            .addComponent(jTextField32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel47)
                            .addComponent(jTextField29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        historyPane.setBottomComponent(jPanel4);

        jTabbedPane1.addTab(resourceMap.getString("historyPane.TabConstraints.tabTitle"), historyPane); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1202, Short.MAX_VALUE))
                    .addComponent(standartToolBar, javax.swing.GroupLayout.DEFAULT_SIZE, 1212, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(standartToolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName(resourceMap.getString("jTabbedPane1.AccessibleContext.accessibleName")); // NOI18N

        setComponent(mainPanel);
        setToolBar(standartToolBar);

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

	private void jTable4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4MouseClicked
            if (evt.getClickCount() == 2) {
                try {
                    int row = jTable4.rowAtPoint(evt.getPoint());
                    int histID = (Integer) jTable4.getValueAt(row, 0);
                    HistoryRecord hist = (HistoryRecord) (Managers.getInstance().getHistManager().find(histID));
                    historyRecordView = new HistoryRecordView(null, true, hist);
                    historyRecordView.setLocationRelativeTo(MediatekaApp.getApplication().getMainFrame());
                    MediatekaApp.getApplication().show(historyRecordView);
                    updateTableHistory();
                } catch (Exception ex) {
                    Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}//GEN-LAST:event_jTable4MouseClicked

	private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
            if (evt.getClickCount() == 2) {
                try {
                    int row = jTable3.rowAtPoint(evt.getPoint());
                    int blID = (Integer) jTable3.getValueAt(row, 0);
                    BlackListRecord bl = (BlackListRecord) (Managers.getInstance().getBlListManager().find(blID));
                    blackListRecordView = new BlackListRecordView(null, true, bl);
                    blackListRecordView.setLocationRelativeTo(MediatekaApp.getApplication().getMainFrame());
                    MediatekaApp.getApplication().show(blackListRecordView);
                    updateTableBlackList();
                } catch (Exception ex) {
                    Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}//GEN-LAST:event_jTable3MouseClicked

	private void jTable5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable5MouseClicked
            if (evt.getClickCount() == 2) {
                try {
                    int row = jTable5.rowAtPoint(evt.getPoint());
                    int personID = (Integer) jTable5.getValueAt(row, 0);
                    Person pers = (Person) (Managers.getInstance().getPersManager().find(personID));
                    personView = new PersonView(null, true, pers);
                    personView.setLocationRelativeTo(MediatekaApp.getApplication().getMainFrame());
                    MediatekaApp.getApplication().show(personView);
                    updateTablePersons();
                    updateTableBlackList();
                    updateTableHistory();
                } catch (Exception ex) {
                    Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}//GEN-LAST:event_jTable5MouseClicked

	private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
            if (evt.getClickCount() == 2) {
                try {
                    int row = jTable1.rowAtPoint(evt.getPoint());
                    int discID = (Integer) jTable1.getValueAt(row, 0);
                    Disc disc = (Disc) (Managers.getInstance().getDiscsManager().find(discID));
                    discView = new DiscView(null, true, disc);
                    discView.setLocationRelativeTo(MediatekaApp.getApplication().getMainFrame());
                    MediatekaApp.getApplication().show(discView);
                    updateTableDiscs();
                    updateTableHistory();
                } catch (Exception ex) {
                    Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}//GEN-LAST:event_jTable2MouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {

        if (evt.getClickCount() == 2) {
            try {
                int row = jTable1.rowAtPoint(evt.getPoint());
                int filmID = (Integer) jTable1.getValueAt(row, 0);
                Film film = (Film) (Managers.getInstance().getFilmsManager().find(filmID));
                filmView = new FilmView(null, true, film);
                filmView.setLocationRelativeTo(MediatekaApp.getApplication().getMainFrame());
                MediatekaApp.getApplication().show(filmView);
                updateTableFilms();
                updateTableDiscs();
                updateTableHistory();
            } catch (Exception ex) {
                Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Action
    public void update() {
        updateTableFilms();
        updateTableDiscs();
        updateTablePersons();
        updateTableBlackList();
        updateTableHistory();
    }

    @Action
    public void showFilmView() {
        jTabbedPane1.setSelectedComponent(filmPane);
        JFrame mainFrame = MediatekaApp.getApplication().getMainFrame();
        filmView = new FilmView(mainFrame, true, null);
        filmView.setLocationRelativeTo(mainFrame);
        MediatekaApp.getApplication().show(filmView);
        updateTableFilms();
    }

    @Action
    public void showDiscView() {
        jTabbedPane1.setSelectedComponent(discPane);
        JFrame mainFrame = MediatekaApp.getApplication().getMainFrame();
        discView = new DiscView(mainFrame, true, null);
        discView.setLocationRelativeTo(mainFrame);
        MediatekaApp.getApplication().show(discView);
        updateTableDiscs();
        updateTableHistory();
    }

    @Action
    public void showPersonView() {
        jTabbedPane1.setSelectedComponent(personPane);
        JFrame mainFrame = MediatekaApp.getApplication().getMainFrame();
        personView = new PersonView(mainFrame, true, null);
        personView.setLocationRelativeTo(mainFrame);
        MediatekaApp.getApplication().show(personView);
        updateTablePersons();
    }

    @Action
    public void showBlackListRecordView() {
        jTabbedPane1.setSelectedComponent(blackListPane);
        JFrame mainFrame = MediatekaApp.getApplication().getMainFrame();
        blackListRecordView = new BlackListRecordView(mainFrame, true, null);
        blackListRecordView.setLocationRelativeTo(mainFrame);
        MediatekaApp.getApplication().show(blackListRecordView);
        updateTableBlackList();
    }

    @Action
    public void showHistoryView() {
        jTabbedPane1.setSelectedComponent(historyPane);
        JFrame mainFrame = MediatekaApp.getApplication().getMainFrame();
        historyRecordView = new HistoryRecordView(mainFrame, true, null);
        historyRecordView.setLocationRelativeTo(mainFrame);
        MediatekaApp.getApplication().show(historyRecordView);
        updateTableHistory();
    }

    @Action
    public void showFindView() {
        FindView fv = new FindView(null, true);
        fv.setLocationRelativeTo(MediatekaApp.getApplication().getMainFrame());
        MediatekaApp.getApplication().show(fv);
    }

    private boolean deleteRecord(int type, int id) {
        Manager manager = null;
        String[] types = new String[]{
            "фильм",
            "диск",
            "персональные данные",
            "запись \"черного списка\"",
            "запись истории"
        };
        try {
            switch (type) {
                case 0:
                    manager = Managers.getInstance().getFilmsManager();
                    break;
                case 1:
                    manager = Managers.getInstance().getDiscsManager();
                    break;
                case 2:
                    manager = Managers.getInstance().getPersManager();
                    break;
                case 3:
                    manager = Managers.getInstance().getBlListManager();
                    break;
                case 4:
                    manager = Managers.getInstance().getHistManager();
                    break;
            }
            if (JOptionPane.showConfirmDialog(getFrame(), String.format("Вы действительно хотите удалить %s?", types[type]), "Удалить запись", JOptionPane.YES_NO_CANCEL_OPTION) == 0) {
                manager.delete(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(getFrame(), "Удаление " + types[type] + " прошло неудачно", "Ошибка!", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(MediatekaView.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Action
    public void deleteFilm() {
        if (deleteRecord(0, (Integer) jTable1.getValueAt(jTable1.getSelectedRow(), 0))) {
            updateTableFilms();
            updateTableDiscs();
            updateTableHistory();
        }
    }

    @Action
    public void deleteDisc() {
        if (deleteRecord(1, (Integer) jTable2.getValueAt(jTable2.getSelectedRow(), 0))) {
            updateTableDiscs();
            updateTableHistory();
        }
    }

    @Action
    public void deletePerson() {
        if (deleteRecord(2, (Integer) jTable5.getValueAt(jTable5.getSelectedRow(), 0))) {
            updateTablePersons();
            updateTableBlackList();
            updateTableHistory();
        }
    }

    @Action
    public void deleteBlackListRecord() {
        if (deleteRecord(3, (Integer) jTable3.getValueAt(jTable3.getSelectedRow(), 0))) {
            updateTableBlackList();
        }
    }

    @Action
    public void deleteHistoryRecord() {
        if (deleteRecord(4, (Integer) jTable4.getValueAt(jTable4.getSelectedRow(), 0))) {
            updateTableHistory();
        }
    }

    @Action
    public void exit() {
        getFrame().dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSplitPane blackListPane;
    private javax.swing.JSplitPane discPane;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JSplitPane filmPane;
    private javax.swing.JSplitPane historyPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar.Separator jSeparator7;
    private javax.swing.JToolBar.Separator jSeparator8;
    private javax.swing.JToolBar.Separator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField29;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField30;
    private javax.swing.JTextField jTextField31;
    private javax.swing.JTextField jTextField32;
    private javax.swing.JTextField jTextField37;
    private javax.swing.JTextField jTextField38;
    private javax.swing.JTextField jTextField39;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField40;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JSplitPane personPane;
    private javax.swing.JToolBar standartToolBar;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
    private JDialog aboutBox = null;
    private JDialog filmView = null;
    private JDialog discView = null;
    private JDialog personView = null;
    private JDialog blackListRecordView = null;
    private JDialog historyRecordView = null;
}
