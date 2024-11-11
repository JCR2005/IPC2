package ControlPersistencia;

import ControlPersistencia.exceptions.NonexistentEntityException;
import JPA.Ingreso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * Controlador JPA para gestionar las operaciones CRUD de la entidad {@link Ingreso}.
 * Permite crear, editar, eliminar y buscar registros de ingresos en la base de datos.
 * Utiliza la gestión de transacciones de JPA.
 * 
 * @author carlosrodriguez
 */
public class IngresosJpaController implements Serializable {

    private EntityManagerFactory emf;

    /**
     * Constructor que inicializa el controlador con una fábrica de gestores de entidad (EntityManagerFactory).
     *
     * @param emf La fábrica de gestores de entidad.
     */
    public IngresosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * Constructor sin parámetros que inicializa la fábrica de gestores de entidad usando una utilidad.
     */
    public IngresosJpaController() {
        this.emf = EntityManagerUtil.getEntityManagerFactory();
    }

    /**
     * Obtiene un nuevo gestor de entidad (EntityManager).
     *
     * @return Un gestor de entidad.
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Crea un nuevo registro de ingresos en la base de datos.
     *
     * @param ingresos La entidad {@link Ingreso} a persistir.
     */
    public void create(Ingreso ingresos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ingresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Actualiza un registro de ingresos en la base de datos.
     *
     * @param ingresos La entidad {@link Ingreso} a actualizar.
     * @throws NonexistentEntityException Si el registro no existe en la base de datos.
     * @throws Exception Si ocurre un error durante la transacción.
     */
    public void edit(Ingreso ingresos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ingresos = em.merge(ingresos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ingresos.getIdIngerso();
                if (findIngresos(id) == null) {
                    throw new NonexistentEntityException("El ingreso con id " + id + " no existe.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Elimina un registro de ingresos de la base de datos.
     *
     * @param id El identificador del registro de ingresos a eliminar.
     * @throws NonexistentEntityException Si el registro no existe en la base de datos.
     */
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingreso ingresos;
            try {
                ingresos = em.getReference(Ingreso.class, id);
                ingresos.getIdIngerso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("El ingreso con id " + id + " no existe.", enfe);
            }
            em.remove(ingresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Obtiene una lista de todos los registros de ingresos.
     *
     * @return Una lista de entidades {@link Ingreso}.
     */
    public List<Ingreso> findIngresosEntities() {
        return findIngresosEntities(true, -1, -1);
    }

    /**
     * Obtiene una lista de registros de ingresos con límite de resultados.
     *
     * @param maxResults Número máximo de resultados a obtener.
     * @param firstResult Primer resultado a obtener.
     * @return Una lista de entidades {@link Ingreso}.
     */
    public List<Ingreso> findIngresosEntities(int maxResults, int firstResult) {
        return findIngresosEntities(false, maxResults, firstResult);
    }

    /**
     * Obtiene una lista de registros de ingresos, con o sin límite de resultados.
     *
     * @param all Indica si se deben obtener todos los registros.
     * @param maxResults Número máximo de resultados a obtener.
     * @param firstResult Primer resultado a obtener.
     * @return Una lista de entidades {@link Ingreso}.
     */
    private List<Ingreso> findIngresosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Ingreso> cq = em.getCriteriaBuilder().createQuery(Ingreso.class);
            cq.select(cq.from(Ingreso.class));
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

    /**
     * Busca un registro de ingresos por su ID.
     *
     * @param id Identificador del registro de ingresos.
     * @return La entidad {@link Ingreso} encontrada o {@code null} si no existe.
     */
    public Ingreso findIngresos(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingreso.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene la cantidad total de registros de ingresos en la base de datos.
     *
     * @return Número total de registros de ingresos.
     */
    public int getIngresosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Long> cq = em.getCriteriaBuilder().createQuery(Long.class);
            Root<Ingreso> rt = cq.from(Ingreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
