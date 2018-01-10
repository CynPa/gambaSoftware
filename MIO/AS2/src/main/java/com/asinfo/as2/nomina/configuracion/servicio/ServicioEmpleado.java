package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.Departamento;
import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.Rubro;
import com.asinfo.as2.entities.nomina.asistencia.GrupoTrabajo;
import com.asinfo.as2.entities.nomina.asistencia.HorarioEmpleado;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioEmpleado
{
  public abstract List<Empleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<Empleado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Empleado> obtenerListaComboMultiple(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Empleado cargarDetalle(int paramInt);
  
  public abstract GrupoTrabajo cargarDetalleGrupoTrabajo(int paramInt);
  
  public abstract Empleado cargarRubros(int paramInt);
  
  public abstract Empleado bucarEmpleadoPorIdentificacion(String paramString, int paramInt);
  
  public abstract Empleado buscarPorId(int paramInt);
  
  public abstract List generarDatosArchivoEntradaIESS(int paramInt, Date paramDate1, Date paramDate2);
  
  public abstract List generarDatosArchivoSalidaIESS(Date paramDate1, Date paramDate2);
  
  public abstract Empleado getEmpleadoPorCargaEmpleado(int paramInt);
  
  public abstract List<HorarioEmpleado> getListaHorarios(Departamento paramDepartamento);
  
  public abstract List<Rubro> getListaRubros(int paramInt);
  
  public abstract Empleado VerificarGrupoTrabajoEmpleado(Empleado paramEmpleado);
  
  public abstract void guardarSoloEntidad(Empleado paramEmpleado);
  
  public abstract Empleado cargarEmpresa(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado
 * JD-Core Version:    0.7.0.1
 */