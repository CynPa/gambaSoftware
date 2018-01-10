package com.asinfo.as2.produccion.procesos.servicio;

import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.DetalleOrdenSalidaMaterialOrdenFabricacion;
import com.asinfo.as2.entities.Lote;
import com.asinfo.as2.entities.OrdenSalidaMaterial;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
import com.asinfo.as2.entities.produccion.OperacionOrdenFabricacion;
import com.asinfo.as2.entities.produccion.OrdenFabricacion;
import com.asinfo.as2.entities.produccion.RutaFabricacion;
import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
import com.asinfo.as2.enumeraciones.EstadoProduccionEnum;
import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioOrdenFabricacion
{
  public abstract void guardar(OrdenFabricacion paramOrdenFabricacion)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(OrdenFabricacion paramOrdenFabricacion);
  
  public abstract OrdenFabricacion buscarPorId(int paramInt);
  
  public abstract List<OrdenFabricacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean1, Map<String, String> paramMap, boolean paramBoolean2);
  
  public abstract List<OrdenFabricacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract OrdenFabricacion cargarDetalle(int paramInt);
  
  public abstract void lanzarOrden(OrdenFabricacion paramOrdenFabricacion)
    throws AS2Exception;
  
  public abstract void finalizarOrden(OrdenFabricacion paramOrdenFabricacion, Date paramDate, boolean paramBoolean)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract List<OrdenFabricacion> autocompletarOrdenesAbiertas(int paramInt, String paramString, CategoriaProducto paramCategoriaProducto);
  
  public abstract List<OrdenFabricacion> getListaOrdenesLanzadas(int paramInt, Date paramDate1, Date paramDate2);
  
  public abstract void cargarOperaciones(OrdenFabricacion paramOrdenFabricacion, boolean paramBoolean);
  
  public abstract List<OrdenFabricacion> getListaOrdenFabricacionFinalizar(List<OrdenFabricacion> paramList, Boolean paramBoolean1, Boolean paramBoolean2);
  
  public abstract void anular(OrdenFabricacion paramOrdenFabricacion)
    throws AS2Exception;
  
  public abstract void reabrirOrden(OrdenFabricacion paramOrdenFabricacion)
    throws ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void solicitarMaterial(List<OrdenFabricacion> paramList, String paramString, OrdenSalidaMaterial paramOrdenSalidaMaterial, Date paramDate, boolean paramBoolean)
    throws AS2Exception, ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract List<OrdenFabricacion> obtenerListaSubordenFabricacion(int paramInt, boolean paramBoolean);
  
  public abstract void cargarSubordenes(OrdenFabricacion paramOrdenFabricacion)
    throws AS2Exception;
  
  public abstract void actualizarDatosSubordenes(OrdenFabricacion paramOrdenFabricacion)
    throws AS2Exception;
  
  public abstract void cargarSaldoBodegaTrabajo(OrdenFabricacion paramOrdenFabricacion, Lote paramLote, Date paramDate);
  
  public abstract List<Object[]> getReporteInspeccionCalidadPT(Date paramDate1, Date paramDate2, EstadoControlCalidad paramEstadoControlCalidad, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, OrdenFabricacion paramOrdenFabricacion);
  
  public abstract List<Object[]> getReporteFormulacion(OrdenFabricacion paramOrdenFabricacion);
  
  public abstract void actualizarFormula(List<DetalleOrdenFabricacionMaterial> paramList, OrdenFabricacion paramOrdenFabricacion, BigDecimal paramBigDecimal)
    throws AS2Exception;
  
  public abstract void actualizarCantidadSubordenes(OrdenFabricacion paramOrdenFabricacion);
  
  public abstract void suspenderOrdenFabricacion(OrdenFabricacion paramOrdenFabricacion, boolean paramBoolean)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract List<OrdenFabricacion> buscarOrdenesPorRangoFechaCosto(int paramInt, Date paramDate1, Date paramDate2, boolean paramBoolean);
  
  public abstract BigDecimal getCostosMateriales(OrdenFabricacion paramOrdenFabricacion);
  
  public abstract List<DetalleOrdenSalidaMaterialOrdenFabricacion> obtenerDetalleOrdenSalidaMaterialOrdenFabricacion(OrdenFabricacion paramOrdenFabricacion, boolean paramBoolean);
  
  public abstract List<OrdenFabricacion> autocompletarOrdenesAbiertas(int paramInt, String paramString, CategoriaProducto paramCategoriaProducto, List<Integer> paramList, Boolean paramBoolean);
  
  public abstract List<OperacionOrdenFabricacion> getOperacionOrdenFabricacionPorAnioMes(int paramInt1, OrdenFabricacion paramOrdenFabricacion, int paramInt2, int paramInt3, boolean paramBoolean);
  
  public abstract List<OperacionOrdenFabricacion> getOperacionOrdenFabricacionActualizado(int paramInt1, OrdenFabricacion paramOrdenFabricacion, int paramInt2, int paramInt3, boolean paramBoolean);
  
  public abstract void guardarOperacionOrdenFrabricacion(OperacionOrdenFabricacion paramOperacionOrdenFabricacion);
  
  public abstract List<OrdenFabricacion> getConsultaOrdenFabricacion(Date paramDate, TipoCicloProduccionEnum paramTipoCicloProduccionEnum, EstadoProduccionEnum paramEstadoProduccionEnum, RutaFabricacion paramRutaFabricacion);
  
  public abstract List<OrdenFabricacion> getOrdenFabricacionPorOrdenSalidaMaterial(OrdenSalidaMaterial paramOrdenSalidaMaterial);
  
  public abstract List<Object[]> getReporteOrdenFabricacion(OrdenFabricacion paramOrdenFabricacion);
  
  public abstract void enviarWinCC(OrdenFabricacion paramOrdenFabricacion)
    throws AS2Exception;
  
  public abstract void finalizarOrdenPorFormulacion(OrdenFabricacion paramOrdenFabricacion, Date paramDate)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void anularPorFormulacion(OrdenFabricacion paramOrdenFabricacion)
    throws AS2Exception;
  
  public abstract void sincronizarMovimientosProduccion();
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenFabricacion
 * JD-Core Version:    0.7.0.1
 */