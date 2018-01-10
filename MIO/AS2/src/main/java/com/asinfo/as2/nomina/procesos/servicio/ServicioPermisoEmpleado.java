package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.Departamento;
import com.asinfo.as2.entities.DetallePermisoEmpleado;
import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.PermisoEmpleado;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPermisoEmpleado
{
  public abstract void guardar(PermisoEmpleado paramPermisoEmpleado)
    throws ExcepcionAS2;
  
  public abstract void eliminar(PermisoEmpleado paramPermisoEmpleado);
  
  public abstract PermisoEmpleado buscarPorId(int paramInt);
  
  public abstract List<PermisoEmpleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PermisoEmpleado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PermisoEmpleado cargarDetalle(int paramInt);
  
  public abstract List getPersmisoEmpleado(int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<DetallePermisoEmpleado> getPermisoEmpleadoPorFecha(int paramInt, Date paramDate, Departamento paramDepartamento, Empleado paramEmpleado);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioPermisoEmpleado
 * JD-Core Version:    0.7.0.1
 */