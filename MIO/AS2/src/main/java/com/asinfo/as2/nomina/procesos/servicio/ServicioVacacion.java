package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.Departamento;
import com.asinfo.as2.entities.DetalleVacacion;
import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.HistoricoEmpleado;
import com.asinfo.as2.entities.Vacacion;
import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioVacacion
{
  public abstract void guardar(Vacacion paramVacacion);
  
  public abstract void eliminar(Vacacion paramVacacion);
  
  public abstract Vacacion buscarPorId(int paramInt);
  
  public abstract List<Vacacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Vacacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Vacacion cargarDetalle(int paramInt);
  
  public abstract void generarVacaciones(int paramInt, Empleado paramEmpleado, boolean paramBoolean);
  
  public abstract Vacacion buscarVacacionPorEmpleadoId(int paramInt);
  
  public abstract Vacacion generarAnticipoVacacion(int paramInt1, int paramInt2)
    throws ExcepcionAS2Nomina;
  
  public abstract List<Empleado> getEmpleadosConVacacionesPorFecha(int paramInt, Date paramDate, Departamento paramDepartamento, Empleado paramEmpleado);
  
  public abstract int getDiasPendientes(int paramInt);
  
  public abstract int getDiasPendientesFiniquito(HistoricoEmpleado paramHistoricoEmpleado);
  
  public abstract long getTotalDiasPendientesXCerrar(DetalleVacacion paramDetalleVacacion, Empleado paramEmpleado);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioVacacion
 * JD-Core Version:    0.7.0.1
 */