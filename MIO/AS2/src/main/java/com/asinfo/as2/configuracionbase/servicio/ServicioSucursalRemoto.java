package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioSucursalRemoto
{
  public abstract Sucursal guardar(Sucursal paramSucursal)
    throws ExcepcionAS2Financiero;
  
  public abstract void eliminar(Sucursal paramSucursal);
  
  public abstract List<Sucursal> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Sucursal> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Sucursal buscarPorId(Integer paramInteger);
  
  public abstract Sucursal cargarDetalle(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioSucursalRemoto
 * JD-Core Version:    0.7.0.1
 */