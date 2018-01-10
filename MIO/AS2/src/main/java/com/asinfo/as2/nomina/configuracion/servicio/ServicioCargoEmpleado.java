package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.CargoEmpleado;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCargoEmpleado
{
  public abstract void guardar(CargoEmpleado paramCargoEmpleado);
  
  public abstract void eliminar(CargoEmpleado paramCargoEmpleado);
  
  public abstract CargoEmpleado buscarPorId(int paramInt);
  
  public abstract List<CargoEmpleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CargoEmpleado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CargoEmpleado cargarDetalle(int paramInt);
  
  public abstract CargoEmpleado buscarPorNombre(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioCargoEmpleado
 * JD-Core Version:    0.7.0.1
 */