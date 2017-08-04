package DBlab1;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;

public class AccessRW {

	Connection connSql = null;
	Statement stmtSql;
	//	ResultSet rsSql;

//	Connection connAcc = null;
//	Statement stmtAcc;
//	ResultSet rsAcc;

	public void accessOperation() throws SQLException, IOException{
		AccessRW adb = new AccessRW();
//		connAcc = connectAcc();
		connSql = adb.connectSql();
		createTable();
		readAccessWriteSql();
		readAccessWriteSqlStu();

		connSql.close();
//		connAcc.close();
		//		stmtAcc.close();
		//		rsAcc.close();
		//		stmtSql.close();

	}

//连接sql数据库
	public Connection connectSql() throws SQLException{
		//		Connect to sql database
		String url = "jdbc:mysql://localhost:3306/lab1";
		String username = "root";
		String password = "admin";

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Error when loading jdbc");
			e.printStackTrace();
		}

		try {
			connSql = DriverManager.getConnection(url, username, password);
		} catch (SQLException se) {
			System.out.println ("Error when connecting database");
			se.printStackTrace();
		}
		//		connSql = DriverManager.getConnection(url,username,password);
		return connSql;
	}
//	public Connection connectAcc() throws SQLException{
//		//		connect to the access database
//		try {
//			Class.forName("com.hxtt.sql.access.AccessDriver").newInstance();
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("Have problem!");
//		}
//		String url2 = "jdbc:Access:///src/oralexam.mdb";
//
//		connAcc = DriverManager.getConnection(url2, "", "");
//		return connAcc;
//		//		stmtAcc = connAcc.createStatement();
//	}
//	创建表
	public void createTable() throws SQLException{
		String sql = "CREATE TABLE `oralexam`.`room` (`kdno` INT NULL,`kcno` INT NULL,`ccno` INT NULL,`kdname` VARCHAR(45) NULL,`exptime` DATETIME NULL, `papername` VARCHAR(45) NULL)";
		String sql2 = "CREATE TABLE `oralexam`.`student` (`registno` VARCHAR(10) NOT NULL,`name` VARCHAR(20) NOT NULL,`kdno` INT NULL,`kcno` INT NULL, `ccno` INT NULL,`seat` INT NULL)";

		stmtSql = connSql.createStatement();
		stmtSql.execute(sql);
		stmtSql.execute(sql2);
		stmtSql.close();
	}
//	从accee数据库读取数据并写入到sql数据库中
	public void readAccessWriteSql() throws SQLException, IOException{

		Table table = Database.open(new File("oralexam.accdb")).getTable("room");  
		for(Map row : table) {  
			System.out.println("kdno: " + row.get("kdno")+", kcno: " + row.get("kcno")+", ccno: "+row.get("ccno")+", kdname: "+row.get("kdname")+", exptime: "+row.get("exptime"));
			int a = Integer.parseInt(row.get("kdno").toString());
			int b = Integer.parseInt(row.get("kcno").toString());
			int c = Integer.parseInt(row.get("ccno").toString());
			String d = row.get("kdname").toString();
			String e = row.get("exptime").toString();
			String f = row.get("papername").toString();
//			String a = row.get("registno").toString();
//			String b = row.get("name").toString();
////			Double cDouble = Double.parseDouble(row.get("kcno").toString());
//			int cInt = Integer.parseInt(row.get("kdno").toString());
//			Double dDouble = Double.parseDouble(row.get("kcno").toString());
//			int dInt = dDouble.intValue();
//			Double eDouble = Double.parseDouble(row.get("ccno").toString());
//			int eInt = eDouble.intValue();
//			Double fDouble = Double.parseDouble(row.get("seat").toString());
//			int fInt = fDouble.intValue();
			insertData(a,b,c,d,e,f, connSql);
		}  
		//		String sql = "select * from room ";
		//		stmtAcc = connAcc.createStatement();
		//		rsAcc = stmtAcc.executeQuery(sql);      
		//
		//		while (rsAcc.next()) {
		//			int a = rsAcc.getInt(1);
		//			int b = rsAcc.getInt(2);
		//			int c = rsAcc.getInt(3);
		//			String d = rsAcc.getString(4);
		//			String e = rsAcc.getString(5);
		//			String f = rsAcc.getString(6);
		////			Date f = rsAcc.getDate(6);
		//			System.out.println(a+","+b+","+c+","+d+","+e+","+f);
		//			insertData(a,b,c,d,e,f, connSql);
		//		}
		//		stmtAcc.close();
		//		rsAcc.close();
	}
//	从access数据库中读取数据并写入到student表中
	public void readAccessWriteSqlStu() throws SQLException, IOException{
		Table table = Database.open(new File("oralexam.accdb")).getTable("student");  
		for(Map row : table) {  
			System.out.println("registno: " + row.get("registno")+", name: " + row.get("name")+", kdno: "+row.get("kdno")+", kcno: "+row.get("kcno"));
			String a = row.get("registno").toString();
			String b = row.get("name").toString();
//			Double cDouble = Double.parseDouble(row.get("kcno").toString());
			int cInt = Integer.parseInt(row.get("kdno").toString());
			Double dDouble = Double.parseDouble(row.get("kcno").toString());
			int dInt = dDouble.intValue();
			Double eDouble = Double.parseDouble(row.get("ccno").toString());
			int eInt = eDouble.intValue();
			Double fDouble = Double.parseDouble(row.get("seat").toString());
			int fInt = fDouble.intValue();
			insertDataStu(a,b,cInt,dInt,eInt,fInt, connSql);
		}  
//		String sql = "select * from student ";
//		stmtAcc = connAcc.createStatement(java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,java.sql.ResultSet.CONCUR_READ_ONLY);
//		//		stmtAcc = connAcc.createStatement();
//		rsAcc = stmtAcc.executeQuery(sql);      
//		//		rsAcc.getRow();
//		rsAcc.absolute(998);
//		while (rsAcc.next()) {
//			String a = rsAcc.getString(1);
//			String b = rsAcc.getString(2);
//			int c = rsAcc.getInt(3);
//			int d = rsAcc.getInt(4);
//			int e = rsAcc.getInt(5);
//			int f = rsAcc.getInt(6);
//			System.out.println(a+","+b+","+c+","+d+","+e+","+f);
//			insertDataStu(a,b,c,d,e,f, connSql);
//		}
//		stmtAcc.close();
//		rsAcc.close();
		
	}
//	插入数据到room表中
	public void insertData(int a,int b,int c,String d,String e,String f,Connection con) throws SQLException{
		String sql = "INSERT INTO `oralexam`.`room` values('"+a+"','"+b+"','"+c+"','"+d+"','"+e+"','"+f+"')";
		stmtSql = con.createStatement();
		stmtSql.executeUpdate(sql);
		stmtSql.close();
	}
//	插入数据到student表中
	public void insertDataStu(String a,String b,int c,int d,int e,int f,Connection con) throws SQLException{
		String sql = "INSERT INTO `oralexam`.`student` values('"+a+"','"+b+"','"+c+"','"+d+"','"+e+"','"+f+"')";
		stmtSql = con.createStatement();
		stmtSql.executeUpdate(sql);
		stmtSql.close();
	}

}
