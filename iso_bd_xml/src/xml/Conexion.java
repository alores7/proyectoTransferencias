package xml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexion {
	
	public Conexion() {}
	
	public Connection conectar(int puerto, String user, String pass, String bd) {
		Connection miConexion = null;
		String url = "jdbc:mysql://localhost:" + puerto + "/" + bd + "?useUnicode=true&characterEncoding=utf8&connectionCollation=utf8_spanish_ci";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			miConexion = DriverManager.getConnection(url, user, pass);
		} catch(ClassNotFoundException | SQLException ex) {
		//	JOptionPane.showMessageDialog(null, ex);
			JOptionPane.showMessageDialog(null, "MySQL Error", "Error", JOptionPane.ERROR_MESSAGE);	
		}
		return miConexion;
	}
	
	public int seConecta(int puerto, String user, String pass, String bd) {
		Connection conexion = null;
		String url = "jdbc:mysql://localhost:" + puerto + "/" + bd + "?useUnicode=true&characterEncoding=utf8&connectionCollation=utf8_spanish_ci";;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, user, pass);
		} catch(ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
			return 0;			
		}
		return 1;
	}
}
