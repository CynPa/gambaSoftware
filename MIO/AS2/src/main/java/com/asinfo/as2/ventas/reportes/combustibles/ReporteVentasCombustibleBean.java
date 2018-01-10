/*   1:    */ package com.asinfo.as2.ventas.reportes.combustibles;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.Canal;
/*   6:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Zona;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  13:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  14:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  15:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  16:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  17:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  18:    */ import java.io.IOException;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  27:    */ import net.sf.jasperreports.engine.JRException;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class ReporteVentasCombustibleBean
/*  33:    */   extends AbstractBaseReportBean
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = -3761178279233655854L;
/*  36:    */   @EJB
/*  37:    */   private ServicioReporteVenta servicioReporteVenta;
/*  38:    */   @EJB
/*  39:    */   private ServicioEmpresa servicioEmpresa;
/*  40:    */   @EJB
/*  41:    */   private ServicioCanal servicioCanal;
/*  42:    */   @EJB
/*  43:    */   private ServicioZona servicioZona;
/*  44:    */   @EJB
/*  45:    */   private ServicioCategoriaProducto servicioCategoriaProducto;
/*  46:    */   private Empresa empresa;
/*  47:    */   private int idCategoriaProducto;
/*  48:    */   private int idCanal;
/*  49:    */   private int idZona;
/*  50:    */   private boolean anuladas;
/*  51:    */   private Date fechaDesde;
/*  52:    */   private Date fechaHasta;
/*  53:    */   private List<Empresa> listaCliente;
/*  54:    */   private List<Zona> listaZona;
/*  55:    */   private List<Canal> listaCanal;
/*  56:    */   private List<CategoriaProducto> listaCategoriaProducto;
/*  57:    */   
/*  58:    */   protected JRDataSource getJRDataSource()
/*  59:    */   {
/*  60: 89 */     List listaDatosReporte = new ArrayList();
/*  61: 90 */     JRDataSource ds = null;
/*  62:    */     
/*  63: 92 */     listaDatosReporte = this.servicioReporteVenta.getListaReporteVentasCombustible(this.fechaDesde, this.fechaHasta, this.empresa.getId(), this.anuladas, this.idCanal, this.idZona, this.idCategoriaProducto, 
/*  64: 93 */       AppUtil.getOrganizacion().getId());
/*  65:    */     
/*  66: 95 */     String[] fields = { "f_codigoCliente", "f_identificacionCliente", "f_nombreCliente", "f_estacion", "f_nombreProducto", "f_fechaFactura", "f_tipoComprobante", "f_numeroFactura", "f_autorizacionFactura", "f_cantidad", "f_precio", "f_tipoEstructuraPrecio", "f_valorEstructuraPrecio" };
/*  67:    */     
/*  68:    */ 
/*  69: 98 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  70:    */     
/*  71:100 */     return ds;
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected String getCompileFileName()
/*  75:    */   {
/*  76:110 */     return "reporteVentasCombustible";
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected Map<String, Object> getReportParameters()
/*  80:    */   {
/*  81:120 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  82:121 */     reportParameters.put("ReportTitle", "Reporte de ventas de combustible");
/*  83:122 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  84:123 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  85:124 */     reportParameters.put("Total", "Total");
/*  86:125 */     return reportParameters;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String execute()
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:135 */       validaDatos();
/*  94:136 */       super.prepareReport();
/*  95:    */     }
/*  96:    */     catch (JRException e)
/*  97:    */     {
/*  98:138 */       LOG.info("Error JRException");
/*  99:139 */       e.printStackTrace();
/* 100:140 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 101:    */     }
/* 102:    */     catch (IOException e)
/* 103:    */     {
/* 104:142 */       LOG.info("Error IOException");
/* 105:143 */       e.printStackTrace();
/* 106:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 107:    */     }
/* 108:147 */     return null;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void validaDatos()
/* 112:    */   {
/* 113:152 */     if (this.empresa == null)
/* 114:    */     {
/* 115:153 */       this.empresa = new Empresa();
/* 116:154 */       this.empresa.setId(-1);
/* 117:    */     }
/* 118:156 */     if (this.fechaDesde == null) {
/* 119:157 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 120:    */     }
/* 121:159 */     if (this.fechaHasta == null) {
/* 122:160 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Date getFechaDesde()
/* 127:    */   {
/* 128:170 */     return this.fechaDesde;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setFechaDesde(Date fechaDesde)
/* 132:    */   {
/* 133:180 */     this.fechaDesde = fechaDesde;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Date getFechaHasta()
/* 137:    */   {
/* 138:189 */     return this.fechaHasta;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setFechaHasta(Date fechaHasta)
/* 142:    */   {
/* 143:199 */     this.fechaHasta = fechaHasta;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public Empresa getEmpresa()
/* 147:    */   {
/* 148:208 */     if (this.empresa == null)
/* 149:    */     {
/* 150:209 */       this.empresa = new Empresa();
/* 151:210 */       this.empresa.setId(0);
/* 152:    */     }
/* 153:212 */     return this.empresa;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setEmpresa(Empresa empresa)
/* 157:    */   {
/* 158:222 */     this.empresa = empresa;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public int getIdCanal()
/* 162:    */   {
/* 163:231 */     return this.idCanal;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void setIdCanal(int idCanal)
/* 167:    */   {
/* 168:241 */     this.idCanal = idCanal;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public int getIdZona()
/* 172:    */   {
/* 173:250 */     return this.idZona;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setIdZona(int idZona)
/* 177:    */   {
/* 178:260 */     this.idZona = idZona;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 182:    */   {
/* 183:264 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<Empresa> getListaCliente()
/* 187:    */   {
/* 188:273 */     if (this.listaCliente == null) {
/* 189:274 */       this.listaCliente = this.servicioEmpresa.obtenerClientes();
/* 190:    */     }
/* 191:276 */     return this.listaCliente;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public List<Zona> getListaZona()
/* 195:    */   {
/* 196:285 */     if (this.listaZona == null) {
/* 197:286 */       this.listaZona = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 198:    */     }
/* 199:288 */     return this.listaZona;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public List<Canal> getListaCanal()
/* 203:    */   {
/* 204:297 */     if (this.listaCanal == null) {
/* 205:298 */       this.listaCanal = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 206:    */     }
/* 207:300 */     return this.listaCanal;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public boolean isAnuladas()
/* 211:    */   {
/* 212:304 */     return this.anuladas;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setAnuladas(boolean anuladas)
/* 216:    */   {
/* 217:308 */     this.anuladas = anuladas;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public int getIdCategoriaProducto()
/* 221:    */   {
/* 222:317 */     return this.idCategoriaProducto;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setIdCategoriaProducto(int idCategoriaProducto)
/* 226:    */   {
/* 227:327 */     this.idCategoriaProducto = idCategoriaProducto;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public List<CategoriaProducto> getListaCategoriaProducto()
/* 231:    */   {
/* 232:336 */     if (this.listaCategoriaProducto == null) {
/* 233:337 */       this.listaCategoriaProducto = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 234:    */     }
/* 235:339 */     return this.listaCategoriaProducto;
/* 236:    */   }
/* 237:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.combustibles.ReporteVentasCombustibleBean
 * JD-Core Version:    0.7.0.1
 */