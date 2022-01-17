package com.db.user;

import java.sql.*;

public class MySqlCon {
     static String code;
     public static void main(String[] args) throws SQLException {

        getConMethod();

         }

     static void getConMethod() throws SQLException {

         Connection con= DriverManager.getConnection(
                 "jdbc:mysql://172.16.3.33:3306/backend_api","root","m00bifun");
         //here backend_api is database name, root is username and password
         Statement stmt=con.createStatement();
         ResultSet rs=stmt.executeQuery("select * from backend_api.mobile_number where mobile_number = 22196170824488");

         while(rs.next())
         code = rs.getString(3);
         System.out.println(code);
     }

}
