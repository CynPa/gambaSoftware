package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.Propina;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPropina
{
  public abstract void guardar(Propina paramPropina);
  
  public abstract void eliminar(Propina paramPropina);
  
  public abstract Propina buscarPorId(int paramInt);
  
  public abstract List<Propina> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Propina> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Propina obtenerPorCodigo(String paramString);
  
  public abstract Propina cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioPropina
 * JD-Core Version:    0.7.0.1
 */