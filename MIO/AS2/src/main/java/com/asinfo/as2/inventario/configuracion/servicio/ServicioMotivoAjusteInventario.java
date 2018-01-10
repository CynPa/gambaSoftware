package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.MotivoAjusteInventario;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioMotivoAjusteInventario
{
  public abstract void guardar(MotivoAjusteInventario paramMotivoAjusteInventario);
  
  public abstract void eliminar(MotivoAjusteInventario paramMotivoAjusteInventario);
  
  public abstract MotivoAjusteInventario buscarPorId(int paramInt);
  
  public abstract List<MotivoAjusteInventario> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<MotivoAjusteInventario> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract MotivoAjusteInventario cargarDetalle(int paramInt);
  
  public abstract List<MotivoAjusteInventario> autoCompletarMotivoAjusteInventario(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioMotivoAjusteInventario
 * JD-Core Version:    0.7.0.1
 */