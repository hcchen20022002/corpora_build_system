import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NLP_FactoryMain {

	public static void main(String[] args) throws IOException {

		String select = args[args.length - args.length].toString();
		String modifyFilePathSlash = "\\"; // 替換檔案路徑的斜線
		String filePath = args[args.length - (args.length - 1)].toString().replace(modifyFilePathSlash,
				modifyFilePathSlash + modifyFilePathSlash);
		BufferedReader fr = new BufferedReader(new FileReader(filePath));
		String str;

		// first char is mean "select delete StopWords process".
		// Second char is mean "select delete Symbol process".
		// Third char is mean "select Stemming process".

		while ((str = fr.readLine()) != null) {
			if (select.charAt(0) == '1') {
				StopWords SW = new StopWords(str);
				str = SW.get();
			}
			if (select.charAt(1) == '1') {
				Symbol SB = new Symbol(str);
				str = SB.get();
			}
			if (select.charAt(2) == '1') {
				Stemming ST = new Stemming(str);
				str = ST.get();
			}
			System.out.println(str);
		}
		fr.close();
	}
	
}