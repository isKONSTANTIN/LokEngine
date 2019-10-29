package LokEngine.Network.Report;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;

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

    protected String sendReport(Report report, HttpURLConnection connection) throws Exception {
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(this.connectionTimeout);

        connection.setDoOutput(true);
        OutputStream out = connection.getOutputStream();
        out.write(gson.toJson(report).getBytes());
        out.flush();
        out.close();

        InputStream inputStream = connection.getInputStream();
        String returnMessage = IOUtils.toString(inputStream, Charset.defaultCharset());

        inputStream.close();
        connection.disconnect();

        return returnMessage;
    }

    public String sendReport(Report report) throws Exception {
        return sendReport(report, (HttpURLConnection) (proxy != null ? url.openConnection(proxy) : url.openConnection()));
    }

    public String sendReportOverHttps(Report report) throws Exception {
        return sendReport(report, (HttpsURLConnection)(proxy != null ? url.openConnection(proxy) : url.openConnection()));
    }
}