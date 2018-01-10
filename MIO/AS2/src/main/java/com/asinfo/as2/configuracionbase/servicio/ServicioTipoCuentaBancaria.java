package com.asinfo.as2.configuracionbase.servicio;

import com.asinfo.as2.entities.TipoCuentaBancaria;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioTipoCuentaBancaria
{
  public abstract void guardarTipoCuentaBancaria(TipoCuentaBancaria paramTipoCuentaBancaria);
  
  public abstract void eliminarTipoCuentaBancaria(TipoCuentaBancaria paramTipoCuentaBancaria);
  
  public abstract TipoCuentaBancaria buscarPorId(int paramInt);
  
  public abstract List<TipoCuentaBancaria> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<TipoCuentaBancaria> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract TipoCuentaBancaria buscarPorCodigo(String paramString)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.configuracionbase.servicio.ServicioTipoCuentaBancaria
 * JD-Core Version:    0.7.0.1
 */