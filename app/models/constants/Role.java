package models.constants;

import play.db.jpa.JPA;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "picimaci_role")
public class Role implements Comparable {
    @Id
    public long id;

    @Column(name = "message_key", columnDefinition = "nvarchar(255)", nullable = false)
    public String messageKey;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "picimaci_role_privilege", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    public Set<Privilege> privileges;

    public static List<models.constants.Role> getRoles() {
        String select = "SELECT r FROM role r";
        TypedQuery<models.constants.Role> query = JPA.em().createQuery(select, models.constants.Role.class);
        return query.getResultList();
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof models.constants.Role) {
            return ((models.constants.Role) o).messageKey == this.messageKey;
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        models.constants.Role r;
        try {
            r = (models.constants.Role) o;
        } catch (ClassCastException e) {
            return 1;
        }
        return messageKey.compareTo(r.messageKey);
    }
}
