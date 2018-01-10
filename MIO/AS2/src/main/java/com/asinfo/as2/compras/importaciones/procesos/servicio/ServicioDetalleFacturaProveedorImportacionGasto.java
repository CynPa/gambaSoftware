package com.asinfo.as2.compras.importaciones.procesos.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.DetalleFacturaProveedorImportacionGasto;
import com.asinfo.as2.entities.FacturaProveedor;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDetalleFacturaProveedorImportacionGasto
{
  public abstract void guardar(DetalleFacturaProveedorImportacionGasto paramDetalleFacturaProveedorImportacionGasto);
  
  public abstract void eliminar(DetalleFacturaProveedorImportacionGasto paramDetalleFacturaProveedorImportacionGasto);
  
  public abstract DetalleFacturaProveedorImportacionGasto buscarPorId(int paramInt);
  
  public abstract List<DetalleFacturaProveedorImportacionGasto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DetalleFacturaProveedorImportacionGasto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract DetalleFacturaProveedorImportacionGasto cargarDetalle(int paramInt);
  
  public abstract List<DetalleFacturaProveedorImportacionGasto> obtenerListaPresupuestoImportacion(FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2Compras;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.procesos.servicio.ServicioDetalleFacturaProveedorImportacionGasto
 * JD-Core Version:    0.7.0.1
 */