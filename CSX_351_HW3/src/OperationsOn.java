import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class OperationsOn {
	FileReader readerUnsortKeyword,reReadUnsortKeyword;
	BufferedReader bufferUnsortKeyword,reBufferUnsortKeyword;
	String lineToRead;
	String [] keyWords;
	int totalLines=0;
	// open unsorted keyword file
	//counting the no. of lines in unsorted keywords file
	public void openfile() throws IOException {
		//reading the unsorted keywords file 
		
		readerUnsortKeyword = new FileReader("/home/enigr_cros/java Assignments/csx-351-hw3-HarshC19-master/HW3-unsorted-keywords.txt");
		bufferUnsortKeyword = new BufferedReader(readerUnsortKeyword);
		while((lineToRead = bufferUnsortKeyword.readLine()) != null){
			totalLines++; //counting the total lines in unsorted keywords file
		}
	}
	//reread of unsorted keywords file and storing keywords in string array 
	//sorting of string array
	public void readfile() throws Exception{
		keyWords = new String[totalLines]; // dynamic allocation of array of strings for storing unsorted keywords
		
		//Again reading the same file to store in array
		reReadUnsortKeyword = new FileReader("/home/enigr_cros/java Assignments/csx-351-hw3-HarshC19-master/HW3-unsorted-keywords.txt");
		reBufferUnsortKeyword = new BufferedReader(reReadUnsortKeyword);
		int i=0;
		while((lineToRead = reBufferUnsortKeyword.readLine()) != null){
			keyWords[i] = lineToRead; // storing the keywords in array	
			i++;
		}
		sortKeywords(); //function to sort the string array
	}
	
	//function to sort the array using insertion sort
	public void sortKeywords() {
		for(int i=1; i < totalLines; i++) {
			String temp = keyWords[i];
			int j;
			for(j=i; j>0; j--) {
				if(keyWords[j-1].compareTo(temp)<0)
					break;
				keyWords[j] = keyWords[j-1];
			}
			keyWords[j]=temp;
		}
	}
	
	//function to extract keywords from .cpp file and
	// Searching those keywords in keyWords array
	
	
	public void searchInCPPfile() throws Exception{
		//fileReader to read HW3-input-code.cpp file
		//Buffer Reader for CppFile
		
		FileReader CppFile = new FileReader("/home/enigr_cros/java Assignments/csx-351-hw3-HarshC19-master/HW3-input-code.cpp");
		BufferedReader CppfileBuffer = new BufferedReader(CppFile);
		
		int lineCount=0,t,x,KeyWordCount=0; // counters for line number, keywords count
		
		
		while((lineToRead = CppfileBuffer.readLine()) != null) {
			t=0;
			lineCount++;
			x=1;
			
			//read only lines that have some content 
			//ignore all empty lines
			if(lineToRead.trim().length()!=0) {
				//conversion in character array for position
				char [] ArrOfCppCode = lineToRead.toCharArray(); //function to convert string into character array  
				String makeKeyWords = "";				
				
				for(int j=0;j<ArrOfCppCode.length;j++) {
					if(ArrOfCppCode[j]=='/' && ArrOfCppCode[j+1]=='/') // to ignore all statements after comments
						break;
					// syntax of keywords include underscore '_', small alphabets and numbers
					else if((ArrOfCppCode[j]=='_') || (ArrOfCppCode[j]>='a' && ArrOfCppCode[j]<='z') || (ArrOfCppCode[j]>='0' && ArrOfCppCode[j]<='9')) {
						makeKeyWords  = makeKeyWords + ArrOfCppCode[j];
					}						
					else if(makeKeyWords.length()!=0)//if one word is formed check for length and send for search 
					{
						//function which uses binary search for searching the word among keywords
						if(printKeywords(makeKeyWords)==1) {
							KeyWordCount++;//no. of keywords found
							if(x==1) {
								System.out.println();
								System.out.print("Line: "+ lineCount + " "+makeKeyWords+ "("+ t+")");
								x=0;
							}
							else if(x==0)
								System.out.print(makeKeyWords+ "("+ t+")");						
						}							
						t=j+1;//t keeps the count of position of keyword
						makeKeyWords="";// the string is emptied again and will hold next word in upcoming loops
					}
					else
						t=j+1;//or if encountered with another character just increase the position
				}
			}
		}
		System.out.println();
		System.out.println("No. of keywords found: "+KeyWordCount);
		CppfileBuffer.close();
	}
	
	//function to do binary Search in sorted array of keyWords 
	public int printKeywords(String makeKeyWords) {
		int l=0;//lower value of array
		int r=keyWords.length-1;//higher value of array
		int mid;
		while(l<=r) {
			
		 mid = l + (r-l)/2;
			
			if(keyWords[mid].compareTo(makeKeyWords)<0)
				l=mid+1;
			else if(keyWords[mid].compareTo(makeKeyWords)>0)
				r=mid-1;
			else 
				return 1;
		}
		return -1;
	}

}

