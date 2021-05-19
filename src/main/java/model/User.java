package model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clasa pentru user
 * @author Birlutiu Claudiu-Andrei, CTI-ro 2021
 */
public  class User implements Serializable {
    /**
     * userId
     */
    private Integer userId;
    /**
     *userName
     */
    private String userName;
    /**
     *password
     */
    private String password;
    /**
     *type
     */
    private UserType type;

    /**
     * Constructor
     * @param userId integer
     * @param userName string
     * @param password string
     * @param type type
     */
    public User(Integer userId, String userName, String password, UserType type) {
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.userId = userId;
    }

    /**
     * Getter
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Getter
     * @return id
     */
    public Integer getId(){
        return userId;
    }

    /**
     * Suprascriere equals
     * @param o obiect
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userName.equals(user.userName) && password.equals(user.password);
    }

    /**
     * Suprascriere hascode pe user si password
     * @return hascodeul
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }

    /**
     * Suprascriere toString
     * @return afisare user
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", type=" + type +
                '}';
    }

    /**
     * Getter
     * @return user type
     */
    public UserType getType(){
        return this.type;
    }
}
