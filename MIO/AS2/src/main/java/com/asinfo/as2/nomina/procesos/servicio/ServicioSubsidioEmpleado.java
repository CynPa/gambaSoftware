package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.Departamento;
import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.SubsidioEmpleado;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioSubsidioEmpleado
{
  public abstract void guardar(SubsidioEmpleado paramSubsidioEmpleado);
  
  public abstract void eliminar(SubsidioEmpleado paramSubsidioEmpleado);
  
  public abstract SubsidioEmpleado buscarPorId(int paramInt);
  
  public abstract List<SubsidioEmpleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<SubsidioEmpleado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract SubsidioEmpleado cargarDetalle(int paramInt);
  
  public abstract List<SubsidioEmpleado> getSubsidioEmpleadoPorFecha(int paramInt, Date paramDate, Departamento paramDepartamento, Empleado paramEmpleado);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioSubsidioEmpleado
 * JD-Core Version:    0.7.0.1
 */