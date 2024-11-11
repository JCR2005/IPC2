package JPA;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.sql.Date;

/**
 * Entidad JPA que representa los ingresos de artículos en el sistema. Contiene
 * información sobre el monto, la fecha, el usuario que realizó el ingreso y el
 * propósito del mismo.
 *
 * @author carlosrodriguez
 */
@Entity
public class Ingreso implements Serializable {

    /**
     * Identificador único del artículo, generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIngerso;

    /**
     * Monto asociado al ingreso.
     */
    private double monto;

    /**
     * Fecha en la que se registró el ingreso.
     */
    private Date fecha;

    /**
     * Descripción o identificación del usuario que realizó el ingreso.
     */
    private String idUsuario;

    /**
     * Propósito o razón del ingreso.
     */
    private String proposito;

    /**
     * Descripción o identificación de la revista por el cual se hizo el pago.
     */
    private String idVinculado;

    /**
     * Constructor sin argumentos requerido por JPA.
     */
    public Ingreso() {
    }

    /**
     * Constructor con todos los parámetros.
     *
     * @param monto Monto del ingreso.
     * @param fecha Fecha del ingreso.
     * @param dUsuario Descripción del usuario.
     * @param proposito Propósito del ingreso.
     * @param idRevista Revista asociada al ingreso.
     */
    public Ingreso(double monto, Date fecha, String dUsuario, String proposito, String idRevista) {
        this.monto = monto;
        this.fecha = fecha;
        this.idUsuario = dUsuario;
        this.proposito = proposito;
        this.idVinculado = idRevista;
    }

    /**
     * Obtiene el identificador del artículo.
     *
     * @return idArticulo Identificador del artículo.
     */
    public Long getIdIngerso() {
        return idIngerso;
    }

    /**
     * Establece el identificador del artículo.
     *
     * @param idArticulo Identificador único del artículo.
     */
    public void setIdIngreso(Long idArticulo) {
        this.idIngerso = idArticulo;
    }

    /**
     * Obtiene el monto del ingreso.
     *
     * @return monto Monto del ingreso.
     */
    public double getMonto() {
        return monto;
    }

    /**
     * Establece el monto del ingreso.
     *
     * @param monto Monto del ingreso.
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }

    /**
     * Obtiene la fecha del ingreso.
     *
     * @return fecha Fecha del ingreso.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha del ingreso.
     *
     * @param fecha Fecha del ingreso.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

  

    /**
     * Obtiene la descripción del usuario que realizó el ingreso.
     *
     * @return dUsuario Descripción del usuario.
     */
    public void setIdUsuario(String idUsuario) {   
        this.idUsuario = idUsuario;
    }

    /**
     * Establece la descripción del usuario que realizó el ingreso.
     *
     * @param dUsuario Descripción del usuario.
     */
      public String getIdUsuario() {
        return idUsuario;
    }
      
    /**
     * Obtiene el propósito del ingreso.
     *
     * @return proposito Propósito del ingreso.
     */
    public String getProposito() {
        return proposito;
    }

    /**
     * Establece el propósito del ingreso.
     *
     * @param proposito Propósito del ingreso.
     */
    public void setProposito(String proposito) {
        this.proposito = proposito;
    }

    /**
     * Obtiene el id de la revista del del pago.
     *
     * @param idVinculado idVinculado del ingreso.
     */
    public String getIdVinculado() {
        return idVinculado;
    }

    /**
     * Establece el id de la revista del del pago.
     *
     * @param idVinculado idVinculado del ingreso.
     */
    public void setIdVinculado(String idVinculado) {
        this.idVinculado = idVinculado;
    }

}
