/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.JsfUtil;
/*  12:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  13:    */ import com.asinfo.as2.ventas.procesos.ErrorCarga;
/*  14:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  15:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaCreditoCliente;
/*  16:    */ import java.io.BufferedInputStream;
/*  17:    */ import java.io.InputStream;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Iterator;
/*  20:    */ import java.util.List;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ import org.primefaces.event.FileUploadEvent;
/*  25:    */ import org.primefaces.model.UploadedFile;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class NotaCreditoFinancieraClienteElectronicaBean
/*  30:    */   extends NotaCreditoFinancieraClienteBean
/*  31:    */ {
/*  32: 78 */   private List<ErrorCarga> errores = new ArrayList();
/*  33:    */   
/*  34:    */   public String guardar()
/*  35:    */   {
/*  36:    */     try
/*  37:    */     {
/*  38: 84 */       this.servicioNotaCreditoCliente.guardar(this.notaCreditoCliente);
/*  39: 85 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  40: 86 */       limpiar();
/*  41:    */     }
/*  42:    */     catch (ExcepcionAS2Financiero e)
/*  43:    */     {
/*  44: 88 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion());
/*  45: 89 */       if (e.getMessage() != null) {
/*  46: 90 */         strMensaje = strMensaje + " " + e.getMessage();
/*  47:    */       }
/*  48: 92 */       addInfoMessage(strMensaje);
/*  49: 93 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  50:    */     }
/*  51:    */     catch (ExcepcionAS2 e)
/*  52:    */     {
/*  53: 96 */       String strMensaje = getLanguageController().getMensaje(e.getCodigoExcepcion());
/*  54: 97 */       if (e.getMessage() != null) {
/*  55: 98 */         strMensaje = strMensaje + " " + e.getMessage();
/*  56:    */       }
/*  57:100 */       addInfoMessage(strMensaje);
/*  58:101 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  59:    */     }
/*  60:    */     catch (AS2Exception e)
/*  61:    */     {
/*  62:104 */       JsfUtil.addErrorMessage(e, "");
/*  63:105 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  64:    */     }
/*  65:    */     catch (Exception e)
/*  66:    */     {
/*  67:107 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  68:108 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  69:    */     }
/*  70:112 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String eliminar()
/*  74:    */   {
/*  75:117 */     if (this.notaCreditoCliente.getId() > 0) {
/*  76:    */       try
/*  77:    */       {
/*  78:119 */         this.servicioNotaCreditoCliente.anular(this.notaCreditoCliente);
/*  79:120 */         addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/*  80:    */       }
/*  81:    */       catch (ExcepcionAS2Ventas e)
/*  82:    */       {
/*  83:122 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  84:123 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE ExcepcionAS2Ventas", e);
/*  85:124 */         e.printStackTrace();
/*  86:    */       }
/*  87:    */       catch (ExcepcionAS2Financiero e)
/*  88:    */       {
/*  89:126 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  90:127 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE ExcepcionAS2Financiero", e);
/*  91:128 */         e.printStackTrace();
/*  92:    */       }
/*  93:    */       catch (Exception e)
/*  94:    */       {
/*  95:130 */         addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  96:131 */         LOG.info("ERROR AL ANULAR UNA NOTA DE CREDITO DE CLIENTE Exception", e);
/*  97:132 */         e.printStackTrace();
/*  98:    */       }
/*  99:    */     } else {
/* 100:135 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 101:    */     }
/* 102:138 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String cargarNotaCreditoFinancieraCliente(FileUploadEvent event)
/* 106:    */   {
/* 107:147 */     List<FacturaCliente> lisFacturaCliente = new ArrayList();
/* 108:    */     try
/* 109:    */     {
/* 110:150 */       String fileName = "migracion_factura_cliente" + event.getFile().getFileName();
/* 111:151 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 112:152 */       lisFacturaCliente = this.servicioFacturaCliente.migracionFacturaCliente(AppUtil.getOrganizacion().getId(), fileName, input, 4);
/* 113:153 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/* 114:155 */       for (localIterator = lisFacturaCliente.iterator(); localIterator.hasNext();)
/* 115:    */       {
/* 116:155 */         facturaCliente = (FacturaCliente)localIterator.next();
/* 117:156 */         this.servicioNotaCreditoCliente.guardar(facturaCliente);
/* 118:    */       }
/* 119:    */     }
/* 120:    */     catch (AS2Exception e)
/* 121:    */     {
/* 122:    */       Iterator localIterator;
/* 123:160 */       e.printStackTrace();
/* 124:161 */       List<String> listaMensajes = e.getCodigoMensajes();
/* 125:162 */       int i = 0;
/* 126:163 */       for (String a : listaMensajes)
/* 127:    */       {
/* 128:164 */         i = a.indexOf("*");
/* 129:165 */         a.substring(0, i + 1);
/* 130:166 */         ErrorCarga ec = new ErrorCarga();
/* 131:167 */         ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/* 132:168 */         this.errores.add(ec);
/* 133:    */       }
/* 134:170 */       for (String a : e.getMensajes())
/* 135:    */       {
/* 136:171 */         ErrorCarga ec = new ErrorCarga();
/* 137:172 */         ec.setError(a);
/* 138:173 */         this.errores.add(ec);
/* 139:    */       }
/* 140:    */     }
/* 141:    */     catch (ExcepcionAS2Financiero e)
/* 142:    */     {
/* 143:    */       FacturaCliente facturaCliente;
/* 144:180 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 145:181 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 146:    */     }
/* 147:    */     catch (ExcepcionAS2Compras e)
/* 148:    */     {
/* 149:184 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 150:185 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 151:    */     }
/* 152:    */     catch (ExcepcionAS2 e)
/* 153:    */     {
/* 154:188 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 155:189 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 156:    */     }
/* 157:    */     catch (Exception e)
/* 158:    */     {
/* 159:192 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 160:193 */       e.printStackTrace();
/* 161:    */     }
/* 162:195 */     return null;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<ErrorCarga> getErrores()
/* 166:    */   {
/* 167:202 */     return this.errores;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setErrores(List<ErrorCarga> errores)
/* 171:    */   {
/* 172:206 */     this.errores = errores;
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.NotaCreditoFinancieraClienteElectronicaBean
 * JD-Core Version:    0.7.0.1
 */