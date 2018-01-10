/*  1:   */ package com.asinfo.as2.finaciero.activos.procesos;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*  4:   */ import com.asinfo.as2.controller.LanguageController;
/*  5:   */ import com.asinfo.as2.controller.PageController;
/*  6:   */ import com.asinfo.as2.excepciones.AS2Exception;
/*  7:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  8:   */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioActivoFijo;
/*  9:   */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/* 10:   */ import com.asinfo.as2.utils.JsfUtil;
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
/* 23:   */ public class CargaActivosFijosBean
/* 24:   */   extends PageController
/* 25:   */ {
/* 26:   */   private static final long serialVersionUID = 1L;
/* 27:   */   @EJB
/* 28:   */   private ServicioActivoFijo servicioActivoFijo;
/* 29:   */   
/* 30:   */   public String cargarActivosFijos(FileUploadEvent event)
/* 31:   */   {
/* 32:   */     try
/* 33:   */     {
/* 34:58 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 35:59 */       this.servicioActivoFijo.cargarActivosFijos(input, 4);
/* 36:60 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 37:   */     }
/* 38:   */     catch (AS2Exception e)
/* 39:   */     {
/* 40:63 */       JsfUtil.addErrorMessage(e, "");
/* 41:64 */       e.printStackTrace();
/* 42:   */     }
/* 43:   */     catch (ExcepcionAS2Financiero e)
/* 44:   */     {
/* 45:67 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 46:68 */       LOG.error("ERROR AL CARGA DATOS ACTIVOS FIJOS", e);
/* 47:   */     }
/* 48:   */     catch (ExcepcionAS2Compras e)
/* 49:   */     {
/* 50:71 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 51:72 */       LOG.error("ERROR AL CARGA DATOS ACTIVOS FIJOS", e);
/* 52:   */     }
/* 53:   */     catch (ExcepcionAS2 e)
/* 54:   */     {
/* 55:75 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 56:76 */       LOG.error("ERROR AL CARGA DATOS ACTIVOS FIJOS", e);
/* 57:   */     }
/* 58:   */     catch (IOException e)
/* 59:   */     {
/* 60:79 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 61:80 */       LOG.error("ERROR AL CARGA DATOS ACTIVOS FIJOS", e);
/* 62:   */     }
/* 63:   */     catch (Exception e)
/* 64:   */     {
/* 65:82 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 66:83 */       LOG.error("ERROR AL CARGA DATOS ACTIVOS FIJOS", e);
/* 67:   */     }
/* 68:86 */     return null;
/* 69:   */   }
/* 70:   */   
/* 71:   */   public String getRutaPlantilla()
/* 72:   */   {
/* 73:91 */     return "/resources/plantillas/activosFijos/AS2 Activos Fijos.xls";
/* 74:   */   }
/* 75:   */   
/* 76:   */   public String getNombrePlantilla()
/* 77:   */   {
/* 78:96 */     return "AS2 Activos Fijos.xls";
/* 79:   */   }
/* 80:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.procesos.CargaActivosFijosBean
 * JD-Core Version:    0.7.0.1
 */