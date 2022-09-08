import java.io.File;
import java.sql.*;

public class dbConnection {
    static  final String userDirectory = System.getProperty("user.dir");
    public void createNewDb(){
//        File theDir = new File(userDirectory);
//        if (!theDir.exists()){
//            theDir.mkdirs();
//        }

        System.out.println("userDirectory :" + userDirectory);

        String url = "jdbc:sqlite:"+ userDirectory + "\\menu.db";
        System.out.println("url :" + url);
        try (Connection conn = DriverManager.getConnection(url)){
            if (conn != null){
                DatabaseMetaData meta = conn.getMetaData();
//                 System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("URL" + url);
                System.out.println("A new database has been created.");
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void createNewTable(){
        String url = "jdbc:sqlite:"+ userDirectory + "\\menu.db";;
        String sql = "CREATE TABLE IF NOT EXISTS MenuItems (\n"
                + "	Name text,\n"
                + "	menuIdent INT PRIMARY KEY,\n"
                + "	Code INT,\n"
                + "	AltName text,\n"
                + "	ModiScheme text,\n"
                + "	mainParentIdent INT,\n"
                + "	Comment text ,\n"
                + "	genname0450 text ,\n"
                + "	genName0409 text ,\n"
                + "	genForWeb text ,\n"
                + "	genSortForWeb text,  \n"
                + "	Instruct text ,\n"
                +" FOREIGN KEY(mainParentIdent) REFERENCES CategList(Ident)\n "
                + ");";


        String sql1 = "CREATE TABLE IF NOT EXISTS CategList (\n"
                + "	Name text,\n"
                + "	Ident INT PRIMARY KEY,\n"
                + "	Comment text ,\n"
                + "	genname0450 text ,\n"
                + "	genName0409 text\n"
                + ");";

        String sql2 = "CREATE TABLE IF NOT EXISTS Price (\n"
                + "	menuPriceIdent INT PRIMARY KEY,\n"
                + "	menuPriceItemIdent INT,\n"
                + "	menuPriceValue INT ,\n"
                + "	menuPriceObjectID INT\n"
                + ");";

        String sql3 = "CREATE VIEW IF NOT EXISTS price_value AS\n" +
                "SELECT \n" +
                "Name,\n" +
                "menuIdent,\n" +
                "Code,\n" +
                "AltName,\n" +
                "Instruct,\n" +
                "ModiScheme,\n" +
                "mainParentIdent,\n" +
                "Comment, \n" +
                "genname0450,\n" +
                "genName0409,\n" +
                "genForWeb,\n" +
                "menuPriceIdent,\n" +
                "menuPriceItemIdent,\n" +
                "menuPriceValue,\n" +
                "menuPriceObjectID\n" +
                " FROM MenuItems JOIN Price ON MenuItems.menuIdent = Price.menuPriceObjectID";


        String sql4 = "CREATE TABLE IF NOT EXISTS RKOrderMenu (\n"
                + "	identOrderMenu INT PRIMARY KEY,\n"
                + "	priceOrderMenu INT\n"
                + ");";


        String sql5 = "CREATE TABLE IF NOT EXISTS Tables (\n"
                + "	tablesIdent INT PRIMARY KEY,\n"
                + "	TablesMainParentIdent INT,\n"
                + "	TablesCode INT,\n"
                + "	TablesName text\n"
                + ");";

        String sql6 = "CREATE TABLE IF NOT EXISTS HallPlans (\n"
                + "	HallPlansMainParentIdent INT,\n"
                + "	hallPlansIdent INT PRIMARY KEY\n"
                + ");";


        String sql7 = "CREATE TABLE IF NOT EXISTS Settings (\n"
                + "	rkApiUrl text, \n"
                + " username text, \n"
                + " password text, \n"
                + " restaurantCode text \n"
                + ");";


        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql4);
            stmt.execute(sql1);
            stmt.execute(sql2);
            stmt.execute(sql);
            stmt.execute(sql3);
            stmt.execute(sql5);
            stmt.execute(sql6);
            stmt.execute(sql7);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}