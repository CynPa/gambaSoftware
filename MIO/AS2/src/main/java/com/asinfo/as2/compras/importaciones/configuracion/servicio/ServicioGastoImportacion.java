package com.asinfo.as2.compras.importaciones.configuracion.servicio;

import com.asinfo.as2.entities.GastoImportacion;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioGastoImportacion
{
  public abstract void guardar(GastoImportacion paramGastoImportacion);
  
  public abstract void eliminar(GastoImportacion paramGastoImportacion);
  
  public abstract GastoImportacion buscarPorId(int paramInt);
  
  public abstract List<GastoImportacion> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<GastoImportacion> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract GastoImportacion cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.importaciones.configuracion.servicio.ServicioGastoImportacion
 * JD-Core Version:    0.7.0.1
 */