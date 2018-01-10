package com.asinfo.as2.compras.procesos.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.DetalleSolicitudCompra;
import com.asinfo.as2.entities.DetalleSolicitudCompraProveedor;
import com.asinfo.as2.entities.Empleado;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.ProductoUltimaCompra;
import com.asinfo.as2.entities.SolicitudCompra;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioSolicitudCompra
{
  public abstract void guardar(SolicitudCompra paramSolicitudCompra)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract List<SolicitudCompra> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void cerrarSolicitudCompra(SolicitudCompra paramSolicitudCompra)
    throws AS2Exception;
  
  public abstract SolicitudCompra cargarDetalle(int paramInt);
  
  public abstract List<Empleado> autocompletarEmpleado(String paramString, HashMap<String, String> paramHashMap);
  
  public abstract List<Producto> autocompletarProducto(String paramString, HashMap<String, String> paramHashMap);
  
  public abstract SolicitudCompra consolidarProducto(int paramInt, List<CategoriaProducto> paramList, List<Producto> paramList1, List<Empleado> paramList2, SolicitudCompra paramSolicitudCompra, Date paramDate1, Date paramDate2, List<SolicitudCompra> paramList3);
  
  public abstract void crearDetalleProveedor(SolicitudCompra paramSolicitudCompra, List<ProductoUltimaCompra> paramList, DetalleSolicitudCompra paramDetalleSolicitudCompra)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void crearPedidoProveedor(SolicitudCompra paramSolicitudCompra, List<DetalleSolicitudCompraProveedor> paramList, Bodega paramBodega)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract SolicitudCompra copiar(SolicitudCompra paramSolicitudCompra, boolean paramBoolean, Sucursal paramSucursal);
  
  public abstract List<Object[]> getReporteSolicitudCompra(int paramInt);
  
  public abstract List<Object[]> getReporteConsolidacionCompra(SolicitudCompra paramSolicitudCompra);
  
  public abstract List<Object[]> getReporteProductosConsolidados(int paramInt);
  
  public abstract void cerrarConsolidacion(int paramInt);
  
  public abstract void enviarEmail(SolicitudCompra paramSolicitudCompra);
  
  public abstract void enviarEmail(SolicitudCompra paramSolicitudCompra, String paramString);
  
  public abstract String getEmails(SolicitudCompra paramSolicitudCompra);
  
  public abstract List<SolicitudCompra> autocompletarSolicitudCompra(String paramString, HashMap<String, String> paramHashMap);
  
  public abstract void aprobar(SolicitudCompra paramSolicitudCompra);
  
  public abstract void aprobar(SolicitudCompra paramSolicitudCompra, boolean paramBoolean);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.ServicioSolicitudCompra
 * JD-Core Version:    0.7.0.1
 */