/*
  Copyright 2004-2006 University Corporation for Advanced Internet Development, Inc.
  Copyright 2004-2006 The University Of Chicago

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/

package edu.internet2.middleware.grouper;
import  junit.framework.*;
import  org.apache.commons.logging.*;

/**
 * @author  blair christensen.
 * @version $Id: TestGAttr9.java,v 1.2 2006-09-27 13:56:54 blair Exp $
 * @since   1.1.0
 */
public class TestGAttr9 extends TestCase {

  private static final Log LOG = LogFactory.getLog(TestGAttr9.class);

  public TestGAttr9(String name) {
    super(name);
  }

  protected void setUp () {
    LOG.debug("setUp");
    RegistryReset.reset();
  }

  protected void tearDown () {
    LOG.debug("tearDown");
  }

  public void testSetAttribute() {
    LOG.info("testSetAttribute");
    try {
      R     r   = R.populateRegistry(1, 1, 0);
      Group gA  = r.getGroup("a", "a");

      String  k = "description";
      String  v = "foo";
      gA.setAttribute("description", v);
      T.ok("set attr value");
      T.string(
        "updated attr value",
        v,
        gA.getAttribute(k)
      );

      r.rs.stop();
    }
    catch (Exception e) {
      T.e(e);
    }
  } // public void testSetAttribute()

} // public class TestGAttr9

