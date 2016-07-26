package models;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;

/**
 * Created by PICIMACI on 2016.07.24..
 */
@Entity(name = "gallery")
public class Gallery extends Model{
    @Id
    @GeneratedValue
    public long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "type_key", nullable = false)
    public String key;
}