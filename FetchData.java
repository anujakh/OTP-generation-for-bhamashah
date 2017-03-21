package decodingRaj;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.json.simple.*;

public class FetchData {
	
	String familyId;
	String bhamashahId;
	String mobileNo;
	URL url;
	
	public String getUrl() {
		return url.toString();
	}
	public void setUrl(String url) {
		URL temp=null;
		try {
			temp = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.url = temp;
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
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	FetchData(String urlString){
		try {
//			System.out.println("Hello World");
			setUrl(urlString);
		//Fetching the JSON file from url
			File file = new File("tempReport.json");
			FileUtils.copyURLToFile(url, file);
			
		//Extracting Data
			FileInputStream fis;
			StringBuffer sb = new StringBuffer(150);
					
			fis = new FileInputStream(file);
			int ch;
			while((ch=fis.read())!=-1){
//				System.out.print((char)ch);
				sb.append((char)ch);
			}
			fis.close();
			
			
		//Work on StringBuffer
		// TODO Use reader class instead using String class to read the url file.
			String myJson = sb.toString();
			JSONObject root = (JSONObject) JSONValue.parse(myJson);
			JSONObject hof = (JSONObject) root.get("hof_Details");
			setMobileNo((String)hof.get("MOBILE_NO"));
			setFamilyId((String)hof.get("FAMILYIDNO"));
			setBhamashahId((String)hof.get("BHAMASHAH_ID"));
			
			
//			System.out.println("\nMobile Number is:"+getMobileNo());
//			System.out.println("\nFamily Id is:"+getFamilyId());
//			System.out.println("\nBhamashah Id is:"+getBhamashahId());
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
        
	}
}
