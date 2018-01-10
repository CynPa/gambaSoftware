package com.asinfo.as2.financiero.presupuesto.configuracion.servicio;

import com.asinfo.as2.entities.presupuesto.NivelPartidaPresupuestaria;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioNivelPartidaPresupuestaria
{
  public abstract void guardar(NivelPartidaPresupuestaria paramNivelPartidaPresupuestaria);
  
  public abstract void eliminar(NivelPartidaPresupuestaria paramNivelPartidaPresupuestaria);
  
  public abstract NivelPartidaPresupuestaria buscarPorId(int paramInt);
  
  public abstract List<NivelPartidaPresupuestaria> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<NivelPartidaPresupuestaria> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract NivelPartidaPresupuestaria cargarDetalle(int paramInt);
  
  public abstract String getMascara(int paramInt1, int paramInt2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioNivelPartidaPresupuestaria
 * JD-Core Version:    0.7.0.1
 */