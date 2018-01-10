/*   1:    */ package com.asinfo.as2.finaciero.pagos.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.financiero.pagos.reportes.servicio.ServicioReporteDebitosYCreditosPorProveedor;
/*  10:    */ import com.asinfo.as2.util.AppUtil;
/*  11:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  12:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  13:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Calendar;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  25:    */ import net.sf.jasperreports.engine.JRException;
/*  26:    */ import org.apache.log4j.Logger;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class ReporteDebitosYCreditosPorProveedorBean
/*  31:    */   extends AbstractBaseReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   private ServicioReporteDebitosYCreditosPorProveedor servicioReporteDebitosYCreditosPorProveedor;
/*  36:    */   @EJB
/*  37:    */   private ServicioEmpresa servicioEmpresa;
/*  38:    */   @EJB
/*  39:    */   private ServicioSucursal servicioSucursal;
/*  40:    */   private Date fechaDesde;
/*  41:    */   private Date fechaHasta;
/*  42:    */   private Empresa empresa;
/*  43: 62 */   private int tipoReporte = 1;
/*  44: 63 */   private boolean saldosDiferenteDeCero = true;
/*  45:    */   private Sucursal sucursal;
/*  46:    */   private List<Sucursal> listaSucursal;
/*  47:    */   private List<Empresa> listaProveedores;
/*  48:    */   
/*  49:    */   public ReporteDebitosYCreditosPorProveedorBean()
/*  50:    */   {
/*  51: 73 */     Calendar cal = Calendar.getInstance();
/*  52: 74 */     this.fechaDesde = FuncionesUtiles.getFecha(1, cal.get(2) + 1, cal.get(1));
/*  53: 75 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected String getCompileFileName()
/*  57:    */   {
/*  58: 85 */     return this.tipoReporte == 1 ? "reporteDebitosYCreditosPorProveedor" : "reporteDebitosYCreditosPorFacturaProveedor";
/*  59:    */   }
/*  60:    */   
/*  61:    */   protected Map<String, Object> getReportParameters()
/*  62:    */   {
/*  63: 95 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  64: 96 */     reportParameters.put("fechaDesde", this.fechaDesde);
/*  65: 97 */     reportParameters.put("fechaHasta", this.fechaHasta);
/*  66: 98 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/*  67:    */     
/*  68:100 */     return reportParameters;
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected JRDataSource getJRDataSource()
/*  72:    */   {
/*  73:110 */     List<Object[]> listaDatosReporte = new ArrayList();
/*  74:    */     
/*  75:112 */     int idOrganizacion = AppUtil.getOrganizacion().getId();
/*  76:113 */     listaDatosReporte = this.servicioReporteDebitosYCreditosPorProveedor.getReporteDebitosYCreditosPorProveedor(idOrganizacion, this.sucursal, this.empresa, this.fechaDesde, this.fechaHasta, this.tipoReporte, this.saldosDiferenteDeCero);
/*  77:    */     
/*  78:115 */     String[] fields = { "idEmpresa", "nombreFiscal", "factura", "fechaEmision", "fechaVencimiento", "saldo", "compras", "impuestos", "nd", "nc", "liquidaciones", "pagos", "saldoAnticipo" };
/*  79:    */     
/*  80:    */ 
/*  81:118 */     return new QueryResultDataSource(listaDatosReporte, fields);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public String execute()
/*  85:    */   {
/*  86:    */     try
/*  87:    */     {
/*  88:128 */       super.prepareReport();
/*  89:    */     }
/*  90:    */     catch (JRException e)
/*  91:    */     {
/*  92:130 */       LOG.info("Error JRException");
/*  93:131 */       e.printStackTrace();
/*  94:132 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  95:    */     }
/*  96:    */     catch (IOException e)
/*  97:    */     {
/*  98:134 */       LOG.info("Error IOException");
/*  99:135 */       e.printStackTrace();
/* 100:136 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 101:    */     }
/* 102:139 */     return "";
/* 103:    */   }
/* 104:    */   
/* 105:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 106:    */   {
/* 107:149 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Date getFechaHasta()
/* 111:    */   {
/* 112:158 */     return this.fechaHasta;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setFechaHasta(Date fechaHasta)
/* 116:    */   {
/* 117:168 */     this.fechaHasta = fechaHasta;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Empresa getEmpresa()
/* 121:    */   {
/* 122:172 */     return this.empresa;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setEmpresa(Empresa empresa)
/* 126:    */   {
/* 127:176 */     this.empresa = empresa;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Date getFechaDesde()
/* 131:    */   {
/* 132:185 */     return this.fechaDesde;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setFechaDesde(Date fechaDesde)
/* 136:    */   {
/* 137:195 */     this.fechaDesde = fechaDesde;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getTipoReporte()
/* 141:    */   {
/* 142:202 */     return this.tipoReporte;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setTipoReporte(int tipoReporte)
/* 146:    */   {
/* 147:210 */     this.tipoReporte = tipoReporte;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Sucursal getSucursal()
/* 151:    */   {
/* 152:217 */     return this.sucursal;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setSucursal(Sucursal sucursal)
/* 156:    */   {
/* 157:225 */     this.sucursal = sucursal;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List<Sucursal> getListaSucursal()
/* 161:    */   {
/* 162:232 */     if (this.listaSucursal == null)
/* 163:    */     {
/* 164:233 */       Map<String, String> filters = new HashMap();
/* 165:234 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 166:235 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 167:    */     }
/* 168:237 */     return this.listaSucursal;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 172:    */   {
/* 173:245 */     this.listaSucursal = listaSucursal;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean isSaldosDiferenteDeCero()
/* 177:    */   {
/* 178:252 */     return this.saldosDiferenteDeCero;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setSaldosDiferenteDeCero(boolean saldosDiferenteDeCero)
/* 182:    */   {
/* 183:260 */     this.saldosDiferenteDeCero = saldosDiferenteDeCero;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<Empresa> getListaProveedores()
/* 187:    */   {
/* 188:267 */     if (this.listaProveedores == null) {
/* 189:268 */       this.listaProveedores = this.servicioEmpresa.obtenerProveedores();
/* 190:    */     }
/* 191:271 */     return this.listaProveedores;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setListaProveedores(List<Empresa> listaProveedores)
/* 195:    */   {
/* 196:278 */     this.listaProveedores = listaProveedores;
/* 197:    */   }
/* 198:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.pagos.reportes.ReporteDebitosYCreditosPorProveedorBean
 * JD-Core Version:    0.7.0.1
 */