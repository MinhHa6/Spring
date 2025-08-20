package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.Configurations;
import com.vuminhha.decorstore.repository.ConfigurationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationsService {
    private ConfigurationsRepository configurationsRepository;
    // lay tat ca cau hinh
    public List<Configurations> getAll()
    {
        return configurationsRepository.findAll();
    }
    // lay cau hinh theo id
    public Configurations getConfigurationsId(Long id)
    {
        return configurationsRepository.findById(id).orElseThrow(()->new RuntimeException("No configurations"));
    }
    public Configurations saveConfigurations(Configurations configurations)
    {
        return configurationsRepository.save(configurations);
    }
    //xoa cau hinh
    public void deleteConfigurations(Long id)
    {
        configurationsRepository.deleteById(id);
    }
}
