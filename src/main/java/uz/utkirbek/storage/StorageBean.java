package uz.utkirbek.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import uz.utkirbek.enums.Table;
import uz.utkirbek.model.BaseIdBean;
import uz.utkirbek.model.User;
import uz.utkirbek.service.ServiceFacade;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:application.properties")
public class StorageBean implements InitializingBean {
    static final Logger LOG = LoggerFactory.getLogger(StorageBean.class);

    private Map<Table, List<BaseIdBean>> storage;

    private ServiceFacade serviceFacade;

    @Value("${path}")
    private String filePath;

    @Autowired
    public void setStorage(Map<Table, List<BaseIdBean>> storage) {
        this.storage = storage;
    }

    @Autowired
    public void setServiceFacade(@Lazy ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    public List<BaseIdBean> getList(Table key) {
        return storage.getOrDefault(key, new ArrayList<>());
    }

    public void setList(Table key, List<BaseIdBean> value) {
        storage.put(key, value);
    }

    @Override
    public void afterPropertiesSet() {
        Gson gson = new Gson();
        Type type = new TypeToken<List<User>>() {
        }.getType();

        try {
            List<User> users = gson.fromJson(new FileReader(filePath), type);

            for (User user : users) {
                serviceFacade.addUser(user);
            }

        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        }
    }
}
