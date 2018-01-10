package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.CategoriaArticuloServicio;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCategoriaArticuloServicio
{
  public abstract void guardar(CategoriaArticuloServicio paramCategoriaArticuloServicio);
  
  public abstract void eliminar(CategoriaArticuloServicio paramCategoriaArticuloServicio);
  
  public abstract CategoriaArticuloServicio buscarPorId(int paramInt);
  
  public abstract List<CategoriaArticuloServicio> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CategoriaArticuloServicio> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioCategoriaArticuloServicio
 * JD-Core Version:    0.7.0.1
 */