import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class split_string {

	public static void main(String[] args) throws IOException {

		String[] inputKeywords = args[args.length - args.length].toString().split("[,\"]");
		String modifyFilePathSlash = "\\";
		String filePath = args[args.length - (args.length - 1)].toString().replace(modifyFilePathSlash,
				modifyFilePathSlash + modifyFilePathSlash);
		BufferedReader fr = new BufferedReader(new FileReader(filePath));

		String searchedRegexFront = ".* ";
		String searchedRegexBehind = " .*";

		String initialStr;
		String adaptedStr;
	    ArrayList<String> initialStrCharArray = new ArrayList<>();
		//char[] initialStrCharArray = new char[500];
		int readChar1, readChar2;
		int strLength = 0;
		int matchedKeywordNum = 0;
		boolean foundThisStr = false;
		Stemmer toStem = new Stemmer();

		for (int i = 0; i < inputKeywords.length; i++) {
			String strTemp = String.valueOf(inputKeywords[i]).toLowerCase();
			char[] chTemp = strTemp.toCharArray();

			chTemp[0] = Character.toUpperCase(chTemp[0]);
			toStem.add(chTemp, chTemp.length);
			toStem.stem();

			inputKeywords[i] = toStem.toString();
		}

		while ((readChar1 = fr.read()) != -1) {

			if (readChar1 == '.') {

				readChar2 = fr.read();

				if (readChar2 == ' ' || readChar2 == '\n' || readChar2 == '\r' || readChar2 == -1) {
					initialStrCharArray.add(String.valueOf((char) readChar1));
					//initialStr = String.valueOf(initialStrCharArray, 0, strLength + 1);
                    initialStr = "";
                    for (String S : initialStrCharArray){
                        initialStr = initialStr + S;
                    }

					initialStr = initialStr.replaceAll(" +", " ");

					String[] cutArray = initialStr.split("[^a-zA-Z]+");
					StringBuffer buf = new StringBuffer();

					for (int i = 0; i < cutArray.length; i++) {
						String strTemp = String.valueOf(cutArray[i]).toLowerCase();
						char[] chTemp = strTemp.toCharArray();

						if (!strTemp.isEmpty()) {

							chTemp[0] = Character.toUpperCase(chTemp[0]);
							toStem.add(chTemp, chTemp.length);
							toStem.stem();

							buf.append(" " + toStem.toString() + " ");
						}
					}

					adaptedStr = buf.toString();
					strLength = 0;
					matchedKeywordNum = 0;

					for (int i = 0; i < inputKeywords.length; i++) {
						if (adaptedStr.matches(searchedRegexFront + inputKeywords[i] + searchedRegexBehind)) {
							matchedKeywordNum++;
						}
					}

					if (matchedKeywordNum == inputKeywords.length) {
						System.out.println(initialStr);
						foundThisStr = true;
					}
                    initialStrCharArray.clear();
				} else {
					initialStrCharArray.add(String.valueOf((char) readChar1));
					initialStrCharArray.add(String.valueOf((char) readChar2));
				}
			} else if (readChar1 == '\n') {
				initialStrCharArray.add(" ");
			} else if (readChar1 == '\r') {

			} else {
				initialStrCharArray.add(String.valueOf((char) readChar1));
			}
		}

		if (foundThisStr == false)
			System.out.println("not found!");

		fr.close();
	}

}
