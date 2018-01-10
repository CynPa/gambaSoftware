package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.Criticidad;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCriticidad
{
  public abstract void guardar(Criticidad paramCriticidad);
  
  public abstract void eliminar(Criticidad paramCriticidad);
  
  public abstract Criticidad buscarPorId(int paramInt);
  
  public abstract List<Criticidad> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Criticidad> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioCriticidad
 * JD-Core Version:    0.7.0.1
 */