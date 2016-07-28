package controllers.DTOs.authorization;

public class KeyValueDTO implements Comparable<KeyValueDTO> {

    public long id;
    public String messageKey;
    public String label;

    public KeyValueDTO() {
    }

    public KeyValueDTO(long id, String messageKey, String label) {
        this.id = id;
        this.messageKey = messageKey;
        this.label = label;
    }

    @Override
    public int compareTo(KeyValueDTO o) {
        return this.label.compareToIgnoreCase(o.label);
    }

}