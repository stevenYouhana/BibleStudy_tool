package Utility;

public class Log {
	
	public void p(String toLog) {
		System.out.println(toLog);
	}
	public void p(int[] generateVerseCode) {
		if(generateVerseCode == null) {
			System.out.println("int array is null!");
			return;
		}
		System.out.println(
				"int array: "+
						generateVerseCode[0]+
						generateVerseCode[1]+
						generateVerseCode[2]
						);
	}
}
