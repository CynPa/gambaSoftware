package com.asinfo.as2.mantenimiento.configuracion.servicio;

import com.asinfo.as2.entities.UbicacionActivo;
import com.asinfo.as2.entities.mantenimiento.ActividadMantenimiento;
import com.asinfo.as2.entities.mantenimiento.CalendarioMantenimiento;
import com.asinfo.as2.entities.mantenimiento.CalendarioMantenimientoEntidad;
import com.asinfo.as2.entities.mantenimiento.CategoriaEquipo;
import com.asinfo.as2.entities.mantenimiento.ComponenteEquipo;
import com.asinfo.as2.entities.mantenimiento.Equipo;
import com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo;
import com.asinfo.as2.entities.mantenimiento.TipoActividad;
import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCalendarioMantenimiento
{
  public abstract void generarCalendarioMantenimiento(int paramInt1, int paramInt2, Date paramDate)
    throws AS2ExceptionMantenimiento;
  
  public abstract List<CalendarioMantenimientoEntidad> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<CalendarioMantenimiento> obtenerListaCalendarioPorFecha(Date paramDate1, Date paramDate2, int paramInt, CategoriaEquipo paramCategoriaEquipo, SubcategoriaEquipo paramSubcategoriaEquipo, Equipo paramEquipo, ComponenteEquipo paramComponenteEquipo, TipoActividad paramTipoActividad, ActividadMantenimiento paramActividadMantenimiento, UbicacionActivo paramUbicacionActivo, boolean paramBoolean);
  
  public abstract void guardarInformacionCalendario(CalendarioMantenimientoEntidad paramCalendarioMantenimientoEntidad);
  
  public abstract List<Object[]> getReporteCalendarioMantenimiento(int paramInt, Date paramDate1, Date paramDate2, CategoriaEquipo paramCategoriaEquipo, SubcategoriaEquipo paramSubcategoriaEquipo, Equipo paramEquipo, ComponenteEquipo paramComponenteEquipo, TipoActividad paramTipoActividad, ActividadMantenimiento paramActividadMantenimiento, UbicacionActivo paramUbicacionActivo, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void enviarEmail(int paramInt, Date paramDate1, Date paramDate2, CategoriaEquipo paramCategoriaEquipo, SubcategoriaEquipo paramSubcategoriaEquipo, Equipo paramEquipo, ComponenteEquipo paramComponenteEquipo, TipoActividad paramTipoActividad, ActividadMantenimiento paramActividadMantenimiento, UbicacionActivo paramUbicacionActivo, boolean paramBoolean1, boolean paramBoolean2, String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.servicio.ServicioCalendarioMantenimiento
 * JD-Core Version:    0.7.0.1
 */