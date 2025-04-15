package utils;

import de.daycu.passik.model.auth.Master;
import de.daycu.passik.model.auth.MasterId;
import de.daycu.passik.model.auth.MasterLogin;
import de.daycu.passik.model.auth.MasterPassword;
import port.out.persistance.MasterRepository;
import service.vault.exception.DuplicateAccountException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMasterRepository implements MasterRepository {

    private final Map<MasterLogin, Master> masters = new ConcurrentHashMap<>();

    @Override
    public Master register(MasterLogin masterLogin, MasterPassword masterPassword) {
        if (masters.containsKey(masterLogin)) throw new DuplicateAccountException("Login already taken: " + masterLogin);

        Master master = new Master(new MasterId(UUID.randomUUID()), masterLogin, masterPassword);
        masters.put(masterLogin, master);

        return master;
    }

    @Override
    public Master getMasterByLogin(MasterLogin masterLogin) {
        return masters.get(masterLogin);
    }

    @Override
    public void delete(Master master) {
        if (masters.get(master.masterLogin()) != null)
            masters.remove(master.masterLogin());
    }

    @Override
    public List<Master> findAll() {
        List<Master> listOfMasters = new ArrayList<>();
        masters.forEach((masterLogin, master) -> {
            listOfMasters.add(master);
        });

        return listOfMasters;
    }
}
