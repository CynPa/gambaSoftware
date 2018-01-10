package com.asinfo.as2.compras.importaciones.configuracion.servicio;

import com.asinfo.as2.entities.TipoTramiteImportacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoTramiteImportacion
{
  public abstract void guardar(TipoTramiteImportacion paramTipoTramiteImportacion);
  
  public abstract void eliminar(TipoTramiteImportacion paramTipoTramiteImportacion);
  
  public abstract TipoTramiteImportacion buscarPorId(int paramInt);
  
  public abstract List<TipoTramiteImportacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoTramiteImportacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TipoTramiteImportacion cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioTipoTramiteImportacion
 * JD-Core Version:    0.7.0.1
 */