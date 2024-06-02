// package net.enjoy.springboot.registrationlogin.entity;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;

// @Entity
// public class Settings {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     private int baudRate;
//     private int dataBits;
//     private int stopBits;
//     private int parity;
//     private int comPort; // Adding COM port field

//     // Default constructor with default values
//     public Settings() {
//         this.baudRate = 9600;
//         this.dataBits = 8;
//         this.stopBits = 1;
//         this.parity = 0;
//         this.comPort = 4; // Default COM port value
//     }

//     // Constructor with all parameters
//     public Settings(int baudRate, int dataBits, int stopBits, int parity, int comPort) {
//         this.baudRate = baudRate;
//         this.dataBits = dataBits;
//         this.stopBits = stopBits;
//         this.parity = parity;
//         this.comPort = comPort;
//     }

//     // Getters and setters for all fields
//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public int getBaudRate() {
//         return baudRate;
//     }

//     public void setBaudRate(int baudRate) {
//         this.baudRate = baudRate;
//     }

//     public int getDataBits() {
//         return dataBits;
//     }

//     public void setDataBits(int dataBits) {
//         this.dataBits = dataBits;
//     }

//     public int getStopBits() {
//         return stopBits;
//     }

//     public void setStopBits(int stopBits) {
//         this.stopBits = stopBits;
//     }

//     public int getParity() {
//         return parity;
//     }

//     public void setParity(int parity) {
//         this.parity = parity;
//     }

//     public int getComPort() {
//         return comPort;
//     }

//     public void setComPort(int comPort) {
//         this.comPort = comPort;
//     }
// }
