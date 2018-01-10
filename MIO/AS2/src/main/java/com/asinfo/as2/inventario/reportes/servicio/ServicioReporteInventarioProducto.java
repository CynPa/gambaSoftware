package com.asinfo.as2.inventario.reportes.servicio;

import com.asinfo.as2.clases.ReporteInventarioProducto;
import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.InventarioProducto;
import com.asinfo.as2.entities.Lote;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface ServicioReporteInventarioProducto
{
  public abstract List<InventarioProducto> obtenerMovimientos(Integer paramInteger, Producto paramProducto, Bodega paramBodega, Date paramDate1, Date paramDate2, int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<ReporteInventarioProducto> obtenerMovimientosInventarioProducto(int paramInt1, List<Producto> paramList, Bodega paramBodega, Date paramDate1, Date paramDate2, int paramInt2, Lote paramLote, List<Bodega> paramList1, int paramInt3)
    throws ExcepcionAS2;
  
  public abstract List getReporteListaMaterial(int paramInt);
  
  public abstract List getReporteListaMaterialSubproductos(int paramInt);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.ServicioReporteInventarioProducto
 * JD-Core Version:    0.7.0.1
 */