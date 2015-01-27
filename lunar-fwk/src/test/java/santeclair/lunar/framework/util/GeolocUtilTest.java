package santeclair.lunar.framework.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test pour GeolocUtil.
 * @author tsensebe
 *
 */
public class GeolocUtilTest {

	@Test
	public void distance() {
		double latitudeParis = 48.5112;
		double longitudeParis = 2.2055;
		double latitudeLondre = 51.3030;
		double longitudeLondre = 0.732;
		double latitudeNY = 40.4251;
		double longitudeNY = -74.021;
		
		// Distance Paris - Londre 328km.
		Double distance = GeolocUtil.distance(latitudeParis, longitudeParis, latitudeLondre, longitudeLondre);
		assertEquals(new Double(328), new Double(Math.round(distance)));
		
		// Distance Paris - NewYork 5861km.
		distance = GeolocUtil.distance(latitudeParis, longitudeParis, latitudeNY, longitudeNY);
		assertEquals(new Double(5861), new Double(Math.round(distance)));
	}
	
}
