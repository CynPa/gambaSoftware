package com.gambalit.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
	
	public static Connection conectar() {
		Connection con = null;
 
		try {
			String url = "jdbc:postgresql://localhost:5433/gambalitReport?user=postgres&password=etorres";
			con = DriverManager.getConnection(url);
			if (con != null) {
				System.out.println("Conexion Satisfactoria");
			}
 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return con;
	}

}
