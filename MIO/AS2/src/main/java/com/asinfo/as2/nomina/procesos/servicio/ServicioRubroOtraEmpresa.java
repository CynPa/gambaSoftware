package com.asinfo.as2.nomina.procesos.servicio;

import com.asinfo.as2.entities.RubroOtraEmpresa;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRubroOtraEmpresa
{
  public abstract void guardar(RubroOtraEmpresa paramRubroOtraEmpresa);
  
  public abstract void eliminar(RubroOtraEmpresa paramRubroOtraEmpresa);
  
  public abstract RubroOtraEmpresa buscarPorId(int paramInt);
  
  public abstract List<RubroOtraEmpresa> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<RubroOtraEmpresa> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract RubroOtraEmpresa cargarDetalle(int paramInt);
  
  public abstract RubroOtraEmpresa buscarPorNombre(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.procesos.servicio.ServicioRubroOtraEmpresa
 * JD-Core Version:    0.7.0.1
 */