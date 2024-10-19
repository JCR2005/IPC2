/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import ControlPersistencia.exceptions.PreexistingEntityException;
import JPA.revistaEtiqueta;
import JPA.revistaEtiqueta.RevistaEtiquetaId;
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
public class revistaEtiquetaJpaController implements Serializable {

    public revistaEtiquetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;
    
      public revistaEtiquetaJpaController() {
      
        this.emf = EntityManagerUtil.getEntityManagerFactory();
    }
    

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(revistaEtiqueta revistaEtiqueta) throws PreexistingEntityException, Exception {
        if (revistaEtiqueta.getId() == null) {
            revistaEtiqueta.setId(new RevistaEtiquetaId());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(revistaEtiqueta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findrevistaEtiqueta(revistaEtiqueta.getId()) != null) {
                throw new PreexistingEntityException("revistaEtiqueta " + revistaEtiqueta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(revistaEtiqueta revistaEtiqueta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            revistaEtiqueta = em.merge(revistaEtiqueta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                RevistaEtiquetaId id = revistaEtiqueta.getId();
                if (findrevistaEtiqueta(id) == null) {
                    throw new NonexistentEntityException("The revistaEtiqueta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(revistaEtiqueta.RevistaEtiquetaId id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            revistaEtiqueta revistaEtiqueta;
            try {
                revistaEtiqueta = em.getReference(revistaEtiqueta.class, id);
                revistaEtiqueta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The revistaEtiqueta with id " + id + " no longer exists.", enfe);
            }
            em.remove(revistaEtiqueta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<revistaEtiqueta> findrevistaEtiquetaEntities() {
        return findrevistaEtiquetaEntities(true, -1, -1);
    }

    public List<revistaEtiqueta> findrevistaEtiquetaEntities(int maxResults, int firstResult) {
        return findrevistaEtiquetaEntities(false, maxResults, firstResult);
    }

    private List<revistaEtiqueta> findrevistaEtiquetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(revistaEtiqueta.class));
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

    public revistaEtiqueta findrevistaEtiqueta(revistaEtiqueta.RevistaEtiquetaId id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(revistaEtiqueta.class, id);
        } finally {
            em.close();
        }
    }

    public int getrevistaEtiquetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<revistaEtiqueta> rt = cq.from(revistaEtiqueta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
