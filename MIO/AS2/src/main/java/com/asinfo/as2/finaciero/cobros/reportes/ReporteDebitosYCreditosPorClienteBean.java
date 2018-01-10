/*   1:    */ package com.asinfo.as2.finaciero.cobros.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  10:    */ import com.asinfo.as2.enumeraciones.TipoOrganizacion;
/*  11:    */ import com.asinfo.as2.financiero.cobros.reportes.servicio.ServicioReporteDebitosYCreditosPorCliente;
/*  12:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  16:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  17:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRecaudador;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Calendar;
/*  21:    */ import java.util.Date;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import net.sf.jasperreports.engine.JRException;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class ReporteDebitosYCreditosPorClienteBean
/*  35:    */   extends AbstractBaseReportBean
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = 1L;
/*  38:    */   @EJB
/*  39:    */   private ServicioReporteDebitosYCreditosPorCliente servicioReporteDebitosYCreditosPorCliente;
/*  40:    */   @EJB
/*  41:    */   private ServicioEmpresa servicioEmpresa;
/*  42:    */   @EJB
/*  43:    */   private ServicioRecaudador servicioRecaudador;
/*  44:    */   @EJB
/*  45:    */   private ServicioUsuario servicioUsuario;
/*  46:    */   @EJB
/*  47:    */   private ServicioSucursal servicioSucursal;
/*  48:    */   private Date fechaDesde;
/*  49:    */   private Date fechaHasta;
/*  50:    */   private Empresa empresa;
/*  51: 74 */   private int tipoReporte = 1;
/*  52: 75 */   private boolean saldosDiferenteDeCero = getTipoOrganizacion() == TipoOrganizacion.TIPO_ORGANIZACION_TEXTILES_PADILLA;
/*  53:    */   private Sucursal sucursal;
/*  54:    */   private List<Sucursal> listaSucursal;
/*  55:    */   private int idVendedor;
/*  56:    */   private List<EntidadUsuario> listaVendedor;
/*  57:    */   private List<Empresa> listaClientes;
/*  58:    */   
/*  59:    */   public ReporteDebitosYCreditosPorClienteBean()
/*  60:    */   {
/*  61: 89 */     Calendar cal = Calendar.getInstance();
/*  62: 90 */     this.fechaDesde = FuncionesUtiles.getFecha(1, cal.get(2) + 1, cal.get(1));
/*  63: 91 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  64:    */   }
/*  65:    */   
/*  66:    */   protected String getCompileFileName()
/*  67:    */   {
/*  68:101 */     return this.tipoReporte == 1 ? "reporteDebitosYCreditosPorCliente" : "reporteDebitosYCreditosPorFactura";
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected Map<String, Object> getReportParameters()
/*  72:    */   {
/*  73:111 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  74:112 */     reportParameters.put("fechaDesde", this.fechaDesde);
/*  75:113 */     reportParameters.put("fechaHasta", this.fechaHasta);
/*  76:114 */     reportParameters.put("p_idVendedor", this.idVendedor == 0 ? "sinVendedor" : "conVendedor");
/*  77:    */     
/*  78:116 */     return reportParameters;
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected JRDataSource getJRDataSource()
/*  82:    */   {
/*  83:126 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  84:    */     
/*  85:    */ 
/*  86:129 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/*  87:    */     
/*  88:131 */     listaDatosReporte = this.servicioReporteDebitosYCreditosPorCliente.getReporteDebitosYCreditosPorCliente(idOrganizacion, this.sucursal, this.empresa, this.fechaDesde, this.fechaHasta, this.tipoReporte, this.saldosDiferenteDeCero, this.idVendedor);
/*  89:    */     
/*  90:    */ 
/*  91:    */ 
/*  92:135 */     String[] fields = { "idEmpresa", "nombreFiscal", "factura", "fechaEmision", "fechaVencimiento", "saldo", "ventas", "impuestos", "nd", "nc", "liquidaciones", "cobros", "retenciones", "anticipos", "protestos", "chequeProtestado", "posfechados", "saldoAnticipo", "identificacionCliente", "f_numeroPacking", "f_agenteComercial", "f_nombreAgenteComercial" };
/*  93:    */     
/*  94:    */ 
/*  95:138 */     return new QueryResultDataSource(listaDatosReporte, fields);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String execute()
/*  99:    */   {
/* 100:    */     try
/* 101:    */     {
/* 102:149 */       super.prepareReport();
/* 103:    */     }
/* 104:    */     catch (JRException e)
/* 105:    */     {
/* 106:152 */       LOG.info("Error JRException");
/* 107:153 */       e.printStackTrace();
/* 108:154 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 109:    */     }
/* 110:    */     catch (IOException e)
/* 111:    */     {
/* 112:156 */       LOG.info("Error IOException");
/* 113:157 */       e.printStackTrace();
/* 114:158 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 115:    */     }
/* 116:161 */     return "";
/* 117:    */   }
/* 118:    */   
/* 119:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 120:    */   {
/* 121:171 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public Date getFechaHasta()
/* 125:    */   {
/* 126:180 */     return this.fechaHasta;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setFechaHasta(Date fechaHasta)
/* 130:    */   {
/* 131:190 */     this.fechaHasta = fechaHasta;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Empresa getEmpresa()
/* 135:    */   {
/* 136:194 */     return this.empresa;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setEmpresa(Empresa empresa)
/* 140:    */   {
/* 141:198 */     this.empresa = empresa;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<Empresa> getListaClientes()
/* 145:    */   {
/* 146:207 */     if (this.listaClientes == null) {
/* 147:208 */       this.listaClientes = this.servicioEmpresa.obtenerClientes();
/* 148:    */     }
/* 149:210 */     return this.listaClientes;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setListaClientes(List<Empresa> listaClientes)
/* 153:    */   {
/* 154:220 */     this.listaClientes = listaClientes;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Date getFechaDesde()
/* 158:    */   {
/* 159:229 */     return this.fechaDesde;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setFechaDesde(Date fechaDesde)
/* 163:    */   {
/* 164:239 */     this.fechaDesde = fechaDesde;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int getTipoReporte()
/* 168:    */   {
/* 169:246 */     return this.tipoReporte;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setTipoReporte(int tipoReporte)
/* 173:    */   {
/* 174:254 */     this.tipoReporte = tipoReporte;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Sucursal getSucursal()
/* 178:    */   {
/* 179:261 */     return this.sucursal;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setSucursal(Sucursal sucursal)
/* 183:    */   {
/* 184:269 */     this.sucursal = sucursal;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public List<Sucursal> getListaSucursal()
/* 188:    */   {
/* 189:276 */     if (this.listaSucursal == null)
/* 190:    */     {
/* 191:277 */       Map<String, String> filters = new HashMap();
/* 192:278 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 193:279 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 194:    */     }
/* 195:281 */     return this.listaSucursal;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 199:    */   {
/* 200:289 */     this.listaSucursal = listaSucursal;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean isSaldosDiferenteDeCero()
/* 204:    */   {
/* 205:296 */     return this.saldosDiferenteDeCero;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setSaldosDiferenteDeCero(boolean saldosDiferenteDeCero)
/* 209:    */   {
/* 210:304 */     this.saldosDiferenteDeCero = saldosDiferenteDeCero;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<EntidadUsuario> getListaVendedor()
/* 214:    */   {
/* 215:311 */     this.listaVendedor = new ArrayList();
/* 216:312 */     this.listaVendedor = this.servicioUsuario.obtenerListaAgenteComercial(AppUtil.getOrganizacion().getId());
/* 217:313 */     return this.listaVendedor;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setListaVendedor(List<EntidadUsuario> listaVendedor)
/* 221:    */   {
/* 222:321 */     this.listaVendedor = listaVendedor;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public int getIdVendedor()
/* 226:    */   {
/* 227:328 */     return this.idVendedor;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setIdVendedor(int idVendedor)
/* 231:    */   {
/* 232:336 */     this.idVendedor = idVendedor;
/* 233:    */   }
/* 234:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.cobros.reportes.ReporteDebitosYCreditosPorClienteBean
 * JD-Core Version:    0.7.0.1
 */