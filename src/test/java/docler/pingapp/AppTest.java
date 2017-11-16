package docler.pingapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import docler.pingapp.core.ICMPPing;
import docler.pingapp.core.ReportPing;
import docler.pingapp.core.TCPPing;
import docler.pingapp.core.TracertPing;
import docler.pingapp.dao.PingConfig;
import docler.pingapp.dto.ObjectTransformer;
import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */

public class AppTest extends TestCase {

	static Logger logger = Logger.getLogger(AppTest.class.getName());

	static PingConfig pc;
	static {
		try {
			FileInputStream fis = new FileInputStream("logging.properties");
			LogManager.getLogManager().readConfiguration(fis);
			pc = new PingConfig();
			pc = (PingConfig) new ObjectTransformer().deserializeObj("PingConfig.json");
			TCPPing.setMaxResponseTime(pc.getTcpMaxResponseTime() * 1000);
			ReportPing.setURL(pc.getReportUrl());
			ObjectTransformer.setIcmpLog(pc.getIcmplogFile());
			ObjectTransformer.setTcpLog(pc.getTcplogFile());
			ObjectTransformer.setTraceLog(pc.getTracelogFile());
		} catch (IOException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Test ICMP command
	 */

	@org.testng.annotations.Test(priority = 1)
	public void testIcmpCommand() throws Exception {

		StringBuffer sbuf = new ICMPPing(pc.getHostNameX()).call();
		assertNotNull(sbuf);
	}

	/**
	 * Test TCP command
	 */
	@org.testng.annotations.Test(priority = 2)
	public void testTcpCommand() throws Exception {

		StringBuffer sbuf = new TCPPing(pc.getHostNameX()).call();
		assertNotNull(sbuf);
	}

	/**
	 * Test Trace command
	 */
	@org.testng.annotations.Test(priority = 2)
	public void testTraceCommand() throws Exception {

		StringBuffer sbuf = new TracertPing(pc.getHostNameX()).call();
		assertNotNull(sbuf);
	}

}