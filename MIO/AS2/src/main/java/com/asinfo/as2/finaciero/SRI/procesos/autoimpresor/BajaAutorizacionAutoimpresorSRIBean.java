/*   1:    */ package com.asinfo.as2.finaciero.SRI.procesos.autoimpresor;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.sri.AutorizacionAutoimpresorSRI;
/*   7:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI;
/*   8:    */ import com.asinfo.as2.enumeraciones.ProcesoAutoimpresorSRIEnum;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioAutorizacionAutoimpresorSRI;
/*  11:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @ViewScoped
/*  21:    */ public class BajaAutorizacionAutoimpresorSRIBean
/*  22:    */   extends PageControllerAS2
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 8782410859351300688L;
/*  25:    */   @EJB
/*  26:    */   private ServicioAutorizacionAutoimpresorSRI servicioAutorizacionAutoimpresorSRI;
/*  27:    */   private AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI;
/*  28:    */   
/*  29:    */   @PostConstruct
/*  30:    */   public void init()
/*  31:    */   {
/*  32:    */     try
/*  33:    */     {
/*  34: 72 */       this.autorizacionAutoimpresorSRI = this.servicioAutorizacionAutoimpresorSRI.obtenerAutorizacionSRIVigente(AppUtil.getOrganizacion()
/*  35: 73 */         .getIdOrganizacion());
/*  36: 74 */       this.autorizacionAutoimpresorSRI = this.servicioAutorizacionAutoimpresorSRI.cargarDetalle(this.autorizacionAutoimpresorSRI.getId());
/*  37: 75 */       setEditado(true);
/*  38:    */     }
/*  39:    */     catch (ExcepcionAS2Financiero e)
/*  40:    */     {
/*  41: 77 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   private void crearEntidad()
/*  46:    */   {
/*  47: 90 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String editar()
/*  51:    */   {
/*  52: 99 */     if (getAutorizacionAutoimpresorSRI().getIdAutorizacionAutoimpresorSRI() > 0)
/*  53:    */     {
/*  54:101 */       this.autorizacionAutoimpresorSRI = this.servicioAutorizacionAutoimpresorSRI.cargarDetalle(this.autorizacionAutoimpresorSRI.getId());
/*  55:102 */       setEditado(true);
/*  56:    */     }
/*  57:    */     else
/*  58:    */     {
/*  59:104 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  60:    */     }
/*  61:106 */     return "";
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String guardar()
/*  65:    */   {
/*  66:    */     try
/*  67:    */     {
/*  68:117 */       for (AutorizacionDocumentoPuntoDeVentaAutoimpresorSRI adpva : this.autorizacionAutoimpresorSRI
/*  69:118 */         .getListaAutorizacionDocumentoPuntoDeVentaAutoimpresorSRIActivos()) {
/*  70:119 */         adpva.setActivo(false);
/*  71:    */       }
/*  72:121 */       this.servicioAutorizacionAutoimpresorSRI.guardar(this.autorizacionAutoimpresorSRI, ProcesoAutoimpresorSRIEnum.BAJA, this.autorizacionAutoimpresorSRI
/*  73:122 */         .getFechaBaja());
/*  74:123 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  75:124 */       setEditado(false);
/*  76:125 */       limpiar();
/*  77:    */     }
/*  78:    */     catch (ExcepcionAS2 e)
/*  79:    */     {
/*  80:127 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  81:128 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  86:131 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  87:    */     }
/*  88:133 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:143 */       this.servicioAutorizacionAutoimpresorSRI.eliminar(this.autorizacionAutoimpresorSRI);
/*  96:144 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:146 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 101:147 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 102:    */     }
/* 103:149 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String cargarDatos()
/* 107:    */   {
/* 108:158 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String limpiar()
/* 112:    */   {
/* 113:167 */     crearEntidad();
/* 114:168 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public AutorizacionAutoimpresorSRI getAutorizacionAutoimpresorSRI()
/* 118:    */   {
/* 119:185 */     return this.autorizacionAutoimpresorSRI;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setAutorizacionAutoimpresorSRI(AutorizacionAutoimpresorSRI autorizacionAutoimpresorSRI)
/* 123:    */   {
/* 124:195 */     this.autorizacionAutoimpresorSRI = autorizacionAutoimpresorSRI;
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.procesos.autoimpresor.BajaAutorizacionAutoimpresorSRIBean
 * JD-Core Version:    0.7.0.1
 */