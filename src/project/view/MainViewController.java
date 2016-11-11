package project.view;

import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import project.Database;
import project.Main;
import project.Operation;






public class MainViewController {   
    
    
    
    
        ObservableList<Operation> operationsList;
    ObservableList<String> categoriesList;
   ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    ObservableList<String> monthsList = FXCollections.observableArrayList("Styczeń","Luty","Marzec","Kwiecień","Maj","Czerwiec","Lipiec","Sierpień","Wrzesień","Październik","Listopad","Grudzień");
    ObservableList<String> yearsList = FXCollections.observableArrayList();
    private CategoryAxis xAxisC = new CategoryAxis();
    private NumberAxis yAxisC = new NumberAxis();
    private CategoryAxis xAxisM = new CategoryAxis();
    private NumberAxis yAxisM = new NumberAxis();
  
    @FXML private TableView<Operation> table;   
    @FXML private TableColumn<Operation, String> id, name, cat, date, cash;    
    @FXML private Button delete;   
    @FXML private Tab tab, tabC;   
    @FXML private GridPane filter;  
    @FXML private RadioButton all, day, month, year;  
    @FXML private CheckBox catB, dateB;  
    @FXML private DatePicker dateP;   
    @FXML private ListView catL, monthL, yearL;
    @FXML private BarChart catChart;
    
    public Database db;
    @FXML
    private Label budget, budget1, month_wp;
    private Main main; 
    
    @FXML
    public void initialize() throws SQLException {
                categoriesList = db.categories().sorted();
        String cash = db.getCash();
        String month_cash = db.month_cash();
        String month_add = db.month_wplata();
 String kategoria;
        budget.setText(cash);
        budget1.setText(month_cash);
        month_wp.setText(month_add);
        
        
      for(int x=0; x<categoriesList.size(); x++) {
        kategoria = categoriesList.get(x); 
                  if(!"Wpłata".equals(kategoria))  {
          catChart.getData().addAll(db.month(categoriesList.get(x)));
      }
      }
        
    }
    
    @FXML
    private void goAddOperation() throws IOException {
       main.showAddOperation();

    }
    

    
    @FXML
    private void goAddCategory() throws IOException {
        main.showAddCategory();
    }
    
    @FXML
    private void goShowCategories() throws IOException {
       main.showCategories();
       
    }
    
    @FXML
    private void goShowExpenses() throws IOException {
        main.showExpenses();
    }
    
    @FXML
    private void goShowMain() throws IOException, SQLException {       
        main.showMainView();      
    }      
    
    
   

    private double getDouble(XYChart.Series chartDataC) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
    
