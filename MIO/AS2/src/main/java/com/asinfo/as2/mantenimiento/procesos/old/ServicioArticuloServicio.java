package com.asinfo.as2.mantenimiento.procesos.old;

import com.asinfo.as2.entities.mantenimiento.old.ArticuloServicio;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioArticuloServicio
{
  public abstract void guardar(ArticuloServicio paramArticuloServicio);
  
  public abstract void eliminar(ArticuloServicio paramArticuloServicio);
  
  public abstract ArticuloServicio buscarPorId(int paramInt);
  
  public abstract List<ArticuloServicio> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ArticuloServicio> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract ArticuloServicio cargarDetalle(int paramInt);
  
  public abstract List<ArticuloServicio> obtenerArticulosDisponibles();
  
  public abstract List<ArticuloServicio> autocompletarArticuloServicio(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.procesos.old.ServicioArticuloServicio
 * JD-Core Version:    0.7.0.1
 */