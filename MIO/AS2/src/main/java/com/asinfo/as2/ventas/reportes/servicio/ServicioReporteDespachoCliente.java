package com.asinfo.as2.ventas.reportes.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.Subempresa;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteDespachoCliente
{
  public abstract List getReporteDespachoDetallado(int paramInt1, Date paramDate1, Date paramDate2, int paramInt2, int paramInt3, Bodega paramBodega, Subempresa paramSubempresa, int paramInt4, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Producto paramProducto);
  
  public abstract List getReporteDespachoPrefacturaFactura(Date paramDate1, Date paramDate2, Empresa paramEmpresa, boolean paramBoolean);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.servicio.ServicioReporteDespachoCliente
 * JD-Core Version:    0.7.0.1
 */