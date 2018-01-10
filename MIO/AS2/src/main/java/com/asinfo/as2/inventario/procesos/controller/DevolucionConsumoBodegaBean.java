/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   5:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   6:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   7:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*   8:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidad;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  14:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  15:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioDestinoCosto;
/*  16:    */ import com.asinfo.as2.mantenimiento.procesos.servicio.ServicioOrdenTrabajoMantenimiento;
/*  17:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioOrdenSalidaMaterial;
/*  18:    */ import java.math.BigDecimal;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class DevolucionConsumoBodegaBean
/*  28:    */   extends ConsumoBodegaBean
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 5617445651812644617L;
/*  31:    */   @EJB
/*  32:    */   private ServicioDocumento servicioDocumento;
/*  33:    */   @EJB
/*  34:    */   private ServicioProducto servicioProducto;
/*  35:    */   @EJB
/*  36:    */   private ServicioDestinoCosto servicioDestinoCosto;
/*  37:    */   @EJB
/*  38:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  39:    */   @EJB
/*  40:    */   private ServicioUnidad servicioUnidad;
/*  41:    */   @EJB
/*  42:    */   private ServicioLote servicioLote;
/*  43:    */   @EJB
/*  44:    */   private ServicioOrdenSalidaMaterial servicioOrdenSalidaMaterial;
/*  45:    */   @EJB
/*  46:    */   private ServicioOrdenTrabajoMantenimiento servicioOrdenTrabajoMantenimiento;
/*  47:    */   
/*  48:    */   public DocumentoBase getDocumentoBase()
/*  49:    */   {
/*  50: 59 */     return DocumentoBase.DEVOLUCION_CONSUMO_BODEGA;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public List<MovimientoInventario> autocompletarConsumoBodega(String consulta)
/*  54:    */   {
/*  55: 63 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/*  56: 64 */     filtros.put("documento.documentoBase", DocumentoBase.CONSUMO_BODEGA.toString());
/*  57: 65 */     filtros.put("estado", Estado.ELABORADO.toString());
/*  58: 66 */     filtros.put("numero", "%" + consulta + "%");
/*  59: 67 */     List<MovimientoInventario> lista = this.servicioMovimientoInventario.obtenerListaPorPagina(0, 100, "fecha", false, filtros);
/*  60: 68 */     return lista;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void actualizarConsumoBodega()
/*  64:    */   {
/*  65:    */     try
/*  66:    */     {
/*  67: 73 */       this.servicioMovimientoInventario.cargarDetallesConsumoBodegaADevolver(getMovimientoInventario());
/*  68:    */     }
/*  69:    */     catch (ExcepcionAS2 e)
/*  70:    */     {
/*  71: 75 */       e.printStackTrace();
/*  72: 76 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void seleccionarTodos()
/*  77:    */   {
/*  78: 81 */     for (DetalleMovimientoInventario detalle : getListaDetalleMovimientoInventario()) {
/*  79: 82 */       cargarCantidadADevolver(detalle);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void limpiarTodos()
/*  84:    */   {
/*  85: 87 */     for (DetalleMovimientoInventario detalle : getListaDetalleMovimientoInventario()) {
/*  86: 88 */       limpiarCantidadADevolver(detalle);
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void cargarCantidadADevolver(DetalleMovimientoInventario detalle)
/*  91:    */   {
/*  92: 93 */     BigDecimal cantidad = detalle.getDetalleMovimientoInventarioPadre().getCantidadPorDevolver();
/*  93: 94 */     detalle.setCantidadOrigen(cantidad);
/*  94: 95 */     actualizarCantidadDetalleMovimientoInventario(detalle);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void limpiarCantidadADevolver(DetalleMovimientoInventario detalle)
/*  98:    */   {
/*  99: 99 */     detalle.setCantidadOrigen(BigDecimal.ZERO);
/* 100:100 */     actualizarCantidadDetalleMovimientoInventario(detalle);
/* 101:    */   }
/* 102:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.DevolucionConsumoBodegaBean
 * JD-Core Version:    0.7.0.1
 */