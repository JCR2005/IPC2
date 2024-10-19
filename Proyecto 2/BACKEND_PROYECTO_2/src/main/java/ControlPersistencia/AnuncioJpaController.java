package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.Anuncio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;
import java.util.List;

public class AnuncioJpaController implements Serializable {

    private EntityManagerFactory emf = null;

    public AnuncioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public AnuncioJpaController() {
        this.emf = EntityManagerUtil.getEntityManagerFactory();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Anuncio anuncio) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(anuncio);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Asegúrate de hacer rollback en caso de error
            }
            e.printStackTrace();
        } finally {
            em.close(); // Asegura que el EntityManager se cierre
        }
    }

    public void edit(Anuncio anuncio) throws NonexistentEntityException, Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            anuncio = em.merge(anuncio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback si hay un error
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.isEmpty()) {
                String id = anuncio.getIdAnuncio();
                if (findAnuncio(id) == null) {
                    throw new NonexistentEntityException("The anuncio with id " + id + " no longer exists.");
                }
            }
            throw ex; // Vuelve a lanzar la excepción para manejarla en un nivel superior
        } finally {
            em.close(); // Asegura que el EntityManager se cierre
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Anuncio anuncio;
            try {
                anuncio = em.getReference(Anuncio.class, id);
                anuncio.getIdAnuncio(); // Este método puede lanzar EntityNotFoundException
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anuncio with id " + id + " no longer exists.", enfe);
            }
            em.remove(anuncio);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Rollback si hay un error
            }
            e.printStackTrace();
        } finally {
            em.close(); // Asegura que el EntityManager se cierre
        }
    }

    public List<Anuncio> findAnuncioEntities() {
        return findAnuncioEntities(true, -1, -1);
    }

    public List<Anuncio> findAnuncioEntities(int maxResults, int firstResult) {
        return findAnuncioEntities(false, maxResults, firstResult);
    }

    private List<Anuncio> findAnuncioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Anuncio> cq = em.getCriteriaBuilder().createQuery(Anuncio.class);
            cq.select(cq.from(Anuncio.class));
            TypedQuery<Anuncio> q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close(); // Asegura que el EntityManager se cierre
        }
    }

    public Anuncio findAnuncio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anuncio.class, id);
        } finally {
            em.close(); // Asegura que el EntityManager se cierre
        }
    }

    public int getAnuncioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Anuncio> rt = cq.from(Anuncio.class);
            cq.select(cb.count(rt));
            TypedQuery<Long> query = em.createQuery(cq);
            return query.getSingleResult().intValue(); // Devuelve el resultado como int
        } catch (Exception e) {
            e.printStackTrace(); // Para depurar el error
            return 0; // Retorna 0 en caso de error
        } finally {
            em.close(); // Asegura que el EntityManager se cierre
        }
    }
}
