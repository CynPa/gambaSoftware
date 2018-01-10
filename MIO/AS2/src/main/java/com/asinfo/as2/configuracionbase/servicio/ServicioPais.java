package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.Pais;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPais
{
  public abstract void guardar(Pais paramPais);
  
  public abstract void eliminar(Pais paramPais);
  
  public abstract Pais buscarPorId(Integer paramInteger);
  
  public abstract Pais cargarDetalle(int paramInt);
  
  public abstract List<Pais> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Pais> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Pais buscarPorNombre(String paramString);
  
  public abstract List<Pais> obtenerPaises();
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioPais
 * JD-Core Version:    0.7.0.1
 */