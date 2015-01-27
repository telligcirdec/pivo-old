package santeclair.lunar.framework.util;


/**
 * Classe utilitaire pour des calcules liés à la localisation.
 * @author tsensebe
 *
 */
public class GeolocUtil {

	
	/**
	 * Calcule la distance en km entre deux endroits.
	 * @param latitude1 latitude du 1ier endroit
	 * @param longitude1 longitude du 1ier endroit
	 * @param latitude2 latitude du 2iem endroit
	 * @param longitude2 longitude du 2iem endroit
	 * @return la distance en km.
	 */
	public static Double distance(double latitude1, double longitude1, double latitude2, double longitude2) {
	    double rayonTerre = 6371;
	    double dLat = Math.toRadians(latitude2-latitude1);
	    double dLng = Math.toRadians(longitude2-longitude1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    return rayonTerre * c;
	}
	
	
}
