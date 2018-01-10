package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.TipoBodega;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoBodega
{
  public abstract void guardar(TipoBodega paramTipoBodega);
  
  public abstract void eliminar(TipoBodega paramTipoBodega);
  
  public abstract TipoBodega buscarPorId(Integer paramInteger);
  
  public abstract List<TipoBodega> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoBodega> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioTipoBodega
 * JD-Core Version:    0.7.0.1
 */