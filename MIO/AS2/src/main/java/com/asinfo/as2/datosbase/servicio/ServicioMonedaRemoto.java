package com.asinfo.as2.datosbase.servicio;

import com.asinfo.as2.entities.Moneda;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioMonedaRemoto
{
  public abstract void guardar(Moneda paramMoneda);
  
  public abstract void eliminar(Moneda paramMoneda);
  
  public abstract Moneda buscarPorId(int paramInt);
  
  public abstract List<Moneda> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Moneda> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract Moneda obtenerPorCodigo(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.ServicioMonedaRemoto
 * JD-Core Version:    0.7.0.1
 */