package santeclair.lunar.framework.util;
import java.util.HashMap;
import java.util.Map;


/**
 * Implementation de l'algo de Damerau-Levenstein
 * http://fr.wikipedia.org/wiki/Distance_de_Damerau-Levenshtein
 * Amelioration de l'algo de Levenstein dans laquel on peut donner plus ou moins de 
 * poids aux opérations (suppression, ajout, remplacement, ou swap)
 * 
 * @author tsensebe
 */
public class DamerauLevenshtein {

	public final static Integer getDamerauLevenshteinDistance(String s1, String s2, Integer delete, Integer insert, Integer replace, Integer swap) {
		if (2 * swap < insert + delete) {
			throw new IllegalArgumentException("Swap doit etre > (insert + delete) /2");
		}
	
		if (s1.isEmpty()) return s2.length() * insert;
		if (s2.isEmpty()) return s1.length() * delete;

		int[][] stringMatrice = new int[s1.length()][s2.length()];
		
		Map<Character, Integer> sourceMap = new HashMap<Character, Integer>();
		if (s1.charAt(0) != s2.charAt(0)) {
			stringMatrice[0][0] = Math.min(replace, delete + insert);
		}
		sourceMap.put(s1.charAt(0), 0);
		
		for (int i = 1; i < s1.length(); i++) {
			Integer deleteDistance = stringMatrice[i - 1][0] + delete;
			Integer insertDistance = (i + 1) * delete + insert;
			Integer matchDistance = i * delete + (s1.charAt(i) == s2.charAt(0) ? 0 : replace);
			stringMatrice[i][0] = Math.min(Math.min(deleteDistance, insertDistance), matchDistance);
		}
		
		for (int j = 1; j < s2.length(); j++) {
			Integer deleteDistance = stringMatrice[0][j - 1] + insert;
			Integer insertDistance = (j + 1) * insert + delete;
			Integer matchDistance = j * insert	+ (s1.charAt(0) == s2.charAt(j) ? 0 : replace);
			stringMatrice[0][j] = Math.min(Math.min(deleteDistance, insertDistance), matchDistance);
		}
		
		for (int i = 1; i < s1.length(); i++) {
			Integer maxS1letterMatchIndex = (s1.charAt(i) == s2.charAt(0) ? 0 : -1);
			for (int j = 1; j < s2.length(); j++) {
				Integer candidateSwapIndex = sourceMap.get(s2.charAt(j));
				Integer jSwap = maxS1letterMatchIndex;
				Integer deleteDistance = stringMatrice[i - 1][j] + delete;
				Integer insertDistance = stringMatrice[i][j - 1] + insert;
				Integer matchDistance = stringMatrice[i - 1][j - 1];
				if (s1.charAt(i) != s2.charAt(j)) {
					matchDistance += replace;
				} else {
					maxS1letterMatchIndex = j;
				}
				Integer swapDistance;
				if (candidateSwapIndex != null && jSwap != -1) {
					Integer iSwap = candidateSwapIndex;
					Integer preSwapCost;
					if (iSwap == 0 && jSwap == 0) {
						preSwapCost = 0;
					} else {
						preSwapCost = stringMatrice[Math.max(0, iSwap - 1)][Math.max(0,jSwap - 1)];
					}
					swapDistance = preSwapCost + (i - iSwap - 1) * delete
							+ (j - jSwap - 1) * insert + swap;
				} else {
					swapDistance = Integer.MAX_VALUE;
				}
				stringMatrice[i][j] = Math.min(
						Math.min(Math.min(deleteDistance, insertDistance),
								matchDistance), swapDistance);
			}
			sourceMap.put(s1.charAt(i), i);
		}
		return stringMatrice[s1.length() - 1][s2.length() - 1];
	
	}





}
