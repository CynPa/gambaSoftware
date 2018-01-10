/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   5:    */ import com.asinfo.as2.entities.Bodega;
/*   6:    */ import com.asinfo.as2.entities.Documento;
/*   7:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  10:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioMotivoAjusteInventario;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  15:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoInventario;
/*  16:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  20:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  21:    */ import java.io.IOException;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Calendar;
/*  24:    */ import java.util.Date;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.annotation.PostConstruct;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.faces.bean.ManagedBean;
/*  30:    */ import javax.faces.bean.ViewScoped;
/*  31:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  32:    */ import net.sf.jasperreports.engine.JRException;
/*  33:    */ import org.apache.log4j.Logger;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class ReporteAjusteInventarioProductoBean
/*  38:    */   extends AbstractBaseReportBean
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 1L;
/*  41:    */   @EJB
/*  42:    */   private ServicioReporteMovimientoInventario servicioReporteMovimientoInventario;
/*  43:    */   @EJB
/*  44:    */   private ServicioDocumento servicioDocumento;
/*  45:    */   @EJB
/*  46:    */   private ServicioBodega servicioBodega;
/*  47:    */   @EJB
/*  48:    */   private ServicioMotivoAjusteInventario servicioMotivoAjusteInventario;
/*  49:    */   @EJB
/*  50:    */   private ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  51:    */   private Date fechaDesde;
/*  52:    */   private Date fechaHasta;
/*  53:    */   private MotivoAjusteInventario motivoAjusteInventario;
/*  54:    */   private Documento documento;
/*  55:    */   private Bodega bodega;
/*  56:    */   private SubcategoriaProducto subcategoriaProducto;
/*  57:    */   private List<MotivoAjusteInventario> listaMotivoAjusteInventario;
/*  58:    */   private List<Documento> listaDocumento;
/*  59:    */   private List<Bodega> listaBodega;
/*  60:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  61:    */   
/*  62:    */   protected JRDataSource getJRDataSource()
/*  63:    */   {
/*  64: 85 */     List listaDatosReporte = new ArrayList();
/*  65: 86 */     JRDataSource ds = null;
/*  66:    */     try
/*  67:    */     {
/*  68: 88 */       listaDatosReporte = this.servicioReporteMovimientoInventario.getReporteAjusteInventario(this.fechaDesde, this.fechaHasta, getDocumento().getId(), 
/*  69: 89 */         getMotivoAjusteInventario().getId(), AppUtil.getOrganizacion().getId(), getSubcategoriaProducto().getId(), getBodega().getId());
/*  70:    */       
/*  71: 91 */       String[] fields = { "f_numero", "f_fecha", "f_descripcion", "f_codigoProducto", "f_nombreProducto", "f_unidad", "f_cantidad", "f_costo", "f_bodega", "f_motivoAjusteInventario", "f_documento", "nombreProductoNoComercial", "codigoAlternoProducto" };
/*  72:    */       
/*  73: 93 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  74:    */     }
/*  75:    */     catch (Exception e)
/*  76:    */     {
/*  77: 95 */       e.printStackTrace();
/*  78:    */     }
/*  79: 97 */     return ds;
/*  80:    */   }
/*  81:    */   
/*  82:    */   @PostConstruct
/*  83:    */   public void init()
/*  84:    */   {
/*  85:102 */     Calendar calfechaDesde = Calendar.getInstance();
/*  86:103 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  87:104 */     this.fechaDesde = calfechaDesde.getTime();
/*  88:105 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  89:    */   }
/*  90:    */   
/*  91:    */   protected String getCompileFileName()
/*  92:    */   {
/*  93:110 */     return "reporteAjusteInventarioProducto";
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected Map<String, Object> getReportParameters()
/*  97:    */   {
/*  98:116 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  99:117 */     reportParameters.put("ReportTitle", "Ajuste Inventario");
/* 100:118 */     reportParameters.put("fechaDesde", this.fechaDesde);
/* 101:119 */     reportParameters.put("fechaHasta", this.fechaHasta);
/* 102:120 */     reportParameters.put("motivoAjusteInventario", getMotivoAjusteInventario().getNombre());
/* 103:121 */     reportParameters.put("documento", getDocumento().getNombre());
/* 104:122 */     reportParameters.put("subcategoriaProducto", getSubcategoriaProducto().getNombre());
/* 105:123 */     reportParameters.put("p_bodega", this.bodega != null ? this.bodega.getNombre() : "Todos");
/* 106:124 */     return reportParameters;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String execute()
/* 110:    */   {
/* 111:    */     try
/* 112:    */     {
/* 113:133 */       validaDatos();
/* 114:134 */       super.prepareReport();
/* 115:    */     }
/* 116:    */     catch (JRException e)
/* 117:    */     {
/* 118:137 */       LOG.info("Error JRException");
/* 119:138 */       e.printStackTrace();
/* 120:139 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 121:    */     }
/* 122:    */     catch (IOException e)
/* 123:    */     {
/* 124:141 */       LOG.info("Error IOException");
/* 125:142 */       e.printStackTrace();
/* 126:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 127:    */     }
/* 128:146 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void validaDatos()
/* 132:    */   {
/* 133:150 */     if (this.fechaDesde == null) {
/* 134:151 */       this.fechaDesde = FuncionesUtiles.obtenerFechaInicial();
/* 135:    */     }
/* 136:153 */     if (this.fechaHasta == null) {
/* 137:154 */       this.fechaHasta = FuncionesUtiles.obtenerFechaFinal();
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public Date getFechaDesde()
/* 142:    */   {
/* 143:164 */     return this.fechaDesde;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setFechaDesde(Date fechaDesde)
/* 147:    */   {
/* 148:174 */     this.fechaDesde = fechaDesde;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public Date getFechaHasta()
/* 152:    */   {
/* 153:183 */     return this.fechaHasta;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setFechaHasta(Date fechaHasta)
/* 157:    */   {
/* 158:193 */     this.fechaHasta = fechaHasta;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public MotivoAjusteInventario getMotivoAjusteInventario()
/* 162:    */   {
/* 163:202 */     if (this.motivoAjusteInventario == null) {
/* 164:203 */       this.motivoAjusteInventario = new MotivoAjusteInventario();
/* 165:    */     }
/* 166:205 */     return this.motivoAjusteInventario;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setMotivoAjusteInventario(MotivoAjusteInventario motivoAjusteInventario)
/* 170:    */   {
/* 171:215 */     this.motivoAjusteInventario = motivoAjusteInventario;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Documento getDocumento()
/* 175:    */   {
/* 176:224 */     if (this.documento == null) {
/* 177:225 */       this.documento = new Documento();
/* 178:    */     }
/* 179:227 */     return this.documento;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setDocumento(Documento documento)
/* 183:    */   {
/* 184:237 */     this.documento = documento;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 188:    */   {
/* 189:246 */     if (this.subcategoriaProducto == null) {
/* 190:247 */       this.subcategoriaProducto = new SubcategoriaProducto();
/* 191:    */     }
/* 192:249 */     return this.subcategoriaProducto;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 196:    */   {
/* 197:259 */     this.subcategoriaProducto = subcategoriaProducto;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public List<MotivoAjusteInventario> getListaMotivoAjusteInventario()
/* 201:    */   {
/* 202:268 */     if (this.listaMotivoAjusteInventario == null) {
/* 203:269 */       this.listaMotivoAjusteInventario = this.servicioMotivoAjusteInventario.obtenerListaCombo("nombre", true, null);
/* 204:    */     }
/* 205:271 */     return this.listaMotivoAjusteInventario;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setListaMotivoAjusteInventario(List<MotivoAjusteInventario> listaMotivoAjusteInventario)
/* 209:    */   {
/* 210:281 */     this.listaMotivoAjusteInventario = listaMotivoAjusteInventario;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public List<Documento> getListaDocumento()
/* 214:    */   {
/* 215:290 */     if (this.listaDocumento == null) {
/* 216:    */       try
/* 217:    */       {
/* 218:292 */         this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.AJUSTE_INVENTARIO);
/* 219:    */       }
/* 220:    */       catch (ExcepcionAS2 e)
/* 221:    */       {
/* 222:294 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 223:    */       }
/* 224:    */     }
/* 225:297 */     return this.listaDocumento;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public List<Bodega> getListaBodega()
/* 229:    */   {
/* 230:306 */     if (this.listaBodega == null) {
/* 231:307 */       this.listaBodega = this.servicioBodega.obtenerListaComboPorUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario(), AppUtil.getOrganizacion()
/* 232:308 */         .getIdOrganizacion());
/* 233:    */     }
/* 234:310 */     return this.listaBodega;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 238:    */   {
/* 239:320 */     this.listaDocumento = listaDocumento;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 243:    */   {
/* 244:329 */     if (this.listaSubcategoriaProducto == null) {
/* 245:330 */       this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 246:    */     }
/* 247:332 */     return this.listaSubcategoriaProducto;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 251:    */   {
/* 252:342 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public Bodega getBodega()
/* 256:    */   {
/* 257:346 */     if (this.bodega == null) {
/* 258:347 */       this.bodega = new Bodega();
/* 259:    */     }
/* 260:349 */     return this.bodega;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void setBodega(Bodega bodega)
/* 264:    */   {
/* 265:353 */     this.bodega = bodega;
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteAjusteInventarioProductoBean
 * JD-Core Version:    0.7.0.1
 */