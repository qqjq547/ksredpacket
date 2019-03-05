package com.guochuang.mimedia.mvp.model;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class PhoneEntity {

    @Id
    public Long id;

    public String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
