/*
 * Copyright (c) 2017 MuleSoft, Inc. This software is protected under international
 * copyright law. All use of this software is subject to MuleSoft's Master Subscription
 * Agreement (or other master license agreement) separately entered into in writing between
 * you and MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package com.mulesoft.tools.migration.library.mule.steps.http;

import com.mulesoft.tools.migration.step.category.MigrationReport;

import org.apache.commons.lang3.StringUtils;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 * Migrates the listener source of the HTTP Connector
 *
 * @author Mulesoft Inc.
 * @since 1.0.0
 */
public class HttpConnectorHeaders extends AbstractHttpConnectorMigrationStep {

  public static final String XPATH_SELECTOR = "//http:*[local-name()='header' or local-name()='headers']";

  @Override
  public String getDescription() {
    return "Update HTTP headers in request/response builders.";
  }

  public HttpConnectorHeaders() {
    this.setAppliedTo(XPATH_SELECTOR);
  }

  @Override
  public void execute(Element object, MigrationReport report) throws RuntimeException {
    final Namespace httpNamespace = Namespace.getNamespace("http", HTTP_NAMESPACE);
    object.setNamespace(httpNamespace);

    int idx = object.getParent().indexOf(object);

    if ("headers".equals(object.getName()) && StringUtils.isEmpty(object.getText())) {
      String headersExpr = object.getAttributeValue("expression");

      setMule4MapBuilderTagText(idx, "headers", object.getParentElement(), httpNamespace, report,
                                () -> getExpressionMigrator().migrateExpression(getExpressionMigrator().wrap(headersExpr), true),
                                expr -> getExpressionMigrator()
                                    .wrap(getExpressionMigrator().unwrap(expr) + " ++ "
                                        + getExpressionMigrator().unwrap(getExpressionMigrator()
                                            .migrateExpression(getExpressionMigrator().wrap(headersExpr), true))));


      object.getParent().removeContent(object);
      object.setText(getExpressionMigrator().migrateExpression(getExpressionMigrator().wrap(headersExpr), true));
    }
    if ("header".equals(object.getName())) {
      String headerName = object.getAttributeValue("headerName");
      String headerValue = object.getAttributeValue("value");

      String dwHeaderMapElement = "'" + headerName + "' : "
          + (getExpressionMigrator().isWrapped(headerValue)
              ? getExpressionMigrator().unwrap(getExpressionMigrator().migrateExpression(headerValue, true))
              : ("'" + headerValue + "'"));

      setMule4MapBuilderTagText(idx, "headers", object.getParentElement(), httpNamespace, report,
                                () -> getExpressionMigrator().wrap("{" + dwHeaderMapElement + "}"),
                                expr -> getExpressionMigrator()
                                    .wrap(getExpressionMigrator().unwrap(expr) + " ++ {" + dwHeaderMapElement + "}"));

      object.getParent().removeContent(object);
    }
  }

}