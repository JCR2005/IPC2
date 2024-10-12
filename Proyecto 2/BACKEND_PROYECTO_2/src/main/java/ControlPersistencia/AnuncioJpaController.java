/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.Anuncio;
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
public class AnuncioJpaController implements Serializable {

    public AnuncioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
      public AnuncioJpaController() {
     
        emf = Persistence.createEntityManagerFactory("PersistentUnit");

    }

    public void create(Anuncio anuncio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(anuncio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Anuncio anuncio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            anuncio = em.merge(anuncio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = anuncio.getIdAnuncio();
                if (findAnuncio(id) == null) {
                    throw new NonexistentEntityException("The anuncio with id " + id + " no longer exists.");
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
            Anuncio anuncio;
            try {
                anuncio = em.getReference(Anuncio.class, id);
                anuncio.getIdAnuncio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The anuncio with id " + id + " no longer exists.", enfe);
            }
            em.remove(anuncio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
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
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Anuncio.class));
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

    public Anuncio findAnuncio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Anuncio.class, id);
        } finally {
            em.close();
        }
    }

    public int getAnuncioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Anuncio> rt = cq.from(Anuncio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
