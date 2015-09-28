package ee.aivar.service;

import ee.aivar.model.WeaponRack;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class WeaponRackRegistration {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<WeaponRack> weaponRackEventSrc;

    public void register(WeaponRack weaponRack) throws Exception {
        log.info("Registering " + weaponRack.getName());
        em.persist(weaponRack);
        weaponRackEventSrc.fire(weaponRack);
    }
}
