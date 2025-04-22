package utils;

import de.daycu.passik.model.auth.MasterId;
import de.daycu.passik.model.vault.Credentials;
import de.daycu.passik.model.vault.DigitalAccount;
import de.daycu.passik.model.vault.DigitalServiceName;
import port.out.persistance.DigitalAccountRepository;
import service.vault.exception.DigitalAccountNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDigitalAccountRepository implements DigitalAccountRepository {

    private final Map<MasterId, Map<DigitalServiceName, Credentials>> digitalAccounts = new ConcurrentHashMap<>();

    @Override
    public DigitalAccount save(DigitalAccount digitalAccount) {
        digitalAccounts
                .computeIfAbsent(digitalAccount.masterId(), k -> new ConcurrentHashMap<>())
                .put(digitalAccount.digitalServiceName(), digitalAccount.credentials());

        return digitalAccount;
    }

    @Override
    public void delete(DigitalAccount digitalAccount) {
        Map<DigitalServiceName, Credentials> account = digitalAccounts.get(digitalAccount.masterId());
        if (account != null)
            account.remove(digitalAccount.digitalServiceName());
    }

    @Override
    public DigitalAccount update(DigitalAccount updatedDigitalAccount) {

        Map<DigitalServiceName, Credentials> account = digitalAccounts.get(updatedDigitalAccount.masterId());

        if (account == null || !account.containsKey(updatedDigitalAccount.digitalServiceName()))
            throw new DigitalAccountNotFoundException(
                    "Cannot update: Digital account for service: %s".formatted(account));

        account.put(updatedDigitalAccount.digitalServiceName(), updatedDigitalAccount.credentials());

        return updatedDigitalAccount;
    }

    @Override
    public List<DigitalAccount> findAllByMasterId(MasterId masterId) {
        Map<DigitalServiceName, Credentials> accounts = digitalAccounts.get(masterId);

        List<DigitalAccount> digitalAccountList = new ArrayList<>();
        accounts.forEach((serviceName, credentials) ->
                digitalAccountList.add(new DigitalAccount(masterId, serviceName, credentials)));

        return digitalAccountList;
    }

    @Override
    public DigitalAccount findByDigitalServiceName(MasterId masterId, DigitalServiceName digitalServiceName) {
        Map<DigitalServiceName, Credentials> service = digitalAccounts.get(masterId);

        if (service == null || !service.containsKey(digitalServiceName))
            return null;

        Credentials credentials = service.get(digitalServiceName);

        return new DigitalAccount(masterId, digitalServiceName, credentials);
    }

}
