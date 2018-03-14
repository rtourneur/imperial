package com.raf.imperial.jpa.domain.card;

import java.util.List;

import com.raf.fwk.jpa.domain.AbstractNamedEntity;
import com.raf.imperial.jpa.domain.model.Expansion;
import com.raf.imperial.jpa.enums.MissionTypeEnum;

public class Mission extends AbstractNamedEntity {

  
  private MissionTypeEnum missionType;
  
  private Expansion expansion;
  
  private List<MissionDeployment> initial;
  
  private List<MissionDeployment> reserved;
  
  
}
