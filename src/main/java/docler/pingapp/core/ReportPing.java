package docler.pingapp.core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import docler.pingapp.dao.ReportObj;

public class ReportPing {

	private static final Logger logger = Logger.getLogger(ReportPing.class.getName());

	private static String URL;

	public static String getURL() {
		return URL;
	}

	public static void setURL(String uRL) {
		URL = uRL;
	}

	public StringBuffer pingURL(String url, int timeout, String method, ReportObj obj) {

		logger.log(Level.WARNING, "Calling Reporting");
		StringBuffer sbuf = new StringBuffer();

		try {

			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			logger.log(Level.WARNING, " calling for report URL:" + url);
			connection.setConnectTimeout(timeout);
			connection.setReadTimeout(timeout);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
			String jsonStr = new ObjectMapper().writeValueAsString(obj);
			logger.log(Level.WARNING, "Request Body:" + jsonStr);
			connection.setRequestProperty("Content-Length", Integer.toString(jsonStr.length()));
			final OutputStream os = connection.getOutputStream();
			os.write(jsonStr.getBytes("utf8"));
			int responseCode = connection.getResponseCode();
			logger.log(Level.WARNING, "Response :" + responseCode);
			String responseMess = connection.getResponseMessage();
			sbuf.append(responseMess);
			os.flush();
			os.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "error inside reporting ping:" + e.getMessage());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "error inside reporting ping:" + e.getMessage());
		}
		return sbuf;
	}

}
