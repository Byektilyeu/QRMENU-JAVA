import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

public class GUI extends JFrame implements ActionListener
{
    JLabel RK_API_URL_Label, USERNAME_Label, PASSWORD_Label, Restaurant_code_Label, EmpId_Label, StationId_Label;
    JTextField rkApiTF, usernameTF, passwordTF, restaurantCodeTF, empIdTF, stationIdTF;
    JButton submitButton, clearButton, startButton;
    GUI()
    {
        setVisible(true);
        setSize(600, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RK_API_URL_Label = new JLabel("RK_API_URL :");
        USERNAME_Label = new JLabel("USERNAME :");
        PASSWORD_Label = new JLabel("PASSWORD :");
        Restaurant_code_Label = new JLabel("Restaurant_code :");
        EmpId_Label = new JLabel("Employee id :");
        StationId_Label = new JLabel("Station id :");

        rkApiTF = new JTextField();
        usernameTF = new JTextField();
        passwordTF = new JTextField();
        restaurantCodeTF = new JTextField();
        empIdTF = new JTextField();
        stationIdTF = new JTextField();

        submitButton = new JButton("Submit");
        clearButton = new JButton("Clear");
        startButton = new JButton("Start");

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        startButton.addActionListener(this);

        RK_API_URL_Label.setBounds(40, 70, 200, 30);
        USERNAME_Label.setBounds(40, 110, 200, 30);
        PASSWORD_Label.setBounds(40, 150, 200, 30);
        Restaurant_code_Label.setBounds(40, 190, 200, 30);
        EmpId_Label.setBounds(40, 270, 200, 30);
        StationId_Label.setBounds(40, 310, 200, 30);

        rkApiTF.setBounds(200, 70, 300, 30);
        usernameTF.setBounds(200, 110, 300, 30);
        passwordTF.setBounds(200, 150, 300, 30);
        restaurantCodeTF.setBounds(200, 190, 300, 30);
        empIdTF.setBounds(200, 270, 300, 30);
        stationIdTF.setBounds(200, 310, 300, 30);

        submitButton.setBounds(200, 230, 100, 30);
        clearButton.setBounds(320, 230, 100, 30);
        startButton.setBounds(200, 350, 100, 30);

        add(RK_API_URL_Label);
        add(rkApiTF);
        add(USERNAME_Label);
        add(usernameTF);
        add(PASSWORD_Label);
        add(passwordTF);
        add(Restaurant_code_Label);
        add(restaurantCodeTF);
        add(EmpId_Label);
        add(empIdTF);
        add(StationId_Label);
        add(stationIdTF);

        add(submitButton);
        add(clearButton);
        add(startButton);
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == submitButton)
        {
              String rkApiUrl = rkApiTF.getText();
             String username = usernameTF.getText();
            String password = passwordTF.getText();
            String restaurantCode = restaurantCodeTF.getText();
            String empID = empIdTF.getText();
            String stationID = stationIdTF.getText();
             String response = "";
            Request req = new Request();
            try {
                 response = req.postRequest(rkApiUrl, username, password);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (response != null){
                JOptionPane.showMessageDialog(null, "R-Keeper-т амжилттай холбогдлоо", "Message: ", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(response);

                InsertApp app = new InsertApp();
                app.insertSettings(rkApiUrl, username, password, restaurantCode);

            }else{
                System.out.println("401 aldaa garlaa");
            }
        }
        else if(e.getSource() == startButton){

            getSettingsData settings = new getSettingsData();
            String[] arr = settings.getSettings();
            String rkApiUrl = arr[0];
            String username = arr[1];
            String password = arr[2];
            String restaurantCode = arr[3];

            mainClass Main = new mainClass();
            Main.getValues(rkApiUrl, username, password, restaurantCode);

        }
        else {
            rkApiTF.setText("");
            usernameTF.setText("");
            passwordTF.setText("");
            restaurantCodeTF.setText("");
            empIdTF.setText("");
            stationIdTF.setText("");
        }
    }
    public static void main(String args[])
    {
        new GUI();
        sslDisable ssl = new sslDisable();
        sslDisable.disableSslVerification();
        // DB connection
        dbConnection database = new dbConnection();
        database.createNewDb();
        database.createNewTable();
    }
}