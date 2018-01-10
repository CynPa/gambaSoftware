package com.asinfo.as2.produccion.procesos.servicio;

import com.asinfo.as2.entities.calidad.VariableCalidad;
import com.asinfo.as2.entities.produccion.HistoricoCalidadOrdenFabricacion;
import com.asinfo.as2.entities.produccion.OrdenFabricacion;
import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioHistoricoCalidadOrdenFabricacion
{
  public abstract void guardar(HistoricoCalidadOrdenFabricacion paramHistoricoCalidadOrdenFabricacion);
  
  public abstract void eliminar(HistoricoCalidadOrdenFabricacion paramHistoricoCalidadOrdenFabricacion);
  
  public abstract HistoricoCalidadOrdenFabricacion buscarPorId(int paramInt);
  
  public abstract List<HistoricoCalidadOrdenFabricacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<HistoricoCalidadOrdenFabricacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract HistoricoCalidadOrdenFabricacion cargarDetalle(int paramInt);
  
  public abstract BigDecimal obtenerSumaCantidadPorOrdenFabricacionEstado(OrdenFabricacion paramOrdenFabricacion, EstadoControlCalidad paramEstadoControlCalidad);
  
  public abstract List<VariableCalidad> getListaVariableCalidadPromedioSubordenes(List<OrdenFabricacion> paramList);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.ServicioHistoricoCalidadOrdenFabricacion
 * JD-Core Version:    0.7.0.1
 */