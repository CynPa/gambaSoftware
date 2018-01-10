/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   6:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*   7:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*   8:    */ import com.asinfo.as2.util.AppUtil;
/*   9:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  10:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  11:    */ import java.util.Calendar;
/*  12:    */ import java.util.Date;
/*  13:    */ import java.util.List;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ 
/*  19:    */ @ManagedBean
/*  20:    */ @ViewScoped
/*  21:    */ public class RenumeracionFacturaClienteBean
/*  22:    */   extends PageControllerAS2
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 5545305285234368058L;
/*  25:    */   @EJB
/*  26:    */   private ServicioFacturaClienteSRI servicioFacturaClienteSRI;
/*  27:    */   @EJB
/*  28:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  29:    */   private String establecimiento;
/*  30:    */   private String puntoVenta;
/*  31: 58 */   private Date fechaDesde = Calendar.getInstance().getTime();
/*  32: 59 */   private Date fechaHasta = Calendar.getInstance().getTime();
/*  33:    */   private FacturaClienteSRI facturaClienteSRI;
/*  34:    */   private List<FacturaClienteSRI> listaFacturaClienteSRI;
/*  35:    */   
/*  36:    */   private void crearEntidad()
/*  37:    */   {
/*  38: 77 */     setEditado(true);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String editar()
/*  42:    */   {
/*  43: 86 */     addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  44: 87 */     return "";
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String guardar()
/*  48:    */   {
/*  49:    */     try
/*  50:    */     {
/*  51: 98 */       limpiar();
/*  52: 99 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  53:    */     }
/*  54:    */     catch (Exception e)
/*  55:    */     {
/*  56:101 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  57:102 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  58:    */     }
/*  59:104 */     return "";
/*  60:    */   }
/*  61:    */   
/*  62:    */   public String cambiarNumeroFactura()
/*  63:    */   {
/*  64:    */     try
/*  65:    */     {
/*  66:109 */       this.facturaClienteSRI.getFacturaCliente().setFacturaClienteSRI(this.facturaClienteSRI);
/*  67:110 */       this.servicioFacturaCliente.actualizarNumeroFactura(this.facturaClienteSRI);
/*  68:111 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  69:    */     }
/*  70:    */     catch (ExcepcionAS2Ventas e)
/*  71:    */     {
/*  72:113 */       addErrorMessage(getLanguageController().getMensaje("msg_error_factura_reenumerar"));
/*  73:114 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77:116 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  78:117 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  79:    */     }
/*  80:119 */     return "";
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String validarCambioNumeroFactura()
/*  84:    */   {
/*  85:    */     try
/*  86:    */     {
/*  87:124 */       this.servicioFacturaCliente.validarCambioNumeroFactura(this.facturaClienteSRI.getFacturaCliente());
/*  88:    */     }
/*  89:    */     catch (ExcepcionAS2Ventas e)
/*  90:    */     {
/*  91:126 */       addErrorMessage(getLanguageController().getMensaje("msg_error_factura_reenumerar"));
/*  92:127 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  93:    */     }
/*  94:129 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String eliminar()
/*  98:    */   {
/*  99:    */     try
/* 100:    */     {
/* 101:140 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 106:143 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 107:    */     }
/* 108:145 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String cargarDatos()
/* 112:    */   {
/* 113:154 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String limpiar()
/* 117:    */   {
/* 118:163 */     crearEntidad();
/* 119:164 */     return "";
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String procesar()
/* 123:    */   {
/* 124:168 */     this.listaFacturaClienteSRI = this.servicioFacturaClienteSRI.obtenerFacturasPorSerieEntreNumero(this.establecimiento, this.puntoVenta, this.fechaDesde, this.fechaHasta, AppUtil.getOrganizacion());
/* 125:169 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String getEstablecimiento()
/* 129:    */   {
/* 130:186 */     return this.establecimiento;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setEstablecimiento(String establecimiento)
/* 134:    */   {
/* 135:196 */     this.establecimiento = establecimiento;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String getPuntoVenta()
/* 139:    */   {
/* 140:205 */     return this.puntoVenta;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setPuntoVenta(String puntoVenta)
/* 144:    */   {
/* 145:215 */     this.puntoVenta = puntoVenta;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public List<FacturaClienteSRI> getListaFacturaClienteSRI()
/* 149:    */   {
/* 150:224 */     return this.listaFacturaClienteSRI;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setListaFacturaClienteSRI(List<FacturaClienteSRI> listaFacturaClienteSRI)
/* 154:    */   {
/* 155:234 */     this.listaFacturaClienteSRI = listaFacturaClienteSRI;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public Date getFechaDesde()
/* 159:    */   {
/* 160:243 */     return this.fechaDesde;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setFechaDesde(Date fechaDesde)
/* 164:    */   {
/* 165:253 */     this.fechaDesde = fechaDesde;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public Date getFechaHasta()
/* 169:    */   {
/* 170:262 */     return this.fechaHasta;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setFechaHasta(Date fechaHasta)
/* 174:    */   {
/* 175:272 */     this.fechaHasta = fechaHasta;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public FacturaClienteSRI getFacturaClienteSRI()
/* 179:    */   {
/* 180:281 */     return this.facturaClienteSRI;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setFacturaClienteSRI(FacturaClienteSRI facturaClienteSRI)
/* 184:    */   {
/* 185:291 */     this.facturaClienteSRI = facturaClienteSRI;
/* 186:    */   }
/* 187:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.RenumeracionFacturaClienteBean
 * JD-Core Version:    0.7.0.1
 */