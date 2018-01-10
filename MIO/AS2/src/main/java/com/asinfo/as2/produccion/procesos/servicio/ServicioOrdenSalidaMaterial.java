package com.asinfo.as2.produccion.procesos.servicio;

import com.asinfo.as2.entities.Bodega;
import com.asinfo.as2.entities.DetalleOrdenSalidaMaterial;
import com.asinfo.as2.entities.LecturaBalanza;
import com.asinfo.as2.entities.MovimientoInventario;
import com.asinfo.as2.entities.OrdenFabricacionOrdenSalidaMaterial;
import com.asinfo.as2.entities.OrdenSalidaMaterial;
import com.asinfo.as2.entities.Producto;
import com.asinfo.as2.entities.ProductoMaterial;
import com.asinfo.as2.entities.Sucursal;
import com.asinfo.as2.entities.produccion.OrdenFabricacion;
import com.asinfo.as2.enumeraciones.DocumentoBase;
import com.asinfo.as2.enumeraciones.Estado;
import com.asinfo.as2.enumeraciones.TipoCicloProduccionEnum;
import com.asinfo.as2.excepciones.AS2Exception;
import com.asinfo.as2.excepciones.ExcepcionAS2;
import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
import com.asinfo.as2.seguridad.modelo.Usuario;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public abstract interface ServicioOrdenSalidaMaterial
{
  public abstract void guardar(OrdenSalidaMaterial paramOrdenSalidaMaterial)
    throws ExcepcionAS2Financiero, ExcepcionAS2, AS2Exception;
  
  public abstract void eliminar(OrdenSalidaMaterial paramOrdenSalidaMaterial);
  
  public abstract OrdenSalidaMaterial buscarPorId(int paramInt);
  
  public abstract List<OrdenSalidaMaterial> obtenerListaPorPagina(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract List<OrdenSalidaMaterial> obtenerListaCombo(String paramString, boolean paramBoolean, Map<String, String> paramMap);
  
  public abstract int contarPorCriterio(Map<String, String> paramMap);
  
  public abstract OrdenSalidaMaterial cargarDetalle(int paramInt);
  
  public abstract List<ProductoMaterial> getMateriales(Producto paramProducto, Date paramDate);
  
  public abstract MovimientoInventario generarConsumoBodega(OrdenSalidaMaterial paramOrdenSalidaMaterial, MovimientoInventario paramMovimientoInventario, boolean paramBoolean, DocumentoBase paramDocumentoBase)
    throws ExcepcionAS2;
  
  public abstract MovimientoInventario cerrarOrdenSalidaMaterial(OrdenSalidaMaterial paramOrdenSalidaMaterial)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract void confirmarDevolucion(OrdenSalidaMaterial paramOrdenSalidaMaterial)
    throws AS2Exception;
  
  public abstract List<OrdenSalidaMaterial> autocompletarOrdenSalidaMaterial(int paramInt, String paramString, Boolean paramBoolean);
  
  public abstract MovimientoInventario generarTransferenciaBodega(OrdenSalidaMaterial paramOrdenSalidaMaterial, MovimientoInventario paramMovimientoInventario)
    throws ExcepcionAS2;
  
  public abstract void recibirLecturaBalanza(LecturaBalanza paramLecturaBalanza)
    throws AS2Exception;
  
  public abstract List<LecturaBalanza> obtenerPesadasNoRecibidas(int paramInt, String paramString, List<Integer> paramList);
  
  public abstract List<OrdenFabricacion> getOrdenesNoFinalizadas(OrdenSalidaMaterial paramOrdenSalidaMaterial);
  
  public abstract void reabrirOrden(OrdenSalidaMaterial paramOrdenSalidaMaterial)
    throws ExcepcionAS2Financiero, AS2Exception;
  
  public abstract void crearMovimientoInventario(OrdenSalidaMaterial paramOrdenSalidaMaterial, Producto paramProducto)
    throws AS2Exception, ExcepcionAS2;
  
  public abstract void anularOrdenFabricacion(OrdenFabricacion paramOrdenFabricacion)
    throws AS2Exception;
  
  public abstract void agregarPeso(OrdenSalidaMaterial paramOrdenSalidaMaterial, LecturaBalanza paramLecturaBalanza, DetalleOrdenSalidaMaterial paramDetalleOrdenSalidaMaterial, boolean paramBoolean, List<DetalleOrdenSalidaMaterial> paramList, String paramString, List<LecturaBalanza> paramList1, Bodega paramBodega)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract DetalleOrdenSalidaMaterial crearDetalleOrdenSalida(OrdenSalidaMaterial paramOrdenSalidaMaterial, boolean paramBoolean, List<DetalleOrdenSalidaMaterial> paramList, DetalleOrdenSalidaMaterial paramDetalleOrdenSalidaMaterial);
  
  public abstract void totalizarCantidadPesada(DetalleOrdenSalidaMaterial paramDetalleOrdenSalidaMaterial, String paramString)
    throws AS2Exception;
  
  public abstract void actulizarBodega(DetalleOrdenSalidaMaterial paramDetalleOrdenSalidaMaterial, Bodega paramBodega, int paramInt);
  
  public abstract void editarDetalle(DetalleOrdenSalidaMaterial paramDetalleOrdenSalidaMaterial, String paramString)
    throws ExcepcionAS2;
  
  public abstract void guardarLogistica(OrdenSalidaMaterial paramOrdenSalidaMaterial);
  
  public abstract List<OrdenSalidaMaterial> autocompletarOrdenSalidaMaterial(int paramInt, String paramString, Boolean paramBoolean1, Boolean paramBoolean2);
  
  public abstract void aprobarDesaprobarOrdenSalidaMaterial(OrdenSalidaMaterial paramOrdenSalidaMaterial, boolean paramBoolean);
  
  public abstract OrdenSalidaMaterial copiarOrdenSalidaMaterial(int paramInt, Sucursal paramSucursal);
  
  public abstract void asignarBodegaDestinoTransferenciaDeBodega(MovimientoInventario paramMovimientoInventario);
  
  public abstract void agregarDetalleDesdeCodigoCabecera(OrdenSalidaMaterial paramOrdenSalidaMaterial, DetalleOrdenSalidaMaterial paramDetalleOrdenSalidaMaterial)
    throws ExcepcionAS2, AS2Exception;
  
  public abstract List<OrdenSalidaMaterial> getConsultaOrdenSalidaMaterial(Date paramDate, TipoCicloProduccionEnum paramTipoCicloProduccionEnum, Estado paramEstado, boolean paramBoolean);
  
  public abstract List<OrdenSalidaMaterial> getOrdenSalidaMaterialPorOrdenFabricacion(OrdenFabricacion paramOrdenFabricacion);
  
  public abstract List<OrdenFabricacionOrdenSalidaMaterial> getOrdenFabricacionOrdenSalidaMaterialPorOrdenSalidaMaterial(OrdenSalidaMaterial paramOrdenSalidaMaterial);
  
  public abstract List<OrdenSalidaMaterial> getOrdenSalidaMaterialPorAprobar(int paramInt, Usuario paramUsuario, Integer paramInteger, String paramString1, TipoCicloProduccionEnum paramTipoCicloProduccionEnum, String paramString2, Estado paramEstado, String paramString3)
    throws AS2Exception;
  
  public abstract void enlazarOrdenesDeFabricacion(DetalleOrdenSalidaMaterial paramDetalleOrdenSalidaMaterial, List<OrdenFabricacion> paramList);
  
  public abstract void eliminarDetalleOrdenSalidaMaterial(DetalleOrdenSalidaMaterial paramDetalleOrdenSalidaMaterial);
  
  public abstract List<String> ordenSalidaMaterialEnConsumoBodega(OrdenSalidaMaterial paramOrdenSalidaMaterial);
  
  public abstract void validarCantidadDesecho(DetalleOrdenSalidaMaterial paramDetalleOrdenSalidaMaterial, BigDecimal paramBigDecimal, String paramString, TipoCicloProduccionEnum paramTipoCicloProduccionEnum)
    throws AS2Exception;
}


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial
 * JD-Core Version:    0.7.0.1
 */