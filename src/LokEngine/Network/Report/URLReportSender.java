package LokEngine.Network.Report;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

public class URLReportSender {

    protected int connectionTimeout;
    protected Proxy proxy;
    protected URL url;
    protected Gson gson;

    public URLReportSender(URL url, int connectionTimeout, Proxy proxy) {
        this.connectionTimeout = connectionTimeout;
        this.proxy = proxy;
        this.url = url;
        this.gson = new Gson();
    }

    public URLReportSender(URL url, int connectionTimeout) {
        this(url, connectionTimeout, null);
    }

    public URLReportSender(URL url) {
        this(url, 5000);
    }

    public void sendReport(Report report) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) (proxy != null ? url.openConnection(proxy) : url.openConnection());
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(this.connectionTimeout);

        connection.setDoOutput(true);
        OutputStream out = connection.getOutputStream();
        out.write(gson.toJson(report).getBytes());
        out.flush();
        out.close();

        connection.disconnect();
    }
}
