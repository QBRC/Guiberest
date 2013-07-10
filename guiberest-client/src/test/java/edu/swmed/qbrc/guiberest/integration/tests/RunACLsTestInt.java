package edu.swmed.qbrc.guiberest.integration.tests;

import cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(features = "src/test/resources/edu/swmed/qbrc/guiberest/ACLs.feature", glue = { "edu.swmed.qbrc.guiberest.webapp.stepdefs" })
public class RunACLsTestInt {
}
