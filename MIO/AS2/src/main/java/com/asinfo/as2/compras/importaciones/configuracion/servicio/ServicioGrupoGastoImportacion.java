package com.asinfo.as2.compras.importaciones.configuracion.servicio;

import com.asinfo.as2.entities.GrupoGastoImportacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioGrupoGastoImportacion
{
  public abstract void guardar(GrupoGastoImportacion paramGrupoGastoImportacion);
  
  public abstract void eliminar(GrupoGastoImportacion paramGrupoGastoImportacion);
  
  public abstract GrupoGastoImportacion buscarPorId(int paramInt);
  
  public abstract List<GrupoGastoImportacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<GrupoGastoImportacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract GrupoGastoImportacion cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioGrupoGastoImportacion
 * JD-Core Version:    0.7.0.1
 */