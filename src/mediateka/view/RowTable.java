/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mediateka.view;

import javax.swing.*;

/**
 *
 * @author Alexandr
 */
public class RowTable extends JTable {

  /**
  * Компонент названий строк
  */
  private JList rowHeader = null;

  /**
  * устанавливает новые названия строк по ListModel
  */
  public void setRowHeader(ListModel lm) {
    rowHeader = new JList(lm);
    rowHeader.setForeground(getTableHeader().getForeground());
    rowHeader.setBackground(getTableHeader().getBackground());
    rowHeader.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    rowHeader.setAutoscrolls(false);
    rowHeader.setFixedCellHeight(getRowHeight());
    if (getRowCount() == 0)
    rowHeader.setFixedCellWidth(25);
    rowHeader.setCellRenderer(new RowHeaderRenderer(this));
    ((JScrollPane) getParent().getParent()).setRowHeaderView(rowHeader);
    if (rowHeader.getCellRenderer().getListCellRendererComponent(rowHeader, lm.getElementAt(lm.getSize() - 1), lm.getSize() - 1, false, false).getPreferredSize().getWidth() < 25)
    rowHeader.setFixedCellWidth(25);
  }

  /**
  * Возвращает компонент названий строк
  */
  public JList getRowHeader() {
    return rowHeader;
  }

  private boolean visibleRowHeader = true;

  /**
  * делает видимыми или невидимыми названия строк, в зависимости от параметра
  */
  public void setVisibleRowHeader(boolean visible) {
    visibleRowHeader = visible;
    if (visible) {
      if (rowHeader == null)
      repaint();
      else
      ((JScrollPane) getParent().getParent()).setRowHeaderView(rowHeader);
    } else
    ((JScrollPane) getParent().getParent()).setRowHeaderView(null);

  }

  /**
  * Удаляет названия строк
  */
  public void removeRowHeader() {
    ((JScrollPane) getParent().getParent()).setRowHeaderView(null);
    rowHeader = null;
  }
 }