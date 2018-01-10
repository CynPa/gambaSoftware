package com.asinfo.as2.compras.procesos.servicio;

import com.asinfo.as2.entities.RecepcionProveedor;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioRecepcionProveedorRemote
{
  public abstract RecepcionProveedor cargarDetalle(RecepcionProveedor paramRecepcionProveedor);
  
  public abstract RecepcionProveedor buscarRecepcionPorNumero(String paramString);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedorRemote
 * JD-Core Version:    0.7.0.1
 */