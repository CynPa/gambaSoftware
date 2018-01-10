package com.asinfo.as2.financiero.activos.configuracion.servicio;

import com.asinfo.as2.entities.MotivoBajaActivo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMotivoBajaActivo
{
  public abstract void guardar(MotivoBajaActivo paramMotivoBajaActivo);
  
  public abstract void eliminar(MotivoBajaActivo paramMotivoBajaActivo);
  
  public abstract MotivoBajaActivo buscarPorId(int paramInt);
  
  public abstract List<MotivoBajaActivo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<MotivoBajaActivo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract MotivoBajaActivo cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioMotivoBajaActivo
 * JD-Core Version:    0.7.0.1
 */