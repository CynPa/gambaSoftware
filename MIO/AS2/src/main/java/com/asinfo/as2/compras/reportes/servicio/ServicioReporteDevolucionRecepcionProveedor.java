package com.asinfo.as2.compras.reportes.servicio;

import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteDevolucionRecepcionProveedor
{
  public abstract List getReporteDevolucionRecepcionProveedor(int paramInt, Empresa paramEmpresa, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto, Date paramDate1, Date paramDate2);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.reportes.servicio.ServicioReporteDevolucionRecepcionProveedor
 * JD-Core Version:    0.7.0.1
 */