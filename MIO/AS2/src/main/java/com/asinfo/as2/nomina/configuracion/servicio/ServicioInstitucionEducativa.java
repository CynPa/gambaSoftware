package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.InstitucionEducativa;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioInstitucionEducativa
{
  public abstract void guardar(InstitucionEducativa paramInstitucionEducativa);
  
  public abstract void eliminar(InstitucionEducativa paramInstitucionEducativa);
  
  public abstract InstitucionEducativa buscarPorId(int paramInt);
  
  public abstract List<InstitucionEducativa> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<InstitucionEducativa> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract InstitucionEducativa cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioInstitucionEducativa
 * JD-Core Version:    0.7.0.1
 */