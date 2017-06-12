
public class Stemming extends String_process {

	public Stemming(String str) {
		set(str);
	}

	public String get() {
		return stem_String(inputString);
	}

	private String stem_String(String str) {
		String[] strSplit = str.split("[^a-zA-Z0-9]+");
		StringBuffer buffer = new StringBuffer();
		Stemmer S = new Stemmer();

		for (int i = 0; i < strSplit.length - 1; i++) {
			char[] charArray = strSplit[i].toCharArray();
			S.add(charArray, charArray.length);
			S.stem();
			buffer.append(S.toString() + " ");
		}
		char[] charArray = strSplit[strSplit.length - 1].toCharArray();
		S.add(charArray, charArray.length);
		S.stem();
		buffer.append(S.toString());
		String strTemp = buffer.toString();
		return strTemp;
	}
	
}
