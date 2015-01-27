package santeclair.lunar.framework.util;

public class Base64DataUrlTools {

    /**
     * 
     * @param base64
     * @return The string représentation of the mimeType from the data URL
     */
    public static String getMimeTypeFromDataUrl(String base64) {
        int position1 = base64.indexOf(":");
        int position2 = base64.indexOf(";");
        return base64.substring(position1 + 1, position2);
    }

    /**
     * 
     * 
     * @param base64
     * @return The String représentation of the file part from a base64 data URL
     */
    public static String getBase64FileFromDataUrl(String base64) {
        String[] base64File = base64.split(",");
        return base64File[1];
    }

    /**
     * From a mimeType return the file extension associated with
     * 
     * @param mimeType
     * @return The String représentation of the file extension associated with the mime type
     */
    public static String getFileExtensionFromMimeType(String mimeType) {
        String[] mimeExtension = mimeType.split("/");
        return mimeExtension[1];
    }

}
