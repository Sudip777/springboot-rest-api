package com.sudip.restwebservices.versioning;

public class CustomerV2 {
    private Name name;

    public CustomerV2(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CustomerV2{" +
                "name=" + name +
                '}';
    }
}
