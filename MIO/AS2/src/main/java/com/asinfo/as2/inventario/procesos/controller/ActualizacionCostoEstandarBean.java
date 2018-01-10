/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   6:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   7:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   8:    */ import java.io.BufferedInputStream;
/*   9:    */ import java.io.InputStream;
/*  10:    */ import javax.annotation.PostConstruct;
/*  11:    */ import javax.ejb.EJB;
/*  12:    */ import javax.faces.bean.ManagedBean;
/*  13:    */ import javax.faces.bean.ViewScoped;
/*  14:    */ import org.primefaces.event.FileUploadEvent;
/*  15:    */ import org.primefaces.model.UploadedFile;
/*  16:    */ 
/*  17:    */ @ManagedBean
/*  18:    */ @ViewScoped
/*  19:    */ public class ActualizacionCostoEstandarBean
/*  20:    */   extends PageControllerAS2
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 1L;
/*  23:    */   @EJB
/*  24:    */   private ServicioProducto servicioProducto;
/*  25:    */   
/*  26:    */   @PostConstruct
/*  27:    */   public void init() {}
/*  28:    */   
/*  29:    */   private void crearEntidad() {}
/*  30:    */   
/*  31:    */   public String editar()
/*  32:    */   {
/*  33: 86 */     return "";
/*  34:    */   }
/*  35:    */   
/*  36:    */   public String guardar()
/*  37:    */   {
/*  38: 95 */     return "";
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String eliminar()
/*  42:    */   {
/*  43:104 */     return "";
/*  44:    */   }
/*  45:    */   
/*  46:    */   public String cargarDatos()
/*  47:    */   {
/*  48:113 */     return "";
/*  49:    */   }
/*  50:    */   
/*  51:    */   public String limpiar()
/*  52:    */   {
/*  53:122 */     crearEntidad();
/*  54:123 */     return "";
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void cargarCostoEstandar(FileUploadEvent event)
/*  58:    */   {
/*  59:    */     try
/*  60:    */     {
/*  61:129 */       String fileName = "costo_estandar_" + event.getFile().getFileName();
/*  62:130 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  63:131 */       this.servicioProducto.cargaCostoEstandar(fileName, input, 5);
/*  64:132 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  65:    */     }
/*  66:    */     catch (ExcepcionAS2Financiero e)
/*  67:    */     {
/*  68:135 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  69:    */     }
/*  70:    */     catch (ExcepcionAS2 e)
/*  71:    */     {
/*  72:137 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  73:    */     }
/*  74:    */     catch (Exception e)
/*  75:    */     {
/*  76:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  77:140 */       e.printStackTrace();
/*  78:    */     }
/*  79:    */   }
/*  80:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.ActualizacionCostoEstandarBean
 * JD-Core Version:    0.7.0.1
 */