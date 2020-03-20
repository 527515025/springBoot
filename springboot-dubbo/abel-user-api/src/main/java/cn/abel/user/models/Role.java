package cn.abel.user.models;


import java.io.Serializable;

public class Role implements Serializable {

    private static final long serialVersionUID = -4873217276773065821L;
    private Integer id;
    private String name;
    private Integer roleLevel;
    private String description;

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

    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                ", roleLevel=" + roleLevel +
                ", description=" + description +
                '}';
    }
}