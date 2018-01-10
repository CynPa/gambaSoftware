package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.TipoDiscapacidad;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoDiscapacidad
{
  public abstract void guardar(TipoDiscapacidad paramTipoDiscapacidad);
  
  public abstract void eliminar(TipoDiscapacidad paramTipoDiscapacidad);
  
  public abstract TipoDiscapacidad buscarPorId(int paramInt);
  
  public abstract List<TipoDiscapacidad> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoDiscapacidad> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TipoDiscapacidad cargarDetalle(int paramInt);
  
  public abstract TipoDiscapacidad buscarPorNombre(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoDiscapacidad
 * JD-Core Version:    0.7.0.1
 */