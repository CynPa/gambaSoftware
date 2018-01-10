package com.asinfo.as2.compras.configuracion.servicio.impl;

import com.asinfo.as2.entities.TipoOperacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoOperacion
{
  public abstract void guardar(TipoOperacion paramTipoOperacion);
  
  public abstract void eliminar(TipoOperacion paramTipoOperacion);
  
  public abstract TipoOperacion buscarPorId(int paramInt);
  
  public abstract List<TipoOperacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoOperacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TipoOperacion cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.configuracion.servicio.impl.ServicioTipoOperacion
 * JD-Core Version:    0.7.0.1
 */