package uz.utkirbek.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.utkirbek.model.Trainee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StorageBean {
    @Autowired
    Map<String, List> storage;

    public List getList(String key){
        return storage.getOrDefault(key,new ArrayList());
    }

    public void setList(String key, List value){
        storage.put(key,value);
    }
}
