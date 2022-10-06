import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertApp {

    static final String userDirectory = System.getProperty("user.dir");

    // database connection
    public Connection connect() {
        String url = "jdbc:sqlite:" + userDirectory + "\\menu.db";
//        String url = constVal.SQLITE_DB_PATH +"\\"+ constVal.DB_FILENAME;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("err database connection " + e.getMessage());
        }

//        System.out.println("haaaaaaaaaaaaaaaaaaaa_______________________++++++++++++++++++++++++++++: "+ constVal.SQLITE_DB_PATH);
        return conn;
    }

    // insert data
    public void insertCategList(String Name, Integer Ident, String Comment, String genname0450, String genname0409) {
        String sql12 = "INSERT or REPLACE INTO CategList(Name, Ident, Comment, genname0450, genname0409) VALUES(?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = connect().prepareStatement(sql12)) {
            pstmt.setString(1, Name);
            pstmt.setInt(2, Ident);
            pstmt.setString(3, Comment);
            pstmt.setString(4, genname0450);
            pstmt.setString(5, genname0409);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(" insertCategList error" + e.getMessage());
        }
    }

    public void insertMenuItems(String Name, Integer menuIdent, Integer Code, String AltName, Integer ModiScheme, Integer MainParentIdent, String Comment, String genname0450, String genname0409, String genForWeb, String genSortForWeb, String Instruct) {
        String sql = "INSERT or REPLACE INTO MenuItems(Name, menuIdent, Code, AltName, ModiScheme, MainParentIdent, Comment, genname0450, genname0409, genForWeb, genSortForWeb, Instruct) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, Name);
            pstmt.setInt(2, menuIdent);
            pstmt.setInt(3, Code);
            pstmt.setString(4, AltName);
            pstmt.setInt(5, ModiScheme);
            pstmt.setInt(6, MainParentIdent);
            pstmt.setString(7, Comment);
            pstmt.setString(8, genname0450);
            pstmt.setString(9, genname0409);
            pstmt.setString(10, genForWeb);
            pstmt.setString(11, genSortForWeb);
            pstmt.setString(12, Instruct);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertMenuItemsPrice(Integer menuPriceIdent, Integer menuPriceItemIdent, Integer menuPriceValue, Integer menuPriceObjectID) {
        String sql = "INSERT or REPLACE INTO Price(menuPriceIdent, menuPriceItemIdent, menuPriceValue, menuPriceObjectID) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, menuPriceIdent);
            pstmt.setInt(2, menuPriceItemIdent);
            pstmt.setInt(3, menuPriceValue);
            pstmt.setInt(4, menuPriceObjectID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertRKOrderMenu(Integer identOrderMenu, Integer priceOrderMenu) {
        String sql = "INSERT or REPLACE INTO RKOrderMenu(identOrderMenu, priceOrderMenu) VALUES(?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, identOrderMenu);
            pstmt.setInt(2, priceOrderMenu);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertTables(Integer tablesIdent, Integer TablesMainParentIdent, Integer TablesCode, String TablesName) {
        String sql = "INSERT or REPLACE INTO Tables(tablesIdent, TablesMainParentIdent, TablesCode, TablesName) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, tablesIdent);
            pstmt.setInt(2, TablesMainParentIdent);
            pstmt.setInt(3, TablesCode);
            pstmt.setString(4, TablesName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertHallPlans(Integer HallPlansMainParentIdent, Integer HallPlansIdent) {
        String sql = "INSERT or REPLACE INTO HallPlans(HallPlansMainParentIdent, HallPlansIdent) VALUES(?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setInt(1, HallPlansMainParentIdent);
            pstmt.setInt(2, HallPlansIdent);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertSettings(String rkApiUrl, String username, String password, String restaurantCode) {
        String sql = "INSERT or REPLACE INTO Settings(rkApiUrl, username, password, restaurantCode) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = connect().prepareStatement(sql)) {
            pstmt.setString(1, rkApiUrl);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            pstmt.setString(4, restaurantCode);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}