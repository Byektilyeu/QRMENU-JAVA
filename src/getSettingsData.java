import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getSettingsData {

    InsertApp app = new InsertApp();

    public String[] getSettings() {
        String sql = "SELECT rkApiUrl, username, password, restaurantCode "
                + "FROM Settings";
        String rkApiUrl = null;
        String username = null;
        String password = null;
        String restaurantCode = null;

        try (Connection conn = app.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                rkApiUrl = rs.getString("rkApiUrl");
                username = rs.getString("username");
                password = rs.getString("password");
                restaurantCode = rs.getString("restaurantCode");
            }
        } catch (SQLException e) {
            System.out.println("Greater than zero error" + e.getMessage());
        }
        String[] arr = new String [4];
        arr[0] =rkApiUrl;
        arr[1] = username;
        arr[2] = password;
        arr[3] = restaurantCode;
        return new String[] {rkApiUrl, username, password, restaurantCode};
    }
}
