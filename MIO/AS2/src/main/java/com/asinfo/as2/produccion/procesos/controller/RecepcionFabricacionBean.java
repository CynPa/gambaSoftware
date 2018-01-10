/*   1:    */ package com.asinfo.as2.produccion.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.enumeraciones.TipoVisualizacionEnum;
/*   7:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  10:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  11:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.JsfUtil;
/*  14:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  15:    */ import java.math.BigDecimal;
/*  16:    */ import java.util.List;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.component.datatable.DataTable;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class RecepcionFabricacionBean
/*  26:    */   extends IngresoFabricacionBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -5463907651730263568L;
/*  29:    */   private List<DetalleMovimientoInventario> listaDetalleAjusteInventario;
/*  30:    */   private List<DetalleMovimientoInventario> listaDetalleAjusteInventarioFiltrados;
/*  31:    */   private Boolean digitaCantidadAlRecibir;
/*  32:    */   
/*  33:    */   @PostConstruct
/*  34:    */   public void init()
/*  35:    */   {
/*  36: 49 */     this.listaDetalleAjusteInventario = null;
/*  37: 50 */     getListaDetalleAjusteInventario();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public List<DetalleMovimientoInventario> getListaDetalleAjusteInventario()
/*  41:    */   {
/*  42: 54 */     if (this.listaDetalleAjusteInventario == null)
/*  43:    */     {
/*  44: 55 */       Usuario usuarioSesion = AppUtil.getUsuarioEnSesion();
/*  45: 56 */       List<Integer> idsSucursalesAsignadasUsuarioEnSesion = null;
/*  46: 60 */       if ((TipoVisualizacionEnum.MIS_SUCURSALES.equals(usuarioSesion.getTipoVisualizacion())) || 
/*  47: 61 */         (TipoVisualizacionEnum.MIS_REGISTROS.equals(usuarioSesion.getTipoVisualizacion()))) {
/*  48: 62 */         idsSucursalesAsignadasUsuarioEnSesion = getListaIdsSucursalesAsignadasUsuarioEnSesion(usuarioSesion);
/*  49:    */       }
/*  50: 69 */       this.listaDetalleAjusteInventario = this.servicioMovimientoInventario.obtenerListaDetalleRecepcionFabricacion(AppUtil.getOrganizacion().getId(), null, null, idsSucursalesAsignadasUsuarioEnSesion);
/*  51:    */     }
/*  52: 72 */     return this.listaDetalleAjusteInventario;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setListaDetalleAjusteInventario(List<DetalleMovimientoInventario> listaDetalleAjusteInventario)
/*  56:    */   {
/*  57: 76 */     this.listaDetalleAjusteInventario = listaDetalleAjusteInventario;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void recibir()
/*  61:    */   {
/*  62: 80 */     if (getDigitaCantidadAlRecibir().booleanValue()) {
/*  63: 81 */       validarCantidades();
/*  64:    */     } else {
/*  65: 83 */       recepcionarFabricacion();
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void validarCantidades()
/*  70:    */   {
/*  71: 88 */     if (this.detalleSeleccionado.getCantidad().compareTo(this.detalleSeleccionado.getTraCantidadADevolver()) == 0) {
/*  72: 89 */       recepcionarFabricacion();
/*  73:    */     } else {
/*  74: 91 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cantidad_diferente_al_recibir_fabricacion"));
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void recepcionarFabricacion()
/*  79:    */   {
/*  80:    */     try
/*  81:    */     {
/*  82: 97 */       this.servicioMovimientoInventario.recepcionarDetalleIngresoFabricacion(this.detalleSeleccionado);
/*  83: 98 */       getDtListaAjuste().reset();
/*  84: 99 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  85:    */     }
/*  86:    */     catch (ExcepcionAS2Financiero e)
/*  87:    */     {
/*  88:101 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  89:102 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/*  90:    */     }
/*  91:    */     catch (ExcepcionAS2 e)
/*  92:    */     {
/*  93:104 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  94:105 */       LOG.error("ERROR AL GUARDAR UN INGRESO DE FABRICACION", e);
/*  95:    */     }
/*  96:    */     catch (AS2Exception e)
/*  97:    */     {
/*  98:107 */       JsfUtil.addErrorMessage(e, "");
/*  99:    */     }
/* 100:109 */     this.listaDetalleAjusteInventario = null;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public List<DetalleMovimientoInventario> getListaDetalleAjusteInventarioFiltrados()
/* 104:    */   {
/* 105:113 */     return this.listaDetalleAjusteInventarioFiltrados;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setListaDetalleAjusteInventarioFiltrados(List<DetalleMovimientoInventario> listaDetalleAjusteInventarioFiltrados)
/* 109:    */   {
/* 110:117 */     this.listaDetalleAjusteInventarioFiltrados = listaDetalleAjusteInventarioFiltrados;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Boolean getDigitaCantidadAlRecibir()
/* 114:    */   {
/* 115:121 */     if (this.digitaCantidadAlRecibir == null) {
/* 116:122 */       this.digitaCantidadAlRecibir = ParametrosSistema.getDigitaCantidadAlRecibir(AppUtil.getOrganizacion().getId());
/* 117:    */     }
/* 118:124 */     return this.digitaCantidadAlRecibir;
/* 119:    */   }
/* 120:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.RecepcionFabricacionBean
 * JD-Core Version:    0.7.0.1
 */