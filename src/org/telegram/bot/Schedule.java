package org.telegram.bot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Schedule {
    private Document document;
    String url;

    public void connect() {
        try {
            document = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTodaysSchedule() {
        Elements elements = document.select("div.today");
        System.out.println("SIZE " + elements.size() );
        String result = document.select("#today").text() + "\n";

        for (int j = 0; j < 5; j++) {
            if (!elements.get(j).hasClass("empty") && elements.get(j).hasClass("rasp-table-row")) {
                String timeString = document.select
                        ("div.rasp-table > div.rasp-table-col-first > div.rasp-table-row > div.rasp-table-inner-cell").get(j-1).text() + "\n";
                timeString = "✅" + timeString.substring(1);
                result += timeString;
                result += elements.get(j).select("div.rasp-table-inner-cell > div.small > div.subject-m").text();
                result += elements.get(j).select("div.rasp-table-inner-cell > div.small > div.subject").text();
                result += "\n";
            }
        }
        return !result.contains("✅") ? "Сегодня выходной! Ну... или пары пока не добавили в расписание" : result;
    }

    public String getWeeksSchedule() {
        String result = "";

        Elements elements1 = document.select("div.rasp-table > div.rasp-table-col");
        for (int i = 0; i < 6; i++) {
            result += elements1.get(i).select
                    ("div.rasp-table-row-header > div.rasp-table-inner-cell").text() + "\n";
            Elements elements3 = elements1.get(i).select
                    ("div.rasp-table-row");

            for (int j = 0; j < 4; j++) {
                if (!elements3.get(j).hasClass("empty")) {
                    String timeString = document.select
                            ("div.chet > div.rasp-table-col-first > div.rasp-table-row > div.rasp-table-inner-cell").get(j).text() + "\n";
                    timeString = "✅" + timeString.substring(1);
                    result += timeString;
                    result += elements3.get(j).select("div.rasp-table-inner-cell > div.small > div.subject-m").text();
                    result += elements3.get(j).select("div.rasp-table-inner-cell > div.small > div.subject").text();
                    result += "\n";
                }
            }
            result += "\n";
        }

        return result;
    }
}
