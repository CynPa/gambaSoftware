/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Lote;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.Sucursal;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoProducto;
/*  14:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.JsfUtil;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.annotation.PostConstruct;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import org.apache.log4j.Logger;
/*  28:    */ import org.primefaces.component.datatable.DataTable;
/*  29:    */ import org.primefaces.model.LazyDataModel;
/*  30:    */ import org.primefaces.model.SortOrder;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class LoteBean
/*  35:    */   extends PageControllerAS2
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  38:    */   @EJB
/*  39:    */   private ServicioLote servicioLote;
/*  40:    */   @EJB
/*  41:    */   private ServicioEmpresa servicioEmpresa;
/*  42:    */   @EJB
/*  43:    */   private ServicioProducto servicioProducto;
/*  44:    */   @EJB
/*  45:    */   private ServicioAtributo servicioAtributo;
/*  46:    */   private Lote lote;
/*  47:    */   private Producto producto;
/*  48:    */   private LazyDataModel<Lote> listaLote;
/*  49:    */   private DataTable dtLote;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 91 */     this.listaLote = new LazyDataModel()
/*  55:    */     {
/*  56:    */       private static final long serialVersionUID = 1L;
/*  57:    */       
/*  58:    */       public List<Lote> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */       {
/*  60: 98 */         List<Lote> lista = new ArrayList();
/*  61: 99 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  62:    */         
/*  63:101 */         lista = LoteBean.this.servicioLote.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  64:    */         
/*  65:103 */         LoteBean.this.listaLote.setRowCount(LoteBean.this.servicioLote.contarPorCriterio(filters));
/*  66:    */         
/*  67:105 */         return lista;
/*  68:    */       }
/*  69:    */     };
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void movimientoInterno()
/*  73:    */   {
/*  74:117 */     if (this.lote.isIndicadorMovimientoInterno()) {
/*  75:118 */       this.lote.setEmpresa(null);
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   private void crearEntidad()
/*  80:    */   {
/*  81:131 */     this.lote = new Lote();
/*  82:132 */     this.lote.setActivo(true);
/*  83:133 */     this.lote.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  84:134 */     this.lote.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String editar()
/*  88:    */   {
/*  89:143 */     if ((getLote() != null) && (getLote().getIdLote() != 0))
/*  90:    */     {
/*  91:144 */       this.lote = this.servicioLote.cargarDetalle(this.lote.getIdLote());
/*  92:145 */       cargarAtributos();
/*  93:146 */       setEditado(true);
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:148 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  98:    */     }
/*  99:150 */     return "";
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String guardar()
/* 103:    */   {
/* 104:    */     try
/* 105:    */     {
/* 106:160 */       this.lote.setCodigo(this.lote.getProducto().getPrefijoLote() + this.lote.getCodigo());
/* 107:161 */       this.servicioLote.guardar(this.lote);
/* 108:162 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 109:163 */       limpiar();
/* 110:164 */       setEditado(false);
/* 111:    */     }
/* 112:    */     catch (AS2Exception e)
/* 113:    */     {
/* 114:166 */       JsfUtil.addErrorMessage(e, "");
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:168 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 119:169 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 120:    */     }
/* 121:171 */     return "";
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String eliminar()
/* 125:    */   {
/* 126:    */     try
/* 127:    */     {
/* 128:181 */       this.servicioLote.eliminar(this.lote);
/* 129:182 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 130:    */     }
/* 131:    */     catch (Exception e)
/* 132:    */     {
/* 133:184 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 134:185 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 135:    */     }
/* 136:187 */     return "";
/* 137:    */   }
/* 138:    */   
/* 139:    */   public String cargarDatos()
/* 140:    */   {
/* 141:196 */     return "";
/* 142:    */   }
/* 143:    */   
/* 144:    */   public String limpiar()
/* 145:    */   {
/* 146:205 */     crearEntidad();
/* 147:206 */     return "";
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<Empresa> autocompletarProveedores(String consulta)
/* 151:    */   {
/* 152:210 */     return this.servicioEmpresa.autocompletarProveedores(consulta);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public List<Producto> autocompletarProducto(String consulta)
/* 156:    */   {
/* 157:214 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String cargarProducto(Producto newProducto)
/* 161:    */   {
/* 162:223 */     this.producto = newProducto;
/* 163:224 */     if (this.producto.getTipoProducto() != TipoProducto.ARTICULO)
/* 164:    */     {
/* 165:225 */       this.lote.setProducto(null);
/* 166:226 */       addInfoMessage(getLanguageController().getMensaje("msg_info_producto_articulo"));
/* 167:    */     }
/* 168:    */     else
/* 169:    */     {
/* 170:228 */       this.lote.setProducto(this.producto);
/* 171:229 */       actualizarAtributos(this.lote);
/* 172:    */     }
/* 173:231 */     return "";
/* 174:    */   }
/* 175:    */   
/* 176:    */   private void actualizarAtributos(Lote lote)
/* 177:    */   {
/* 178:237 */     Producto producto = this.servicioProducto.cargarProductoConAtributoInstancia(lote.getProducto().getIdProducto());
/* 179:    */     
/* 180:239 */     Atributo atributo = producto.getAtributoProduccion1();
/* 181:240 */     if (atributo != null)
/* 182:    */     {
/* 183:241 */       lote.setAtributo1(atributo);
/* 184:242 */       lote.setValorAtributo1(atributo.getValorAtributoPredeterminado());
/* 185:    */     }
/* 186:245 */     atributo = producto.getAtributoProduccion2();
/* 187:246 */     if (atributo != null)
/* 188:    */     {
/* 189:247 */       lote.setAtributo2(atributo);
/* 190:248 */       lote.setValorAtributo2(atributo.getValorAtributoPredeterminado());
/* 191:    */     }
/* 192:251 */     atributo = producto.getAtributoProduccion3();
/* 193:252 */     if (atributo != null)
/* 194:    */     {
/* 195:253 */       lote.setAtributo3(atributo);
/* 196:254 */       lote.setValorAtributo3(atributo.getValorAtributoPredeterminado());
/* 197:    */     }
/* 198:257 */     atributo = producto.getAtributoProduccion4();
/* 199:258 */     if (atributo != null)
/* 200:    */     {
/* 201:259 */       lote.setAtributo4(atributo);
/* 202:260 */       lote.setValorAtributo4(atributo.getValorAtributoPredeterminado());
/* 203:    */     }
/* 204:263 */     atributo = producto.getAtributoProduccion5();
/* 205:264 */     if (atributo != null)
/* 206:    */     {
/* 207:265 */       lote.setAtributo5(atributo);
/* 208:266 */       lote.setValorAtributo5(atributo.getValorAtributoPredeterminado());
/* 209:    */     }
/* 210:269 */     atributo = producto.getAtributoProduccion6();
/* 211:270 */     if (atributo != null)
/* 212:    */     {
/* 213:271 */       lote.setAtributo6(atributo);
/* 214:272 */       lote.setValorAtributo6(atributo.getValorAtributoPredeterminado());
/* 215:    */     }
/* 216:275 */     atributo = producto.getAtributoProduccion7();
/* 217:276 */     if (atributo != null)
/* 218:    */     {
/* 219:277 */       lote.setAtributo7(atributo);
/* 220:278 */       lote.setValorAtributo7(atributo.getValorAtributoPredeterminado());
/* 221:    */     }
/* 222:281 */     atributo = producto.getAtributoProduccion8();
/* 223:282 */     if (atributo != null)
/* 224:    */     {
/* 225:283 */       lote.setAtributo8(atributo);
/* 226:284 */       lote.setValorAtributo8(atributo.getValorAtributoPredeterminado());
/* 227:    */     }
/* 228:287 */     atributo = producto.getAtributoProduccion9();
/* 229:288 */     if (atributo != null)
/* 230:    */     {
/* 231:289 */       lote.setAtributo9(atributo);
/* 232:290 */       lote.setValorAtributo9(atributo.getValorAtributoPredeterminado());
/* 233:    */     }
/* 234:293 */     atributo = producto.getAtributoProduccion10();
/* 235:294 */     if (atributo != null)
/* 236:    */     {
/* 237:295 */       lote.setAtributo10(atributo);
/* 238:296 */       lote.setValorAtributo10(atributo.getValorAtributoPredeterminado());
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void cargarAtributos()
/* 243:    */   {
/* 244:302 */     Lote loteAtributo = this.servicioLote.getAtributosLote(this.lote.getId(), AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/* 245:303 */     this.lote.setAtributo1(loteAtributo.getAtributo1());
/* 246:304 */     this.lote.setValorAtributo1(loteAtributo.getValorAtributo1());
/* 247:305 */     this.lote.setAtributo2(loteAtributo.getAtributo2());
/* 248:306 */     this.lote.setValorAtributo2(loteAtributo.getValorAtributo2());
/* 249:307 */     this.lote.setAtributo3(loteAtributo.getAtributo3());
/* 250:308 */     this.lote.setValorAtributo3(loteAtributo.getValorAtributo3());
/* 251:309 */     this.lote.setAtributo4(loteAtributo.getAtributo4());
/* 252:310 */     this.lote.setValorAtributo4(loteAtributo.getValorAtributo4());
/* 253:311 */     this.lote.setAtributo5(loteAtributo.getAtributo5());
/* 254:312 */     this.lote.setValorAtributo5(loteAtributo.getValorAtributo5());
/* 255:313 */     this.lote.setAtributo6(loteAtributo.getAtributo6());
/* 256:314 */     this.lote.setValorAtributo6(loteAtributo.getValorAtributo6());
/* 257:315 */     this.lote.setAtributo7(loteAtributo.getAtributo7());
/* 258:316 */     this.lote.setValorAtributo7(loteAtributo.getValorAtributo7());
/* 259:317 */     this.lote.setAtributo8(loteAtributo.getAtributo8());
/* 260:318 */     this.lote.setValorAtributo8(loteAtributo.getValorAtributo8());
/* 261:319 */     this.lote.setAtributo9(loteAtributo.getAtributo9());
/* 262:320 */     this.lote.setValorAtributo9(loteAtributo.getValorAtributo9());
/* 263:321 */     this.lote.setAtributo10(loteAtributo.getAtributo10());
/* 264:322 */     this.lote.setValorAtributo10(loteAtributo.getValorAtributo10());
/* 265:    */   }
/* 266:    */   
/* 267:    */   public Lote getLote()
/* 268:    */   {
/* 269:386 */     return this.lote;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setLote(Lote lote)
/* 273:    */   {
/* 274:396 */     this.lote = lote;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public LazyDataModel<Lote> getListaLote()
/* 278:    */   {
/* 279:405 */     return this.listaLote;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setListaLote(LazyDataModel<Lote> listaLote)
/* 283:    */   {
/* 284:415 */     this.listaLote = listaLote;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public DataTable getDtLote()
/* 288:    */   {
/* 289:424 */     return this.dtLote;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setDtLote(DataTable dtLote)
/* 293:    */   {
/* 294:434 */     this.dtLote = dtLote;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public Producto getProducto()
/* 298:    */   {
/* 299:443 */     return this.producto;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setProducto(Producto producto)
/* 303:    */   {
/* 304:453 */     this.producto = producto;
/* 305:    */   }
/* 306:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.LoteBean
 * JD-Core Version:    0.7.0.1
 */