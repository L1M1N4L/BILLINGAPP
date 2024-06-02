// package net.enjoy.springboot.registrationlogin.service;

// import net.enjoy.springboot.registrationlogin.entity.Settings;
// import net.enjoy.springboot.registrationlogin.repository.SettingsRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.Optional;

// @Service
// public class SettingsService {

//     @Autowired
//     private SettingsRepository settingsRepository;

//     public Optional<Settings> getSettings() {
//         return settingsRepository.findById(1L);
//     }

//     public void saveSettings(Settings settings) {
//         settings.setId(1L); // Ensure only one settings record
//         settingsRepository.save(settings);
//     }
// }
