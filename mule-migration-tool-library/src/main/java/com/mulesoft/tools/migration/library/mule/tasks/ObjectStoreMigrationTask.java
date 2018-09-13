/*
 * Copyright (c) 2017 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master Subscription
 * Agreement (or other master license agreement) separately entered into in writing between
 * you and MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.tools.migration.library.mule.tasks;

import com.mulesoft.tools.migration.library.mule.steps.os.OSBasicOperations;
import com.mulesoft.tools.migration.library.mule.steps.os.OSConfig;
import com.mulesoft.tools.migration.library.mule.steps.os.OSDisposeStore;
import com.mulesoft.tools.migration.library.mule.steps.os.OSDualStore;
import com.mulesoft.tools.migration.library.mule.steps.os.OSPomContribution;
import com.mulesoft.tools.migration.library.mule.steps.os.OSRetrieve;
import com.mulesoft.tools.migration.library.mule.steps.os.OSRetrieveStore;
import com.mulesoft.tools.migration.library.mule.steps.os.OSStore;
import com.mulesoft.tools.migration.step.MigrationStep;
import com.mulesoft.tools.migration.task.AbstractMigrationTask;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.mulesoft.tools.migration.util.MuleVersion.MULE_3_VERSION;
import static com.mulesoft.tools.migration.util.MuleVersion.MULE_4_VERSION;

/**
 * Migration definition for Object Store connector.
 *
 * @author Mulesoft Inc.
 * @since 1.0.0
 */
public class ObjectStoreMigrationTask extends AbstractMigrationTask {

  @Override
  public String getDescription() {
    return "Migrate Object Store connector.";
  }

  @Override
  public String getTo() {
    return MULE_4_VERSION;
  }

  @Override
  public String getFrom() {
    return MULE_3_VERSION;
  }

  @Override
  public List<MigrationStep> getSteps() {
    return newArrayList(new OSConfig(),
                        new OSDualStore(),
                        new OSRetrieveStore(),
                        new OSBasicOperations(),
                        new OSStore(),
                        new OSDisposeStore(),
                        new OSRetrieve(),
                        new OSPomContribution());
  }
}