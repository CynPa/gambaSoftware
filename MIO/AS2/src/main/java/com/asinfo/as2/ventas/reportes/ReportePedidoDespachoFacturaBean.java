/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.PedidoCliente;
/*   8:    */ import com.asinfo.as2.entities.Producto;
/*   9:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  10:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioPedidoCliente;
/*  17:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReportePedidoDespachoFactura;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Calendar;
/*  21:    */ import java.util.Collection;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.HashMap;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import java.util.TreeMap;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ import org.primefaces.event.SelectEvent;
/*  35:    */ 
/*  36:    */ @ManagedBean
/*  37:    */ @ViewScoped
/*  38:    */ public class ReportePedidoDespachoFacturaBean
/*  39:    */   extends AbstractBaseReportBean
/*  40:    */ {
/*  41:    */   private static final long serialVersionUID = -5635860014279789163L;
/*  42:    */   @EJB
/*  43:    */   private transient ServicioReportePedidoDespachoFactura servicioReportePedidoDespachoFactura;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioEmpresa servicioEmpresa;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioPedidoCliente servicioPedidoCliente;
/*  48:    */   @EJB
/*  49:    */   private transient ServicioProducto servicioProducto;
/*  50: 70 */   private String COMPILE_FILE_NAME = "reportePedidoDespachoFactura";
/*  51:    */   private Date fechaDesde;
/*  52:    */   private Date fechaHasta;
/*  53:    */   private Empresa cliente;
/*  54:    */   private PedidoCliente pedidoCliente;
/*  55:    */   private boolean indicadorReporteDetallado;
/*  56:    */   private Producto producto;
/*  57:    */   private List<PedidoCliente> listaPedidoCliente;
/*  58:    */   
/*  59:    */   protected JRDataSource getJRDataSource()
/*  60:    */   {
/*  61: 90 */     JRDataSource ds = null;
/*  62:    */     
/*  63: 92 */     List<Object[]> listaDatosReportePedidoDespachoFactura = new ArrayList();
/*  64: 93 */     List<Object[]> listaDatosReporteDespachoSinFactura = new ArrayList();
/*  65: 94 */     List<Object[]> listaDatosReportePedidoConFactura = new ArrayList();
/*  66:    */     try
/*  67:    */     {
/*  68: 99 */       LOG.info("000000000");
/*  69:100 */       listaDatosReportePedidoDespachoFactura = this.servicioReportePedidoDespachoFactura.getReportePedidoDespachoFactura(this.pedidoCliente, this.fechaDesde, this.fechaHasta, this.cliente, 
/*  70:101 */         AppUtil.getOrganizacion().getId(), this.producto);
/*  71:102 */       LOG.info("111111111");
/*  72:    */       
/*  73:    */ 
/*  74:105 */       LOG.info("222222222");
/*  75:106 */       listaDatosReporteDespachoSinFactura = this.servicioReportePedidoDespachoFactura.getReporteDespachoSinFactura(this.pedidoCliente, this.fechaDesde, this.fechaHasta, this.cliente, 
/*  76:107 */         AppUtil.getOrganizacion().getId(), this.producto);
/*  77:108 */       LOG.info("333333333");
/*  78:    */       
/*  79:    */ 
/*  80:111 */       LOG.info("44444444");
/*  81:112 */       listaDatosReportePedidoConFactura = this.servicioReportePedidoDespachoFactura.getReportePedidoConFactura(this.pedidoCliente, this.fechaDesde, this.fechaHasta, this.cliente, 
/*  82:113 */         AppUtil.getOrganizacion().getId(), this.producto);
/*  83:114 */       LOG.info("55555555");
/*  84:    */       
/*  85:116 */       Collection coleccionAux = obtenerListaOrdenada(listaDatosReportePedidoDespachoFactura, listaDatosReporteDespachoSinFactura, listaDatosReportePedidoConFactura);
/*  86:    */       
/*  87:118 */       List listaDatosReporte = new ArrayList();
/*  88:119 */       listaDatosReporte.addAll(coleccionAux);
/*  89:    */       
/*  90:121 */       String[] fields = { "numeroFactura", "fechaFactura", "numeroDespacho", "fechaDespacho", "cantidadFacturada", "cantidadDespachada", "cantidadPedida", "numeroPedido", "fechaPedido", "codigoProducto", "nombreProducto", "nombreFiscal" };
/*  91:    */       
/*  92:    */ 
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:128 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  98:    */     }
/*  99:    */     catch (ExcepcionAS2 e)
/* 100:    */     {
/* 101:130 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 102:    */     }
/* 103:136 */     return ds;
/* 104:    */   }
/* 105:    */   
/* 106:    */   @PostConstruct
/* 107:    */   public void init()
/* 108:    */   {
/* 109:141 */     Calendar calfechaDesde = Calendar.getInstance();
/* 110:142 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 111:143 */     this.fechaDesde = calfechaDesde.getTime();
/* 112:144 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 113:    */   }
/* 114:    */   
/* 115:    */   private Collection obtenerListaOrdenada(List<Object[]> listaDatosReportePedidoDespachoFactura, List<Object[]> listaDatosReporteDespachoSinFactura, List<Object[]> listaDatosReportePedidoConFactura)
/* 116:    */   {
/* 117:159 */     Map<String, Object> tmReporte = new TreeMap();
/* 118:162 */     for (Object[] object : listaDatosReportePedidoDespachoFactura)
/* 119:    */     {
/* 120:164 */       String clave = String.valueOf((Integer)object[14]) + ":" + String.valueOf((Integer)object[15]) + ":" + String.valueOf((Integer)object[12]) + ":" + String.valueOf((Integer)object[13]);
/* 121:    */       
/* 122:166 */       tmReporte.put(clave, object);
/* 123:    */     }
/* 124:170 */     for (Object[] object : listaDatosReporteDespachoSinFactura)
/* 125:    */     {
/* 126:172 */       String clave = String.valueOf((Integer)object[14]) + ":" + String.valueOf((Integer)object[15]) + ":" + String.valueOf((Integer)object[12]) + ":" + String.valueOf((Integer)object[13]);
/* 127:174 */       if (!tmReporte.containsKey(clave)) {
/* 128:175 */         tmReporte.put(clave, object);
/* 129:    */       }
/* 130:    */     }
/* 131:181 */     for (Object[] object : listaDatosReportePedidoConFactura)
/* 132:    */     {
/* 133:184 */       String clave = String.valueOf((Integer)object[16]) + ":" + String.valueOf((Integer)object[17]) + ":" + String.valueOf((Integer)object[14]) + ":" + String.valueOf((Integer)object[15]) + ":" + String.valueOf((Integer)object[12]) + ":" + String.valueOf((Integer)object[13]);
/* 134:186 */       if (!tmReporte.containsKey(clave)) {
/* 135:187 */         tmReporte.put(clave, object);
/* 136:    */       }
/* 137:    */     }
/* 138:191 */     return tmReporte.values();
/* 139:    */   }
/* 140:    */   
/* 141:    */   protected Map<String, Object> getReportParameters()
/* 142:    */   {
/* 143:202 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 144:203 */     reportParameters.put("ReportTitle", "Pedidos, Despachos y Factura");
/* 145:204 */     return reportParameters;
/* 146:    */   }
/* 147:    */   
/* 148:    */   protected String getCompileFileName()
/* 149:    */   {
/* 150:214 */     if (this.indicadorReporteDetallado) {
/* 151:215 */       this.COMPILE_FILE_NAME = "reportePedidoDespachoFacturaDetallado";
/* 152:    */     }
/* 153:217 */     return this.COMPILE_FILE_NAME;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String execute()
/* 157:    */   {
/* 158:    */     try
/* 159:    */     {
/* 160:222 */       validaFechas();
/* 161:223 */       super.prepareReport();
/* 162:    */     }
/* 163:    */     catch (JRException e)
/* 164:    */     {
/* 165:226 */       e.printStackTrace();
/* 166:227 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 167:    */     }
/* 168:    */     catch (IOException e)
/* 169:    */     {
/* 170:229 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 171:    */     }
/* 172:231 */     return "";
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 176:    */   {
/* 177:241 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void validaFechas()
/* 181:    */   {
/* 182:248 */     if (this.fechaDesde == null) {
/* 183:249 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 184:    */     }
/* 185:251 */     if (this.fechaHasta == null) {
/* 186:252 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void obtenerPedidosCliente(SelectEvent event)
/* 191:    */   {
/* 192:262 */     Empresa clienteAux = this.servicioEmpresa.obtenerDatosCliente(((Empresa)event.getObject()).getIdEmpresa());
/* 193:263 */     setCliente(clienteAux);
/* 194:265 */     if (this.listaPedidoCliente == null) {
/* 195:266 */       this.listaPedidoCliente = new ArrayList();
/* 196:    */     }
/* 197:268 */     this.listaPedidoCliente.clear();
/* 198:269 */     if (getCliente() != null)
/* 199:    */     {
/* 200:270 */       HashMap<String, String> filters = new HashMap();
/* 201:271 */       filters.put("empresa.idEmpresa", String.valueOf(getCliente().getId()));
/* 202:272 */       filters.put("estado", "=" + Estado.PROCESADO.toString());
/* 203:273 */       this.listaPedidoCliente = this.servicioPedidoCliente.obtenerListaCombo("numero", true, filters);
/* 204:275 */       if ((getPedidoCliente() != null) && (!this.listaPedidoCliente.contains(getPedidoCliente()))) {
/* 205:276 */         this.listaPedidoCliente.add(getPedidoCliente());
/* 206:    */       }
/* 207:    */     }
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List<Producto> autocompletarProducto(String consulta)
/* 211:    */   {
/* 212:288 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Date getFechaDesde()
/* 216:    */   {
/* 217:299 */     return this.fechaDesde;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setFechaDesde(Date fechaDesde)
/* 221:    */   {
/* 222:309 */     this.fechaDesde = fechaDesde;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public Date getFechaHasta()
/* 226:    */   {
/* 227:318 */     return this.fechaHasta;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setFechaHasta(Date fechaHasta)
/* 231:    */   {
/* 232:328 */     this.fechaHasta = fechaHasta;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public Empresa getCliente()
/* 236:    */   {
/* 237:337 */     return this.cliente;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setCliente(Empresa cliente)
/* 241:    */   {
/* 242:347 */     this.cliente = cliente;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public PedidoCliente getPedidoCliente()
/* 246:    */   {
/* 247:356 */     return this.pedidoCliente;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setPedidoCliente(PedidoCliente pedidoCliente)
/* 251:    */   {
/* 252:366 */     this.pedidoCliente = pedidoCliente;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public boolean isIndicadorReporteDetallado()
/* 256:    */   {
/* 257:375 */     return this.indicadorReporteDetallado;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setIndicadorReporteDetallado(boolean indicadorReporteDetallado)
/* 261:    */   {
/* 262:385 */     this.indicadorReporteDetallado = indicadorReporteDetallado;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public List<PedidoCliente> getListaPedido()
/* 266:    */   {
/* 267:389 */     return this.listaPedidoCliente;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public Producto getProducto()
/* 271:    */   {
/* 272:398 */     return this.producto;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setProducto(Producto producto)
/* 276:    */   {
/* 277:408 */     this.producto = producto;
/* 278:    */   }
/* 279:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePedidoDespachoFacturaBean
 * JD-Core Version:    0.7.0.1
 */