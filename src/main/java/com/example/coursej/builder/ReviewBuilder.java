package com.example.coursej.builder;

import com.example.coursej.model.Review;

public class ReviewBuilder {
    private String title;
    private String description;

    public ReviewBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ReviewBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public Review createReview() {
        return new Review(title, description);
    }
}