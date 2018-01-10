package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.HistoricoEmpleado;
import com.asinfo.as2.entities.PagoRol;
import com.asinfo.as2.entities.PagoRolEmpleadoRubro;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPagoRolEmpleadoRubro
{
  public abstract void guardar(List<PagoRolEmpleadoRubro> paramList, PagoRol paramPagoRol);
  
  public abstract List<PagoRolEmpleadoRubro> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<PagoRolEmpleadoRubro> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PagoRolEmpleadoRubro> getListaPagoRolEmpleadoRubroFiniquito(HistoricoEmpleado paramHistoricoEmpleado);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioPagoRolEmpleadoRubro
 * JD-Core Version:    0.7.0.1
 */