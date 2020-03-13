package com.example.practiceexam.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 试卷-班级关系表
 * @author ShiQing_Chen  2020/3/14  01:23
 **/
@Entity
@Table(name = "paper_class")
public class PaperClass {
    @Id
    private Long id;
    /**
     * 试卷ID
     */
    private Long paperId;
    /**
     * 班级ID
     */
    private Long classId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }
}
