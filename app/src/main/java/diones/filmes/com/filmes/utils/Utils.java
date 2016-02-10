package diones.filmes.com.filmes.utils;

public class Utils {
    public final static String IMG_URL_NAME = "http://image.tmdb.org/t/p/";
    public final static String IMG_SIZE_NORMAL = "w500";

    public static String getImageUrl(String imageName) {
        return IMG_URL_NAME + IMG_SIZE_NORMAL + imageName;
    }
}
