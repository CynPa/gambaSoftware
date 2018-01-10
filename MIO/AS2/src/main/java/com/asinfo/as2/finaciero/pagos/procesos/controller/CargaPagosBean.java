/*  1:   */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*  4:   */ import com.asinfo.as2.controller.LanguageController;
/*  5:   */ import com.asinfo.as2.controller.PageController;
/*  6:   */ import com.asinfo.as2.entities.Organizacion;
/*  7:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  8:   */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  9:   */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioPago;
/* 10:   */ import com.asinfo.as2.util.AppUtil;
/* 11:   */ import java.io.BufferedInputStream;
/* 12:   */ import java.io.IOException;
/* 13:   */ import java.io.InputStream;
/* 14:   */ import javax.ejb.EJB;
/* 15:   */ import javax.faces.bean.ManagedBean;
/* 16:   */ import javax.faces.bean.ViewScoped;
/* 17:   */ import org.apache.log4j.Logger;
/* 18:   */ import org.primefaces.event.FileUploadEvent;
/* 19:   */ import org.primefaces.model.UploadedFile;
/* 20:   */ 
/* 21:   */ @ManagedBean
/* 22:   */ @ViewScoped
/* 23:   */ public class CargaPagosBean
/* 24:   */   extends PageController
/* 25:   */ {
/* 26:   */   private static final long serialVersionUID = 1L;
/* 27:   */   @EJB
/* 28:   */   private ServicioPago servicioPago;
/* 29:   */   
/* 30:   */   public String cargarPagos(FileUploadEvent event)
/* 31:   */   {
/* 32:   */     try
/* 33:   */     {
/* 34:56 */       String fileName = event.getFile().getFileName();
/* 35:57 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 36:58 */       this.servicioPago.cargarPagos(fileName, input, 4, AppUtil.getOrganizacion().getIdOrganizacion());
/* 37:   */       
/* 38:60 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 39:   */     }
/* 40:   */     catch (ExcepcionAS2Financiero e)
/* 41:   */     {
/* 42:63 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 43:64 */       LOG.error("ERROR AL CARGA DATOS PAGOS", e);
/* 44:   */     }
/* 45:   */     catch (ExcepcionAS2Compras e)
/* 46:   */     {
/* 47:67 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 48:68 */       LOG.error("ERROR AL CARGA DATOS PAGOS", e);
/* 49:   */     }
/* 50:   */     catch (ExcepcionAS2 e)
/* 51:   */     {
/* 52:71 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 53:72 */       LOG.error("ERROR AL CARGA DATOS PAGOS", e);
/* 54:   */     }
/* 55:   */     catch (IOException e)
/* 56:   */     {
/* 57:75 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 58:76 */       LOG.error("ERROR AL CARGA DATOS PAGOS", e);
/* 59:   */     }
/* 60:   */     catch (Exception e)
/* 61:   */     {
/* 62:79 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 63:80 */       LOG.error("ERROR AL CARGA DATOS PAGOS", e);
/* 64:   */     }
/* 65:82 */     return null;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public String getRutaPlantilla()
/* 69:   */   {
/* 70:87 */     return "/resources/plantillas/financiero/AS2 Pagos.xls";
/* 71:   */   }
/* 72:   */   
/* 73:   */   public String getNombrePlantilla()
/* 74:   */   {
/* 75:92 */     return "AS2 Pagos.xls";
/* 76:   */   }
/* 77:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.CargaPagosBean
 * JD-Core Version:    0.7.0.1
 */