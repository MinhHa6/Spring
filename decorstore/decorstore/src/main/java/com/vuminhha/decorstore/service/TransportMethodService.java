package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.TransportMethod;
import com.vuminhha.decorstore.repository.TransportMethodRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportMethodService {
    private final TransportMethodRepository transportMethodRepository;
    public TransportMethodService(TransportMethodRepository transportMethodRepository)
    {
        this.transportMethodRepository=transportMethodRepository;
    }
    // lay tat ca phuong thuc van chuyen
    public List<TransportMethod> getAll ()
    {
        return transportMethodRepository.findAll();
    }
    // lay phuong thuc van chuyen theo Id
    public TransportMethod getById(Long id)
    {
        return transportMethodRepository.findById(id).orElseThrow(()->new RuntimeException("Transport method not found with Id:"+id));
    }
    // them va update phuong thuc van chuyen
    public TransportMethod saveTransportMethod(TransportMethod transportMethod)
    {
        return transportMethodRepository.save(transportMethod);
    }
    // xoa theo  Id
    public void deleteTransportMethod(Long id)
    {
        if(transportMethodRepository.existsById(id))
        {
            throw  new RuntimeException("Transport method not found with id:"+id);
        }
        transportMethodRepository.deleteById(id);
    }
    // tim kiem phuong thuc van chuyen  theo ten
    public List<TransportMethod> searchByName(String keyword)
    {
        return transportMethodRepository.findByNameContainingIgnoreCase(keyword);
    }
}
