package com.asinfo.as2.inventario.reportes.servicio;

import com.asinfo.as2.clases.ReporteSaldoProducto;
import com.asinfo.as2.clases.ReporteStockValoradoResumido;
import com.asinfo.as2.entities.Atributo;
import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.Lote;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.Sucursal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteStockValoradoPersonalizado
{
  public abstract List<ReporteStockValoradoResumido> getReporteStockValoradoResumido(Sucursal paramSucursal, Bodega paramBodega, Date paramDate1, Date paramDate2, Atributo paramAtributo, String paramString, int paramInt, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, boolean paramBoolean);
  
  public abstract List<ReporteSaldoProducto> getReporteSaldoProducto(Sucursal paramSucursal, Bodega paramBodega, Date paramDate, Atributo paramAtributo, String paramString, int paramInt, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto);
  
  public abstract List<ReporteSaldoProducto> getReporteSaldoProducto(Sucursal paramSucursal, Bodega paramBodega, Date paramDate, Atributo paramAtributo1, String paramString1, int paramInt, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Atributo paramAtributo2, String paramString2);
  
  public abstract List<ReporteSaldoProducto> getReporteSaldoProductoPorLote(Sucursal paramSucursal, Bodega paramBodega, Date paramDate, Atributo paramAtributo, String paramString, int paramInt, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, boolean paramBoolean, Lote paramLote, Producto paramProducto);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.ServicioReporteStockValoradoPersonalizado
 * JD-Core Version:    0.7.0.1
 */