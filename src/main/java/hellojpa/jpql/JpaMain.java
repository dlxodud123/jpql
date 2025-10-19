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
			Team team = new Team();
			team.setName("teamA");
			em.persist(team);

			Member member = new Member();
			member.setUsername("teamA");
			member.setAge(10);
			member.changeTeam(team);
			em.persist(member);

			em.flush();
			em.clear();

			String query = "select avg(select m2.age from Member m2) as avg_age from Member";
			List<Member> result = em.createQuery(query, Member.class)
					.getResultList();

			System.out.println("result = " + result.size());

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
