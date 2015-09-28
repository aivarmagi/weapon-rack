package ee.aivar.data;

import ee.aivar.model.WeaponRack;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class WeaponRackRepository {

    @Inject
    private EntityManager em;

    public WeaponRack findById(String id) {
        return em.find(WeaponRack.class, id);
    }

//    public Member findByEmail(String email) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
//        Root<Member> member = criteria.from(Member.class);
//        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
//        // feature in JPA 2.0
//        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
//        criteria.select(member).where(cb.equal(member.get("email"), email));
//        return em.createQuery(criteria).getSingleResult();
//    }

    public List<WeaponRack> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<WeaponRack> criteria = cb.createQuery(WeaponRack.class);
        Root<WeaponRack> weaponRack = criteria.from(WeaponRack.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(weaponRack).orderBy(cb.asc(weaponRack.get("name")));
        return em.createQuery(criteria).getResultList();
    }
}
