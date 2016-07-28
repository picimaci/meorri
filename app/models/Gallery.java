package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by PICIMACI on 2016.07.24..
 */
@Entity(name = "picimaci_gallery")
public class Gallery extends Model{
    @Id
    @GeneratedValue
    public long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "type_key", nullable = false)
    public String key;
}