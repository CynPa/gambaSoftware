package com.asinfo.as2.mantenimiento.configuracion.servicio;

import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioActividadMantenimiento
{
  public abstract void guardar(ActividadMantenimiento paramActividadMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract void eliminar(ActividadMantenimiento paramActividadMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract ActividadMantenimiento buscarPorId(Integer paramInteger);
  
  public abstract List<ActividadMantenimiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract ActividadMantenimiento cargarDetalle(ActividadMantenimiento paramActividadMantenimiento);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<ActividadMantenimiento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioActividadMantenimiento
 * JD-Core Version:    0.7.0.1
 */