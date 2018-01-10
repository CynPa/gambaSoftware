/*  1:   */ package com.asinfo.as2.produccion.procesos.controller;
/*  2:   */ 
/*  3:   */ import com.asinfo.as2.controller.LanguageController;
/*  4:   */ import com.asinfo.as2.controller.PageControllerAS2;
/*  5:   */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*  6:   */ import com.asinfo.as2.entities.Organizacion;
/*  7:   */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  8:   */ import com.asinfo.as2.util.AppUtil;
/*  9:   */ import java.io.BufferedInputStream;
/* 10:   */ import java.io.InputStream;
/* 11:   */ import javax.ejb.EJB;
/* 12:   */ import javax.faces.bean.ManagedBean;
/* 13:   */ import javax.faces.bean.ViewScoped;
/* 14:   */ import org.primefaces.event.FileUploadEvent;
/* 15:   */ import org.primefaces.model.UploadedFile;
/* 16:   */ 
/* 17:   */ @ManagedBean
/* 18:   */ @ViewScoped
/* 19:   */ public class CargaListaDeMaterialesBean
/* 20:   */   extends PageControllerAS2
/* 21:   */ {
/* 22:   */   private static final long serialVersionUID = 1L;
/* 23:   */   @EJB
/* 24:   */   private ServicioMigracion servicioMigracion;
/* 25:   */   
/* 26:   */   public String cargarListaDeMateriales(FileUploadEvent event)
/* 27:   */   {
/* 28:   */     try
/* 29:   */     {
/* 30:34 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 31:   */       
/* 32:36 */       this.servicioMigracion.cargaListaDeMateriales(AppUtil.getOrganizacion().getId(), input, 4);
/* 33:37 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 34:   */     }
/* 35:   */     catch (ExcepcionAS2 e)
/* 36:   */     {
/* 37:40 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 38:41 */       e.printStackTrace();
/* 39:   */     }
/* 40:   */     catch (Exception e)
/* 41:   */     {
/* 42:43 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 43:44 */       e.printStackTrace();
/* 44:   */     }
/* 45:46 */     return null;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public String editar()
/* 49:   */   {
/* 50:51 */     return null;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public String guardar()
/* 54:   */   {
/* 55:56 */     return null;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public String eliminar()
/* 59:   */   {
/* 60:61 */     return null;
/* 61:   */   }
/* 62:   */   
/* 63:   */   public String limpiar()
/* 64:   */   {
/* 65:66 */     return null;
/* 66:   */   }
/* 67:   */   
/* 68:   */   public String cargarDatos()
/* 69:   */   {
/* 70:71 */     return null;
/* 71:   */   }
/* 72:   */   
/* 73:   */   public String getRutaPlantilla()
/* 74:   */   {
/* 75:76 */     return "/resources/plantillas/inventario/AS2 Lista de Materiales.xls";
/* 76:   */   }
/* 77:   */   
/* 78:   */   public String getNombrePlantilla()
/* 79:   */   {
/* 80:81 */     return "AS2 Lista de Materiales.xls";
/* 81:   */   }
/* 82:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.controller.CargaListaDeMaterialesBean
 * JD-Core Version:    0.7.0.1
 */