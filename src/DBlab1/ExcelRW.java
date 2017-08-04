package DBlab1;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.text.SimpleDateFormat;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ExcelRW {
	Connection conn = null;
	Statement stm;
	public void ExcelOperation() throws SQLException{
		conn = connectSql();
		createTable();
		readExcelWriteSql();
		readExcelWriteSqlStu();
		conn.close();
	} 
	
//	read data from excel file and store it to sql database 
	public void readExcelWriteSql(){
		jxl.Workbook readwb = null; 
		java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
		
		try{   
			/*
			 * 构建Workbook对象, 只读Workbook对象
			 * 直接从本地文件创建Workbook 
			 */
			InputStream instream = new FileInputStream("room.xls");   
			readwb = Workbook.getWorkbook(instream);   
			/*
			 * Sheet的下标是从0开始   
			 * 获取第一张Sheet表
			 */
			Sheet readsheet = readwb.getSheet(0);   
			//获取Sheet表中所包含的总列数   
			int rsColumns = readsheet.getColumns();   
			//获取Sheet表中所包含的总行数   
			int rsRows = readsheet.getRows();   
			//获取指定单元格的对象引用   
			for (int i = 1; i < rsRows; i++){
				
				String[] datas = new String[rsColumns];
				for (int j = 0; j < rsColumns; j++){   
					Cell cell = readsheet.getCell(j, i);
					datas[j] = cell.getContents();
//					System.out.print(cell.getContents() + " ");   
				} 
				int a = Integer.parseInt(datas[0]);
				int b = Integer.parseInt(datas[1]);
				int c = Integer.parseInt(datas[2]);
				String dString = datas[3];
				String eString = datas[4];
//				Date eDate = formatter.parse(datas[4]);
				String fString = datas[5];
				System.out.println("kdno: " + datas[0]+", kcno: " + datas[1]+", ccno: "+datas[2]+", kdname: "+datas[3]+", exptime: "+datas[4]);
				insertData(a, b, c, dString, eString, fString,conn);
			}   
		} catch (Exception e) {   
			e.printStackTrace();   
		} finally {   
			readwb.close();   
		}   
	}
//	读取excel文件并且将数据写入sql数据库
	public void readExcelWriteSqlStu(){
		jxl.Workbook readwb = null; 
		java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm");
		
		try{   
			/*
			 * 构建Workbook对象, 只读Workbook对象
			 * 直接从本地文件创建Workbook 
			 */
			InputStream instream = new FileInputStream("student.xls");   
			readwb = Workbook.getWorkbook(instream);   
			/*
			 * Sheet的下标是从0开始   
			 * 获取第一张Sheet表
			 */
			Sheet readsheet = readwb.getSheet(0);   
			//获取Sheet表中所包含的总列数   
			int rsColumns = readsheet.getColumns();   
			//获取Sheet表中所包含的总行数   
			int rsRows = readsheet.getRows();   
			//获取指定单元格的对象引用   
			for (int i = 1; i < rsRows; i++){
				
				String[] datas = new String[rsColumns];
				for (int j = 0; j < rsColumns; j++){   
					Cell cell = readsheet.getCell(j, i);
					datas[j] = cell.getContents();
//					System.out.print(cell.getContents() + " ");   
				} 
				String aString = datas[0];
				String bString = datas[1];
				int c = Integer.parseInt(datas[2]);
				int d = Integer.parseInt(datas[3]);
				int e = Integer.parseInt(datas[4]);
				int f = Integer.parseInt(datas[5]);
				System.out.println("registno: " + datas[0]+", name: " + datas[1]+", kdno: "+datas[2]+", kcno: "+datas[3]); 
				insertDataStu(aString, bString, c, d, e, f,conn);
			}   
		} catch (Exception e) {   
			e.printStackTrace();   
		} finally {   
			readwb.close();   
		}   
	}
//	连接sql数据库
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
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException se) {
			System.out.println ("Error when connecting database");
			se.printStackTrace();
		}
//		connSql = DriverManager.getConnection(url,username,password);
		return conn;
	}
//	创建表
	public void createTable() throws SQLException{
		String sql = "CREATE TABLE `oralexam`.`roomexc` (`kdno` INT NULL,`kcno` INT NULL,`ccno` INT NULL,`kdname` VARCHAR(45) NULL,`exptime` DATETIME NULL, `papername` VARCHAR(45) NULL)";
		String sql2 = "CREATE TABLE `oralexam`.`studentexc` (`registno` VARCHAR(10) NOT NULL,`name` VARCHAR(20) NOT NULL,`kdno` INT NULL,`kcno` INT NULL, `ccno` INT NULL,`seat` INT NULL)";
		stm = conn.createStatement();
		stm.execute(sql);
		stm.execute(sql2);
		stm.close();
	}
//	插入数据到roomexc表中
	public void insertData(int a,int b,int c,String d,String e,String f,Connection con) throws SQLException{
		String sql = "INSERT INTO `oralexam`.`roomexc` values('"+a+"','"+b+"','"+c+"','"+d+"','"+e+"','"+f+"')";
		stm = con.createStatement();
		stm.executeUpdate(sql);
		stm.close();
	}
//	插入数据到studentexc表中
	public void insertDataStu(String a,String b,int c,int d,int e,int f,Connection con) throws SQLException{
		String sql = "INSERT INTO `oralexam`.`studentexc` values('"+a+"','"+b+"','"+c+"','"+d+"','"+e+"','"+f+"')";
		stm = con.createStatement();
		stm.executeUpdate(sql);
		stm.close();
	}
}

