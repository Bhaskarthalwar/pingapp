package docler.pingapp.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import docler.pingapp.dao.ReportObj;
import docler.pingapp.dao.TracertObj;
import docler.pingapp.dto.ObjectTransformer;

public class TracertPing implements Runnable, Callable {

	
	private static final Logger logger = Logger.getLogger(TracertPing.class.getName());
	
	String address;
	private TracertObj tracertObj;
	private ObjectTransformer objTrans;
	private ReportObj repObj;

	private TracertPing() {}

	public TracertPing(String addr) {
		this.address = addr;
		this.tracertObj = new TracertObj();
		this.objTrans = new ObjectTransformer();
		this.repObj=new ReportObj();
		
	}

	private StringBuffer runCommand(String command) {
		
		logger.log(Level.INFO, "inside tracert for command :"+command);
		final StringBuffer buf = new StringBuffer();

		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
			Date dt = new Date(System.currentTimeMillis());
			this.tracertObj.setOnTime(sdf.format(dt));
			String s = "";
			while ((s = inputStream.readLine()) != null) {
				buf.append(s);
				if(s.contains("Tracing")){
					this.tracertObj.setHostName(s.substring(s.indexOf("Tracing route to ")+"Tracing route to ".length()));
				}
			}
			this.tracertObj.setTraceRoute(buf);
			this.objTrans.serializeObj(tracertObj);
			logger.log(Level.INFO, "trace route ended for command :"+command);
			this.repObj.setHost(this.tracertObj.getHostName());
			this.repObj.setTrace(this.tracertObj.getTraceRoute().toString());
			new ReportPing().pingURL(ReportPing.getURL(), 0, "POST", this.repObj);
			logger.log(Level.INFO, "trace route ended for command :"+command +" after sending to report");

		} catch (Exception e) {
			this.tracertObj.setErrorMessage("error in the host "+this.tracertObj.getHostName()+" is "+e.getMessage());
			this.repObj.setTrace("trace command:"+"error is "+ e.getMessage());
			new ReportPing().pingURL(ReportPing.getURL(), 0, "POST", this.repObj);
		}
		return buf;

	}

	public static void main(String[] args) {

		String ip = "escalion.com";
		new TracertPing().runCommand("tracert " + ip);

	}

	public StringBuffer call() throws Exception {

		return runCommand("tracert " + this.address);

	}

	public void run() {

		System.out.println(runCommand("tracert " + this.address));
	}
}
