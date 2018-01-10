package com.asinfo.as2.seguridad;

import com.asinfo.as2.entities.seguridad.EntidadSistema;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioSistema
{
  public abstract void guardar(EntidadSistema paramEntidadSistema);
  
  public abstract void eliminar(EntidadSistema paramEntidadSistema);
  
  public abstract EntidadSistema buscarPorId(Integer paramInteger);
  
  public abstract List<EntidadSistema> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract EntidadSistema buscarPorNombre(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.ServicioSistema
 * JD-Core Version:    0.7.0.1
 */