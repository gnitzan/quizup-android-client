package com.rom.quizup.client.models;

import java.util.List;

public class Board {

    private Integer allottedTime;

    private List<String> answers;

    private String boardId;

    private List<String> gridDefinitions;

    private String id;

    private Integer level;

    private List<String> questions;

    /**
     * @return value or {@code null} for none
     */
    public Integer getAllottedTime() {
        return allottedTime;
    }

    /**
     * @param allottedTime allottedTime or {@code null} for none
     */
    public Board setAllottedTime(Integer allottedTime) {
        this.allottedTime = allottedTime;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public List<String> getAnswers() {
        return answers;
    }

    /**
     * @param answers answers or {@code null} for none
     */
    public Board setAnswers(List<String> answers) {
        this.answers = answers;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getBoardId() {
        return boardId;
    }

    /**
     * @param boardId boardId or {@code null} for none
     */
    public Board setBoardId(String boardId) {
        this.boardId = boardId;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public List<String> getGridDefinitions() {
        return gridDefinitions;
    }

    /**
     * @param gridDefinitions gridDefinitions or {@code null} for none
     */
    public Board setGridDefinitions(List<String> gridDefinitions) {
        this.gridDefinitions = gridDefinitions;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public String getId() {
        return id;
    }

    /**
     * @param id id or {@code null} for none
     */
    public Board setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level level or {@code null} for none
     */
    public Board setLevel(Integer level) {
        this.level = level;
        return this;
    }

    /**
     * @return value or {@code null} for none
     */
    public List<String> getQuestions() {
        return questions;
    }

    /**
     * @param questions questions or {@code null} for none
     */
    public Board setQuestions(List<String> questions) {
        this.questions = questions;
        return this;
    }
}