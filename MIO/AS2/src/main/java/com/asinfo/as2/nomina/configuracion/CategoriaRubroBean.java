/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaRubro;
/*   6:    */ import com.asinfo.as2.entities.CategoriaRubroRubro;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Rubro;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  12:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioRubro;
/*  13:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import com.asinfo.as2.utils.JsfUtil;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.HashMap;
/*  18:    */ import java.util.List;
/*  19:    */ import java.util.Map;
/*  20:    */ import javax.annotation.PostConstruct;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.faces.bean.ManagedBean;
/*  23:    */ import javax.faces.bean.ViewScoped;
/*  24:    */ import javax.faces.model.SelectItem;
/*  25:    */ import org.apache.log4j.Logger;
/*  26:    */ import org.primefaces.component.datatable.DataTable;
/*  27:    */ import org.primefaces.model.LazyDataModel;
/*  28:    */ import org.primefaces.model.SortOrder;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class CategoriaRubroBean
/*  33:    */   extends PageControllerAS2
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = 2099368479844654064L;
/*  36:    */   @EJB
/*  37:    */   private ServicioGenerico<CategoriaRubro> servicioCategoriaRubro;
/*  38:    */   @EJB
/*  39:    */   ServicioAtributo servicioAtributo;
/*  40:    */   @EJB
/*  41:    */   ServicioRubro servicioRubro;
/*  42:    */   private CategoriaRubro categoriaRubro;
/*  43:    */   private LazyDataModel<CategoriaRubro> listaCategoriaRubro;
/*  44:    */   private List<SelectItem> categoriaRubroItems;
/*  45:    */   private DataTable dtCategoriaRubro;
/*  46:    */   private DataTable dtRubro;
/*  47:    */   private Rubro[] listaRubrosSeleccionados;
/*  48:    */   private List<Rubro> listaRubrosNoAsignados;
/*  49:    */   private List<CategoriaRubroRubro> listaCategoriaRubroRubro;
/*  50:    */   
/*  51:    */   @PostConstruct
/*  52:    */   public void init()
/*  53:    */   {
/*  54: 75 */     this.listaCategoriaRubro = new LazyDataModel()
/*  55:    */     {
/*  56:    */       private static final long serialVersionUID = 1L;
/*  57:    */       
/*  58:    */       public List<CategoriaRubro> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */       {
/*  60: 82 */         List<CategoriaRubro> lista = new ArrayList();
/*  61: 83 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  62:    */         
/*  63: 85 */         lista = CategoriaRubroBean.this.servicioCategoriaRubro.obtenerListaPorPagina(CategoriaRubro.class, startIndex, pageSize, sortField, ordenar, filters);
/*  64: 86 */         CategoriaRubroBean.this.listaCategoriaRubro.setRowCount(CategoriaRubroBean.this.servicioCategoriaRubro.contarPorCriterio(CategoriaRubro.class, filters));
/*  65:    */         
/*  66: 88 */         return lista;
/*  67:    */       }
/*  68:    */     };
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String editar()
/*  72:    */   {
/*  73: 96 */     if (getCategoriaRubro().getIdCategoriaRubro() > 0)
/*  74:    */     {
/*  75: 97 */       List<String> listaCampos = new ArrayList();
/*  76: 98 */       listaCampos.add("listaCategoriaRubroRubro.rubro");
/*  77: 99 */       this.categoriaRubro = ((CategoriaRubro)this.servicioCategoriaRubro.cargarDetalle(this.categoriaRubro.getClass(), this.categoriaRubro.getId(), listaCampos));
/*  78:100 */       setEditado(true);
/*  79:101 */       getListaCategoriaRubroRubro();
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83:103 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  84:    */     }
/*  85:106 */     return "";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String guardar()
/*  89:    */   {
/*  90:    */     try
/*  91:    */     {
/*  92:113 */       this.servicioCategoriaRubro.guardarValidar(this.categoriaRubro, this.categoriaRubro.getListaCategoriaRubroRubro());
/*  93:114 */       cargarDatos();
/*  94:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  95:    */     }
/*  96:    */     catch (AS2Exception e)
/*  97:    */     {
/*  98:117 */       JsfUtil.addErrorMessage(e, "");
/*  99:    */     }
/* 100:    */     catch (Exception e)
/* 101:    */     {
/* 102:119 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 103:120 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 104:    */     }
/* 105:122 */     return "";
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String eliminar()
/* 109:    */   {
/* 110:    */     try
/* 111:    */     {
/* 112:134 */       List<String> listaCampos = new ArrayList();
/* 113:135 */       listaCampos.add("listaCategoriaRubroRubro.rubro");
/* 114:136 */       this.categoriaRubro = ((CategoriaRubro)this.servicioCategoriaRubro.cargarDetalle(this.categoriaRubro.getClass(), this.categoriaRubro.getId(), listaCampos));
/* 115:137 */       this.servicioCategoriaRubro.eliminar(this.categoriaRubro, this.categoriaRubro.getListaCategoriaRubroRubro());
/* 116:138 */       cargarDatos();
/* 117:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 118:    */     }
/* 119:    */     catch (Exception e)
/* 120:    */     {
/* 121:141 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 122:142 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 123:    */     }
/* 124:144 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String cargarDatos()
/* 128:    */   {
/* 129:150 */     setEditado(false);
/* 130:    */     try
/* 131:    */     {
/* 132:153 */       limpiar();
/* 133:    */     }
/* 134:    */     catch (Exception e)
/* 135:    */     {
/* 136:156 */       LOG.error("ERROR AL CARGAR DATOS", e);
/* 137:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 138:    */     }
/* 139:159 */     return "";
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String agregarRubros()
/* 143:    */   {
/* 144:170 */     if (this.listaRubrosSeleccionados != null) {
/* 145:    */       label152:
/* 146:171 */       for (Rubro rubro : this.listaRubrosSeleccionados)
/* 147:    */       {
/* 148:172 */         for (CategoriaRubroRubro dr : this.categoriaRubro.getListaCategoriaRubroRubro()) {
/* 149:173 */           if (dr.getRubro().equals(rubro))
/* 150:    */           {
/* 151:174 */             dr.setEliminado(false);
/* 152:175 */             getListaCategoriaRubroRubro().add(dr);
/* 153:    */             break label152;
/* 154:    */           }
/* 155:    */         }
/* 156:179 */         CategoriaRubroRubro crr = new CategoriaRubroRubro();
/* 157:180 */         crr.setCategoriaRubro(this.categoriaRubro);
/* 158:181 */         crr.setRubro(rubro);
/* 159:182 */         this.categoriaRubro.getListaCategoriaRubroRubro().add(crr);
/* 160:183 */         getListaCategoriaRubroRubro().add(crr);
/* 161:    */       }
/* 162:    */     }
/* 163:186 */     this.listaRubrosSeleccionados = null;
/* 164:    */     
/* 165:188 */     return "";
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void cargarRubrosNoAsignados()
/* 169:    */   {
/* 170:192 */     this.listaRubrosSeleccionados = null;
/* 171:193 */     Map<String, String> filters = new HashMap();
/* 172:194 */     filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 173:195 */     this.listaRubrosNoAsignados = this.servicioRubro.obtenerListaCombo("nombre", true, filters);
/* 174:196 */     for (CategoriaRubroRubro dr : this.categoriaRubro.getListaCategoriaRubroRubro()) {
/* 175:197 */       if (!dr.isEliminado()) {
/* 176:198 */         this.listaRubrosNoAsignados.remove(dr.getRubro());
/* 177:    */       }
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void eliminarCategoriaRubroRubro(CategoriaRubroRubro categoriaRubroRubro)
/* 182:    */   {
/* 183:210 */     categoriaRubroRubro.setEliminado(true);
/* 184:211 */     this.listaCategoriaRubroRubro = null;
/* 185:212 */     this.dtRubro.reset();
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String limpiar()
/* 189:    */   {
/* 190:220 */     this.categoriaRubro = new CategoriaRubro();
/* 191:221 */     this.categoriaRubro.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 192:222 */     this.categoriaRubro.setIdSucursal(AppUtil.getSucursal().getId());
/* 193:223 */     this.listaCategoriaRubroRubro = null;
/* 194:224 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public CategoriaRubro getCategoriaRubro()
/* 198:    */   {
/* 199:229 */     if (this.categoriaRubro == null) {
/* 200:230 */       this.categoriaRubro = new CategoriaRubro();
/* 201:    */     }
/* 202:232 */     return this.categoriaRubro;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setCategoriaRubro(CategoriaRubro categoriaRubro)
/* 206:    */   {
/* 207:236 */     this.categoriaRubro = categoriaRubro;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setConjuntoAtributoItems(List<SelectItem> conjuntoAtributoItems)
/* 211:    */   {
/* 212:240 */     this.categoriaRubroItems = conjuntoAtributoItems;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public List<SelectItem> getConjuntoAtributoItems()
/* 216:    */   {
/* 217:249 */     return this.categoriaRubroItems;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public DataTable getDtCategoriaRubro()
/* 221:    */   {
/* 222:254 */     return this.dtCategoriaRubro;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void setDtCategoriaRubro(DataTable dtCategoriaRubro)
/* 226:    */   {
/* 227:258 */     this.dtCategoriaRubro = dtCategoriaRubro;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public DataTable getDtRubro()
/* 231:    */   {
/* 232:262 */     return this.dtRubro;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setDtRubro(DataTable dtRubro)
/* 236:    */   {
/* 237:266 */     this.dtRubro = dtRubro;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public List<CategoriaRubroRubro> getListaCategoriaRubroRubro()
/* 241:    */   {
/* 242:270 */     if ((this.categoriaRubro != null) && (this.listaCategoriaRubroRubro == null))
/* 243:    */     {
/* 244:271 */       this.listaCategoriaRubroRubro = new ArrayList();
/* 245:272 */       for (CategoriaRubroRubro cRRubro : this.categoriaRubro.getListaCategoriaRubroRubro()) {
/* 246:273 */         if (!cRRubro.isEliminado()) {
/* 247:274 */           this.listaCategoriaRubroRubro.add(cRRubro);
/* 248:    */         }
/* 249:    */       }
/* 250:    */     }
/* 251:278 */     return this.listaCategoriaRubroRubro;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public void setListaCategoriaRubroRubro(List<CategoriaRubroRubro> listaCategoriaRubroRubro)
/* 255:    */   {
/* 256:283 */     this.listaCategoriaRubroRubro = listaCategoriaRubroRubro;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public Rubro[] getListaRubrosSeleccionados()
/* 260:    */   {
/* 261:287 */     return this.listaRubrosSeleccionados;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setListaRubrosSeleccionados(Rubro[] listaRubrosSeleccionados)
/* 265:    */   {
/* 266:291 */     this.listaRubrosSeleccionados = listaRubrosSeleccionados;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public List<Rubro> getListaRubrosNoAsignados()
/* 270:    */   {
/* 271:295 */     return this.listaRubrosNoAsignados;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void setListaRubrosNoAsignados(List<Rubro> listaRubrosNoAsignados)
/* 275:    */   {
/* 276:299 */     this.listaRubrosNoAsignados = listaRubrosNoAsignados;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public LazyDataModel<CategoriaRubro> getListaCategoriaRubro()
/* 280:    */   {
/* 281:303 */     return this.listaCategoriaRubro;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public void setListaCategoriaRubro(LazyDataModel<CategoriaRubro> listaCategoriaRubro)
/* 285:    */   {
/* 286:308 */     this.listaCategoriaRubro = listaCategoriaRubro;
/* 287:    */   }
/* 288:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.CategoriaRubroBean
 * JD-Core Version:    0.7.0.1
 */