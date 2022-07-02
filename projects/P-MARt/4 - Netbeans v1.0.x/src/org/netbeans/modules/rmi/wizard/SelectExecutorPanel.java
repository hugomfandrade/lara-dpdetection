/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is NetBeans. The Initial Developer of the Original
 * Code is Sun Microsystems, Inc. Portions Copyright 1997-2001 Sun
 * Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.rmi.wizard;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.openide.*;
import org.openide.execution.*;

import org.netbeans.modules.rmi.*;
/**
 *
 * @author   Martin Ryzl
 */
public class SelectExecutorPanel extends AbstractWizardPanel {

    // ---------------------------------------------------------------------------------------
    // WizardPanel initialization

    static final long serialVersionUID =7539455730103083156L;
    /** Creates new BeanBusinessPanel */
    public SelectExecutorPanel() {
        initComponents ();
        list.setCellRenderer(new DefaultListCellRenderer() {
                                 public Component getListCellRendererComponent(JList list, Object value,
                                         int index, boolean isSelected, boolean cellHasFocus) {

                                     super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                                     if (value instanceof Executor) setText(((Executor)value).getName());
                                     return this;
                                 }
                             });
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints1;
        setBorder (new javax.swing.border.EmptyBorder(new java.awt.Insets(10, 10, 10, 10)));
        setPreferredSize (new java.awt.Dimension(480, 320));

        titleLabel = new javax.swing.JLabel ();
        titleLabel.setText ("Select Executor");
        titleLabel.setFont (new java.awt.Font ("Dialog", 0, 18));


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets (2, 2, 10, 2);
        gridBagConstraints1.weightx = 1.0;
        add (titleLabel, gridBagConstraints1);

        contentPanel = new javax.swing.JPanel ();
        contentPanel.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints2;

        listScroll = new javax.swing.JScrollPane ();
        listScroll.setViewportBorder (null);

        list = new javax.swing.JList ();
        list.addListSelectionListener (new javax.swing.event.ListSelectionListener () {
                                           public void valueChanged (javax.swing.event.ListSelectionEvent evt) {
                                               listValueChanged (evt);
                                           }
                                       }
                                      );

        listScroll.setViewportView (list);

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints2.weightx = 1.0;
        gridBagConstraints2.weighty = 1.0;
        contentPanel.add (listScroll, gridBagConstraints2);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.weightx = 1.0;
        gridBagConstraints1.weighty = 1.0;
        add (contentPanel, gridBagConstraints1);

    }//GEN-END:initComponents

    private void listValueChanged (javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listValueChanged
        // Add your handling code here:
        fireChange();
    }//GEN-LAST:event_listValueChanged

    private void removeButtonPressed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonPressed
    }//GEN-LAST:event_removeButtonPressed

    private void addButtonPressed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonPressed
    }//GEN-LAST:event_addButtonPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JScrollPane listScroll;
    private javax.swing.JList list;
    // End of variables declaration//GEN-END:variables

    Vector methods = new Vector (32);

    // ---------------------------------------------------------------------------------------
    // WizardDescriptor.Panel implementation


    /**
     * @associates Object 
     */
    Vector executors = new Vector(32);

    public void storeRMISettings(RMIWizardData data) {
        data.setExecutor((Executor)list.getSelectedValue());
    }

    public void readRMISettings(RMIWizardData data) {
        Enumeration exs = Executor.executors();
        while (exs.hasMoreElements()) executors.add(exs.nextElement());
        list.setListData(executors);
        Executor executor = Executor.find(RMIExecutor.class);
        list.setSelectedValue(executor, true);
    }

    public boolean isValid() {
        return true;
    }
}

/*
 * <<Log>>
 *  6    Gandalf   1.5         11/27/99 Patrik Knakal   
 *  5    Gandalf   1.4         10/23/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  4    Gandalf   1.3         8/16/99  Martin Ryzl     debug prints were 
 *       removed
 *  3    Gandalf   1.2         8/12/99  Martin Ryzl     hints on executors and 
 *       compiler, debug executors
 *  2    Gandalf   1.1         7/29/99  Martin Ryzl     executor selection is 
 *       working
 *  1    Gandalf   1.0         7/29/99  Martin Ryzl     
 * $
 */