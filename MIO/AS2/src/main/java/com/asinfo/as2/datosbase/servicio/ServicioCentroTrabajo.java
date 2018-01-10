package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.CentroTrabajo;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCentroTrabajo
{
  public abstract void guardar(CentroTrabajo paramCentroTrabajo);
  
  public abstract void eliminar(CentroTrabajo paramCentroTrabajo);
  
  public abstract CentroTrabajo buscarPorId(int paramInt);
  
  public abstract List<CentroTrabajo> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CentroTrabajo> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CentroTrabajo cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioCentroTrabajo
 * JD-Core Version:    0.7.0.1
 */