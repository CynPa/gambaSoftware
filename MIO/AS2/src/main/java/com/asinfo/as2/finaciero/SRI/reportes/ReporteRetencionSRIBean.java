/*   1:    */ package com.asinfo.as2.finaciero.SRI.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.ReporteComprasVentasRetenciones;
/*   4:    */ import com.asinfo.as2.clases.ReporteGeneralRetencion;
/*   5:    */ import com.asinfo.as2.clases.ReporteRetencionesResumido;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   7:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   8:    */ import com.asinfo.as2.controller.LanguageController;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  13:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaProveedorSRI;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  16:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  17:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Calendar;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.faces.bean.ManagedBean;
/*  25:    */ import javax.faces.bean.ViewScoped;
/*  26:    */ import javax.faces.model.SelectItem;
/*  27:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  28:    */ import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/*  29:    */ import org.apache.log4j.Logger;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ReporteRetencionSRIBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 5705884098905402775L;
/*  37:    */   @EJB
/*  38:    */   private ServicioFacturaProveedorSRI servicioFacturaProveedorSRI;
/*  39:    */   @EJB
/*  40:    */   private ServicioSucursal servicioSucursal;
/*  41:    */   @EJB
/*  42:    */   private ServicioPuntoDeVenta servicioPuntoVenta;
/*  43:    */   private List<SelectItem> listaMes;
/*  44:    */   
/*  45:    */   static enum TipoReporte
/*  46:    */   {
/*  47: 53 */     POR_CONCEPTO("Por Concepto"),  POR_FACTURA("Por Factura"),  RESUMIDO("Resumido"),  POR_RETENCION("Por Retencion");
/*  48:    */     
/*  49:    */     public String nombre;
/*  50:    */     
/*  51:    */     private TipoReporte(String nombre)
/*  52:    */     {
/*  53: 58 */       this.nombre = nombre;
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57: 73 */   private int anio = Calendar.getInstance().get(1);
/*  58: 74 */   private int mes = Calendar.getInstance().get(2);
/*  59: 75 */   private final int idOrganizacion = AppUtil.getOrganizacion().getId();
/*  60:    */   private TipoReporte tipoReporte;
/*  61:    */   private Sucursal sucursal;
/*  62:    */   private Sucursal sucursalRetencion;
/*  63:    */   private PuntoDeVenta puntoVentaRetencion;
/*  64: 80 */   private boolean anuladas = false;
/*  65: 81 */   private boolean mostrarFiltro = false;
/*  66:    */   private List<Sucursal> listaSucursal;
/*  67:    */   private List<PuntoDeVenta> listaPuntoVenta;
/*  68:    */   
/*  69:    */   protected JRDataSource getJRDataSource()
/*  70:    */   {
/*  71: 94 */     JRDataSource ds = null;
/*  72: 95 */     List listaDatosReporte = null;
/*  73: 96 */     String[] fields = null;
/*  74:    */     try
/*  75:    */     {
/*  76: 99 */       switch (1.$SwitchMap$com$asinfo$as2$finaciero$SRI$reportes$ReporteRetencionSRIBean$TipoReporte[this.tipoReporte.ordinal()])
/*  77:    */       {
/*  78:    */       case 1: 
/*  79:    */       case 2: 
/*  80:    */       case 3: 
/*  81:103 */         listaDatosReporte = this.servicioFacturaProveedorSRI.getRetencionSRI(this.mes, this.anio, this.idOrganizacion, this.sucursal, this.sucursalRetencion, this.puntoVentaRetencion, this.tipoReporte
/*  82:104 */           .name(), this.anuladas);
/*  83:    */         
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:111 */         fields = new String[] { "fechaEmisionRetencion", "fechaRegistro", "fechaEmision", "numeroFactura", "identificacionProveedor", "conceptoRetencion", "baseImponibleRetencion", "porcentajeRetencion", "valorRetencion", "codigoConceptoRetencionSRI", "Tipo", "numeroRetencion", "baseImponibleTarifaCero", "baseImponibleDiferenteCero", "baseImponibleNoObjetoIva", "nombreProveedor", "montoIva", "montoIce", "codigoCreditoTributario", "autorizacion", "fechaFacturaProveedor", "f_autorizacion", "f_claveAcceso", "f_estado", "idFacturaProveedorSRI", "notaFactura" };
/*  90:    */         
/*  91:    */ 
/*  92:    */ 
/*  93:    */ 
/*  94:116 */         ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  95:117 */         break;
/*  96:    */       case 4: 
/*  97:119 */         ReporteGeneralRetencion reporteGeneralRetencion = new ReporteGeneralRetencion();
/*  98:    */         
/*  99:121 */         List<ReporteComprasVentasRetenciones> listaDatosReporteCompras = this.servicioFacturaProveedorSRI.getReporteCompras(this.mes, this.anio, this.idOrganizacion);
/* 100:    */         
/* 101:123 */         reporteGeneralRetencion.setListaRetencionCompra(listaDatosReporteCompras);
/* 102:    */         
/* 103:125 */         List<ReporteComprasVentasRetenciones> listaDatosReporteVentas = this.servicioFacturaProveedorSRI.getReporteVentas(this.mes, this.anio, this.idOrganizacion, true);
/* 104:    */         
/* 105:127 */         reporteGeneralRetencion.setListaRetencionVenta(listaDatosReporteVentas);
/* 106:    */         
/* 107:129 */         List<ReporteComprasVentasRetenciones> listaDatosReporteVentasFisicas = this.servicioFacturaProveedorSRI.getReporteVentas(this.mes, this.anio, this.idOrganizacion, false);
/* 108:    */         
/* 109:131 */         reporteGeneralRetencion.setListaDatosReporteVentasFisicas(listaDatosReporteVentasFisicas);
/* 110:    */         
/* 111:133 */         List<ReporteComprasVentasRetenciones> listaDatosReporteRetencionCliente = this.servicioFacturaProveedorSRI.getReporteRetencionClientes(this.mes, this.anio, this.idOrganizacion);
/* 112:    */         
/* 113:135 */         reporteGeneralRetencion.setListaRetencionCliente(listaDatosReporteRetencionCliente);
/* 114:    */         
/* 115:137 */         List<ReporteComprasVentasRetenciones> listaDatosReporteExportaciones = this.servicioFacturaProveedorSRI.getReporteExportaciones(this.mes, this.anio, this.idOrganizacion);
/* 116:    */         
/* 117:139 */         reporteGeneralRetencion.setListaRetencionExportacion(listaDatosReporteExportaciones);
/* 118:    */         
/* 119:141 */         List<ReporteComprasVentasRetenciones> listaDatosReporteAnulados = this.servicioFacturaProveedorSRI.getNumeroComprobantesAnulados(this.mes, this.anio, this.idOrganizacion);
/* 120:    */         
/* 121:143 */         reporteGeneralRetencion.setListaRetencionAnulado(listaDatosReporteAnulados);
/* 122:    */         
/* 123:145 */         List<ReporteRetencionesResumido> listaReporteRetencionesResumido = this.servicioFacturaProveedorSRI.getRetencionSRIResumido(this.mes, this.anio, this.idOrganizacion);
/* 124:    */         
/* 125:    */ 
/* 126:    */ 
/* 127:149 */         reporteGeneralRetencion.setListaReporteRetencionesResumido(listaReporteRetencionesResumido);
/* 128:    */         
/* 129:151 */         List<ReporteGeneralRetencion> listaReporteGeneralRetencion = new ArrayList();
/* 130:152 */         listaReporteGeneralRetencion.add(reporteGeneralRetencion);
/* 131:    */         
/* 132:154 */         ds = new JRBeanCollectionDataSource(listaReporteGeneralRetencion);
/* 133:    */       }
/* 134:    */     }
/* 135:    */     catch (Exception e)
/* 136:    */     {
/* 137:158 */       LOG.info("Error " + e);
/* 138:159 */       e.printStackTrace();
/* 139:    */     }
/* 140:161 */     return ds;
/* 141:    */   }
/* 142:    */   
/* 143:    */   protected String getCompileFileName()
/* 144:    */   {
/* 145:167 */     String nombreReporte = "";
/* 146:168 */     switch (1.$SwitchMap$com$asinfo$as2$finaciero$SRI$reportes$ReporteRetencionSRIBean$TipoReporte[this.tipoReporte.ordinal()])
/* 147:    */     {
/* 148:    */     case 1: 
/* 149:170 */       nombreReporte = "reporteRetencionSRIDetallado";
/* 150:171 */       break;
/* 151:    */     case 4: 
/* 152:173 */       nombreReporte = "reporteRetencionesResumidoSRI";
/* 153:174 */       break;
/* 154:    */     case 3: 
/* 155:176 */       nombreReporte = "reporteRetencionSRIDetalladoPorFactura";
/* 156:177 */       break;
/* 157:    */     case 2: 
/* 158:180 */       nombreReporte = "reporteRetencionSRIDetalladoPorRetencion";
/* 159:    */     }
/* 160:184 */     return nombreReporte;
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected Map<String, Object> getReportParameters()
/* 164:    */   {
/* 165:190 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 166:191 */     reportParameters.put("ReportTitle", getLanguageController().getMensaje("msg_reporte_retencion_sri_titulo"));
/* 167:192 */     reportParameters.put("mes", FuncionesUtiles.nombreMes(this.mes - 1));
/* 168:193 */     reportParameters.put("anio", Integer.valueOf(this.anio));
/* 169:194 */     reportParameters.put("p_sucursal", this.sucursal != null ? this.sucursal.getNombre() : "Todos");
/* 170:195 */     reportParameters.put("p_sucursal_retencion", this.sucursalRetencion != null ? this.sucursalRetencion.getNombre() : "Todos");
/* 171:196 */     reportParameters.put("p_punto_venta_retencion", this.puntoVentaRetencion != null ? this.puntoVentaRetencion.getNombre() : "Todos");
/* 172:197 */     reportParameters.put("p_tipo_reporte", this.tipoReporte.equals(TipoReporte.POR_RETENCION) ? TipoReporte.POR_RETENCION.name() : "");
/* 173:198 */     if (this.tipoReporte == TipoReporte.RESUMIDO) {
/* 174:199 */       reportParameters.put("SUBREPORT_DIR", getPathReportes());
/* 175:    */     }
/* 176:202 */     return reportParameters;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public String execute()
/* 180:    */   {
/* 181:    */     try
/* 182:    */     {
/* 183:212 */       super.prepareReport();
/* 184:    */     }
/* 185:    */     catch (Exception e)
/* 186:    */     {
/* 187:214 */       e.printStackTrace();
/* 188:    */     }
/* 189:217 */     return null;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void actualizarMostrarFiltro()
/* 193:    */   {
/* 194:221 */     switch (1.$SwitchMap$com$asinfo$as2$finaciero$SRI$reportes$ReporteRetencionSRIBean$TipoReporte[this.tipoReporte.ordinal()])
/* 195:    */     {
/* 196:    */     case 2: 
/* 197:    */     case 3: 
/* 198:224 */       this.mostrarFiltro = true;
/* 199:225 */       break;
/* 200:    */     default: 
/* 201:227 */       this.mostrarFiltro = false;
/* 202:    */     }
/* 203:    */   }
/* 204:    */   
/* 205:    */   public List<SelectItem> getListaMes()
/* 206:    */   {
/* 207:233 */     if (this.listaMes == null)
/* 208:    */     {
/* 209:234 */       this.listaMes = new ArrayList();
/* 210:235 */       for (Mes t : Mes.values())
/* 211:    */       {
/* 212:236 */         SelectItem item = new SelectItem(Integer.valueOf(t.ordinal() + 1), t.toString());
/* 213:237 */         this.listaMes.add(item);
/* 214:    */       }
/* 215:    */     }
/* 216:240 */     return this.listaMes;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setListaMes(List<SelectItem> listaMes)
/* 220:    */   {
/* 221:244 */     this.listaMes = listaMes;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public int getMes()
/* 225:    */   {
/* 226:248 */     return this.mes;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setMes(int mes)
/* 230:    */   {
/* 231:252 */     this.mes = mes;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public int getAnio()
/* 235:    */   {
/* 236:256 */     return this.anio;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setAnio(int anio)
/* 240:    */   {
/* 241:260 */     this.anio = anio;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public List<SelectItem> getListaTipoReporte()
/* 245:    */   {
/* 246:264 */     List<SelectItem> listaSelectItems = new ArrayList();
/* 247:265 */     for (TipoReporte tipoReporte : TipoReporte.values())
/* 248:    */     {
/* 249:266 */       SelectItem item = new SelectItem(tipoReporte, tipoReporte.nombre);
/* 250:267 */       listaSelectItems.add(item);
/* 251:    */     }
/* 252:269 */     return listaSelectItems;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public TipoReporte getTipoReporte()
/* 256:    */   {
/* 257:273 */     return this.tipoReporte;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void setTipoReporte(TipoReporte tipoReporte)
/* 261:    */   {
/* 262:277 */     this.tipoReporte = tipoReporte;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public Sucursal getSucursal()
/* 266:    */   {
/* 267:284 */     return this.sucursal;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setSucursal(Sucursal sucursal)
/* 271:    */   {
/* 272:292 */     this.sucursal = sucursal;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public Sucursal getSucursalRetencion()
/* 276:    */   {
/* 277:299 */     return this.sucursalRetencion;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setSucursalRetencion(Sucursal sucursalRetencion)
/* 281:    */   {
/* 282:307 */     this.sucursalRetencion = sucursalRetencion;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public PuntoDeVenta getPuntoVentaRetencion()
/* 286:    */   {
/* 287:314 */     return this.puntoVentaRetencion;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public void setPuntoVentaRetencion(PuntoDeVenta puntoVentaRetencion)
/* 291:    */   {
/* 292:322 */     this.puntoVentaRetencion = puntoVentaRetencion;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public List<Sucursal> getListaSucursal()
/* 296:    */   {
/* 297:329 */     if (this.listaSucursal == null)
/* 298:    */     {
/* 299:330 */       Map<String, String> filters = new HashMap();
/* 300:331 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 301:332 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 302:    */     }
/* 303:335 */     return this.listaSucursal;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public List<PuntoDeVenta> getListaPuntoVenta()
/* 307:    */   {
/* 308:342 */     if (this.listaPuntoVenta == null)
/* 309:    */     {
/* 310:343 */       Map<String, String> filters = new HashMap();
/* 311:344 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 312:345 */       this.listaPuntoVenta = this.servicioPuntoVenta.obtenerListaCombo("nombre", true, filters);
/* 313:    */     }
/* 314:348 */     return this.listaPuntoVenta;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public void setListaPuntoVenta(List<PuntoDeVenta> listaPuntoVenta)
/* 318:    */   {
/* 319:356 */     this.listaPuntoVenta = listaPuntoVenta;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 323:    */   {
/* 324:364 */     this.listaSucursal = listaSucursal;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public boolean isAnuladas()
/* 328:    */   {
/* 329:371 */     return this.anuladas;
/* 330:    */   }
/* 331:    */   
/* 332:    */   public void setAnuladas(boolean anuladas)
/* 333:    */   {
/* 334:379 */     this.anuladas = anuladas;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public boolean isMostrarFiltro()
/* 338:    */   {
/* 339:386 */     return this.mostrarFiltro;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public void setMostrarFiltro(boolean mostrarFiltro)
/* 343:    */   {
/* 344:394 */     this.mostrarFiltro = mostrarFiltro;
/* 345:    */   }
/* 346:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.SRI.reportes.ReporteRetencionSRIBean
 * JD-Core Version:    0.7.0.1
 */