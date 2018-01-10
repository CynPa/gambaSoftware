/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  13:    */ import java.io.BufferedInputStream;
/*  14:    */ import java.io.InputStream;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import org.apache.log4j.Logger;
/*  21:    */ import org.primefaces.event.FileUploadEvent;
/*  22:    */ import org.primefaces.model.UploadedFile;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class MigracionFacturaClienteBean
/*  27:    */   extends PageController
/*  28:    */ {
/*  29: 47 */   private List<ErrorCarga> errores = new ArrayList();
/*  30:    */   private static final long serialVersionUID = -2279483837568577014L;
/*  31:    */   @EJB
/*  32:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  33:    */   
/*  34:    */   public String migrarFacturaCliente(FileUploadEvent event)
/*  35:    */   {
/*  36: 62 */     List<FacturaCliente> lisFacturaCliente = new ArrayList();
/*  37:    */     try
/*  38:    */     {
/*  39: 65 */       String fileName = "migracion_factura_cliente" + event.getFile().getFileName();
/*  40: 66 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  41: 67 */       lisFacturaCliente = this.servicioFacturaCliente.migracionFacturaCliente(AppUtil.getOrganizacion().getId(), fileName, input, 4);
/*  42: 68 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  43:    */       
/*  44: 70 */       this.servicioFacturaCliente.guardarFacturaClienteRevisadas(lisFacturaCliente);
/*  45:    */     }
/*  46:    */     catch (AS2Exception e)
/*  47:    */     {
/*  48: 73 */       e.printStackTrace();
/*  49: 74 */       List<String> listaMensajes = e.getCodigoMensajes();
/*  50: 75 */       int i = 0;
/*  51: 76 */       for (String a : listaMensajes)
/*  52:    */       {
/*  53: 77 */         i = a.indexOf("*");
/*  54: 78 */         a.substring(0, i + 1);
/*  55: 79 */         ErrorCarga ec = new ErrorCarga();
/*  56: 80 */         ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/*  57: 81 */         this.errores.add(ec);
/*  58:    */       }
/*  59: 83 */       for (String a : e.getMensajes())
/*  60:    */       {
/*  61: 84 */         ErrorCarga ec = new ErrorCarga();
/*  62: 85 */         ec.setError(a);
/*  63: 86 */         this.errores.add(ec);
/*  64:    */       }
/*  65:    */     }
/*  66:    */     catch (ExcepcionAS2Financiero e)
/*  67:    */     {
/*  68: 93 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  69: 94 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  70:    */     }
/*  71:    */     catch (ExcepcionAS2Compras e)
/*  72:    */     {
/*  73: 97 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  74: 98 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  75:    */     }
/*  76:    */     catch (ExcepcionAS2 e)
/*  77:    */     {
/*  78:101 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  79:102 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  80:    */     }
/*  81:    */     catch (Exception e)
/*  82:    */     {
/*  83:105 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  84:106 */       e.printStackTrace();
/*  85:    */     }
/*  86:108 */     return null;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public List<ErrorCarga> getErrores()
/*  90:    */   {
/*  91:115 */     return this.errores;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setErrores(List<ErrorCarga> errores)
/*  95:    */   {
/*  96:119 */     this.errores = errores;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public ServicioFacturaCliente getServicioFacturaCliente()
/* 100:    */   {
/* 101:123 */     return this.servicioFacturaCliente;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setServicioFacturaCliente(ServicioFacturaCliente servicioFacturaCliente)
/* 105:    */   {
/* 106:127 */     this.servicioFacturaCliente = servicioFacturaCliente;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String getRutaPlantilla()
/* 110:    */   {
/* 111:132 */     return "/resources/plantillas/ventas/AS2 Factura Cliente.xls";
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getNombrePlantilla()
/* 115:    */   {
/* 116:137 */     return "AS2 Factura Cliente.xls";
/* 117:    */   }
/* 118:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.MigracionFacturaClienteBean
 * JD-Core Version:    0.7.0.1
 */