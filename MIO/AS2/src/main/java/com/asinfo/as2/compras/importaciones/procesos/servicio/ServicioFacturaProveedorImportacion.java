package com.asinfo.as2.compras.importaciones.procesos.servicio;

import com.asinfo.as2.entities.DetalleFacturaProveedorImportacion;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.FacturaProveedorImportacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioFacturaProveedorImportacion
{
  public abstract void guardar(FacturaProveedorImportacion paramFacturaProveedorImportacion);
  
  public abstract void eliminar(FacturaProveedorImportacion paramFacturaProveedorImportacion);
  
  public abstract FacturaProveedorImportacion buscarPorId(int paramInt);
  
  public abstract List<FacturaProveedorImportacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<FacturaProveedorImportacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract FacturaProveedorImportacion cargarDetalle(int paramInt);
  
  public abstract void validarFacturaProveedorImportacion(FacturaProveedorImportacion paramFacturaProveedorImportacion)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract FacturaProveedorImportacion prorratear(int paramInt)
    throws ExcepcionAS2;
  
  public abstract void liquidarImportacion(FacturaProveedor paramFacturaProveedor, Date paramDate)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void guardarDatosImportacion(FacturaProveedorImportacion paramFacturaProveedorImportacion);
  
  public abstract void agregarPresupuestoImportacion(FacturaProveedor paramFacturaProveedor, boolean paramBoolean)
    throws AS2Exception;
  
  public abstract List<DetalleFacturaProveedorImportacion> cargarFacturasProveedor(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<Object[]> getFacturasProveedorImportacion(int paramInt1, int paramInt2)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioFacturaProveedorImportacion
 * JD-Core Version:    0.7.0.1
 */