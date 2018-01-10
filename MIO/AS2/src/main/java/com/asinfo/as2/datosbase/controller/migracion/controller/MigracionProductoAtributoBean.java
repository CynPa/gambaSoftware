/*  1:   */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.controller.PageController;
/*  5:   */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*  6:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  7:   */ import java.io.BufferedInputStream;
/*  8:   */ import java.io.IOException;
/*  9:   */ import java.io.InputStream;
/* 10:   */ import javax.ejb.EJB;
/* 11:   */ import javax.faces.bean.ManagedBean;
/* 12:   */ import javax.faces.bean.ViewScoped;
/* 13:   */ import org.apache.log4j.Logger;
/* 14:   */ import org.primefaces.event.FileUploadEvent;
/* 15:   */ import org.primefaces.model.UploadedFile;
/* 16:   */ 
/* 17:   */ @ManagedBean
/* 18:   */ @ViewScoped
/* 19:   */ public class MigracionProductoAtributoBean
/* 20:   */   extends PageController
/* 21:   */ {
/* 22:   */   private static final long serialVersionUID = -7213077706907154319L;
/* 23:   */   @EJB
/* 24:   */   private ServicioMigracion servicioMigracion;
/* 25:   */   
/* 26:   */   public String migrarProductoAtributo(FileUploadEvent event)
/* 27:   */   {
/* 28:   */     try
/* 29:   */     {
/* 30:47 */       String fileName = "migracion_producto_atributo_" + event.getFile().getFileName();
/* 31:48 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 32:49 */       this.servicioMigracion.migracionProductoAtributo(fileName, input, 4);
/* 33:50 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 34:   */     }
/* 35:   */     catch (ExcepcionAS2 e)
/* 36:   */     {
/* 37:53 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 38:54 */       LOG.error("ERROR AL MIGRAR PRODUCTOS ATRIBUTO", e);
/* 39:   */     }
/* 40:   */     catch (IOException e)
/* 41:   */     {
/* 42:56 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 43:57 */       e.printStackTrace();
/* 44:   */     }
/* 45:59 */     return null;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String getRutaPlantilla()
/* 49:   */   {
/* 50:64 */     return "/resources/plantillas/inventario/AS2 Producto Atributo.xls";
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String getNombrePlantilla()
/* 54:   */   {
/* 55:69 */     return "AS2 Producto Atributo.xls";
/* 56:   */   }
/* 57:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionProductoAtributoBean
 * JD-Core Version:    0.7.0.1
 */