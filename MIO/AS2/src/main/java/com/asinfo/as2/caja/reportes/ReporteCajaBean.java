/*   1:    */ package com.asinfo.as2.caja.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioCaja;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.controller.LanguageController;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioFormaPago;
/*   7:    */ import com.asinfo.as2.entities.Caja;
/*   8:    */ import com.asinfo.as2.entities.DetalleFormaCobro;
/*   9:    */ import com.asinfo.as2.entities.FormaPago;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Sucursal;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import java.io.IOException;
/*  17:    */ import java.util.ArrayList;
/*  18:    */ import java.util.Date;
/*  19:    */ import java.util.HashMap;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.faces.bean.ManagedBean;
/*  24:    */ import javax.faces.bean.ViewScoped;
/*  25:    */ import javax.faces.model.SelectItem;
/*  26:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  27:    */ import net.sf.jasperreports.engine.JRException;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.component.datatable.DataTable;
/*  30:    */ import org.primefaces.model.LazyDataModel;
/*  31:    */ import org.primefaces.model.SortOrder;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @ViewScoped
/*  35:    */ public class ReporteCajaBean
/*  36:    */   extends AbstractBaseReportBean
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 2581044010235310949L;
/*  39:    */   @EJB
/*  40:    */   private ServicioCaja servicioCaja;
/*  41:    */   @EJB
/*  42:    */   private ServicioSucursal servicioSucursal;
/*  43:    */   @EJB
/*  44:    */   private ServicioFormaPago servicioFormaPago;
/*  45:    */   private Date fechaDesde;
/*  46:    */   private Date fechaHasta;
/*  47:    */   private int idCuentaBancariaOrganizacion;
/*  48:    */   private int idSucursal;
/*  49:    */   private int idFormaPago;
/*  50:    */   private int idCaja;
/*  51: 58 */   private String nombreUsuario = "";
/*  52:    */   private List<SelectItem> listaSucursal;
/*  53:    */   private List<SelectItem> listaCaja;
/*  54:    */   private List<SelectItem> listaFormaPago;
/*  55:    */   private boolean resumen;
/*  56:    */   private LazyDataModel<DetalleFormaCobro> listaDetalleFormaCobro;
/*  57:    */   private DataTable dtDetalleFormaCobro;
/*  58:    */   
/*  59:    */   protected JRDataSource getJRDataSource()
/*  60:    */   {
/*  61: 72 */     List listaDatosReporte = new ArrayList();
/*  62: 73 */     JRDataSource ds = null;
/*  63:    */     try
/*  64:    */     {
/*  65: 76 */       listaDatosReporte = this.servicioCaja.getReporteCaja(this.fechaDesde, this.fechaHasta, this.nombreUsuario, this.idFormaPago);
/*  66:    */       
/*  67: 78 */       String[] fields = { "f_nombreFormaPago", "f_nombreCliente", "f_numero", "f_descripcion", "f_valor", "f_documentoReferencia", "f_usuario", "f_fecha" };
/*  68:    */       
/*  69:    */ 
/*  70: 81 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  71:    */     }
/*  72:    */     catch (Exception e)
/*  73:    */     {
/*  74: 83 */       LOG.info("Error " + e);
/*  75: 84 */       e.printStackTrace();
/*  76:    */     }
/*  77: 86 */     return ds;
/*  78:    */   }
/*  79:    */   
/*  80:    */   protected String getCompileFileName()
/*  81:    */   {
/*  82: 96 */     return "reporteCajaFormaPago";
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected Map<String, Object> getReportParameters()
/*  86:    */   {
/*  87:107 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  88:108 */     reportParameters.put("ReportTitle", "Movimientos Bancarios");
/*  89:109 */     reportParameters.put("fechaDesde", FuncionesUtiles.dateToString(this.fechaDesde));
/*  90:110 */     reportParameters.put("fechaHasta", FuncionesUtiles.dateToString(this.fechaHasta));
/*  91:111 */     return reportParameters;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String execute()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:117 */       validarDatos();
/*  99:    */       
/* 100:119 */       super.prepareReport();
/* 101:    */     }
/* 102:    */     catch (JRException e)
/* 103:    */     {
/* 104:122 */       LOG.info("Error JRException");
/* 105:123 */       e.printStackTrace();
/* 106:124 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 107:    */     }
/* 108:    */     catch (IOException e)
/* 109:    */     {
/* 110:126 */       LOG.info("Error IOException");
/* 111:127 */       e.printStackTrace();
/* 112:128 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 113:    */     }
/* 114:131 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void validarDatos()
/* 118:    */   {
/* 119:135 */     if (this.fechaDesde == null) {
/* 120:136 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 121:    */     }
/* 122:138 */     if (this.fechaHasta == null) {
/* 123:139 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String procesar()
/* 128:    */   {
/* 129:145 */     validarDatos();
/* 130:    */     
/* 131:147 */     this.listaDetalleFormaCobro = new LazyDataModel()
/* 132:    */     {
/* 133:    */       private static final long serialVersionUID = 1L;
/* 134:    */       
/* 135:    */       public List<DetalleFormaCobro> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/* 136:    */       {
/* 137:153 */         List<DetalleFormaCobro> lista = new ArrayList();
/* 138:    */         
/* 139:155 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/* 140:157 */         if (filters != null) {
/* 141:158 */           filters = new HashMap();
/* 142:    */         }
/* 143:161 */         filters.put("fechaDesde", "" + FuncionesUtiles.dateToString(ReporteCajaBean.this.fechaDesde));
/* 144:162 */         filters.put("fechaHasta", "" + FuncionesUtiles.dateToString(ReporteCajaBean.this.fechaHasta));
/* 145:164 */         if (ReporteCajaBean.this.nombreUsuario.compareTo("") != 0) {
/* 146:165 */           filters.put("nombreUsuario", "" + ReporteCajaBean.this.nombreUsuario);
/* 147:    */         }
/* 148:168 */         if (ReporteCajaBean.this.idFormaPago != 0) {
/* 149:169 */           filters.put("idFormaPago", "" + ReporteCajaBean.this.idFormaPago);
/* 150:    */         }
/* 151:176 */         return lista;
/* 152:    */       }
/* 153:179 */     };
/* 154:180 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Date getFechaDesde()
/* 158:    */   {
/* 159:189 */     return this.fechaDesde;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setFechaDesde(Date fechaDesde)
/* 163:    */   {
/* 164:199 */     this.fechaDesde = fechaDesde;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Date getFechaHasta()
/* 168:    */   {
/* 169:208 */     return this.fechaHasta;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setFechaHasta(Date fechaHasta)
/* 173:    */   {
/* 174:218 */     this.fechaHasta = fechaHasta;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int getIdCuentaBancariaOrganizacion()
/* 178:    */   {
/* 179:227 */     return this.idCuentaBancariaOrganizacion;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setIdCuentaBancariaOrganizacion(int idCuentaBancariaOrganizacion)
/* 183:    */   {
/* 184:237 */     this.idCuentaBancariaOrganizacion = idCuentaBancariaOrganizacion;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getIdFormaPago()
/* 188:    */   {
/* 189:246 */     return this.idFormaPago;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setIdFormaPago(int idFormaPago)
/* 193:    */   {
/* 194:256 */     this.idFormaPago = idFormaPago;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getNombreUsuario()
/* 198:    */   {
/* 199:265 */     return this.nombreUsuario;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setNombreUsuario(String nombreUsuario)
/* 203:    */   {
/* 204:275 */     this.nombreUsuario = nombreUsuario;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public List<SelectItem> getListaSucursal()
/* 208:    */   {
/* 209:284 */     if (this.listaSucursal == null)
/* 210:    */     {
/* 211:285 */       this.listaSucursal = new ArrayList();
/* 212:286 */       String sortField = "codigo";
/* 213:287 */       Map<String, String> filters = new HashMap();
/* 214:288 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 215:290 */       for (Sucursal sucursal : this.servicioSucursal.obtenerListaCombo(sortField, true, filters))
/* 216:    */       {
/* 217:291 */         SelectItem se = new SelectItem();
/* 218:292 */         se.setValue(Integer.valueOf(sucursal.getIdSucursal()));
/* 219:293 */         se.setLabel(sucursal.getNombre());
/* 220:294 */         this.listaSucursal.add(se);
/* 221:    */       }
/* 222:    */     }
/* 223:297 */     return this.listaSucursal;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void setListaSucursal(List<SelectItem> listaSucursal)
/* 227:    */   {
/* 228:307 */     this.listaSucursal = listaSucursal;
/* 229:    */   }
/* 230:    */   
/* 231:    */   public int getIdSucursal()
/* 232:    */   {
/* 233:316 */     return this.idSucursal;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setIdSucursal(int idSucursal)
/* 237:    */   {
/* 238:326 */     this.idSucursal = idSucursal;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public int getIdCaja()
/* 242:    */   {
/* 243:335 */     return this.idCaja;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setIdCaja(int idCaja)
/* 247:    */   {
/* 248:345 */     this.idCaja = idCaja;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public List<SelectItem> getListaCaja()
/* 252:    */   {
/* 253:354 */     if (this.listaCaja == null)
/* 254:    */     {
/* 255:355 */       this.listaCaja = new ArrayList();
/* 256:356 */       String sortField = "codigo";
/* 257:357 */       Map<String, String> filters = new HashMap();
/* 258:358 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 259:360 */       for (Caja caja : this.servicioCaja.obtenerListaCombo(sortField, true, filters))
/* 260:    */       {
/* 261:361 */         SelectItem se = new SelectItem();
/* 262:362 */         se.setValue(Integer.valueOf(caja.getIdCaja()));
/* 263:363 */         se.setLabel(caja.getNombre());
/* 264:364 */         this.listaCaja.add(se);
/* 265:    */       }
/* 266:    */     }
/* 267:367 */     return this.listaCaja;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void setListaCaja(List<SelectItem> listaCaja)
/* 271:    */   {
/* 272:377 */     this.listaCaja = listaCaja;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public boolean isResumen()
/* 276:    */   {
/* 277:386 */     return this.resumen;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public void setResumen(boolean resumen)
/* 281:    */   {
/* 282:396 */     this.resumen = resumen;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public List<SelectItem> getListaFormaPago()
/* 286:    */   {
/* 287:405 */     if (this.listaFormaPago == null)
/* 288:    */     {
/* 289:406 */       this.listaFormaPago = new ArrayList();
/* 290:407 */       String sortField = "codigo";
/* 291:408 */       Map<String, String> filters = new HashMap();
/* 292:409 */       filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/* 293:411 */       for (FormaPago formaPago : this.servicioFormaPago.obtenerListaCombo(sortField, true, filters))
/* 294:    */       {
/* 295:412 */         SelectItem se = new SelectItem();
/* 296:413 */         se.setValue(Integer.valueOf(formaPago.getIdFormaPago()));
/* 297:414 */         se.setLabel(formaPago.getNombre());
/* 298:415 */         this.listaFormaPago.add(se);
/* 299:    */       }
/* 300:    */     }
/* 301:418 */     return this.listaFormaPago;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public LazyDataModel<DetalleFormaCobro> getListaDetalleFormaCobro()
/* 305:    */   {
/* 306:427 */     return this.listaDetalleFormaCobro;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void setListaDetalleFormaCobro(LazyDataModel<DetalleFormaCobro> listaDetalleFormaCobro)
/* 310:    */   {
/* 311:437 */     this.listaDetalleFormaCobro = listaDetalleFormaCobro;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public DataTable getDtDetalleFormaCobro()
/* 315:    */   {
/* 316:446 */     return this.dtDetalleFormaCobro;
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void setDtDetalleFormaCobro(DataTable dtDetalleFormaCobro)
/* 320:    */   {
/* 321:456 */     this.dtDetalleFormaCobro = dtDetalleFormaCobro;
/* 322:    */   }
/* 323:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.caja.reportes.ReporteCajaBean
 * JD-Core Version:    0.7.0.1
 */