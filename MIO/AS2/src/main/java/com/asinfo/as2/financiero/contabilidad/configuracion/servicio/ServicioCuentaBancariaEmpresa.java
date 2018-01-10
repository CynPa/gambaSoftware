package com.asinfo.as2.financiero.contabilidad.configuracion.servicio;

import com.asinfo.as2.entities.CuentaBancariaEmpresa;
import javax.ejb.Local;

@Local
public abstract interface ServicioCuentaBancariaEmpresa
{
  public abstract void guardarCuentaBancariaEmpresa(CuentaBancariaEmpresa paramCuentaBancariaEmpresa);
  
  public abstract void eliminarCuentaBancariaEmpresa(CuentaBancariaEmpresa paramCuentaBancariaEmpresa);
  
  public abstract CuentaBancariaEmpresa buscarPorId(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaBancariaEmpresa
 * JD-Core Version:    0.7.0.1
 */