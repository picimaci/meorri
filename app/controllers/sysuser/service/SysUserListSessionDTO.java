package controllers.sysuser.service;

public class SysUserListSessionDTO {

    public String fullName;
    public String email;
    public String isActive;
    public String sortBy;
    public boolean sortOrder;

    public SysUserListSessionDTO() {

    }

    public SysUserListSessionDTO(final String fullName, final String email, final String isActive, final String sortBy, final boolean sortOrder) {
        this.fullName = fullName;
        this.email = email;
        this.isActive = isActive;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
    }

    public static class DTOBuilder {
        private String nestedUserName;
        private String nestedEmail;
        private String nestedIsActive;
        private String nestedSortBy;
        private boolean nestedSortOrder;

        public DTOBuilder() {
        }

        public DTOBuilder fullName(String userName) {
            this.nestedUserName = userName;
            return this;
        }

        public DTOBuilder email(String email) {
            this.nestedEmail = email;
            return this;
        }

        public DTOBuilder isActive(String isActive) {
            this.nestedIsActive = isActive;
            return this;
        }

        public DTOBuilder sortBy(String sortBy) {
            this.nestedSortBy = sortBy;
            return this;
        }

        public DTOBuilder sortOrder(boolean sortOrder) {
            this.nestedSortOrder = sortOrder;
            return this;
        }

        public SysUserListSessionDTO build() {
            return new SysUserListSessionDTO(nestedUserName, nestedEmail, nestedIsActive, nestedSortBy, nestedSortOrder);
        }

    }
}
