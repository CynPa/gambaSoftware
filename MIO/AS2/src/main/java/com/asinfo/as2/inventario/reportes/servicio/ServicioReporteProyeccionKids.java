package com.asinfo.as2.inventario.reportes.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.ProductoMaterial;
import com.asinfo.as2.entities.clases.ProyeccionKid;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteProyeccionKids
{
  public abstract List<ProductoMaterial> getListaProductoMaterial(int paramInt, Producto paramProducto, Bodega paramBodega, Date paramDate1, Date paramDate2);
  
  public abstract List<ProyeccionKid> getListaProyeccionKids(int paramInt, Producto paramProducto, Bodega paramBodega, Date paramDate1, Date paramDate2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.ServicioReporteProyeccionKids
 * JD-Core Version:    0.7.0.1
 */