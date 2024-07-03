package xyz.cringee;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        SetPointTest.class,
        JsonTest.class
})
public class MainTests {

}
