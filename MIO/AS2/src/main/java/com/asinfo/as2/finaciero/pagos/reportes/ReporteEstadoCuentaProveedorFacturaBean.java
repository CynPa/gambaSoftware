/*   1:    */ package com.asinfo.as2.finaciero.pagos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteEstadoCuentaFactura;
/*   4:    */ import com.asinfo.as2.compras.procesos.servicio.ServicioFacturaProveedor;
/*   5:    */ import com.asinfo.as2.compras.reportes.servicio.ServicioReporteCompra;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.FacturaProveedor;
/*  10:    */ import com.asinfo.as2.entities.sri.FacturaProveedorSRI;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  14:    */ import java.math.BigDecimal;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.event.SelectEvent;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class ReporteEstadoCuentaProveedorFacturaBean
/*  31:    */   extends AbstractBaseReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 3861782987466876655L;
/*  34:    */   @EJB
/*  35:    */   private transient ServicioEmpresa servicioEmpresa;
/*  36:    */   @EJB
/*  37:    */   private transient ServicioReporteCompra servicioReporteCompra;
/*  38:    */   @EJB
/*  39:    */   private transient ServicioFacturaProveedor servicioFacturaProveedor;
/*  40:    */   private Empresa empresa;
/*  41:    */   private String establecimiento;
/*  42:    */   private String puntoVenta;
/*  43:    */   private String numeroFactura;
/*  44:    */   private FacturaProveedor facturaProveedor;
/*  45:    */   private DataTable dtListaReporte;
/*  46:    */   private List<ReporteEstadoCuentaFactura> listaReporte;
/*  47:    */   private ReporteEstadoCuentaFactura reporteEstadoCuentaFactura;
/*  48:    */   
/*  49:    */   public void procesar()
/*  50:    */   {
/*  51:    */     try
/*  52:    */     {
/*  53: 77 */       if (getFacturaProveedor() != null)
/*  54:    */       {
/*  55: 78 */         String factura = "";
/*  56: 79 */         if (getFacturaProveedor().getFacturaProveedorSRI() != null) {
/*  57: 82 */           factura = getFacturaProveedor().getFacturaProveedorSRI().getEstablecimiento().concat("-").concat(getFacturaProveedor().getFacturaProveedorSRI().getPuntoEmision()).concat("-").concat(getFacturaProveedor().getFacturaProveedorSRI().getNumero());
/*  58:    */         } else {
/*  59: 84 */           factura = getFacturaProveedor().getReferencia3();
/*  60:    */         }
/*  61: 87 */         LOG.info("Numero Factura >>>" + factura);
/*  62: 88 */         List<Object[]> reporteAux = this.servicioReporteCompra.getListaReporteEstadoCuenta(this.empresa.getId(), getFacturaProveedor());
/*  63: 89 */         ReporteEstadoCuentaFactura reporteEstadoCuentaFactura = null;
/*  64: 90 */         BigDecimal totalDebito = BigDecimal.ZERO;
/*  65: 91 */         BigDecimal totalCredito = BigDecimal.ZERO;
/*  66: 92 */         BigDecimal totalSaldo = BigDecimal.ZERO;
/*  67: 94 */         for (Object[] object : reporteAux)
/*  68:    */         {
/*  69: 95 */           reporteEstadoCuentaFactura = new ReporteEstadoCuentaFactura();
/*  70: 96 */           reporteEstadoCuentaFactura.setFechaDocumento((Date)object[0]);
/*  71: 97 */           reporteEstadoCuentaFactura.setNumeroDocumento((String)object[1]);
/*  72: 98 */           reporteEstadoCuentaFactura.setFechaVencimiento((Date)object[2]);
/*  73: 99 */           reporteEstadoCuentaFactura.setNumeroFactura((String)object[3]);
/*  74:100 */           BigDecimal debito = (BigDecimal)object[4];
/*  75:101 */           BigDecimal credito = (BigDecimal)object[5];
/*  76:102 */           BigDecimal saldo = debito.subtract(credito);
/*  77:103 */           reporteEstadoCuentaFactura.setDebito(debito);
/*  78:104 */           reporteEstadoCuentaFactura.setCredito(credito);
/*  79:105 */           reporteEstadoCuentaFactura.setSaldo(saldo);
/*  80:    */           
/*  81:107 */           totalDebito = totalDebito.add(debito);
/*  82:108 */           totalCredito = totalCredito.add(credito);
/*  83:109 */           totalSaldo = totalSaldo.add(saldo);
/*  84:    */           
/*  85:111 */           getListaReporte().add(reporteEstadoCuentaFactura);
/*  86:    */         }
/*  87:114 */         getReporteEstadoCuentaFactura().setTotalDebito(totalDebito);
/*  88:115 */         getReporteEstadoCuentaFactura().setTotalCredito(totalCredito);
/*  89:116 */         getReporteEstadoCuentaFactura().setTotalSaldo(totalSaldo);
/*  90:117 */         LOG.info("TotalDebito >>>" + totalDebito);
/*  91:118 */         LOG.info("TotalCredito >>>" + totalCredito);
/*  92:    */       }
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:122 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void actualizaFactura(SelectEvent event)
/* 101:    */   {
/* 102:128 */     getListaReporte().clear();
/* 103:129 */     FacturaProveedor facturaProveedorAux = (FacturaProveedor)event.getObject();
/* 104:130 */     setFacturaProveedor(facturaProveedorAux);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 108:    */   {
/* 109:140 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public List<FacturaProveedor> autocompletarFacturas(String consulta)
/* 113:    */   {
/* 114:150 */     Map<String, String> filters = new HashMap();
/* 115:151 */     List<FacturaProveedor> lista = new ArrayList();
/* 116:153 */     if (this.empresa != null)
/* 117:    */     {
/* 118:154 */       filters.put("empresa.idEmpresa", "" + getEmpresa().getId());
/* 119:155 */       if ((consulta != null) && (!consulta.isEmpty())) {
/* 120:156 */         filters.put("facturaProveedorSRI.numero", consulta);
/* 121:    */       }
/* 122:159 */       filters.put("documento.documentoBase", DocumentoBase.FACTURA_PROVEEDOR.toString());
/* 123:160 */       filters.put("documento.indicadorDocumentoExterior", "!=true");
/* 124:161 */       filters.put("estado", "!=" + Estado.ANULADO.toString());
/* 125:    */       
/* 126:163 */       lista = this.servicioFacturaProveedor.obtenerListaComboConEqual("fecha", true, filters);
/* 127:    */     }
/* 128:    */     else
/* 129:    */     {
/* 130:165 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 131:    */     }
/* 132:168 */     return lista;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String encerarDatos()
/* 136:    */   {
/* 137:173 */     this.facturaProveedor = null;
/* 138:174 */     this.listaReporte.clear();
/* 139:175 */     return "";
/* 140:    */   }
/* 141:    */   
/* 142:    */   public ServicioEmpresa getServicioEmpresa()
/* 143:    */   {
/* 144:186 */     return this.servicioEmpresa;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setServicioEmpresa(ServicioEmpresa servicioEmpresa)
/* 148:    */   {
/* 149:196 */     this.servicioEmpresa = servicioEmpresa;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public ServicioReporteCompra getServicioReporteCompra()
/* 153:    */   {
/* 154:205 */     return this.servicioReporteCompra;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setServicioReporteCompra(ServicioReporteCompra servicioReporteCompra)
/* 158:    */   {
/* 159:215 */     this.servicioReporteCompra = servicioReporteCompra;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Empresa getEmpresa()
/* 163:    */   {
/* 164:224 */     return this.empresa;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setEmpresa(Empresa empresa)
/* 168:    */   {
/* 169:234 */     this.empresa = empresa;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String getEstablecimiento()
/* 173:    */   {
/* 174:243 */     return this.establecimiento;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setEstablecimiento(String establecimiento)
/* 178:    */   {
/* 179:253 */     this.establecimiento = establecimiento;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String getPuntoVenta()
/* 183:    */   {
/* 184:262 */     return this.puntoVenta;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setPuntoVenta(String puntoVenta)
/* 188:    */   {
/* 189:272 */     this.puntoVenta = puntoVenta;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String getNumeroFactura()
/* 193:    */   {
/* 194:281 */     return this.numeroFactura;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setNumeroFactura(String numeroFactura)
/* 198:    */   {
/* 199:291 */     this.numeroFactura = numeroFactura;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public DataTable getDtListaReporte()
/* 203:    */   {
/* 204:300 */     return this.dtListaReporte;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setDtListaReporte(DataTable dtListaReporte)
/* 208:    */   {
/* 209:310 */     this.dtListaReporte = dtListaReporte;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<ReporteEstadoCuentaFactura> getListaReporte()
/* 213:    */   {
/* 214:319 */     if (this.listaReporte == null) {
/* 215:320 */       this.listaReporte = new ArrayList();
/* 216:    */     }
/* 217:322 */     return this.listaReporte;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setListaReporte(List<ReporteEstadoCuentaFactura> listaReporte)
/* 221:    */   {
/* 222:332 */     this.listaReporte = listaReporte;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public ReporteEstadoCuentaFactura getReporteEstadoCuentaFactura()
/* 226:    */   {
/* 227:341 */     if (this.reporteEstadoCuentaFactura == null) {
/* 228:342 */       this.reporteEstadoCuentaFactura = new ReporteEstadoCuentaFactura();
/* 229:    */     }
/* 230:344 */     return this.reporteEstadoCuentaFactura;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void setReporteEstadoCuentaFactura(ReporteEstadoCuentaFactura reporteEstadoCuentaFactura)
/* 234:    */   {
/* 235:354 */     this.reporteEstadoCuentaFactura = reporteEstadoCuentaFactura;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public FacturaProveedor getFacturaProveedor()
/* 239:    */   {
/* 240:363 */     return this.facturaProveedor;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void setFacturaProveedor(FacturaProveedor facturaProveedor)
/* 244:    */   {
/* 245:373 */     this.facturaProveedor = facturaProveedor;
/* 246:    */   }
/* 247:    */   
/* 248:    */   protected JRDataSource getJRDataSource()
/* 249:    */   {
/* 250:379 */     return null;
/* 251:    */   }
/* 252:    */   
/* 253:    */   protected String getCompileFileName()
/* 254:    */   {
/* 255:385 */     return null;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public String execute()
/* 259:    */   {
/* 260:391 */     return null;
/* 261:    */   }
/* 262:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReporteEstadoCuentaProveedorFacturaBean
 * JD-Core Version:    0.7.0.1
 */