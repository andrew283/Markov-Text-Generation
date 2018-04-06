import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Model {
	private static final String FILE_PATH = "Lorem.txt";
	private static final int ORDER = 2;
	private static final int NUMBER_OF_WORDS_TO_GENERATE = 100;

	public static void main(String[] args) {
		System.out.println(generateText());
	}

	private static HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
	private static ArrayList<String> keyList = new ArrayList<String>();
	private static String[] textSplit;

	public static void putIntoMap() {
		textSplit = convertFileToString(FILE_PATH).split(" ");
		textSplit[textSplit.length - 1] = textSplit[textSplit.length - 1].trim();
		for (int i = 0; i < textSplit.length - ORDER; i++) {
			String currentWord = "";
			for (int j = 0; j < ORDER; j++) {
				currentWord += textSplit[i + j];
				if (j < ORDER - 1) {
					currentWord += " ";
				}
			}
			String nextWord = textSplit[i + ORDER];
			if (map.containsKey(currentWord)) {
				map.get(currentWord).add(nextWord);
			} else {
				ArrayList<String> nextWordsList = new ArrayList<String>();
				nextWordsList.add(nextWord);
				map.put(currentWord, nextWordsList);
				keyList.add(currentWord);
			}
		}
	}

	public static String generateText(String firstWord) {
		putIntoMap();
		String text = firstWord;
		String currentWord = firstWord;
		for (int i = 0; i < NUMBER_OF_WORDS_TO_GENERATE; i++) {
			Random rand = new Random();
			ArrayList<String> nextWordsList = map.get(currentWord);
			String nextWord = "";
			if (nextWordsList != null) {
				nextWord = nextWordsList.get(rand.nextInt(nextWordsList.size()));
			}
			text += " " + nextWord;
			String[] currentWordSplit = currentWord.split(" ");
			currentWord = "";
			for (int j = 1; j < currentWordSplit.length; j++) {
				currentWord += currentWordSplit[j] + " ";
			}
			currentWord += nextWord;
		}
		return text;
	}

	public static String generateText() {
		putIntoMap();
		return generateText(keyList.get(0));
	}

	public static String convertFileToString(String filePath) {
		String text = "";
		String line = null;
		try {
			FileReader fileReader = new FileReader(filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				text += line + "\n";
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}
}
