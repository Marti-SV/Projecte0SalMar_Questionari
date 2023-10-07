package com.example.projecte0salmar.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.List;

public class Questions implements Serializable {
    private List<Question> questions;

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public static class Question implements Serializable {
        private List<Integer> answers;

        private Integer id;

        private String title;

        private String poster;

        public List<Integer> getAnswers() {
            return this.answers;
        }

        public void setAnswers(List<Integer> answers) {
            this.answers = answers;
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPoster() {
            return this.poster;
        }

        public void setPoster(String poster) {
            this.poster = poster;
        }
    }
}
