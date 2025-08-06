package devmaster_lesson91.service;

import devmaster_lesson91.entity.Configuration;
import devmaster_lesson91.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationService {
    @Autowired
    private ConfigurationRepository configurationRepository;
    public List<Configuration> getAll()
    {
        return configurationRepository.findAll();
    }
    public Configuration saveConfig(Configuration configuration)
    {
        return configurationRepository.save(configuration);
    }
    public Configuration getConfigById(Long id)
    {
        return configurationRepository.findById(id).orElse(null);
    }
    public void deleteConfig(Long id)
    {
        configurationRepository.deleteById(id);
    }
}
