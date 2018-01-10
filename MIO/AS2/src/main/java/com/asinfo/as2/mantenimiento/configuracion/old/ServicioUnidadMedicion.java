package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.UnidadMedicion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioUnidadMedicion
{
  public abstract void guardar(UnidadMedicion paramUnidadMedicion);
  
  public abstract void eliminar(UnidadMedicion paramUnidadMedicion);
  
  public abstract UnidadMedicion buscarPorId(int paramInt);
  
  public abstract List<UnidadMedicion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<UnidadMedicion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioUnidadMedicion
 * JD-Core Version:    0.7.0.1
 */