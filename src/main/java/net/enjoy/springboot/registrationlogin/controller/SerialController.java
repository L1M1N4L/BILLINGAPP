package net.enjoy.springboot.registrationlogin.controller;

import net.enjoy.springboot.registrationlogin.entity.SerialData;
import net.enjoy.springboot.registrationlogin.service.SerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serial")
public class SerialController {

    @Autowired
    private SerialService serialService;

    @GetMapping("/data")
    public ResponseEntity<List<SerialData>> getAllSerialData() {
        List<SerialData> serialDataList = serialService.getAllSerialData();
        return ResponseEntity.ok(serialDataList);
    }

    @GetMapping("/data/{id}")
    public ResponseEntity<SerialData> getSerialDataById(@PathVariable Long id) {
        SerialData serialData = serialService.getSerialDataById(id);
        return ResponseEntity.ok(serialData);
    }

    @PostMapping("/data")
    public ResponseEntity<SerialData> saveSerialData(@RequestBody SerialData serialData) {
        SerialData savedSerialData = serialService.saveSerialData(serialData);
        return ResponseEntity.ok(savedSerialData);
    }

    @DeleteMapping("/data/{id}")
    public ResponseEntity<Void> deleteSerialData(@PathVariable Long id) {
        serialService.deleteSerialData(id);
        return ResponseEntity.noContent().build();
    }
}
