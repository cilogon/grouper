/*
  Copyright 2004-2005 University Corporation for Advanced Internet Development, Inc.
  Copyright 2004-2005 The University Of Chicago

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

package test.edu.internet2.middleware.grouper;

import  edu.internet2.middleware.grouper.*;
import  edu.internet2.middleware.subject.*;
import  edu.internet2.middleware.subject.provider.*;
import  java.util.*;
import  junit.framework.*;

/**
 * Test use of the STEM {@link NamingPrivilege} with {@link Group}
 * subjects.
 * <p />
 * @author  blair christensen.
 * @version $Id: TestPrivGroupSTEM.java,v 1.1 2005-12-02 18:39:42 blair Exp $
 */
public class TestPrivGroupSTEM extends TestCase {

  // Private Class Constants
  private static final Privilege PRIV = NamingPrivilege.STEM;


  // Private Class Variables
  private static Group          uofc;
  private static Stem           nrroot;
  private static GrouperSession nrs;
  private static GrouperSession s;
  private static Stem           root;
  private static Stem           i2;


  public TestPrivGroupSTEM(String name) {
    super(name);
  }

  protected void setUp () {
    Db.refreshDb();
    s       = SessionHelper.getRootSession();
    root    = StemHelper.findRootStem(s);
    i2      = StemHelper.addChildStem(root, "i2", "internet2");
    uofc    = StemHelper.addChildGroup(i2, "uofc", "uchicago");
    nrs     = SessionHelper.getSession(uofc.toSubject().getId());
    nrroot  = StemHelper.findRootStem(nrs);
  }

  protected void tearDown () {
    // Nothing 
  }

  // Tests

/* TODO
  public void testGrantedToCreator() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Get root stem and grant STEM on it to !root subject
    Stem            root    = StemHelper.findRootStem(s);
    PrivHelper.grantPriv(s, root, nrs.getSubject(), PRIV);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Create stem as !root subject.  
    Stem            edu     = StemHelper.addChildStem(nrroot, "edu", "education");
    // !root subject should have STEM on the new child stem.
    PrivHelper.hasPriv(nrs, edu, nrs.getSubject(), PRIV, true);
  } // public void testGrantedToCreator()
*/

  // Grant CREATE without STEM 
  public void testGrantCreateFail() {
    // Now get root as !root subject 
    Stem nrroot  = StemHelper.findRootStem(nrs);
    // Now fail to grant priv as !root to another !root
    PrivHelper.grantPrivFail(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE); 
  } // public void testGrantCreateFail()

