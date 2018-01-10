package com.asinfo.as2.seguridad;

import com.asinfo.as2.entities.seguridad.EntidadAccion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioAccion
{
  public abstract void guardar(EntidadAccion paramEntidadAccion);
  
  public abstract void eliminar(EntidadAccion paramEntidadAccion);
  
  public abstract EntidadAccion buscarPorId(Integer paramInteger);
  
  public abstract List<EntidadAccion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.ServicioAccion
 * JD-Core Version:    0.7.0.1
 */