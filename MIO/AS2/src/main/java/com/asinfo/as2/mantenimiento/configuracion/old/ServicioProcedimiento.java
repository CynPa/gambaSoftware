package com.asinfo.as2.mantenimiento.configuracion.old;

import com.asinfo.as2.entities.mantenimiento.old.Procedimiento;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioProcedimiento
{
  public abstract void guardar(Procedimiento paramProcedimiento);
  
  public abstract void eliminar(Procedimiento paramProcedimiento);
  
  public abstract Procedimiento buscarPorId(int paramInt);
  
  public abstract List<Procedimiento> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Procedimiento> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Procedimiento cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.old.ServicioProcedimiento
 * JD-Core Version:    0.7.0.1
 */