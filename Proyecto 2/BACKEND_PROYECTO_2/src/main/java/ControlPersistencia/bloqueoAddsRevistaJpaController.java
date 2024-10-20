/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import ControlPersistencia.exceptions.PreexistingEntityException;
import JPA.bloqueoAddsRevista;
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
public class bloqueoAddsRevistaJpaController implements Serializable {

    public bloqueoAddsRevistaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public bloqueoAddsRevistaJpaController(){
     this.emf = EntityManagerUtil.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(bloqueoAddsRevista bloqueoAddsRevista) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(bloqueoAddsRevista);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findbloqueoAddsRevista(bloqueoAddsRevista.getIdRevista()) != null) {
                throw new PreexistingEntityException("bloqueoAddsRevista " + bloqueoAddsRevista + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(bloqueoAddsRevista bloqueoAddsRevista) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            bloqueoAddsRevista = em.merge(bloqueoAddsRevista);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = bloqueoAddsRevista.getIdRevista();
                if (findbloqueoAddsRevista(id) == null) {
                    throw new NonexistentEntityException("The bloqueoAddsRevista with id " + id + " no longer exists.");
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
            bloqueoAddsRevista bloqueoAddsRevista;
            try {
                bloqueoAddsRevista = em.getReference(bloqueoAddsRevista.class, id);
                bloqueoAddsRevista.getIdRevista();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bloqueoAddsRevista with id " + id + " no longer exists.", enfe);
            }
            em.remove(bloqueoAddsRevista);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<bloqueoAddsRevista> findbloqueoAddsRevistaEntities() {
        return findbloqueoAddsRevistaEntities(true, -1, -1);
    }

    public List<bloqueoAddsRevista> findbloqueoAddsRevistaEntities(int maxResults, int firstResult) {
        return findbloqueoAddsRevistaEntities(false, maxResults, firstResult);
    }

    private List<bloqueoAddsRevista> findbloqueoAddsRevistaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(bloqueoAddsRevista.class));
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

    public bloqueoAddsRevista findbloqueoAddsRevista(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(bloqueoAddsRevista.class, id);
        } finally {
            em.close();
        }
    }

    public int getbloqueoAddsRevistaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<bloqueoAddsRevista> rt = cq.from(bloqueoAddsRevista.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
