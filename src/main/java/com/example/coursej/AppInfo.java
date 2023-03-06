package com.example.coursej;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app-info")
public class AppInfo {

    private String author;

    private int creationDate;
    private String profile;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreationDate(int creationDate) {
        this.creationDate = creationDate;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        System.out.println(author);
        System.out.println(creationDate);
        System.out.println(profile);
    }
}
