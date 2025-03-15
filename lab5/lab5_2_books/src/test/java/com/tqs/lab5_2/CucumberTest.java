package com.tqs.lab5_2;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.IncludeEngines;

import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.tqs.lab5_2")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.tqs.lab5_2")
public class CucumberTest {
}
