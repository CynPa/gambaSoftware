package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.Ciudad;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioCiudadRemoto
{
  public abstract List<Ciudad> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Ciudad> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap, int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioCiudadRemoto
 * JD-Core Version:    0.7.0.1
 */