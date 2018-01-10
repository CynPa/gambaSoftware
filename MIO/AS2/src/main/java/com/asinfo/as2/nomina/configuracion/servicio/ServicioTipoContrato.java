package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.TipoContrato;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoContrato
{
  public abstract void guardar(TipoContrato paramTipoContrato);
  
  public abstract void eliminar(TipoContrato paramTipoContrato);
  
  public abstract TipoContrato buscarPorId(int paramInt);
  
  public abstract List<TipoContrato> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoContrato> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TipoContrato cargarDetalle(int paramInt);
  
  public abstract TipoContrato buscarPorNombre(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoContrato
 * JD-Core Version:    0.7.0.1
 */