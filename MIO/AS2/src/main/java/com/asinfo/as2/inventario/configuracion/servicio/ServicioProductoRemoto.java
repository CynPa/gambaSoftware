package com.asinfo.as2.inventario.configuracion.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.ProductoSustituto;
import com.asinfo.as2.entities.RangoImpuesto;
import com.asinfo.as2.entities.SaldoProducto;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Remote;

@Remote
public abstract interface ServicioProductoRemoto
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
  
  public abstract BigDecimal getSaldo(int paramInt1, int paramInt2, Date paramDate)
    throws ExcepcionAS2Inventario;
  
  public abstract List<SaldoProducto> obtenerSaldos(int paramInt, Date paramDate, Bodega paramBodega);
  
  public abstract List<ProductoSustituto> obtenerProductosSustitutos(int paramInt);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract Producto buscarPorCodigoYCargarCuentasContables(String paramString)
    throws ExcepcionAS2;
  
  public abstract void guardarPost(Producto paramProducto)
    throws ExcepcionAS2Inventario;
  
  public abstract Producto buscarProducto(Map<String, String> paramMap)
    throws ExcepcionAS2;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.ServicioProductoRemoto
 * JD-Core Version:    0.7.0.1
 */