package com.sjtu.project.servicemanagement.domain;


public enum ParamIn {
    /**
     * param in path
     */
    PATH("path"),
    /**
     * param in query
     */
    QUERY("query");

    private String value;

    ParamIn(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
