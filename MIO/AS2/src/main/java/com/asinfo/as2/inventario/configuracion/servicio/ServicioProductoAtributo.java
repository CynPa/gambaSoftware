package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.ProductoAtributo;
import com.asinfo.as2.entities.ValorAtributo;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioProductoAtributo
{
  public abstract void guardar(ProductoAtributo paramProductoAtributo);
  
  public abstract void eliminar(ProductoAtributo paramProductoAtributo);
  
  public abstract ProductoAtributo buscarPorId(int paramInt);
  
  public abstract ProductoAtributo buscarPorAtributoProducto(int paramInt1, int paramInt2);
  
  public abstract List<ProductoAtributo> obtenerPorProducto(int paramInt);
  
  public abstract void actualizarProductoAtributo(ValorAtributo paramValorAtributo);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioProductoAtributo
 * JD-Core Version:    0.7.0.1
 */