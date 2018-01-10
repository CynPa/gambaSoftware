package com.asinfo.as2.financiero.activos.configuracion.servicio;

import com.asinfo.as2.entities.UbicacionActivo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioUbicacionActivo
{
  public abstract void guardar(UbicacionActivo paramUbicacionActivo);
  
  public abstract void eliminar(UbicacionActivo paramUbicacionActivo);
  
  public abstract UbicacionActivo buscarPorId(int paramInt);
  
  public abstract List<UbicacionActivo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<UbicacionActivo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract UbicacionActivo cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioUbicacionActivo
 * JD-Core Version:    0.7.0.1
 */