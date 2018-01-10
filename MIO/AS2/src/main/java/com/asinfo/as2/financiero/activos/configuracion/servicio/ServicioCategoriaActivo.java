package com.asinfo.as2.financiero.activos.configuracion.servicio;

import com.asinfo.as2.entities.CategoriaActivo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCategoriaActivo
{
  public abstract void guardar(CategoriaActivo paramCategoriaActivo);
  
  public abstract void eliminar(CategoriaActivo paramCategoriaActivo);
  
  public abstract CategoriaActivo buscarPorId(int paramInt);
  
  public abstract List<CategoriaActivo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CategoriaActivo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CategoriaActivo cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCategoriaActivo
 * JD-Core Version:    0.7.0.1
 */