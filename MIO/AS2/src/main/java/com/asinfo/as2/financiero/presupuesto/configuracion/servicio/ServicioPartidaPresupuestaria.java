package com.asinfo.as2.financiero.presupuesto.configuracion.servicio;

import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
import com.asinfo.as2.enumeraciones.GrupoCuenta;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPartidaPresupuestaria
{
  public abstract void guardar(PartidaPresupuestaria paramPartidaPresupuestaria)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(PartidaPresupuestaria paramPartidaPresupuestaria);
  
  public abstract PartidaPresupuestaria buscarPorId(int paramInt);
  
  public abstract List<PartidaPresupuestaria> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PartidaPresupuestaria> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract PartidaPresupuestaria cargarDetalle(int paramInt);
  
  public abstract List<PartidaPresupuestaria> buscarPorGrupoNivelPartidaPresupuestaria(GrupoCuenta paramGrupoCuenta, int paramInt1, int paramInt2);
  
  public abstract List<Object[]> getReportePartidaPresupuestaria(int paramInt1, int paramInt2);
  
  public abstract List<PartidaPresupuestaria> obtenerPartidaPresupuestariaPorUsuario(int paramInt1, int paramInt2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioPartidaPresupuestaria
 * JD-Core Version:    0.7.0.1
 */