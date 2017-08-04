package DBlab1;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws SQLException, IOException{
		System.out.println("Input A to read Access database and, input E to read Excel files\n");
		Scanner input = new Scanner(System.in);
		String type = input.nextLine();
//		System.out.println("Please input the file name: ");
//		String name = input.nextLine();
		if (type.equalsIgnoreCase("A")) {
			AccessRW arw = new AccessRW();
			arw.accessOperation();
			System.out.println("Now, you can read Excel files by input input any letter:");
			input.nextLine();
			ExcelRW er = new ExcelRW();
			er.ExcelOperation();
		}else if (type.equalsIgnoreCase("E")) {
			ExcelRW er = new ExcelRW();
			er.ExcelOperation();
			System.out.println("Now, you can read Access database files by input input any letter:");
			input.nextLine();
			AccessRW arw = new AccessRW();
			arw.accessOperation();
		}else {
			System.out.println("Sorry, please try again!");
		}
		System.out.println("HAHA, Read and Write files success!");
				
	}
}
