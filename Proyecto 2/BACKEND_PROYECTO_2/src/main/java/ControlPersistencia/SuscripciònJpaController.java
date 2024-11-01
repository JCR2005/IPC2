/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.Suscripciòn;
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
public class SuscripciònJpaController implements Serializable {

    public SuscripciònJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

      
    public   SuscripciònJpaController(){
       
        this.emf = EntityManagerUtil.getEntityManagerFactory();
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Suscripciòn suscripciòn) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(suscripciòn);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Suscripciòn suscripciòn) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            suscripciòn = em.merge(suscripciòn);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = suscripciòn.getIdSuscripcion();
                if (findSuscripciòn(id) == null) {
                    throw new NonexistentEntityException("The suscripci\u00f2n with id " + id + " no longer exists.");
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
            Suscripciòn suscripciòn;
            try {
                suscripciòn = em.getReference(Suscripciòn.class, id);
                suscripciòn.getIdSuscripcion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suscripci\u00f2n with id " + id + " no longer exists.", enfe);
            }
            em.remove(suscripciòn);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Suscripciòn> findSuscripciònEntities() {
        return findSuscripciònEntities(true, -1, -1);
    }

    public List<Suscripciòn> findSuscripciònEntities(int maxResults, int firstResult) {
        return findSuscripciònEntities(false, maxResults, firstResult);
    }

    private List<Suscripciòn> findSuscripciònEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Suscripciòn.class));
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

    public Suscripciòn findSuscripciòn(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Suscripciòn.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuscripciònCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Suscripciòn> rt = cq.from(Suscripciòn.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
