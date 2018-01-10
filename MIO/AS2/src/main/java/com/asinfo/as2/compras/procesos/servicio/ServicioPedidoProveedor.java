package com.asinfo.as2.compras.procesos.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.CategoriaEmpresa;
import com.asinfo.as2.entities.DetallePedidoProveedor;
import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.PedidoProveedor;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.RecepcionProveedor;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import com.asinfo.as2.seguridad.modelo.Usuario;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioPedidoProveedor
{
  public abstract void guardar(PedidoProveedor paramPedidoProveedor)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void actualizarEstado(Integer paramInteger, Estado paramEstado);
  
  public abstract PedidoProveedor buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract PedidoProveedor cargarDetalle(Integer paramInteger)
    throws ExcepcionAS2Compras;
  
  public abstract void totalizar(PedidoProveedor paramPedidoProveedor)
    throws ExcepcionAS2Compras;
  
  public abstract void totalizarImpuesto(PedidoProveedor paramPedidoProveedor)
    throws ExcepcionAS2Compras;
  
  public abstract List<PedidoProveedor> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract List<PedidoProveedor> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<PedidoProveedor> listaPedidosPorFacturar(int paramInt);
  
  public abstract List<PedidoProveedor> listaPedidosPorRecibir(int paramInt);
  
  public abstract List getReportePedidoProveedor(int paramInt)
    throws ExcepcionAS2;
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract void anular(PedidoProveedor paramPedidoProveedor)
    throws ExcepcionAS2Compras, ExcepcionAS2Financiero;
  
  public abstract void esEditable(PedidoProveedor paramPedidoProveedor)
    throws ExcepcionAS2Compras, ExcepcionAS2Financiero;
  
  public abstract void abrirPedidoProveedor(int paramInt);
  
  public abstract List<DetallePedidoProveedor> obtenerListaDetallePedidoPorRecibir(int paramInt, boolean paramBoolean);
  
  public abstract List<DetallePedidoProveedor> obtenerListaDetallePedidoPorRecibir(int paramInt);
  
  public abstract void cierreAutomatico(int paramInt);
  
  public abstract boolean esPedidoPorRecibir(int paramInt);
  
  public abstract List<DetallePedidoProveedor> obtenerListaDetallePedidoPorFacturar(int paramInt);
  
  public abstract void cargarDetallePedidoProveedorExcel(PedidoProveedor paramPedidoProveedor, String paramString, InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2;
  
  public abstract void obtenerImpuestosProductos(Producto paramProducto, DetallePedidoProveedor paramDetallePedidoProveedor)
    throws ExcepcionAS2Inventario;
  
  public abstract void cambiarEstadoPedidos(List<PedidoProveedor> paramList1, Estado paramEstado, String paramString, Date paramDate, List<PedidoProveedor> paramList2)
    throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception;
  
  public abstract List<Object[]> getResumenAnualAprobadasYPorAprobar(int paramInt1, int paramInt2, int paramInt3, Documento paramDocumento);
  
  public abstract List<FacturaProveedor> pedidoEnFacturaProveedor(int paramInt)
    throws ExcepcionAS2Compras;
  
  public abstract List<RecepcionProveedor> pedidoEnRecepcionProveedor(int paramInt)
    throws ExcepcionAS2Compras;
  
  public abstract PedidoProveedor copiarPedidoProveedor(PedidoProveedor paramPedidoProveedor)
    throws ExcepcionAS2Financiero;
  
  public abstract List<PedidoProveedor> cargarPedidosPorAprobar(int paramInt, Sucursal paramSucursal, Documento paramDocumento, Empresa paramEmpresa, Date paramDate1, Date paramDate2, boolean paramBoolean1, Usuario paramUsuario, boolean paramBoolean2, CategoriaEmpresa paramCategoriaEmpresa)
    throws AS2Exception;
  
  public abstract DetallePedidoProveedor cargarDetallePedidoProveedor(DetallePedidoProveedor paramDetallePedidoProveedor);
  
  public abstract List<DetallePedidoProveedor> obtenerListaPorPaginaDetallePedidoProveedor(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterioDetallePedidoProveedor(Map<String, String> paramMap);
  
  public abstract void enviarEmail(PedidoProveedor paramPedidoProveedor, String paramString);
  
  public abstract void cargaDatosProveedor(PedidoProveedor paramPedidoProveedor);
  
  public abstract void actualizarAtributoEntidad(PedidoProveedor paramPedidoProveedor, HashMap<String, Object> paramHashMap);
  
  public abstract PedidoProveedor buscarPorNumero(Integer paramInteger, String paramString1, String paramString2)
    throws AS2Exception;
  
  public abstract void aprobarPorProducto(PedidoProveedor paramPedidoProveedor, Producto paramProducto);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.ServicioPedidoProveedor
 * JD-Core Version:    0.7.0.1
 */