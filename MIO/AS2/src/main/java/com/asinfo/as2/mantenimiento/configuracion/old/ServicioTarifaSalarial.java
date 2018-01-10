package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.TarifaSalarial;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTarifaSalarial
{
  public abstract void guardar(TarifaSalarial paramTarifaSalarial);
  
  public abstract void eliminar(TarifaSalarial paramTarifaSalarial);
  
  public abstract TarifaSalarial buscarPorId(int paramInt);
  
  public abstract List<TarifaSalarial> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TarifaSalarial> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioTarifaSalarial
 * JD-Core Version:    0.7.0.1
 */