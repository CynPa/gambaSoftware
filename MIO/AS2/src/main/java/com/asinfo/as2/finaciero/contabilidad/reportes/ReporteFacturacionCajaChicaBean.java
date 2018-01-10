/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Empresa;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   8:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioCompraCajaChica;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  11:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  12:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  13:    */ import java.io.IOException;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.Calendar;
/*  16:    */ import java.util.Date;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.RequestScoped;
/*  23:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  24:    */ import net.sf.jasperreports.engine.JRException;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @RequestScoped
/*  29:    */ public class ReporteFacturacionCajaChicaBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -6479363958245152900L;
/*  33:    */   @EJB
/*  34:    */   private ServicioEmpresa servicioEmpresa;
/*  35:    */   @EJB
/*  36:    */   private ServicioCompraCajaChica servicioCompraCajaChica;
/*  37:    */   private Date fechaDesde;
/*  38:    */   private Date fechaHasta;
/*  39:    */   private String numeroFacturaDesde;
/*  40:    */   private String numeroFacturaHasta;
/*  41:    */   private List<Empresa> listaProveedores;
/*  42: 63 */   private int tipoCompra = -1;
/*  43: 65 */   private final String COMPILE_FILE_NAME = "reporteComprasResumido";
/*  44: 67 */   private boolean seleccionarProveedor = true;
/*  45: 68 */   private boolean fechaContabilizacion = false;
/*  46:    */   
/*  47:    */   protected JRDataSource getJRDataSource()
/*  48:    */   {
/*  49: 78 */     List listaDatosReporte = new ArrayList();
/*  50: 79 */     JRDataSource ds = null;
/*  51:    */     try
/*  52:    */     {
/*  53: 81 */       listaDatosReporte = this.servicioCompraCajaChica.getListaReporteCompraCajaChica(this.fechaDesde, this.fechaHasta, this.numeroFacturaDesde, this.numeroFacturaHasta, 0, this.tipoCompra, 
/*  54: 82 */         AppUtil.getOrganizacion().getIdOrganizacion(), this.fechaContabilizacion);
/*  55:    */       
/*  56: 84 */       String[] fields = { "f_codigoCajaChica", "f_nombreCajaChica", "f_numeroFactura", "f_fechaFactura", "f_indicadorFactura", "f_empresa", "f_subTotal", "f_descuento", "f_impuestos", "f_descripcion", "f_codigoEmpresa", "f_nombreCategoriaEmpresa", "f_identificacionEmpresa", "f_numeroCompra", "f_SRIfechaEmision", "f_SRIautorizacion", "f_SRIautorizacionRetencion", "f_SRIbaseImponibleTarifaCero", "f_SRIbaseImponibleDiferenteCero", "f_SRIclaveAccesoRetencion", "f_descuentoSolidario" };
/*  57:    */       
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62: 90 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  63:    */     }
/*  64:    */     catch (ExcepcionAS2 e)
/*  65:    */     {
/*  66: 92 */       LOG.info("Error " + e);
/*  67: 93 */       e.printStackTrace();
/*  68:    */     }
/*  69: 95 */     return ds;
/*  70:    */   }
/*  71:    */   
/*  72:    */   @PostConstruct
/*  73:    */   public void init()
/*  74:    */   {
/*  75:100 */     Calendar calfechaDesde = Calendar.getInstance();
/*  76:101 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  77:102 */     this.fechaDesde = calfechaDesde.getTime();
/*  78:103 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  79:    */   }
/*  80:    */   
/*  81:    */   protected String getCompileFileName()
/*  82:    */   {
/*  83:113 */     return "reporteComprasResumido";
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected Map<String, Object> getReportParameters()
/*  87:    */   {
/*  88:124 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  89:125 */     reportParameters.put("ReportTitle", "Compras Caja Chica");
/*  90:126 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  91:127 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  92:128 */     reportParameters.put("NumeroDesde", this.numeroFacturaDesde);
/*  93:129 */     reportParameters.put("NumeroHasta", this.numeroFacturaHasta);
/*  94:130 */     reportParameters.put("Total", "Total");
/*  95:131 */     reportParameters.put("indicadorFecha", Boolean.valueOf(true));
/*  96:132 */     reportParameters.put("indicadorCajaChica", Boolean.valueOf(true));
/*  97:    */     
/*  98:134 */     return reportParameters;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String execute()
/* 102:    */   {
/* 103:    */     try
/* 104:    */     {
/* 105:144 */       super.prepareReport();
/* 106:    */     }
/* 107:    */     catch (JRException e)
/* 108:    */     {
/* 109:146 */       LOG.info("Error JRException");
/* 110:147 */       e.printStackTrace();
/* 111:148 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 112:    */     }
/* 113:    */     catch (IOException e)
/* 114:    */     {
/* 115:150 */       LOG.info("Error IOException");
/* 116:151 */       e.printStackTrace();
/* 117:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 118:    */     }
/* 119:155 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Date getFechaDesde()
/* 123:    */   {
/* 124:165 */     return this.fechaDesde;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setFechaDesde(Date fechaDesde)
/* 128:    */   {
/* 129:175 */     this.fechaDesde = fechaDesde;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Date getFechaHasta()
/* 133:    */   {
/* 134:184 */     return this.fechaHasta;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setFechaHasta(Date fechaHasta)
/* 138:    */   {
/* 139:194 */     this.fechaHasta = fechaHasta;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getNumeroFacturaDesde()
/* 143:    */   {
/* 144:203 */     return this.numeroFacturaDesde;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setNumeroFacturaDesde(String numeroFacturaDesde)
/* 148:    */   {
/* 149:213 */     this.numeroFacturaDesde = numeroFacturaDesde;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getNumeroFacturaHasta()
/* 153:    */   {
/* 154:222 */     return this.numeroFacturaHasta;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setNumeroFacturaHasta(String numeroFacturaHasta)
/* 158:    */   {
/* 159:232 */     this.numeroFacturaHasta = numeroFacturaHasta;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public List<Empresa> getListaProveedores()
/* 163:    */   {
/* 164:241 */     if (this.listaProveedores == null) {
/* 165:242 */       this.listaProveedores = this.servicioEmpresa.obtenerProveedores();
/* 166:    */     }
/* 167:244 */     return this.listaProveedores;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setListaProveedores(List<Empresa> listaProveedores)
/* 171:    */   {
/* 172:254 */     this.listaProveedores = listaProveedores;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 176:    */   {
/* 177:258 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 178:    */   }
/* 179:    */   
/* 180:    */   public boolean isSeleccionarProveedor()
/* 181:    */   {
/* 182:262 */     return this.seleccionarProveedor;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setSeleccionarProveedor(boolean seleccionarProveedor)
/* 186:    */   {
/* 187:266 */     this.seleccionarProveedor = seleccionarProveedor;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int getTipoCompra()
/* 191:    */   {
/* 192:275 */     return this.tipoCompra;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setTipoCompra(int tipoCompra)
/* 196:    */   {
/* 197:285 */     this.tipoCompra = tipoCompra;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public boolean isFechaContabilizacion()
/* 201:    */   {
/* 202:289 */     return this.fechaContabilizacion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setFechaContabilizacion(boolean fechaContabilizacion)
/* 206:    */   {
/* 207:293 */     this.fechaContabilizacion = fechaContabilizacion;
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.reportes.ReporteFacturacionCajaChicaBean
 * JD-Core Version:    0.7.0.1
 */