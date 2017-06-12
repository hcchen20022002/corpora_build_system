
public class Symbol extends String_process {

	public Symbol(String str) {
		set(str);
	}

	public String get() {
		return delete_Symbol(inputString).toString();
	}

	private String delete_Symbol(String str) {
		String[] strSplit = str.split("[^a-zA-Z0-9 ]+");
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < strSplit.length - 1; i++) {
			buffer.append(strSplit[i].toString() + " ");
		}

		buffer.append(strSplit[strSplit.length - 1].toString());
		String strTemp = buffer.toString();
		return strTemp;
	}
	
}
