/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.MovimientoUnidadManejo;
/*   5:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   6:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   9:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  10:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoUnidadManejo;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.List;
/*  13:    */ import java.util.Map;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import javax.faces.context.FacesContext;
/*  18:    */ import javax.faces.context.PartialViewContext;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.context.RequestContext;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class RecepcionUnidadManejoBean
/*  26:    */   extends TransferenciaUnidadManejoBean
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -5463907651730263568L;
/*  29:    */   
/*  30:    */   @PostConstruct
/*  31:    */   public void init()
/*  32:    */   {
/*  33: 45 */     super.init();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public Map<String, String> getFiltros(Map<String, String> filters)
/*  37:    */   {
/*  38: 50 */     super.getFiltros(filters);
/*  39: 51 */     filters.put("estado", "" + Estado.ELABORADO);
/*  40: 52 */     return filters;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public String cargarDatos()
/*  44:    */   {
/*  45: 57 */     MovimientoUnidadManejo mum = (MovimientoUnidadManejo)getDtListaAjuste().getRowData();
/*  46: 58 */     setMovimientoUnidadManejo(this.servicioMovimientoUnidadManejo.cargarDetalle(mum.getId()));
/*  47: 59 */     return "";
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String editar()
/*  51:    */   {
/*  52: 64 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  53: 65 */     return "";
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String crear()
/*  57:    */   {
/*  58: 70 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  59: 71 */     return "";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String guardar()
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66: 77 */       if (getDetalleMovimientoUnidadManejo().size() > 0)
/*  67:    */       {
/*  68: 79 */         this.servicioMovimientoUnidadManejo.guardarRecepcion(getMovimientoUnidadManejo());
/*  69: 80 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  70: 81 */         setEditado(false);
/*  71:    */       }
/*  72:    */       else
/*  73:    */       {
/*  74: 84 */         addErrorMessage(getLanguageController().getMensaje("msg_error_detalles_vacios"));
/*  75:    */       }
/*  76:    */     }
/*  77:    */     catch (ExcepcionAS2Inventario e)
/*  78:    */     {
/*  79: 87 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  80: 88 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/*  81:    */     }
/*  82:    */     catch (ExcepcionAS2Financiero e)
/*  83:    */     {
/*  84: 90 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  85: 91 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/*  86:    */     }
/*  87:    */     catch (ExcepcionAS2 e)
/*  88:    */     {
/*  89: 93 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  90: 94 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/*  91:    */     }
/*  92:    */     catch (AS2Exception e)
/*  93:    */     {
/*  94: 96 */       FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("panelErrores");
/*  95: 97 */       RequestContext.getCurrentInstance().execute("dialogoErrores.show()");
/*  96: 98 */       LOG.error("ERROR AL GUARDAR UN MOVIMIENTO", e);
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:100 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 101:101 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 102:    */     }
/* 103:103 */     return "";
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.RecepcionUnidadManejoBean
 * JD-Core Version:    0.7.0.1
 */