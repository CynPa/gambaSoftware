/*   1:    */ package com.asinfo.as2.finaciero.cobros.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.AnticipoCliente;
/*   8:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*   9:    */ import com.asinfo.as2.entities.DetalleLiquidacionAnticipoCliente;
/*  10:    */ import com.asinfo.as2.entities.Documento;
/*  11:    */ import com.asinfo.as2.entities.Empresa;
/*  12:    */ import com.asinfo.as2.entities.LiquidacionAnticipoCliente;
/*  13:    */ import com.asinfo.as2.entities.Organizacion;
/*  14:    */ import com.asinfo.as2.entities.Sucursal;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.cobros.procesos.servicio.ServicioLiquidacionAnticipoCliente;
/*  19:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  22:    */ import java.math.BigDecimal;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.List;
/*  25:    */ import javax.annotation.PostConstruct;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ import org.primefaces.component.datatable.DataTable;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class LiquidacionAnticipoClienteBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -5048629620982061155L;
/*  38:    */   @EJB
/*  39:    */   private ServicioLiquidacionAnticipoCliente servicioLiquidacionAnticipoCliente;
/*  40:    */   @EJB
/*  41:    */   private ServicioDocumento servicioDocumento;
/*  42:    */   @EJB
/*  43:    */   private ServicioEmpresa servicioEmpresa;
/*  44:    */   @EJB
/*  45:    */   private ServicioFacturaCliente servicioFacturaCliente;
/*  46:    */   private LiquidacionAnticipoCliente liquidacionAnticipoCliente;
/*  47:    */   private AnticipoCliente anticipoCliente;
/*  48:    */   private List<LiquidacionAnticipoCliente> listaLiquidacionAnticipoCliente;
/*  49: 71 */   private BigDecimal totalLiquidado = BigDecimal.ZERO;
/*  50:    */   private List<Empresa> listaEmpresa;
/*  51:    */   private List<Documento> listaDocumento;
/*  52:    */   private List<AnticipoCliente> listaAnticipoCliente;
/*  53:    */   private DataTable dtLiquidacionAnticipoCliente;
/*  54:    */   private DataTable dtDetalleLiquidacionAnticipoCliente;
/*  55:    */   
/*  56:    */   @PostConstruct
/*  57:    */   public void init()
/*  58:    */   {
/*  59: 84 */     this.anticipoCliente = ((AnticipoCliente)AppUtil.getAtributo("anticipo_cliente"));
/*  60: 85 */     getLiquidacionAnticipoCliente().setAnticipoCliente(this.anticipoCliente);
/*  61: 86 */     setEditado(true);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String cancelar()
/*  65:    */   {
/*  66: 91 */     return "anticipoCliente?faces-redirect=true";
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String editar()
/*  70:    */   {
/*  71:101 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  72:102 */     return "";
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String guardar()
/*  76:    */   {
/*  77:    */     try
/*  78:    */     {
/*  79:114 */       this.servicioLiquidacionAnticipoCliente.guardar(this.liquidacionAnticipoCliente);
/*  80:    */       
/*  81:116 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  82:    */       
/*  83:118 */       return "anticipoCliente?faces-redirect=true";
/*  84:    */     }
/*  85:    */     catch (ExcepcionAS2Financiero e)
/*  86:    */     {
/*  87:120 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  88:121 */       LOG.info("ERROR AL GUARDAR DATOS", e);
/*  89:122 */       return "";
/*  90:    */     }
/*  91:    */     catch (ExcepcionAS2 e)
/*  92:    */     {
/*  93:124 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  94:125 */       LOG.info("ERROR AL GUARDAR DATOS", e);
/*  95:126 */       return "";
/*  96:    */     }
/*  97:    */     catch (Exception e)
/*  98:    */     {
/*  99:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 100:129 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 101:    */     }
/* 102:131 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public String eliminar()
/* 106:    */   {
/* 107:143 */     return null;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String limpiar()
/* 111:    */   {
/* 112:153 */     crearLiquidacionAnticipoCliente();
/* 113:154 */     return "";
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String cargarDatos()
/* 117:    */   {
/* 118:164 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   private void crearLiquidacionAnticipoCliente()
/* 122:    */   {
/* 123:168 */     this.liquidacionAnticipoCliente = new LiquidacionAnticipoCliente();
/* 124:169 */     this.liquidacionAnticipoCliente.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 125:170 */     this.liquidacionAnticipoCliente.setIdSucursal(AppUtil.getSucursal().getId());
/* 126:171 */     this.liquidacionAnticipoCliente.setFecha(new Date());
/* 127:172 */     this.liquidacionAnticipoCliente.setEstado(Estado.ELABORADO);
/* 128:173 */     this.liquidacionAnticipoCliente.setAnticipoCliente(new AnticipoCliente());
/* 129:175 */     if ((getListaDocumento() != null) && (!getListaDocumento().isEmpty()))
/* 130:    */     {
/* 131:176 */       Documento documento = (Documento)getListaDocumento().get(0);
/* 132:177 */       this.liquidacionAnticipoCliente.setDocumento(documento);
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void cargarFacturasPendientes()
/* 137:    */   {
/* 138:188 */     this.liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente().clear();
/* 139:    */     
/* 140:190 */     int idEmpresa = this.liquidacionAnticipoCliente.getAnticipoCliente().getEmpresa().getId();
/* 141:    */     
/* 142:192 */     List<CuentaPorCobrar> listaFacturasPendientes = this.servicioFacturaCliente.obtenerFacturasPendientes(idEmpresa);
/* 143:    */     
/* 144:194 */     BigDecimal valorPago = this.liquidacionAnticipoCliente.getAnticipoCliente().getSaldo();
/* 145:196 */     for (CuentaPorCobrar cxc : listaFacturasPendientes)
/* 146:    */     {
/* 147:197 */       DetalleLiquidacionAnticipoCliente detalleLiquidacionAnticipoCliente = new DetalleLiquidacionAnticipoCliente();
/* 148:198 */       detalleLiquidacionAnticipoCliente.setLiquidacionAnticipoCliente(this.liquidacionAnticipoCliente);
/* 149:199 */       detalleLiquidacionAnticipoCliente.setCuentaPorCobrar(cxc);
/* 150:    */       
/* 151:201 */       BigDecimal saldoCuentaPorCobra = cxc.getSaldo().subtract(cxc.getValorBloqueado());
/* 152:202 */       BigDecimal valor = BigDecimal.ZERO;
/* 153:204 */       if (valorPago.compareTo(saldoCuentaPorCobra) > 0) {
/* 154:205 */         valor = saldoCuentaPorCobra;
/* 155:    */       } else {
/* 156:207 */         valor = valorPago;
/* 157:    */       }
/* 158:210 */       detalleLiquidacionAnticipoCliente.setValor(valor);
/* 159:211 */       valorPago = valorPago.subtract(valor);
/* 160:    */       
/* 161:213 */       this.liquidacionAnticipoCliente.getListaDetalleLiquidacionAnticipoCliente().add(detalleLiquidacionAnticipoCliente);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   public LiquidacionAnticipoCliente getLiquidacionAnticipoCliente()
/* 166:    */   {
/* 167:218 */     if (this.liquidacionAnticipoCliente == null) {
/* 168:219 */       crearLiquidacionAnticipoCliente();
/* 169:    */     }
/* 170:221 */     return this.liquidacionAnticipoCliente;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setLiquidacionAnticipoCliente(LiquidacionAnticipoCliente liquidacionAnticipoCliente)
/* 174:    */   {
/* 175:225 */     this.liquidacionAnticipoCliente = liquidacionAnticipoCliente;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public List<LiquidacionAnticipoCliente> getListaLiquidacionAnticipoCliente()
/* 179:    */   {
/* 180:229 */     if (this.listaLiquidacionAnticipoCliente == null) {
/* 181:230 */       cargarDatos();
/* 182:    */     }
/* 183:232 */     return this.listaLiquidacionAnticipoCliente;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setListaLiquidacionAnticipoCliente(List<LiquidacionAnticipoCliente> listaLiquidacionAnticipoCliente)
/* 187:    */   {
/* 188:236 */     this.listaLiquidacionAnticipoCliente = listaLiquidacionAnticipoCliente;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public List<Empresa> getListaEmpresa()
/* 192:    */   {
/* 193:240 */     if (this.listaEmpresa == null) {
/* 194:241 */       this.listaEmpresa = this.servicioEmpresa.obtenerClientes();
/* 195:    */     }
/* 196:244 */     return this.listaEmpresa;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setListaEmpresa(List<Empresa> listaEmpresa)
/* 200:    */   {
/* 201:248 */     this.listaEmpresa = listaEmpresa;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public List<Documento> getListaDocumento()
/* 205:    */   {
/* 206:252 */     if (this.listaDocumento == null) {
/* 207:    */       try
/* 208:    */       {
/* 209:254 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.LIQUIDACION_ANTICIPO_CLIENTE);
/* 210:    */       }
/* 211:    */       catch (ExcepcionAS2 e)
/* 212:    */       {
/* 213:256 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 214:    */       }
/* 215:    */     }
/* 216:259 */     return this.listaDocumento;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 220:    */   {
/* 221:263 */     this.listaDocumento = listaDocumento;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public List<AnticipoCliente> getListaAnticipoCliente()
/* 225:    */   {
/* 226:267 */     return this.listaAnticipoCliente;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setListaAnticipoCliente(List<AnticipoCliente> listaAnticipoCliente)
/* 230:    */   {
/* 231:271 */     this.listaAnticipoCliente = listaAnticipoCliente;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public DataTable getDtLiquidacionAnticipoCliente()
/* 235:    */   {
/* 236:275 */     return this.dtLiquidacionAnticipoCliente;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setDtLiquidacionAnticipoCliente(DataTable dtLiquidacionAnticipoCliente)
/* 240:    */   {
/* 241:279 */     this.dtLiquidacionAnticipoCliente = dtLiquidacionAnticipoCliente;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public DataTable getDtDetalleLiquidacionAnticipoCliente()
/* 245:    */   {
/* 246:283 */     return this.dtDetalleLiquidacionAnticipoCliente;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void setDtDetalleLiquidacionAnticipoCliente(DataTable dtDetalleLiquidacionAnticipoCliente)
/* 250:    */   {
/* 251:287 */     this.dtDetalleLiquidacionAnticipoCliente = dtDetalleLiquidacionAnticipoCliente;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public List<DetalleLiquidacionAnticipoCliente> getListaDetalleLiquidacionAnticipoCliente()
/* 255:    */   {
/* 256:292 */     return getLiquidacionAnticipoCliente().getListaDetalleLiquidacionAnticipoCliente();
/* 257:    */   }
/* 258:    */   
/* 259:    */   public BigDecimal getTotalLiquidado()
/* 260:    */   {
/* 261:296 */     this.totalLiquidado = BigDecimal.ZERO;
/* 262:298 */     for (DetalleLiquidacionAnticipoCliente detalleCobro : getLiquidacionAnticipoCliente().getListaDetalleLiquidacionAnticipoCliente()) {
/* 263:299 */       if (!detalleCobro.isEliminado()) {
/* 264:300 */         this.totalLiquidado = this.totalLiquidado.add(detalleCobro.getValor());
/* 265:    */       }
/* 266:    */     }
/* 267:303 */     return this.totalLiquidado;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setTotalLiquidado(BigDecimal totalLiquidado)
/* 271:    */   {
/* 272:307 */     this.totalLiquidado = totalLiquidado;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public AnticipoCliente getAnticipoCliente()
/* 276:    */   {
/* 277:311 */     return this.anticipoCliente;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setAnticipoCliente(AnticipoCliente anticipoCliente)
/* 281:    */   {
/* 282:315 */     this.anticipoCliente = anticipoCliente;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public String limpiarValorCuotaLiquidar()
/* 286:    */   {
/* 287:320 */     DetalleLiquidacionAnticipoCliente d = (DetalleLiquidacionAnticipoCliente)this.dtDetalleLiquidacionAnticipoCliente.getRowData();
/* 288:321 */     d.setValor(BigDecimal.ZERO);
/* 289:322 */     getTotalLiquidado();
/* 290:    */     
/* 291:324 */     return "";
/* 292:    */   }
/* 293:    */   
/* 294:    */   public String limpiarValorCuotaLiquidarTodo()
/* 295:    */   {
/* 296:328 */     this.totalLiquidado = BigDecimal.ZERO;
/* 297:330 */     for (DetalleLiquidacionAnticipoCliente detalleCobro : getLiquidacionAnticipoCliente().getListaDetalleLiquidacionAnticipoCliente()) {
/* 298:331 */       if (!detalleCobro.isEliminado()) {
/* 299:332 */         detalleCobro.setValor(BigDecimal.ZERO);
/* 300:    */       }
/* 301:    */     }
/* 302:335 */     return "";
/* 303:    */   }
/* 304:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.procesos.controller.LiquidacionAnticipoClienteBean
 * JD-Core Version:    0.7.0.1
 */