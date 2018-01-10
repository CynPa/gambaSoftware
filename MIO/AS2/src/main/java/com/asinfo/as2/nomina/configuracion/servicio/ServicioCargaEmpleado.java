package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.CargaEmpleado;
import com.asinfo.as2.entities.Empleado;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCargaEmpleado
{
  public abstract void guardar(Empleado paramEmpleado);
  
  public abstract void eliminar(CargaEmpleado paramCargaEmpleado);
  
  public abstract CargaEmpleado buscarPorId(int paramInt);
  
  public abstract List<CargaEmpleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CargaEmpleado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CargaEmpleado cargarDetalle(int paramInt);
  
  public abstract void guardar(CargaEmpleado paramCargaEmpleado);
  
  public abstract void actualizaIndicadorActivo(int paramInt, boolean paramBoolean);
  
  public abstract void actualizaCargasActivas(int paramInt, Date paramDate);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioCargaEmpleado
 * JD-Core Version:    0.7.0.1
 */