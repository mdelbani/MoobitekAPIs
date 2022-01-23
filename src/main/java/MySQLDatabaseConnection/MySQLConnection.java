package MySQLDatabaseConnection;

import java.sql.*;
import java.util.Arrays;

public class MySQLConnection<string> {
     static String code;
    //here backend_api is database name, root is username and password
     static final String DB_BackendAPI = "jdbc:mysql://172.16.3.33:3306/backend_api";
     static final String DB_TpGame = "jdbc:mysql://172.16.3.33:3306/tpgame";
     static final String USER = "root";
     static final String PASS = "m00bifun";
     static int UserId;
     static int PlayerId;

    Connection conBackend = DriverManager.getConnection(DB_BackendAPI, USER, PASS);
    Connection conTpGame = DriverManager.getConnection(DB_TpGame, USER, PASS);

    public MySQLConnection() throws SQLException {
    }

    public String getCodeMethod() throws SQLException {

        Statement stmt=conBackend.createStatement();
        ResultSet rs=stmt.executeQuery("select * from backend_api.mobile_number where mobile_number = 22196170000000");
        while(rs.next())
        code = rs.getString(3);
        conBackend.close();
        return code;
    }

    public int getUserIdMethod() throws SQLException {
        //here backend_api is database name, root is username and password
        Statement stmt=conBackend.createStatement();
        ResultSet rs=stmt.executeQuery("select * from backend_api.mobile_number where mobile_number = 22196170000000");
        while(rs.next())
        UserId = rs.getInt(1);
        //System.out.println(UserId);;
        return UserId;
    }

    public int getPlayerId() throws SQLException {
        //here backend_api is database name, root is username and password
        Statement stmt=conTpGame.createStatement();
        ResultSet rs=stmt.executeQuery("select * FROM tpgame.participant where msisdn = 22196170000000");
        while(rs.next())
        PlayerId = rs.getInt(1);
        //System.out.println(PlayerId);
        return PlayerId;
    }

    public void deleteDataMethod() throws SQLException {
        getUserIdMethod();
        getPlayerId();
        String deleteLoginToken = "Delete FROM backend_api.login_token where user_id ="+UserId;
        String deleteExtendedWallet = "Delete From tpgame.extended_wallet where player_id ="+PlayerId;
        String deleteMobileNb = "Delete FROM backend_api.mobile_number where id ="+UserId;
        String deleteParticipant = "Delete FROM tpgame.participant where id ="+PlayerId;
        String deleteEmail = "Delete FROM backend_api.email where email = \"test@test.com\"";
        String deleteUser = "Delete FROM backend_api.user where username = \"mtest\"";
        try {
            Statement stmt = conBackend.createStatement();
            Statement stmt1 = conTpGame.createStatement();
            stmt.executeUpdate(deleteEmail);
            stmt.executeUpdate(deleteMobileNb);
            stmt.executeUpdate(deleteLoginToken);
            stmt.executeUpdate(deleteUser);
            stmt1.executeUpdate(deleteExtendedWallet);
            stmt1.executeUpdate(deleteParticipant);
            conBackend.close();
            conTpGame.close();
            System.out.println("All data related to the new created user has been deleted successfully");
        }catch (Exception e) {
            System.out.println("something wrong");
        }
    }

    public static void main(String[] args) throws SQLException {

    }
}
