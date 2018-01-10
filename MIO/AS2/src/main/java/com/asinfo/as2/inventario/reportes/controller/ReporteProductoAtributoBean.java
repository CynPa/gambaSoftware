/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.entities.Atributo;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.ConjuntoAtributo;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   9:    */ import com.asinfo.as2.enumeraciones.OrdenarEnum;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioCategoriaProducto;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioConjuntoAtributo;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioSubcategoriaProducto;
/*  14:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  15:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteProductoAtributo;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.reportes.AbstractBaseReportBean;
/*  18:    */ import com.asinfo.as2.utils.reportes.QueryResultDataSource;
/*  19:    */ import java.io.IOException;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import javax.faces.model.SelectItem;
/*  28:    */ import net.sf.jasperreports.engine.JRDataSource;
/*  29:    */ import net.sf.jasperreports.engine.JRException;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ReporteProductoAtributoBean
/*  34:    */   extends AbstractBaseReportBean
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = -6560379730096562196L;
/*  37: 57 */   private String COMPILE_FILE_NAME = "reporteProductoAtributo";
/*  38:    */   private CategoriaProducto categoriaProducto;
/*  39:    */   private SubcategoriaProducto subcategoriaProducto;
/*  40:    */   private Atributo atributo;
/*  41:    */   private ConjuntoAtributo conjuntoAtributo;
/*  42:    */   @EJB
/*  43:    */   private transient ServicioReporteProductoAtributo servicioReporteProductoAtributo;
/*  44:    */   @EJB
/*  45:    */   private transient ServicioCategoriaProducto servicioCategoriaProducto;
/*  46:    */   @EJB
/*  47:    */   private transient ServicioSubcategoriaProducto servicioSubcategoriaProducto;
/*  48:    */   @EJB
/*  49:    */   private transient ServicioAtributo servicioAtributo;
/*  50:    */   @EJB
/*  51:    */   private transient ServicioConjuntoAtributo servicioConjuntoAtributo;
/*  52:    */   private List<CategoriaProducto> listaCategoriaProducto;
/*  53:    */   private List<SubcategoriaProducto> listaSubcategoriaProducto;
/*  54:    */   private List<Atributo> listaAtributo;
/*  55:    */   private List<SelectItem> listaOrdenar;
/*  56:    */   private List<ConjuntoAtributo> listaConjuntoAtributo;
/*  57:    */   
/*  58:    */   protected JRDataSource getJRDataSource()
/*  59:    */   {
/*  60: 94 */     List listaDatosReporte = new ArrayList();
/*  61: 95 */     JRDataSource ds = null;
/*  62:    */     try
/*  63:    */     {
/*  64: 98 */       String[] fields = { "nombreAtributo", "valorProductoAtributo", "codigoProducto", "nombreProducto", "nombreCategoria", "subcategoriaProducto", "nombreComercial" };
/*  65:    */       
/*  66:    */ 
/*  67:    */ 
/*  68:102 */       listaDatosReporte = this.servicioReporteProductoAtributo.obtenerProductoAtributo(this.categoriaProducto, this.subcategoriaProducto, this.atributo, this.conjuntoAtributo, 
/*  69:103 */         AppUtil.getOrganizacion().getId());
/*  70:104 */       ds = new QueryResultDataSource(listaDatosReporte, fields);
/*  71:    */     }
/*  72:    */     catch (ExcepcionAS2Inventario e)
/*  73:    */     {
/*  74:106 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  75:    */     }
/*  76:108 */     return ds;
/*  77:    */   }
/*  78:    */   
/*  79:    */   protected Map<String, Object> getReportParameters()
/*  80:    */   {
/*  81:119 */     Map<String, Object> reportParameters = super.getReportParameters();
/*  82:120 */     reportParameters.put("ReportTitle", "Atributos por Producto");
/*  83:121 */     reportParameters.put("p_categoriaProducto", this.categoriaProducto != null ? this.categoriaProducto.getNombre() : "Todos");
/*  84:122 */     reportParameters.put("p_subcategoriaProducto", this.subcategoriaProducto != null ? this.subcategoriaProducto.getNombre() : "Todos");
/*  85:123 */     reportParameters.put("p_atributo", this.atributo != null ? this.atributo.getNombre() : "Todos");
/*  86:124 */     reportParameters.put("p_conjuntoAtributo", this.conjuntoAtributo != null ? this.conjuntoAtributo.getNombre() : "Todos");
/*  87:125 */     return reportParameters;
/*  88:    */   }
/*  89:    */   
/*  90:    */   protected String getCompileFileName()
/*  91:    */   {
/*  92:135 */     return this.COMPILE_FILE_NAME;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String execute()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:146 */       super.prepareReport();
/* 100:    */     }
/* 101:    */     catch (JRException e)
/* 102:    */     {
/* 103:149 */       e.printStackTrace();
/* 104:150 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 105:    */     }
/* 106:    */     catch (IOException e)
/* 107:    */     {
/* 108:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 109:    */     }
/* 110:154 */     return "";
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String obtenerSubcategoria()
/* 114:    */   {
/* 115:158 */     Map<String, String> filters = new HashMap();
/* 116:159 */     filters.put("categoriaProducto.idCategoriaProducto", String.valueOf(this.categoriaProducto.getId()));
/* 117:    */     
/* 118:161 */     this.listaSubcategoriaProducto = this.servicioSubcategoriaProducto.obtenerListaCombo("nombre", true, filters);
/* 119:    */     
/* 120:163 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String obtenerAtributo()
/* 124:    */   {
/* 125:167 */     Map<String, String> filters = new HashMap();
/* 126:168 */     filters.put("conjuntoAtributo.idConjuntoAtributo", String.valueOf(this.conjuntoAtributo.getId()));
/* 127:    */     
/* 128:170 */     this.listaAtributo = this.servicioAtributo.obtenerListaCombo("nombre", true, filters);
/* 129:    */     
/* 130:172 */     return "";
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String obtenerConjuntoAtributoAtributo()
/* 134:    */   {
/* 135:176 */     Map<String, String> filters = new HashMap();
/* 136:177 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public CategoriaProducto getCategoriaProducto()
/* 140:    */   {
/* 141:190 */     return this.categoriaProducto;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setCategoriaProducto(CategoriaProducto categoriaProducto)
/* 145:    */   {
/* 146:200 */     this.categoriaProducto = categoriaProducto;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public SubcategoriaProducto getSubcategoriaProducto()
/* 150:    */   {
/* 151:209 */     return this.subcategoriaProducto;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setSubcategoriaProducto(SubcategoriaProducto subcategoriaProducto)
/* 155:    */   {
/* 156:219 */     this.subcategoriaProducto = subcategoriaProducto;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public List<CategoriaProducto> getListaCategoriaProducto()
/* 160:    */   {
/* 161:228 */     if (this.listaCategoriaProducto == null) {
/* 162:229 */       this.listaCategoriaProducto = this.servicioCategoriaProducto.obtenerListaCombo("nombre", true, null);
/* 163:    */     }
/* 164:230 */     return this.listaCategoriaProducto;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setListaCategoriaProducto(List<CategoriaProducto> listaCategoriaProducto)
/* 168:    */   {
/* 169:240 */     this.listaCategoriaProducto = listaCategoriaProducto;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public List<SubcategoriaProducto> getListaSubcategoriaProducto()
/* 173:    */   {
/* 174:250 */     if (this.listaSubcategoriaProducto == null) {
/* 175:251 */       this.listaSubcategoriaProducto = new ArrayList();
/* 176:    */     }
/* 177:253 */     return this.listaSubcategoriaProducto;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setListaSubcategoriaProducto(List<SubcategoriaProducto> listaSubcategoriaProducto)
/* 181:    */   {
/* 182:263 */     this.listaSubcategoriaProducto = listaSubcategoriaProducto;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public List<Atributo> getListaAtributo()
/* 186:    */   {
/* 187:272 */     if (this.listaAtributo == null)
/* 188:    */     {
/* 189:274 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 190:275 */       filters.put("indicadorProducto", "true");
/* 191:276 */       this.listaAtributo = this.servicioAtributo.obtenerListaCombo("nombre", false, filters);
/* 192:    */     }
/* 193:278 */     return this.listaAtributo;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setListaAtributo(List<Atributo> listaAtributo)
/* 197:    */   {
/* 198:288 */     this.listaAtributo = listaAtributo;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public Atributo getAtributo()
/* 202:    */   {
/* 203:297 */     return this.atributo;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setAtributo(Atributo atributo)
/* 207:    */   {
/* 208:307 */     this.atributo = atributo;
/* 209:    */   }
/* 210:    */   
/* 211:    */   public List<SelectItem> getListaOrdenar()
/* 212:    */   {
/* 213:316 */     if (this.listaOrdenar == null)
/* 214:    */     {
/* 215:317 */       this.listaOrdenar = new ArrayList();
/* 216:318 */       for (OrdenarEnum ordenar : OrdenarEnum.values())
/* 217:    */       {
/* 218:319 */         SelectItem item = new SelectItem(ordenar, ordenar.getNombre());
/* 219:320 */         this.listaOrdenar.add(item);
/* 220:    */       }
/* 221:    */     }
/* 222:323 */     return this.listaOrdenar;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setListaOrdenar(List<SelectItem> listaOrdenar)
/* 226:    */   {
/* 227:333 */     this.listaOrdenar = listaOrdenar;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public ConjuntoAtributo getConjuntoAtributo()
/* 231:    */   {
/* 232:342 */     return this.conjuntoAtributo;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setConjuntoAtributo(ConjuntoAtributo conjuntoAtributo)
/* 236:    */   {
/* 237:352 */     this.conjuntoAtributo = conjuntoAtributo;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List<ConjuntoAtributo> getListaConjuntoAtributo()
/* 241:    */   {
/* 242:361 */     if (this.listaConjuntoAtributo == null)
/* 243:    */     {
/* 244:362 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 245:363 */       filters.put("indicadorProducto", "true");
/* 246:364 */       this.listaConjuntoAtributo = this.servicioConjuntoAtributo.obtenerListaCombo("nombre", false, filters);
/* 247:    */     }
/* 248:367 */     return this.listaConjuntoAtributo;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setListaConjuntoAtributo(List<ConjuntoAtributo> listaConjuntoAtributo)
/* 252:    */   {
/* 253:377 */     this.listaConjuntoAtributo = listaConjuntoAtributo;
/* 254:    */   }
/* 255:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteProductoAtributoBean
 * JD-Core Version:    0.7.0.1
 */