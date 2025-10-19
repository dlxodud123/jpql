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

			String query = "select m.username, 'HELLO', TRUE from Member m";
			List<Object[]> result = em.createQuery(query)
					.getResultList();

			for (Object[] objects : result){
				System.out.println("objects = " + objects[0]);
				System.out.println("objects = " + objects[1]);
				System.out.println("objects = " + objects[2]);
			}

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
