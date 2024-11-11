
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.Comentario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class ComentarioJpaController implements Serializable {

    public ComentarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
public ComentarioJpaController(){
     this.emf = EntityManagerUtil.getEntityManagerFactory();
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comentario comentario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(comentario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comentario comentario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            comentario = em.merge(comentario);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = comentario.getIdComentario();
                if (findComentario(id) == null) {
                    throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentario comentario;
            try {
                comentario = em.getReference(Comentario.class, id);
                comentario.getIdComentario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.", enfe);
            }
            em.remove(comentario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comentario> findComentarioEntities() {
        return findComentarioEntities(true, -1, -1);
    }

    public List<Comentario> findComentarioEntities(int maxResults, int firstResult) {
        return findComentarioEntities(false, maxResults, firstResult);
    }

    private List<Comentario> findComentarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comentario.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Comentario findComentario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comentario.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comentario> rt = cq.from(Comentario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
