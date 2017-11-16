package docler.pingapp.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import docler.pingapp.dao.PingConfig;
import docler.pingapp.dto.ObjectTransformer;

public class App {
	
	static PingConfig pc;
	static {
		try {
						
			FileInputStream fis = new FileInputStream("logging.properties");
			LogManager.getLogManager().readConfiguration(fis);
			pc = new PingConfig();
			pc = (PingConfig) new ObjectTransformer().deserializeObj("PingConfig.json");
			TCPPing.setMaxResponseTime(pc.getTcpMaxResponseTime()*1000);
			ReportPing.setURL(pc.getReportUrl());
			ObjectTransformer.setIcmpLog(pc.getIcmplogFile());
			ObjectTransformer.setTcpLog(pc.getTcplogFile());
			ObjectTransformer.setTraceLog(pc.getTracelogFile());
		} catch (IOException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException,Exception {
		
		ScheduledExecutorService exec = null;
		ReportPing.setURL(pc.getReportUrl());
		exec = Executors.newScheduledThreadPool(pc.getThreadPoolSize());
		exec.scheduleWithFixedDelay(new ICMPPing(pc.getHostNameX()), 0, pc.getIcmpPingDelay(), TimeUnit.SECONDS);
		exec.scheduleWithFixedDelay(new ICMPPing(pc.getHostNameY()), 0, pc.getIcmpPingDelay(), TimeUnit.SECONDS);
		exec.scheduleWithFixedDelay(new TCPPing(pc.getUrlX()), 1, pc.getTcpPingDelay(), TimeUnit.SECONDS);
		exec.scheduleWithFixedDelay(new TCPPing(pc.getUrlY()), 1, pc.getTcpPingDelay(), TimeUnit.SECONDS);
		exec.scheduleWithFixedDelay(new TracertPing(pc.getHostNameX()), 3, 30, TimeUnit.SECONDS);
		exec.scheduleWithFixedDelay(new TracertPing(pc.getHostNameY()), 5, 30, TimeUnit.SECONDS);

	}
}
