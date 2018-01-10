package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.TipoTarifaSalarialHoraria;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoTarifaSalarialHoraria
{
  public abstract void guardar(TipoTarifaSalarialHoraria paramTipoTarifaSalarialHoraria);
  
  public abstract void eliminar(TipoTarifaSalarialHoraria paramTipoTarifaSalarialHoraria);
  
  public abstract TipoTarifaSalarialHoraria buscarPorId(int paramInt);
  
  public abstract List<TipoTarifaSalarialHoraria> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoTarifaSalarialHoraria> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoTarifaSalarialHoraria
 * JD-Core Version:    0.7.0.1
 */