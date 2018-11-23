package com.ankon.problem2.domain;

public class Frequency {

    private String subString;

    private Integer frequency;

    public Frequency() {
    }

    public Frequency(String subString, Integer frequency) {
        this.subString = subString;
        this.frequency = frequency;
    }

    public String getSubString() {
        return subString;
    }

    public void setSubString(String subString) {
        this.subString = subString;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Frequency{" +
                "subString='" + subString + '\'' +
                ", frequency=" + frequency +
                '}';
    }
}
