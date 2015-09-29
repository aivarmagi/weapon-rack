package ee.aivar.data;

import ee.aivar.model.Weapon;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class WeaponListProducer {

    @Inject
    private WeaponRepository weaponRepository;

    private List<Weapon> weapons;

    @Produces
    @Named
    public List<Weapon> getWeapons() {
        return weapons;
    }

    public void onWeaponListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Weapon weapon) {
        retrieveAllWeaponsOrderedByName();
    }

    @PostConstruct
    public void retrieveAllWeaponsOrderedByName() {
        weapons = weaponRepository.findAllOrderedByName();
    }
}
