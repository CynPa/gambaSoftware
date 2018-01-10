package com.asinfo.as2.nomina.configuracion.servicio;

import com.asinfo.as2.entities.EstadoCivil;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioEstadoCivil
{
  public abstract void guardar(EstadoCivil paramEstadoCivil);
  
  public abstract void eliminar(EstadoCivil paramEstadoCivil);
  
  public abstract EstadoCivil buscarPorId(int paramInt);
  
  public abstract List<EstadoCivil> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<EstadoCivil> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract EstadoCivil cargarDetalle(int paramInt);
  
  public abstract EstadoCivil buscarPorNombre(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.ServicioEstadoCivil
 * JD-Core Version:    0.7.0.1
 */