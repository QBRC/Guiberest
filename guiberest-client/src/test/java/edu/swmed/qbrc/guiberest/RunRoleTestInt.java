package edu.swmed.qbrc.guiberest;

import cucumber.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(features = "src/test/resources/edu/swmed/qbrc/guiberest/Roles.feature")
public class RunRoleTestInt {
}