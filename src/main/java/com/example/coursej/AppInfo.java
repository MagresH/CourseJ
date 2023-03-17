package com.example.coursej;

import lombok.Setter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app-info")
@Setter
public class AppInfo {
    private String name;
    private String author;
    private int creationDate;
    private String profile;



    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        System.out.println(name);
        System.out.println(author);
        System.out.println(creationDate);
        System.out.println(profile);

    }
}
