
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import ControlPersistencia.exceptions.PreexistingEntityException;
import JPA.vigenciaAnuncio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author carlosrodriguez
 */
public class vigenciaAnuncioJpaController implements Serializable {

    public vigenciaAnuncioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
     public vigenciaAnuncioJpaController() {
     
       this.emf = EntityManagerUtil.getEntityManagerFactory();
    }
     
     public void initializeVigenciaAnuncio() throws Exception {
   
        int count = getvigenciaAnuncioCount();

        if (count == 0) {
          create(new vigenciaAnuncio("v_1", 1));
          create(new vigenciaAnuncio("v_2", 7));
          create(new vigenciaAnuncio("v_3", 14));
           create(new vigenciaAnuncio("v_4", 21));
        }
    }

    public void create(vigenciaAnuncio vigenciaAnuncio) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(vigenciaAnuncio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findvigenciaAnuncio(vigenciaAnuncio.getId_vigencia()) != null) {
                throw new PreexistingEntityException("vigenciaAnuncio " + vigenciaAnuncio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(vigenciaAnuncio vigenciaAnuncio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vigenciaAnuncio = em.merge(vigenciaAnuncio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = vigenciaAnuncio.getId_vigencia();
                if (findvigenciaAnuncio(id) == null) {
                    throw new NonexistentEntityException("The vigenciaAnuncio with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            vigenciaAnuncio vigenciaAnuncio;
            try {
                vigenciaAnuncio = em.getReference(vigenciaAnuncio.class, id);
                vigenciaAnuncio.getId_vigencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vigenciaAnuncio with id " + id + " no longer exists.", enfe);
            }
            em.remove(vigenciaAnuncio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<vigenciaAnuncio> findvigenciaAnuncioEntities() {
        return findvigenciaAnuncioEntities(true, -1, -1);
    }

    public List<vigenciaAnuncio> findvigenciaAnuncioEntities(int maxResults, int firstResult) {
        return findvigenciaAnuncioEntities(false, maxResults, firstResult);
    }

    private List<vigenciaAnuncio> findvigenciaAnuncioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(vigenciaAnuncio.class));
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

    public vigenciaAnuncio findvigenciaAnuncio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(vigenciaAnuncio.class, id);
        } finally {
            em.close();
        }
    }

    public int getvigenciaAnuncioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<vigenciaAnuncio> rt = cq.from(vigenciaAnuncio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
