package com.asinfo.as2.nomina.asistencia.configuracion;

import com.asinfo.as2.entities.Departamento;
import com.asinfo.as2.entities.PagoRol;
import com.asinfo.as2.entities.nomina.asistencia.Asistencia;
import com.asinfo.as2.entities.nomina.asistencia.EmpleadoAsistencia;
import com.asinfo.as2.entities.nomina.asistencia.Turno;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.servicio.ServicioGenerico;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAsistencia
  extends ServicioGenerico<Asistencia>
{
  public abstract void procesarAsistencia(int paramInt, Date paramDate, Departamento paramDepartamento)
    throws AS2Exception;
  
  public abstract void generarAsistencia(int paramInt, Date paramDate, Departamento paramDepartamento)
    throws AS2Exception;
  
  public abstract void generarAsistencia(int paramInt, Date paramDate)
    throws AS2Exception;
  
  public abstract void generarNovedades(int paramInt, Date paramDate)
    throws AS2Exception;
  
  public abstract void calcularHorasExtras(int paramInt, Date paramDate)
    throws AS2Exception;
  
  public abstract void generarHorasExtrasPagoRol(int paramInt, PagoRol paramPagoRol)
    throws AS2Exception;
  
  public abstract List<Asistencia> obtenerListaPorPagina(int paramInt1, int paramInt2, Map<String, String> paramMap, Date paramDate1, Date paramDate2);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap, Date paramDate1, Date paramDate2);
  
  public abstract Asistencia calcularHorasExtras(Asistencia paramAsistencia)
    throws AS2Exception;
  
  public abstract void actualizarFeriados(int paramInt, Date paramDate1, Date paramDate2, Departamento paramDepartamento)
    throws AS2Exception;
  
  public abstract Asistencia cargarDetalle(int paramInt);
  
  public abstract void registrarAsistencia(int paramInt, Date paramDate, Departamento paramDepartamento)
    throws AS2Exception;
  
  public abstract void registrarAsistencia(int paramInt, Date paramDate)
    throws AS2Exception;
  
  public abstract List<EmpleadoAsistencia> obtenerListaHoraExtraEmpleado(Departamento paramDepartamento, Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract void crearAsistenciaManual(Asistencia paramAsistencia, Turno paramTurno)
    throws AS2Exception;
  
  public abstract List<EmpleadoAsistencia> obtenerListaFaltasEmpleado(Departamento paramDepartamento, Date paramDate1, Date paramDate2, int paramInt);
  
  public abstract void actualizarVacacion(int paramInt, Date paramDate1, Date paramDate2, Departamento paramDepartamento)
    throws AS2Exception;
  
  public abstract void actualizarSubsidio(int paramInt, Date paramDate1, Date paramDate2, Departamento paramDepartamento)
    throws AS2Exception;
  
  public abstract void actualizarHorasPermiso(int paramInt, Date paramDate1, Date paramDate2, Departamento paramDepartamento)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.asistencia.configuracion.ServicioAsistencia
 * JD-Core Version:    0.7.0.1
 */