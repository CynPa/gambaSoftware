package com.asinfo.as2.financiero.contabilidad.procesos.servicio;

import com.asinfo.as2.entities.DetalleAsiento;
import javax.ejb.Local;

@Local
public abstract interface ServicioDetalleAsiento
{
  public abstract void guardar(DetalleAsiento paramDetalleAsiento);
  
  public abstract void eliminar(DetalleAsiento paramDetalleAsiento);
  
  public abstract DetalleAsiento buscarPorId(Integer paramInteger);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioDetalleAsiento
 * JD-Core Version:    0.7.0.1
 */