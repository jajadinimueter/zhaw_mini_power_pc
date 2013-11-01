package ch.zhaw.inf3.fmuellerbfuchs.minipowerpc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 */
@RunWith(value=Suite.class)
@SuiteClasses(value = {ParserTest.class,
                       UtilTest.class,
                       OperationsTest.class})
public class MiniPpcSuite {}
