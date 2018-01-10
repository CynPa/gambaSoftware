package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.CausaSalidaEmpleado;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCausaSalidaEmpleado
{
  public abstract void guardar(CausaSalidaEmpleado paramCausaSalidaEmpleado);
  
  public abstract void eliminar(CausaSalidaEmpleado paramCausaSalidaEmpleado);
  
  public abstract CausaSalidaEmpleado buscarPorId(int paramInt);
  
  public abstract List<CausaSalidaEmpleado> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CausaSalidaEmpleado> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CausaSalidaEmpleado cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioCausaSalidaEmpleado
 * JD-Core Version:    0.7.0.1
 */