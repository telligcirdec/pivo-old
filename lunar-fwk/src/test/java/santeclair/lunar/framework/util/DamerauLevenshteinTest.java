package santeclair.lunar.framework.util;

import org.junit.Assert;
import org.junit.Test;

public class DamerauLevenshteinTest {

	
	@Test
	public void deleteTest() {
		String s1 = "mouton";
		String s2 = "mouto";
		
		Integer deleteCost = 1;
		Integer damerauLeven = DamerauLevenshtein.getDamerauLevenshteinDistance(s1, s2, deleteCost, 1, 1, 3);
		Assert.assertEquals(deleteCost, damerauLeven);
		
		deleteCost = 10;
		damerauLeven = DamerauLevenshtein.getDamerauLevenshteinDistance(s1, s2, deleteCost, 1, 1, 12);
		Assert.assertEquals(deleteCost, damerauLeven);
	}	
		
	@Test
	public void insertTest() {
		String s1 = "mouton";
		String s2 = "moutoXn";
		
		Integer insertCost = 1;
		Integer damerauLeven = DamerauLevenshtein.getDamerauLevenshteinDistance(s1, s2, 1, insertCost, 1, 3);
		Assert.assertEquals(insertCost, damerauLeven);
		
		insertCost = 10;
		damerauLeven = DamerauLevenshtein.getDamerauLevenshteinDistance(s1, s2, 1, insertCost, 1, 12);
		Assert.assertEquals(insertCost, damerauLeven);
	}
	
	@Test
	public void replaceTest() {
		String s1 = "mouton";
		String s2 = "moutom";
		
		Integer replaceCost = 1;
		Integer damerauLeven = DamerauLevenshtein.getDamerauLevenshteinDistance(s1, s2, 1, 1, replaceCost, 3);
		Assert.assertEquals(replaceCost, damerauLeven);
		
		replaceCost = 3;
		damerauLeven = DamerauLevenshtein.getDamerauLevenshteinDistance(s1, s2, 5, 5, replaceCost, 12);
		Assert.assertEquals(replaceCost, damerauLeven);
	}
	
	@Test
	public void swapTest() {
		String s1 = "mouton";
		String s2 = "moutno";
		
		Integer swapCost = 1;
		Integer damerauLeven = DamerauLevenshtein.getDamerauLevenshteinDistance(s1, s2, 1, 1, 5, 1);
		Assert.assertEquals(swapCost, damerauLeven);
		
		swapCost = 10;
		damerauLeven = DamerauLevenshtein.getDamerauLevenshteinDistance(s1, s2, 10, 10, 5, swapCost);
		Assert.assertEquals(swapCost, damerauLeven);
	}	
	
}
	





