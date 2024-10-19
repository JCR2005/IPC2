/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import ControlPersistencia.exceptions.PreexistingEntityException;
import JPA.Cartera;
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
public class CarteraJpaController implements Serializable {

    public CarteraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
     public CarteraJpaController() {
     
        this.emf = EntityManagerUtil.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cartera cartera) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cartera);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCartera(cartera.getIdCartera()) != null) {
                throw new PreexistingEntityException("Cartera " + cartera + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cartera cartera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cartera = em.merge(cartera);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cartera.getIdCartera();
                if (findCartera(id) == null) {
                    throw new NonexistentEntityException("The cartera with id " + id + " no longer exists.");
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
            Cartera cartera;
            try {
                cartera = em.getReference(Cartera.class, id);
                cartera.getIdCartera();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cartera with id " + id + " no longer exists.", enfe);
            }
            em.remove(cartera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cartera> findCarteraEntities() {
        return findCarteraEntities(true, -1, -1);
    }

    public List<Cartera> findCarteraEntities(int maxResults, int firstResult) {
        return findCarteraEntities(false, maxResults, firstResult);
    }

    private List<Cartera> findCarteraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cartera.class));
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

    public Cartera findCartera(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cartera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarteraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cartera> rt = cq.from(Cartera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally { 
            em.close();
        }
    }
    
}
