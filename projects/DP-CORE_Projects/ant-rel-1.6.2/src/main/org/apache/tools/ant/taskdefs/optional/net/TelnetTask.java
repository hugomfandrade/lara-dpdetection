/*
 * Copyright  2000,2002-2004 The Apache Software Foundation
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

package org.apache.tools.ant.taskdefs.optional.net;

import org.apache.commons.net.telnet.TelnetClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * Automates the telnet protocol.
 *
 * @version $Revision$
 */

public class TelnetTask extends Task {
    /**
     *  The userid to login with, if automated login is used
     */
    private String userid  = null;

    /**
     *  The password to login with, if automated login is used
     */
    private String password = null;

    /**
     *  The server to connect to.
     */
    private String server  = null;

    /**
     *  The tcp port to connect to.
     */
    private int port = 23;

    /**
     *  The list of read/write commands for this session
     */
    private Vector telnetTasks = new Vector();

    /**
     *  If true, adds a CR to beginning of login script
     */
    private boolean addCarriageReturn = false;

    /**
     *  Default time allowed for waiting for a valid response
     *  for all child reads.  A value of 0 means no limit.
     */
    private Integer defaultTimeout = null;

    /**
     *  Verify that all parameters are included.
     *  Connect and possibly login
     *  Iterate through the list of Reads and writes
     */
    public void execute() throws BuildException {
       /**  A server name is required to continue */
       if (server == null) {
           throw new BuildException("No Server Specified");
       }
       /**  A userid and password must appear together
        *   if they appear.  They are not required.
        */
       if (userid == null && password != null) {
           throw new BuildException("No Userid Specified");
       }
       if (password == null && userid != null) {
           throw new BuildException("No Password Specified");
       }

       /**  Create the telnet client object */
       AntTelnetClient telnet = null;
       try {
           telnet = new AntTelnetClient();
           try {
               telnet.connect(server, port);
           } catch (IOException e) {
               throw new BuildException("Can't connect to " + server);
           }
           /**  Login if userid and password were specified */
           if (userid != null && password != null) {
               login(telnet);
           }
           /**  Process each sub command */
           Enumeration tasksToRun = telnetTasks.elements();
           while (tasksToRun != null && tasksToRun.hasMoreElements()) {
               TelnetSubTask task = (TelnetSubTask) tasksToRun.nextElement();
               if (task instanceof TelnetRead && defaultTimeout != null) {
                   ((TelnetRead) task).setDefaultTimeout(defaultTimeout);
               }
               task.execute(telnet);
           }
       } finally {
           if (telnet != null) {
               try {
                   telnet.disconnect();
               } catch (IOException e) {
                   throw new BuildException("Error disconnecting from " 
                                            + server);
               }
           }
       }
    }

    /**
     *  Process a 'typical' login.  If it differs, use the read
     *  and write tasks explicitely
     */
    private void login(AntTelnetClient telnet) {
       if (addCarriageReturn) {
          telnet.sendString("\n", true);
       }
       telnet.waitForString("ogin:");
       telnet.sendString(userid, true);
       telnet.waitForString("assword:");
       telnet.sendString(password, false);
    }

    /**
     * Set the the login id to use on the server;
     * required if <tt>password</tt> is set.
     */
    public void setUserid(String u) { this.userid = u; }

    /**
     *  Set the the login password to use
     * required if <tt>userid</tt> is set.
     */
    public void setPassword(String p) { this.password = p; }

    /**
     *  Set the hostname or address of the remote server.
     */
    public void setServer(String m) { this.server = m; }

    /**
     *  Set the tcp port to connect to; default is 23.
     */
    public void setPort(int p) { this.port = p; }

    /**
     *  send a carriage return after connecting; optional, defaults to false.
     */
    public void setInitialCR(boolean b) {
       this.addCarriageReturn = b;
    }

    /**
     * set a default timeout in seconds to wait for a response,
     * zero means forever (the default)
     */
    public void setTimeout(Integer i) {
       this.defaultTimeout = i;
    }

    /**
     *  A string to wait for from the server.
     *  A subTask &lt;read&gt; tag was found.  Create the object,
     *  Save it in our list, and return it.
     */

    public TelnetSubTask createRead() {
        TelnetSubTask task = (TelnetSubTask) new TelnetRead();
        telnetTasks.addElement(task);
        return task;
    }

