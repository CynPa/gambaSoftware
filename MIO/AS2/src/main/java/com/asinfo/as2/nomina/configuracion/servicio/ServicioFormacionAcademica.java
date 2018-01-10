package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.FormacionAcademica;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioFormacionAcademica
{
  public abstract void guardar(FormacionAcademica paramFormacionAcademica);
  
  public abstract void eliminar(FormacionAcademica paramFormacionAcademica);
  
  public abstract FormacionAcademica buscarPorId(int paramInt);
  
  public abstract List<FormacionAcademica> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<FormacionAcademica> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract FormacionAcademica cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioFormacionAcademica
 * JD-Core Version:    0.7.0.1
 */