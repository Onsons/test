package com.test.demo.services;

import com.test.demo.entities.User;

import java.util.List;

public interface IExcelDataService {

    List<User> getExcelDataAsList();
    int saveExcelData(List<User> invoices);
}
