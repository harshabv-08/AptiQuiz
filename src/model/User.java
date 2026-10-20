package model;

public class User {

    private int userId;
    private String name;
    private String email;
    private String password;
    private String college;
    private String department;
    private int year;
    private String phone;

    public User() {
    }

    public User(int userId, String name, String email, String password,
                String college, String department, int year, String phone) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.college = college;
        this.department = department;
        this.year = year;
        this.phone = phone;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
