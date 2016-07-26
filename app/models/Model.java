package models;

import play.db.jpa.JPA;

import java.io.Serializable;

/**
 * Created by PICIMACI on 2016.07.25..
 */
public class Model implements Serializable {

    public void save() {
        JPA.em().merge(this);
    }

    public void persist() {
        JPA.em().persist(this);
    }

    public void flush() {
        JPA.em().flush();
    }

    public void delete() {
        JPA.em().remove(this);
    }

}
