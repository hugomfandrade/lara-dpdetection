/*
 * Copyright  2002-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.apache.tools.ant.taskdefs.condition;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.ProjectComponent;
import org.apache.tools.ant.types.Reference;

/**
 * Condition that tests whether a given reference has been defined.
 *
 * <p>Optionally tests whether it is of a given type/class.</p>
 *
 * @since Ant 1.6
 * @version $Revision$
 */
public class IsReference extends ProjectComponent implements Condition {
    private Reference ref;
    private String type;

    /**
     * Set the refid attribute.
     *
     * @param r a Reference value
     */
    public void setRefid(Reference r) {
        ref = r;
    }

    /**
     * Set the type attribute. This is optional attribute.
     *
     * @param type an ant component type name
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return true if the reference exists and if type is set, if
     *              the reference is the same type
     * @exception BuildException if an error occurs
     */
    public boolean eval() throws BuildException {
        if (ref == null) {
            throw new BuildException("No reference specified for isreference "
                                     + "condition");
        }

        Object o = getProject().getReference(ref.getRefId());

        if (o == null) {
            return false;
        } else if (type == null) {
            return true;
        } else {
            Class typeClass =
                (Class) getProject().getDataTypeDefinitions().get(type);

            if (typeClass == null) {
                typeClass =
                    (Class) getProject().getTaskDefinitions().get(type);
            }

            if (typeClass == null) {
                // don't know the type, should throw exception instead?
                return false;
            }

            return typeClass.isAssignableFrom(o.getClass());
        }
    }

}
