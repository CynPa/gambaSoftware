package com.asinfo.as2.financiero.SRI.configuracion.servicio;

import com.asinfo.as2.entities.CategoriaRetencion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCategoriaRetencion
{
  public abstract void guardar(CategoriaRetencion paramCategoriaRetencion);
  
  public abstract void eliminar(CategoriaRetencion paramCategoriaRetencion);
  
  public abstract CategoriaRetencion buscarPorId(int paramInt);
  
  public abstract List<CategoriaRetencion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CategoriaRetencion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CategoriaRetencion cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.SRI.configuracion.servicio.ServicioCategoriaRetencion
 * JD-Core Version:    0.7.0.1
 */