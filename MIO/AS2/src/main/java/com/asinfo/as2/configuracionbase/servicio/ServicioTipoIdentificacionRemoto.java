package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.TipoIdentificacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioTipoIdentificacionRemoto
{
  public abstract void guardar(TipoIdentificacion paramTipoIdentificacion);
  
  public abstract void eliminar(TipoIdentificacion paramTipoIdentificacion);
  
  public abstract TipoIdentificacion buscarPorId(Integer paramInteger);
  
  public abstract List<TipoIdentificacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoIdentificacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TipoIdentificacion buscarPorCodigo(String paramString);
  
  public abstract TipoIdentificacion buscarPorNombre(String paramString);
  
  public abstract TipoIdentificacion devuelvePrimerTipoIdentificacion();
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacionRemoto
 * JD-Core Version:    0.7.0.1
 */