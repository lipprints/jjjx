package com.jjjx.function.entity;

import java.io.Serializable;

/**
 * 省市区实体
 *
 * @author xz
 */
public class CityEntity implements Serializable {
    private int id;
    /**
     * 城市名
     */
    private String name;
    /**
     * 城市首字母
     */
    private String initials;
    /**
     * 上级ID
     */
    private int parentId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }


    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
