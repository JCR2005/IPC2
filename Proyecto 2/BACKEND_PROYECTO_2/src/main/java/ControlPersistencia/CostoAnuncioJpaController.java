/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import ControlPersistencia.exceptions.PreexistingEntityException;
import JPA.CostoAnuncio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlosrodriguez
 */
public class CostoAnuncioJpaController implements Serializable {

    public CostoAnuncioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
       
    }
    
      public CostoAnuncioJpaController() {
     
       
        this.emf = EntityManagerUtil.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
     
        return emf.createEntityManager();
       
    }

     public void initializeCostoAnuncio() throws Exception {
   
        int count = getCostoAnuncioCount();

        if (count == 0) {
          create(new CostoAnuncio("add_texto", 10));
          create(new CostoAnuncio("add_imagen", 30));
          create(new CostoAnuncio("add_video", 50));
        }
    }
    public void create(CostoAnuncio costoAnuncio) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(costoAnuncio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCostoAnuncio(costoAnuncio.getId_Add()) != null) {
                throw new PreexistingEntityException("CostoAnuncio " + costoAnuncio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CostoAnuncio costoAnuncio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            costoAnuncio = em.merge(costoAnuncio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = costoAnuncio.getId_Add();
                if (findCostoAnuncio(id) == null) {
                    throw new NonexistentEntityException("The costoAnuncio with id " + id + " no longer exists.");
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
            CostoAnuncio costoAnuncio;
            try {
                costoAnuncio = em.getReference(CostoAnuncio.class, id);
                costoAnuncio.getId_Add();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The costoAnuncio with id " + id + " no longer exists.", enfe);
            }
            em.remove(costoAnuncio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CostoAnuncio> findCostoAnuncioEntities() {
        return findCostoAnuncioEntities(true, -1, -1);
    }

    public List<CostoAnuncio> findCostoAnuncioEntities(int maxResults, int firstResult) {
        return findCostoAnuncioEntities(false, maxResults, firstResult);
    }

    private List<CostoAnuncio> findCostoAnuncioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CostoAnuncio.class));
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

    public CostoAnuncio findCostoAnuncio(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CostoAnuncio.class, id);
        } finally {
            em.close();
        }
    }

    public int getCostoAnuncioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CostoAnuncio> rt = cq.from(CostoAnuncio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
