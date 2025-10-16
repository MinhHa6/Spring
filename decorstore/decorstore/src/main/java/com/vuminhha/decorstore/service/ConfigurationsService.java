package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Configuration;
import com.vuminhha.decorstore.repository.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.annotation.Configurations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationsService {
    // service cau hinh
    private final ConfigurationRepository configurationRepository;
    public ConfigurationsService (ConfigurationRepository configurationRepository)
    {
        this.configurationRepository=configurationRepository;
    }
    // lay tat ca cau hinh
    public List<Configuration> getAll()
    {
        return configurationRepository.findAll();
    }
    // lay cau hinh theo ID
    public Configuration getConfigurationId(Long id)
    {
        return configurationRepository.findById(id).orElseThrow(()->new RuntimeException("No configurations"));
    }
    //Them hoac cap nhat cau hinh
    public Configuration saveConfiguration(Configuration configuration)
    {
        return configurationRepository.save(configuration);
    }
    //xoa cau hinh
    public void deleteConfiguration(Long id)
    {
        configurationRepository.deleteById(id);
    }
}
