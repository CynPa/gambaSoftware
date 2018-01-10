/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Empleado;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*   9:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  10:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReportePedidoDespachoFactura;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.math.BigDecimal;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Calendar;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.annotation.PostConstruct;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  26:    */ import net.sf.jasperreports.engine.JRException;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class ReportePedidosBean
/*  32:    */   extends AbstractBaseReportBean
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = 1584134027790279238L;
/*  35:    */   @EJB
/*  36:    */   private ServicioReportePedidoDespachoFactura servicioReportePedidoDespachoFactura;
/*  37:    */   @EJB
/*  38:    */   private ServicioEmpresa servicioEmpresa;
/*  39:    */   @EJB
/*  40:    */   private ServicioUsuario servicioUsuario;
/*  41:    */   @EJB
/*  42:    */   private ServicioProducto servicioProducto;
/*  43:    */   private Empresa empresa;
/*  44:    */   private Producto producto;
/*  45:    */   private Date fechaDesde;
/*  46:    */   private Date fechaHasta;
/*  47:    */   private String numeroFacturaDesde;
/*  48:    */   private String numeroFacturaHasta;
/*  49:    */   private List<Empresa> listaClienteCombo;
/*  50: 72 */   private boolean indicadorResumen = false;
/*  51:    */   private boolean indicadorElaborado;
/*  52:    */   
/*  53:    */   protected JRDataSource getJRDataSource()
/*  54:    */   {
/*  55: 84 */     List listaDatosReportePedidos = new ArrayList();
/*  56: 85 */     List listaDatosReportePedidosResumido = new ArrayList();
/*  57: 86 */     JRDataSource ds = null;
/*  58: 87 */     this.empresa.getEmpleado().getId();
/*  59:    */     String[] fields;
/*  60: 88 */     if (!this.indicadorResumen)
/*  61:    */     {
/*  62: 89 */       listaDatosReportePedidos = this.servicioReportePedidoDespachoFactura.getReportePedidos(this.fechaDesde, this.fechaHasta, this.empresa.getId(), this.producto, this.indicadorElaborado);
/*  63: 90 */       fields = new String[] { "f_nombreCliente", "f_transportista", "f_numeroPedido", "f_fechaPedido", "f_codigoProducto", "f_nombreProducto", "f_cantidad" };
/*  64: 91 */       ds = new QueryResultDataSource(listaDatosReportePedidos, fields);
/*  65:    */     }
/*  66:    */     else
/*  67:    */     {
/*  68: 95 */       listaDatosReportePedidosResumido = this.servicioReportePedidoDespachoFactura.getReportePedidosResumido(this.fechaDesde, this.fechaHasta, this.producto, this.indicadorElaborado);
/*  69: 96 */       for (Object object : listaDatosReportePedidosResumido)
/*  70:    */       {
/*  71: 97 */         Object[] objeto = (Object[])object;
/*  72: 98 */         int idProducto = Integer.parseInt(objeto[0].toString());
/*  73: 99 */         BigDecimal saldo = this.servicioProducto.getSaldo(idProducto, this.fechaHasta);
/*  74:100 */         objeto[5] = saldo;
/*  75:    */       }
/*  76:102 */       String[] fields = { "f_idProducto", "f_fecha", "f_codigoProducto", "f_nombreProducto", "f_sumCantidad", "f_saldo" };
/*  77:103 */       ds = new QueryResultDataSource(listaDatosReportePedidosResumido, fields);
/*  78:    */     }
/*  79:106 */     return ds;
/*  80:    */   }
/*  81:    */   
/*  82:    */   @PostConstruct
/*  83:    */   public void init()
/*  84:    */   {
/*  85:111 */     Calendar calfechaDesde = Calendar.getInstance();
/*  86:112 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  87:113 */     this.fechaDesde = calfechaDesde.getTime();
/*  88:114 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected String getCompileFileName()
/*  92:    */   {
/*  93:126 */     if (this.indicadorResumen) {
/*  94:127 */       return "reportePedidosResumido";
/*  95:    */     }
/*  96:129 */     return "reportePedidos";
/*  97:    */   }
/*  98:    */   
/*  99:    */   protected Map<String, Object> getReportParameters()
/* 100:    */   {
/* 101:142 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 102:143 */     reportParameters.put("ReportTitle", "Reporte Pedido Vs Stock");
/* 103:144 */     if (!this.indicadorResumen) {
/* 104:145 */       reportParameters.put("ReportTitle", "Reporte Pedidos");
/* 105:    */     }
/* 106:147 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 107:148 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 108:149 */     reportParameters.put("p_cliente", getEmpresa() != null ? getEmpresa().getNombreFiscal() : "Todos");
/* 109:150 */     return reportParameters;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String execute()
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:160 */       validaDatos();
/* 117:161 */       super.prepareReport();
/* 118:    */     }
/* 119:    */     catch (JRException e)
/* 120:    */     {
/* 121:163 */       LOG.info("Error JRException");
/* 122:164 */       e.printStackTrace();
/* 123:165 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 124:    */     }
/* 125:    */     catch (IOException e)
/* 126:    */     {
/* 127:167 */       LOG.info("Error IOException");
/* 128:168 */       e.printStackTrace();
/* 129:169 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 130:    */     }
/* 131:172 */     return null;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void validaDatos()
/* 135:    */   {
/* 136:177 */     if (this.empresa == null)
/* 137:    */     {
/* 138:178 */       this.empresa = new Empresa();
/* 139:179 */       this.empresa.setId(-1);
/* 140:    */     }
/* 141:181 */     if (this.fechaDesde == null) {
/* 142:182 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 143:    */     }
/* 144:184 */     if (this.fechaHasta == null) {
/* 145:185 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   public Date getFechaDesde()
/* 150:    */   {
/* 151:195 */     return this.fechaDesde;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setFechaDesde(Date fechaDesde)
/* 155:    */   {
/* 156:205 */     this.fechaDesde = fechaDesde;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public Date getFechaHasta()
/* 160:    */   {
/* 161:214 */     return this.fechaHasta;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setFechaHasta(Date fechaHasta)
/* 165:    */   {
/* 166:224 */     this.fechaHasta = fechaHasta;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public String getNumeroFacturaDesde()
/* 170:    */   {
/* 171:233 */     return this.numeroFacturaDesde;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setNumeroFacturaDesde(String numeroFacturaDesde)
/* 175:    */   {
/* 176:243 */     this.numeroFacturaDesde = numeroFacturaDesde;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String getNumeroFacturaHasta()
/* 180:    */   {
/* 181:252 */     return this.numeroFacturaHasta;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setNumeroFacturaHasta(String numeroFacturaHasta)
/* 185:    */   {
/* 186:262 */     this.numeroFacturaHasta = numeroFacturaHasta;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public Empresa getEmpresa()
/* 190:    */   {
/* 191:271 */     if (this.empresa == null)
/* 192:    */     {
/* 193:272 */       this.empresa = new Empresa();
/* 194:273 */       this.empresa.setId(0);
/* 195:    */     }
/* 196:275 */     return this.empresa;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setEmpresa(Empresa empresa)
/* 200:    */   {
/* 201:285 */     this.empresa = empresa;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean isIndicadorResumen()
/* 205:    */   {
/* 206:294 */     return this.indicadorResumen;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 210:    */   {
/* 211:304 */     this.indicadorResumen = indicadorResumen;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 215:    */   {
/* 216:308 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public List<Empresa> getListaClienteCombo()
/* 220:    */   {
/* 221:317 */     if (this.listaClienteCombo == null) {
/* 222:318 */       this.listaClienteCombo = this.servicioEmpresa.obtenerClientes();
/* 223:    */     }
/* 224:320 */     return this.listaClienteCombo;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setListaClienteCombo(List<Empresa> listaClienteCombo)
/* 228:    */   {
/* 229:330 */     this.listaClienteCombo = listaClienteCombo;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public Producto getProducto()
/* 233:    */   {
/* 234:335 */     return this.producto;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setProducto(Producto producto)
/* 238:    */   {
/* 239:339 */     this.producto = producto;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public boolean isIndicadorElaborado()
/* 243:    */   {
/* 244:343 */     return this.indicadorElaborado;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void setIndicadorElaborado(boolean indicadorElaborado)
/* 248:    */   {
/* 249:347 */     this.indicadorElaborado = indicadorElaborado;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void cargarProducto() {}
/* 253:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReportePedidosBean
 * JD-Core Version:    0.7.0.1
 */