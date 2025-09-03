package com.vuminhha.decorstore.service;

import com.vuminhha.decorstore.entity.TransportMethod;
import com.vuminhha.decorstore.repository.TransportMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportMethodService {
    @Autowired
    private TransportMethodRepository transportMethodRepository;
    // lay tat ca phuong thuc van chuyen
    public List<TransportMethod> getAll ()
    {
        return transportMethodRepository.findAll();
    }
    // lay theo di a chi id
    public TransportMethod getById(Long id)
    {
        return transportMethodRepository.findById(id).orElseThrow(()->new RuntimeException("null"));
    }
    // luu thong tin update
    public TransportMethod saveTransportMethod(TransportMethod transportMethod)
    {
        return transportMethodRepository.save(transportMethod);
    }
    // xoa theo dia chi Id
    public void deleteTransportMethod(Long id)
    {
        transportMethodRepository.deleteById(id);
    }
}
