package edu.internet2.middleware.grouper.app.externalSystem;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import edu.internet2.middleware.grouper.cfg.text.GrouperTextContainer;

public class GrouperExternalSystemSubSection {

  
  private String label;
  
  private GrouperExternalSystem grouperExternalSystem;
  
  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }
  
  public GrouperExternalSystem getGrouperExternalSystem() {
    return grouperExternalSystem;
  }
  
  public void setGrouperExternalSystem(GrouperExternalSystem grouperExternalSystem) {
    this.grouperExternalSystem = grouperExternalSystem;
  }

  /**
   * if label blank, there is no heading
   * @return
   */
  public String getTitle() {
    String title = GrouperTextContainer.textOrNull("externalSystem." + this.grouperExternalSystem.getClass().getSimpleName() + ".subSection." + this.label +".title");
    if (StringUtils.isBlank(title)) {
      return label;
    }
    return title;
  }
  
  /**
   * if label blank, there is no heading
   * @return
   */
  public String getDescription() {
    String title = GrouperTextContainer.textOrNull("externalSystem." + this.grouperExternalSystem.getClass().getSimpleName() + ".subSection." + this.label + ".description");
    if (StringUtils.isBlank(title)) {
      return label;
    }
    return title;
  }

  /**
   * return config suffix to attribute
   * @return map
   */
  public Map<String, GrouperExternalSystemAttribute> retrieveAttributes() {
    Map<String, GrouperExternalSystemAttribute> results = new LinkedHashMap<String, GrouperExternalSystemAttribute>();
    for (GrouperExternalSystemAttribute grouperExternalSystemAttribute : this.grouperExternalSystem.retrieveAttributes().values()) {
      if (StringUtils.equals(this.getLabel(), grouperExternalSystemAttribute.getConfigItemMetadata().getSubSection())) {
        results.put(grouperExternalSystemAttribute.getConfigSuffix(), grouperExternalSystemAttribute);
      }
    }
    return results;
  }
  
}
