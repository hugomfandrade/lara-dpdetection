/*
 * Copyright  2002,2004 The Apache Software Foundation
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
package org.apache.tools.ant.taskdefs.optional.extension;

import org.apache.tools.ant.BuildException;

/**
 * Simple holder for extra attributes in main section of manifest.
 *
 * @version $Revision$ $Date$
 * @todo Refactor this and all the other parameter, sysproperty,
 *   property etc into a single class in framework
 */
public class ExtraAttribute {
    private String name;
    private String value;

    /**
     * Set the name of the parameter.
     *
     * @param name the name of parameter
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Set the value of the parameter.
     *
     * @param value the parameter value
     */
    public void setValue(final String value) {
        this.value = value;
    }

    /**
     * Retrieve name of parameter.
     *
     * @return the name of parameter.
     */
    String getName() {
        return name;
    }

    /**
     * Retrieve the value of parameter.
     *
     * @return the value of parameter.
     */
    String getValue() {
        return value;
    }

    /**
     * Make sure that neither the name or the value
     * is null.
     *
     * @throws BuildException if the attribute is invalid.
     */
    public void validate() throws BuildException {
        if (null == name) {
            final String message = "Missing name from parameter.";
            throw new BuildException(message);
        } else if (null == value) {
            final String message = "Missing value from parameter " + name + ".";
            throw new BuildException(message);
        }
    }
}
