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

/**
 * Enum used in (@link Extension) to indicate the compatability
 * of one extension to another. See (@link Extension) for instances
 * of object.
 *
 * WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
 *  This file is from excalibur.extension package. Dont edit this file
 * directly as there is no unit tests to make sure it is operational
 * in ant. Edit file in excalibur and run tests there before changing
 * ants file.
 * WARNING WARNING WARNING WARNING WARNING WARNING WARNING WARNING
 *
 * @version $Revision$ $Date$
 * @see Extension
 */
public final class Compatability {
    /**
     * A string representaiton of compatability level.
     */
    private final String name;

    /**
     * Create a compatability enum with specified name.
     *
     * @param name the name of compatability level
     */
    Compatability(final String name) {
        this.name = name;
    }

    /**
     * Return name of compatability level.
     *
     * @return the name of compatability level
     */
    public String toString() {
        return name;
    }
}
