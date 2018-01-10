/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   7:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   8:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.FormaPago;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioAnticipoCliente;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  15:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import java.math.BigDecimal;
/*  18:    */ import java.util.List;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import org.apache.log4j.Logger;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class DevolucionAnticipoClienteBean
/*  28:    */   extends PageControllerAS2
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -2663447654948526952L;
/*  31:    */   @EJB
/*  32:    */   private ServicioAnticipoCliente servicioAnticipoCliente;
/*  33:    */   @EJB
/*  34:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  35:    */   @EJB
/*  36:    */   private ServicioDocumento servicioDocumento;
/*  37:    */   @EJB
/*  38:    */   private ServicioSecuencia servicioSecuencia;
/*  39:    */   private AnticipoCliente anticipoCliente;
/*  40:    */   private List<Documento> listaDocumentoCombo;
/*  41:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacionCombo;
/*  42:    */   
/*  43:    */   @PostConstruct
/*  44:    */   public void init()
/*  45:    */   {
/*  46: 81 */     this.anticipoCliente = ((AnticipoCliente)AppUtil.getAtributo("anticipo_cliente"));
/*  47: 83 */     if (this.anticipoCliente != null)
/*  48:    */     {
/*  49: 84 */       this.anticipoCliente = this.servicioAnticipoCliente.cargarDetalle(this.anticipoCliente.getIdAnticipoCliente());
/*  50: 85 */       if (this.anticipoCliente.getCuentaBancariaOrganizacionDevolucion() != null)
/*  51:    */       {
/*  52: 86 */         cancelar();
/*  53:    */       }
/*  54:    */       else
/*  55:    */       {
/*  56: 88 */         this.anticipoCliente.setCuentaBancariaOrganizacionDevolucion(new CuentaBancariaOrganizacion());
/*  57: 89 */         this.anticipoCliente.setValorDevolucion(this.anticipoCliente.getSaldo());
/*  58: 90 */         if ((getListaDocumentoCombo() != null) && (!getListaDocumentoCombo().isEmpty()))
/*  59:    */         {
/*  60: 91 */           Documento documento = (Documento)getListaDocumentoCombo().get(0);
/*  61: 92 */           this.anticipoCliente.setDocumentoDevolucion(documento);
/*  62:    */         }
/*  63: 95 */         setEditado(true);
/*  64:    */       }
/*  65:    */     }
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String editar()
/*  69:    */   {
/*  70:110 */     if (getAnticipoCliente().getIdAnticipoCliente() > 0) {
/*  71:111 */       setEditado(true);
/*  72:    */     } else {
/*  73:113 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  74:    */     }
/*  75:115 */     return "";
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String guardar()
/*  79:    */   {
/*  80:124 */     String resultado = "";
/*  81:    */     try
/*  82:    */     {
/*  83:127 */       if (validarSaldoDevolucion(this.anticipoCliente))
/*  84:    */       {
/*  85:128 */         this.servicioAnticipoCliente.guardarDevolucion(getAnticipoCliente());
/*  86:129 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  87:130 */         setEditado(false);
/*  88:131 */         limpiar();
/*  89:132 */         resultado = "anticipoCliente?faces-redirect=true";
/*  90:    */       }
/*  91:    */       else
/*  92:    */       {
/*  93:134 */         addErrorMessage(getLanguageController().getMensaje("msg_info_saldo_devolucion"));
/*  94:    */       }
/*  95:    */     }
/*  96:    */     catch (ExcepcionAS2Financiero e)
/*  97:    */     {
/*  98:138 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  99:139 */       LOG.info("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:142 */       addErrorMessage(getLanguageController().getMensaje("msg_error_editar"));
/* 104:143 */       LOG.error("ERROR AL EDITAR FACTURA PROVEEDOR:", e);
/* 105:    */     }
/* 106:145 */     return resultado;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String eliminar()
/* 110:    */   {
/* 111:    */     try
/* 112:    */     {
/* 113:155 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 114:    */     }
/* 115:    */     catch (Exception e)
/* 116:    */     {
/* 117:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 118:158 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 119:    */     }
/* 120:160 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String cargarDatos()
/* 124:    */   {
/* 125:169 */     return "";
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String limpiar()
/* 129:    */   {
/* 130:178 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String cancelar()
/* 134:    */   {
/* 135:183 */     return "anticipoCliente?faces-redirect=true";
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void actualizarCuentaBancariaOrganizacion()
/* 139:    */   {
/* 140:190 */     this.anticipoCliente.setCuentaBancariaOrganizacionDevolucion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(getAnticipoCliente()
/* 141:191 */       .getCuentaBancariaOrganizacionDevolucion().getId()));
/* 142:    */   }
/* 143:    */   
/* 144:    */   public boolean validarSaldoDevolucion(AnticipoCliente anticipoCliente)
/* 145:    */   {
/* 146:195 */     boolean resultado = false;
/* 147:196 */     if (anticipoCliente.getSaldo().compareTo(anticipoCliente.getValorDevolucion()) == 0) {
/* 148:197 */       resultado = true;
/* 149:    */     }
/* 150:199 */     return resultado;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void actualizarFormaPago()
/* 154:    */   {
/* 155:207 */     String numero = "";
/* 156:208 */     int idFormaPago = getAnticipoCliente().getFormaPagoDevolucion().getId();
/* 157:209 */     int idCuentaBancariaOrganizacion = getAnticipoCliente().getCuentaBancariaOrganizacionDevolucion().getId();
/* 158:210 */     getAnticipoCliente().setSecuencia(this.servicioCuentaBancariaOrganizacion.obtenerSecuenciaPorFormaPago(idCuentaBancariaOrganizacion, idFormaPago));
/* 159:212 */     if (getAnticipoCliente().getSecuencia() != null) {
/* 160:    */       try
/* 161:    */       {
/* 162:215 */         numero = this.servicioSecuencia.obtenerSecuencia(getAnticipoCliente().getSecuencia(), getAnticipoCliente().getFechaDevolucion());
/* 163:216 */         getAnticipoCliente().setDocumentoReferenciaDevolucion(numero);
/* 164:    */       }
/* 165:    */       catch (ExcepcionAS2 e)
/* 166:    */       {
/* 167:218 */         addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 168:219 */         e.printStackTrace();
/* 169:    */       }
/* 170:    */     }
/* 171:223 */     getAnticipoCliente().setDocumentoReferenciaDevolucion(numero);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public AnticipoCliente getAnticipoCliente()
/* 175:    */   {
/* 176:236 */     return this.anticipoCliente;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setAnticipoCliente(AnticipoCliente anticipoCliente)
/* 180:    */   {
/* 181:246 */     this.anticipoCliente = anticipoCliente;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public List<Documento> getListaDocumentoCombo()
/* 185:    */   {
/* 186:255 */     if (this.listaDocumentoCombo == null) {
/* 187:    */       try
/* 188:    */       {
/* 189:257 */         this.listaDocumentoCombo = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.DEVOLUCION_ANTICIPO_CLIENTE);
/* 190:    */       }
/* 191:    */       catch (ExcepcionAS2 e)
/* 192:    */       {
/* 193:259 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 194:    */       }
/* 195:    */     }
/* 196:262 */     return this.listaDocumentoCombo;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setListaDocumentoCombo(List<Documento> listaDocumentoCombo)
/* 200:    */   {
/* 201:272 */     this.listaDocumentoCombo = listaDocumentoCombo;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacionCombo()
/* 205:    */   {
/* 206:281 */     if (this.listaCuentaBancariaOrganizacionCombo == null) {
/* 207:282 */       this.listaCuentaBancariaOrganizacionCombo = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, true, null);
/* 208:    */     }
/* 209:284 */     return this.listaCuentaBancariaOrganizacionCombo;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setListaCuentaBancariaOrganizacionCombo(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacionCombo)
/* 213:    */   {
/* 214:295 */     this.listaCuentaBancariaOrganizacionCombo = listaCuentaBancariaOrganizacionCombo;
/* 215:    */   }
/* 216:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.DevolucionAnticipoClienteBean
 * JD-Core Version:    0.7.0.1
 */