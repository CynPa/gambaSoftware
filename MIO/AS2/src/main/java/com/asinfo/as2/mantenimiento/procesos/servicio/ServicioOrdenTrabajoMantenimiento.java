package com.asinfo.as2.mantenimiento.procesos.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
import com.asinfo.as2.entities.mantenimiento.ConsumoAgilMantenimiento;
import com.asinfo.as2.entities.mantenimiento.DetalleOrdenTrabajoMantenimiento;
import com.asinfo.as2.entities.mantenimiento.Equipo;
import com.asinfo.as2.entities.mantenimiento.OrdenTrabajoMantenimiento;
import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioOrdenTrabajoMantenimiento
{
  public abstract void guardar(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract void eliminar(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract OrdenTrabajoMantenimiento buscarPorId(Integer paramInteger);
  
  public abstract List<OrdenTrabajoMantenimiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract OrdenTrabajoMantenimiento cargarDetalle(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<OrdenTrabajoMantenimiento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void actualizarMateriales(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento);
  
  public abstract void actualizarHerramientas(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento, boolean paramBoolean);
  
  public abstract void procesar(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract void cargarOrdenTrabajoEnConsumoBodega(MovimientoInventario paramMovimientoInventario, OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento, Bodega paramBodega)
    throws ExcepcionAS2;
  
  public abstract void actualizarOrdenTrabajo(MovimientoInventario paramMovimientoInventario)
    throws AS2Exception;
  
  public abstract void anular(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract void cerrarDetalleOrdenTrabajo(DetalleOrdenTrabajoMantenimiento paramDetalleOrdenTrabajoMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract void cargarResponsablesporActividad(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento, DetalleOrdenTrabajoMantenimiento paramDetalleOrdenTrabajoMantenimiento);
  
  public abstract void cargarMaterialesporActividad(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento, DetalleOrdenTrabajoMantenimiento paramDetalleOrdenTrabajoMantenimiento);
  
  public abstract void cerrarOrdenTrabajo(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract void guardarOrdentTrabajo(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento, ConsumoAgilMantenimiento paramConsumoAgilMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract void cargarOrdenTrabajoEnConsumoBodega(MovimientoInventario paramMovimientoInventario, OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento, ConsumoAgilMantenimiento paramConsumoAgilMantenimiento)
    throws ExcepcionAS2, AS2ExceptionMantenimiento;
  
  public abstract void procesarConsumoAgilMantenimiento(OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento, ConsumoAgilMantenimiento paramConsumoAgilMantenimiento, MovimientoInventario paramMovimientoInventario)
    throws AS2ExceptionMantenimiento, ExcepcionAS2;
  
  public abstract List<Object[]> getReporteCostoHistoricoMantenimiento(Date paramDate1, Date paramDate2, CategoriaEquipo paramCategoriaEquipo, SubcategoriaEquipo paramSubcategoriaEquipo, Equipo paramEquipo, ComponenteEquipo paramComponenteEquipo, ActividadMantenimiento paramActividadMantenimiento, OrdenTrabajoMantenimiento paramOrdenTrabajoMantenimiento, String paramString, int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.servicio.ServicioOrdenTrabajoMantenimiento
 * JD-Core Version:    0.7.0.1
 */