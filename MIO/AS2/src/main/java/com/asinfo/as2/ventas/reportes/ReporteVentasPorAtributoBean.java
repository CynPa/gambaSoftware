/*   1:    */ package com.asinfo.as2.ventas.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Canal;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.Zona;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  15:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  16:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  17:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioCanal;
/*  18:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  19:    */ import com.asinfo.as2.ventas.reportes.servicio.ServicioReporteVenta;
/*  20:    */ import java.io.IOException;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Calendar;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.List;
/*  25:    */ import java.util.Map;
/*  26:    */ import javax.annotation.PostConstruct;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import javax.faces.bean.ManagedBean;
/*  29:    */ import javax.faces.bean.ViewScoped;
/*  30:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  31:    */ import net.sf.jasperreports.engine.JRException;
/*  32:    */ import org.apache.log4j.Logger;
/*  33:    */ 
/*  34:    */ @ManagedBean
/*  35:    */ @ViewScoped
/*  36:    */ public class ReporteVentasPorAtributoBean
/*  37:    */   extends AbstractBaseReportBean
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 1584134027790279238L;
/*  40:    */   @EJB
/*  41:    */   private ServicioReporteVenta servicioReporteVenta;
/*  42:    */   @EJB
/*  43:    */   private ServicioEmpresa servicioEmpresa;
/*  44:    */   @EJB
/*  45:    */   private ServicioCanal servicioCanal;
/*  46:    */   @EJB
/*  47:    */   private ServicioZona servicioZona;
/*  48:    */   @EJB
/*  49:    */   private ServicioSucursal servicioSucursal;
/*  50:    */   @EJB
/*  51:    */   private ServicioAtributo servicioAtributo;
/*  52:    */   private Empresa empresa;
/*  53:    */   private int idCanal;
/*  54:    */   private int idZona;
/*  55:    */   private int idSucursal;
/*  56:    */   private int idAtributo;
/*  57:    */   private Date fechaDesde;
/*  58:    */   private Date fechaHasta;
/*  59:    */   private List<Empresa> listaClienteCombo;
/*  60:    */   private List<Zona> listaZonaCombo;
/*  61:    */   private List<Canal> listaCanalCombo;
/*  62:    */   private List<Sucursal> listaSucursalCombo;
/*  63:    */   private List<Atributo> listaAtributoCombo;
/*  64:    */   
/*  65:    */   protected JRDataSource getJRDataSource()
/*  66:    */   {
/*  67: 96 */     List listaDatosReporte = new ArrayList();
/*  68: 97 */     JRDataSource ds = null;
/*  69:    */     
/*  70: 99 */     listaDatosReporte = this.servicioReporteVenta.geteReporteVentasPorAtributo(this.fechaDesde, this.fechaHasta, this.empresa.getId(), 0, false, this.idCanal, this.idZona, this.idSucursal, this.idAtributo, 
/*  71:100 */       AppUtil.getOrganizacion().getId());
/*  72:101 */     String[] fields = { "f_codigoSucursal", "f_nombreSucursal", "f_codigoCanal", "f_nombreCanal", "f_codigoZona", "f_nombreZona", "f_codigoVendedor", "f_nombreVendedor", "f_numeroFactura", "f_fechaFactura", "f_nombreCliente", "f_identificacionCliente", "f_totalFactura", "f_descuentoFactura", "f_impuestoFactura", "f_codigoProducto", "f_codigoComercial", "f_nombreProducto", "f_unidad", "f_cantidad", "f_precio", "f_descripcion", "f_descuento", "f_cantidadDevolucion", "f_precioDevolucion", "f_precioNotaCredito", "f_valorAtributo" };
/*  73:    */     
/*  74:    */ 
/*  75:    */ 
/*  76:    */ 
/*  77:106 */     ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  78:    */     
/*  79:108 */     return ds;
/*  80:    */   }
/*  81:    */   
/*  82:    */   @PostConstruct
/*  83:    */   public void init()
/*  84:    */   {
/*  85:113 */     Calendar calfechaDesde = Calendar.getInstance();
/*  86:114 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  87:115 */     this.fechaDesde = calfechaDesde.getTime();
/*  88:116 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected String getCompileFileName()
/*  92:    */   {
/*  93:127 */     return "reporteVentasPorAtributo";
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected Map<String, Object> getReportParameters()
/*  97:    */   {
/*  98:139 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  99:140 */     reportParameters.put("ReportTitle", "Ventas por Atributo");
/* 100:141 */     reportParameters.put("FechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/* 101:142 */     reportParameters.put("FechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/* 102:143 */     reportParameters.put("Total", "Total");
/* 103:144 */     return reportParameters;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String execute()
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:154 */       validaDatos();
/* 111:155 */       super.prepareReport();
/* 112:    */     }
/* 113:    */     catch (JRException e)
/* 114:    */     {
/* 115:157 */       LOG.info("Error JRException");
/* 116:158 */       e.printStackTrace();
/* 117:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 118:    */     }
/* 119:    */     catch (IOException e)
/* 120:    */     {
/* 121:161 */       LOG.info("Error IOException");
/* 122:162 */       e.printStackTrace();
/* 123:163 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 124:    */     }
/* 125:166 */     return null;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void validaDatos()
/* 129:    */   {
/* 130:171 */     if (this.empresa == null)
/* 131:    */     {
/* 132:172 */       this.empresa = new Empresa();
/* 133:173 */       this.empresa.setId(-1);
/* 134:    */     }
/* 135:175 */     if (this.fechaDesde == null) {
/* 136:176 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 137:    */     }
/* 138:178 */     if (this.fechaHasta == null) {
/* 139:179 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 140:    */     }
/* 141:    */   }
/* 142:    */   
/* 143:    */   public Date getFechaDesde()
/* 144:    */   {
/* 145:189 */     return this.fechaDesde;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setFechaDesde(Date fechaDesde)
/* 149:    */   {
/* 150:199 */     this.fechaDesde = fechaDesde;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public Date getFechaHasta()
/* 154:    */   {
/* 155:208 */     return this.fechaHasta;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setFechaHasta(Date fechaHasta)
/* 159:    */   {
/* 160:218 */     this.fechaHasta = fechaHasta;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Empresa getEmpresa()
/* 164:    */   {
/* 165:227 */     if (this.empresa == null)
/* 166:    */     {
/* 167:228 */       this.empresa = new Empresa();
/* 168:229 */       this.empresa.setId(0);
/* 169:    */     }
/* 170:231 */     return this.empresa;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void setEmpresa(Empresa empresa)
/* 174:    */   {
/* 175:241 */     this.empresa = empresa;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public int getIdCanal()
/* 179:    */   {
/* 180:250 */     return this.idCanal;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public void setIdCanal(int idCanal)
/* 184:    */   {
/* 185:260 */     this.idCanal = idCanal;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int getIdZona()
/* 189:    */   {
/* 190:269 */     return this.idZona;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setIdZona(int idZona)
/* 194:    */   {
/* 195:279 */     this.idZona = idZona;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public int getIdSucursal()
/* 199:    */   {
/* 200:288 */     return this.idSucursal;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void setIdSucursal(int idSucursal)
/* 204:    */   {
/* 205:298 */     this.idSucursal = idSucursal;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public int getIdAtributo()
/* 209:    */   {
/* 210:307 */     return this.idAtributo;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void setIdAtributo(int idAtributo)
/* 214:    */   {
/* 215:317 */     this.idAtributo = idAtributo;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 219:    */   {
/* 220:321 */     return this.servicioEmpresa.autocompletarClientes(consulta);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<Empresa> getListaClienteCombo()
/* 224:    */   {
/* 225:330 */     if (this.listaClienteCombo == null) {
/* 226:331 */       this.listaClienteCombo = this.servicioEmpresa.obtenerClientes();
/* 227:    */     }
/* 228:333 */     return this.listaClienteCombo;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public void setListaClienteCombo(List<Empresa> listaClienteCombo)
/* 232:    */   {
/* 233:343 */     this.listaClienteCombo = listaClienteCombo;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public List<Zona> getListaZonaCombo()
/* 237:    */   {
/* 238:352 */     if (this.listaZonaCombo == null) {
/* 239:353 */       this.listaZonaCombo = this.servicioZona.obtenerListaCombo("nombre", true, null);
/* 240:    */     }
/* 241:355 */     return this.listaZonaCombo;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public void setListaZonaCombo(List<Zona> listaZonaCombo)
/* 245:    */   {
/* 246:365 */     this.listaZonaCombo = listaZonaCombo;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public List<Canal> getListaCanalCombo()
/* 250:    */   {
/* 251:374 */     if (this.listaCanalCombo == null) {
/* 252:375 */       this.listaCanalCombo = this.servicioCanal.obtenerListaCombo("nombre", true, null);
/* 253:    */     }
/* 254:377 */     return this.listaCanalCombo;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setListaCanalCombo(List<Canal> listaCanalCombo)
/* 258:    */   {
/* 259:387 */     this.listaCanalCombo = listaCanalCombo;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public List<Sucursal> getListaSucursalCombo()
/* 263:    */   {
/* 264:396 */     if (this.listaSucursalCombo == null) {
/* 265:397 */       this.listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 266:    */     }
/* 267:399 */     return this.listaSucursalCombo;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setListaSucursalCombo(List<Sucursal> listaSucursalCombo)
/* 271:    */   {
/* 272:409 */     if (listaSucursalCombo == null) {
/* 273:410 */       listaSucursalCombo = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 274:    */     }
/* 275:412 */     this.listaSucursalCombo = listaSucursalCombo;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public List<Atributo> getListaAtributoCombo()
/* 279:    */   {
/* 280:421 */     if (this.listaAtributoCombo == null)
/* 281:    */     {
/* 282:422 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 283:423 */       filters.put("indicadorProducto", "true");
/* 284:424 */       this.listaAtributoCombo = this.servicioAtributo.obtenerListaCombo("nombre", true, filters);
/* 285:    */     }
/* 286:426 */     return this.listaAtributoCombo;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setListaAtributoCombo(List<Atributo> listaAtributoCombo)
/* 290:    */   {
/* 291:436 */     this.listaAtributoCombo = listaAtributoCombo;
/* 292:    */   }
/* 293:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.reportes.ReporteVentasPorAtributoBean
 * JD-Core Version:    0.7.0.1
 */