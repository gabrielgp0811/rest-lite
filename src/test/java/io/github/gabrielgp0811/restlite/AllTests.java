/**
 * 
 */
package io.github.gabrielgp0811.restlite;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author gabrielgp0811
 */
@Suite
@SelectClasses({
	ConvertersTest.class,
	RestServiceTest.class,
	UserConsumerTest.class
})
public class AllTests {

}