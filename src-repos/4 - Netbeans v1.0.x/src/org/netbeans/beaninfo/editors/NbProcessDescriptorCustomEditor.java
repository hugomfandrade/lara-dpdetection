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

package org.netbeans.beaninfo.editors;

import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import org.openide.execution.NbProcessDescriptor;
import org.openide.explorer.propertysheet.editors.EnhancedCustomPropertyEditor;
import org.openide.util.HelpCtx;

/** Custom property editor for NbProcessDescriptor class.
*
* @author  Ian Formanek
*/
public class NbProcessDescriptorCustomEditor extends javax.swing.JPanel implements EnhancedCustomPropertyEditor {
    private NbProcessDescriptorEditor editor;

    private static int DEFAULT_WIDTH = 530;
    private static int DEFAULT_HEIGHT = 400;

    static final long serialVersionUID =-2766277953540349247L;
    /** Creates new NbProcessDescriptorCustomEditor
     * @param editor the NbProcessDescriptorEditor
     */
    public NbProcessDescriptorCustomEditor (NbProcessDescriptorEditor editor) {
        this.editor = editor;
        initComponents ();
        processField.setText (editor.pd.getProcessName ());
        argumentsArea.setText (editor.pd.getArguments ());

        jPanel1.setBorder (new javax.swing.border.CompoundBorder(
                               new javax.swing.border.TitledBorder(
                                   org.openide.util.NbBundle.getBundle(NbProcessDescriptorCustomEditor.class).getString("CTL_BorderTitle")),
                               new javax.swing.border.EmptyBorder(new java.awt.Insets(8, 8, 8, 8))));

        hintArea.setText (editor.pd.getInfo ());
        HelpCtx.setHelpIDString (this, NbProcessDescriptorCustomEditor.class.getName ());
    }

    public java.awt.Dimension getPreferredSize() {
        java.awt.Dimension inh = super.getPreferredSize ();
        return new java.awt.Dimension (DEFAULT_WIDTH, Math.max (inh.height, DEFAULT_HEIGHT));
    }

    /** Get the customized property value.
    * @return the property value
    * @exception InvalidStateException when the custom property editor does not contain a valid property value
    *            (and thus it should not be set)
    */
    public Object getPropertyValue () throws IllegalStateException {
        return new NbProcessDescriptor (processField.getText (), argumentsArea.getText (), editor.pd.getInfo ());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents () {//GEN-BEGIN:initComponents
        processLabel = new javax.swing.JLabel ();
        processField = new javax.swing.JTextField ();
        jButton1 = new javax.swing.JButton ();
        argumentsLabel = new javax.swing.JLabel ();
        argumentsScrollPane = new javax.swing.JScrollPane ();
        argumentsArea = new javax.swing.JTextArea ();
        jPanel1 = new javax.swing.JPanel ();
        jScrollPane1 = new javax.swing.JScrollPane ();
        hintArea = new javax.swing.JTextArea ();
        setLayout (new java.awt.GridBagLayout ());
        java.awt.GridBagConstraints gridBagConstraints1;
        setBorder (new javax.swing.border.EmptyBorder(new java.awt.Insets(8, 8, 8, 8)));

        processLabel.setText (java.util.ResourceBundle.getBundle("org/netbeans/beaninfo/editors/Bundle").getString("CTL_NbProcessDescriptorCustomEditor.processLabel.text"));


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.insets = new java.awt.Insets (8, 8, 8, 8);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
        add (processLabel, gridBagConstraints1);



        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = -1;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints1.weightx = 3.0;
        add (processField, gridBagConstraints1);

        jButton1.setText (java.util.ResourceBundle.getBundle("org/netbeans/beaninfo/editors/Bundle").getString("CTL_NbProcessDescriptorCustomEditor.jButton1.text"));
        jButton1.addActionListener (new java.awt.event.ActionListener () {
                                        public void actionPerformed (java.awt.event.ActionEvent evt) {
                                            jButton1ActionPerformed (evt);
                                        }
                                    }
                                   );


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.insets = new java.awt.Insets (8, 8, 8, 8);
        add (jButton1, gridBagConstraints1);

        argumentsLabel.setText (java.util.ResourceBundle.getBundle("org/netbeans/beaninfo/editors/Bundle").getString("CTL_NbProcessDescriptorCustomEditor.argumentsLabel.text"));


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridx = 0;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.insets = new java.awt.Insets (0, 8, 8, 8);
        gridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add (argumentsLabel, gridBagConstraints1);



        argumentsScrollPane.setViewportView (argumentsArea);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridx = 1;
        gridBagConstraints1.gridy = 1;
        gridBagConstraints1.gridwidth = 0;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.insets = new java.awt.Insets (0, 0, 8, 8);
        gridBagConstraints1.weightx = 1.0;
        gridBagConstraints1.weighty = 1.0;
        add (argumentsScrollPane, gridBagConstraints1);

        jPanel1.setLayout (new java.awt.BorderLayout ());


        hintArea.setBackground ((java.awt.Color) javax.swing.UIManager.getDefaults ().get ("Label.background"));
        hintArea.setEditable (false);

        jScrollPane1.setViewportView (hintArea);

        jPanel1.add (jScrollPane1, java.awt.BorderLayout.CENTER);


        gridBagConstraints1 = new java.awt.GridBagConstraints ();
        gridBagConstraints1.gridwidth = 3;
        gridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints1.insets = new java.awt.Insets (0, 8, 8, 8);
        gridBagConstraints1.weightx = 7.0;
        gridBagConstraints1.weighty = 7.0;
        add (jPanel1, gridBagConstraints1);

    }//GEN-END:initComponents

    private void jButton1ActionPerformed (java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Add your handling code here:
        JFileChooser chooser = new JFileChooser ();
        chooser.setMultiSelectionEnabled (false);
        int retVal = chooser.showOpenDialog (this);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            String absolute_name = chooser.getSelectedFile ().getAbsolutePath ();
            //System.out.println("file:" + absolute_name); // NOI18N
            processField.setText (absolute_name);
        }
    }//GEN-LAST:event_jButton1ActionPerformed




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel processLabel;
    private javax.swing.JTextField processField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel argumentsLabel;
    private javax.swing.JScrollPane argumentsScrollPane;
    private javax.swing.JTextArea argumentsArea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea hintArea;
    // End of variables declaration//GEN-END:variables

}

/*
 * Log
 *  11   Gandalf   1.10        2/10/00  Karel Gardas    Arguments Key Text Area 
 *       fixed
 *  10   Gandalf   1.9         1/13/00  Petr Jiricka    i18n
 *  9    Gandalf   1.8         1/9/00   Karel Gardas    
 *  8    Gandalf   1.7         1/7/00   Karel Gardas    fixed design
 *  7    Gandalf   1.6         1/6/00   Karel Gardas    file chooser added & 
 *       fixed for using too long arguments key
 *  6    Gandalf   1.5         11/5/99  Jesse Glick     Context help jumbo 
 *       patch.
 *  5    Gandalf   1.4         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  4    Gandalf   1.3         9/24/99  Jesse Glick     "Arguments Hint" -> 
 *       "Arguments Key" (at Patrick's request).
 *  3    Gandalf   1.2         8/9/99   Ian Formanek    Generated Serial Version
 *       UID
 *  2    Gandalf   1.1         8/8/99   Ian Formanek    
 *  1    Gandalf   1.0         8/7/99   Ian Formanek    
 * $
 */