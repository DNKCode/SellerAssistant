package kz.alser.stepanov.semen.sellerassistant.Helpers;

import android.support.annotation.NonNull;

import org.jsoup.Jsoup;

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
}
