package hellojpa.jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
	public static void main(String[] args) {

		// entityManagerFactory
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		// entityManager
		EntityManager em = emf.createEntityManager();
		// transaction
		EntityTransaction tx = em.getTransaction();

		tx.begin();

		try{
			Member member = new Member();
			member.setUsername("member1");
			member.setAge(20);

			em.persist(member);
			Member result = em.createQuery("select m from Member m where m.username = :username", Member.class)
					.setParameter("username", "member1")
					.getSingleResult();

			System.out.println("singleResult : " + result.getUsername());

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
