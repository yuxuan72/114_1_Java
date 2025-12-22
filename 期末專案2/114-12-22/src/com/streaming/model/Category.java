package com.streaming.model;

import java.util.Objects;

public class Category {
    private final String name;
    public Category(String name) { this.name = Objects.requireNonNull(name); }
    public String getName() { return name; }
}
