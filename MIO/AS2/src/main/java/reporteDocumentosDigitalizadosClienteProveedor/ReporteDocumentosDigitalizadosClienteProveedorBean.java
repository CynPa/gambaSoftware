/*   1:    */ package reporteDocumentosDigitalizadosClienteProveedor;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   5:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*   7:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoCategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCategoriaDocumentoDigitalizado;
/*  11:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDetalleDocumentoDigitalizado;
/*  12:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDocumentoDigitalizado;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  15:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import javax.faces.model.SelectItem;
/*  24:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ 
/*  27:    */ @ManagedBean
/*  28:    */ @ViewScoped
/*  29:    */ public class ReporteDocumentosDigitalizadosClienteProveedorBean
/*  30:    */   extends AbstractBaseReportBean
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @EJB
/*  34:    */   private ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  35:    */   @EJB
/*  36:    */   private ServicioEmpresa servicioEmpresa;
/*  37:    */   @EJB
/*  38:    */   private ServicioCategoriaDocumentoDigitalizado servicioCategoriaDocumentoDigitalizado;
/*  39:    */   @EJB
/*  40:    */   private ServicioDocumentoDigitalizado servicioDocumentoDigitalizado;
/*  41:    */   @EJB
/*  42:    */   private ServicioDetalleDocumentoDigitalizado servicioDetalleDocumentoDigitalizado;
/*  43:    */   private List<CategoriaEmpresa> listaCategoriaEmpresa;
/*  44:    */   private List<DocumentoDigitalizado> listaDocumentoDigitalizado;
/*  45:    */   private CategoriaEmpresa categoriaEmpresaSeleccionada;
/*  46:    */   private DocumentoDigitalizado documentoDigitalizadoSeleccionado;
/*  47: 60 */   private boolean indicadorCliente = true;
/*  48:    */   private boolean indicadorProveedor;
/*  49: 62 */   private boolean noCargados = true;
/*  50: 63 */   private boolean cargados = true;
/*  51: 64 */   private Date fechaVencer = null;
/*  52:    */   private Empresa empresaSeleccionada;
/*  53: 67 */   private final String COMPILE_FILE_NAME = "reporteDocumentosDigitalizadosClienteProveedor";
/*  54:    */   
/*  55:    */   private static enum enumClasificacion
/*  56:    */   {
/*  57: 70 */     Cliente,  Proveedor;
/*  58:    */     
/*  59:    */     private enumClasificacion() {}
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected JRDataSource getJRDataSource()
/*  63:    */   {
/*  64: 76 */     List listaDatosReporte = new ArrayList();
/*  65: 77 */     JRDataSource ds = null;
/*  66: 78 */     String[] fields = null;
/*  67:    */     try
/*  68:    */     {
/*  69: 80 */       listaDatosReporte = this.servicioDetalleDocumentoDigitalizado.reporteDocumentosDigitalizadosEmpresa(AppUtil.getOrganizacion().getId(), this.empresaSeleccionada, this.categoriaEmpresaSeleccionada, this.indicadorCliente, this.indicadorProveedor, this.documentoDigitalizadoSeleccionado, this.cargados, this.noCargados, this.fechaVencer);
/*  70:    */       
/*  71:    */ 
/*  72:    */ 
/*  73: 84 */       fields = new String[] { "f_categoria", "f_documento", "f_requerido", "f_eNombres", "f_eIdentificacion", "f_categoriaEmpresa", "f_desde", "f_hasta", "f_cargado" };
/*  74:    */       
/*  75:    */ 
/*  76: 87 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  77:    */     }
/*  78:    */     catch (Exception e)
/*  79:    */     {
/*  80: 90 */       LOG.info("Error " + e);
/*  81: 91 */       e.printStackTrace();
/*  82:    */     }
/*  83: 93 */     return ds;
/*  84:    */   }
/*  85:    */   
/*  86:    */   protected String getCompileFileName()
/*  87:    */   {
/*  88: 98 */     return "reporteDocumentosDigitalizadosClienteProveedor";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void actualizarCategoriaEmpresa()
/*  92:    */   {
/*  93:102 */     this.empresaSeleccionada = null;
/*  94:103 */     this.listaDocumentoDigitalizado = null;
/*  95:104 */     this.documentoDigitalizadoSeleccionado = null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void actualizarEmpresa()
/*  99:    */   {
/* 100:108 */     this.documentoDigitalizadoSeleccionado = null;
/* 101:109 */     this.listaDocumentoDigitalizado = null;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String execute()
/* 105:    */   {
/* 106:    */     try
/* 107:    */     {
/* 108:119 */       super.prepareReport();
/* 109:    */     }
/* 110:    */     catch (Exception e)
/* 111:    */     {
/* 112:121 */       e.printStackTrace();
/* 113:    */     }
/* 114:124 */     return null;
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected Map<String, Object> getReportParameters()
/* 118:    */   {
/* 119:130 */     Map<String, Object> reportParameters = super.getReportParameters();
/* 120:131 */     reportParameters.put("ReportTitle", "Reporte de Documentos Digitalizados Cliente/Proveedor");
/* 121:    */     
/* 122:133 */     return reportParameters;
/* 123:    */   }
/* 124:    */   
/* 125:136 */   private enumClasificacion clasificacion = enumClasificacion.Cliente;
/* 126:    */   private List<SelectItem> listaClasificacion;
/* 127:    */   
/* 128:    */   public List<Empresa> autocompletarEmpresa(String consulta)
/* 129:    */   {
/* 130:140 */     CategoriaEmpresa categoria = null;
/* 131:141 */     if (this.categoriaEmpresaSeleccionada != null) {
/* 132:142 */       categoria = this.categoriaEmpresaSeleccionada;
/* 133:143 */     } else if (this.empresaSeleccionada != null) {
/* 134:144 */       categoria = this.empresaSeleccionada.getCategoriaEmpresa();
/* 135:    */     }
/* 136:146 */     if (this.clasificacion == enumClasificacion.Cliente) {
/* 137:147 */       return this.servicioEmpresa.autocompletarClientes(consulta, true, categoria);
/* 138:    */     }
/* 139:149 */     return this.servicioEmpresa.autocompletarProveedores(consulta, true, categoria);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 143:    */   {
/* 144:159 */     if (this.listaCategoriaEmpresa == null) {
/* 145:160 */       this.listaCategoriaEmpresa = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/* 146:    */     }
/* 147:162 */     return this.listaCategoriaEmpresa;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setListaCategoriaEmpresa(List<CategoriaEmpresa> listaCategoriaEmpresa)
/* 151:    */   {
/* 152:172 */     this.listaCategoriaEmpresa = listaCategoriaEmpresa;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public enumClasificacion getClasificacion()
/* 156:    */   {
/* 157:181 */     return this.clasificacion;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setClasificacion(enumClasificacion clasificacion)
/* 161:    */   {
/* 162:191 */     this.clasificacion = clasificacion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public List<SelectItem> getListaClasificacion()
/* 166:    */   {
/* 167:198 */     if (this.listaClasificacion == null)
/* 168:    */     {
/* 169:199 */       this.listaClasificacion = new ArrayList();
/* 170:200 */       for (enumClasificacion clasificacion : enumClasificacion.values())
/* 171:    */       {
/* 172:201 */         SelectItem item = new SelectItem(clasificacion, clasificacion.name());
/* 173:202 */         this.listaClasificacion.add(item);
/* 174:    */       }
/* 175:    */     }
/* 176:205 */     return this.listaClasificacion;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setListaClasificacion(List<SelectItem> listaClasificacion)
/* 180:    */   {
/* 181:209 */     this.listaClasificacion = listaClasificacion;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean isIndicadorCliente()
/* 185:    */   {
/* 186:213 */     return this.indicadorCliente;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setIndicadorCliente(boolean indicadorCliente)
/* 190:    */   {
/* 191:217 */     this.indicadorCliente = indicadorCliente;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean isIndicadorProveedor()
/* 195:    */   {
/* 196:221 */     return this.indicadorProveedor;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setIndicadorProveedor(boolean indicadorProveedor)
/* 200:    */   {
/* 201:225 */     this.indicadorProveedor = indicadorProveedor;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void actualizarClasificacion()
/* 205:    */   {
/* 206:229 */     if (this.clasificacion.equals(enumClasificacion.Cliente))
/* 207:    */     {
/* 208:230 */       this.indicadorCliente = true;
/* 209:231 */       this.indicadorProveedor = false;
/* 210:    */     }
/* 211:    */     else
/* 212:    */     {
/* 213:233 */       this.indicadorProveedor = true;
/* 214:234 */       this.indicadorCliente = false;
/* 215:    */     }
/* 216:236 */     this.empresaSeleccionada = null;
/* 217:237 */     this.listaDocumentoDigitalizado = null;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public Empresa getEmpresaSeleccionada()
/* 221:    */   {
/* 222:241 */     return this.empresaSeleccionada;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setEmpresaSeleccionada(Empresa empresaSeleccionada)
/* 226:    */   {
/* 227:245 */     this.empresaSeleccionada = empresaSeleccionada;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public boolean isNoCargados()
/* 231:    */   {
/* 232:249 */     return this.noCargados;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setNoCargados(boolean noCargados)
/* 236:    */   {
/* 237:253 */     this.noCargados = noCargados;
/* 238:254 */     if (noCargados) {
/* 239:255 */       this.fechaVencer = null;
/* 240:    */     }
/* 241:    */   }
/* 242:    */   
/* 243:    */   public boolean isCargados()
/* 244:    */   {
/* 245:260 */     return this.cargados;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void setCargados(boolean cargados)
/* 249:    */   {
/* 250:264 */     this.cargados = cargados;
/* 251:    */   }
/* 252:    */   
/* 253:    */   public Date getFechaVencer()
/* 254:    */   {
/* 255:268 */     return this.fechaVencer;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setFechaVencer(Date fechaVencer)
/* 259:    */   {
/* 260:272 */     this.fechaVencer = fechaVencer;
/* 261:273 */     if (fechaVencer != null) {
/* 262:274 */       this.noCargados = false;
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public List<DocumentoDigitalizado> getListaDocumentoDigitalizado()
/* 267:    */   {
/* 268:279 */     if (this.listaDocumentoDigitalizado == null)
/* 269:    */     {
/* 270:280 */       CategoriaEmpresa categoriaEmpresa = null;
/* 271:281 */       if (this.categoriaEmpresaSeleccionada != null) {
/* 272:282 */         categoriaEmpresa = this.categoriaEmpresaSeleccionada;
/* 273:283 */       } else if (this.empresaSeleccionada != null) {
/* 274:284 */         categoriaEmpresa = this.empresaSeleccionada.getCategoriaEmpresa();
/* 275:    */       }
/* 276:288 */       if (categoriaEmpresa != null)
/* 277:    */       {
/* 278:289 */         categoriaEmpresa = this.servicioCategoriaEmpresa.cargarDetalle(categoriaEmpresa.getId());
/* 279:290 */         this.listaDocumentoDigitalizado = new ArrayList();
/* 280:291 */         for (DocumentoDigitalizadoCategoriaEmpresa documento : categoriaEmpresa.getListaDocumentoDigitalizadoCategoriaEmpresa()) {
/* 281:292 */           this.listaDocumentoDigitalizado.add(documento.getDocumentoDigitalizado());
/* 282:    */         }
/* 283:    */       }
/* 284:    */       else
/* 285:    */       {
/* 286:296 */         Object filters = agregarFiltroOrganizacion(null);
/* 287:297 */         ((Map)filters).put("activo", "true");
/* 288:298 */         ((Map)filters).put("categoriaDocumentoDigitalizado.activo", "true");
/* 289:299 */         this.listaDocumentoDigitalizado = this.servicioDocumentoDigitalizado.obtenerListaCombo("nombre", true, (Map)filters);
/* 290:    */       }
/* 291:    */     }
/* 292:302 */     return this.listaDocumentoDigitalizado;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public CategoriaEmpresa getCategoriaEmpresaSeleccionada()
/* 296:    */   {
/* 297:306 */     return this.categoriaEmpresaSeleccionada;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void setCategoriaEmpresaSeleccionada(CategoriaEmpresa categoriaEmpresaSeleccionada)
/* 301:    */   {
/* 302:310 */     this.categoriaEmpresaSeleccionada = categoriaEmpresaSeleccionada;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public DocumentoDigitalizado getDocumentoDigitalizadoSeleccionado()
/* 306:    */   {
/* 307:314 */     return this.documentoDigitalizadoSeleccionado;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public void setDocumentoDigitalizadoSeleccionado(DocumentoDigitalizado documentoDigitalizadoSeleccionado)
/* 311:    */   {
/* 312:318 */     this.documentoDigitalizadoSeleccionado = documentoDigitalizadoSeleccionado;
/* 313:    */   }
/* 314:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     reporteDocumentosDigitalizadosClienteProveedor.ReporteDocumentosDigitalizadosClienteProveedorBean
 * JD-Core Version:    0.7.0.1
 */