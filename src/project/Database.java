package project;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javax.swing.JOptionPane;
import project.Database;
  

public class Database {
    
    
    
    static String connection = "jdbc:mysql://localhost/project?useUnicode=true&characterEncoding=utf-8&user=root&password=";
    static String query;
    static String query2;
    private static LocalDate date;

     

    
    
    
    
    public static String getCash() throws SQLException {        
        query = "Select cash From money";
        Connection conn = null;   
        String cash = null;
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            
            cash = rs.getString(1);
 
            conn.close();
            } catch(ClassNotFoundException exception) {
                System.out.println("Problem ze sterownikiem");
              }
        return cash;
    } 
    
    public static ObservableList<String> getCategories() throws SQLException {
        query = "SELECT name FROM categories";
        Connection conn = null;   
        ObservableList<String> result = FXCollections.observableArrayList();
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()) 
                result.add(rs.getString("name"));               
            
            conn.close();
            } catch(ClassNotFoundException exception) {
                System.out.println("Problem ze sterownikiem");
              }
        
        return result;
    }
    
    public static ObservableList<Category> showCategories() throws SQLException {
        query = "SELECT * FROM categories";
        Connection conn = null;   
        ObservableList<Category> result = FXCollections.observableArrayList();
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
                      
            while(rs.next()) 
                result.add(new Category(
                        rs.getInt("idcategories"),
                        rs.getString("name")));

            conn.close();
            } catch(ClassNotFoundException exception) {
                System.out.println("Problem ze sterownikiem");
              }
        
        return result;
    }
    
    public static ObservableList<Operation> showOperations() throws SQLException {
        query = "SELECT * FROM operations";
        Connection conn = null;   
        ObservableList<Operation> result = FXCollections.observableArrayList();
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
                  
            while(rs.next()) 
                result.add(new Operation(
                        rs.getInt("idoperations"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDate("date"),
                        rs.getDouble("cost")));

            conn.close();
            } catch(ClassNotFoundException exception) {
                System.out.println("Problem ze sterownikiem");
              }
        
        return result;
    }
    
    public static ObservableList<Operation> getOperations(String query, String month, String year) throws SQLException {
        Connection conn = null;   
        ObservableList<Operation> result = FXCollections.observableArrayList();
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()) {
                if(month.isEmpty() && year.isEmpty()) 
                    result.add(new Operation(
                        rs.getInt("idoperations"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDate("date"),
                        rs.getDouble("cost"))); 
               
                else {
                    if(month.isEmpty() && year.contains(String.valueOf(rs.getDate("date").toLocalDate().getYear()))) 
                        result.add(new Operation(
                            rs.getInt("idoperations"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getDate("date"),
                            rs.getDouble("cost")));
                    else if(month.contains(rs.getDate("date").toLocalDate().getMonth().toString()) && year.contains(String.valueOf(rs.getDate("date").toLocalDate().getYear())))
                        result.add(new Operation(
                            rs.getInt("idoperations"),
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getDate("date"),
                            rs.getDouble("cost")));
                }
            }
                
            conn.close();
            } catch(ClassNotFoundException exception) {
                System.out.println("Problem ze sterownikiem");
              }
        
        return result;
    }
    
    public static void saveCategory(String Cname) throws SQLException {
        query = "INSERT INTO categories (name) VALUES ('"+Cname+"')";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        
            conn.close();
        } catch(ClassNotFoundException exception) {}
    }
    
    public static void saveOperation(String Cname, double cash, String cat, LocalDate dt) throws SQLException {
        
         double wydatek = cash*2;
        query = "INSERT INTO operations (name,category,date,cost) VALUES ('"+Cname+"','"+cat+"','"+dt+"','"+cash+"')";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        
            conn.close();
        } catch(ClassNotFoundException exception) {}
        
      cash = cash - wydatek;
        if(!"Wpłata".equals(cat))  changeCash(cash);
   
    }   
     private Main main; 
    public static void changeCash(double money) throws SQLException {
        
       
        double cash = Double.parseDouble(Database.getCash());
      
        money=money+cash;
        
        query = "UPDATE money SET cash="+money+"";
     
        
        Connection conn = null;
      
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            stmt.execute(query);
           
        
            conn.close();
            } catch(ClassNotFoundException exception) {}    
    }   
    
    public static void deleteCategory(String del) throws SQLException {
        query = "DELETE FROM categories WHERE idcategories='"+del+"'";
        Connection conn = null;
        
        if(!"1".equals(del)){
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        
            conn.close();
            } catch(ClassNotFoundException exception) {} 
        } else{String message = "Nie można usunąć kategori wpłata";
                JOptionPane.showMessageDialog(null, message);               
        }
    }
    
    public static void deleteOperation(String del) throws SQLException {
        query = "DELETE FROM operations WHERE idoperations='"+del+"'";
        Connection conn = null;

  
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        
            conn.close();
            } catch(ClassNotFoundException exception) {} 
        
        
        
    }
    
    public static ObservableList<String> categories() throws SQLException {
        ObservableList<String> results = FXCollections.observableArrayList();
        query = "SELECT DISTINCT category FROM operations";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()) 
                results.add(rs.getString("category"));
            
            conn.close();
            } catch(ClassNotFoundException exception) {} 
        return results;
    }
    
    public static ObservableList<String> years() throws SQLException {
        ObservableList<String> results = FXCollections.observableArrayList();
        query = "SELECT DISTINCT YEAR(date) AS year FROM operations";
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()) 
                results.add(rs.getString("year"));
            
            conn.close();
            } catch(ClassNotFoundException exception) {} 
        return results;
    }  
    
    public static XYChart.Series chartDataC(String category) throws SQLException {
        XYChart.Series results = new XYChart.Series();       
        double sum;
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            for(int x=1; x<13; x++) {
                query = "SELECT cost FROM operations WHERE category='"+category+"' AND MONTH(date)='"+x+"' AND YEAR(date)='"+LocalDate.now().getYear()+"'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                sum = 0;
                
                while(rs.next()) 
                    sum += rs.getDouble("cost");
                    
                results.getData().add(new XYChart.Data(monthLang(String.valueOf(x)), sum));               
            }
            conn.close();
        } catch(ClassNotFoundException exception) {} 
        results.setName(category);
        return results;
    }

    
    
     public static XYChart.Series month(String category) throws SQLException {
        XYChart.Series results = new XYChart.Series();       
        double sum,razem;
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            
                query = "SELECT cost FROM operations WHERE category='"+category+"' AND MONTH(date)='"+LocalDate.now().getMonthValue()+"' AND YEAR(date)='"+LocalDate.now().getYear()+"'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                sum = 0;
                razem = 0;
                while(rs.next()) 
                    sum += rs.getDouble("cost");
                    
                results.getData().add(new XYChart.Data(monthLang(String.valueOf(LocalDate.now().getMonthValue())), sum));               
            razem= razem + sum;
            conn.close();
        } catch(ClassNotFoundException exception) {} 
        results.setName(category);
        return results;
    }
    
    
     public static String month_cash() throws SQLException {        
              
        double sum,razem = 0;
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            
                query = "SELECT cost FROM operations WHERE category!='Wpłata' AND MONTH(date)='"+LocalDate.now().getMonthValue()+"' AND YEAR(date)='"+LocalDate.now().getYear()+"'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                sum = 0;
                razem = 0;
                while(rs.next()) 
                    sum += rs.getDouble("cost");              
            razem= razem + sum;
            conn.close();
        } catch(ClassNotFoundException exception) {} 
        String wynik = Double.toString(razem);
        
        return wynik;
    } 
     
          public static String month_wplata() throws SQLException {        
              
        double sum,razem = 0;
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(connection);
            Class.forName("com.mysql.jdbc.Driver");
            
            
                query = "SELECT cost FROM operations WHERE category='Wpłata' AND MONTH(date)='"+LocalDate.now().getMonthValue()+"' AND YEAR(date)='"+LocalDate.now().getYear()+"'";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                sum = 0;
                razem = 0;
                while(rs.next()) 
                    sum += rs.getDouble("cost");              
            razem= razem + sum;
            conn.close();
        } catch(ClassNotFoundException exception) {} 
        String wynik = Double.toString(razem);
        
        return wynik;
    } 
    
    public static String monthLang(String m) {
        switch(m) {
            case "1": m = "Styczeń"; break;
            case "2": m = "Luty"; break;
            case "3": m = "Marzec"; break;
            case "4": m = "Kwiecień"; break;
            case "5": m = "Maj"; break;
            case "6": m = "Czerwiec"; break;
            case "7": m = "Lipiec"; break;
            case "8": m = "Sierpień"; break;
            case "9": m = "Wrzesień"; break;
            case "10": m = "Październik"; break;
            case "11": m = "Listopad"; break;
            case "12": m = "Grudzień";
        }

        return m;
    }
    
}
