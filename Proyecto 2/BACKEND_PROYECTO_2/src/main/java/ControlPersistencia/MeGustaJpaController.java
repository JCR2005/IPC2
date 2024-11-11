/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.MeGusta;
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
public class MeGustaJpaController implements Serializable {

    public MeGustaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
      /**
     * Constructor sin parámetros que inicializa la fábrica de gestores de entidad usando una utilidad.
     */
    public MeGustaJpaController() {
        this.emf = EntityManagerUtil.getEntityManagerFactory();
    }

    public void create(MeGusta meGusta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(meGusta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MeGusta meGusta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            meGusta = em.merge(meGusta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = meGusta.getIdMeGusta();
                if (findMeGusta(id) == null) {
                    throw new NonexistentEntityException("The meGusta with id " + id + " no longer exists.");
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
            MeGusta meGusta;
            try {
                meGusta = em.getReference(MeGusta.class, id);
                meGusta.getIdMeGusta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The meGusta with id " + id + " no longer exists.", enfe);
            }
            em.remove(meGusta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MeGusta> findMeGustaEntities() {
        return findMeGustaEntities(true, -1, -1);
    }

    public List<MeGusta> findMeGustaEntities(int maxResults, int firstResult) {
        return findMeGustaEntities(false, maxResults, firstResult);
    }

    private List<MeGusta> findMeGustaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MeGusta.class));
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

    public MeGusta findMeGusta(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MeGusta.class, id);
        } finally {
            em.close();
        }
    }

    public int getMeGustaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MeGusta> rt = cq.from(MeGusta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
