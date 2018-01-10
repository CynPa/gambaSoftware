package com.asinfo.as2.mantenimiento.configuracion.servicio;

import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
import com.asinfo.as2.entities.mantenimiento.DetallePlanMantenimientoFrecuencia;
import com.asinfo.as2.entities.mantenimiento.Equipo;
import com.asinfo.as2.entities.mantenimiento.LecturaMantenimiento;
import com.asinfo.as2.entities.mantenimiento.PlanMantenimiento;
import com.asinfo.as2.enumeraciones.TipoFrecuenciaEnum;
import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPlanMantenimiento
{
  public abstract void guardar(PlanMantenimiento paramPlanMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract void eliminar(PlanMantenimiento paramPlanMantenimiento)
    throws AS2ExceptionMantenimiento;
  
  public abstract PlanMantenimiento buscarPorId(Integer paramInteger);
  
  public abstract List<PlanMantenimiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract PlanMantenimiento cargarDetalle(PlanMantenimiento paramPlanMantenimiento);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<PlanMantenimiento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<DetallePlanMantenimientoFrecuencia> cargarDetallePlan(ComponenteEquipo paramComponenteEquipo, Equipo paramEquipo);
  
  public abstract Equipo cargarEquipoAsignadoPlanMantenimiento(Equipo paramEquipo);
  
  public abstract List<DetallePlanMantenimientoFrecuencia> obtenerDetallePlanMantenimientoFrecuencia(Equipo paramEquipo, ComponenteEquipo paramComponenteEquipo, ActividadMantenimiento paramActividadMantenimiento, TipoFrecuenciaEnum paramTipoFrecuenciaEnum);
  
  public abstract List<LecturaMantenimiento> crearLecturasMantenimiento(Date paramDate, boolean paramBoolean, Equipo paramEquipo, ComponenteEquipo paramComponenteEquipo, ActividadMantenimiento paramActividadMantenimiento, TipoFrecuenciaEnum paramTipoFrecuenciaEnum);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioPlanMantenimiento
 * JD-Core Version:    0.7.0.1
 */