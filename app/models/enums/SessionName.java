package models.enums;

public enum SessionName {

    //@formatter:off
	USERS("users"),
	SYSUSERS("sysusers")
	//@formatter:on
    ;

    private String aggregationName;

    SessionName(String name) {
        aggregationName = name;
    }

    @Override
    public String toString() {
        return aggregationName;
    }
}
