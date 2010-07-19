package com.example.ncontainr;

public interface Customer {

    Logger getLogger();

    String getName();

    String getPhone();

    void sayMyName();

    void setName(String name);

    void setPhone(String phone);

    String getMyName();

    Account getAccount();
    
}
