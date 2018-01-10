package com.asinfo.as2.seguridad;

import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.seguridad.EntidadPermiso;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPermiso
{
  public abstract void guardar(EntidadPermiso paramEntidadPermiso);
  
  public abstract void eliminar(EntidadPermiso paramEntidadPermiso);
  
  public abstract EntidadPermiso buscarPorId(Integer paramInteger);
  
  public abstract List<EntidadPermiso> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void copiarPermisos(Organizacion paramOrganizacion1, Organizacion paramOrganizacion2, boolean paramBoolean);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.ServicioPermiso
 * JD-Core Version:    0.7.0.1
 */