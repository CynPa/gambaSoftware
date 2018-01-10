package com.asinfo.as2.seguridad;

import com.asinfo.as2.entities.Organizacion;
import com.asinfo.as2.entities.ProcesoOrganizacion;
import com.asinfo.as2.entities.seguridad.EntidadProceso;
import com.asinfo.as2.entities.seguridad.EntidadSistema;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioProceso
{
  public abstract void guardar(EntidadProceso paramEntidadProceso);
  
  public abstract void eliminar(EntidadProceso paramEntidadProceso);
  
  public abstract EntidadProceso buscarPorId(Integer paramInteger);
  
  public abstract List<EntidadProceso> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<EntidadProceso> obtenerLista(List<Integer> paramList, EntidadSistema paramEntidadSistema);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract List<EntidadProceso> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ProcesoOrganizacion> obtenerListaProcesoOrganizacion(EntidadSistema paramEntidadSistema, Organizacion paramOrganizacion);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.seguridad.ServicioProceso
 * JD-Core Version:    0.7.0.1
 */