package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.CuentaBancaria;
import com.asinfo.as2.entities.Empresa;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioCuentaBancaria
{
  public abstract void guardar(CuentaBancaria paramCuentaBancaria);
  
  public abstract void eliminar(CuentaBancaria paramCuentaBancaria);
  
  public abstract CuentaBancaria buscarPorId(int paramInt);
  
  public abstract List<CuentaBancaria> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<CuentaBancaria> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract CuentaBancaria cargarDetalle(int paramInt);
  
  public abstract List<CuentaBancaria> listaCuentaBancariaPorCliente(Empresa paramEmpresa);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaBancaria
 * JD-Core Version:    0.7.0.1
 */