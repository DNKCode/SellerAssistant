package kz.alser.stepanov.semen.sellerassistant.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import org.jsoup.Jsoup;

import kz.alser.stepanov.semen.sellerassistant.R;

import static android.text.Html.fromHtml;

/**
 * Created by semen.stepanov on 15.09.2016.
 */
public class Helpers
{
    public static String ClearSpecialSymbols(String inputStr)
    {
        return html2text(ClearHTMLTags(inputStr))
                .replace("&copy;", "©")
                .replace("&reg;", "®")
                .replace("&euro;", "€")
                .replace("&trade;", "™")
                .replace("&lt;p&gt;", "")
                .replace("&quot;", "'")
                .replace("&#39;", "'")
                .replace("&lt;/p&gt;", "")
                .replace("\r\n", "")
                .replace("&amp;", "&");
    }

    @NonNull
    private static String ClearHTMLTags(String inputStr)
    {
        return fromHtml(inputStr).toString().replaceAll("\n", "").trim();
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    public static boolean isNullOrEmpty (String s)
    {
        if (s == null || s.trim() == "" || s.equals("") || s.isEmpty() || s.length() == 0)
            return true;

        return false;
    }

    public static int GetDefaultLanguageId ()
    {
        try
        {
            Context context = ApplicationContextSingleton.getInstance().getApplicationContext();

            SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.default_language_id_pref), Context.MODE_PRIVATE);
            int defaultValue = Integer.valueOf(context.getResources().getString(R.string.default_language_id));

            return sharedPref.getInt(context.getString(R.string.default_language_id_pref), defaultValue);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 1;
        }
    }
}
