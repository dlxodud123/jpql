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
			member.setAge(10);
			em.persist(member);

			em.flush();
			em.clear();

			List<MemberDTO> result = em.createQuery("select new hellojpa.jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
					.getResultList();

			System.out.println("membeDTO = " + result.get(0).getUsername());
			System.out.println("membeDTO = " + result.get(0).getAge());

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();
	}
}
