package com.asinfo.as2.webservices.inventario.producto;

import com.asinfo.pos.model.Producto;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebProducto
{
  @WebMethod
  public abstract List<Producto> getListaProducto();
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.inventario.producto.ServicioWebProducto
 * JD-Core Version:    0.7.0.1
 */