/*
  Copyright (C) 2004-2007 University Corporation for Advanced Internet Development, Inc.
  Copyright (C) 2004-2007 The University Of Chicago

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

package edu.internet2.middleware.grouper.member;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import junit.textui.TestRunner;

import org.apache.commons.logging.Log;

import edu.internet2.middleware.grouper.Field;
import edu.internet2.middleware.grouper.FieldFinder;
import edu.internet2.middleware.grouper.Group;
import edu.internet2.middleware.grouper.GroupSave;
import edu.internet2.middleware.grouper.GroupType;
import edu.internet2.middleware.grouper.GrouperSession;
import edu.internet2.middleware.grouper.Member;
import edu.internet2.middleware.grouper.MemberFinder;
import edu.internet2.middleware.grouper.Membership;
import edu.internet2.middleware.grouper.MembershipFinder;
import edu.internet2.middleware.grouper.RegistrySubject;
import edu.internet2.middleware.grouper.Stem;
import edu.internet2.middleware.grouper.StemFinder;
import edu.internet2.middleware.grouper.StemSave;
import edu.internet2.middleware.grouper.SubjectFinder;
import edu.internet2.middleware.grouper.Stem.Scope;
import edu.internet2.middleware.grouper.cfg.ApiConfig;
import edu.internet2.middleware.grouper.exception.GrantPrivilegeException;
import edu.internet2.middleware.grouper.exception.GroupAddException;
import edu.internet2.middleware.grouper.exception.InsufficientPrivilegeException;
import edu.internet2.middleware.grouper.exception.MemberNotFoundException;
import edu.internet2.middleware.grouper.exception.SchemaException;
import edu.internet2.middleware.grouper.exception.SessionException;
import edu.internet2.middleware.grouper.exception.StemAddException;
import edu.internet2.middleware.grouper.helper.GroupHelper;
import edu.internet2.middleware.grouper.helper.GrouperTest;
import edu.internet2.middleware.grouper.helper.R;
import edu.internet2.middleware.grouper.helper.SessionHelper;
import edu.internet2.middleware.grouper.helper.SubjectTestHelper;
import edu.internet2.middleware.grouper.helper.T;
import edu.internet2.middleware.grouper.hibernate.HibernateSession;
import edu.internet2.middleware.grouper.internal.dao.QueryOptions;
import edu.internet2.middleware.grouper.internal.util.GrouperUuid;
import edu.internet2.middleware.grouper.membership.MembershipType;
import edu.internet2.middleware.grouper.misc.CompositeType;
import edu.internet2.middleware.grouper.misc.GrouperDAOFactory;
import edu.internet2.middleware.grouper.privs.AccessPrivilege;
import edu.internet2.middleware.grouper.privs.NamingPrivilege;
import edu.internet2.middleware.grouper.util.GrouperUtil;
import edu.internet2.middleware.subject.Source;
import edu.internet2.middleware.subject.Subject;
import edu.internet2.middleware.subject.provider.SourceManager;

/**
 * Test {@link Member}.
 * <p />
 * @author  blair christensen.
 * @version $Id$
 */
