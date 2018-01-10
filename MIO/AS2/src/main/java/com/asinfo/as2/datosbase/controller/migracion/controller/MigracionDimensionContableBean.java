/*   1:    */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.io.BufferedInputStream;
/*  11:    */ import java.io.InputStream;
/*  12:    */ import javax.annotation.PostConstruct;
/*  13:    */ import javax.ejb.EJB;
/*  14:    */ import javax.faces.bean.ManagedBean;
/*  15:    */ import javax.faces.bean.ViewScoped;
/*  16:    */ import org.primefaces.event.FileUploadEvent;
/*  17:    */ import org.primefaces.model.UploadedFile;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @ViewScoped
/*  21:    */ public class MigracionDimensionContableBean
/*  22:    */   extends PageControllerAS2
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 2209691394285804395L;
/*  25:    */   @EJB
/*  26:    */   private ServicioMigracion servicioMigracion;
/*  27: 53 */   private String dimension = "1";
/*  28:    */   
/*  29:    */   @PostConstruct
/*  30:    */   public void init() {}
/*  31:    */   
/*  32:    */   private void crearEntidad() {}
/*  33:    */   
/*  34:    */   public String editar()
/*  35:    */   {
/*  36: 88 */     return "";
/*  37:    */   }
/*  38:    */   
/*  39:    */   public String guardar()
/*  40:    */   {
/*  41: 97 */     return "";
/*  42:    */   }
/*  43:    */   
/*  44:    */   public String eliminar()
/*  45:    */   {
/*  46:106 */     return "";
/*  47:    */   }
/*  48:    */   
/*  49:    */   public String cargarDatos()
/*  50:    */   {
/*  51:115 */     return "";
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String limpiar()
/*  55:    */   {
/*  56:124 */     crearEntidad();
/*  57:125 */     return "";
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void migrarDimensionContable(FileUploadEvent event)
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64:131 */       String fileName = "migracion_dimension_contable_" + event.getFile().getFileName();
/*  65:132 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  66:133 */       this.servicioMigracion.migracionDimensionesContables(AppUtil.getOrganizacion().getIdOrganizacion(), this.dimension, fileName, input, 5, getNivelDimension(this.dimension));
/*  67:134 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  68:    */     }
/*  69:    */     catch (ExcepcionAS2Financiero e)
/*  70:    */     {
/*  71:137 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  72:    */     }
/*  73:    */     catch (ExcepcionAS2 e)
/*  74:    */     {
/*  75:139 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  76:    */     }
/*  77:    */     catch (Exception e)
/*  78:    */     {
/*  79:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  80:142 */       e.printStackTrace();
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String getDimension()
/*  85:    */   {
/*  86:155 */     return this.dimension;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setDimension(String dimension)
/*  90:    */   {
/*  91:159 */     this.dimension = dimension;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getRutaPlantilla()
/*  95:    */   {
/*  96:164 */     return "/resources/plantillas/financiero/AS2 Dimension Contable.xls";
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getNombrePlantilla()
/* 100:    */   {
/* 101:169 */     return "AS2 Dimension Contable.xls";
/* 102:    */   }
/* 103:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionDimensionContableBean
 * JD-Core Version:    0.7.0.1
 */