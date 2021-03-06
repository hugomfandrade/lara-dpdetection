/*
 * Copyright  2001-2004 The Apache Software Foundation
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

package org.apache.tools.ant.taskdefs;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.BuildFileTest;

/**
 * @version $Revision$
 */
public class TypedefTest extends BuildFileTest {

    public TypedefTest(String name) {
        super(name);
    }

    public void setUp() {
        configureProject("src/etc/testcases/taskdefs/typedef.xml");
    }

    public void testEmpty() {
        expectBuildException("empty", "required argument not specified");
    }

    public void testNoName() {
        expectBuildException("noName", "required argument not specified");
    }

    public void testNoClassname() {
        expectBuildException("noClassname", "required argument not specified");
    }

    public void testClassNotFound() {
        expectBuildException("classNotFound",
                             "classname specified doesn't exist");
    }

    public void testGlobal() {
        expectLog("testGlobal", "");
        Object ref = project.getReferences().get("global");
        assertNotNull("ref is not null", ref);
        assertEquals("org.example.types.TypedefTestType",
                     ref.getClass().getName());
    }

    public void testLocal() {
        expectLog("testLocal", "");
        Object ref = project.getReferences().get("local");
        assertNotNull("ref is not null", ref);
        assertEquals("org.example.types.TypedefTestType",
                     ref.getClass().getName());
    }

    /**
     * test to make sure that one can define a not present
     * optional type twice and then have a valid definition.
     */
    public void testDoubleNotPresent() {
        expectLogContaining("double-notpresent", "hi");
    }
}
