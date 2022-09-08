package model;

public class Users {
    private int users_id;
    private String users_name;
    private String users_email;
    private String users_password;
    private String users_phone;
    private String users_address;
    private boolean users_role;

    public Users() {
    }

    public Users(int users_id, String users_name, String users_email, String users_password, String users_phone, String users_address, boolean users_role) {
        this.users_id = users_id;
        this.users_name = users_name;
        this.users_email = users_email;
        this.users_password = users_password;
        this.users_phone = users_phone;
        this.users_address = users_address;
        this.users_role = users_role;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getUsers_name() {
        return users_name;
    }

    public void setUsers_name(String users_name) {
        this.users_name = users_name;
    }

    public String getUsers_email() {
        return users_email;
    }

    public void setUsers_email(String users_email) {
        this.users_email = users_email;
    }

    public String getPassword() {
        return users_password;
    }

    public void setPassword(String users_password) {
        this.users_password = users_password;
    }

    public String getUsers_phone() {
        return users_phone;
    }

    public void setUsers_phone(String users_phone) {
        this.users_phone = users_phone;
    }

    public String getUsers_address() {
        return users_address;
    }

    public void setUsers_address(String users_address) {
        this.users_address = users_address;
    }

    public boolean isUsers_role() {
        return users_role;
    }

    public void setUsers_role(boolean users_role) {
        this.users_role = users_role;
    }
}
