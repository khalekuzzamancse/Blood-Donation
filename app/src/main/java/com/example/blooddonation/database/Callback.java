package com.example.blooddonation.database;

import com.example.blooddonation.ui.datatypes.DomainUserInfo;

import java.util.List;

public interface Callback {
    void receivedList(List<DomainUserInfo> list);
}
