package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.UbicacionBodega;
import javax.ejb.Local;

@Local
public abstract interface ServicioUbicacionBodega
{
  public abstract void guardar(UbicacionBodega paramUbicacionBodega);
  
  public abstract void eliminar(UbicacionBodega paramUbicacionBodega);
  
  public abstract UbicacionBodega buscarPorId(Integer paramInteger);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioUbicacionBodega
 * JD-Core Version:    0.7.0.1
 */