package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.DetalleVacacion;
import com.asinfo.as2.entities.Vacacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioDetalleVacacion
{
  public abstract void guardar(DetalleVacacion paramDetalleVacacion)
    throws ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(DetalleVacacion paramDetalleVacacion);
  
  public abstract DetalleVacacion buscarPorId(int paramInt);
  
  public abstract List<DetalleVacacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DetalleVacacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract DetalleVacacion cargarDetalle(int paramInt);
  
  public abstract List<Vacacion> getListaVacacion(int paramInt1, int paramInt2);
  
  public abstract int totalDiasSolicitadosPorEmpleadoPorVacacion(int paramInt1, int paramInt2);
  
  public abstract List getReporteSolicitudVacacion(int paramInt1, int paramInt2);
  
  public abstract List<Vacacion> obtenerListaVacacionesPorDetalle(int paramInt);
  
  public abstract boolean verificarDetalleVacacionDuplicado(DetalleVacacion paramDetalleVacacion);
  
  public abstract List<Vacacion> getListaVacacion(int paramInt1, int paramInt2, DetalleVacacion paramDetalleVacacion);
  
  public abstract void aprobarVacacion(DetalleVacacion paramDetalleVacacion)
    throws AS2Exception;
  
  public abstract void cerrarVacacion(DetalleVacacion paramDetalleVacacion)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioDetalleVacacion
 * JD-Core Version:    0.7.0.1
 */