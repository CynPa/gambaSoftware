package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.Ciudad;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCiudad
{
  public abstract List<Ciudad> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Ciudad> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap, int paramInt);
  
  public abstract Ciudad buscarPorId(int paramInt);
  
  public abstract Ciudad buscarPorNombre(String paramString);
  
  public abstract List<Ciudad> autocompletarCiudad(String paramString);
  
  public abstract void guardar(Ciudad paramCiudad);
  
  public abstract List<Ciudad> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Ciudad getCiudadDeSucursal(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioCiudad
 * JD-Core Version:    0.7.0.1
 */