/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import ControlPersistencia.exceptions.PreexistingEntityException;
import JPA.CostosGlobales;
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
public class CostosGlobalesJpaController implements Serializable {

    public CostosGlobalesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    
    public   CostosGlobalesJpaController(){
       
        this.emf = EntityManagerUtil.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
         public void initializeCostosGlobales() throws Exception {
   
        int count = getCostosGlobalesCount();

        if (count == 0) {
          create(new CostosGlobales("CostoOcultaciòn", 10));
          create(new CostosGlobales("CostoAsociado", 2));
  
        }
    }

    public void create(CostosGlobales costosGlobales) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(costosGlobales);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCostosGlobales(costosGlobales.getIdCosto()) != null) {
                throw new PreexistingEntityException("CostosGlobales " + costosGlobales + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CostosGlobales costosGlobales) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            costosGlobales = em.merge(costosGlobales);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = costosGlobales.getIdCosto();
                if (findCostosGlobales(id) == null) {
                    throw new NonexistentEntityException("The costosGlobales with id " + id + " no longer exists.");
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
            CostosGlobales costosGlobales;
            try {
                costosGlobales = em.getReference(CostosGlobales.class, id);
                costosGlobales.getIdCosto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The costosGlobales with id " + id + " no longer exists.", enfe);
            }
            em.remove(costosGlobales);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CostosGlobales> findCostosGlobalesEntities() {
        return findCostosGlobalesEntities(true, -1, -1);
    }

    public List<CostosGlobales> findCostosGlobalesEntities(int maxResults, int firstResult) {
        return findCostosGlobalesEntities(false, maxResults, firstResult);
    }

    private List<CostosGlobales> findCostosGlobalesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CostosGlobales.class));
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

    public CostosGlobales findCostosGlobales(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CostosGlobales.class, id);
        } finally {
            em.close();
        }
    }

    public int getCostosGlobalesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CostosGlobales> rt = cq.from(CostosGlobales.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
