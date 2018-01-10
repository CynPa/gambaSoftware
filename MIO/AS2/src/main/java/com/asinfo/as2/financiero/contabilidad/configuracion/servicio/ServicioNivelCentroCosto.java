package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.CentroCosto;
import com.asinfo.as2.entities.NivelCentroCosto;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioNivelCentroCosto
{
  public abstract void guardar(NivelCentroCosto paramNivelCentroCosto);
  
  public abstract void eliminar(NivelCentroCosto paramNivelCentroCosto);
  
  public abstract NivelCentroCosto buscarPorId(int paramInt);
  
  public abstract String getMascara(CentroCosto paramCentroCosto);
  
  public abstract List<NivelCentroCosto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<NivelCentroCosto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioNivelCentroCosto
 * JD-Core Version:    0.7.0.1
 */