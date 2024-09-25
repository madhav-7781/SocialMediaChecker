package com.Madhav.SocialMediaChecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocialMediaChecker {
	public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a company domain name as an argument.");
            System.exit(1);
        }

        String domain = args[0];
        System.out.println("Checking social media activity for domain: " + domain);

        // Checking Facebook
        checkFacebookActivity(domain);

        // Checking LinkedIn
        checkLinkedInActivity(domain);

        // Checking Instagram
        checkInstagramActivity(domain);
    }

    private static void checkFacebookActivity(String domain) {
        String facebookUrl = "https://www.facebook.com/" + domain;
        try {
            Document doc = Jsoup.connect(facebookUrl).get();
            // Assuming posts have a class name ".timestampContent"
            Element lastPost = doc.select(".timestampContent").first();
            if (lastPost != null) {
                String dateStr = lastPost.text();
                checkRecentActivity(dateStr, "Facebook");
            } else {
                System.out.println("No recent activity found on Facebook.");
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch Facebook page: " + facebookUrl);
        }
    }

    private static void checkLinkedInActivity(String domain) {
        String linkedInUrl = "https://www.linkedin.com/company/" + domain;
        try {
            Document doc = Jsoup.connect(linkedInUrl).get();
            // Assuming posts have a class name ".activity-update"
            Element lastPost = doc.select(".activity-update").first();
            if (lastPost != null) {
                String dateStr = lastPost.text();
                checkRecentActivity(dateStr, "LinkedIn");
            } else {
                System.out.println("No recent activity found on LinkedIn.");
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch LinkedIn page: " + linkedInUrl);
        }
    }

    private static void checkInstagramActivity(String domain) {
        String instagramUrl = "https://www.instagram.com/" + domain;
        try {
            Document doc = Jsoup.connect(instagramUrl).get();
            // Assuming posts have a class name "time"
            Element lastPost = doc.select("time").first();
            if (lastPost != null) {
                String dateStr = lastPost.attr("datetime");
                checkRecentActivity(dateStr, "Instagram");
            } else {
                System.out.println("No recent activity found on Instagram.");
            }
        } catch (IOException e) {
            System.out.println("Failed to fetch Instagram page: " + instagramUrl);
        }
    }

    private static void checkRecentActivity(String dateStr, String platform) {
        // Parse date string to determine if the post is recent
        LocalDate postDate = parseDate(dateStr);
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);

        if (postDate != null && postDate.isAfter(sixMonthsAgo)) {
            System.out.println("The company has been active on " + platform + " within the last 6 months.");
        } else {
            System.out.println("No recent activity found on " + platform + ".");
        }
    }

    static LocalDate parseDate(String dateStr) {
        // Example dateStr formats: "January 1, 2023" or "2023-01-01"
        try {
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("MMMM d, yyyy");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (dateStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(dateStr, formatter2);
            } else {
                return LocalDate.parse(dateStr, formatter1);
            }
        } catch (Exception e) {
            System.out.println("Failed to parse date: " + dateStr);
            return null;
        }
    }
}
