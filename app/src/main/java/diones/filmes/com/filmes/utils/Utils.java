package diones.filmes.com.filmes.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public final static String IMG_URL_NAME = "http://image.tmdb.org/t/p/";
    public final static String IMG_SIZE_NORMAL = "w500";

    public static String getImageUrl(String imageName) {
        return IMG_URL_NAME + IMG_SIZE_NORMAL + imageName;
    }

    public static String getDateFormat(String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date = dateFormat.parse(data);
            dateFormat.applyPattern("dd/MM/yyyy");
            return dateFormat.format(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return data;

    }
}
