
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.HistorialEfectividadAnuncios;
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
public class HistorialEfectividadAnunciosJpaController implements Serializable {

    public HistorialEfectividadAnunciosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

        /**
     * Constructor sin parámetros que inicializa la fábrica de gestores de entidad usando una utilidad.
     */
    public HistorialEfectividadAnunciosJpaController() {
        this.emf = EntityManagerUtil.getEntityManagerFactory();
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistorialEfectividadAnuncios historialEfectividadAnuncios) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(historialEfectividadAnuncios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistorialEfectividadAnuncios historialEfectividadAnuncios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            historialEfectividadAnuncios = em.merge(historialEfectividadAnuncios);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = historialEfectividadAnuncios.getId();
                if (findHistorialEfectividadAnuncios(id) == null) {
                    throw new NonexistentEntityException("The historialEfectividadAnuncios with id " + id + " no longer exists.");
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
            HistorialEfectividadAnuncios historialEfectividadAnuncios;
            try {
                historialEfectividadAnuncios = em.getReference(HistorialEfectividadAnuncios.class, id);
                historialEfectividadAnuncios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialEfectividadAnuncios with id " + id + " no longer exists.", enfe);
            }
            em.remove(historialEfectividadAnuncios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistorialEfectividadAnuncios> findHistorialEfectividadAnunciosEntities() {
        return findHistorialEfectividadAnunciosEntities(true, -1, -1);
    }

    public List<HistorialEfectividadAnuncios> findHistorialEfectividadAnunciosEntities(int maxResults, int firstResult) {
        return findHistorialEfectividadAnunciosEntities(false, maxResults, firstResult);
    }

    private List<HistorialEfectividadAnuncios> findHistorialEfectividadAnunciosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistorialEfectividadAnuncios.class));
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

    public HistorialEfectividadAnuncios findHistorialEfectividadAnuncios(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistorialEfectividadAnuncios.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialEfectividadAnunciosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistorialEfectividadAnuncios> rt = cq.from(HistorialEfectividadAnuncios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
