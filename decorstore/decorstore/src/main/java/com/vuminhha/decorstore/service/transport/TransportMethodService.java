package com.vuminhha.decorstore.service.transport;

import com.vuminhha.decorstore.entity.TransportMethod;
import java.util.List;

public interface TransportMethodService  {
    List<TransportMethod> getAll ();
    TransportMethod getById(Long id);
    TransportMethod saveTransportMethod(TransportMethod transportMethod);
    void deleteTransportMethod(Long id);
    public List<TransportMethod> searchByName(String keyword);

}
