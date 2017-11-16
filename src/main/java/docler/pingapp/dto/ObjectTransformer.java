package docler.pingapp.dto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import docler.pingapp.dao.ICMPObj;
import docler.pingapp.dao.PingConfig;
import docler.pingapp.dao.TCPObj;
import docler.pingapp.dao.TracertObj;

public class ObjectTransformer {

	private static String icmpLog;
	private static String tcpLog;
	private static String traceLog;

	public static String getIcmpLog() {
		return icmpLog;
	}

	public static void setIcmpLog(String icmpLog) {
		ObjectTransformer.icmpLog = icmpLog;
	}

	public static String getTcpLog() {
		return tcpLog;
	}

	public static void setTcpLog(String tcpLog) {
		ObjectTransformer.tcpLog = tcpLog;
	}

	public static String getTraceLog() {
		return traceLog;
	}

	public static void setTraceLog(String traceLog) {
		ObjectTransformer.traceLog = traceLog;
	}

	public void serializeObj(Object obj) throws IOException {

		ObjectMapper mapper = new ObjectMapper();
		String fileName = null;
		if (obj != null && obj instanceof ICMPObj) {
			ICMPObj iObj = (ICMPObj) obj;
			fileName = iObj.getHostName() + "_ICMPObj.json";
			mapper.writeValue(new File(fileName), iObj);
			FileWriter fw = getFileWriter(getIcmpLog());
			fw.write("\n");
			fw.write(mapper.writeValueAsString(iObj.getPingOnTime()+"::"+iObj.getOverAllData()));
			fw.close();
			fw.close();
		} else if (obj != null && obj instanceof TCPObj) {
			TCPObj tObj = (TCPObj) obj;
			fileName = tObj.getHostName() + "_TCPObj.json";
			mapper.writeValue(new File(fileName), (TCPObj) obj);
			FileWriter fw = getFileWriter(getTcpLog());
			fw.write("\n");
			fw.write(tObj.getTcptime()+"::"+mapper.writeValueAsString(tObj.getUrl() + ":: response code=" + tObj.getResponseCode())
					+ ":: response time in ms=" + tObj.getResponseTime());
			fw.flush();
			fw.close();
		} else if (obj != null && obj instanceof TracertObj) {
			TracertObj trObj = (TracertObj) obj;
			fileName = trObj.getHostName() + "_TracertObj.json";
			mapper.writeValue(new File(fileName), (TracertObj) obj);
			FileWriter fw = getFileWriter(getTraceLog());
			fw.write("\n");
			fw.write(trObj.getOnTime()+"::"+mapper.writeValueAsString(trObj.getHostName() + "::=" + trObj.getTraceRoute()));
			fw.flush();
			fw.close();
		}

	}

	public Object deserializeObj(String fileName) throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper mapper = new ObjectMapper();
		PingConfig obj = mapper.readValue(new File("PingConfig.json"), PingConfig.class);
		return obj;

	}

	private FileWriter getFileWriter(String fileName) throws IOException {

		File logFile = new File(fileName);
		if (!logFile.exists())
			logFile.createNewFile();
		FileWriter fw = new FileWriter(logFile.getAbsoluteFile(), true);
		return fw;
	}

}
