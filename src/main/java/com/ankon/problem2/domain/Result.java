package com.ankon.problem2.domain;

public class Result {

    private Double score;
    private Integer tokens;
    private Integer count;
    private String sourceCode;

    public Result() {
    }

    public Result(Double score, Integer tokens, Integer count, String sourceCode) {
        this.score = score;
        this.tokens = tokens;
        this.count = count;
        this.sourceCode = sourceCode;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getTokens() {
        return tokens;
    }

    public void setTokens(Integer tokens) {
        this.tokens = tokens;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    @Override
    public String toString() {
        return "Result{" +
                "score=" + score +
                ", tokens=" + tokens +
                ", count=" + count +
                ", sourceCode='" + sourceCode + '\'' +
                '}';
    }
}
