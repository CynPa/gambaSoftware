package com.asinfo.as2.webservices.inventario.configuracion;

import com.asinfo.pos.model.CategoriaProducto;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public abstract interface ServicioWebCategoriaProducto
{
  @WebMethod
  public abstract List<CategoriaProducto> getListaCategoriaProducto(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.webservices.inventario.configuracion.ServicioWebCategoriaProducto
 * JD-Core Version:    0.7.0.1
 */