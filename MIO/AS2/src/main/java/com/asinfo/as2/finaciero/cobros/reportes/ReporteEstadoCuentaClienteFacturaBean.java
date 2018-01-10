/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteEstadoCuentaFactura;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.FacturaCliente;
/*   9:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  10:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  11:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  12:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Date;
/*  16:    */ import java.util.HashMap;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.event.SelectEvent;
/*  24:    */ 
/*  25:    */ @ManagedBean
/*  26:    */ @ViewScoped
/*  27:    */ public class ReporteEstadoCuentaClienteFacturaBean
/*  28:    */   extends PageController
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = -5942173517237995103L;
/*  31:    */   @EJB
/*  32:    */   private transient ServicioEmpresa servicioEmpresa;
/*  33:    */   @EJB
/*  34:    */   private transient ServicioReporteVenta servicioReporteVenta;
/*  35:    */   @EJB
/*  36:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  37:    */   private Empresa empresa;
/*  38:    */   private String establecimiento;
/*  39:    */   private String puntoVenta;
/*  40:    */   private String numeroFactura;
/*  41:    */   private FacturaCliente facturaCliente;
/*  42:    */   private DataTable dtListaReporte;
/*  43:    */   private List<ReporteEstadoCuentaFactura> listaEstadoCuenta;
/*  44:    */   private ReporteEstadoCuentaFactura reporteEstadoCuentaFactura;
/*  45: 68 */   private BigDecimal totalDebito = BigDecimal.ZERO;
/*  46: 69 */   private BigDecimal totalCredito = BigDecimal.ZERO;
/*  47:    */   
/*  48:    */   public void procesar(SelectEvent event)
/*  49:    */   {
/*  50: 77 */     this.totalDebito = BigDecimal.ZERO;
/*  51: 78 */     this.totalCredito = BigDecimal.ZERO;
/*  52: 79 */     getListaEstadoCuenta().clear();
/*  53: 80 */     FacturaCliente facturaClienteAux = (FacturaCliente)event.getObject();
/*  54: 81 */     setFacturaCliente(facturaClienteAux);
/*  55: 82 */     this.empresa = facturaClienteAux.getEmpresa();
/*  56:    */     try
/*  57:    */     {
/*  58: 84 */       if (getFacturaCliente() != null)
/*  59:    */       {
/*  60: 85 */         String factura = getFacturaCliente().getNumero();
/*  61: 86 */         List<Object[]> reporteAux = this.servicioReporteVenta.getListaReporteEstadoCuenta(this.empresa.getId(), factura);
/*  62: 87 */         ReporteEstadoCuentaFactura reporteEstadoCuentaFactura = null;
/*  63:    */         
/*  64:    */ 
/*  65: 90 */         BigDecimal saldo = BigDecimal.ZERO;
/*  66: 92 */         for (Object[] object : reporteAux)
/*  67:    */         {
/*  68: 93 */           reporteEstadoCuentaFactura = new ReporteEstadoCuentaFactura();
/*  69: 94 */           reporteEstadoCuentaFactura.setFechaDocumento((Date)object[0]);
/*  70: 95 */           reporteEstadoCuentaFactura.setNumeroDocumento((String)object[1]);
/*  71: 96 */           reporteEstadoCuentaFactura.setFechaVencimiento((Date)object[2]);
/*  72: 97 */           reporteEstadoCuentaFactura.setNumeroFactura((String)object[3]);
/*  73: 98 */           BigDecimal debito = (BigDecimal)object[4];
/*  74: 99 */           BigDecimal credito = (BigDecimal)object[5];
/*  75:100 */           reporteEstadoCuentaFactura.setDebito(debito);
/*  76:101 */           reporteEstadoCuentaFactura.setCredito(credito);
/*  77:102 */           reporteEstadoCuentaFactura.setCodigoDocumento((String)object[6]);
/*  78:103 */           reporteEstadoCuentaFactura.setNombreDocumento((String)object[7]);
/*  79:104 */           reporteEstadoCuentaFactura.setCodigoDocumentoProceso((String)object[8]);
/*  80:105 */           reporteEstadoCuentaFactura.setIdCobro(object[9] == null ? 0 : ((Integer)object[9]).intValue());
/*  81:106 */           reporteEstadoCuentaFactura.setIdFacturaCliente(object[10] == null ? 0 : ((Integer)object[10]).intValue());
/*  82:107 */           reporteEstadoCuentaFactura.setDocumentoBase((DocumentoBase)(object[11] == null ? Integer.valueOf(0) : object[11]));
/*  83:108 */           reporteEstadoCuentaFactura.setNumeroPacking((String)object[12]);
/*  84:109 */           this.totalDebito = this.totalDebito.add(debito);
/*  85:110 */           this.totalCredito = this.totalCredito.add(credito);
/*  86:111 */           saldo = this.totalDebito.subtract(this.totalCredito);
/*  87:112 */           reporteEstadoCuentaFactura.setSaldo(saldo);
/*  88:113 */           getListaEstadoCuenta().add(reporteEstadoCuentaFactura);
/*  89:    */         }
/*  90:116 */         getReporteEstadoCuentaFactura().setTotalDebito(this.totalDebito);
/*  91:117 */         getReporteEstadoCuentaFactura().setTotalCredito(this.totalCredito);
/*  92:    */       }
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:120 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  97:121 */       e.printStackTrace();
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 102:    */   {
/* 103:132 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<FacturaCliente> autocompletarFacturas(String consulta)
/* 107:    */   {
/* 108:142 */     Map<String, String> filters = new HashMap();
/* 109:143 */     List<FacturaCliente> lista = new ArrayList();
/* 110:145 */     if (this.empresa != null) {
/* 111:146 */       filters.put("empresa.idEmpresa", "" + getEmpresa().getId());
/* 112:    */     }
/* 113:149 */     if ((consulta != null) && (!consulta.isEmpty())) {
/* 114:150 */       filters.put("numero", "%" + consulta);
/* 115:    */     }
/* 116:153 */     filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/* 117:154 */     filters.put("estado", "!=" + Estado.ANULADO.toString());
/* 118:155 */     lista = this.servicioFacturaCliente.obtenerListaCombo("fecha", true, filters);
/* 119:    */     
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:161 */     return lista;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String encerarDatos()
/* 128:    */   {
/* 129:166 */     this.facturaCliente = null;
/* 130:167 */     this.listaEstadoCuenta.clear();
/* 131:168 */     return "";
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Empresa getEmpresa()
/* 135:    */   {
/* 136:179 */     return this.empresa;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setEmpresa(Empresa empresa)
/* 140:    */   {
/* 141:189 */     this.empresa = empresa;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String getEstablecimiento()
/* 145:    */   {
/* 146:198 */     return this.establecimiento;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setEstablecimiento(String establecimiento)
/* 150:    */   {
/* 151:208 */     this.establecimiento = establecimiento;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String getPuntoVenta()
/* 155:    */   {
/* 156:217 */     return this.puntoVenta;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setPuntoVenta(String puntoVenta)
/* 160:    */   {
/* 161:227 */     this.puntoVenta = puntoVenta;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String getNumeroFactura()
/* 165:    */   {
/* 166:236 */     return this.numeroFactura;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setNumeroFactura(String numeroFactura)
/* 170:    */   {
/* 171:246 */     this.numeroFactura = numeroFactura;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public List<ReporteEstadoCuentaFactura> getListaEstadoCuenta()
/* 175:    */   {
/* 176:255 */     if (this.listaEstadoCuenta == null) {
/* 177:256 */       this.listaEstadoCuenta = new ArrayList();
/* 178:    */     }
/* 179:258 */     return this.listaEstadoCuenta;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setListaEstadoCuenta(List<ReporteEstadoCuentaFactura> listaEstadoCuenta)
/* 183:    */   {
/* 184:268 */     this.listaEstadoCuenta = listaEstadoCuenta;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public DataTable getDtListaReporte()
/* 188:    */   {
/* 189:277 */     return this.dtListaReporte;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setDtListaReporte(DataTable dtListaReporte)
/* 193:    */   {
/* 194:287 */     this.dtListaReporte = dtListaReporte;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public ReporteEstadoCuentaFactura getReporteEstadoCuentaFactura()
/* 198:    */   {
/* 199:296 */     if (this.reporteEstadoCuentaFactura == null) {
/* 200:297 */       this.reporteEstadoCuentaFactura = new ReporteEstadoCuentaFactura();
/* 201:    */     }
/* 202:299 */     return this.reporteEstadoCuentaFactura;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setReporteEstadoCuentaFactura(ReporteEstadoCuentaFactura reporteEstadoCuentaFactura)
/* 206:    */   {
/* 207:309 */     this.reporteEstadoCuentaFactura = reporteEstadoCuentaFactura;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public FacturaCliente getFacturaCliente()
/* 211:    */   {
/* 212:318 */     return this.facturaCliente;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setFacturaCliente(FacturaCliente facturaCliente)
/* 216:    */   {
/* 217:328 */     this.facturaCliente = facturaCliente;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public BigDecimal getTotalDebito()
/* 221:    */   {
/* 222:332 */     return this.totalDebito;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setTotalDebito(BigDecimal totalDebito)
/* 226:    */   {
/* 227:336 */     this.totalDebito = totalDebito;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public BigDecimal getTotalCredito()
/* 231:    */   {
/* 232:340 */     return this.totalCredito;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setTotalCredito(BigDecimal totalCredito)
/* 236:    */   {
/* 237:344 */     this.totalCredito = totalCredito;
/* 238:    */   }
/* 239:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteEstadoCuentaClienteFacturaBean
 * JD-Core Version:    0.7.0.1
 */