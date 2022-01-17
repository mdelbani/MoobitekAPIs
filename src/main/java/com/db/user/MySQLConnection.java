package com.db.user;

import java.sql.*;
import java.util.Arrays;

public class MySQLConnection<string> {
     static String code;
     static final String DB_BackendAPI = "jdbc:mysql://172.16.3.33:3306/backend_api";
     static final String DB_TpGame = "jdbc:mysql://172.16.3.33:3306/tpgame";
     static final String USER = "root";
     static final String PASS = "m00bifun";
     static int UserId;
     String deleteEmail = "Delete FROM backend_api.email where email = \"mustapha.delbani@moobitek.com\"";
     String deleteMobileNb = "Delete FROM backend_api.mobile_number where mobile_number = 22196170824488";
     String deleteUser = "Delete FROM backend_api.user where username = \"mdelbani\"";
     String deleteParticipant = "Delete FROM tpgame.participant where msisdn = 22196170824488";
     //should specify which ID
     String deleteLoginToken = "Delete FROM backend_api.login_token";
     //should specify which ID
     String deleteExtendedWallet = "Delete From tpgame.extended_wallet";
    Connection conBackend = DriverManager.getConnection(DB_BackendAPI, USER, PASS);
    Connection conTpGame = DriverManager.getConnection(DB_TpGame, USER, PASS);

    public MySQLConnection() throws SQLException {
    }

    public String getCodeMethod() throws SQLException {
        //here backend_api is database name, root is username and password
        Statement stmt=conBackend.createStatement();
        ResultSet rs=stmt.executeQuery("select * from backend_api.mobile_number where mobile_number = 22196170824488");
        while(rs.next())
        code = rs.getString(3);
        //UserId = rs.getInt(1);
        conBackend.close();
        System.out.println(code);
        //System.out.println(UserId);
        return code;
    }

    public void deleteDataMethod() throws SQLException {

        Statement stmt=conBackend.createStatement();
        Statement stmt1=conTpGame.createStatement();
        stmt.executeUpdate(deleteEmail);
        stmt.executeUpdate(deleteMobileNb);
        stmt.executeUpdate(deleteLoginToken);
        stmt.executeUpdate(deleteUser);
        stmt1.executeUpdate(deleteExtendedWallet);
        stmt1.executeUpdate(deleteParticipant);
        conBackend.close();
        conTpGame.close();
        System.out.println("deleted successfully");
    }
}
