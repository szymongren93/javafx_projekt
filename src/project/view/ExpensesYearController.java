
package project.view;


import java.sql.SQLException;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import project.Database;

public class ExpensesYearController {
    
     
    ObservableList<String> categoriesList;
       private Database db;
   
    @FXML private BarChart catChart1;
    
    
        @FXML
    private void initialize() throws SQLException {
       
        categoriesList = db.categories().sorted();

             show_table();                     
    }
    
  
    
    
    
    
        @FXML
    private void show_table() throws SQLException {
        catChart1.getData().clear();
   String kategoria;
                for(int x=0; x<categoriesList.size(); x++) 
                {
                   kategoria = categoriesList.get(x); 
                      if(!"Wpłata".equals(kategoria))  {
                    catChart1.getData().addAll(db.chartDataC(categoriesList.get(x))); }
                }
        
    }    
    
}
