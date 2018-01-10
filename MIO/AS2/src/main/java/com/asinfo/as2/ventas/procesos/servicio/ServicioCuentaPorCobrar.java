package com.asinfo.as2.ventas.procesos.servicio;

import com.asinfo.as2.entities.Empresa;
import javax.ejb.Local;

@Local
public abstract interface ServicioCuentaPorCobrar
{
  public abstract void actualizarCuentasPorCobrar(Empresa paramEmpresa);
  
  public abstract void actualizarCuentasPorPagar(Empresa paramEmpresa);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.ServicioCuentaPorCobrar
 * JD-Core Version:    0.7.0.1
 */