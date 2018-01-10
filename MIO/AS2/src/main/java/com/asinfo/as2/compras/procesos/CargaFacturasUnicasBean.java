/*  1:   */ package com.asinfo.as2.compras.procesos;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.combustibles.ServicioCargaProcesoCombustibles;
/*  4:   */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*  5:   */ import com.asinfo.as2.controller.LanguageController;
/*  6:   */ import com.asinfo.as2.controller.PageController;
/*  7:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  8:   */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  9:   */ import java.io.BufferedInputStream;
/* 10:   */ import java.io.IOException;
/* 11:   */ import java.io.InputStream;
/* 12:   */ import javax.ejb.EJB;
/* 13:   */ import javax.faces.bean.ManagedBean;
/* 14:   */ import javax.faces.bean.ViewScoped;
/* 15:   */ import org.apache.log4j.Logger;
/* 16:   */ import org.primefaces.event.FileUploadEvent;
/* 17:   */ import org.primefaces.model.UploadedFile;
/* 18:   */ 
/* 19:   */ @ManagedBean
/* 20:   */ @ViewScoped
/* 21:   */ public class CargaFacturasUnicasBean
/* 22:   */   extends PageController
/* 23:   */ {
/* 24:   */   private static final long serialVersionUID = 1L;
/* 25:   */   @EJB
/* 26:   */   private ServicioCargaProcesoCombustibles servicioCargaVentasComercializadoras;
/* 27:   */   
/* 28:   */   public String cargarFacturasUnicas(FileUploadEvent event)
/* 29:   */   {
/* 30:   */     try
/* 31:   */     {
/* 32:56 */       String fileName = event.getFile().getFileName();
/* 33:57 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 34:58 */       this.servicioCargaVentasComercializadoras.cargarFacturaUnica(fileName, input, 4);
/* 35:   */       
/* 36:60 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 37:   */     }
/* 38:   */     catch (ExcepcionAS2Financiero e)
/* 39:   */     {
/* 40:63 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 41:64 */       LOG.error("ERROR AL CARGA DATOS FACTURAS UNICAS", e);
/* 42:   */     }
/* 43:   */     catch (ExcepcionAS2Compras e)
/* 44:   */     {
/* 45:67 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 46:68 */       LOG.error("ERROR AL CARGA DATOS FACTURAS UNICAS", e);
/* 47:   */     }
/* 48:   */     catch (ExcepcionAS2 e)
/* 49:   */     {
/* 50:71 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 51:72 */       LOG.error("ERROR AL CARGA DATOS FACTURAS UNICAS", e);
/* 52:   */     }
/* 53:   */     catch (IOException e)
/* 54:   */     {
/* 55:75 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 56:76 */       LOG.error("ERROR AL CARGA DATOS FACTURAS UNICAS", e);
/* 57:   */     }
/* 58:   */     catch (Exception e)
/* 59:   */     {
/* 60:78 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 61:79 */       LOG.error("ERROR AL CARGA DATOS FACTURAS UNICAS", e);
/* 62:   */     }
/* 63:81 */     return null;
/* 64:   */   }
/* 65:   */   
/* 66:   */   public String cargarFacturasUnicas2(FileUploadEvent event)
/* 67:   */   {
/* 68:89 */     return "";
/* 69:   */   }
/* 70:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.compras.procesos.CargaFacturasUnicasBean
 * JD-Core Version:    0.7.0.1
 */