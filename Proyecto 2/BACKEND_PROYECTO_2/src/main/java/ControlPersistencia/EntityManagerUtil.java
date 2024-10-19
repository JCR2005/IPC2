package ControlPersistencia;



import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistentUnit");

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
