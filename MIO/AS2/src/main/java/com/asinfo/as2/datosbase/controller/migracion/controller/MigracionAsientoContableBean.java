/*   1:    */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*   9:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import java.io.BufferedInputStream;
/*  13:    */ import java.io.InputStream;
/*  14:    */ import javax.annotation.PostConstruct;
/*  15:    */ import javax.ejb.EJB;
/*  16:    */ import javax.faces.bean.ManagedBean;
/*  17:    */ import javax.faces.bean.ViewScoped;
/*  18:    */ import org.apache.log4j.Logger;
/*  19:    */ import org.primefaces.event.FileUploadEvent;
/*  20:    */ import org.primefaces.model.UploadedFile;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class MigracionAsientoContableBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 1L;
/*  28:    */   @EJB
/*  29:    */   private ServicioAsiento servicioAsiento;
/*  30:    */   
/*  31:    */   @PostConstruct
/*  32:    */   public void init() {}
/*  33:    */   
/*  34:    */   private void crearEntidad() {}
/*  35:    */   
/*  36:    */   public String editar()
/*  37:    */   {
/*  38: 59 */     return "";
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String guardar()
/*  42:    */   {
/*  43: 63 */     return "";
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String eliminar()
/*  47:    */   {
/*  48: 67 */     return "";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String cargarDatos()
/*  52:    */   {
/*  53: 71 */     return "";
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String limpiar()
/*  57:    */   {
/*  58: 75 */     crearEntidad();
/*  59: 76 */     return "";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String migrarAsientoContable(FileUploadEvent event)
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66: 81 */       String fileName = "migracion_asiento_" + event.getFile().getFileName();
/*  67: 82 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  68: 83 */       this.servicioAsiento.migracionAsiento(fileName, input, 5, AppUtil.getOrganizacion().getIdOrganizacion());
/*  69: 84 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  70:    */     }
/*  71:    */     catch (AS2Exception e)
/*  72:    */     {
/*  73: 86 */       JsfUtil.addErrorMessage(e, "");
/*  74:    */     }
/*  75:    */     catch (ExcepcionAS2Financiero e)
/*  76:    */     {
/*  77: 88 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  78: 89 */       LOG.error("ERROR AL MIGRAR ASIENTO", e);
/*  79: 90 */       e.printStackTrace();
/*  80:    */     }
/*  81:    */     catch (ExcepcionAS2 e)
/*  82:    */     {
/*  83: 92 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  84: 93 */       LOG.error("ERROR AL MIGRAR ASIENTO", e);
/*  85: 94 */       e.printStackTrace();
/*  86:    */     }
/*  87:    */     catch (Exception e)
/*  88:    */     {
/*  89: 96 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  90: 97 */       LOG.error("ERROR AL MIGRAR ASIENTO", e);
/*  91: 98 */       e.printStackTrace();
/*  92:    */     }
/*  93:100 */     return null;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getRutaPlantilla()
/*  97:    */   {
/*  98:111 */     return "/resources/plantillas/financiero/AS2 Asiento Contable.xls";
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getNombrePlantilla()
/* 102:    */   {
/* 103:116 */     return "AS2 Asiento Contable.xls";
/* 104:    */   }
/* 105:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionAsientoContableBean
 * JD-Core Version:    0.7.0.1
 */