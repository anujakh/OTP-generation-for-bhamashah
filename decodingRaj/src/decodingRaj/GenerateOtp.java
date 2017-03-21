package decodingRaj;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Scanner;

public class GenerateOtp {
	
	int Otp;
	boolean used;
	String url,familyId,bhamashahId,mobileNo;
	FetchData fetchData;
	int month,year;
	
	//Getters and Setters
	public int getOtp() {
		return Otp;
	}
	void setOtp(int otp) {
		Otp = otp;
	}
	public boolean isUsed() {
		return used;
	}
	void setUsed(boolean used) {
		this.used = used;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFamilyId() {
		return familyId;
	}
	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}
	public String getBhamashahId() {
		return bhamashahId;
	}
	public void setBhamashahId(String bhamashahId) {
		this.bhamashahId = bhamashahId;
	}
	
	
	
	public GenerateOtp(String fId,String clientId) {
		url = "https://apitest.sewadwaar.rajasthan.gov.in/app/live/Service/hofAndMember/ForApp/"+fId+"?client_id="+clientId;
		fetchData = new FetchData(url);
		
		//Setting Up Data
		month = Calendar.MONTH+1;
		year = Calendar.getInstance().get(Calendar.YEAR)-2000;
		familyId = fetchData.getFamilyId();
		bhamashahId = fetchData.getBhamashahId();
		mobileNo = fetchData.getMobileNo();
		setOtp();
		
		
		//Send data to Server
		sendData();
		
	}
	
	//Set the OTP
	public void setOtp(){
		String str = month+familyId+year;
		char [] chArray=str.toCharArray();
		int k = 73;
		for(char ch: chArray){
			k = k*3 + ch;
		}
		Otp = k + (k%1559);
		System.out.println("OTP: "+Otp);
	}
	
	
	//To send the data to Server
	public void sendData(){
		@SuppressWarnings("unused")
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		final String DB_URL = "jdbc:mysql://localhost/decodingRaj";
		
		//  Database credentials
		final String USER = "root";
		final String PASS = "";
		
		//Month and Year
		
		String date = month+","+year;
		
		//Family ID and Ack ID
		String familyAck = "\'"+familyId+"\'"+","+"\'"+bhamashahId+"\'";
		
		String otpUsed = "\'"+mobileNo+"\'"+","+Otp+","+used;
		
		
		
		
		try{
		      Class.forName("com.mysql.jdbc.Driver");
		      Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      Statement stmt = conn.createStatement();
		      String sql;
		      sql = "INSERT INTO bhamashah_table (FamilyId,AckId,Month,Year,Mobile,OTP,Used) VALUES("+familyAck+","+date+","+otpUsed+")";
//		      System.out.println(sql);
		      stmt.executeUpdate(sql);
		      
		      conn.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
public static void main(String args[]){
		
	
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Family Id");
		String family = sc.nextLine();
		System.out.println("Enter the Client Id");
		String client = sc.nextLine();
		@SuppressWarnings("unused")
		GenerateOtp genotp = new GenerateOtp(family,client);
		sc.close();
	}

}