  // Grant CREATE with STEM 
  public void testGrantCreate() {
    // Grant STEM on root stem to !root subject
    PrivHelper.grantPriv(s, root, nrs.getSubject(), PRIV);
    // Now grant priv as !root to another !root
    PrivHelper.grantPriv(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE);
    // Other !root should now have STEM 
    PrivHelper.hasPriv(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE, true);
  } // public void testGrantCreate()

/*
  // Grant STEM without STEM 
  public void testGrantStemFail() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now fail to grant priv as !root to another !root
    PrivHelper.grantPrivFail(nrs, nrroot, SubjectHelper.SUBJ1, PRIV); 
  } // public void testGrantStemFail()

  // Grant STEM with STEM 
  public void testGrantStem() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Get root stem and grant STEM on it to !root subject
    Stem            root    = StemHelper.findRootStem(s);
    PrivHelper.grantPriv(s, root, nrs.getSubject(), PRIV);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now grant priv as !root to another !root
    PrivHelper.grantPriv(nrs, nrroot, SubjectHelper.SUBJ1, PRIV);
    // Other !root should now have STEM 
    PrivHelper.hasPriv(nrs, nrroot, SubjectHelper.SUBJ1, PRIV, true);
  } // public void testGrantStem()

  // Revoke all CREATE without STEM 
  public void testRevokeAllCreateFail() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now fail to revoke priv as !root 
    PrivHelper.revokePrivFail(nrs, nrroot, NamingPrivilege.CREATE); 
  } // public void testRevokeCreateFail()

  // Revoke all CREATE with STEM 
  public void testRevokeAllCreate() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Get root stem and grant STEM on it to !root subject
    Stem            root    = StemHelper.findRootStem(s);
    PrivHelper.grantPriv(s, root, nrs.getSubject(), PRIV);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now grant priv as !root to another !root
    PrivHelper.grantPriv(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE);
    // Other !root should now have CREATE 
    PrivHelper.hasPriv(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE, true);
    // Now revoke priv as !root 
    PrivHelper.revokePriv(nrs, nrroot, NamingPrivilege.CREATE);
    // Other !root should now not have CREATE 
    PrivHelper.hasPriv(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE, false);
  } // public void testRevokeAllCreate()

  // Revoke all STEM without STEM 
  public void testRevokeAllStemFail() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now fail to revoke priv as !root from another !root
    PrivHelper.revokePrivFail(nrs, nrroot, PRIV);
  } // public void testRevokeAllStemFail()

  // Revoke all STEM with STEM 
  public void testRevokeAllStem() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Get root stem and grant STEM on it to !root subject
    Stem            root    = StemHelper.findRootStem(s);
    PrivHelper.grantPriv(s, root, nrs.getSubject(), PRIV);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now grant priv as !root to another !root
    PrivHelper.grantPriv(nrs, nrroot, SubjectHelper.SUBJ1, PRIV);
    // Other !root should now have priv 
    PrivHelper.hasPriv(nrs, nrroot, SubjectHelper.SUBJ1, PRIV, true);
    // Now revoke priv as !root 
    PrivHelper.revokePriv(nrs, nrroot, PRIV);
    // Other !root should no longer have priv 
    PrivHelper.hasPriv(nrs, nrroot, SubjectHelper.SUBJ1, PRIV, false);
  } // public void testRevokeAllStem()

  // Revoke CREATE without STEM 
  public void testRevokeCreateFail() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now fail to revoke priv as !root from another !root
    PrivHelper.revokePrivFail(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE); 
  } // public void testRevokeCreateFail()

  // Revoke CREATE with STEM 
  public void testRevokeCreate() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Get root stem and grant STEM on it to !root subject
    Stem            root    = StemHelper.findRootStem(s);
    PrivHelper.grantPriv(s, root, nrs.getSubject(), PRIV);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now grant priv as !root to another !root
    PrivHelper.grantPriv(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE);
    // Other !root should now have CREATE 
    PrivHelper.hasPriv(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE, true);
    // Now revoke priv as !root from another !root
    PrivHelper.revokePriv(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE);
    // Other !root should now not have CREATE 
    PrivHelper.hasPriv(nrs, nrroot, SubjectHelper.SUBJ1, NamingPrivilege.CREATE, false);
  } // public void testRevokeCreate()

  // Revoke STEM without STEM 
  public void testRevokeStemFail() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now fail to revoke priv as !root from another !root
    PrivHelper.revokePrivFail(nrs, nrroot, SubjectHelper.SUBJ1, PRIV);
  } // public void testRevokeStemFail()

  // Revoke STEM with STEM 
  public void testRevokeStem() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Get root stem and grant STEM on it to !root subject
    Stem            root    = StemHelper.findRootStem(s);
    PrivHelper.grantPriv(s, root, nrs.getSubject(), PRIV);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now grant priv as !root to another !root
    PrivHelper.grantPriv(nrs, nrroot, SubjectHelper.SUBJ1, PRIV);
    // Other !root should now have STEM 
    PrivHelper.hasPriv(nrs, nrroot, SubjectHelper.SUBJ1, PRIV, true);
    // Now revoke priv as !root from another !root
    PrivHelper.revokePriv(nrs, nrroot, SubjectHelper.SUBJ1, PRIV);
    // Other !root should no longer have STEM 
    PrivHelper.hasPriv(nrs, nrroot, SubjectHelper.SUBJ1, PRIV, false);
  } // public void testRevokeStem()

  // Create child stem without STEM
  public void testCreateChildStemFail() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Get root stem and grant STEM on it to !root subject
    Stem            root    = StemHelper.findRootStem(s);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Fail to add child stem
    StemHelper.addChildStemFail(nrroot, "edu", "educational");
  } // public void testCreateChildStemFail()

  // Create child stem with STEM
  public void testCreateChildStem() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Get root stem and grant STEM on it to !root subject
    Stem            root    = StemHelper.findRootStem(s);
    PrivHelper.grantPriv(s, root, nrs.getSubject(), PRIV);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now grant priv as !root to another !root
    PrivHelper.grantPriv(nrs, nrroot, SubjectHelper.SUBJ1, PRIV);
    // Add child stem
    StemHelper.addChildStem(nrroot, "edu", "educational");
  } // public void testCreateChildStem()

  // Modify stem attrs without STEM
  public void testModifyAttrsFail() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Get root stem and grant STEM on it to !root subject
    Stem            root    = StemHelper.findRootStem(s);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now fail to modify stem attrs
    StemHelper.setAttrFail(nrroot, "description", "foo");
    StemHelper.setAttrFail(nrroot, "displayExtension", "foo");
  } // public void testModifyAttrsFail()

  // Modify stem attrs with STEM
  public void testModifyttrs() {
    // Get root and !root sessions
    GrouperSession  s       = SessionHelper.getRootSession();
    GrouperSession  nrs     = SessionHelper.getSession(SubjectHelper.SUBJ0_ID);
    // Get root stem and grant STEM on it to !root subject
    Stem            root    = StemHelper.findRootStem(s);
    PrivHelper.grantPriv(s, root, nrs.getSubject(), PRIV);
    // Now get root as !root subject 
    Stem            nrroot  = StemHelper.findRootStem(nrs);
    // Now modify stem attrs
    StemHelper.setAttr(nrroot, "description", "foo");
    StemHelper.setAttr(nrroot, "displayExtension", "foo");
  } // public void testModifyAttrs()
*/

}

