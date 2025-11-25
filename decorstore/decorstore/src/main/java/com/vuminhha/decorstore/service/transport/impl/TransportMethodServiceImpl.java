package com.vuminhha.decorstore.service.transport.impl;

import com.vuminhha.decorstore.config.exception.ResourceNotFoundException;
import com.vuminhha.decorstore.entity.TransportMethod;
import com.vuminhha.decorstore.repository.TransportMethodRepository;
import com.vuminhha.decorstore.service.transport.TransportMethodService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TransportMethodServiceImpl implements TransportMethodService {
     TransportMethodRepository transportMethodRepository;
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
