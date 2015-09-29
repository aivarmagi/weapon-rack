package ee.aivar.test;

import ee.aivar.model.Weapon;
import ee.aivar.service.WeaponRegistration;
import ee.aivar.util.Resources;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;

@RunWith(Arquillian.class)
public class WeaponRegistrationTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Weapon.class, WeaponRegistration.class, Resources.class)
                .addAsResource("META-INF/test-weapon-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-weapon-ds.xml");
    }

    @Inject
    WeaponRegistration weaponRegistration;

    @Inject
    Logger log;

    @Test
    public void testRegister() throws Exception {
        Weapon newWeapon = new Weapon();
        newWeapon.setName("Double Barrel Shotgun");
        newWeapon.setId("1234-5678-90");
        newWeapon.setWeapon_rack_id("11111-11111-1111-11");
		newWeapon.setWeight(1000);
		newWeapon.setCaliber("5.56");
//		newWeapon.setPut_time(); //TODO
        weaponRegistration.register(newWeapon);
        assertNotNull(newWeapon.getId());
        log.info(newWeapon.getName() + " was persisted with id " + newWeapon.getId());
    }

}
