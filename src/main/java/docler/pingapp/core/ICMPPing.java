package docler.pingapp.core;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import docler.pingapp.dao.ICMPObj;
import docler.pingapp.dao.ReportObj;

import docler.pingapp.dto.ObjectTransformer;

public class ICMPPing implements Runnable, Callable {

	private static final Logger logger = Logger.getLogger(ICMPPing.class.getName());

	String address;
	private ICMPObj icmpObject;
	private ObjectTransformer objTransform;
	private ReportObj repObj;

	private ICMPPing() {
	}

	public ICMPPing(String addr) {
		this.address = addr;
		this.icmpObject = new ICMPObj();
		this.objTransform = new ObjectTransformer();
		this.repObj = new ReportObj();
	}

	private StringBuffer runCommand(String command) {

		logger.log(Level.INFO, "Inside ICMP Ping for command :"+command);
		final StringBuffer buf = new StringBuffer();
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String s = "";

			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
			Date dt = new Date(System.currentTimeMillis());
			this.icmpObject.setPingOnTime(sdf.format(dt));

			while ((s = inputStream.readLine()) != null) {
				buf.append(s);
				if (s.contains("Pinging")) {
					this.icmpObject.setHostName(
							s.substring(s.indexOf("Pinging") + "Pinging".length() + 1, s.indexOf("with") - 1));
				} 
				else if (s.contains("Packets")) {
					this.icmpObject.setPacketsSent(
							s.substring(s.lastIndexOf("Sent = ") + "Sent = ".length(), s.indexOf("Received") - 2));
					this.icmpObject.setPacketsRecieved(
							s.substring(s.lastIndexOf("Received = ") + "Received = ".length(), s.indexOf("Lost") - 2));
					this.icmpObject.setLostPackets(
							s.substring(s.lastIndexOf("Lost = ") + "Lost = ".length(), s.indexOf("(") - 1));
				} 
				else if (s.contains("Minimum")) {
					this.icmpObject.setMinTripTime(s.substring(s.lastIndexOf("Minimum = ") + "Minimum = ".length(),
							s.indexOf("Maximum = ") - 2));
					this.icmpObject.setMaxTripTime(
							s.substring(s.lastIndexOf("Maximum = ") + "Maximum = ".length(), s.indexOf("Average") - 2));
					this.icmpObject.setAvgTripTime(s.substring(s.lastIndexOf("Average = ") + "Average = ".length()));
				}

			}
			if (buf != null)
				this.icmpObject.setOverAllData(buf);

			if (!this.icmpObject.getLostPackets().equalsIgnoreCase("0")) {
				logger.log(Level.WARNING, "Calling reporting ping from ICMP ping since there is a packet loss of "
						+ icmpObject.getLostPackets());
				this.repObj.setHost(this.icmpObject.getHostName());
				this.repObj.setIcmp_ping(this.icmpObject.getOverAllData().toString());
				this.repObj.setTcp_ping(null);
				this.repObj.setTrace(null);
				new ReportPing().pingURL(ReportPing.getURL(), 0, "POST", this.repObj);
			}
			this.objTransform.serializeObj(this.icmpObject);
			logger.log(Level.INFO, "Done with ICMP Ping for command:"+command);

		} catch (IOException e) {
			this.icmpObject.setErrorMessage("error in the host " + this.icmpObject.getHostName() + " is " + e.getMessage());
			logger.log(Level.SEVERE, "error in ICMP :", e);
		} catch (Exception e) {
			this.icmpObject.setErrorMessage("error in the host " + this.icmpObject.getHostName() + " is " + e.getMessage());
			logger.log(Level.SEVERE, "error in ICMP :", e);
		}
		
		return buf;
	}

	public static void main(String[] args) {

		String ip = "escalion.com";
		new ICMPPing("escalion.com").runCommand("ping -n 5 " + ip);
	}

	public StringBuffer call() throws Exception {

		return runCommand("ping -n 5 " + this.address);
	}

	public void run() {

		System.out.println(runCommand("ping -n 5 " + this.address));
	}

}
