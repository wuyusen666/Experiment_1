package database;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccessDatabase {

	Connection con;
	String databaseFilePath; // 数据库文件路径: "src/playerinfo.accdb"
	
	public AccessDatabase(String path) {
		this.databaseFilePath = path;
	}
	
	public void builtConnection () { // 建立数据库连接
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver");
			con = DriverManager.getConnection("jdbc:Access:/" + databaseFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "数据库连接发生错误！请将数据库文件放在如下路径 src/playerinfo.accdb ", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// 插入数据
	public boolean insertData(String tableName, String userName, String password, String email, int numOfLevel) {
		try {
	        String insertSql = "INSERT INTO " + tableName + " (userName, password, email, numOfLevel) VALUES (?, ?, ?, ?)";
		    PreparedStatement statement = con.prepareStatement(insertSql);
		    statement.setString(1, userName);
		    statement.setString(2, password);
		    statement.setString(3, email);
		    statement.setInt(4, numOfLevel);
		    statement.executeUpdate();
		    return true;
	    }catch (Exception e) {
			JOptionPane.showMessageDialog(null, "数据错误，注册失败！", "错误", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
	// 查询数据
	public ResultSet executeQuery (String querySql) {  // 返回ResultSet！！！参数为自己输入的sql语句
		try {
			PreparedStatement preStat = con.prepareStatement(querySql);
			ResultSet rs = preStat.executeQuery();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL查询语句语法错误！", "错误", JOptionPane.ERROR_MESSAGE);
		}
		return null; // null为失败
	}
	
	// 更新数据
	public boolean updateData (String tableName, String columnName, String newValue, String condition) { // 表名、字段（列）名、新的值、满足什么条件的数据
		try {
			String sql = "UPDATE "+tableName+" SET "+columnName+" = ? WHERE "+condition;
			PreparedStatement preStat = con.prepareStatement(sql);
			preStat.setString(1, newValue);
			preStat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL语句语法错误！无法更新数据！", "错误", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
	public boolean deleteData (String tableName, String userName) { // 删除用户名为userName的数据
		try {
			String sql = "DELETE FROM "+tableName+" WHERE userName = ?";
			PreparedStatement preStat = con.prepareStatement(sql);
			preStat.setString(1, userName);
			preStat.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL语句语法错误！无法进行删除！", "错误", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
}