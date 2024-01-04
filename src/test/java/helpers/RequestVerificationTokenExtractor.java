package helpers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestVerificationTokenExtractor {
    public static List<String> extractTokens(String url, String cookieName, String cookieValue) throws IOException{
        List<String> tokenValues = new ArrayList<>();

        Document document = Jsoup.connect(url)
                .cookie(cookieName, cookieValue)
                .get();
        tokenValues.add(document.connection().response().cookie("__RequestVerificationToken"));
        tokenValues.add(document.select("input[name=__RequestVerificationToken]")
                .attr("value"));

        return tokenValues;
    }
}