public class TestMember1 extends GrouperTest {

  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    TestRunner.run(new TestMember1("testStemGroupPrivs"));
  }
  
  /** logger */
  private static final Log LOG = GrouperUtil.getLog(TestMember1.class);

  /**
   * 
   * @param name
   */
  public TestMember1(String name) {
    super(name);
  }

  // Tests

  /**
   * 
   */
  public void testStemGroupPrivs() {
    
    GrouperSession grouperSession = GrouperSession.startRootSession();
    
    Subject subject0 = SubjectTestHelper.SUBJ0;
    Member member0 = MemberFinder.findBySubject(grouperSession, SubjectTestHelper.SUBJ0, true);
    Subject subject1 = SubjectTestHelper.SUBJ1;
    Member member1 = MemberFinder.findBySubject(grouperSession, SubjectTestHelper.SUBJ1, true);
    Subject subject2 = SubjectTestHelper.SUBJ2;
    Member member2 = MemberFinder.findBySubject(grouperSession, SubjectTestHelper.SUBJ2, true);
    Subject subject3 = SubjectTestHelper.SUBJ3;
    Member member3 = MemberFinder.findBySubject(grouperSession, SubjectTestHelper.SUBJ3, true);
    Subject subject4 = SubjectTestHelper.SUBJ4;
    Member member4 = MemberFinder.findBySubject(grouperSession, SubjectTestHelper.SUBJ4, true);
    Subject subject5 = SubjectTestHelper.SUBJ5;
    Member member5 = MemberFinder.findBySubject(grouperSession, SubjectTestHelper.SUBJ5, true);
    
    Stem parent = new StemSave(grouperSession).assignName("parent").save();
    Stem stem = new StemSave(grouperSession).assignName("stem").save();
    @SuppressWarnings("unused")
    Stem stem2 = new StemSave(grouperSession).assignName("stem2").save();
    Stem child = new StemSave(grouperSession).assignName("parent:child").save();
    
    Group parentGroup0 = new GroupSave(grouperSession).assignName("parent:parentGroup0").save();
    Group parentGroup1 = new GroupSave(grouperSession).assignName("parent:parentGroup1").save();
    Group parentGroup2 = new GroupSave(grouperSession).assignName("parent:parentGroup2").save();
    Group parentGroup3 = new GroupSave(grouperSession).assignName("parent:parentGroup3").save();
    Group parentGroup4 = new GroupSave(grouperSession).assignName("parent:parentGroup4").save();
    Group parentGroup5 = new GroupSave(grouperSession).assignName("parent:parentGroup5").save();
    
    Group stemGroup0 = new GroupSave(grouperSession).assignName("stem:stemGroup0").save();
    Group stemGroup1 = new GroupSave(grouperSession).assignName("stem:stemGroup1").save();
    Group stemGroup2 = new GroupSave(grouperSession).assignName("stem:stemGroup2").save();
    Group stemGroup3 = new GroupSave(grouperSession).assignName("stem:stemGroup3").save();
    Group stemGroup4 = new GroupSave(grouperSession).assignName("stem:stemGroup4").save();
    Group stemGroup5 = new GroupSave(grouperSession).assignName("stem:stemGroup5").save();
    
    @SuppressWarnings("unused")
    Group stem2Group0 = new GroupSave(grouperSession).assignName("stem2:stemGroup0").save();
    @SuppressWarnings("unused")
    Group stem2Group1 = new GroupSave(grouperSession).assignName("stem2:stemGroup1").save();
    @SuppressWarnings("unused")
    Group stem2Group2 = new GroupSave(grouperSession).assignName("stem2:stemGroup2").save();
    @SuppressWarnings("unused")
    Group stem2Group3 = new GroupSave(grouperSession).assignName("stem2:stemGroup3").save();
    @SuppressWarnings("unused")
    Group stem2Group4 = new GroupSave(grouperSession).assignName("stem2:stemGroup4").save();
    @SuppressWarnings("unused")
    Group stem2Group5 = new GroupSave(grouperSession).assignName("stem2:stemGroup5").save();
    
    Group childGroup0 = new GroupSave(grouperSession).assignName("parent:child:childGroup0").save();
    Group childGroup1 = new GroupSave(grouperSession).assignName("parent:child:childGroup1").save();
    Group childGroup2 = new GroupSave(grouperSession).assignName("parent:child:childGroup2").save();
    Group childGroup3 = new GroupSave(grouperSession).assignName("parent:child:childGroup3").save();
    Group childGroup4 = new GroupSave(grouperSession).assignName("parent:child:childGroup4").save();
    Group childGroup5 = new GroupSave(grouperSession).assignName("parent:child:childGroup5").save();
    
    parentGroup0.grantPriv(subject0, AccessPrivilege.ADMIN, false);
    parentGroup1.grantPriv(subject1, AccessPrivilege.OPTIN, false);
    parentGroup2.grantPriv(subject2, AccessPrivilege.OPTOUT, false);
    parentGroup3.grantPriv(subject3, AccessPrivilege.READ, false);
    parentGroup4.grantPriv(subject4, AccessPrivilege.UPDATE, false);
    parentGroup5.grantPriv(subject5, AccessPrivilege.VIEW, false);
    
    stemGroup0.grantPriv(subject0, AccessPrivilege.ADMIN, false);
    stemGroup1.grantPriv(subject1, AccessPrivilege.OPTIN, false);
    stemGroup2.grantPriv(subject2, AccessPrivilege.OPTOUT, false);
    stemGroup3.grantPriv(subject3, AccessPrivilege.READ, false);
    stemGroup4.grantPriv(subject4, AccessPrivilege.UPDATE, false);
    stemGroup5.grantPriv(subject5, AccessPrivilege.VIEW, false);

    childGroup0.grantPriv(subject0, AccessPrivilege.ADMIN, false);
    childGroup1.grantPriv(subject1, AccessPrivilege.OPTIN, false);
    childGroup2.grantPriv(subject2, AccessPrivilege.OPTOUT, false);
    childGroup3.grantPriv(subject3, AccessPrivilege.READ, false);
    childGroup4.grantPriv(subject4, AccessPrivilege.UPDATE, false);
    childGroup5.grantPriv(subject5, AccessPrivilege.VIEW, false);

    assertEquals(3, member0.hasAdmin().size());
    assertEquals(0, member0.hasOptin().size());
    assertEquals(0, member0.hasOptout().size());
    assertEquals(0, member0.hasRead().size());
    assertEquals(0, member0.hasUpdate().size());
    assertEquals(0, member0.hasView().size());
    assertTrue(member0.hasAdmin(parentGroup0));
    assertTrue(member0.hasAdmin(stemGroup0));
    assertTrue(member0.hasAdmin(childGroup0));

    assertEquals(0, member1.hasAdmin().size());
    assertEquals(3, member1.hasOptin().size());
    assertEquals(0, member1.hasOptout().size());
    assertEquals(0, member1.hasRead().size());
    assertEquals(0, member1.hasUpdate().size());
    assertEquals(0, member1.hasView().size());
    assertTrue(member1.hasOptin(parentGroup1));
    assertTrue(member1.hasOptin(stemGroup1));
    assertTrue(member1.hasOptin(childGroup1));
    
    assertEquals(0, member2.hasAdmin().size());
    assertEquals(0, member2.hasOptin().size());
    assertEquals(3, member2.hasOptout().size());
    assertEquals(0, member2.hasRead().size());
    assertEquals(0, member2.hasUpdate().size());
    assertEquals(0, member2.hasView().size());
    assertTrue(member2.hasOptout(parentGroup2));
    assertTrue(member2.hasOptout(stemGroup2));
    assertTrue(member2.hasOptout(childGroup2));

    assertEquals(0, member3.hasAdmin().size());
    assertEquals(0, member3.hasOptin().size());
    assertEquals(0, member3.hasOptout().size());
    assertEquals(3, member3.hasRead().size());
    assertEquals(0, member3.hasUpdate().size());
    assertEquals(0, member3.hasView().size());
    assertTrue(member3.hasRead(parentGroup3));
    assertTrue(member3.hasRead(stemGroup3));
    assertTrue(member3.hasRead(childGroup3));

    assertEquals(0, member4.hasAdmin().size());
    assertEquals(0, member4.hasOptin().size());
    assertEquals(0, member4.hasOptout().size());
    assertEquals(0, member4.hasRead().size());
    assertEquals(3, member4.hasUpdate().size());
    assertEquals(0, member4.hasView().size());
    assertTrue(member4.hasUpdate(parentGroup4));
    assertTrue(member4.hasUpdate(stemGroup4));
    assertTrue(member4.hasUpdate(childGroup4));

    assertEquals(0, member5.hasAdmin().size());
    assertEquals(0, member5.hasOptin().size());
    assertEquals(0, member5.hasOptout().size());
    assertEquals(0, member5.hasRead().size());
    assertEquals(0, member5.hasUpdate().size());
    assertEquals(3, member5.hasView().size());
    assertTrue(member5.hasView(parentGroup5));
    assertTrue(member5.hasView(stemGroup5));
    assertTrue(member5.hasView(childGroup5));

    assertEquals(3, member0.hasAdminInStem().size());
    assertEquals(0, member0.hasOptinInStem().size());
    assertEquals(0, member0.hasOptoutInStem().size());
    assertEquals(0, member0.hasReadInStem().size());
    assertEquals(0, member0.hasUpdateInStem().size());
    assertEquals(0, member0.hasViewInStem().size());
    assertTrue(member0.hasAdminInStem().contains(parent));
    assertTrue(member0.hasAdminInStem().contains(child));
    assertTrue(member0.hasAdminInStem().contains(stem));
    
    assertEquals(0, member1.hasAdminInStem().size());
    assertEquals(3, member1.hasOptinInStem().size());
    assertEquals(0, member1.hasOptoutInStem().size());
    assertEquals(0, member1.hasReadInStem().size());
    assertEquals(0, member1.hasUpdateInStem().size());
    assertEquals(0, member1.hasViewInStem().size());
    assertTrue(member1.hasOptinInStem().contains(parent));
    assertTrue(member1.hasOptinInStem().contains(child));
    assertTrue(member1.hasOptinInStem().contains(stem));
    
    assertEquals(0, member2.hasAdminInStem().size());
    assertEquals(0, member2.hasOptinInStem().size());
    assertEquals(3, member2.hasOptoutInStem().size());
    assertEquals(0, member2.hasReadInStem().size());
    assertEquals(0, member2.hasUpdateInStem().size());
    assertEquals(0, member2.hasViewInStem().size());
    assertTrue(member2.hasOptoutInStem().contains(parent));
    assertTrue(member2.hasOptoutInStem().contains(child));
    assertTrue(member2.hasOptoutInStem().contains(stem));
    
    assertEquals(0, member3.hasAdminInStem().size());
    assertEquals(0, member3.hasOptinInStem().size());
    assertEquals(0, member3.hasOptoutInStem().size());
    assertEquals(3, member3.hasReadInStem().size());
    assertEquals(0, member3.hasUpdateInStem().size());
    assertEquals(0, member3.hasViewInStem().size());
    assertTrue(member3.hasReadInStem().contains(parent));
    assertTrue(member3.hasReadInStem().contains(child));
    assertTrue(member3.hasReadInStem().contains(stem));
    
    assertEquals(0, member4.hasAdminInStem().size());
    assertEquals(0, member4.hasOptinInStem().size());
    assertEquals(0, member4.hasOptoutInStem().size());
    assertEquals(0, member4.hasReadInStem().size());
    assertEquals(3, member4.hasUpdateInStem().size());
    assertEquals(0, member4.hasViewInStem().size());
    assertTrue(member4.hasUpdateInStem().contains(parent));
    assertTrue(member4.hasUpdateInStem().contains(child));
    assertTrue(member4.hasUpdateInStem().contains(stem));
    
    assertEquals(0, member5.hasAdminInStem().size());
    assertEquals(0, member5.hasOptinInStem().size());
    assertEquals(0, member5.hasOptoutInStem().size());
    assertEquals(0, member5.hasReadInStem().size());
    assertEquals(0, member5.hasUpdateInStem().size());
    assertEquals(3, member5.hasViewInStem().size());
    assertTrue(member5.hasViewInStem().contains(parent));
    assertTrue(member5.hasViewInStem().contains(child));
    assertTrue(member5.hasViewInStem().contains(stem));
    
  }

  /**
   * @see edu.internet2.middleware.grouper.helper.GrouperTest#setUp()
   */
  @Override
  protected void setUp() {
    super.setUp();
    ApiConfig.testConfig.put("groups.create.grant.all.read", "false");
    ApiConfig.testConfig.put("groups.create.grant.all.view", "false");
  }

}

