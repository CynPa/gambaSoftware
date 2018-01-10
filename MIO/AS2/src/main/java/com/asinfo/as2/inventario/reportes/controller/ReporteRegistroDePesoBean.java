/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Documento;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Lote;
/*   9:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  10:    */ import com.asinfo.as2.entities.Organizacion;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.RegistroPeso;
/*  13:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  14:    */ import com.asinfo.as2.enumeraciones.EstadoControlCalidad;
/*  15:    */ import com.asinfo.as2.enumeraciones.TipoRegistroPeso;
/*  16:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  18:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  19:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioRegistroPeso;
/*  20:    */ import com.asinfo.as2.util.AppUtil;
/*  21:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  22:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  23:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  24:    */ import java.io.IOException;
/*  25:    */ import java.util.ArrayList;
/*  26:    */ import java.util.Calendar;
/*  27:    */ import java.util.Date;
/*  28:    */ import java.util.List;
/*  29:    */ import java.util.Map;
/*  30:    */ import javax.annotation.PostConstruct;
/*  31:    */ import javax.ejb.EJB;
/*  32:    */ import javax.faces.bean.ManagedBean;
/*  33:    */ import javax.faces.bean.ViewScoped;
/*  34:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  35:    */ import net.sf.jasperreports.engine.JRException;
/*  36:    */ import org.apache.log4j.Logger;
/*  37:    */ 
/*  38:    */ @ManagedBean
/*  39:    */ @ViewScoped
/*  40:    */ public class ReporteRegistroDePesoBean
/*  41:    */   extends AbstractBaseReportBean
/*  42:    */ {
/*  43:    */   private static final long serialVersionUID = 3748793990194304607L;
/*  44:    */   @EJB
/*  45:    */   private ServicioRegistroPeso servicioRegistroPeso;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioDocumento servicioDocumento;
/*  48:    */   @EJB
/*  49:    */   private ServicioEmpresa servicioEmpresa;
/*  50:    */   @EJB
/*  51:    */   private ServicioMovimientoInventario servicioTransferencia;
/*  52:    */   @EJB
/*  53:    */   private ServicioLote servicioLote;
/*  54:    */   private RegistroPeso registroPeso;
/*  55:    */   private Date fechaDesde;
/*  56:    */   private Date fechaHasta;
/*  57:    */   private EstadoControlCalidad estadoControlCalidad;
/*  58:    */   private Documento documento;
/*  59:    */   private Empresa empresa;
/*  60:    */   private Producto producto;
/*  61:    */   private MovimientoInventario transferencia;
/*  62:    */   private Lote lote;
/*  63:    */   private TipoRegistroPeso tipoRegistroPeso;
/*  64:    */   private List<Documento> listaDocumento;
/*  65:    */   private List<TipoRegistroPeso> listaTipoRegistroPeso;
/*  66:    */   
/*  67:    */   protected JRDataSource getJRDataSource()
/*  68:    */   {
/*  69: 94 */     List listaDatosReporte = new ArrayList();
/*  70: 95 */     JRDataSource ds = null;
/*  71:    */     try
/*  72:    */     {
/*  73:    */       String[] fields;
/*  74:    */       String[] fields;
/*  75: 98 */       if (this.tipoRegistroPeso.equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA))
/*  76:    */       {
/*  77: 99 */         listaDatosReporte = this.servicioRegistroPeso.getReporteRegistroPesoRecepcionMateriaPrima(this.fechaDesde, this.fechaHasta, this.empresa, this.producto, this.lote);
/*  78:100 */         fields = new String[] { "f_fechaRegistroPeso", "f_codigoProducto", "f_nombreProducto", "f_numeroRegistroPeso", "f_numeroGuiaRemision", "f_nombreChofer", "f_pesoReferencia", "f_pesoNeto", "f_nombreEmpresa", "f_pesoDiferencia", "f_lote" };
/*  79:    */       }
/*  80:    */       else
/*  81:    */       {
/*  82:103 */         listaDatosReporte = this.servicioRegistroPeso.getReporteRegistroPesoTransferencia(this.fechaDesde, this.fechaHasta, this.producto, this.transferencia, this.lote, this.tipoRegistroPeso);
/*  83:    */         
/*  84:105 */         fields = new String[] { "f_fechaRegistroPeso", "f_codigoProducto", "f_nombreProducto", "f_numeroRegistroPeso", "f_numeroGuiaRemision", "f_nombreChofer", "f_pesoReferencia", "f_pesoNeto", "f_pesoEntrada", "f_pesoSalida", "f_pesoDestareTotal", "f_lote", "f_transferencia", "f_tipoRegistroPeso" };
/*  85:    */       }
/*  86:109 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  87:    */     }
/*  88:    */     catch (Exception e)
/*  89:    */     {
/*  90:111 */       addInfoMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  91:    */     }
/*  92:114 */     return ds;
/*  93:    */   }
/*  94:    */   
/*  95:    */   @PostConstruct
/*  96:    */   public void init()
/*  97:    */   {
/*  98:119 */     Calendar calfechaDesde = Calendar.getInstance();
/*  99:120 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/* 100:121 */     this.fechaDesde = calfechaDesde.getTime();
/* 101:122 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/* 102:123 */     this.tipoRegistroPeso = TipoRegistroPeso.INGRESO_MATERIA_PRIMA;
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected String getCompileFileName()
/* 106:    */   {
/* 107:128 */     if (this.tipoRegistroPeso.equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA)) {
/* 108:129 */       return "reporteRegistroPesoRecepcionMateriaPrima";
/* 109:    */     }
/* 110:131 */     return "reporteRegistroPesoTransferencia";
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected Map<String, Object> getReportParameters()
/* 114:    */   {
/* 115:138 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 116:139 */     if (this.tipoRegistroPeso.equals(TipoRegistroPeso.INGRESO_MATERIA_PRIMA))
/* 117:    */     {
/* 118:140 */       reportParameters.put("ReportTitle", "Registro Peso Recepcion Materia Prima");
/* 119:141 */       reportParameters.put("p_tipo", "Local");
/* 120:142 */       reportParameters.put("p_proveedor", this.empresa == null ? "TODOS" : this.empresa.getNombreFiscal());
/* 121:    */     }
/* 122:    */     else
/* 123:    */     {
/* 124:144 */       reportParameters.put("ReportTitle", "Registro Peso Transferencia");
/* 125:145 */       reportParameters.put("p_tipo", "Importacion");
/* 126:146 */       reportParameters.put("p_transferencia", this.transferencia == null ? "TODOS" : this.transferencia.getNumero());
/* 127:    */     }
/* 128:148 */     reportParameters.put("p_fechaDesde", this.fechaDesde);
/* 129:149 */     reportParameters.put("p_fechaHasta", this.fechaHasta);
/* 130:150 */     reportParameters.put("p_producto", this.producto == null ? "TODOS" : this.producto.getNombre());
/* 131:151 */     reportParameters.put("p_lote", this.lote == null ? "TODOS" : this.lote.getCodigo());
/* 132:152 */     reportParameters.put("p_documento", this.documento == null ? " " : this.documento.getNombre());
/* 133:153 */     return reportParameters;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String execute()
/* 137:    */   {
/* 138:    */     try
/* 139:    */     {
/* 140:162 */       super.prepareReport();
/* 141:    */     }
/* 142:    */     catch (JRException e)
/* 143:    */     {
/* 144:165 */       LOG.info("Error JRException");
/* 145:166 */       e.printStackTrace();
/* 146:167 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 147:    */     }
/* 148:    */     catch (IOException e)
/* 149:    */     {
/* 150:169 */       LOG.info("Error IOException");
/* 151:170 */       e.printStackTrace();
/* 152:171 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 153:    */     }
/* 154:174 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Date getFechaDesde()
/* 158:    */   {
/* 159:178 */     return this.fechaDesde;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setFechaDesde(Date fechaDesde)
/* 163:    */   {
/* 164:182 */     this.fechaDesde = fechaDesde;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Date getFechaHasta()
/* 168:    */   {
/* 169:186 */     return this.fechaHasta;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setFechaHasta(Date fechaHasta)
/* 173:    */   {
/* 174:190 */     this.fechaHasta = fechaHasta;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public EstadoControlCalidad getEstadoControlCalidad()
/* 178:    */   {
/* 179:194 */     return this.estadoControlCalidad;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setEstadoControlCalidad(EstadoControlCalidad estadoControlCalidad)
/* 183:    */   {
/* 184:198 */     this.estadoControlCalidad = estadoControlCalidad;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public RegistroPeso getRegistroPeso()
/* 188:    */   {
/* 189:202 */     return this.registroPeso;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setRegistroPeso(RegistroPeso registroPeso)
/* 193:    */   {
/* 194:206 */     this.registroPeso = registroPeso;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public List<Documento> getListaDocumento()
/* 198:    */   {
/* 199:210 */     if (this.listaDocumento == null) {
/* 200:    */       try
/* 201:    */       {
/* 202:212 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(DocumentoBase.REGISTRO_PESO, AppUtil.getOrganizacion().getId());
/* 203:    */       }
/* 204:    */       catch (ExcepcionAS2 e)
/* 205:    */       {
/* 206:214 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 207:    */       }
/* 208:    */     }
/* 209:217 */     return this.listaDocumento;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 213:    */   {
/* 214:221 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public Documento getDocumento()
/* 218:    */   {
/* 219:225 */     return this.documento;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setDocumento(Documento documento)
/* 223:    */   {
/* 224:229 */     this.documento = documento;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public Empresa getEmpresa()
/* 228:    */   {
/* 229:233 */     return this.empresa;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setEmpresa(Empresa empresa)
/* 233:    */   {
/* 234:237 */     this.empresa = empresa;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Producto getProducto()
/* 238:    */   {
/* 239:241 */     return this.producto;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setProducto(Producto producto)
/* 243:    */   {
/* 244:245 */     this.producto = producto;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public void cargarProducto(Producto producto)
/* 248:    */   {
/* 249:249 */     this.producto = producto;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public MovimientoInventario getTransferencia()
/* 253:    */   {
/* 254:253 */     return this.transferencia;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public void setTransferencia(MovimientoInventario transferencia)
/* 258:    */   {
/* 259:257 */     this.transferencia = transferencia;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public List<MovimientoInventario> autocompletarTransferencia(String consulta)
/* 263:    */   {
/* 264:262 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 265:263 */     filters.put("tipoRegistroPeso", this.tipoRegistroPeso.toString());
/* 266:264 */     if ((!consulta.isEmpty()) && (consulta != null)) {
/* 267:265 */       filters.put("transferenciaBodega.numero", consulta);
/* 268:    */     }
/* 269:267 */     List<MovimientoInventario> lista = new ArrayList();
/* 270:268 */     for (RegistroPeso registroPeso : this.servicioRegistroPeso.obtenerListaCombo("transferenciaBodega.numero", true, filters)) {
/* 271:269 */       if (registroPeso.getTransferenciaBodega() != null) {
/* 272:270 */         lista.add(registroPeso.getTransferenciaBodega());
/* 273:    */       }
/* 274:    */     }
/* 275:273 */     return lista;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public Lote getLote()
/* 279:    */   {
/* 280:277 */     return this.lote;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setLote(Lote lote)
/* 284:    */   {
/* 285:281 */     this.lote = lote;
/* 286:    */   }
/* 287:    */   
/* 288:    */   public List<Lote> autocompletarLotes(String consulta)
/* 289:    */   {
/* 290:285 */     return this.servicioLote.autocompletarLote(getProducto(), consulta);
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void eliminarProducto()
/* 294:    */   {
/* 295:289 */     setProducto(null);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public List<TipoRegistroPeso> getListaTipoRegistroPeso()
/* 299:    */   {
/* 300:293 */     if (this.listaTipoRegistroPeso == null)
/* 301:    */     {
/* 302:294 */       this.listaTipoRegistroPeso = new ArrayList();
/* 303:295 */       this.listaTipoRegistroPeso.add(TipoRegistroPeso.INGRESO_MATERIA_PRIMA);
/* 304:296 */       this.listaTipoRegistroPeso.add(TipoRegistroPeso.RECEPCION_TRANSFERENCIA);
/* 305:297 */       this.listaTipoRegistroPeso.add(TipoRegistroPeso.TRANSFERENCIA_ENTRE_BODEGAS);
/* 306:    */     }
/* 307:299 */     return this.listaTipoRegistroPeso;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public TipoRegistroPeso getTipoRegistroPeso()
/* 311:    */   {
/* 312:303 */     return this.tipoRegistroPeso;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setTipoRegistroPeso(TipoRegistroPeso tipoRegistroPeso)
/* 316:    */   {
/* 317:307 */     this.tipoRegistroPeso = tipoRegistroPeso;
/* 318:    */   }
/* 319:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteRegistroDePesoBean
 * JD-Core Version:    0.7.0.1
 */