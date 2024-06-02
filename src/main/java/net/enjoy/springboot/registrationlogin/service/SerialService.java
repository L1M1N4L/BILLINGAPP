package net.enjoy.springboot.registrationlogin.service;

import net.enjoy.springboot.registrationlogin.entity.SerialData;

import java.util.List;

public interface SerialService {
    List<SerialData> getAllSerialData();
    SerialData getSerialDataById(Long id);
    SerialData saveSerialData(SerialData serialData);
    void deleteSerialData(Long id);
}
