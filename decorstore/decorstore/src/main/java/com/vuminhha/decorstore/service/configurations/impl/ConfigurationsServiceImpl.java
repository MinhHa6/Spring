package com.vuminhha.decorstore.service.configurations.impl;

import com.vuminhha.decorstore.entity.Configuration;
import com.vuminhha.decorstore.repository.ConfigurationRepository;
import com.vuminhha.decorstore.service.configurations.ConfigurationsService;

import java.util.List;

public class ConfigurationsServiceImpl implements ConfigurationsService {
    private final ConfigurationRepository configurationRepository;
    public ConfigurationsServiceImpl (ConfigurationRepository configurationRepository)
    {
        this.configurationRepository=configurationRepository;
    }
    // lay tat ca cau hinh
    @Override
    public List<Configuration> getAll()
    {
        return configurationRepository.findAll();
    }
    // lay cau hinh theo ID
    @Override
    public Configuration getConfigurationId(Long id)
    {
        return configurationRepository.findById(id).orElseThrow(()->new RuntimeException("No configurations"));
    }
    //Them hoac cap nhat cau hinh
    @Override
    public Configuration saveConfiguration(Configuration configuration)
    {
        return configurationRepository.save(configuration);
    }
    //xoa cau hinh
    @Override
    public void deleteConfiguration(Long id)
    {
        configurationRepository.deleteById(id);
    }
}
