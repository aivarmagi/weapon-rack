package ee.aivar.service;

import ee.aivar.model.Weapon;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class WeaponRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Weapon> weaponEventSrc;

    public void register(Weapon weapon) throws Exception {
        log.info("Registering " + weapon.getName());
        em.persist(weapon);
        weaponEventSrc.fire(weapon);
    }
}
