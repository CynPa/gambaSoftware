package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.Provincia;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioProvincia
{
  public abstract void guardar(Provincia paramProvincia);
  
  public abstract void eliminar(Provincia paramProvincia);
  
  public abstract Provincia buscarPorId(Integer paramInteger);
  
  public abstract Provincia cargarDetalle(int paramInt);
  
  public abstract List<Provincia> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Provincia> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap, int paramInt);
  
  public abstract Provincia buscarPorNombre(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioProvincia
 * JD-Core Version:    0.7.0.1
 */