package com.asinfo.as2.compras.reportes.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteRecepcionProveedor
{
  public abstract List getReporteRecepcionProveedor(int paramInt);
  
  public abstract List getReporteRecepcionProveedor(int paramInt1, int paramInt2, int paramInt3, Bodega paramBodega, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, Date paramDate1, Date paramDate2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.servicio.ServicioReporteRecepcionProveedor
 * JD-Core Version:    0.7.0.1
 */