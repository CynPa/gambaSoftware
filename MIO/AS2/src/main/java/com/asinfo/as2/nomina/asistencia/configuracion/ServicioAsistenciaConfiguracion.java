package com.asinfo.as2.nomina.asistencia.configuracion;

import com.asinfo.as2.entities.Rubro;
import com.asinfo.as2.entities.nomina.asistencia.HoraExtra;
import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
import com.asinfo.as2.enumeraciones.PorCientoHoraExtra;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAsistenciaConfiguracion
{
  public abstract void copiarDiasFestivos(Integer paramInteger1, Integer paramInteger2);
  
  public abstract HorarioEmpleado cargarDetalleHorarioEmpleado(HorarioEmpleado paramHorarioEmpleado);
  
  public abstract boolean esDiaFestivo(int paramInt, Date paramDate);
  
  public abstract HoraExtra cargarDetalleHoraExtra(HoraExtra paramHoraExtra);
  
  public abstract List<HoraExtra> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Rubro buscarRubroHoraExtra(int paramInt, PorCientoHoraExtra paramPorCientoHoraExtra);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.configuracion.ServicioAsistenciaConfiguracion
 * JD-Core Version:    0.7.0.1
 */