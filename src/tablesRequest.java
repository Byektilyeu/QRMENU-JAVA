import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class tablesRequest {
    public String postRequest(String rkApiUrl,String username,String password) throws IOException {

        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();
        final URL url = new URL(rkApiUrl);

        String auth = username + ":" + password;
        byte[] bytes = auth.getBytes(StandardCharsets.UTF_8);
        String base64Encoded = Base64.getEncoder().encodeToString(bytes);
        String authHeaderValue = "Basic " + new String(base64Encoded);
//		System.out.println("--------------------------------------------------------------------------------------------");
        System.out.println("Хүсэлт илгээж буй хэрэглэгчийн токен: => " + base64Encoded);

        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", authHeaderValue);
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Content-Type", "application/xml; charset=UTF-8");

        conn.setDoOutput(true);
        conn.setConnectTimeout(500);

        // Send post request
        conn.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());

        final String msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?><RK7Query>\n" +
                " <RK7Command CMD=\"GetRefData\" RefName = \"TABLES\" >\n" +
                " </RK7Command>\n" +
                "</RK7Query>";
        wr.writeBytes(msg);
//			 send request
        wr.flush();
        // close
        wr.close();
        // read response
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String str;
        while ((str = in.readLine()) != null) {
            content.append(str);
        }
        in.close();
        return content.toString();
    }
}

