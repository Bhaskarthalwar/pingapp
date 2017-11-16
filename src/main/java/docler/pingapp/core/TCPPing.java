package docler.pingapp.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import docler.pingapp.dao.ReportObj;
import docler.pingapp.dao.TCPObj;
import docler.pingapp.dto.ObjectTransformer;

public class TCPPing implements Runnable, Callable {

	private static final Logger logger = Logger.getLogger(TCPPing.class.getName());

	String url;
	private TCPObj tcpObj;
	private ObjectTransformer objTrans;
	private ReportObj repObj;
	private static int maxResponseTime;

	public static int getMaxResponseTime() {
		return maxResponseTime;
	}

	public static void setMaxResponseTime(int maxResponseTime) {
		TCPPing.maxResponseTime = maxResponseTime;
	}

	private TCPPing() {
	}

	public TCPPing(String url) {
		this.url = url;
		this.tcpObj = new TCPObj();
		this.objTrans = new ObjectTransformer();
		this.repObj = new ReportObj();
	}

	public StringBuffer pingURL(String url, int timeout, String method) {
		logger.log(Level.INFO, "Inside the tcp ping for URL:" + url);
		url = url.replaceFirst("^https", "http");
		StringBuffer sbuf = new StringBuffer();

		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod(method);
			String httpUrl = connection.getURL().toString();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");
			Date dt = new Date(System.currentTimeMillis());
			this.tcpObj.setTcptime(sdf.format(dt));
			this.tcpObj.setUrl(httpUrl);
			String serverName = httpUrl.split("/")[2];
			if (serverName.contains("www."))
				this.tcpObj.setHostName(serverName.substring(serverName.indexOf("www.") + "www.".length()));

			long startTime = System.currentTimeMillis();
			int responseCode = connection.getResponseCode();
			long endTime = System.currentTimeMillis();
			this.tcpObj.setResponseTime(String.valueOf(endTime - startTime));
			this.tcpObj.setResponseCode(String.valueOf(responseCode));
			this.objTrans.serializeObj(this.tcpObj);

			if (Integer.valueOf(this.tcpObj.getResponseTime()) >= getMaxResponseTime()) {
				this.repObj.setHost(this.tcpObj.getHostName());
				this.repObj.setTcp_ping(
						"URL:" + httpUrl + " method:" + method + " response code:" + this.tcpObj.getResponseCode());
				new ReportPing().pingURL(ReportPing.getURL(), 0, "POST", this.repObj);
			}
			logger.log(Level.INFO, "finished with tcp ping for :" + url + " with status code:" + responseCode);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "error inside TCPPing for " + url, e.getMessage());
			this.tcpObj.setErrorMessage("error in the host " + this.tcpObj.getHostName() + " is " + e.getMessage());
			this.repObj.setTcp_ping("URL:" + url + " method:" + "POST" + " response:" + e.getMessage());
			new ReportPing().pingURL(ReportPing.getURL(), 0, "POST", this.repObj);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "error inside TCPPing for " + url, e.getMessage());
			this.tcpObj.setErrorMessage("error in the host " + this.tcpObj.getHostName() + " is " + e.getMessage());
			this.repObj.setTcp_ping("URL:" + url + " method:" + "POST" + " response:" + e.getMessage());
			new ReportPing().pingURL(ReportPing.getURL(), 0, "POST", this.repObj);
		}
		return sbuf;
	}

	public static void main(String[] args) {
		System.out.println(new TCPPing().pingURL("https://www.oranum.com/", 10000, "HEAD"));
	}

	public StringBuffer call() throws Exception {

		return pingURL(this.url, 10000, "HEAD");
	}

	public void run() {

		System.out.println(pingURL(this.url, 10000, "HEAD"));

	}


}
