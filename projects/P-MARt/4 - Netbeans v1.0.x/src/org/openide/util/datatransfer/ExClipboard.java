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

package org.openide.util.datatransfer;

import java.io.IOException;
import java.awt.datatransfer.*;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.swing.event.EventListenerList;

// and holding a list of services built over data flavors. [???]
/** Extended clipboard that supports listeners that can be notified about
* changes of content. Also contains support for attaching content convertors.
*
* @author Jaroslav Tulach
*/
public abstract class ExClipboard extends Clipboard {
    /** listeners */
    private EventListenerList listeners = new EventListenerList ();

    /** Make a new clipboard.
    * @param name name of the clipboard
    */
    public ExClipboard (String name) {
        super (name);
    }

    /** Add a listener to clipboard operations.
    * @param list the listener
    */
    public final void addClipboardListener (ClipboardListener list) {
        listeners.add (ClipboardListener.class, list);
    }

    /** Remove a listener to clipboard operations.
    * @param list the listener
    */
    public final void removeClipboardListener (ClipboardListener list) {
        listeners.remove (ClipboardListener.class, list);
    }

    /** Fires event about change of content in the clipboard.
    */
    protected final void fireClipboardChange () {
        Object[] l = listeners.getListenerList ();
        ClipboardEvent ev = null;
        for (int i = l.length - 2; i >= 0; i -= 2) {
            ClipboardListener list = (ClipboardListener)l[i + 1];
            if (ev == null) {
                ev = new ClipboardEvent (this);
            }
            list.clipboardChanged (ev);
        }
    }

    /** Obtain a list of convertors assigned to
    * this clipboard.
    * @return the convertors
    */
    protected abstract Convertor[] getConvertors ();


    /** Method that takes a transferable, applies all convertors,
    * and creates a new transferable using the abilities of the
    * convertors.
    * <P>
    * This method is used when the contents of the clipboard are changed and
    * also can be used by Drag &amp; Drop to process transferables between source
    * and target.
    * <p>
    * Note that it is possible for the results to vary according to order
    * of the convertors as specified by {@link #getConvertors}. For example,
    * the input transferable may contain flavor A, and there may be a convertor
    * from A to B, and one from B to C; flavor B will always be available, but
    * flavor C will only be available if the convertor list is in the order
    * that these were mentioned. If it is desired, the APIs could be enhanced
    * to specify a partial ordering of convertors in the manifest.
    *
    * @param t input transferable
    * @return new transferable
    */
    public Transferable convert (Transferable t) {
        Convertor[] convertors = getConvertors ();
        for (int i = 0; i < convertors.length; i++)
            t = convertors[i].convert (t);
        return t;
    }

    /** Notifies the transferable that it has been accepted by a drop.
    * Works only for ExTransferable, other types of transferables are 
    * not notified.
    *
    * @param t transferable to notify its listeners
    * @param action which action has been performed
    */
    public static void transferableAccepted (Transferable t, int action) {
        if (t instanceof ExTransferable) {
            ((ExTransferable)t).fireAccepted (action);
        }
    }

    /** Notifies the transferable that it has been rejected by a drop.
    * Works only for ExTransferable, other types of transferables are 
    * not notified.
    *
    * @param t transferable to notify its listeners
    */
    public static void transferableRejected (Transferable t) {
        if (t instanceof ExTransferable) {
            ((ExTransferable)t).fireRejected ();
        }
    }

    /** Notifies the transferable that it has lost ownership in clipboard.
    * Works only for ExTransferable, other types of transferables are 
    * not notified.
    *
    * @param t transferable to notify its listeners
    */
    public static void transferableOwnershipLost (Transferable t) {
        if (t instanceof ExTransferable) {
            ((ExTransferable)t).fireOwnershipLost ();
        }
    }

    /** Convertor that can convert the {@link Transferable contents} of a clipboard to
    * additional {@link DataFlavor flavors}.
    */
    public static interface Convertor {

        /** Convert a given transferable to a new transferable,
        * generally one which adds new flavors based on the existing flavors.
        * The recommended usage is as follows:
        *
        * <br><code><pre>
        * public Transferable convert (final Transferable t) {
        *   if (! t.isDataFlavorSupported (fromFlavor)) return t;
        *   if (t.isDataFlavorSupported (toFlavor)) return t;
        *   ExTransferable et = ExTransferable.create (t);
        *   et.put (new ExTransferable.Single (toFlavor) {
        *     public Object getData () throws IOException, UnsupportedFlavorException {
        *       FromObject from = (FromObject) t.getTransferData (fromFlavor);
        *       ToObject to = translateFormats (from);
        *       return to;
        *     }
        *   });
        *   return et;
        * }
        * </pre></code>
        *
        * <br>Note that this does not perform the conversion until <code>toFlavor</code> is
        * actually requested, nor does it advertise <code>toFlavor</code> as being available
        * unless <code>fromFlavor</code> already was.
        *
        * <p>You may also want to convert some flavor to a node selection, in which case you should do:
        *
        * <br><code><pre>
        * public Transferable convert (final Transferable t) {
        *   if (! t.isDataFlavorSupported (DataFlavor.stringFlavor)) return t;
        *   if (NodeTransfer.findPaste (t) != null) return t;
        *   ExTransferable et = ExTransferable.create (t);
        *   et.put (NodeTransfer.createPaste (new NodeTransfer.Paste () {
        *     public PasteType[] types (Node target) {
        *       if (isSuitable (target)) {
        *         return new PasteType[] { new PasteType () {
        *           public Transferable paste () throws IOException {
        *             try {
        *               String s = (String) t.getTransferData (DataFlavor.stringFlavor);
        *               addNewSubnode (target, s);
        *             } catch (UnsupportedFlavorException ufe) {
        *               throw new IOException (ufe.toString ());
        *             }
        *             return t;
        *           }
        *         }};
        *       } else {
        *         return new PasteType[0];
        *       }
        *     }
        *   }));
        *   return et;
        * }
        * </pre></code>
        *
        * <p>Convertors should generally avoid removing flavors from the transferable,
        * or changing the data for an existing flavor.
        *
        * @param t the incoming basic transferable
        * @return a possible enhanced transferable
        */
        public Transferable convert (Transferable t);

    }

}

/*
 * Log
 *  8    Gandalf   1.7         10/22/99 Ian Formanek    NO SEMANTIC CHANGE - Sun
 *       Microsystems Copyright in File Comment
 *  7    Gandalf   1.6         9/22/99  Jesse Glick     ExClipboard.Convertor 
 *       changed signature to accommodate converting to node selection.
 *  6    Gandalf   1.5         6/30/99  Jaroslav Tulach Drag and drop support
 *  5    Gandalf   1.4         6/8/99   Ian Formanek    ---- Package Change To 
 *       org.openide ----
 *  4    Gandalf   1.3         3/10/99  Jesse Glick     [JavaDoc]
 *  3    Gandalf   1.2         2/21/99  Jaroslav Tulach Clipboard convertors
 *  2    Gandalf   1.1         2/10/99  Jaroslav Tulach Clipboard improved.
 *  1    Gandalf   1.0         1/5/99   Ian Formanek    
 * $
 */