import java.io.*;
import java.util.HashSet;
import java.util.regex.*;

public class BusinessCardParser {
	private HashSet<String> dictionary;

	public BusinessCardParser(){
		dictionary = new HashSet<String>();

	}

	/* This method will extract the contact information from a document.
	 * It traverses through the input document, line by line, and first
	 * determines whether the line is a name, phone number, email, or
	 * negligible input. From there, the method will create a ContactInfo
	 * object with the stored name, email, and phone number.
	 */
	ContactInfo getContactInfo(String document){

		buildDictionary();

		//Ints to store the probability that a given line is a name and the number of capital letters it has.
		int nameProbability;
		int highestProbabilityFound=0;
		int capitals;

		String name = null;
		String phone = null;
		String email = null;
		String line = null;


		//Split the document into separate lines based on newline character.
		String [] lines = document.split("\\r?\\n");


		for(int count = 0; count < lines.length; count++){
			

			line = lines[count];


			/* Email check. If a line contains an @ character, then it is assumed
			 * that the line is the contact's email, since the @ character is not
			 * present in a contact's name or phone number.
			 */
			if(line.contains("@") && email == null){
				email = line;
				continue;
			}


			/* Number check. This checks uses a regular expression to check that the line is formatted correctly as a
			 * telephone number.
			 *
			 */

			Pattern numberRegex = Pattern.compile("[Phone: || Tel: ||(].{9,}");
			if(numberRegex.matcher(line).matches() == true && phone == null){

				//Strip all non-numerical characters from the line.
				phone = line.replaceAll("[^\\d]", "");
			}
			

			/* Name check. First, it uses a regular expression to filter out all lines that aren't exclusively words.
			 * Then, it goes through and evaluates each line's probability of being a name. The first check is if the 
			 * word is capitalized as a name (e.g. Names have at most 2 capital letters each.) The second check is to see
			 * if the word exists in a word list.
			 */
			

			Pattern nameRegex = Pattern.compile("^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$");
			
			if(nameRegex.matcher(line).matches() == true){
				
				/* All lines start with max probability to be a name.
				 * Probability decreases as the program checks against
				 * criteria.
				 */
				nameProbability = 10;
				capitals=0;
				
				String[] words = line.split(" ");

				for(String word:words){

					if(dictionary.contains(word.toLowerCase())){
						//Word list check.
						nameProbability -= 2;						
					}
					for(char c:word.toCharArray()){
						int num = c;

						if(num >='A' && num<='Z'){
							//Capital letter check.
							capitals++;
						}
					}
				}

				if(capitals > 4){
					
					//Capital letter penalty is weighed more than dictionary penalty.
					nameProbability-=8;
				}

				if(nameProbability > highestProbabilityFound){
					
					name=line;
					highestProbabilityFound = nameProbability;
				}


			}

		}

		return new ContactInfo(name,phone,email);

	}
	

	/*
	 * This method builds a hashSet full of words in the English dictionary to be used for name probability testing.
	 */
	public void buildDictionary(){
		try {


			String sCurrentLine;

			BufferedReader br1 = new BufferedReader(new FileReader("words.txt"));
			while ((sCurrentLine = br1.readLine()) != null) {
				this.dictionary.add(sCurrentLine);
			}
			br1.close();


			br1 = new BufferedReader(new FileReader("words1.txt"));
			while ((sCurrentLine = br1.readLine()) != null) {
				this.dictionary.add(sCurrentLine);
			}
			br1.close();


			br1 = new BufferedReader(new FileReader("words2.txt"));
			while ((sCurrentLine = br1.readLine()) != null) {
				this.dictionary.add(sCurrentLine);
			}
			br1.close();


			br1 = new BufferedReader(new FileReader("words3.txt"));
			while ((sCurrentLine = br1.readLine()) != null) {
				this.dictionary.add(sCurrentLine);
			}


			br1.close();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
}
