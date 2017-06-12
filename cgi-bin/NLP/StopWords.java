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

			while ((word = fr.readLine()) != null) { // 將stop word加入倒HashSet中
				StopWordList.add(word);
				char[] chTemp = word.toCharArray();

				if (!word.isEmpty()) { // 如果字串不是空的,才進行開頭轉換和Stemming
					chTemp[0] = Character.toUpperCase(chTemp[0]); // 再把開頭第一個字母轉成大寫
				}
				StopWordList.add(String.valueOf(chTemp)); // 存入同樣但第一個字母是大寫的字
			}
			fr.close();
		} catch (IOException e) {
			System.out.println("input stop words list error: " + e);
		}
	}

	private String delete_StopWord(String str) throws NullPointerException {

		String strTemp = str;

		try {
			for (int i = 0; i < StopWordList.size(); i++) { // 只要還有下一個元素就繼續迴圈
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
