/*   1:    */ package com.asinfo.as2.finaciero.pagos.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.entities.AnticipoProveedor;
/*   7:    */ import com.asinfo.as2.entities.CuentaBancariaOrganizacion;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.FormaPago;
/*  10:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCuentaBancariaOrganizacion;
/*  13:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  14:    */ import com.asinfo.as2.financiero.pagos.procesos.servicio.ServicioAnticipoProveedor;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import java.math.BigDecimal;
/*  17:    */ import java.util.List;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class DevolucionAnticipoProveedorBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -5404991454559428854L;
/*  30:    */   @EJB
/*  31:    */   private ServicioAnticipoProveedor servicioAnticipoProveedor;
/*  32:    */   @EJB
/*  33:    */   private ServicioCuentaBancariaOrganizacion servicioCuentaBancariaOrganizacion;
/*  34:    */   @EJB
/*  35:    */   private ServicioDocumento servicioDocumento;
/*  36:    */   private AnticipoProveedor anticipoProveedor;
/*  37:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  38:    */   private List<Documento> listaDocumentoCombo;
/*  39:    */   private List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacionCombo;
/*  40:    */   
/*  41:    */   @PostConstruct
/*  42:    */   public void init()
/*  43:    */   {
/*  44: 79 */     this.anticipoProveedor = ((AnticipoProveedor)AppUtil.getAtributo("anticipo_proveedor"));
/*  45: 81 */     if (this.anticipoProveedor != null) {
/*  46: 83 */       if (this.anticipoProveedor.getCuentaBancariaOrganizacionDevolucion() != null)
/*  47:    */       {
/*  48: 84 */         cancelar();
/*  49:    */       }
/*  50:    */       else
/*  51:    */       {
/*  52: 86 */         this.anticipoProveedor.setDocumentoDevolucion(new Documento());
/*  53: 87 */         this.anticipoProveedor.setCuentaBancariaOrganizacionDevolucion(new CuentaBancariaOrganizacion());
/*  54: 88 */         this.anticipoProveedor.setFormaPagoDevolucion(new FormaPago());
/*  55: 89 */         this.anticipoProveedor.setValorDevolucion(this.anticipoProveedor.getSaldo());
/*  56: 90 */         setEditado(true);
/*  57:    */       }
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public String editar()
/*  62:    */   {
/*  63:105 */     if (getAnticipoProveedor().getIdAnticipoProveedor() > 0) {
/*  64:106 */       setEditado(true);
/*  65:    */     } else {
/*  66:108 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  67:    */     }
/*  68:110 */     return "";
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String guardar()
/*  72:    */   {
/*  73:119 */     String resultado = "";
/*  74:    */     try
/*  75:    */     {
/*  76:122 */       this.servicioAnticipoProveedor.guardarDevolucion(getAnticipoProveedor());
/*  77:123 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  78:124 */       setEditado(false);
/*  79:125 */       limpiar();
/*  80:126 */       resultado = "anticipoProveedor?faces-redirect=true";
/*  81:    */     }
/*  82:    */     catch (ExcepcionAS2Financiero e)
/*  83:    */     {
/*  84:129 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  85:130 */       LOG.info("ERROR AL GUARDAR DEVOLUCION:", e);
/*  86:    */     }
/*  87:    */     catch (ExcepcionAS2 e)
/*  88:    */     {
/*  89:133 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  90:    */     }
/*  91:138 */     return resultado;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String eliminar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:148 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:150 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 103:151 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 104:    */     }
/* 105:153 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String cargarDatos()
/* 109:    */   {
/* 110:162 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String limpiar()
/* 114:    */   {
/* 115:171 */     return "";
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String cancelar()
/* 119:    */   {
/* 120:176 */     return "anticipoProveedor?faces-redirect=true";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void actualizarDocumento()
/* 124:    */   {
/* 125:180 */     Documento documento = this.servicioDocumento.buscarPorId(Integer.valueOf(getAnticipoProveedor().getDocumentoDevolucion().getId()));
/* 126:181 */     this.anticipoProveedor.setDocumentoDevolucion(documento);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void actualizarFormaPago()
/* 130:    */   {
/* 131:188 */     this.anticipoProveedor.setCuentaBancariaOrganizacionDevolucion(this.servicioCuentaBancariaOrganizacion.cargarDetalle(getAnticipoProveedor()
/* 132:189 */       .getCuentaBancariaOrganizacionDevolucion().getId()));
/* 133:    */   }
/* 134:    */   
/* 135:    */   public boolean validarSaldoDevolucion(AnticipoProveedor anticipoProveedor)
/* 136:    */   {
/* 137:193 */     boolean resultado = false;
/* 138:194 */     if (anticipoProveedor.getSaldo().compareTo(anticipoProveedor.getValorDevolucion()) == 0) {
/* 139:195 */       resultado = true;
/* 140:    */     }
/* 141:197 */     return resultado;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public AnticipoProveedor getAnticipoProveedor()
/* 145:    */   {
/* 146:210 */     return this.anticipoProveedor;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setAnticipoProveedor(AnticipoProveedor anticipoProveedor)
/* 150:    */   {
/* 151:220 */     this.anticipoProveedor = anticipoProveedor;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 155:    */   {
/* 156:229 */     if (this.cuentaBancariaOrganizacion == null) {
/* 157:230 */       this.cuentaBancariaOrganizacion = new CuentaBancariaOrganizacion();
/* 158:    */     }
/* 159:232 */     return this.cuentaBancariaOrganizacion;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 163:    */   {
/* 164:242 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<Documento> getListaDocumentoCombo()
/* 168:    */   {
/* 169:251 */     if (this.listaDocumentoCombo == null) {
/* 170:    */       try
/* 171:    */       {
/* 172:253 */         this.listaDocumentoCombo = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.DEVOLUCION_ANTICIPO_PROVEEDOR);
/* 173:    */       }
/* 174:    */       catch (ExcepcionAS2 e)
/* 175:    */       {
/* 176:255 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 177:    */       }
/* 178:    */     }
/* 179:258 */     return this.listaDocumentoCombo;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setListaDocumentoCombo(List<Documento> listaDocumentoCombo)
/* 183:    */   {
/* 184:268 */     this.listaDocumentoCombo = listaDocumentoCombo;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<CuentaBancariaOrganizacion> getListaCuentaBancariaOrganizacionCombo()
/* 188:    */   {
/* 189:277 */     if (this.listaCuentaBancariaOrganizacionCombo == null) {
/* 190:278 */       this.listaCuentaBancariaOrganizacionCombo = this.servicioCuentaBancariaOrganizacion.obtenerListaCombo(null, true, null);
/* 191:    */     }
/* 192:280 */     return this.listaCuentaBancariaOrganizacionCombo;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setListaCuentaBancariaOrganizacionCombo(List<CuentaBancariaOrganizacion> listaCuentaBancariaOrganizacionCombo)
/* 196:    */   {
/* 197:290 */     this.listaCuentaBancariaOrganizacionCombo = listaCuentaBancariaOrganizacionCombo;
/* 198:    */   }
/* 199:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.procesos.controller.DevolucionAnticipoProveedorBean
 * JD-Core Version:    0.7.0.1
 */