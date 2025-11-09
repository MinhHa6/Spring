package com.vuminhha.decorstore.service.configurations;


import com.vuminhha.decorstore.entity.Configuration;

import java.util.List;

public interface ConfigurationsService  {
    List<Configuration> getAll();
    Configuration getConfigurationId(Long id);
    Configuration saveConfiguration(Configuration configuration);
    void deleteConfiguration(Long id);
}
