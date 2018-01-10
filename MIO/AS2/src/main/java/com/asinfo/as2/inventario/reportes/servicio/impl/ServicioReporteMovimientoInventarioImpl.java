/*   1:    */ package com.asinfo.as2.inventario.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.reportes.inventario.ReporteMovimientoInventarioDao;
/*   4:    */ import com.asinfo.as2.entities.Bodega;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.DestinoCosto;
/*   7:    */ import com.asinfo.as2.entities.DimensionContable;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Lote;
/*  10:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.TomaFisica;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  18:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.List;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.ejb.Stateless;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class ServicioReporteMovimientoInventarioImpl
/*  26:    */   implements ServicioReporteMovimientoInventario
/*  27:    */ {
/*  28:    */   @EJB
/*  29:    */   private transient ReporteMovimientoInventarioDao reporteMovimientoInventarioDao;
/*  30:    */   @EJB
/*  31:    */   private transient ServicioProducto servicioProducto;
/*  32:    */   @EJB
/*  33:    */   private transient ServicioBodega servicioBodega;
/*  34:    */   
/*  35:    */   public List getReporteTransferenciaBodega(int idTransferenciaBodega)
/*  36:    */   {
/*  37: 54 */     return this.reporteMovimientoInventarioDao.getReporteTransferenciaBodega(idTransferenciaBodega);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List getReporteConsumoBodega(int idConsumoBodega)
/*  41:    */   {
/*  42: 65 */     return this.reporteMovimientoInventarioDao.getReporteConsumoBodega(idConsumoBodega);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List getReporteAjusteInventario(int idAjusteInventario)
/*  46:    */   {
/*  47: 71 */     return this.reporteMovimientoInventarioDao.getReporteAjusteInventario(idAjusteInventario);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public List getReporteConsumoBodegaDestinoCostoResumido(Date fechaDesde, Date fechaHasta, SubcategoriaProducto subcategoriaProducto, int idOrganizacion, Producto producto, DestinoCosto destinoCosto, List<CategoriaProducto> categoriaProducto, DimensionContable proyecto, Sucursal sucursal)
/*  51:    */   {
/*  52: 85 */     return this.reporteMovimientoInventarioDao.getReporteConsumoBodegaDestinoCostoResumido(fechaDesde, fechaHasta, subcategoriaProducto, idOrganizacion, producto, destinoCosto, categoriaProducto, proyecto, sucursal);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public List getReporteConsumoBodegaDestinoCostoMensual(Date fechaDesde, Date fechaHasta, int idOrganizacion, Producto producto, DestinoCosto destinoCosto, List<CategoriaProducto> categoriaProducto, SubcategoriaProducto subcategoriaProducto, DimensionContable proyecto, Sucursal sucursal, Documento documento)
/*  56:    */   {
/*  57: 94 */     return this.reporteMovimientoInventarioDao.getReporteConsumoBodegaDestinoCostoMensual(fechaDesde, fechaHasta, idOrganizacion, producto, destinoCosto, categoriaProducto, subcategoriaProducto, proyecto, sucursal, documento);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public List getReporteConsumoBodegaDestinoCostoDetallado(Date fechaDesde, Date fechaHasta, SubcategoriaProducto subcategoriaProducto, int idOrganizacion, Producto producto, DestinoCosto destinoCosto, List<CategoriaProducto> categoriaProducto, DimensionContable proyecto, Sucursal sucursal, boolean resumido, Documento documento)
/*  61:    */   {
/*  62:110 */     return this.reporteMovimientoInventarioDao.getReporteConsumoBodegaDestinoCostoDetallado(fechaDesde, fechaHasta, subcategoriaProducto, idOrganizacion, producto, destinoCosto, categoriaProducto, proyecto, sucursal, resumido, documento);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List getReporteAjusteInventario(Date fechaDesde, Date fechaHasta, int idDocumento, int idMotivoAjusteInventario, int idOrganizacion, int idSubcategoriaProducto, int idBodega)
/*  66:    */   {
/*  67:124 */     return this.reporteMovimientoInventarioDao.getReporteAjusteInventario(fechaDesde, fechaHasta, idDocumento, idMotivoAjusteInventario, idOrganizacion, idSubcategoriaProducto, idBodega);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List getReporteIngresoFabricacion(MovimientoInventario ingresofabricacion, boolean imprimirOF)
/*  71:    */   {
/*  72:131 */     return this.reporteMovimientoInventarioDao.getReporteIngresoFabricacion(ingresofabricacion, imprimirOF);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public List getReporteOrdenSalidaMaterial(int idOrdenSalidaMaterial, boolean devolucion, Date fechaDesde, Date fechaHasta, Producto producto, Lote lote, boolean indicadorSoloCerradas, boolean dosmConsumoDirecto)
/*  76:    */   {
/*  77:138 */     return this.reporteMovimientoInventarioDao.getReporteOrdenSalidaMaterial(idOrdenSalidaMaterial, devolucion, fechaDesde, fechaHasta, producto, lote, indicadorSoloCerradas, dosmConsumoDirecto);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public List getReporteOrdenSalidaMaterialByOrdenFabricacion(int idOrdenFabricacion)
/*  81:    */   {
/*  82:150 */     return this.reporteMovimientoInventarioDao.getReporteOrdenSalidaMaterialByOrdenFabricacion(idOrdenFabricacion);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public List<Object[]> getReporteTransferenciaInventario(int idOrganizacion, Documento documento, Date fechaDesde, Date fechaHasta, Bodega bodegaOrigen, Bodega bodegaDestino, SubcategoriaProducto subcategoriaProducto, Estado estado, DimensionContable proyecto, CategoriaProducto categoriaProducto)
/*  86:    */   {
/*  87:157 */     return this.reporteMovimientoInventarioDao.getReporteTransferenciaInventario(idOrganizacion, documento, fechaDesde, fechaHasta, bodegaOrigen, bodegaDestino, subcategoriaProducto, estado, proyecto, categoriaProducto);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public List<Object[]> reporteTomaFisica(TomaFisica tomaFisica)
/*  91:    */   {
/*  92:163 */     return this.reporteMovimientoInventarioDao.reporteTomaFisica(tomaFisica);
/*  93:    */   }
/*  94:    */   
/*  95:    */   public List<Object[]> getReporteCierreCirculo(int idOrganizacion, Date fechaDesde, Date fechaHasta, SubcategoriaProducto subcategoriaProducto)
/*  96:    */   {
/*  97:168 */     return this.reporteMovimientoInventarioDao.getReporteCierreCirculo(idOrganizacion, fechaDesde, fechaHasta, subcategoriaProducto);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public List getReporteConsumoBodegaDestinoCostoCategoria(Date fechaDesde, Date fechaHasta, SubcategoriaProducto subcategoriaProducto, int idOrganizacion, Producto producto, DestinoCosto destinoCosto, List<CategoriaProducto> list, DimensionContable proyecto, Sucursal sucursal, Documento documento)
/* 101:    */   {
/* 102:175 */     return this.reporteMovimientoInventarioDao.getReporteConsumoBodegaDestinoCostoCategoria(fechaDesde, fechaHasta, subcategoriaProducto, idOrganizacion, producto, destinoCosto, list, proyecto, sucursal, documento);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List<Object[]> getDatosImpresionEtiquetaLote(int idOrganizacion, int idDocumento, String numero, int idLote, int numeroAtributos)
/* 106:    */   {
/* 107:181 */     return this.reporteMovimientoInventarioDao.getDatosImpresionEtiquetaLote(idOrganizacion, idDocumento, numero, idLote, numeroAtributos);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public List<Object[]> getReporteOrdenSalidaMaterialConOrdenFabricacion(int idOrdenSalidaMaterial, boolean devolucion, Date fechaDesde, Date fechaHasta, Producto producto, Lote lote, boolean indicadorSoloCerradas, boolean dosmConsumoDirecto)
/* 111:    */   {
/* 112:187 */     return this.reporteMovimientoInventarioDao.getReporteOrdenSalidaMaterialConOrdenFabricacion(idOrdenSalidaMaterial, devolucion, fechaDesde, fechaHasta, producto, lote, indicadorSoloCerradas, dosmConsumoDirecto);
/* 113:    */   }
/* 114:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.servicio.impl.ServicioReporteMovimientoInventarioImpl
 * JD-Core Version:    0.7.0.1
 */