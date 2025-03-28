package Controller;

import Model.History;
import Model.Logs;
import Model.Product;
import Model.User;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLite {
   
   public int DEBUG_MODE = 0;
   public static String driverURL = "jdbc:sqlite:" + "database.db";
   
   public void createNewDatabase() {
       try (Connection conn = DriverManager.getConnection(driverURL)) {
           if (conn != null) {
               DatabaseMetaData meta = conn.getMetaData();
               System.out.println("Database database.db created.");
           }
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public static int getRole(String username){
       String sql = "SELECT role FROM users WHERE LOWER(username) = LOWER(?);";
       int role = 0;
       
       try (Connection conn = DriverManager.getConnection(driverURL);
               PreparedStatement pstmt = conn.prepareStatement(sql)){
           pstmt.setString(1, username);
           
           try(ResultSet rs = pstmt.executeQuery()){
               if(rs.next()){
                   role = rs.getInt("role");
               }
           }
       } catch (Exception ex) {}
       return role;
   }
   
   public static String retrievePassword(String username){
       String sql = "SELECT password FROM users WHERE LOWER(username) = LOWER(?);";
       String password = null;
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, username);
           
           try (ResultSet rs = pstmt.executeQuery()) {
               if (rs.next()) {
                   password = rs.getString("password");
               }
           }
       } catch (Exception ex) {}
       return password;
   }
   
   public boolean isUsernameAvailable(String username) {
       String sql = "SELECT COUNT(*) FROM users WHERE LOWER(username) = LOWER(?)";
       try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, username);
           ResultSet rs = pstmt.executeQuery();
           if (rs.next()) {
               return rs.getInt(1) == 0; // Return true if count is 0 (username available)
           }
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return false; // Default to false if there's an error
   }
   
   public static String hashPassword(String password) {
       try {
           java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
           byte[] hash = md.digest(password.getBytes("UTF-8"));
           StringBuilder hexString = new StringBuilder();
           
           for (byte b : hash) {
               String hex = Integer.toHexString(0xff & b);
               if (hex.length() == 1) hexString.append('0');
               hexString.append(hex);
           }
           
           return hexString.toString();
       } catch (Exception ex) {
           ex.printStackTrace();
           return password; // Fallback to plaintext if hashing fails
       }
   }
   
   public void createHistoryTable() {
       String sql = "CREATE TABLE IF NOT EXISTS history (\n"
           + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
           + " username TEXT NOT NULL,\n"
           + " name TEXT NOT NULL,\n"
           + " stock INTEGER DEFAULT 0,\n"
           + " timestamp TEXT NOT NULL,\n"
           + " price DOUBLE NOT NULL);";

       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement()) {
           stmt.execute(sql);
           System.out.println("Table history in database.db created.");
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public void createLogsTable() {
       String sql = "CREATE TABLE IF NOT EXISTS logs (\n"
           + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
           + " event TEXT NOT NULL,\n"
           + " username TEXT NOT NULL,\n"
           + " desc TEXT NOT NULL,\n"
           + " timestamp TEXT NOT NULL\n"
           + ");";

       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement()) {
           stmt.execute(sql);
           System.out.println("Table logs in database.db created.");
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
    
   public void createProductTable() {
       String sql = "CREATE TABLE IF NOT EXISTS product (\n"
           + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
           + " name TEXT NOT NULL UNIQUE,\n"
           + " stock INTEGER DEFAULT 0,\n"
           + " price REAL DEFAULT 0.00\n"
           + ");";

       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement()) {
           stmt.execute(sql);
           System.out.println("Table product in database.db created.");
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
    
   public void createUserTable() {
       String sql = "CREATE TABLE IF NOT EXISTS users (\n"
           + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
           + " username TEXT NOT NULL UNIQUE,\n"
           + " password TEXT NOT NULL,\n"
           + " role INTEGER DEFAULT 2,\n"
           + " locked INTEGER DEFAULT 0\n"
           + ");";

       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement()) {
           stmt.execute(sql);
           System.out.println("Table users in database.db created.");
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public void dropHistoryTable() {
       String sql = "DROP TABLE IF EXISTS history;";

       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement()) {
           stmt.execute(sql);
           System.out.println("Table history in database.db dropped.");
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public void dropLogsTable() {
       String sql = "DROP TABLE IF EXISTS logs;";

       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement()) {
           stmt.execute(sql);
           System.out.println("Table logs in database.db dropped.");
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public void dropProductTable() {
       String sql = "DROP TABLE IF EXISTS product;";

       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement()) {
           stmt.execute(sql);
           System.out.println("Table product in database.db dropped.");
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public void dropUserTable() {
       String sql = "DROP TABLE IF EXISTS users;";

       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement()) {
           stmt.execute(sql);
           System.out.println("Table users in database.db dropped.");
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public static void addHistory(String username, String name, int stock, String timestamp, double price) {
       String sql = "INSERT INTO history(username,name,stock,timestamp,price) VALUES('" + username.toLowerCase() + "','" + name + "','" + stock + "','" + timestamp + "','" + price + "')";
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement()){
           stmt.execute(sql);
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public static void addLogs(String event, String username, String desc, String timestamp) {
       String sql = "INSERT INTO logs(event,username,desc,timestamp) VALUES(?,?,?,?)";
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           PreparedStatement pstmt = conn.prepareStatement(sql)){
           pstmt.setString(1, event);
           pstmt.setString(2, username.toLowerCase());
           pstmt.setString(3, desc);
           pstmt.setString(4, timestamp);
           pstmt.executeUpdate();
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public static boolean addProduct(String name, int stock, double price) {
       String sql = "INSERT INTO product(name,stock,price) VALUES(?,?,?);";
       
       if(name.isBlank() || stock < 0 || price < 0.0){
           return false;
       }
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           PreparedStatement pstmt = conn.prepareStatement(sql)){
           pstmt.setString(1, name);
           pstmt.setInt(2, stock);
           pstmt.setDouble(3, price);
           int rs = pstmt.executeUpdate();
           
           if(rs > 0){
               return true;
           }
       } catch (Exception ex) {
           System.out.print(ex);
       }
       return false;
   }
   
    public void addUser(String username, String password, int role) {
        String sql = "INSERT INTO users(username,password,role) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)){
             pstmt.setString(1, username.toLowerCase());
             pstmt.setString(2, hashPassword(password));
             pstmt.setInt(3, role);
             pstmt.executeUpdate();
        } catch (Exception ex) {
             System.out.print(ex);
        }
    }
   
    public ArrayList<History> getUserHistory(String username){
       String sql = "SELECT id, username, name, stock, timestamp, price FROM history WHERE username = ?";
       ArrayList<History> histories = new ArrayList<History>();
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, username.toLowerCase());
           
           try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                histories.add(new History(rs.getInt("id"),
                                  rs.getString("username"),
                                  rs.getString("name"),
                                  rs.getInt("stock"),
                                  rs.getString("timestamp"),
                                  rs.getDouble("price")));
                }
           }
       } catch (Exception ex) {}
       
       return histories;
   }
    
   public ArrayList<History> getHistory(){
       String sql = "SELECT id, username, name, stock, timestamp, price FROM history";
       ArrayList<History> histories = new ArrayList<History>();
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql)){
           
           while (rs.next()) {
               histories.add(new History(rs.getInt("id"),
                                  rs.getString("username"),
                                  rs.getString("name"),
                                  rs.getInt("stock"),
                                  rs.getString("timestamp"),
                                  rs.getDouble("price")));
           }
       } catch (Exception ex) {
           System.out.print(ex);
       }
       return histories;
   }
   
   public ArrayList<Logs> getLogs(){
       String sql = "SELECT id, event, username, desc, timestamp FROM logs";
       ArrayList<Logs> logs = new ArrayList<Logs>();
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql)){
           
           while (rs.next()) {
               logs.add(new Logs(rs.getInt("id"),
                                  rs.getString("event"),
                                  rs.getString("username"),
                                  rs.getString("desc"),
                                  rs.getString("timestamp")));
           }
       } catch (Exception ex) {
           ex.printStackTrace();
       }
       return logs;
   }
   
   public ArrayList<Product> getProduct(){
       String sql = "SELECT id, name, stock, price FROM product";
       ArrayList<Product> products = new ArrayList<Product>();
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql)){
           
           while (rs.next()) {
               products.add(new Product(rs.getInt("id"),
                                  rs.getString("name"),
                                  rs.getInt("stock"),
                                  rs.getFloat("price")));
           }
       } catch (Exception ex) {
           System.out.print(ex);
       }
       return products;
   }
   
   public ArrayList<User> getUsers(){
       String sql = "SELECT id, username, password, role, locked FROM users";
       ArrayList<User> users = new ArrayList<User>();
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql)){
           
           while (rs.next()) {
               users.add(new User(rs.getInt("id"),
                                  rs.getString("username"),
                                  rs.getString("password"),
                                  rs.getInt("role"),
                                  rs.getInt("locked")));
           }
       } catch (Exception ex) {}
       return users;
   }
   
   public ArrayList<User> getClients(){
       String sql = "SELECT id, username, password, role, locked FROM users WHERE role = '2';";
       ArrayList<User> users = new ArrayList<User>();
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql)){
           
           while (rs.next()) {
               users.add(new User(rs.getInt("id"),
                                  rs.getString("username"),
                                  rs.getString("password"),
                                  rs.getInt("role"),
                                  rs.getInt("locked")));
           }
       } catch (Exception ex) {}
       return users;
   }
   
   public ArrayList<User> getStaff(){
       String sql = "SELECT id, username, password, role, locked FROM users WHERE role = '2' OR role = '3';";
       ArrayList<User> users = new ArrayList<User>();
       
       try (Connection conn = DriverManager.getConnection(driverURL);
           Statement stmt = conn.createStatement();
           ResultSet rs = stmt.executeQuery(sql)){
           
           while (rs.next()) {
               users.add(new User(rs.getInt("id"),
                                  rs.getString("username"),
                                  rs.getString("password"),
                                  rs.getInt("role"),
                                  rs.getInt("locked")));
           }
       } catch (Exception ex) {}
       return users;
   }
   
   public void removeUser(String username) {
       String sql = "DELETE FROM users WHERE username=?";

       try (Connection conn = DriverManager.getConnection(driverURL);
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, username);
           pstmt.executeUpdate();
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public static void deleteProduct(String name) {
       String sql = "DELETE FROM product WHERE name=?;";

       try (Connection conn = DriverManager.getConnection(driverURL);
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, name);
           pstmt.executeUpdate();
       } catch (Exception ex) {
           System.out.print(ex);
       }
   }
   
   public Product getProduct(String name){
       String sql = "SELECT name, stock, price FROM product WHERE name=?";
       Product product = null;
       try (Connection conn = DriverManager.getConnection(driverURL);
           PreparedStatement pstmt = conn.prepareStatement(sql)) {
           pstmt.setString(1, name);
           ResultSet rs = pstmt.executeQuery();
           if (rs.next()) {
               product = new Product(rs.getString("name"),
                                  rs.getInt("stock"),
                                  rs.getFloat("price"));
           }
       } catch (Exception ex) {
           System.out.print(ex);
       }
       return product;
   }
   
    public static User getUser(String username){
        String sql = "SELECT id, password, role, locked FROM users WHERE username=?";
        User user = null;
        try (Connection conn = DriverManager.getConnection(driverURL);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getInt("id"),
                                username,
                                rs.getString("password"),
                                rs.getInt("role"),
                                rs.getInt("locked"));
            }
        } catch (Exception ex) {
            System.out.print(ex);
        }
        return user;
    }
    
    public static boolean purchaseProduct(String name, int quantity){
        String stock_check = "SELECT stock FROM product WHERE name = ?;";
        String purchase = "UPDATE product SET stock = stock - ? WHERE name = ?;";
        
        if(quantity < 1){
            return false;
        }
        
        try(Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement stock = conn.prepareStatement(stock_check);
                PreparedStatement buy = conn.prepareStatement(purchase)){
            
            stock.setString(1, name);
            ResultSet rs = stock.executeQuery();
            
            if(!rs.next()){
                return false;
            }
            
            int availableStock = rs.getInt("stock");
            
            if(availableStock < quantity){
                return false;
            }
            
            buy.setInt(1, quantity);
            buy.setString(2, name);
            
            int rs2 = buy.executeUpdate();
            if(rs2 > 0){
                return true;
            }
            
        } catch (Exception ex){};
        
        return false;
    }
    
    public static boolean editProduct(String name, int stock, double price){
        String sql = "UPDATE product SET name = ?, stock = ?, price = ? WHERE name = ?;";
        
        if(stock < 0 || price <= 0.0 || name.isBlank()){
            return false;
        }
        
        try(Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement pstmt = conn.prepareStatement(sql);){
            
            pstmt.setString(1, name);
            pstmt.setInt(2, stock);
            pstmt.setDouble(3, price);
            pstmt.setString(4, name);
            int rs = pstmt.executeUpdate();

            if(rs > 0){
                return true;
            }
            
        } catch (Exception ex){};
        
        return false;
    }
    
    public static void editRole(String username, int role){
        String sql = "UPDATE users SET role = ? WHERE username = ?;";
        
        try(Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement pstmt = conn.prepareStatement(sql);){
            
            pstmt.setInt(1, role);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (Exception ex){};
    }
    
    public static void lockUser(String username, int lock){
        String sql = "UPDATE users SET locked = ? WHERE username = ?;";
        
        try(Connection conn = DriverManager.getConnection(driverURL);
                PreparedStatement pstmt = conn.prepareStatement(sql);){
            
            pstmt.setInt(1, lock);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (Exception ex){};
    }
    
    public static void changePassword(String username, String password) {
        // Hash password
        String hashedPassword = hashPassword(password);
        
        String sql = "UPDATE users SET password = ? WHERE LOWER(username) = LOWER(?);";
        
        try (Connection conn = DriverManager.getConnection(driverURL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set hashed password
            pstmt.setString(1, hashedPassword);
            // Use case-insensitive comparison
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            
            // Log successful password change
            java.sql.Timestamp timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
            addLogs("SECURITY", "SYSTEM", "Password changed for user: " + username, timestamp.toString());
        } catch (Exception ex) {
            // Log error
            System.err.println("Error changing password: " + ex.getMessage());
            
            // Log failed password change
            java.sql.Timestamp timestamp = new java.sql.Timestamp(new java.util.Date().getTime());
            addLogs("SECURITY", "SYSTEM", "Failed to change password for user: " + username, timestamp.toString());
        }
    }
    
    public static void clearLogs(){
        String sql = "DELETE FROM logs;";
        
        try(Connection conn = DriverManager.getConnection(driverURL);
                Statement st = conn.createStatement();){

            st.executeUpdate(sql);
        } catch (Exception ex){};
    }
}