    /**
     *  Add text to send to the server
     *  A subTask &lt;write&gt; tag was found.  Create the object,
     *  Save it in our list, and return it.
     */
    public TelnetSubTask createWrite() {
        TelnetSubTask task = (TelnetSubTask) new TelnetWrite();
        telnetTasks.addElement(task);
        return task;
    }

    /**
     *  This class is the parent of the Read and Write tasks.
     *  It handles the common attributes for both.
     */
    public class TelnetSubTask {
        protected String taskString = "";
        public void execute(AntTelnetClient telnet)
                throws BuildException {
            throw new BuildException("Shouldn't be able instantiate a SubTask directly");
        }

        /**
         *  the message as nested text
         */
        public void addText(String s) {
            setString(getProject().replaceProperties(s));
        }

        /**
         * the message as an attribute
         */
        public void setString(String s) {
           taskString += s;
        }
    }

    /**
     *  Sends text to the connected server
     */
    public class TelnetWrite extends TelnetSubTask {
        private boolean echoString = true;
        public void execute(AntTelnetClient telnet)
               throws BuildException {
           telnet.sendString(taskString, echoString);
        }

        /**
         * Whether or not the message should be echoed to the log.
         * Defaults to <code>true</code>.
         */
        public void setEcho(boolean b) {
           echoString = b;
        }
    }

    /**
     *  Reads the output from the connected server
     *  until the required string is found or we time out.
     */
    public class TelnetRead extends TelnetSubTask {
        private Integer timeout = null;
        public void execute(AntTelnetClient telnet)
               throws BuildException {
            telnet.waitForString(taskString, timeout);
        }
        /**
         *  a timeout value that overrides any task wide timeout.
         */
        public void setTimeout(Integer i) {
           this.timeout = i;
        }

        /**
         * Sets the default timeout if none has been set already
         * @ant.attribute ignore="true"
         */
        public void setDefaultTimeout(Integer defaultTimeout) {
           if (timeout == null) {
              timeout = defaultTimeout;
           }
        }
    }

    /**
     *  This class handles the abstraction of the telnet protocol.
     *  Currently it is a wrapper around <a
     *  href="http://jakarta.apache.org/commons/net/index.html">Jakarta
     *  Commons Net</a>.
     */
    public class AntTelnetClient extends TelnetClient {
        /**
         * Read from the telnet session until the string we are
         * waiting for is found
         * @param s The string to wait on
         */
        public void waitForString(String s) {
            waitForString(s, null);
        }

        /**
         * Read from the telnet session until the string we are
         * waiting for is found or the timeout has been reached
         * @param s The string to wait on
         * @param timeout The maximum number of seconds to wait
         */
        public void waitForString(String s, Integer timeout) {
            InputStream is = this.getInputStream();
            try {
                StringBuffer sb = new StringBuffer();
                if (timeout == null || timeout.intValue() == 0) {
                    while (sb.toString().indexOf(s) == -1) {
                        sb.append((char) is.read());
                    }
                } else {
                    Calendar endTime = Calendar.getInstance();
                    endTime.add(Calendar.SECOND, timeout.intValue());
                    while (sb.toString().indexOf(s) == -1) {
                        while (Calendar.getInstance().before(endTime) &&
                               is.available() == 0) {
                            Thread.sleep(250);
                        }
                        if (is.available() == 0) {
                            log("Read before running into timeout: "
                                + sb.toString(), Project.MSG_DEBUG);
                            throw new BuildException(
                                "Response timed-out waiting for \"" + s + '\"',
                                getLocation());
                        }
                        sb.append((char) is.read());
                    }
                }
                log(sb.toString(), Project.MSG_INFO);
            } catch (BuildException be) {
                throw be;
            } catch (Exception e) {
                throw new BuildException(e, getLocation());
            }
        }

        /**
        * Write this string to the telnet session.
        * @param echoString  Logs string sent
        */
        public void sendString(String s, boolean echoString) {
            OutputStream os = this.getOutputStream();
            try {
                os.write((s + "\n").getBytes());
                if (echoString) {
                    log(s, Project.MSG_INFO);
                }
                os.flush();
            } catch (Exception e) {
                throw new BuildException(e, getLocation());
            }
        }
    }
}
