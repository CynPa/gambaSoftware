package com.asinfo.as2.seguridad;

import com.asinfo.as2.entities.seguridad.EntidadRol;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRol
{
  public abstract void guardar(EntidadRol paramEntidadRol);
  
  public abstract void eliminar(EntidadRol paramEntidadRol);
  
  public abstract EntidadRol buscarPorId(Integer paramInteger);
  
  public abstract EntidadRol cargarDetalle(int paramInt);
  
  public abstract List<EntidadRol> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract EntidadRol copiarRol(EntidadRol paramEntidadRol);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.ServicioRol
 * JD-Core Version:    0.7.0.1
 */