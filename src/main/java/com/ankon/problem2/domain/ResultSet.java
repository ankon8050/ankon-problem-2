package com.ankon.problem2.domain;

import java.util.ArrayList;

public class ResultSet {

    private Integer index;
    private String link;
    private ArrayList<Result> results;

    public ResultSet() {
    }

    public ResultSet(String link, ArrayList<Result> results) {
        this.link = link;
        this.results = results;
    }

    public ResultSet(Integer index, ArrayList<Result> results) {
        this.index = index;
        this.results = results;
    }

    public ResultSet(ArrayList<Result> results) {
        this.results = results;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ResultSet{" +
                "index=" + index +
                ", link='" + link + '\'' +
                ", results=" + results +
                '}';
    }
}
