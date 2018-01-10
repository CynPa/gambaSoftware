package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.GrupoArticuloServicio;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioGrupoArticuloServicio
{
  public abstract void guardar(GrupoArticuloServicio paramGrupoArticuloServicio);
  
  public abstract void eliminar(GrupoArticuloServicio paramGrupoArticuloServicio);
  
  public abstract GrupoArticuloServicio buscarPorId(int paramInt);
  
  public abstract List<GrupoArticuloServicio> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<GrupoArticuloServicio> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioGrupoArticuloServicio
 * JD-Core Version:    0.7.0.1
 */