package mediateka.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mediateka.datamanagers.Managers;
import mediateka.db.ChangeDataException;
import mediateka.db.Disc;
import mediateka.db.HistoryRecord;
import mediateka.db.Person;
import mediateka.db.Record;
import org.jdesktop.application.Action;

/**
 *
 * @author DeKaN
 */
public class HistoryRecordView extends javax.swing.JDialog {

    HistoryRecord history = null;
    HashMap<Integer, Integer> discIds = null, personIds = null;
    String[] discs, persons;
    int dIndex = 0, pIndex = 0;

    /** Creates new form HistoryRecordView */
    public HistoryRecordView(java.awt.Frame parent, boolean modal, HistoryRecord histRec) {
        super(parent, modal);
        try {
            history = histRec;
            List<Record> recs = Managers.getInstance().getPersManager().getRecords();
            persons = new String[recs.size()];
            int i = 0, id = history != null ? history.getPerson().getID() : 0;
            personIds = new HashMap<Integer, Integer>();
            for (Record rec : recs) {
                try {
                    Person p = (Person) rec;
                    personIds.put(i, p.getID());
                    persons[i] = p.toString();
                    if (p.getID() == id) {
                        pIndex = i;
                    }
                    i++;
                } catch (Exception e) {
                    Logger.getLogger(HistoryRecordView.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            recs = Managers.getInstance().getDiscsManager().getRecords();
            discs = new String[recs.size()];
            i = 0;
            id = history != null ? history.getDisc().getID() : 0;
            discIds = new HashMap<Integer, Integer>();
            for (Record rec : recs) {
                try {
                    Disc d = (Disc) rec;
                    discIds.put(i, d.getID());
                    discs[i] = d.toString();
                    if (d.getID() == id) {
                        dIndex = i;
                    }
                    i++;
                } catch (Exception e) {
                }
            }
            initComponents();
            jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(discs));
            jComboBox1.setSelectedIndex(dIndex);
            jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(persons));
            jComboBox2.setSelectedIndex(pIndex);
            if (history != null) {
                jDateChooser1.setDate(history.getGiveDate());
                jDateChooser2.setDate(history.getPromisedDate());
                Date d = history.getReturnDate();
                if (d != null) {
                    jCheckBox1.setSelected(false);
                    jDateChooser3.setDate(d);
                } else {
                    jDateChooser3.setDate(new Date());
                    jCheckBox1.setSelected(true);
                }
                setTitle("Изменить запись");
                jButton1.setText("Сохранить");
            } else {
                jDateChooser1.setDate(new Date());
                jDateChooser2.setDate(new Date());
                jDateChooser3.setDate(new Date());
                jCheckBox1.setSelected(true);
                setTitle("Добавить запись");
                jButton1.setText("Добавить");
            }
        } catch (Exception ex) {
            Logger.getLogger(HistoryRecordView.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(mediateka.client.MediatekaApp.class).getContext().getResourceMap(HistoryRecordView.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

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

        jComboBox1.setName("jComboBox1"); // NOI18N

        jComboBox2.setName("jComboBox2"); // NOI18N

        jDateChooser1.setBackground(resourceMap.getColor("jDateChooser1.background")); // NOI18N
        jDateChooser1.setName("jDateChooser1"); // NOI18N
        jDateChooser1.getDateEditor().setEnabled(false);

        jDateChooser2.setBackground(resourceMap.getColor("jDateChooser1.background")); // NOI18N
        jDateChooser2.setName("jDateChooser2"); // NOI18N
        jDateChooser2.getDateEditor().setEnabled(false);

        jDateChooser3.setBackground(resourceMap.getColor("jDateChooser1.background")); // NOI18N
        jDateChooser3.setEnabled(false);
        jDateChooser3.getDateEditor().setEnabled(false);
        jDateChooser3.setName("jDateChooser3"); // NOI18N

        jCheckBox1.setSelected(true);
        jCheckBox1.setText(resourceMap.getString("jCheckBox1.text")); // NOI18N
        jCheckBox1.setName("jCheckBox1"); // NOI18N
        jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox1StateChanged(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setFont(resourceMap.getFont("jTextArea1.font")); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(mediateka.client.MediatekaApp.class).getContext().getActionMap(HistoryRecordView.class, this);
        jButton1.setAction(actionMap.get("save")); // NOI18N
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N

        jButton2.setAction(actionMap.get("close")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                        .addComponent(jButton2))
                    .addComponent(jComboBox1, 0, 285, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, 285, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBox1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5)
                            .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
        jDateChooser3.setEnabled(!jCheckBox1.isSelected());
        jDateChooser3.getDateEditor().setEnabled(false);
    }//GEN-LAST:event_jCheckBox1StateChanged

    @Action
    public void close() {
        this.dispose();
    }

    @Action
    public void save() {
        try {
            Person p = (Person) Managers.getInstance().getPersManager().find(personIds.get(jComboBox2.getSelectedIndex()));
            Disc d = (Disc) Managers.getInstance().getDiscsManager().find(discIds.get(jComboBox1.getSelectedIndex()));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            if (history != null) {
                history.setDisc(d);
                history.setPerson(p);
                history.setGiveDate(jDateChooser1.getDate());
                history.setPromisedDate(jDateChooser2.getDate());
                history.setReturnDate(jCheckBox1.isSelected() ? formatter.parse("1970-01-01") : jDateChooser3.getDate());
                history.setComment(jTextArea1.getText());
                if (!Managers.getInstance().getHistManager().edit(history)) {
                    throw new ChangeDataException("Ошибка при сохранении");
                }
            } else {
                history = new HistoryRecord(d, p, jDateChooser1.getDate(), jDateChooser2.getDate(),
                        jCheckBox1.isSelected() ? formatter.parse("1970-01-01") : jDateChooser3.getDate(), jTextArea1.getText());
                if (!Managers.getInstance().getHistManager().add(history)) {
                    throw new ChangeDataException("Ошибка при добавлении");


                }
            }
        } catch (Exception ex) {
            Logger.getLogger(HistoryRecordView.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
