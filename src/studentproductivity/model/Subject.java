package studentproductivity.model;

import java.io.Serializable;

public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String professorName;
    private double score;

    public Subject(String id, String name, String professorName, double score) {
        this.id = id;
        this.name = name;
        this.professorName = professorName;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfessorName() {
        return professorName;
    }

    public double getScore() {
        return score;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}