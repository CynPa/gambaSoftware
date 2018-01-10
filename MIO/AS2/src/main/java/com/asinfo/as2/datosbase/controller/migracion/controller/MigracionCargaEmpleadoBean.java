/*   1:    */ package com.asinfo.as2.datosbase.controller.migracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.migracion.ServicioMigracion;
/*   6:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   7:    */ import java.io.BufferedInputStream;
/*   8:    */ import java.io.InputStream;
/*   9:    */ import javax.annotation.PostConstruct;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.faces.bean.ManagedBean;
/*  12:    */ import javax.faces.bean.ViewScoped;
/*  13:    */ import org.primefaces.event.FileUploadEvent;
/*  14:    */ import org.primefaces.model.UploadedFile;
/*  15:    */ 
/*  16:    */ @ManagedBean
/*  17:    */ @ViewScoped
/*  18:    */ public class MigracionCargaEmpleadoBean
/*  19:    */   extends PageControllerAS2
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = -2279483837568577014L;
/*  22:    */   @EJB
/*  23:    */   private ServicioMigracion servicioMigracion;
/*  24:    */   
/*  25:    */   @PostConstruct
/*  26:    */   public void init() {}
/*  27:    */   
/*  28:    */   private void crearEntidad() {}
/*  29:    */   
/*  30:    */   public String editar()
/*  31:    */   {
/*  32: 90 */     return "";
/*  33:    */   }
/*  34:    */   
/*  35:    */   public String guardar()
/*  36:    */   {
/*  37:107 */     return "";
/*  38:    */   }
/*  39:    */   
/*  40:    */   public String eliminar()
/*  41:    */   {
/*  42:123 */     return "";
/*  43:    */   }
/*  44:    */   
/*  45:    */   public String cargarDatos()
/*  46:    */   {
/*  47:132 */     return "";
/*  48:    */   }
/*  49:    */   
/*  50:    */   public String limpiar()
/*  51:    */   {
/*  52:141 */     crearEntidad();
/*  53:142 */     return "";
/*  54:    */   }
/*  55:    */   
/*  56:    */   public String migrarCargaEmpleado(FileUploadEvent event)
/*  57:    */   {
/*  58:    */     try
/*  59:    */     {
/*  60:148 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  61:    */       
/*  62:150 */       this.servicioMigracion.migracionCargaEmpleado(input, 4);
/*  63:151 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  64:    */     }
/*  65:    */     catch (ExcepcionAS2 e)
/*  66:    */     {
/*  67:154 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  68:155 */       e.printStackTrace();
/*  69:    */     }
/*  70:    */     catch (Exception e)
/*  71:    */     {
/*  72:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  73:158 */       e.printStackTrace();
/*  74:    */     }
/*  75:160 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getRutaPlantilla()
/*  79:    */   {
/*  80:172 */     return "/resources/plantillas/nomina/AS2 Cargas Familiares.xls";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String getNombrePlantilla()
/*  84:    */   {
/*  85:177 */     return "AS2 Cargas Familiares.xls";
/*  86:    */   }
/*  87:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.controller.migracion.controller.MigracionCargaEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */