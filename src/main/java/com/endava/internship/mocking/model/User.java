package com.endava.internship.mocking.model;

import java.util.List;
import java.util.Objects;

public class User {

    private Integer id;
    private String name;
    private Status status;
    private List<Privilege> privileges;

    public User(Integer id, String name, Status status, List<Privilege> privileges) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.privileges = privileges;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) &&
            Objects.equals(name, user.name) &&
            status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
