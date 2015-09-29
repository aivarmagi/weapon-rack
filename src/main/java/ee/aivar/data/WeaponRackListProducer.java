package ee.aivar.data;

import ee.aivar.model.WeaponRack;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class WeaponRackListProducer {

    @Inject
    private WeaponRackRepository weaponRackRepository;

    private List<WeaponRack> weaponRacks;

    @Produces
    @Named
    public List<WeaponRack> getWeaponRacks() {
        return weaponRacks;
    }

    public void onWeaponRackListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final WeaponRack weaponRack) {
        retrieveAllWeaponRacksOrderedByName();
    }

    @PostConstruct
    public void retrieveAllWeaponRacksOrderedByName() {
        weaponRacks = weaponRackRepository.findAllOrderedByName();
    }
}
