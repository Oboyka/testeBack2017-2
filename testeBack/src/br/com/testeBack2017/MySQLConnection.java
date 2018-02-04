package br.com.testeBack2017;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class MySQLConnection {

	public static String status = "Houve um erro na conex�o com o banco de dados!";
	
	public MySQLConnection(){
	}
	
	public static Connection getMySQLConnection(){
		
		Connection connection = null;
		
		try {
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);
			
			String serverName = "localhost";
			String myDataBase = "testeback";
			String url = "jdbc:mysql://" + serverName + "/" + myDataBase + "?autoReconnect=true&useSSL=false";
			String userName = "root";
			String password = "root";
			
			connection = DriverManager.getConnection(url, userName, password);
			
			if(connection != null){
				status = ("Status: Conex�o com o banco de dados realizada com sucesso!");
			}else{
				status = ("Status: N�o foi poss�vel realizar a conex�o com o banco de dados!");
			}
			
			return connection;
			
		} catch (ClassNotFoundException e) {
			System.out.println("O driver expecificado nao foi encontrado.");
            return null;
		} catch (SQLException e) {
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");
            return null;
		}
	}
	
	public static String statusConection() {
		 
        return status;
 
    }
	
	public static boolean closeConnection() {
		 
        try {
            MySQLConnection.getMySQLConnection().close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
	
	public static Connection resetConnection() {
        closeConnection();
        return MySQLConnection.getMySQLConnection();
    }
}
