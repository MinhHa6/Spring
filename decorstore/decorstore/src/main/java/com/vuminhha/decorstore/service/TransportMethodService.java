package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.TransportMethod;
import com.vuminhha.decorstore.repository.TransportMethodRepository;

import com.vuminhha.decorstore.service.impl.TransportMethodServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.vuminhha.decorstore.config.exception.ResourceNotFoundException;
import java.util.List;

@Service
public class TransportMethodService implements TransportMethodServiceImpl {
    private final TransportMethodRepository transportMethodRepository;
    public TransportMethodService(TransportMethodRepository transportMethodRepository)
    {
        this.transportMethodRepository=transportMethodRepository;
    }
    @Override
    public List<TransportMethod> getAll ()
    {
        return transportMethodRepository.findAll();
    }
    @Override
    public TransportMethod getById(Long id)
    {
        return transportMethodRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Transport method not found with id: " + id));
    }
    @Override
    public TransportMethod saveTransportMethod(TransportMethod transportMethod)
    {
        return transportMethodRepository.save(transportMethod);
    }
    @Override
    public void deleteTransportMethod(Long id)
    {
        if(transportMethodRepository.existsById(id))
        {
            throw new   ResourceNotFoundException("Transport method not found with id: " + id);
        }
        transportMethodRepository.deleteById(id);
    }
    @Override
    public List<TransportMethod> searchByName(String keyword)
    {
        return transportMethodRepository.findByNameContainingIgnoreCase(keyword);
    }
}
