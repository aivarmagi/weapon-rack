package ee.aivar.test;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import javax.inject.Inject;

import ee.aivar.model.WeaponRack;
import ee.aivar.service.WeaponRackRegistration;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import ee.aivar.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class WeaponRackRegistrationTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(WeaponRack.class, WeaponRackRegistration.class, Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    WeaponRackRegistration weaponRackRegistration;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
        WeaponRack newWeaponRack = new WeaponRack();
        newWeaponRack.setName("Double Barrel Shotgun");
        newWeaponRack.setId("1234-5678-90");
        newWeaponRack.setCapacity(2);
        weaponRackRegistration.register(newWeaponRack);
        assertNotNull(newWeaponRack.getId());
        log.info(newWeaponRack.getName() + " was persisted with id " + newWeaponRack.getId());
    }

}
