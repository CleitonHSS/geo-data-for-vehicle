package br.com.project.cleiton.geodata.domain;

import java.io.Serializable;

public class Data implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String body;

    public Data(Integer id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Data{" + "id=" + id + ", title='" + title + '\'' + ", body='" + body + '\'' + '}';
    }
}
