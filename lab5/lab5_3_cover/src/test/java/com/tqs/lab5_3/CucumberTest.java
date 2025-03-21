package com.tqs.lab5_3;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/tqs/lab5_3")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.tqs.lab5_3")
public class CucumberTest {
}
