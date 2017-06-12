import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StopWords extends String_process {

	private String filePath;
	private BufferedReader fr;
	private ArrayList<String> StopWordList;

	public StopWords(String str) throws IOException {
		get_StopWordList();
		set(str);
	}

	public String get() {
		return delete_StopWord(inputString).toString();
	}

	private void get_StopWordList() throws IOException {

		try {
			filePath = "stop_words_list.txt";
			fr = new BufferedReader(new FileReader(filePath));
			StopWordList = new ArrayList<>();
			String word;

			while ((word = fr.readLine()) != null) { // �Nstop word�[�J��HashSet��
				StopWordList.add(word);
				char[] chTemp = word.toCharArray();

				if (!word.isEmpty()) { // �p�G�r�ꤣ�O�Ū�,�~�i��}�Y�ഫ�MStemming
					chTemp[0] = Character.toUpperCase(chTemp[0]); // �A��}�Y�Ĥ@�Ӧr���ন�j�g
				}
				StopWordList.add(String.valueOf(chTemp)); // �s�J�P�˦��Ĥ@�Ӧr���O�j�g���r
			}
			fr.close();
		} catch (IOException e) {
			System.out.println("input stop words list error: " + e);
		}
	}

	private String delete_StopWord(String str) throws NullPointerException {

		String strTemp = str;

		try {
			for (int i = 0; i < StopWordList.size(); i++) { // �u�n�٦��U�@�Ӥ����N�~��j��
				String stopword = StopWordList.get(i);
				if (strTemp.matches(".*[^a-zA-Z0-9]+" + stopword + "[^a-zA-Z0-9]+.*")) {
					strTemp = strTemp.replaceAll("[^a-zA-Z0-9]" + stopword + "[^a-zA-Z0-9]", " ");
				}
			}
		} catch (NullPointerException e) {
			System.out.println(e);
		}

		return strTemp;
	}
	
}
