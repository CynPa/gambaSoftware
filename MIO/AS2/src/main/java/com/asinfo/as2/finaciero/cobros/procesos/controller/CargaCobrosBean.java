/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioCargaCobros;
/*   9:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import java.io.BufferedInputStream;
/*  12:    */ import java.io.IOException;
/*  13:    */ import java.io.InputStream;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.event.FileUploadEvent;
/*  19:    */ import org.primefaces.model.UploadedFile;
/*  20:    */ 
/*  21:    */ @ManagedBean
/*  22:    */ @ViewScoped
/*  23:    */ public class CargaCobrosBean
/*  24:    */   extends PageController
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @EJB
/*  28:    */   private ServicioCargaCobros servicioCargaCobros;
/*  29:    */   private boolean retencion;
/*  30:    */   
/*  31:    */   public String cargarCobros(FileUploadEvent event)
/*  32:    */   {
/*  33:    */     try
/*  34:    */     {
/*  35: 59 */       String fileName = event.getFile().getFileName();
/*  36: 60 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  37: 61 */       this.servicioCargaCobros.cargarCobros(Integer.valueOf(AppUtil.getOrganizacion().getId()), fileName, input, 4, this.retencion);
/*  38:    */       
/*  39: 63 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  40:    */     }
/*  41:    */     catch (ExcepcionAS2Financiero e)
/*  42:    */     {
/*  43: 66 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  44: 67 */       LOG.error("ERROR AL CARGA DATOS COBROS", e);
/*  45:    */     }
/*  46:    */     catch (ExcepcionAS2Compras e)
/*  47:    */     {
/*  48: 70 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  49: 71 */       LOG.error("ERROR AL CARGA DATOS COBROS", e);
/*  50:    */     }
/*  51:    */     catch (ExcepcionAS2 e)
/*  52:    */     {
/*  53: 74 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  54: 75 */       LOG.error("ERROR AL CARGA DATOS COBROS", e);
/*  55:    */     }
/*  56:    */     catch (IOException e)
/*  57:    */     {
/*  58: 78 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  59: 79 */       LOG.error("ERROR AL CARGA DATOS COBROS", e);
/*  60:    */     }
/*  61:    */     catch (Exception e)
/*  62:    */     {
/*  63: 81 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  64: 82 */       LOG.error("ERROR AL CARGA DATOS COBROS", e);
/*  65:    */     }
/*  66: 85 */     return null;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String getRutaPlantilla()
/*  70:    */   {
/*  71: 90 */     if (this.retencion) {
/*  72: 91 */       return "/resources/plantillas/financiero/AS2 Cobro Con Retencion.xls";
/*  73:    */     }
/*  74: 93 */     return "/resources/plantillas/financiero/AS2 Cobros.xls";
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getNombrePlantilla()
/*  78:    */   {
/*  79: 99 */     if (this.retencion) {
/*  80:100 */       return "AS2 Cobro Con Retencion.xls";
/*  81:    */     }
/*  82:102 */     return "AS2 Cobros.xls";
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isRetencion()
/*  86:    */   {
/*  87:107 */     return this.retencion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setRetencion(boolean retencion)
/*  91:    */   {
/*  92:111 */     this.retencion = retencion;
/*  93:    */   }
/*  94:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.CargaCobrosBean
 * JD-Core Version:    0.7.0.1
 */