/*
 *                 Sun Public License Notice
 * 
 * The contents of this file are subject to the Sun Public License
 * Version 1.0 (the "License"). You may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.sun.com/
 * 
 * The Original Code is Forte for Java, Community Edition. The Initial
 * Developer of the Original Code is Sun Microsystems, Inc. Portions
 * Copyright 1997-2000 Sun Microsystems, Inc. All Rights Reserved.
 */

package org.netbeans.modules.icebrowser;

import org.openide.util.NbBundle;


/**
 *
 * @author Jan Jancura
 */
public class FindDialogPanel extends javax.swing.JPanel {

    static final long serialVersionUID =5048601763767383114L;

    /** Initializes the Form */
    public FindDialogPanel() {
        initComponents ();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        mainPanel = new javax.swing.JPanel ();
        findWhatPanel = new javax.swing.JPanel ();
        findWhatLabel = new javax.swing.JLabel ();
        findWhat = new javax.swing.JComboBox ();
        //    wrapSearch = new javax.swing.JCheckBox ();
        setLayout (new java.awt.BorderLayout ());

        mainPanel.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints1;

        findWhatPanel.setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints2;

        findWhatLabel.setText (NbBundle.getBundle (FindDialogPanel.class).getString ("CTL_Find_what"));

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridwidth = -1;
        gridBagConstraints2.insets = new java.awt.Insets (0, 2, 5, 5);
        gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
        findWhatPanel.add (findWhatLabel, gridBagConstraints2);

        findWhat.setEditable (true);
        findWhat.setLightWeightPopupEnabled (false);

        gridBagConstraints2 = new java.awt.GridBagConstraints ();
        gridBagConstraints2.gridwidth = 0;
        gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints2.insets = new java.awt.Insets (0, 5, 5, 5);
        gridBagConstraints2.weightx = 1.0;
        findWhatPanel.add (findWhat, gridBagConstraints2);

        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 0;
        gridBagConstraints1.gridwidth = 2;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.insets = new java.awt.Insets (5, 5, 8, 5);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints1.weightx = 1.0;
        mainPanel.add (findWhatPanel, gridBagConstraints1);

        /*    wrapSearch.setToolTipText (NbBundle.getBundle (FindDialogPanel.class).getString ("CTL_Warp_search_hint") );
            wrapSearch.setText (NbBundle.getBundle (FindDialogPanel.class).getString ("CTL_Warp_search"));
        */
        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 2;
        gridBagConstraints1.insets = new java.awt.Insets (0, 5, 2, 5);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints1.weightx = 1.0;
        gridBagConstraints1.weighty = 1.0;
        //    mainPanel.add (wrapSearch, gridBagConstraints1);
        mainPanel.add (new javax.swing.JPanel (), gridBagConstraints1);


        add (mainPanel, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JPanel mainPanel;
    javax.swing.JPanel findWhatPanel;
    javax.swing.JLabel findWhatLabel;
    javax.swing.JComboBox findWhat;
    //  javax.swing.JCheckBox wrapSearch;
    // End of variables declaration//GEN-END:variables

}

/*
 * Log
 *  1    Gandalf   1.0         12/23/99 Jan Jancura     
 * $
 */