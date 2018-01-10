package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.ListaVerificacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioListaVerificacion
{
  public abstract void guardar(ListaVerificacion paramListaVerificacion);
  
  public abstract void eliminar(ListaVerificacion paramListaVerificacion);
  
  public abstract ListaVerificacion buscarPorId(int paramInt);
  
  public abstract List<ListaVerificacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ListaVerificacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract ListaVerificacion cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioListaVerificacion
 * JD-Core Version:    0.7.0.1
 */