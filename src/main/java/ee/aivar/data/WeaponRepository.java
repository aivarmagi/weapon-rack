package ee.aivar.data;

import ee.aivar.model.Weapon;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class WeaponRepository {

    @Inject
    private EntityManager em;

    public Weapon findById(String id) {
        return em.find(Weapon.class, id);
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

    public List<Weapon> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Weapon> criteria = cb.createQuery(Weapon.class);
        Root<Weapon> weapon = criteria.from(Weapon.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(weapon).orderBy(cb.asc(weapon.get("name")));
        return em.createQuery(criteria).getResultList();
    }
}
