package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.clases.ProductoEstadoImportacion;
import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.CategoriaProducto;
import com.asinfo.as2.entities.CuentaContable;
import com.asinfo.as2.entities.FiltroProducto;
import com.asinfo.as2.entities.InventarioProducto;
import com.asinfo.as2.entities.Lote;
import com.asinfo.as2.entities.MarcaProducto;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.ProductoAtributo;
import com.asinfo.as2.entities.ProductoBodega;
import com.asinfo.as2.entities.ProductoMaterial;
import com.asinfo.as2.entities.ProductoSustituto;
import com.asinfo.as2.entities.RangoImpuesto;
import com.asinfo.as2.entities.SaldoProducto;
import com.asinfo.as2.entities.SaldoProductoLote;
import com.asinfo.as2.entities.SubProducto;
import com.asinfo.as2.entities.SubcategoriaProducto;
import com.asinfo.as2.entities.Unidad;
import com.asinfo.as2.entities.UnidadManejo;
import com.asinfo.as2.entities.UnidadManejoProducto;
import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
import com.asinfo.as2.entities.produccion.OrdenFabricacion;
import com.asinfo.as2.entities.produccion.ProductoRutaFabricacionSucursal;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import com.asinfo.as2.utils.NodoArbol;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioProducto
{
  public abstract void guardar(Producto paramProducto)
    throws ExcepcionAS2Inventario;
  
  public abstract void eliminar(Producto paramProducto)
    throws ExcepcionAS2Inventario, ExcepcionAS2;
  
  public abstract Producto buscarPorId(int paramInt);
  
  public abstract Producto buscarPorCodigo(String paramString, int paramInt, DocumentoBase paramDocumentoBase)
    throws ExcepcionAS2;
  
  public abstract List<RangoImpuesto> impuestoProducto(Producto paramProducto, Date paramDate)
    throws ExcepcionAS2Inventario;
  
  public abstract List<Producto> obtenerProductos(String paramString, boolean paramBoolean, Map<String, String> paramMap)
    throws ExcepcionAS2Inventario;
  
  public abstract List<Producto> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Producto> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap)
    throws ExcepcionAS2Inventario;
  
  public abstract Producto cargaDetalle(int paramInt);
  
  public abstract Producto cargaDetalle(int paramInt, boolean paramBoolean);
  
  public abstract BigDecimal getSaldo(int paramInt1, int paramInt2, Date paramDate);
  
  public abstract BigDecimal getSaldo(int paramInt, Date paramDate);
  
  public abstract List<SaldoProducto> obtenerSaldos(int paramInt, Date paramDate, Bodega paramBodega);
  
  public abstract List<ProductoSustituto> obtenerProductosSustitutos(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Producto buscarPorCodigoYCargarCuentasContables(String paramString)
    throws ExcepcionAS2;
  
  public abstract BigDecimal convierteUnidad(Unidad paramUnidad1, Unidad paramUnidad2, int paramInt, BigDecimal paramBigDecimal)
    throws ExcepcionAS2Inventario;
  
  public abstract Producto buscarProducto(Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract CuentaContable obtenerCuentaContableGastoPorProducto(int paramInt);
  
  public abstract BigDecimal getCosto(int paramInt, Date paramDate, Bodega paramBodega);
  
  public abstract BigDecimal getCostoInicial(int paramInt, Date paramDate);
  
  public abstract BigDecimal getCostoInicial(int paramInt, Date paramDate, Bodega paramBodega);
  
  public abstract BigDecimal getSaldoInicial(int paramInt, Bodega paramBodega, Date paramDate);
  
  public abstract BigDecimal getSaldoInicial(int paramInt, Date paramDate);
  
  public abstract void cargaCostoEstandar(String paramString, InputStream paramInputStream, int paramInt)
    throws ExcepcionAS2Financiero, ExcepcionAS2;
  
  public abstract Producto getProductoCategoriaImpuesto(Producto paramProducto)
    throws ExcepcionAS2;
  
  public abstract Producto buscarPorAtributo(Map<String, String> paramMap)
    throws ExcepcionAS2;
  
  public abstract List<Producto> obtenerListaPos(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<ProductoEstadoImportacion> obtenerListaProductoEstadoImportacion(int paramInt, Date paramDate);
  
  public abstract List<ProductoAtributo> obtenerListaProductoAtributo(int paramInt);
  
  public abstract Producto buscarProductoPorCodigoProductoLote(String paramString, int paramInt, DocumentoBase paramDocumentoBase)
    throws ExcepcionAS2;
  
  public abstract BigDecimal getSaldoLote(int paramInt1, int paramInt2, int paramInt3, Date paramDate);
  
  public abstract void guardarListaMaterial(Producto paramProducto)
    throws AS2Exception;
  
  public abstract List<SubProducto> getListaSubproducto(Producto paramProducto);
  
  public abstract void validarMaterialSubproducto(Producto paramProducto1, Producto paramProducto2)
    throws AS2Exception;
  
  public abstract List<Producto> obtenerListaPorPagina(FiltroProducto paramFiltroProducto, int paramInt1, int paramInt2, String paramString1, boolean paramBoolean, Map<String, String> paramMap, String paramString2);
  
  public abstract int contarPorCriterio(FiltroProducto paramFiltroProducto, Map<String, String> paramMap);
  
  public abstract List getReporteSaldosMinimos(int paramInt1, int paramInt2, int paramInt3, Bodega paramBodega, List<Bodega> paramList, boolean paramBoolean, String paramString);
  
  public abstract void guardarProductoAgil(Producto paramProducto)
    throws ExcepcionAS2Inventario;
  
  public abstract List<ProductoBodega> getListaProductoBodegaVacios(Producto paramProducto);
  
  public abstract Producto cargarDetalleListaMaterial(Producto paramProducto);
  
  public abstract BigDecimal getCostoInicialMayorCero(InventarioProducto paramInventarioProducto, Bodega paramBodega);
  
  public abstract Producto buscarPorCualquierCodigo(String paramString, int paramInt)
    throws ExcepcionAS2;
  
  public abstract List<ProductoMaterial> getProductosCambioAngilProporcionProduccion(int paramInt, SubcategoriaProducto paramSubcategoriaProducto, CategoriaProducto paramCategoriaProducto, MarcaProducto paramMarcaProducto, Producto paramProducto);
  
  public abstract NodoArbol<Producto> obtenerArbolComponentes(Producto paramProducto);
  
  public abstract void actualizarCantidadesProducir(NodoArbol<Producto> paramNodoArbol, BigDecimal paramBigDecimal);
  
  public abstract List<UnidadManejo> obtenerListaUnidadManejoPorProducto(Producto paramProducto);
  
  public abstract UnidadManejoProducto getUnidadManejoProducto(Producto paramProducto, UnidadManejo paramUnidadManejo);
  
  public abstract Bodega obtenerBodegaTrabajoProducto(Producto paramProducto, Integer paramInteger);
  
  public abstract List<Producto> autocompletarProductos(Integer paramInteger, String paramString, Map<String, String> paramMap);
  
  public abstract List<Producto> autocompletarProductos(Integer paramInteger, String paramString);
  
  public abstract List<Producto> getListaProductos(int paramInt, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto, List<Producto> paramList);
  
  public abstract List<ProductoMaterial> getListaProductoMaterial(Producto paramProducto);
  
  public abstract void actualizarCantidadProductoMaterial(ProductoMaterial paramProductoMaterial);
  
  public abstract List<Producto> getListaMaterialesComunes(List<Producto> paramList);
  
  public abstract void procesarNuevosPorcentajes(List<Producto> paramList1, List<Producto> paramList2)
    throws AS2Exception;
  
  public abstract List<SaldoProducto> obtenerProductosConSaldoBodega(Bodega paramBodega, Date paramDate, boolean paramBoolean);
  
  public abstract List<SaldoProductoLote> obtenerProductosConSaldoBodegaLote(Bodega paramBodega, Date paramDate, Producto paramProducto, boolean paramBoolean);
  
  public abstract void agregarNuevosMateriales(List<Producto> paramList1, List<Producto> paramList2)
    throws AS2Exception;
  
  public abstract Producto cargarDetalleListaVariableCalidadProducto(Producto paramProducto);
  
  public abstract void guardarListaVariableCalidadProducto(Producto paramProducto)
    throws AS2Exception;
  
  public abstract List<OrdenFabricacion> buscarOrdenesFabricacionFormuladasPorProducto(Producto paramProducto);
  
  public abstract List<DetalleOrdenFabricacionMaterial> obtenerMaterialesDetalleOrdenFabricacionMaterial(OrdenFabricacion paramOrdenFabricacion, Boolean paramBoolean);
  
  public abstract DetalleOrdenFabricacionMaterial crearDetalle(OrdenFabricacion paramOrdenFabricacion, List<DetalleOrdenFabricacionMaterial> paramList, DetalleOrdenFabricacionMaterial paramDetalleOrdenFabricacionMaterial, Producto paramProducto, BigDecimal paramBigDecimal, SaldoProductoLote paramSaldoProductoLote)
    throws AS2Exception;
  
  public abstract List<DetalleOrdenFabricacionMaterial> copiarFormula(OrdenFabricacion paramOrdenFabricacion1, OrdenFabricacion paramOrdenFabricacion2, BigDecimal paramBigDecimal)
    throws AS2Exception;
  
  public abstract NodoArbol<Producto> obtenerArbolComponentes(Producto paramProducto, boolean paramBoolean);
  
  public abstract BigDecimal getInventarioComprometido(Producto paramProducto, Lote paramLote, Bodega paramBodega, Date paramDate, boolean paramBoolean);
  
  public abstract List<Producto> obtenerListaComboMultiple(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<Bodega> obtenerListaBodegasTrabajoProducto(Producto paramProducto, Integer paramInteger);
  
  public abstract BigDecimal getCostoUnitario(int paramInt, Date paramDate, Bodega paramBodega);
  
  public abstract Producto cargarDetalleOrdenFabricacion(int paramInt);
  
  public abstract List<ProductoRutaFabricacionSucursal> getListaProductoRutaFabricacionSucursal(int paramInt);
  
  public abstract List<SaldoProducto> obtenerProductosConSaldoBodega(Bodega paramBodega, Date paramDate, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract Producto cargarProductoConAtributoInstancia(int paramInt);
  
  public abstract Producto cargarDetalleListaMezclaProducto(Producto paramProducto);
  
  public abstract List<Producto> obtenerListaCombo(int paramInt, String paramString, CategoriaProducto paramCategoriaProducto, SubcategoriaProducto paramSubcategoriaProducto);
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto
 * JD-Core Version:    0.7.0.1
 */