import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Driver {
	public static void main(String [] args)throws Exception{

		Scanner keyboard = new Scanner(System.in);
		BusinessCardParser bcParser = new BusinessCardParser();

		System.out.println("Input business card information, line-by-line. Press ENTER to input a new line of information. When finished, type DONE on a single line and press ENTER.");

		String data = "";
		String input="";  

		InputStreamReader r=new InputStreamReader(System.in);  
		BufferedReader br=new BufferedReader(r);  


		while(!input.equals("DONE")){  
			input=br.readLine();  
			
			if(!input.equals("DONE")){
			data = data.concat(input);
			data = data.concat("\n");
			}
		}  
		
		br.close();  
		r.close();  

		ContactInfo contact = bcParser.getContactInfo(data);
		
		System.out.println("Name: "+contact.getName());
		System.out.println("Phone: "+contact.getPhoneNumber());
		System.out.println("Email: "+contact.getEmailAddress());

	}
}
