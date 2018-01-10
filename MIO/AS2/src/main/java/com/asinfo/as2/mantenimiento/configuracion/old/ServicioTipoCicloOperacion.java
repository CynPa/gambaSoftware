package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.TipoCicloOperacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoCicloOperacion
{
  public abstract void guardar(TipoCicloOperacion paramTipoCicloOperacion);
  
  public abstract void eliminar(TipoCicloOperacion paramTipoCicloOperacion);
  
  public abstract TipoCicloOperacion buscarPorId(int paramInt);
  
  public abstract List<TipoCicloOperacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoCicloOperacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioTipoCicloOperacion
 * JD-Core Version:    0.7.0.1
 */