import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class Student implements Initializable {
    @FXML
    public TextField nameField;
    public TextField phoneField;
    public Button button;
    public TableView<Model> tableView;
    public TableColumn<Model,Integer> idCol;
    public TableColumn<Model,String> nameCol;
    public TableColumn<Model,String> phoneCol;
    public TableColumn<Model,String> cityCol;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println(nameField.getText());
        System.out.println(phoneField.getText());
        String name = nameField.getText();
        String phone = phoneField.getText();
        addValues(name, phone);
    }

    private void addValues(String name, String phone) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/oilab", "root", "root");
            Statement stmt = con.createStatement();

            stmt.execute("insert into students (name, phone, city) values(\""+name+"\",\""+phone+"\",\"jodhpur\")");
            
            ArrayList<Model> modelArrayList = new ArrayList<>();
            ResultSet rs = stmt.executeQuery("select * from students");
            while (rs.next()){
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
                modelArrayList.add(new Model(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }

            tableView.getItems().setAll(modelArrayList);

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<Model, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Model, String>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Model, String>("phone"));
        cityCol.setCellValueFactory(new PropertyValueFactory<Model, String>("city"));
        // Initialization code can go here.
        // The parameters url and resources can be omitted if they are not needed
    }
}
