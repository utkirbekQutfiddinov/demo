package uz.utkirbek.storage;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class StorageBean {
    private final Map<String, List> storage;

    public StorageBean(Map<String, List> storage) {
        this.storage = storage;
    }

    public List getList(String key){
        return storage.getOrDefault(key,new ArrayList());
    }

    public void setList(String key, List value){
        storage.put(key,value);
    }
}
