package com.asinfo.as2.produccion.configuracion.servicio;

import com.asinfo.as2.entities.produccion.TarifaOperacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTarifaOperacion
{
  public abstract void guardar(TarifaOperacion paramTarifaOperacion);
  
  public abstract void eliminar(TarifaOperacion paramTarifaOperacion);
  
  public abstract TarifaOperacion buscarPorId(int paramInt);
  
  public abstract List<TarifaOperacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TarifaOperacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.configuracion.servicio.ServicioTarifaOperacion
 * JD-Core Version:    0.7.0.1
 */