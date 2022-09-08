import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

// Main buyu undsen class
public class mainClass {

    // main function. Hamgiin ehend achaalladag undsen function
//    public void main(String[] args) throws Exception {
////        new GUI();
//        // 30 secund tutamd dahin ajilluulah function
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    mainFunction();
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }, 0, 50000);
//    }

    public void getValues(String rkApiUrl,String username,String password,String restaurantCode) {
                new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    mainFunction(rkApiUrl, username, password, restaurantCode);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }, 0, 50000);
    }


    // Үндсэн функц
    public void mainFunction(String rkApiUrl,String username,String password,String restaurantCode) throws Exception {
        try {
            // DB ruu data insert hiih class
            InsertApp app = new InsertApp();

            // SERVER ruu request ilgeeh class
            RequestMenuItems getResponse = new RequestMenuItems();
            String result = getResponse.postRequest(rkApiUrl, username, password);

            getOrderMenuRequest getOrderMenuResponse = new getOrderMenuRequest();
            String orderMenu = getOrderMenuResponse.postRequest(rkApiUrl, username, password);

            hallPlansRequest hallPlansResponse = new hallPlansRequest();
            String hallPlans = hallPlansResponse.postRequest(rkApiUrl, username, password);

            //  XML to json
            JSONObject jsonResult = XML.toJSONObject(result);
            JSONObject jsonOrderMenu = XML.toJSONObject(orderMenu);
            JSONObject jsonHallPlans = XML.toJSONObject(hallPlans);

            // HallPlans
            JSONObject RK7QueryResultHallPlans = (JSONObject) jsonHallPlans.get("RK7QueryResult");
            JSONObject CommandResultHallPLans = (JSONObject) RK7QueryResultHallPlans.get("CommandResult");
            JSONObject RK7ReferenceHallPlans = (JSONObject) CommandResultHallPLans.get("RK7Reference");
            JSONObject ItemsHallPlans = (JSONObject) RK7ReferenceHallPlans.get("Items");
            JSONArray arrItemHallPlans  = ItemsHallPlans.getJSONArray("Item");

            for (int h = 0; h < arrItemHallPlans.length(); h++) {
                JSONObject ItemHallPlans = arrItemHallPlans.getJSONObject(h);
                int HallPlansMainParentIdent = (int) ItemHallPlans.get("MainParentIdent");
                if(HallPlansMainParentIdent == Integer.parseInt(restaurantCode)){
                    int HallPlansIdent = (int) ItemHallPlans.get("Ident");
                    app.insertHallPlans(HallPlansMainParentIdent, HallPlansIdent);
                    //tables
                    tablesRequest tablesResponse = new tablesRequest();
                    String tables = tablesResponse.postRequest(rkApiUrl, username, password);
                    JSONObject jsonTables = XML.toJSONObject(tables);
                    JSONObject RK7QueryResultTables = (JSONObject) jsonTables.get("RK7QueryResult");
                    JSONObject CommandResultTables = (JSONObject) RK7QueryResultTables.get("CommandResult");
                    JSONObject RK7ReferenceTables = (JSONObject) CommandResultTables.get("RK7Reference");
                    JSONObject ItemsTables = (JSONObject) RK7ReferenceTables.get("Items");
                    JSONArray arrItemTables  = ItemsTables.getJSONArray("Item");
                    for (int t = 0; t < arrItemTables.length(); t++) {
                        JSONObject ItemTables = arrItemTables.getJSONObject(t);
                        int TablesMainParentIdent = (int) ItemTables.get("MainParentIdent");
                        if(TablesMainParentIdent == HallPlansIdent){
                            int TablesIdent = (int) ItemTables.get("Ident");
                            int TablesCode = (int) ItemTables.get("Code");
                            String TablesName = ItemTables.get("Name").toString();
                            //  insert data
                            app.insertTables(TablesIdent, TablesMainParentIdent, TablesCode, TablesName);
                        }
                    }
                }
            }

            //Order menu
            JSONObject RK7QueryResultOrderMenu = (JSONObject) jsonOrderMenu.get("RK7QueryResult");
            JSONObject Dishes = (JSONObject) RK7QueryResultOrderMenu.get("Dishes");
            JSONArray arrItemOrderMenu = Dishes.getJSONArray("Item");
            for (int r = 0; r < arrItemOrderMenu.length(); r++) {
                JSONObject itemOrderMenu = arrItemOrderMenu.getJSONObject(r);
                int identOrderMenu = (int) itemOrderMenu.get("Ident");
//                System.out.println("identOrderMenu" + identOrderMenu);
                int priceOrderMenu = (int) itemOrderMenu.get("Price");
//                System.out.println("priceOrderMenu" + priceOrderMenu);

                app.insertRKOrderMenu(identOrderMenu, priceOrderMenu);
            }


            // Menu

            JSONObject RK7QueryResult = (JSONObject) jsonResult.get("RK7QueryResult");
            JSONArray arrCategList = RK7QueryResult.getJSONArray("CommandResult");

            for (int k = 0; k < arrCategList.length(); k++) {
                JSONObject arrCategList1 = arrCategList.getJSONObject(k);
                JSONObject RK7Reference = arrCategList1.getJSONObject("RK7Reference");
                String RefName = RK7Reference.get("ClassName").toString();
                switch (RefName) {
                    case "TCategListItems":
//                        JSONObject arrCategList0  = (JSONObject) arrCategList.getJSONObject(0);
//                        JSONObject RK7Reference1  = (JSONObject) arrCategList1.getJSONObject("RK7Reference");
                        JSONObject Items = (JSONObject) RK7Reference.get("Items");
//                            System.out.println("GETmenu XML Items: => " + Items);
                        JSONArray arrItem = Items.getJSONArray("Item");
                        for (int i = 0; i < arrItem.length(); i++) {
                            JSONObject Item = arrItem.getJSONObject(i);
                            String Name = Item.get("Name").toString();
                            int Ident = (int) Item.get("Ident");
                            String Comment = Item.get("Comment").toString();


                            String genname0450 = null;
                            String genname0409 = null;
                            if (Item.has("genname0450")) {
                                genname0450 = Item.get("genname0450").toString();
                            } else {
                                genname0450 = "null";
                            }

                            if (Item.has("genname0409")) {
                                genname0409 = Item.get("genname0409").toString();
                            } else {
                                genname0409 = "null";
                            }

                            app.insertCategList(Name, Ident, Comment, genname0450, genname0409);
                        }
                        break;
                    case "TRK7MenuItems":
//                        JSONObject arrCategList1  = (JSONObject) arrCategList.getJSONObject(1);
//                        JSONObject RK7Reference2  = (JSONObject) arrCategList1.getJSONObject("RK7Reference");
                        JSONObject menuItems = (JSONObject) RK7Reference.get("Items");
                        JSONArray arrMenuItem = menuItems.getJSONArray("Item");
                        for (int j = 0; j < arrMenuItem.length(); j++) {
                            JSONObject menuItem = arrMenuItem.getJSONObject(j);
                            String menuName = menuItem.get("Name").toString();
                            int menuIdent = (int) menuItem.get("Ident");
                            int menuCode = (int) menuItem.get("Code");
                            String menuAltName = menuItem.get("AltName").toString();
//                                System.out.println("GETmenu XML Name: => " + menuAltName);
                            int menuModiScheme = (int) menuItem.get("ModiScheme");
                            int menuMainParentIdent = (int) menuItem.get("MainParentIdent");
                            String Comment = menuItem.get("Comment").toString();
                            String Instruct = menuItem.get("Instruct").toString();

                            String menuGenname0450 = null;
                            String menuGenname0409 = null;
                            String menuGenForWeb = null;
                            String menuGenSortForWeb = null;
                            if (menuItem.has("genname0450")) {
                                menuGenname0450 = menuItem.get("genname0450").toString();
                            } else {
                                menuGenname0450 = "null";
                            }

                            if (menuItem.has("genname0409")) {
                                menuGenname0409 = menuItem.get("genname0409").toString();
                            } else {
                                menuGenname0409 = "null";
                            }

                            if (menuItem.has("genForWeb")) {
                                menuGenForWeb = menuItem.get("genForWeb").toString();
                            } else {
                                menuGenForWeb = "null";
                            }

                            if (menuItem.has("genSortForWeb")) {
                                menuGenSortForWeb = menuItem.get("genSortForWeb").toString();
                            } else {
                                menuGenSortForWeb = "null";
                            }
                            app.insertMenuItems(menuName, menuIdent, menuCode, menuAltName, menuModiScheme, menuMainParentIdent, Comment, menuGenname0450, menuGenname0409, menuGenForWeb, menuGenSortForWeb, Instruct);
                        }
                        break;
                    case "TPrices":
                        JSONObject menuItemPrices = (JSONObject) RK7Reference.get("Items");
                        JSONArray arrMenuItemPrices = menuItemPrices.getJSONArray("Item");
                        for (int p = 0; p < arrMenuItemPrices.length(); p++) {
                            JSONObject menuItemPrice = arrMenuItemPrices.getJSONObject(p);
                            int menuPriceIdent = (int) menuItemPrice.get("Ident");
//                                System.out.println("menuPriceIdent" + menuPriceIdent);
                            int menuPriceItemIdent = (int) menuItemPrice.get("ItemIdent");
//                                System.out.println("menuPriceItemIdent" + menuPriceItemIdent);
//                            int menuPriceValue = (int) menuItemPrice.get("ItemIdent");
                            int menuPriceValue = (int) menuItemPrice.get("Value");
//                            int menuPriceValue = menuPriceValue1 / 100;
//                                System.out.println("menuPriceValue" + menuPriceValue);
                            int menuPriceObjectID = (int) menuItemPrice.get("ObjectID");
//                                System.out.println("menuPriceObjectID" + menuPriceObjectID);
                            app.insertMenuItemsPrice(menuPriceIdent, menuPriceItemIdent, menuPriceValue, menuPriceObjectID);
                        }
                        break;
                    default:
                        System.out.println("no match");
                }
            }

        } catch (IOException ex) {
            System.out.println("Алдаа гарлаа: => " + ex);
        }
    }
}
