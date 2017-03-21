package checkOtp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Scanner;

public class CheckOtp {
	
	int otp;
	String familyId;
	int month,year;
	
	public CheckOtp(String familyId,int otp){
		this.familyId=familyId;
		month = Calendar.MONTH+1;
		year = Calendar.getInstance().get(Calendar.YEAR)-2000;
		this.otp=otp;
	}
	
	public boolean verifyOtp() {
		boolean check=false;
		@SuppressWarnings("unused")
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/decodingRaj";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "";
		
		Connection conn = null;
		   Statement stmt = null;
		   try{
			  Class.forName("com.mysql.jdbc.Driver");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT * FROM bhamashah_table WHERE FamilyId=\'"+familyId+"\' AND OTP=\'"+otp+"\'";
		      ResultSet rs = stmt.executeQuery(sql);

		      while(rs.next()){
		         //Retrieve by column name
		    	 int tempUsed = rs.getInt("used");
		         String tempFamilyId  = rs.getString("FamilyId");
		         int tempMonth = rs.getInt("Month");
		         int tempYear = rs.getInt("Year");
		         int tempOtp = rs.getInt("OTP");
		         
		         
		        // System.out.println(tempFamilyId+"\t"+tempMonth+"\t"+tempYear+"\t"+tempOtp);
		         
		         if(tempFamilyId.equals(familyId)&&tempMonth==month&&tempYear==year&&tempOtp==otp){
		        	 if(tempUsed==1){
		        		 break;
		        	 }
		        	 check=true;
		        	 break;
		         }
		      }
		      rs.close();
		      stmt.close();
		      conn.close();
		   }catch(SQLException se){
		      se.printStackTrace();
		   }catch(Exception e){
			   e.printStackTrace();
		   }finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		   }
		   
		   return check;
	}
	
	public static void main(String args[]){
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter family id: ");
		String family = sc.nextLine();
		System.out.println("Enter OTP: ");
		int otp = sc.nextInt();
		
		
		CheckOtp co = new CheckOtp(family, otp);
		System.out.println("Verified?: "+co.verifyOtp());
		sc.close();
		
	}
}
