package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.OrigenIngresos;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioOrigenIngresos
{
  public abstract void guardar(OrigenIngresos paramOrigenIngresos);
  
  public abstract void eliminar(OrigenIngresos paramOrigenIngresos);
  
  public abstract OrigenIngresos buscarPorId(Integer paramInteger);
  
  public abstract List<OrigenIngresos> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<OrigenIngresos> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioOrigenIngresos
 * JD-Core Version:    0.7.0.1
 */