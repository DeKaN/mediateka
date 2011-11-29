package mediateka;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mediateka.commands.AddBlRecordCommand;
import mediateka.commands.EditBlRecordCommand;
import mediateka.datamanagers.Managers;
import mediateka.db.BlackListRecord;
import mediateka.db.Person;
import mediateka.db.Record;
import org.jdesktop.application.Action;

/**
 *
 * @author DeKaN
 */
public class BlackListRecordView extends javax.swing.JDialog {

    BlackListRecord record = null;
    private HashMap<Integer, Integer> map = null;
    String[] strs = null;
    int index = 0;

    /** Creates new form BlackListRecordView */
    public BlackListRecordView(java.awt.Frame parent, boolean modal, BlackListRecord blRecord) {
        super(parent, modal);
        try {
            record = blRecord;
            Record[] recs = Managers.getInstance().getPersManager().getRecords();
            strs = new String[recs.length];
            int i = 0, id = record != null ? record.getPerson().getID() : 0;
            for (Record rec : recs) {
                try {
                    Person p = (Person) rec;
                    map.put(i, p.getID());
                    strs[i] = p.toString();
                    if (p.getID() == id) {
                        index = i;
                    }
                    i++;
                } catch (Exception e) {
                }
            }
            initComponents();
            jComboBox1.setSelectedIndex(index);
            if (record == null) {
                jTextArea1.setText("");
                setTitle("Добавить запись");
                jButton1.setText("Добавить");
            } else {
                jTextArea1.setText(record.getComment());
                setTitle("Изменить запись");
                jButton1.setText("Сохранить");
            }
        } catch (Exception ex) {
            Logger.getLogger(BlackListRecordView.class.getName()).log(Level.SEVERE, null, ex);
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
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(mediateka.MediatekaApp.class).getContext().getResourceMap(BlackListRecordView.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setName("Form"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jComboBox1.setName("jComboBox1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(mediateka.MediatekaApp.class).getContext().getActionMap(BlackListRecordView.class, this);
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(27, 27, 27)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton2))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void close() {
        this.dispose();
    }

    @Action
    public void save() {
        try {
            Person p = (Person) Managers.getInstance().getPersManager().find(map.get(jComboBox1.getSelectedIndex()));
            if (record != null) {
                record.setPerson(p);
                record.setComment(jTextArea1.getText());
                if (!((new EditBlRecordCommand()).execute(record))) {
                    throw new Exception("Ошибка при сохранении");
                }
                //MediatekaView.managers.getBlListManager().edit(record.getID(), record);
            } else {
                record = new BlackListRecord(p, jTextArea1.getText());
                if (!((new AddBlRecordCommand()).execute(record)))
                    throw new Exception("Ошибка при добавлении");
                //MediatekaView.managers.getBlListManager().add(record);
            }
        } catch (Exception ex) {
            Logger.getLogger(BlackListRecordView.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
