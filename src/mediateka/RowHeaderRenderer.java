/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka;

import java.awt.Component;
import javax.swing.*;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Alexandr
 */
class RowHeaderRenderer extends JLabel implements ListCellRenderer {
  private JTable table = null;

  public RowHeaderRenderer(JTable table) {
    this.table = table;
    JTableHeader header = table.getTableHeader();
    setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(CENTER);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
  }

  public Component getListCellRendererComponent(JList list,
  Object value, int index, boolean isSelected, boolean cellHasFocus) {
    setText((value == null) ? "" : value.toString());
    if (isSelected && cellHasFocus) {
      table.setColumnSelectionAllowed(false);
      table.setRowSelectionInterval(index, index);
      table.scrollRectToVisible(table.getCellRect(index, 0, false));
      table.requestFocus();
    } else
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    return this;
  }
}
