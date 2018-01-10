package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.NivelCuenta;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioNivelCuenta
{
  public abstract void guardar(NivelCuenta paramNivelCuenta);
  
  public abstract void eliminar(NivelCuenta paramNivelCuenta);
  
  public abstract NivelCuenta buscarPorId(Integer paramInteger);
  
  public abstract String getMascara(int paramInt1, int paramInt2);
  
  public abstract List<NivelCuenta> obtenerTodosOrdenadoDescendente();
  
  public abstract List<NivelCuenta> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<NivelCuenta> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int ContarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCuenta
 * JD-Core Version:    0.7.0.1
 */