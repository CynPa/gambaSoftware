/*    1:     */ package com.asinfo.as2.finaciero.presupuesto.procesos;
/*    2:     */ 
/*    3:     */ import com.asinfo.as2.controller.LanguageController;
/*    4:     */ import com.asinfo.as2.controller.PageControllerAS2;
/*    5:     */ import com.asinfo.as2.entities.CentroCosto;
/*    6:     */ import com.asinfo.as2.entities.CuentaContable;
/*    7:     */ import com.asinfo.as2.entities.CuentaContableDimensionContable;
/*    8:     */ import com.asinfo.as2.entities.DimensionContable;
/*    9:     */ import com.asinfo.as2.entities.Ejercicio;
/*   10:     */ import com.asinfo.as2.entities.Organizacion;
/*   11:     */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   12:     */ import com.asinfo.as2.entities.Sucursal;
/*   13:     */ import com.asinfo.as2.entities.presupuesto.DetallePresupuesto;
/*   14:     */ import com.asinfo.as2.entities.presupuesto.DetallePresupuestoCentroCosto;
/*   15:     */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*   16:     */ import com.asinfo.as2.entities.presupuesto.Presupuesto;
/*   17:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCentroCosto;
/*   18:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*   19:     */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDimensionContable;
/*   20:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioEjercicio;
/*   21:     */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*   22:     */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*   23:     */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioPartidaPresupuestaria;
/*   24:     */ import com.asinfo.as2.financiero.presupuesto.procesos.servicio.ServicioPresupuesto;
/*   25:     */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*   26:     */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*   27:     */ import com.asinfo.as2.util.AppUtil;
/*   28:     */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   29:     */ import java.math.BigDecimal;
/*   30:     */ import java.util.ArrayList;
/*   31:     */ import java.util.Collections;
/*   32:     */ import java.util.Comparator;
/*   33:     */ import java.util.HashMap;
/*   34:     */ import java.util.Iterator;
/*   35:     */ import java.util.List;
/*   36:     */ import java.util.Map;
/*   37:     */ import javax.annotation.PostConstruct;
/*   38:     */ import javax.ejb.EJB;
/*   39:     */ import javax.faces.bean.ManagedBean;
/*   40:     */ import javax.faces.bean.ViewScoped;
/*   41:     */ import org.apache.log4j.Logger;
/*   42:     */ import org.primefaces.component.datatable.DataTable;
/*   43:     */ import org.primefaces.event.SelectEvent;
/*   44:     */ import org.primefaces.model.LazyDataModel;
/*   45:     */ import org.primefaces.model.SortOrder;
/*   46:     */ 
/*   47:     */ @ManagedBean
/*   48:     */ @ViewScoped
/*   49:     */ public class PresupuestoBean
/*   50:     */   extends PageControllerAS2
/*   51:     */ {
/*   52:     */   private static final long serialVersionUID = 1L;
/*   53:     */   @EJB
/*   54:     */   private ServicioPresupuesto servicioPresupuesto;
/*   55:     */   @EJB
/*   56:     */   private ServicioPeriodo servicioPeriodo;
/*   57:     */   @EJB
/*   58:     */   private ServicioPartidaPresupuestaria servicioPartidaPresupuestaria;
/*   59:     */   @EJB
/*   60:     */   private ServicioCentroCosto servicioCentroCosto;
/*   61:     */   @EJB
/*   62:     */   private ServicioEjercicio servicioEjercicio;
/*   63:     */   @EJB
/*   64:     */   private ServicioDimensionContable servicioDimensionContable;
/*   65:     */   @EJB
/*   66:     */   private ServicioCuentaContable servicioCuentaContable;
/*   67:     */   private Presupuesto presupuesto;
/*   68:     */   private DetallePresupuesto detallePresupuesto;
/*   69:     */   private DimensionContable dimensionContable;
/*   70:     */   private BigDecimal totalValorCentroCosto;
/*   71:  69 */   private BigDecimal totalValorEnero = BigDecimal.ZERO;
/*   72:  70 */   private BigDecimal totalValorFebrero = BigDecimal.ZERO;
/*   73:  71 */   private BigDecimal totalValorMarzo = BigDecimal.ZERO;
/*   74:  72 */   private BigDecimal totalValorAbril = BigDecimal.ZERO;
/*   75:  73 */   private BigDecimal totalValorMayo = BigDecimal.ZERO;
/*   76:  74 */   private BigDecimal totalValorJunio = BigDecimal.ZERO;
/*   77:  75 */   private BigDecimal totalValorJulio = BigDecimal.ZERO;
/*   78:  76 */   private BigDecimal totalValorAgosto = BigDecimal.ZERO;
/*   79:  77 */   private BigDecimal totalValorSeptiembre = BigDecimal.ZERO;
/*   80:  78 */   private BigDecimal totalValorOctubre = BigDecimal.ZERO;
/*   81:  79 */   private BigDecimal totalValorNoviembre = BigDecimal.ZERO;
/*   82:  80 */   private BigDecimal totalValorDiciembre = BigDecimal.ZERO;
/*   83:     */   private BigDecimal valor;
/*   84:  82 */   private BigDecimal porcentaje = BigDecimal.ZERO;
/*   85:     */   private boolean seleccionarEnero;
/*   86:     */   private boolean seleccionarFebrero;
/*   87:     */   private boolean seleccionarMarzo;
/*   88:     */   private boolean seleccionarAbril;
/*   89:     */   private boolean seleccionarMayo;
/*   90:     */   private boolean seleccionarJunio;
/*   91:     */   private boolean seleccionarJulio;
/*   92:     */   private boolean seleccionarAgosto;
/*   93:     */   private boolean seleccionarSeptiembre;
/*   94:     */   private boolean seleccionarOctubre;
/*   95:     */   private boolean seleccionarNoviembre;
/*   96:     */   private boolean seleccionarDiciembre;
/*   97:     */   private boolean disableEnero;
/*   98:     */   private boolean disableFebrero;
/*   99:     */   private boolean disableMarzo;
/*  100:     */   private boolean disableAbril;
/*  101:     */   private boolean disableMayo;
/*  102:     */   private boolean disableJunio;
/*  103:     */   private boolean disableJulio;
/*  104:     */   private boolean disableAgosto;
/*  105:     */   private boolean disableSeptiembre;
/*  106:     */   private boolean disableOctubre;
/*  107:     */   private boolean disableNoviembre;
/*  108:     */   private boolean disableDiciembre;
/*  109:     */   private boolean seleccionarTodos;
/*  110:     */   private boolean asignarDetalle;
/*  111:     */   private int mesSeleccionado;
/*  112: 110 */   private String nombreDimension = "";
/*  113:     */   private LazyDataModel<Presupuesto> listaPresupuesto;
/*  114:     */   private List<Ejercicio> listaEjercicio;
/*  115:     */   private List<DimensionContable> listaDimensionContable;
/*  116:     */   private List<PartidaPresupuestaria> listaPartidaPresupuestaria;
/*  117:     */   private DataTable dtPresupuesto;
/*  118:     */   private DataTable dtDetallePresupuesto;
/*  119:     */   private DataTable dtDetallePresupuestoCentroCosto;
/*  120:     */   
/*  121:     */   @PostConstruct
/*  122:     */   public void init()
/*  123:     */   {
/*  124: 121 */     this.listaPresupuesto = new LazyDataModel()
/*  125:     */     {
/*  126:     */       private static final long serialVersionUID = 1L;
/*  127:     */       
/*  128:     */       public List<Presupuesto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  129:     */       {
/*  130: 125 */         List<Presupuesto> lista = new ArrayList();
/*  131: 126 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  132: 127 */         PresupuestoBean.this.agregarFiltroOrganizacion(filters);
/*  133: 128 */         lista = PresupuestoBean.this.servicioPresupuesto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  134: 129 */         PresupuestoBean.this.listaPresupuesto.setRowCount(PresupuestoBean.this.servicioPresupuesto.contarPorCriterio(filters));
/*  135: 130 */         return lista;
/*  136:     */       }
/*  137:     */     };
/*  138:     */   }
/*  139:     */   
/*  140:     */   private void crearPresupuesto()
/*  141:     */   {
/*  142: 136 */     this.presupuesto = new Presupuesto();
/*  143: 137 */     this.presupuesto.setSucursal(AppUtil.getSucursal());
/*  144: 138 */     this.presupuesto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  145: 139 */     this.presupuesto.setEjercicio(new Ejercicio());
/*  146: 140 */     this.detallePresupuesto = new DetallePresupuesto();
/*  147:     */   }
/*  148:     */   
/*  149:     */   public String editar()
/*  150:     */   {
/*  151: 144 */     if (getPresupuesto().getIdPresupuesto() > 0)
/*  152:     */     {
/*  153: 145 */       this.presupuesto = this.servicioPresupuesto.cargarDetalle(getPresupuesto().getId());
/*  154: 146 */       setEditado(true);
/*  155:     */     }
/*  156:     */     else
/*  157:     */     {
/*  158: 148 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  159:     */     }
/*  160: 150 */     return "";
/*  161:     */   }
/*  162:     */   
/*  163:     */   public String guardar()
/*  164:     */   {
/*  165:     */     try
/*  166:     */     {
/*  167: 155 */       this.servicioPresupuesto.guardar(this.presupuesto);
/*  168: 156 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  169: 157 */       setEditado(false);
/*  170: 158 */       limpiar();
/*  171:     */     }
/*  172:     */     catch (ExcepcionAS2Financiero e)
/*  173:     */     {
/*  174: 160 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  175: 161 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  176:     */     }
/*  177:     */     catch (ExcepcionAS2Inventario e)
/*  178:     */     {
/*  179: 163 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/*  180: 164 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  181:     */     }
/*  182:     */     catch (Exception e)
/*  183:     */     {
/*  184: 166 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  185: 167 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  186:     */     }
/*  187: 169 */     return "";
/*  188:     */   }
/*  189:     */   
/*  190:     */   public String eliminar()
/*  191:     */   {
/*  192:     */     try
/*  193:     */     {
/*  194: 174 */       this.servicioPresupuesto.eliminar(this.presupuesto);
/*  195: 175 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  196:     */     }
/*  197:     */     catch (Exception e)
/*  198:     */     {
/*  199: 177 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/*  200: 178 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/*  201:     */     }
/*  202: 180 */     return "";
/*  203:     */   }
/*  204:     */   
/*  205:     */   public String cargarDatos()
/*  206:     */   {
/*  207: 184 */     return "";
/*  208:     */   }
/*  209:     */   
/*  210:     */   public String limpiar()
/*  211:     */   {
/*  212: 188 */     crearPresupuesto();
/*  213: 189 */     return "";
/*  214:     */   }
/*  215:     */   
/*  216:     */   public List<CentroCosto> autocompletarCentroCosto(String consulta)
/*  217:     */   {
/*  218: 193 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/*  219: 194 */     filters.put("activo", "true");
/*  220: 195 */     filters.put("nombre", "%" + consulta.trim());
/*  221: 196 */     List<CentroCosto> lista = new ArrayList();
/*  222: 197 */     lista = this.servicioCentroCosto.obtenerListaCombo("codigo", true, filters);
/*  223: 198 */     return lista;
/*  224:     */   }
/*  225:     */   
/*  226:     */   public List<PartidaPresupuestaria> autocompletarPartidaPresupuestaria(String consulta)
/*  227:     */   {
/*  228: 202 */     consulta = consulta.toUpperCase();
/*  229: 203 */     String sortField = "codigo";
/*  230: 204 */     Map<String, String> filters = agregarFiltroOrganizacion(null);
/*  231: 205 */     filters.put("activo", "true");
/*  232: 206 */     filters.put("indicadorMovimiento", "true");
/*  233: 207 */     filters.put("nombre", "%" + consulta.trim());
/*  234: 208 */     List<PartidaPresupuestaria> lista = new ArrayList();
/*  235: 209 */     lista = this.servicioPartidaPresupuestaria.obtenerListaCombo(sortField, true, filters);
/*  236: 210 */     return lista;
/*  237:     */   }
/*  238:     */   
/*  239:     */   public void habilitarMeses(int a)
/*  240:     */   {
/*  241: 214 */     this.mesSeleccionado = a;
/*  242: 215 */     if (this.mesSeleccionado == 2)
/*  243:     */     {
/*  244: 216 */       this.disableEnero = true;
/*  245: 217 */       this.seleccionarFebrero = true;
/*  246:     */     }
/*  247: 218 */     else if (this.mesSeleccionado == 3)
/*  248:     */     {
/*  249: 219 */       this.disableEnero = true;
/*  250: 220 */       this.disableFebrero = true;
/*  251: 221 */       this.seleccionarMarzo = true;
/*  252:     */     }
/*  253: 222 */     else if (this.mesSeleccionado == 4)
/*  254:     */     {
/*  255: 223 */       this.disableEnero = true;
/*  256: 224 */       this.disableFebrero = true;
/*  257: 225 */       this.disableMarzo = true;
/*  258: 226 */       this.seleccionarAbril = true;
/*  259:     */     }
/*  260: 227 */     else if (this.mesSeleccionado == 5)
/*  261:     */     {
/*  262: 228 */       this.disableEnero = true;
/*  263: 229 */       this.disableFebrero = true;
/*  264: 230 */       this.disableMarzo = true;
/*  265: 231 */       this.disableAbril = true;
/*  266: 232 */       this.seleccionarMayo = true;
/*  267:     */     }
/*  268: 233 */     else if (this.mesSeleccionado == 6)
/*  269:     */     {
/*  270: 234 */       this.disableEnero = true;
/*  271: 235 */       this.disableFebrero = true;
/*  272: 236 */       this.disableMarzo = true;
/*  273: 237 */       this.disableAbril = true;
/*  274: 238 */       this.disableMayo = true;
/*  275: 239 */       this.seleccionarJunio = true;
/*  276:     */     }
/*  277: 240 */     else if (this.mesSeleccionado == 7)
/*  278:     */     {
/*  279: 241 */       this.disableEnero = true;
/*  280: 242 */       this.disableFebrero = true;
/*  281: 243 */       this.disableMarzo = true;
/*  282: 244 */       this.disableAbril = true;
/*  283: 245 */       this.disableMayo = true;
/*  284: 246 */       this.disableJunio = true;
/*  285: 247 */       this.seleccionarJulio = true;
/*  286:     */     }
/*  287: 248 */     else if (this.mesSeleccionado == 8)
/*  288:     */     {
/*  289: 249 */       this.disableEnero = true;
/*  290: 250 */       this.disableFebrero = true;
/*  291: 251 */       this.disableMarzo = true;
/*  292: 252 */       this.disableAbril = true;
/*  293: 253 */       this.disableMayo = true;
/*  294: 254 */       this.disableJunio = true;
/*  295: 255 */       this.disableJulio = true;
/*  296: 256 */       this.seleccionarAgosto = true;
/*  297:     */     }
/*  298: 257 */     else if (this.mesSeleccionado == 9)
/*  299:     */     {
/*  300: 258 */       this.disableEnero = true;
/*  301: 259 */       this.disableFebrero = true;
/*  302: 260 */       this.disableMarzo = true;
/*  303: 261 */       this.disableAbril = true;
/*  304: 262 */       this.disableMayo = true;
/*  305: 263 */       this.disableJunio = true;
/*  306: 264 */       this.disableJulio = true;
/*  307: 265 */       this.disableAgosto = true;
/*  308: 266 */       this.seleccionarSeptiembre = true;
/*  309:     */     }
/*  310: 267 */     else if (this.mesSeleccionado == 10)
/*  311:     */     {
/*  312: 268 */       this.disableEnero = true;
/*  313: 269 */       this.disableFebrero = true;
/*  314: 270 */       this.disableMarzo = true;
/*  315: 271 */       this.disableAbril = true;
/*  316: 272 */       this.disableMayo = true;
/*  317: 273 */       this.disableJunio = true;
/*  318: 274 */       this.disableJulio = true;
/*  319: 275 */       this.disableAgosto = true;
/*  320: 276 */       this.disableSeptiembre = true;
/*  321: 277 */       this.seleccionarOctubre = true;
/*  322:     */     }
/*  323: 278 */     else if (this.mesSeleccionado == 11)
/*  324:     */     {
/*  325: 279 */       this.disableEnero = true;
/*  326: 280 */       this.disableFebrero = true;
/*  327: 281 */       this.disableMarzo = true;
/*  328: 282 */       this.disableAbril = true;
/*  329: 283 */       this.disableMayo = true;
/*  330: 284 */       this.disableJunio = true;
/*  331: 285 */       this.disableJulio = true;
/*  332: 286 */       this.disableAgosto = true;
/*  333: 287 */       this.disableSeptiembre = true;
/*  334: 288 */       this.disableOctubre = true;
/*  335: 289 */       this.seleccionarNoviembre = true;
/*  336:     */     }
/*  337: 290 */     else if (this.mesSeleccionado == 12)
/*  338:     */     {
/*  339: 291 */       this.disableEnero = true;
/*  340: 292 */       this.disableFebrero = true;
/*  341: 293 */       this.disableMarzo = true;
/*  342: 294 */       this.disableAbril = true;
/*  343: 295 */       this.disableMayo = true;
/*  344: 296 */       this.disableJunio = true;
/*  345: 297 */       this.disableJulio = true;
/*  346: 298 */       this.disableAgosto = true;
/*  347: 299 */       this.disableSeptiembre = true;
/*  348: 300 */       this.disableOctubre = true;
/*  349: 301 */       this.disableNoviembre = true;
/*  350: 302 */       this.seleccionarDiciembre = true;
/*  351:     */     }
/*  352:     */   }
/*  353:     */   
/*  354:     */   public void selecccionarTodos()
/*  355:     */   {
/*  356: 307 */     if (this.mesSeleccionado == 1)
/*  357:     */     {
/*  358: 308 */       this.seleccionarEnero = this.seleccionarTodos;
/*  359: 309 */       this.seleccionarFebrero = this.seleccionarTodos;
/*  360: 310 */       this.seleccionarMarzo = this.seleccionarTodos;
/*  361: 311 */       this.seleccionarAbril = this.seleccionarTodos;
/*  362: 312 */       this.seleccionarMayo = this.seleccionarTodos;
/*  363: 313 */       this.seleccionarJunio = this.seleccionarTodos;
/*  364: 314 */       this.seleccionarJulio = this.seleccionarTodos;
/*  365: 315 */       this.seleccionarAgosto = this.seleccionarTodos;
/*  366: 316 */       this.seleccionarSeptiembre = this.seleccionarTodos;
/*  367: 317 */       this.seleccionarOctubre = this.seleccionarTodos;
/*  368: 318 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  369: 319 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  370:     */     }
/*  371: 320 */     else if (this.mesSeleccionado == 2)
/*  372:     */     {
/*  373: 321 */       this.seleccionarFebrero = this.seleccionarTodos;
/*  374: 322 */       this.seleccionarMarzo = this.seleccionarTodos;
/*  375: 323 */       this.seleccionarAbril = this.seleccionarTodos;
/*  376: 324 */       this.seleccionarMayo = this.seleccionarTodos;
/*  377: 325 */       this.seleccionarJunio = this.seleccionarTodos;
/*  378: 326 */       this.seleccionarJulio = this.seleccionarTodos;
/*  379: 327 */       this.seleccionarAgosto = this.seleccionarTodos;
/*  380: 328 */       this.seleccionarSeptiembre = this.seleccionarTodos;
/*  381: 329 */       this.seleccionarOctubre = this.seleccionarTodos;
/*  382: 330 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  383: 331 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  384:     */     }
/*  385: 332 */     else if (this.mesSeleccionado == 3)
/*  386:     */     {
/*  387: 333 */       this.seleccionarMarzo = this.seleccionarTodos;
/*  388: 334 */       this.seleccionarAbril = this.seleccionarTodos;
/*  389: 335 */       this.seleccionarMayo = this.seleccionarTodos;
/*  390: 336 */       this.seleccionarJunio = this.seleccionarTodos;
/*  391: 337 */       this.seleccionarJulio = this.seleccionarTodos;
/*  392: 338 */       this.seleccionarAgosto = this.seleccionarTodos;
/*  393: 339 */       this.seleccionarSeptiembre = this.seleccionarTodos;
/*  394: 340 */       this.seleccionarOctubre = this.seleccionarTodos;
/*  395: 341 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  396: 342 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  397:     */     }
/*  398: 343 */     else if (this.mesSeleccionado == 4)
/*  399:     */     {
/*  400: 344 */       this.seleccionarAbril = this.seleccionarTodos;
/*  401: 345 */       this.seleccionarMayo = this.seleccionarTodos;
/*  402: 346 */       this.seleccionarJunio = this.seleccionarTodos;
/*  403: 347 */       this.seleccionarJulio = this.seleccionarTodos;
/*  404: 348 */       this.seleccionarAgosto = this.seleccionarTodos;
/*  405: 349 */       this.seleccionarSeptiembre = this.seleccionarTodos;
/*  406: 350 */       this.seleccionarOctubre = this.seleccionarTodos;
/*  407: 351 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  408: 352 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  409:     */     }
/*  410: 353 */     else if (this.mesSeleccionado == 5)
/*  411:     */     {
/*  412: 354 */       this.seleccionarMayo = this.seleccionarTodos;
/*  413: 355 */       this.seleccionarJunio = this.seleccionarTodos;
/*  414: 356 */       this.seleccionarJulio = this.seleccionarTodos;
/*  415: 357 */       this.seleccionarAgosto = this.seleccionarTodos;
/*  416: 358 */       this.seleccionarSeptiembre = this.seleccionarTodos;
/*  417: 359 */       this.seleccionarOctubre = this.seleccionarTodos;
/*  418: 360 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  419: 361 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  420:     */     }
/*  421: 362 */     else if (this.mesSeleccionado == 6)
/*  422:     */     {
/*  423: 363 */       this.seleccionarJunio = this.seleccionarTodos;
/*  424: 364 */       this.seleccionarJulio = this.seleccionarTodos;
/*  425: 365 */       this.seleccionarAgosto = this.seleccionarTodos;
/*  426: 366 */       this.seleccionarSeptiembre = this.seleccionarTodos;
/*  427: 367 */       this.seleccionarOctubre = this.seleccionarTodos;
/*  428: 368 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  429: 369 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  430:     */     }
/*  431: 370 */     else if (this.mesSeleccionado == 7)
/*  432:     */     {
/*  433: 371 */       this.seleccionarJulio = this.seleccionarTodos;
/*  434: 372 */       this.seleccionarAgosto = this.seleccionarTodos;
/*  435: 373 */       this.seleccionarSeptiembre = this.seleccionarTodos;
/*  436: 374 */       this.seleccionarOctubre = this.seleccionarTodos;
/*  437: 375 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  438: 376 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  439:     */     }
/*  440: 377 */     else if (this.mesSeleccionado == 8)
/*  441:     */     {
/*  442: 378 */       this.seleccionarAgosto = this.seleccionarTodos;
/*  443: 379 */       this.seleccionarSeptiembre = this.seleccionarTodos;
/*  444: 380 */       this.seleccionarOctubre = this.seleccionarTodos;
/*  445: 381 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  446: 382 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  447:     */     }
/*  448: 383 */     else if (this.mesSeleccionado == 9)
/*  449:     */     {
/*  450: 384 */       this.seleccionarSeptiembre = this.seleccionarTodos;
/*  451: 385 */       this.seleccionarOctubre = this.seleccionarTodos;
/*  452: 386 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  453: 387 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  454:     */     }
/*  455: 388 */     else if (this.mesSeleccionado == 10)
/*  456:     */     {
/*  457: 389 */       this.seleccionarOctubre = this.seleccionarTodos;
/*  458: 390 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  459: 391 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  460:     */     }
/*  461: 392 */     else if (this.mesSeleccionado == 11)
/*  462:     */     {
/*  463: 393 */       this.seleccionarNoviembre = this.seleccionarTodos;
/*  464: 394 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  465:     */     }
/*  466: 395 */     else if (this.mesSeleccionado == 12)
/*  467:     */     {
/*  468: 396 */       this.seleccionarDiciembre = this.seleccionarTodos;
/*  469:     */     }
/*  470:     */   }
/*  471:     */   
/*  472:     */   public void limpiarDatosDialog()
/*  473:     */   {
/*  474: 401 */     this.mesSeleccionado = 1;
/*  475: 402 */     this.asignarDetalle = false;
/*  476: 403 */     this.disableEnero = false;
/*  477: 404 */     this.disableFebrero = false;
/*  478: 405 */     this.disableMarzo = false;
/*  479: 406 */     this.disableAbril = false;
/*  480: 407 */     this.disableMayo = false;
/*  481: 408 */     this.disableJunio = false;
/*  482: 409 */     this.disableJulio = false;
/*  483: 410 */     this.disableAgosto = false;
/*  484: 411 */     this.disableSeptiembre = false;
/*  485: 412 */     this.disableOctubre = false;
/*  486: 413 */     this.disableNoviembre = false;
/*  487: 414 */     this.disableDiciembre = false;
/*  488: 415 */     this.seleccionarEnero = false;
/*  489: 416 */     this.seleccionarFebrero = false;
/*  490: 417 */     this.seleccionarMarzo = false;
/*  491: 418 */     this.seleccionarAbril = false;
/*  492: 419 */     this.seleccionarMayo = false;
/*  493: 420 */     this.seleccionarJunio = false;
/*  494: 421 */     this.seleccionarJulio = false;
/*  495: 422 */     this.seleccionarAgosto = false;
/*  496: 423 */     this.seleccionarSeptiembre = false;
/*  497: 424 */     this.seleccionarOctubre = false;
/*  498: 425 */     this.seleccionarNoviembre = false;
/*  499: 426 */     this.seleccionarDiciembre = false;
/*  500: 427 */     this.seleccionarTodos = false;
/*  501: 428 */     this.valor = null;
/*  502: 429 */     this.porcentaje = BigDecimal.ZERO;
/*  503:     */   }
/*  504:     */   
/*  505:     */   public void asignarValores()
/*  506:     */   {
/*  507: 433 */     this.porcentaje = this.porcentaje.divide(new BigDecimal(100));
/*  508: 434 */     this.valor = FuncionesUtiles.redondearBigDecimal(this.valor, 2);
/*  509: 435 */     if (this.asignarDetalle) {
/*  510: 436 */       calcular(this.detallePresupuesto);
/*  511:     */     } else {
/*  512: 438 */       for (DetallePresupuesto dp : getListaDetallePresupuesto()) {
/*  513: 439 */         calcular(dp);
/*  514:     */       }
/*  515:     */     }
/*  516: 442 */     limpiarDatosDialog();
/*  517:     */   }
/*  518:     */   
/*  519:     */   private void calcular(DetallePresupuesto detPresupuesto)
/*  520:     */   {
/*  521: 446 */     if (this.porcentaje.compareTo(BigDecimal.ZERO) == 0)
/*  522:     */     {
/*  523: 447 */       if (this.seleccionarEnero) {
/*  524: 448 */         detPresupuesto.setValorEnero(this.valor);
/*  525:     */       }
/*  526: 450 */       if (this.seleccionarFebrero) {
/*  527: 451 */         detPresupuesto.setValorFebrero(this.valor);
/*  528:     */       }
/*  529: 453 */       if (this.seleccionarMarzo) {
/*  530: 454 */         detPresupuesto.setValorMarzo(this.valor);
/*  531:     */       }
/*  532: 456 */       if (this.seleccionarAbril) {
/*  533: 457 */         detPresupuesto.setValorAbril(this.valor);
/*  534:     */       }
/*  535: 459 */       if (this.seleccionarMayo) {
/*  536: 460 */         detPresupuesto.setValorMayo(this.valor);
/*  537:     */       }
/*  538: 462 */       if (this.seleccionarJunio) {
/*  539: 463 */         detPresupuesto.setValorJunio(this.valor);
/*  540:     */       }
/*  541: 465 */       if (this.seleccionarJulio) {
/*  542: 466 */         detPresupuesto.setValorJulio(this.valor);
/*  543:     */       }
/*  544: 468 */       if (this.seleccionarAgosto) {
/*  545: 469 */         detPresupuesto.setValorAgosto(this.valor);
/*  546:     */       }
/*  547: 471 */       if (this.seleccionarSeptiembre) {
/*  548: 472 */         detPresupuesto.setValorSeptiembre(this.valor);
/*  549:     */       }
/*  550: 474 */       if (this.seleccionarOctubre) {
/*  551: 475 */         detPresupuesto.setValorOctubre(this.valor);
/*  552:     */       }
/*  553: 477 */       if (this.seleccionarNoviembre) {
/*  554: 478 */         detPresupuesto.setValorNoviembre(this.valor);
/*  555:     */       }
/*  556: 480 */       if (this.seleccionarDiciembre) {
/*  557: 481 */         detPresupuesto.setValorDiciembre(this.valor);
/*  558:     */       }
/*  559:     */     }
/*  560:     */     else
/*  561:     */     {
/*  562: 484 */       BigDecimal valorAux = FuncionesUtiles.redondearBigDecimal(this.valor.add(this.valor.multiply(this.porcentaje)), 2);
/*  563: 485 */       if (this.seleccionarEnero) {
/*  564: 486 */         detPresupuesto.setValorEnero(this.valor);
/*  565:     */       }
/*  566: 488 */       if (this.seleccionarFebrero) {
/*  567: 489 */         if (this.mesSeleccionado == 2) {
/*  568: 490 */           detPresupuesto.setValorFebrero(this.valor);
/*  569:     */         } else {
/*  570: 492 */           detPresupuesto.setValorFebrero(valorAux);
/*  571:     */         }
/*  572:     */       }
/*  573: 495 */       if (this.seleccionarMarzo) {
/*  574: 496 */         if (this.mesSeleccionado == 3) {
/*  575: 497 */           detPresupuesto.setValorMarzo(this.valor);
/*  576:     */         } else {
/*  577: 499 */           detPresupuesto.setValorMarzo(valorAux);
/*  578:     */         }
/*  579:     */       }
/*  580: 502 */       if (this.seleccionarAbril) {
/*  581: 503 */         if (this.mesSeleccionado == 4) {
/*  582: 504 */           detPresupuesto.setValorAbril(this.valor);
/*  583:     */         } else {
/*  584: 506 */           detPresupuesto.setValorAbril(valorAux);
/*  585:     */         }
/*  586:     */       }
/*  587: 509 */       if (this.seleccionarMayo) {
/*  588: 510 */         if (this.mesSeleccionado == 5) {
/*  589: 511 */           detPresupuesto.setValorMayo(this.valor);
/*  590:     */         } else {
/*  591: 513 */           detPresupuesto.setValorMayo(valorAux);
/*  592:     */         }
/*  593:     */       }
/*  594: 516 */       if (this.seleccionarJunio) {
/*  595: 517 */         if (this.mesSeleccionado == 6) {
/*  596: 518 */           detPresupuesto.setValorJunio(this.valor);
/*  597:     */         } else {
/*  598: 520 */           detPresupuesto.setValorJunio(valorAux);
/*  599:     */         }
/*  600:     */       }
/*  601: 523 */       if (this.seleccionarJulio) {
/*  602: 524 */         if (this.mesSeleccionado == 7) {
/*  603: 525 */           detPresupuesto.setValorJulio(this.valor);
/*  604:     */         } else {
/*  605: 527 */           detPresupuesto.setValorJulio(valorAux);
/*  606:     */         }
/*  607:     */       }
/*  608: 530 */       if (this.seleccionarAgosto) {
/*  609: 531 */         if (this.mesSeleccionado == 8) {
/*  610: 532 */           detPresupuesto.setValorAgosto(this.valor);
/*  611:     */         } else {
/*  612: 534 */           detPresupuesto.setValorAgosto(valorAux);
/*  613:     */         }
/*  614:     */       }
/*  615: 537 */       if (this.seleccionarSeptiembre) {
/*  616: 538 */         if (this.mesSeleccionado == 9) {
/*  617: 539 */           detPresupuesto.setValorSeptiembre(this.valor);
/*  618:     */         } else {
/*  619: 541 */           detPresupuesto.setValorSeptiembre(valorAux);
/*  620:     */         }
/*  621:     */       }
/*  622: 544 */       if (this.seleccionarOctubre) {
/*  623: 545 */         if (this.mesSeleccionado == 10) {
/*  624: 546 */           detPresupuesto.setValorOctubre(this.valor);
/*  625:     */         } else {
/*  626: 548 */           detPresupuesto.setValorOctubre(valorAux);
/*  627:     */         }
/*  628:     */       }
/*  629: 551 */       if (this.seleccionarNoviembre) {
/*  630: 552 */         if (this.mesSeleccionado == 11) {
/*  631: 553 */           detPresupuesto.setValorNoviembre(this.valor);
/*  632:     */         } else {
/*  633: 555 */           detPresupuesto.setValorNoviembre(valorAux);
/*  634:     */         }
/*  635:     */       }
/*  636: 558 */       if (this.seleccionarDiciembre) {
/*  637: 559 */         if (this.mesSeleccionado == 12) {
/*  638: 560 */           detPresupuesto.setValorDiciembre(this.valor);
/*  639:     */         } else {
/*  640: 562 */           detPresupuesto.setValorDiciembre(valorAux);
/*  641:     */         }
/*  642:     */       }
/*  643:     */     }
/*  644:     */   }
/*  645:     */   
/*  646:     */   public String agregarDetalle()
/*  647:     */   {
/*  648: 569 */     DetallePresupuesto detallePresupuesto = new DetallePresupuesto();
/*  649: 570 */     detallePresupuesto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  650: 571 */     detallePresupuesto.setIdSucursal(AppUtil.getSucursal().getId());
/*  651: 572 */     detallePresupuesto.setPresupuesto(getPresupuesto());
/*  652: 573 */     getPresupuesto().getListaDetallePresupuesto().add(detallePresupuesto);
/*  653: 574 */     return "";
/*  654:     */   }
/*  655:     */   
/*  656:     */   public String eliminarDetalle()
/*  657:     */   {
/*  658: 578 */     DetallePresupuesto detallePresupuesto = (DetallePresupuesto)this.dtDetallePresupuesto.getRowData();
/*  659: 579 */     for (DetallePresupuestoCentroCosto detallePresupuestoCentroCosto : getDetallePresupuesto().getListaDetallePresupuestoCentroCosto()) {
/*  660: 580 */       detallePresupuestoCentroCosto.setEliminado(true);
/*  661:     */     }
/*  662: 582 */     detallePresupuesto.setEliminado(true);
/*  663: 583 */     return "";
/*  664:     */   }
/*  665:     */   
/*  666:     */   private void crearDetallePresupuesto(CuentaContable cc, DimensionContable dc)
/*  667:     */   {
/*  668: 587 */     if (dc != null)
/*  669:     */     {
/*  670: 588 */       DetallePresupuesto dp = new DetallePresupuesto();
/*  671: 589 */       dp.setCuentaContable(cc);
/*  672: 590 */       dp.setDimensionContable(dc);
/*  673: 591 */       dp.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  674: 592 */       dp.setIdSucursal(AppUtil.getSucursal().getId());
/*  675: 593 */       dp.setPresupuesto(getPresupuesto());
/*  676: 594 */       getPresupuesto().getListaDetallePresupuesto().add(dp);
/*  677:     */     }
/*  678:     */   }
/*  679:     */   
/*  680:     */   public void actualizarDetallePresupuesto()
/*  681:     */   {
/*  682:     */     Map<String, CuentaContable> hashCuentasContables;
/*  683:     */     Map<Integer, DimensionContable> hashDimensionesContables;
/*  684: 599 */     if (getDimensionContable() != null)
/*  685:     */     {
/*  686: 601 */       hashCuentasContables = new HashMap();
/*  687: 602 */       hashDimensionesContables = new HashMap();
/*  688: 603 */       List<DimensionContable> listDimensionContable = new ArrayList();
/*  689: 604 */       for (DimensionContable dimensionContable : getListaDimensionContable()) {
/*  690: 605 */         if ((dimensionContable.getCodigo().startsWith(getDimensionContable().getCodigo())) && (dimensionContable.isIndicadorMovimiento()))
/*  691:     */         {
/*  692: 606 */           listDimensionContable.add(dimensionContable);
/*  693: 607 */           hashDimensionesContables.put(Integer.valueOf(dimensionContable.getId()), dimensionContable);
/*  694:     */         }
/*  695:     */       }
/*  696: 611 */       for (??? = listDimensionContable.iterator(); ???.hasNext();)
/*  697:     */       {
/*  698: 611 */         dc = (DimensionContable)???.next();
/*  699: 612 */         for (CuentaContableDimensionContable ccdc : this.servicioDimensionContable.cargarDetalle(dc.getId())
/*  700: 613 */           .getListaCuentaContableDimensionContable())
/*  701:     */         {
/*  702: 614 */           CuentaContable cc = this.servicioCuentaContable.cargarDetalle(ccdc.getCuentaContable().getId());
/*  703: 615 */           if (cc.getPartidaPresupuestaria() != null) {
/*  704: 616 */             hashCuentasContables.put(cc.getId() + "~" + dc.getId(), cc);
/*  705:     */           }
/*  706:     */         }
/*  707:     */       }
/*  708:     */       DimensionContable dc;
/*  709: 620 */       for (DetallePresupuesto dp : getPresupuesto().getListaDetallePresupuesto()) {
/*  710: 621 */         if (hashCuentasContables.get(dp.getCuentaContable().getId() + "~" + dp.getDimensionContable().getId()) != null) {
/*  711: 622 */           hashCuentasContables.remove(dp.getCuentaContable().getId() + "~" + dp.getDimensionContable().getId());
/*  712:     */         }
/*  713:     */       }
/*  714: 625 */       for (String key : hashCuentasContables.keySet())
/*  715:     */       {
/*  716: 626 */         String[] ccDc = key.split("~");
/*  717: 627 */         if ((hashCuentasContables.get(key) != null) && (hashDimensionesContables.get(Integer.valueOf(Integer.parseInt(ccDc[1]))) != null)) {
/*  718: 628 */           crearDetallePresupuesto((CuentaContable)hashCuentasContables.get(key), (DimensionContable)hashDimensionesContables.get(Integer.valueOf(Integer.parseInt(ccDc[1]))));
/*  719:     */         }
/*  720:     */       }
/*  721:     */     }
/*  722:     */   }
/*  723:     */   
/*  724:     */   public void resetTotales()
/*  725:     */   {
/*  726: 635 */     this.totalValorEnero = BigDecimal.ZERO;
/*  727: 636 */     this.totalValorFebrero = BigDecimal.ZERO;
/*  728: 637 */     this.totalValorMarzo = BigDecimal.ZERO;
/*  729: 638 */     this.totalValorAbril = BigDecimal.ZERO;
/*  730: 639 */     this.totalValorMayo = BigDecimal.ZERO;
/*  731: 640 */     this.totalValorJunio = BigDecimal.ZERO;
/*  732: 641 */     this.totalValorJulio = BigDecimal.ZERO;
/*  733: 642 */     this.totalValorAgosto = BigDecimal.ZERO;
/*  734: 643 */     this.totalValorSeptiembre = BigDecimal.ZERO;
/*  735: 644 */     this.totalValorOctubre = BigDecimal.ZERO;
/*  736: 645 */     this.totalValorNoviembre = BigDecimal.ZERO;
/*  737: 646 */     this.totalValorDiciembre = BigDecimal.ZERO;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public List<DetallePresupuesto> getListaDetallePresupuesto()
/*  741:     */   {
/*  742: 651 */     resetTotales();
/*  743: 652 */     List<DetallePresupuesto> lista = new ArrayList();
/*  744: 653 */     if (getDimensionContable() != null)
/*  745:     */     {
/*  746: 654 */       Map<Integer, DetallePresupuesto> hashDetalles = new HashMap();
/*  747:     */       
/*  748: 656 */       List<DimensionContable> listDimensionContable = new ArrayList();
/*  749: 657 */       for (DimensionContable dimensionContable : getListaDimensionContable()) {
/*  750: 658 */         if ((dimensionContable.getCodigo().startsWith(getDimensionContable().getCodigo())) && (dimensionContable.isIndicadorMovimiento())) {
/*  751: 659 */           listDimensionContable.add(dimensionContable);
/*  752:     */         }
/*  753:     */       }
/*  754: 662 */       for (??? = getPresupuesto().getListaDetallePresupuesto().iterator(); ???.hasNext();)
/*  755:     */       {
/*  756: 662 */         dp = (DetallePresupuesto)???.next();
/*  757: 667 */         for (DimensionContable dc : listDimensionContable) {
/*  758: 668 */           if ((!dp.isEliminado()) && (dp.getDimensionContable().equals(dc)))
/*  759:     */           {
/*  760: 669 */             if (dp.getValorEnero() != null) {
/*  761: 670 */               setTotalValorEnero(getTotalValorEnero().add(dp.getValorEnero()));
/*  762:     */             }
/*  763: 671 */             if (dp.getValorFebrero() != null) {
/*  764: 672 */               setTotalValorFebrero(getTotalValorFebrero().add(dp.getValorFebrero()));
/*  765:     */             }
/*  766: 673 */             if (dp.getValorMarzo() != null) {
/*  767: 674 */               setTotalValorMarzo(getTotalValorMarzo().add(dp.getValorMarzo()));
/*  768:     */             }
/*  769: 675 */             if (dp.getValorAbril() != null) {
/*  770: 676 */               setTotalValorAbril(getTotalValorAbril().add(dp.getValorAbril()));
/*  771:     */             }
/*  772: 677 */             if (dp.getValorMayo() != null) {
/*  773: 678 */               setTotalValorMayo(getTotalValorMayo().add(dp.getValorMayo()));
/*  774:     */             }
/*  775: 679 */             if (dp.getValorJunio() != null) {
/*  776: 680 */               setTotalValorJunio(getTotalValorJunio().add(dp.getValorJunio()));
/*  777:     */             }
/*  778: 681 */             if (dp.getValorJulio() != null) {
/*  779: 682 */               setTotalValorJulio(getTotalValorJulio().add(dp.getValorJulio()));
/*  780:     */             }
/*  781: 683 */             if (dp.getValorAgosto() != null) {
/*  782: 684 */               setTotalValorAgosto(getTotalValorAgosto().add(dp.getValorAgosto()));
/*  783:     */             }
/*  784: 685 */             if (dp.getValorSeptiembre() != null) {
/*  785: 686 */               setTotalValorSeptiembre(getTotalValorSeptiembre().add(dp.getValorSeptiembre()));
/*  786:     */             }
/*  787: 687 */             if (dp.getValorOctubre() != null) {
/*  788: 688 */               setTotalValorOctubre(getTotalValorOctubre().add(dp.getValorOctubre()));
/*  789:     */             }
/*  790: 689 */             if (dp.getValorNoviembre() != null) {
/*  791: 690 */               setTotalValorNoviembre(getTotalValorNoviembre().add(dp.getValorNoviembre()));
/*  792:     */             }
/*  793: 691 */             if (dp.getValorDiciembre() != null) {
/*  794: 692 */               setTotalValorDiciembre(getTotalValorDiciembre().add(dp.getValorDiciembre()));
/*  795:     */             }
/*  796: 693 */             hashDetalles.put(Integer.valueOf(dp.getCuentaContable().getRowKey()), dp);
/*  797:     */           }
/*  798:     */         }
/*  799:     */       }
/*  800:     */       DetallePresupuesto dp;
/*  801: 697 */       lista.addAll(hashDetalles.values());
/*  802:     */     }
/*  803: 699 */     Collections.sort(lista, new Comparator()
/*  804:     */     {
/*  805:     */       public int compare(Object o1, Object o2)
/*  806:     */       {
/*  807: 704 */         return (((DetallePresupuesto)o1).getDimensionContable().getCodigo() + "_" + ((DetallePresupuesto)o1).getCuentaContable().getCodigo()).compareTo(((DetallePresupuesto)o2).getDimensionContable().getCodigo() + "_" + ((DetallePresupuesto)o2)
/*  808: 705 */           .getCuentaContable().getCodigo());
/*  809:     */       }
/*  810: 708 */     });
/*  811: 709 */     return lista;
/*  812:     */   }
/*  813:     */   
/*  814:     */   public String agregarDetallePresupuestoCentroCosto()
/*  815:     */   {
/*  816: 713 */     if (getDetallePresupuesto().getPartidaPresupuestaria() == null)
/*  817:     */     {
/*  818: 714 */       addInfoMessage(getLanguageController().getMensaje("Mensajes.MENSAJE_INFO_SELECCIONAR"));
/*  819:     */     }
/*  820:     */     else
/*  821:     */     {
/*  822: 716 */       DetallePresupuestoCentroCosto detallePresupuestoCentroCosto = new DetallePresupuestoCentroCosto();
/*  823: 717 */       detallePresupuestoCentroCosto.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  824: 718 */       detallePresupuestoCentroCosto.setIdSucursal(AppUtil.getSucursal().getId());
/*  825: 719 */       detallePresupuestoCentroCosto.setDetallePresupuesto(getDetallePresupuesto());
/*  826: 720 */       getDetallePresupuesto().getListaDetallePresupuestoCentroCosto().add(detallePresupuestoCentroCosto);
/*  827:     */     }
/*  828: 722 */     return "";
/*  829:     */   }
/*  830:     */   
/*  831:     */   public String eliminarDetallePresupuestoCentroCosto()
/*  832:     */   {
/*  833: 726 */     DetallePresupuestoCentroCosto detallePresupuestoCentroCosto = (DetallePresupuestoCentroCosto)this.dtDetallePresupuestoCentroCosto.getRowData();
/*  834: 727 */     detallePresupuestoCentroCosto.setEliminado(true);
/*  835: 728 */     actualizarValor();
/*  836: 729 */     return "";
/*  837:     */   }
/*  838:     */   
/*  839:     */   public List<DetallePresupuestoCentroCosto> getListaDetallePresupuestoCentroCosto()
/*  840:     */   {
/*  841: 733 */     List<DetallePresupuestoCentroCosto> lista = new ArrayList();
/*  842: 734 */     for (DetallePresupuestoCentroCosto detallePresupuestoCentroCosto : getDetallePresupuesto().getListaDetallePresupuestoCentroCosto()) {
/*  843: 735 */       if (!detallePresupuestoCentroCosto.isEliminado()) {
/*  844: 736 */         lista.add(detallePresupuestoCentroCosto);
/*  845:     */       }
/*  846:     */     }
/*  847: 739 */     return lista;
/*  848:     */   }
/*  849:     */   
/*  850:     */   public void onRowSelect(SelectEvent event)
/*  851:     */   {
/*  852: 743 */     this.detallePresupuesto = ((DetallePresupuesto)event.getObject());
/*  853: 744 */     actualizarValor();
/*  854:     */   }
/*  855:     */   
/*  856:     */   public void actualizarValorListener()
/*  857:     */   {
/*  858: 748 */     DetallePresupuesto detallePresupuesto = (DetallePresupuesto)this.dtDetallePresupuesto.getRowData();
/*  859: 749 */     if (detallePresupuesto.getPartidaPresupuestaria() != null) {}
/*  860:     */   }
/*  861:     */   
/*  862:     */   public void actualizarValor()
/*  863:     */   {
/*  864: 755 */     if ((this.detallePresupuesto.getPartidaPresupuestaria() != null) && (this.detallePresupuesto.isIndicadorValidarDistribucion())) {}
/*  865:     */   }
/*  866:     */   
/*  867:     */   public Presupuesto getPresupuesto()
/*  868:     */   {
/*  869: 761 */     if (this.presupuesto == null) {
/*  870: 762 */       crearPresupuesto();
/*  871:     */     }
/*  872: 764 */     return this.presupuesto;
/*  873:     */   }
/*  874:     */   
/*  875:     */   public void setPresupuesto(Presupuesto presupuesto)
/*  876:     */   {
/*  877: 768 */     this.presupuesto = presupuesto;
/*  878:     */   }
/*  879:     */   
/*  880:     */   public DetallePresupuesto getDetallePresupuesto()
/*  881:     */   {
/*  882: 772 */     if (this.detallePresupuesto == null) {
/*  883: 773 */       this.detallePresupuesto = new DetallePresupuesto();
/*  884:     */     }
/*  885: 775 */     return this.detallePresupuesto;
/*  886:     */   }
/*  887:     */   
/*  888:     */   public void setDetallePresupuesto(DetallePresupuesto detallePresupuesto)
/*  889:     */   {
/*  890: 779 */     this.detallePresupuesto = detallePresupuesto;
/*  891:     */   }
/*  892:     */   
/*  893:     */   public LazyDataModel<Presupuesto> getListaPresupuesto()
/*  894:     */   {
/*  895: 783 */     return this.listaPresupuesto;
/*  896:     */   }
/*  897:     */   
/*  898:     */   public void setListaPresupuesto(LazyDataModel<Presupuesto> listaPresupuesto)
/*  899:     */   {
/*  900: 787 */     this.listaPresupuesto = listaPresupuesto;
/*  901:     */   }
/*  902:     */   
/*  903:     */   public List<DimensionContable> getListaDimensionContable()
/*  904:     */   {
/*  905: 791 */     if (this.listaDimensionContable == null) {
/*  906: 792 */       this.listaDimensionContable = this.servicioDimensionContable.obtenerDimensionContablePorUsuario(AppUtil.getOrganizacion().getId(), 
/*  907: 793 */         AppUtil.getUsuarioEnSesion().getIdUsuario(), 0, true);
/*  908:     */     }
/*  909: 795 */     return this.listaDimensionContable;
/*  910:     */   }
/*  911:     */   
/*  912:     */   public void setListaDimensionContable(List<DimensionContable> listaDimensionContable)
/*  913:     */   {
/*  914: 799 */     this.listaDimensionContable = listaDimensionContable;
/*  915:     */   }
/*  916:     */   
/*  917:     */   public List<PartidaPresupuestaria> getListaPartidaPresupuestaria()
/*  918:     */   {
/*  919: 803 */     if (this.listaPartidaPresupuestaria == null) {
/*  920: 804 */       this.listaPartidaPresupuestaria = this.servicioPartidaPresupuestaria.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/*  921:     */     }
/*  922: 806 */     return this.listaPartidaPresupuestaria;
/*  923:     */   }
/*  924:     */   
/*  925:     */   public void setListaPartidaPresupuestaria(List<PartidaPresupuestaria> listaPartidaPresupuestaria)
/*  926:     */   {
/*  927: 810 */     this.listaPartidaPresupuestaria = listaPartidaPresupuestaria;
/*  928:     */   }
/*  929:     */   
/*  930:     */   public List<Ejercicio> getListaEjercicio()
/*  931:     */   {
/*  932: 814 */     if (this.listaEjercicio == null) {
/*  933: 815 */       this.listaEjercicio = this.servicioEjercicio.obtenerListaCombo("nombre", true, agregarFiltroOrganizacion(null));
/*  934:     */     }
/*  935: 817 */     return this.listaEjercicio;
/*  936:     */   }
/*  937:     */   
/*  938:     */   public void setListaEjercicio(List<Ejercicio> listaEjercicio)
/*  939:     */   {
/*  940: 821 */     this.listaEjercicio = listaEjercicio;
/*  941:     */   }
/*  942:     */   
/*  943:     */   public BigDecimal getTotalValorCentroCosto()
/*  944:     */   {
/*  945: 825 */     this.totalValorCentroCosto = BigDecimal.ZERO;
/*  946: 826 */     for (DetallePresupuestoCentroCosto detallePresupuestoCentroCosto : getDetallePresupuesto().getListaDetallePresupuestoCentroCosto()) {
/*  947: 827 */       if (!detallePresupuestoCentroCosto.isEliminado()) {
/*  948: 828 */         this.totalValorCentroCosto = this.totalValorCentroCosto.add(detallePresupuestoCentroCosto.getValor());
/*  949:     */       }
/*  950:     */     }
/*  951: 831 */     return this.totalValorCentroCosto;
/*  952:     */   }
/*  953:     */   
/*  954:     */   public void setTotalValorCentroCosto(BigDecimal totalValorCentroCosto)
/*  955:     */   {
/*  956: 835 */     this.totalValorCentroCosto = totalValorCentroCosto;
/*  957:     */   }
/*  958:     */   
/*  959:     */   public DataTable getDtPresupuesto()
/*  960:     */   {
/*  961: 839 */     return this.dtPresupuesto;
/*  962:     */   }
/*  963:     */   
/*  964:     */   public void setDtPresupuesto(DataTable dtPresupuesto)
/*  965:     */   {
/*  966: 843 */     this.dtPresupuesto = dtPresupuesto;
/*  967:     */   }
/*  968:     */   
/*  969:     */   public DataTable getDtDetallePresupuesto()
/*  970:     */   {
/*  971: 847 */     return this.dtDetallePresupuesto;
/*  972:     */   }
/*  973:     */   
/*  974:     */   public void setDtDetallePresupuesto(DataTable dtDetallePresupuesto)
/*  975:     */   {
/*  976: 851 */     this.dtDetallePresupuesto = dtDetallePresupuesto;
/*  977:     */   }
/*  978:     */   
/*  979:     */   public DataTable getDtDetallePresupuestoCentroCosto()
/*  980:     */   {
/*  981: 855 */     return this.dtDetallePresupuestoCentroCosto;
/*  982:     */   }
/*  983:     */   
/*  984:     */   public void setDtDetallePresupuestoCentroCosto(DataTable dtDetallePresupuestoCentroCosto)
/*  985:     */   {
/*  986: 859 */     this.dtDetallePresupuestoCentroCosto = dtDetallePresupuestoCentroCosto;
/*  987:     */   }
/*  988:     */   
/*  989:     */   public DimensionContable getDimensionContable()
/*  990:     */   {
/*  991: 863 */     return this.dimensionContable;
/*  992:     */   }
/*  993:     */   
/*  994:     */   public void setDimensionContable(DimensionContable dimensionContable)
/*  995:     */   {
/*  996: 867 */     this.dimensionContable = dimensionContable;
/*  997:     */   }
/*  998:     */   
/*  999:     */   public BigDecimal getValor()
/* 1000:     */   {
/* 1001: 871 */     return this.valor;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public void setValor(BigDecimal valor)
/* 1005:     */   {
/* 1006: 875 */     this.valor = valor;
/* 1007:     */   }
/* 1008:     */   
/* 1009:     */   public BigDecimal getPorcentaje()
/* 1010:     */   {
/* 1011: 879 */     return this.porcentaje;
/* 1012:     */   }
/* 1013:     */   
/* 1014:     */   public void setPorcentaje(BigDecimal porcentaje)
/* 1015:     */   {
/* 1016: 883 */     this.porcentaje = porcentaje;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public boolean isSeleccionarEnero()
/* 1020:     */   {
/* 1021: 887 */     return this.seleccionarEnero;
/* 1022:     */   }
/* 1023:     */   
/* 1024:     */   public void setSeleccionarEnero(boolean seleccionarEnero)
/* 1025:     */   {
/* 1026: 891 */     this.seleccionarEnero = seleccionarEnero;
/* 1027:     */   }
/* 1028:     */   
/* 1029:     */   public boolean isSeleccionarFebrero()
/* 1030:     */   {
/* 1031: 895 */     return this.seleccionarFebrero;
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   public void setSeleccionarFebrero(boolean seleccionarFebrero)
/* 1035:     */   {
/* 1036: 899 */     this.seleccionarFebrero = seleccionarFebrero;
/* 1037:     */   }
/* 1038:     */   
/* 1039:     */   public boolean isSeleccionarMarzo()
/* 1040:     */   {
/* 1041: 903 */     return this.seleccionarMarzo;
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   public void setSeleccionarMarzo(boolean seleccionarMarzo)
/* 1045:     */   {
/* 1046: 907 */     this.seleccionarMarzo = seleccionarMarzo;
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   public boolean isSeleccionarAbril()
/* 1050:     */   {
/* 1051: 911 */     return this.seleccionarAbril;
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   public void setSeleccionarAbril(boolean seleccionarAbril)
/* 1055:     */   {
/* 1056: 915 */     this.seleccionarAbril = seleccionarAbril;
/* 1057:     */   }
/* 1058:     */   
/* 1059:     */   public boolean isSeleccionarMayo()
/* 1060:     */   {
/* 1061: 919 */     return this.seleccionarMayo;
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   public void setSeleccionarMayo(boolean seleccionarMayo)
/* 1065:     */   {
/* 1066: 923 */     this.seleccionarMayo = seleccionarMayo;
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   public boolean isSeleccionarJunio()
/* 1070:     */   {
/* 1071: 927 */     return this.seleccionarJunio;
/* 1072:     */   }
/* 1073:     */   
/* 1074:     */   public void setSeleccionarJunio(boolean seleccionarJunio)
/* 1075:     */   {
/* 1076: 931 */     this.seleccionarJunio = seleccionarJunio;
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   public boolean isSeleccionarJulio()
/* 1080:     */   {
/* 1081: 935 */     return this.seleccionarJulio;
/* 1082:     */   }
/* 1083:     */   
/* 1084:     */   public void setSeleccionarJulio(boolean seleccionarJulio)
/* 1085:     */   {
/* 1086: 939 */     this.seleccionarJulio = seleccionarJulio;
/* 1087:     */   }
/* 1088:     */   
/* 1089:     */   public boolean isSeleccionarAgosto()
/* 1090:     */   {
/* 1091: 943 */     return this.seleccionarAgosto;
/* 1092:     */   }
/* 1093:     */   
/* 1094:     */   public void setSeleccionarAgosto(boolean seleccionarAgosto)
/* 1095:     */   {
/* 1096: 947 */     this.seleccionarAgosto = seleccionarAgosto;
/* 1097:     */   }
/* 1098:     */   
/* 1099:     */   public boolean isSeleccionarSeptiembre()
/* 1100:     */   {
/* 1101: 951 */     return this.seleccionarSeptiembre;
/* 1102:     */   }
/* 1103:     */   
/* 1104:     */   public void setSeleccionarSeptiembre(boolean seleccionarSeptiembre)
/* 1105:     */   {
/* 1106: 955 */     this.seleccionarSeptiembre = seleccionarSeptiembre;
/* 1107:     */   }
/* 1108:     */   
/* 1109:     */   public boolean isSeleccionarOctubre()
/* 1110:     */   {
/* 1111: 959 */     return this.seleccionarOctubre;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public void setSeleccionarOctubre(boolean seleccionarOctubre)
/* 1115:     */   {
/* 1116: 963 */     this.seleccionarOctubre = seleccionarOctubre;
/* 1117:     */   }
/* 1118:     */   
/* 1119:     */   public boolean isSeleccionarNoviembre()
/* 1120:     */   {
/* 1121: 967 */     return this.seleccionarNoviembre;
/* 1122:     */   }
/* 1123:     */   
/* 1124:     */   public void setSeleccionarNoviembre(boolean seleccionarNoviembre)
/* 1125:     */   {
/* 1126: 971 */     this.seleccionarNoviembre = seleccionarNoviembre;
/* 1127:     */   }
/* 1128:     */   
/* 1129:     */   public boolean isSeleccionarDiciembre()
/* 1130:     */   {
/* 1131: 975 */     return this.seleccionarDiciembre;
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public void setSeleccionarDiciembre(boolean seleccionarDiciembre)
/* 1135:     */   {
/* 1136: 979 */     this.seleccionarDiciembre = seleccionarDiciembre;
/* 1137:     */   }
/* 1138:     */   
/* 1139:     */   public boolean isDisableEnero()
/* 1140:     */   {
/* 1141: 983 */     return this.disableEnero;
/* 1142:     */   }
/* 1143:     */   
/* 1144:     */   public void setDisableEnero(boolean disableEnero)
/* 1145:     */   {
/* 1146: 987 */     this.disableEnero = disableEnero;
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   public boolean isDisableFebrero()
/* 1150:     */   {
/* 1151: 991 */     return this.disableFebrero;
/* 1152:     */   }
/* 1153:     */   
/* 1154:     */   public void setDisableFebrero(boolean disableFebrero)
/* 1155:     */   {
/* 1156: 995 */     this.disableFebrero = disableFebrero;
/* 1157:     */   }
/* 1158:     */   
/* 1159:     */   public boolean isDisableMarzo()
/* 1160:     */   {
/* 1161: 999 */     return this.disableMarzo;
/* 1162:     */   }
/* 1163:     */   
/* 1164:     */   public void setDisableMarzo(boolean disableMarzo)
/* 1165:     */   {
/* 1166:1003 */     this.disableMarzo = disableMarzo;
/* 1167:     */   }
/* 1168:     */   
/* 1169:     */   public boolean isDisableAbril()
/* 1170:     */   {
/* 1171:1007 */     return this.disableAbril;
/* 1172:     */   }
/* 1173:     */   
/* 1174:     */   public void setDisableAbril(boolean disableAbril)
/* 1175:     */   {
/* 1176:1011 */     this.disableAbril = disableAbril;
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   public boolean isDisableMayo()
/* 1180:     */   {
/* 1181:1015 */     return this.disableMayo;
/* 1182:     */   }
/* 1183:     */   
/* 1184:     */   public void setDisableMayo(boolean disableMayo)
/* 1185:     */   {
/* 1186:1019 */     this.disableMayo = disableMayo;
/* 1187:     */   }
/* 1188:     */   
/* 1189:     */   public boolean isDisableJunio()
/* 1190:     */   {
/* 1191:1023 */     return this.disableJunio;
/* 1192:     */   }
/* 1193:     */   
/* 1194:     */   public void setDisableJunio(boolean disableJunio)
/* 1195:     */   {
/* 1196:1027 */     this.disableJunio = disableJunio;
/* 1197:     */   }
/* 1198:     */   
/* 1199:     */   public boolean isDisableJulio()
/* 1200:     */   {
/* 1201:1031 */     return this.disableJulio;
/* 1202:     */   }
/* 1203:     */   
/* 1204:     */   public void setDisableJulio(boolean disableJulio)
/* 1205:     */   {
/* 1206:1035 */     this.disableJulio = disableJulio;
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   public boolean isDisableAgosto()
/* 1210:     */   {
/* 1211:1039 */     return this.disableAgosto;
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   public void setDisableAgosto(boolean disableAgosto)
/* 1215:     */   {
/* 1216:1043 */     this.disableAgosto = disableAgosto;
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   public boolean isDisableSeptiembre()
/* 1220:     */   {
/* 1221:1047 */     return this.disableSeptiembre;
/* 1222:     */   }
/* 1223:     */   
/* 1224:     */   public void setDisableSeptiembre(boolean disableSeptiembre)
/* 1225:     */   {
/* 1226:1051 */     this.disableSeptiembre = disableSeptiembre;
/* 1227:     */   }
/* 1228:     */   
/* 1229:     */   public boolean isDisableOctubre()
/* 1230:     */   {
/* 1231:1055 */     return this.disableOctubre;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public void setDisableOctubre(boolean disableOctubre)
/* 1235:     */   {
/* 1236:1059 */     this.disableOctubre = disableOctubre;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public boolean isDisableNoviembre()
/* 1240:     */   {
/* 1241:1063 */     return this.disableNoviembre;
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public void setDisableNoviembre(boolean disableNoviembre)
/* 1245:     */   {
/* 1246:1067 */     this.disableNoviembre = disableNoviembre;
/* 1247:     */   }
/* 1248:     */   
/* 1249:     */   public boolean isDisableDiciembre()
/* 1250:     */   {
/* 1251:1071 */     return this.disableDiciembre;
/* 1252:     */   }
/* 1253:     */   
/* 1254:     */   public void setDisableDiciembre(boolean disableDiciembre)
/* 1255:     */   {
/* 1256:1075 */     this.disableDiciembre = disableDiciembre;
/* 1257:     */   }
/* 1258:     */   
/* 1259:     */   public boolean isSeleccionarTodos()
/* 1260:     */   {
/* 1261:1079 */     return this.seleccionarTodos;
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   public void setSeleccionarTodos(boolean seleccionarTodos)
/* 1265:     */   {
/* 1266:1083 */     this.seleccionarTodos = seleccionarTodos;
/* 1267:     */   }
/* 1268:     */   
/* 1269:     */   public boolean isAsignarDetalle()
/* 1270:     */   {
/* 1271:1087 */     return this.asignarDetalle;
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public void setAsignarDetalle(boolean asignarDetalle)
/* 1275:     */   {
/* 1276:1091 */     this.asignarDetalle = asignarDetalle;
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public int getMesSeleccionado()
/* 1280:     */   {
/* 1281:1095 */     return this.mesSeleccionado;
/* 1282:     */   }
/* 1283:     */   
/* 1284:     */   public void setMesSeleccionado(int mesSeleccionado)
/* 1285:     */   {
/* 1286:1099 */     this.mesSeleccionado = mesSeleccionado;
/* 1287:     */   }
/* 1288:     */   
/* 1289:     */   public BigDecimal getTotalValorEnero()
/* 1290:     */   {
/* 1291:1103 */     return this.totalValorEnero;
/* 1292:     */   }
/* 1293:     */   
/* 1294:     */   public void setTotalValorEnero(BigDecimal totalValorEnero)
/* 1295:     */   {
/* 1296:1107 */     this.totalValorEnero = totalValorEnero;
/* 1297:     */   }
/* 1298:     */   
/* 1299:     */   public BigDecimal getTotalValorFebrero()
/* 1300:     */   {
/* 1301:1111 */     return this.totalValorFebrero;
/* 1302:     */   }
/* 1303:     */   
/* 1304:     */   public void setTotalValorFebrero(BigDecimal totalValorFebrero)
/* 1305:     */   {
/* 1306:1115 */     this.totalValorFebrero = totalValorFebrero;
/* 1307:     */   }
/* 1308:     */   
/* 1309:     */   public BigDecimal getTotalValorMarzo()
/* 1310:     */   {
/* 1311:1119 */     return this.totalValorMarzo;
/* 1312:     */   }
/* 1313:     */   
/* 1314:     */   public void setTotalValorMarzo(BigDecimal totalValorMarzo)
/* 1315:     */   {
/* 1316:1123 */     this.totalValorMarzo = totalValorMarzo;
/* 1317:     */   }
/* 1318:     */   
/* 1319:     */   public BigDecimal getTotalValorAbril()
/* 1320:     */   {
/* 1321:1127 */     return this.totalValorAbril;
/* 1322:     */   }
/* 1323:     */   
/* 1324:     */   public void setTotalValorAbril(BigDecimal totalValorAbril)
/* 1325:     */   {
/* 1326:1131 */     this.totalValorAbril = totalValorAbril;
/* 1327:     */   }
/* 1328:     */   
/* 1329:     */   public BigDecimal getTotalValorMayo()
/* 1330:     */   {
/* 1331:1135 */     return this.totalValorMayo;
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   public void setTotalValorMayo(BigDecimal totalValorMayo)
/* 1335:     */   {
/* 1336:1139 */     this.totalValorMayo = totalValorMayo;
/* 1337:     */   }
/* 1338:     */   
/* 1339:     */   public BigDecimal getTotalValorJunio()
/* 1340:     */   {
/* 1341:1143 */     return this.totalValorJunio;
/* 1342:     */   }
/* 1343:     */   
/* 1344:     */   public void setTotalValorJunio(BigDecimal totalValorJunio)
/* 1345:     */   {
/* 1346:1147 */     this.totalValorJunio = totalValorJunio;
/* 1347:     */   }
/* 1348:     */   
/* 1349:     */   public BigDecimal getTotalValorJulio()
/* 1350:     */   {
/* 1351:1151 */     return this.totalValorJulio;
/* 1352:     */   }
/* 1353:     */   
/* 1354:     */   public void setTotalValorJulio(BigDecimal totalValorJulio)
/* 1355:     */   {
/* 1356:1155 */     this.totalValorJulio = totalValorJulio;
/* 1357:     */   }
/* 1358:     */   
/* 1359:     */   public BigDecimal getTotalValorAgosto()
/* 1360:     */   {
/* 1361:1159 */     return this.totalValorAgosto;
/* 1362:     */   }
/* 1363:     */   
/* 1364:     */   public void setTotalValorAgosto(BigDecimal totalValorAgosto)
/* 1365:     */   {
/* 1366:1163 */     this.totalValorAgosto = totalValorAgosto;
/* 1367:     */   }
/* 1368:     */   
/* 1369:     */   public BigDecimal getTotalValorSeptiembre()
/* 1370:     */   {
/* 1371:1167 */     return this.totalValorSeptiembre;
/* 1372:     */   }
/* 1373:     */   
/* 1374:     */   public void setTotalValorSeptiembre(BigDecimal totalValorSeptiembre)
/* 1375:     */   {
/* 1376:1171 */     this.totalValorSeptiembre = totalValorSeptiembre;
/* 1377:     */   }
/* 1378:     */   
/* 1379:     */   public BigDecimal getTotalValorOctubre()
/* 1380:     */   {
/* 1381:1175 */     return this.totalValorOctubre;
/* 1382:     */   }
/* 1383:     */   
/* 1384:     */   public void setTotalValorOctubre(BigDecimal totalValorOctubre)
/* 1385:     */   {
/* 1386:1179 */     this.totalValorOctubre = totalValorOctubre;
/* 1387:     */   }
/* 1388:     */   
/* 1389:     */   public BigDecimal getTotalValorNoviembre()
/* 1390:     */   {
/* 1391:1183 */     return this.totalValorNoviembre;
/* 1392:     */   }
/* 1393:     */   
/* 1394:     */   public void setTotalValorNoviembre(BigDecimal totalValorNoviembre)
/* 1395:     */   {
/* 1396:1187 */     this.totalValorNoviembre = totalValorNoviembre;
/* 1397:     */   }
/* 1398:     */   
/* 1399:     */   public BigDecimal getTotalValorDiciembre()
/* 1400:     */   {
/* 1401:1191 */     return this.totalValorDiciembre;
/* 1402:     */   }
/* 1403:     */   
/* 1404:     */   public void setTotalValorDiciembre(BigDecimal totalValorDiciembre)
/* 1405:     */   {
/* 1406:1195 */     this.totalValorDiciembre = totalValorDiciembre;
/* 1407:     */   }
/* 1408:     */   
/* 1409:     */   public String getNombreDimension()
/* 1410:     */   {
/* 1411:1198 */     OrganizacionConfiguracion aux = AppUtil.getOrganizacion().getOrganizacionConfiguracion();
/* 1412:1199 */     if ((!aux.getNombreDimension1().equals("")) && (aux.isIndicadorUsoPresupuestoDimension1())) {
/* 1413:1200 */       this.nombreDimension = aux.getNombreDimension1();
/* 1414:1201 */     } else if ((!aux.getNombreDimension2().equals("")) && (aux.isIndicadorUsoPresupuestoDimension2())) {
/* 1415:1202 */       this.nombreDimension = aux.getNombreDimension2();
/* 1416:1203 */     } else if ((!aux.getNombreDimension3().equals("")) && (aux.isIndicadorUsoPresupuestoDimension3())) {
/* 1417:1204 */       this.nombreDimension = aux.getNombreDimension3();
/* 1418:1205 */     } else if ((!aux.getNombreDimension4().equals("")) && (aux.isIndicadorUsoPresupuestoDimension4())) {
/* 1419:1206 */       this.nombreDimension = aux.getNombreDimension4();
/* 1420:1207 */     } else if ((!aux.getNombreDimension5().equals("")) && (aux.isIndicadorUsoPresupuestoDimension5())) {
/* 1421:1208 */       this.nombreDimension = aux.getNombreDimension5();
/* 1422:     */     }
/* 1423:1210 */     return this.nombreDimension;
/* 1424:     */   }
/* 1425:     */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.presupuesto.procesos.PresupuestoBean
 * JD-Core Version:    0.7.0.1
 */