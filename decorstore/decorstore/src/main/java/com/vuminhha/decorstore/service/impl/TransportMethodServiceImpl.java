package com.vuminhha.decorstore.service.impl;

import com.vuminhha.decorstore.entity.TransportMethod;

import java.util.List;

public interface TransportMethodServiceImpl {
    List<TransportMethod> getAll ();
    TransportMethod getById(Long id);
    TransportMethod saveTransportMethod(TransportMethod transportMethod);
    void deleteTransportMethod(Long id);
    public List<TransportMethod> searchByName(String keyword);
}
