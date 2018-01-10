package com.asinfo.as2.compras.procesos.servicio;

import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.CuentaContable;
import com.asinfo.as2.entities.DetalleRecepcionProveedor;
import com.asinfo.as2.entities.Documento;
import com.asinfo.as2.entities.Empresa;
import com.asinfo.as2.entities.FacturaProveedor;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.RecepcionProveedor;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.enumeraciones.TipoOrganizacion;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioRecepcionProveedor
{
  public abstract void guardar(RecepcionProveedor paramRecepcionProveedor)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(RecepcionProveedor paramRecepcionProveedor)
    throws ExcepcionAS2;
  
  public abstract RecepcionProveedor buscarPorId(Integer paramInteger)
    throws ExcepcionAS2;
  
  public abstract RecepcionProveedor cargarDetalle(RecepcionProveedor paramRecepcionProveedor);
  
  public abstract List<RecepcionProveedor> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract void contabilizar(RecepcionProveedor paramRecepcionProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void esEditable(RecepcionProveedor paramRecepcionProveedor)
    throws ExcepcionAS2Financiero, ExcepcionAS2Compras;
  
  public abstract void anular(RecepcionProveedor paramRecepcionProveedor, Date paramDate)
    throws ExcepcionAS2Financiero, ExcepcionAS2Compras, ExcepcionAS2Inventario, ExcepcionAS2, AS2Exception;
  
  public abstract void actualizarEstado(Integer paramInteger, Estado paramEstado);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract RecepcionProveedor cargarDetalleAFacturar(int paramInt);
  
  public abstract RecepcionProveedor buscarPorFacturaProveedor(int paramInt);
  
  public abstract void cargarDetalleRecepcionArchivoTexto(TipoOrganizacion paramTipoOrganizacion, RecepcionProveedor paramRecepcionProveedor, InputStream paramInputStream, Bodega paramBodega, Producto paramProducto, Empresa paramEmpresa, DetalleRecepcionProveedor paramDetalleRecepcionProveedor)
    throws ExcepcionAS2;
  
  public abstract void cargarRecepcionDesdeNumeroLotes(TipoOrganizacion paramTipoOrganizacion, RecepcionProveedor paramRecepcionProveedor, Bodega paramBodega, Producto paramProducto, BigDecimal paramBigDecimal, Integer paramInteger, DetalleRecepcionProveedor paramDetalleRecepcionProveedor, boolean paramBoolean)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract List<Object[]> getDatosImpresionEtiquetaLote(int paramInt1, Documento paramDocumento, String paramString1, String paramString2, String paramString3, boolean paramBoolean, int paramInt2);
  
  public abstract void guardarDetalleRecepcionProveedor(RecepcionProveedor paramRecepcionProveedor, BigDecimal paramBigDecimal, CuentaContable paramCuentaContable)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract List<RecepcionProveedor> buscarRecepcionesNoFacturadasPorProveedor(Integer paramInteger);
  
  public abstract void validacionCantidadRecepcion(DetalleRecepcionProveedor paramDetalleRecepcionProveedor, RecepcionProveedor paramRecepcionProveedor, boolean paramBoolean)
    throws AS2Exception;
  
  public abstract void agregarInventariosProducto(RecepcionProveedor paramRecepcionProveedor);
  
  public abstract void guardar(RecepcionProveedor paramRecepcionProveedor, boolean paramBoolean1, boolean paramBoolean2)
    throws ExcepcionAS2, ExcepcionAS2Inventario, AS2Exception;
  
  public abstract List<RecepcionProveedor> autocompletarRecepcionesNoFacturadasPorProveedor(Integer paramInteger, String paramString);
  
  public abstract void contabilizar(RecepcionProveedor paramRecepcionProveedor, FacturaProveedor paramFacturaProveedor)
    throws ExcepcionAS2, ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void actualizarAtributoEntidad(RecepcionProveedor paramRecepcionProveedor, HashMap<String, Object> paramHashMap);
  
  public abstract List<RecepcionProveedor> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Object[]> getDatosImpresionEtiqueta(int paramInt, Producto paramProducto, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, Bodega paramBodega, boolean paramBoolean);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.servicio.ServicioRecepcionProveedor
 * JD-Core Version:    0.7.0.1
 */