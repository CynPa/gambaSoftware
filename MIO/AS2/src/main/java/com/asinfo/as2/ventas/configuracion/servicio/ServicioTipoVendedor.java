package com.asinfo.as2.ventas.configuracion.servicio;

import com.asinfo.as2.entities.TipoVendedor;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoVendedor
{
  public abstract void guardar(TipoVendedor paramTipoVendedor);
  
  public abstract void eliminar(TipoVendedor paramTipoVendedor);
  
  public abstract TipoVendedor buscarPorId(int paramInt);
  
  public abstract List<TipoVendedor> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoVendedor> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.configuracion.servicio.ServicioTipoVendedor
 * JD-Core Version:    0.7.0.1
 */