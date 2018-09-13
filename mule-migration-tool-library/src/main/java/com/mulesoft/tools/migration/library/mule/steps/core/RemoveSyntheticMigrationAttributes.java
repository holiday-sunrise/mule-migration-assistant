/*
 * Copyright (c) 2017 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master Subscription
 * Agreement (or other master license agreement) separately entered into in writing between
 * you and MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.tools.migration.library.mule.steps.core;

import static java.util.stream.Collectors.toList;

import com.mulesoft.tools.migration.step.AbstractApplicationModelMigrationStep;
import com.mulesoft.tools.migration.step.category.MigrationReport;

import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 * Remove isMessageSource attribute that is used for migration purposes.
 *
 * @author Mulesoft Inc.
 * @since 1.0.0
 */
public class RemoveSyntheticMigrationAttributes extends AbstractApplicationModelMigrationStep {

  public static final String XPATH_SELECTOR = "//*[@*[namespace-uri() = 'migration']]";

  @Override
  public String getDescription() {
    return "Update Remove Session Variable namespace to compatibility.";
  }

  public RemoveSyntheticMigrationAttributes() {
    this.setAppliedTo(XPATH_SELECTOR);
  }

  @Override
  public void execute(Element element, MigrationReport report) throws RuntimeException {
    element.getAttributes()
        .stream()
        .filter(att -> att.getNamespace().equals(Namespace.getNamespace("migration")))
        .collect(toList())
        .forEach(att -> att.detach());
  }
}