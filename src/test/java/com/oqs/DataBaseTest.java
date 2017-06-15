package com.oqs;

import static org.assertj.core.api.Assertions.assertThat;

import com.oqs.crud.*;
import com.oqs.model.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DataBaseTest {
    @Autowired
    MasterDAO masterDAO;
    @Autowired
    ServiceDAO serviceDAO;
    @Autowired
    TypeDAO typeDAO;
    @Autowired
    UserDAO userDAO;

    @Test
    public void shouldFindTypes() {
        Collection<Type> types = typeDAO.getTypeList();
        assertThat(types.size()).isEqualTo(3);
        assertThat(typeDAO.get(2).getName()).isEqualTo("Beauty salon");
        assertThat(typeDAO.get(3).getName()).isEqualTo("Service station");
    }

    @Ignore
    @Test
    @Rollback(false)
    public void shouldInsertType() {
        Collection<Type> types = typeDAO.getTypeList();
        int found = types.size();

        Type type = new Type();
        type.setName("Hospital");
        typeDAO.add(type);

        types = typeDAO.getTypeList();
        assertThat(types.size()).isEqualTo(found + 1);
    }

    @Ignore
    @Test
    @Rollback(false)
    public void shouldUpdateType() {
        Type type = typeDAO.get(2);
        String oldType = type.getName();
        String newType = oldType + "1";

        type.setName(newType);
        typeDAO.update(type);

        type = typeDAO.get(2);
        assertThat(type.getName()).isEqualTo(newType);
    }

    @Ignore
    @Test
    @Rollback(false)
    public void removeTypeTest(){
        Collection<Type> types = typeDAO.getTypeList();
        int found = types.size();

        typeDAO.delete(22);

        types = typeDAO.getTypeList();
        assertThat(types.size()).isEqualTo(found - 1);
    }

    @Test
    public void shouldFindServices() {
        assertThat(serviceDAO.get(1).getName()).isEqualTo("Haircut");
        assertThat(serviceDAO.get(2).getName()).isEqualTo("Manicure");
        assertThat(serviceDAO.get(3).getName()).isEqualTo("Pedicure");
    }

    @Ignore
    @Test
    @Rollback(false)
    public void shouldInsertService() {
        Collection<Service> services = serviceDAO.getServiceList();
        int found = services.size();

        Service service = new Service();
        service.setName("new_service");
        service.setType(typeDAO.get(2));
        serviceDAO.add(service);

        services = serviceDAO.getServiceList();
        assertThat(services.size()).isEqualTo(found + 1);
    }

    @Ignore
    @Test
    @Rollback(false)
    public void shouldUpdateService() {
        Service service = serviceDAO.get(4);
        String oldService = service.getName();
        String newService = oldService + "kkk";

        service.setName(newService);
        serviceDAO.update(service);

        service = serviceDAO.get(4);
        assertThat(service.getName()).isEqualTo(newService);
    }

    @Ignore
    @Test
    @Rollback(false)
    public void removeServiceTest(){
        Collection<Service> services = serviceDAO.getServiceList();
        int found = services.size();

        serviceDAO.delete(31);

        services = serviceDAO.getServiceList();
        assertThat(services.size()).isEqualTo(found - 1);
    }

    @Test
    public void shouldFindMasters() {
        assertThat(masterDAO.get(1).getName()).isEqualTo("Ivanova Anna");
        assertThat(masterDAO.get(2).getName()).isEqualTo("Zubko Nina");
        assertThat(masterDAO.get(6).getName()).isEqualTo("Popov Oleh");
    }

    @Ignore
    @Test
    @Rollback(false)
    public void shouldInsertMaster() {
        Collection<Master> masters = masterDAO.getMasterList();
        int found = masters.size();

        Master master = new Master();
        master.setName("Maksim Doshi");
        master.setUser(userDAO.get(6));
        masterDAO.add(master);

        masters = masterDAO.getMasterList();
        assertThat(masters.size()).isEqualTo(found + 1);
    }

    @Ignore
    @Test
    @Rollback(false)
    public void shouldUpdateMaster() {
        Master master = masterDAO.get(9);
        String oldMaster = master.getName();
        String newMaster = oldMaster + "qweqweqwe";

        master.setName(newMaster);
        masterDAO.update(master);

        master = masterDAO.get(9);
        assertThat(master.getName()).isEqualTo(newMaster);
    }

    @Ignore
    @Test
    @Rollback(false)
    public void removeMasterTest(){
        Collection<Master> masters = masterDAO.getMasterList();
        int found = masters.size();

        masterDAO.delete(10);

        masters = masterDAO.getMasterList();
        assertThat(masters.size()).isEqualTo(found - 1);
    }

    @Test
    public void shouldFindServiceMaster() {
        Service service = serviceDAO.get(1);
        Set<Master> masterSet = service.getMasters();
        assertThat(masterSet.size()).isEqualTo(2);
        service = serviceDAO.get(2);
        masterSet = service.getMasters();
        assertThat(masterSet.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindMasterService() {
        Master master = masterDAO.get(1);
        Set<Service> serviceSet = master.getServices();
        assertThat(serviceSet.size()).isEqualTo(3);
        master = masterDAO.get(2);
        serviceSet = master.getServices();
        assertThat(serviceSet.size()).isEqualTo(1);
    }

    @Test
    public void shouldFindAdmin() {
        User user = userDAO.get(1);
        assertThat(user.getFirstname()).isEqualTo("admin");
        assertThat(user.getLastname()).isEqualTo("admin");
        assertThat(user.getEmail()).isEqualTo("admin");
        assertThat(user.getRole()).isEqualTo("ROLE_ADMIN");
    }

}
