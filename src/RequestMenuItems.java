import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RequestMenuItems {
    public String postRequest(String rkApiUrl,String username, String password) throws IOException {

        final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_3) Version/7.0.3 Safari/7046A194A";
        final StringBuffer content = new StringBuffer();
        final URL url = new URL(rkApiUrl);

        String auth = username + ":" + password;
        byte[] bytes = auth.getBytes(StandardCharsets.UTF_8);
        String base64Encoded = Base64.getEncoder().encodeToString(bytes);
        String authHeaderValue = "Basic " + base64Encoded;
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

        final String msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<RK7Query> <RK7Command CMD=\"GetRefData\" RefName=\"CategList\" OnlyActive=\"1\" MacroPropTags=\"true\"   WithMacroProp=\"1\" PropMask=\"Items.(Ident, Name,  Comment, genname0450, genName0409, genForWeb, genSortForWeb)\" >  </RK7Command>\n" +
                "    <RK7Command CMD=\"GetRefData\" RefName=\"menuitems\" OnlyActive=\"1\" MacroPropTags=\"true\"   WithMacroProp=\"1\" PropMask=\"Items.(Ident,Code,Name, AltName, ModiScheme,SaleObjectType, mainParentIdent, LargeImagePath, Comment, CategPath, genFiskBar, genname0450, genName0409, genForWeb, genSortForWeb, RecommendedMenuItems, Instruct)\" >  </RK7Command>\n" +
                "    <RK7Command CMD=\"GetRefData\" RefName=\"ModiSchemes\" IgnoreDefaults=\"1\" IgnoreEnums=\"0\" MacroPropTags=\"true\" OnlyActive=\"1\" WithChildItems=\"3\" PropMask=\"items.(Ident,Code, Name),items.RIChildItems.TModiSchemeDetail(Ident, ModiScheme,ModiGroup, ReadOnlyName, UpLimit, DownLimit)\">\n" +
                "        <PROPFILTERS>\n" +
                "            <PROPFILTER Name=\"ModiSchemeType\" Value=\"mstCombo\"></PROPFILTER>\n" +
                "        </PROPFILTERS>\n" +
                "    </RK7Command>\n" +
                "    <RK7Command CMD=\"GetRefData\" RefName=\"ModiGroups\" OnlyActive=\"1\" IgnoreEnums=\"false\" MacroPropTags=\"true\" WithBlobsData=\"true\" WithChildItems=\"3\" WithMacroProp=\"true\" PropMask=\"items.(Ident,Name,Code,genName0409, genName0450, genSortForWeb ),items.RIChildItems.TModifier(ItemIdent, Ident, Name, Code, MainParentIdent, Dish, Comment, genName0409, genName0450, genSortForWeb)\">\n" +
                "        <PROPFILTERS>\n" +
                "            <PROPFILTER Name=\"ModiGroupType\" Value=\"mgtCombo\"></PROPFILTER>\n" +
                "        </PROPFILTERS>\n" +
                "    </RK7Command>\n" +
                "    <RK7Command CMD=\"GetRefData\" RefName=\"Prices\" >  </RK7Command>\n" +
                "    <RK7Command CMD=\"GetRefData\" RefName=\"TradeGroupDetails\" >  </RK7Command>\n" +
                "    <RK7Command CMD=\"GetRefData\" RefName=\"AVAILABILITYSCHEDULES\" PropMask=\"Items.(ItemIdent, ObjectIdent, SalePointID, AvailPeriod)\">  </RK7Command>\n" +
                "    <RK7Command CMD=\"GetRefData\" RefName=\"PERIODDETAILS\"> </RK7Command>\n" +
                "</RK7Query>\n" +
                "</RK7Query>\n";
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
