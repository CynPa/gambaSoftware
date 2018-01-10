package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.Parroquia;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioParroquia
{
  public abstract List<Parroquia> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Parroquia> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap, int paramInt);
  
  public abstract Parroquia buscarPorId(int paramInt);
  
  public abstract Parroquia buscarPorNombre(String paramString, Organizacion paramOrganizacion);
  
  public abstract List<Parroquia> autocompletarParroquia(String paramString);
  
  public abstract void guardar(Parroquia paramParroquia);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<Parroquia> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void eliminar(Parroquia paramParroquia);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioParroquia
 * JD-Core Version:    0.7.0.1
 */