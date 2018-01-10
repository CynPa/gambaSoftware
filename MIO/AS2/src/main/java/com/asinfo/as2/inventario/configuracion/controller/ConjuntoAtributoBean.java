/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Atributo;
/*   6:    */ import com.asinfo.as2.entities.ConjuntoAtributo;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioConjuntoAtributo;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.HashMap;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map;
/*  16:    */ import javax.annotation.PostConstruct;
/*  17:    */ import javax.ejb.EJB;
/*  18:    */ import javax.faces.bean.ManagedBean;
/*  19:    */ import javax.faces.bean.ViewScoped;
/*  20:    */ import javax.faces.model.SelectItem;
/*  21:    */ import org.apache.log4j.Logger;
/*  22:    */ import org.primefaces.component.datatable.DataTable;
/*  23:    */ import org.primefaces.model.LazyDataModel;
/*  24:    */ import org.primefaces.model.SortOrder;
/*  25:    */ 
/*  26:    */ @ManagedBean
/*  27:    */ @ViewScoped
/*  28:    */ public class ConjuntoAtributoBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 2099368479844654064L;
/*  32:    */   @EJB
/*  33:    */   ServicioConjuntoAtributo servicioConjuntoAtributo;
/*  34:    */   @EJB
/*  35:    */   ServicioAtributo servicioAtributo;
/*  36:    */   private ConjuntoAtributo conjuntoAtributo;
/*  37:    */   private LazyDataModel<ConjuntoAtributo> listaConjuntoAtributo;
/*  38:    */   private List<SelectItem> conjuntoAtributoItems;
/*  39:    */   private DataTable dtConjuntoAtributo;
/*  40:    */   private DataTable dtAtributo;
/*  41:    */   private Atributo[] listaAtributosSeleccionados;
/*  42: 66 */   private List<Atributo> listaAtributosNoAsignados = new ArrayList();
/*  43:    */   
/*  44:    */   @PostConstruct
/*  45:    */   public void init()
/*  46:    */   {
/*  47: 70 */     this.listaConjuntoAtributo = new LazyDataModel()
/*  48:    */     {
/*  49:    */       private static final long serialVersionUID = 1L;
/*  50:    */       
/*  51:    */       public List<ConjuntoAtributo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  52:    */       {
/*  53: 77 */         List<ConjuntoAtributo> lista = new ArrayList();
/*  54: 78 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  55:    */         
/*  56: 80 */         lista = ConjuntoAtributoBean.this.servicioConjuntoAtributo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  57: 81 */         ConjuntoAtributoBean.this.listaConjuntoAtributo.setRowCount(ConjuntoAtributoBean.this.servicioConjuntoAtributo.contarPorCriterio(filters));
/*  58:    */         
/*  59: 83 */         return lista;
/*  60:    */       }
/*  61:    */     };
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String crearConjuntoAtributo()
/*  65:    */   {
/*  66: 89 */     this.conjuntoAtributo = new ConjuntoAtributo();
/*  67: 90 */     this.conjuntoAtributo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  68: 91 */     this.conjuntoAtributo.setIdSucursal(Integer.valueOf(AppUtil.getSucursal().getId()));
/*  69: 92 */     this.conjuntoAtributo.setActivo(true);
/*  70: 93 */     return "";
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String editar()
/*  74:    */   {
/*  75: 99 */     if ((this.conjuntoAtributo != null) && (this.conjuntoAtributo.getIdConjuntoAtributo() > 0))
/*  76:    */     {
/*  77:100 */       this.conjuntoAtributo = this.servicioConjuntoAtributo.cargarDetalle(getConjuntoAtributo().getId());
/*  78:101 */       setEditado(true);
/*  79:    */     }
/*  80:    */     else
/*  81:    */     {
/*  82:103 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  83:    */     }
/*  84:106 */     return "";
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String guardar()
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:116 */       if ((!this.conjuntoAtributo.isIndicadorCliente()) && (!this.conjuntoAtributo.isIndicadorProveedor()) && (!this.conjuntoAtributo.isIndicadorProducto()))
/*  92:    */       {
/*  93:117 */         addErrorMessage(getLanguageController().getMensaje("msg_error_seleccione_indicador"));
/*  94:118 */         return "";
/*  95:    */       }
/*  96:121 */       this.servicioConjuntoAtributo.guardar(this.conjuntoAtributo);
/*  97:122 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  98:123 */       limpiar();
/*  99:124 */       setEditado(false);
/* 100:    */     }
/* 101:    */     catch (Exception e)
/* 102:    */     {
/* 103:126 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 104:127 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 105:    */     }
/* 106:130 */     return "";
/* 107:    */   }
/* 108:    */   
/* 109:    */   public String eliminar()
/* 110:    */   {
/* 111:    */     try
/* 112:    */     {
/* 113:139 */       this.servicioConjuntoAtributo.eliminar(this.conjuntoAtributo);
/* 114:140 */       cargarDatos();
/* 115:141 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 116:    */     }
/* 117:    */     catch (Exception e)
/* 118:    */     {
/* 119:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 120:144 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 121:    */     }
/* 122:147 */     return "";
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String cargarDatos()
/* 126:    */   {
/* 127:155 */     return "";
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String agregarAtributos()
/* 131:    */   {
/* 132:165 */     if (this.listaAtributosSeleccionados != null) {
/* 133:166 */       for (Atributo atributo : this.listaAtributosSeleccionados) {
/* 134:167 */         getConjuntoAtributo().getListaAtributo().add(atributo);
/* 135:    */       }
/* 136:    */     }
/* 137:171 */     return "";
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String cargarAtributosNoAsignados()
/* 141:    */   {
/* 142:181 */     this.listaAtributosSeleccionados = null;
/* 143:182 */     HashMap<Integer, Atributo> atributos = new HashMap();
/* 144:184 */     for (Atributo atributo : this.servicioAtributo.obtenerListaComboPorIndicador(AppUtil.getOrganizacion().getId(), this.conjuntoAtributo
/* 145:185 */       .isIndicadorProducto(), this.conjuntoAtributo.isIndicadorCliente(), this.conjuntoAtributo.isIndicadorProveedor())) {
/* 146:186 */       atributos.put(Integer.valueOf(atributo.getId()), atributo);
/* 147:    */     }
/* 148:189 */     for (Atributo atributo : getConjuntoAtributo().getListaAtributo()) {
/* 149:190 */       atributos.remove(Integer.valueOf(atributo.getId()));
/* 150:    */     }
/* 151:193 */     this.listaAtributosNoAsignados = new ArrayList(atributos.values());
/* 152:    */     
/* 153:195 */     return "";
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String eliminarAtributo()
/* 157:    */   {
/* 158:205 */     Atributo atributo = (Atributo)this.dtAtributo.getRowData();
/* 159:206 */     atributo.setEliminado(true);
/* 160:    */     
/* 161:208 */     getConjuntoAtributo().getListaAtributo().remove(atributo);
/* 162:    */     
/* 163:210 */     return "";
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String limpiar()
/* 167:    */   {
/* 168:218 */     crearConjuntoAtributo();
/* 169:219 */     return "";
/* 170:    */   }
/* 171:    */   
/* 172:    */   public ServicioConjuntoAtributo getServicioConjuntoAtributoBean()
/* 173:    */   {
/* 174:223 */     return this.servicioConjuntoAtributo;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setServicioConjuntoAtributoBean(ServicioConjuntoAtributo servicioConjuntoAtributo)
/* 178:    */   {
/* 179:227 */     this.servicioConjuntoAtributo = servicioConjuntoAtributo;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public ConjuntoAtributo getConjuntoAtributo()
/* 183:    */   {
/* 184:231 */     if (this.conjuntoAtributo == null) {
/* 185:232 */       this.conjuntoAtributo = new ConjuntoAtributo();
/* 186:    */     }
/* 187:235 */     return this.conjuntoAtributo;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setConjuntoAtributo(ConjuntoAtributo conjuntoAtributo)
/* 191:    */   {
/* 192:239 */     this.conjuntoAtributo = conjuntoAtributo;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setConjuntoAtributoItems(List<SelectItem> conjuntoAtributoItems)
/* 196:    */   {
/* 197:243 */     this.conjuntoAtributoItems = conjuntoAtributoItems;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public List<SelectItem> getConjuntoAtributoItems()
/* 201:    */   {
/* 202:252 */     return this.conjuntoAtributoItems;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public DataTable getDtConjuntoAtributo()
/* 206:    */   {
/* 207:261 */     return this.dtConjuntoAtributo;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setDtConjuntoAtributo(DataTable dtConjuntoAtributo)
/* 211:    */   {
/* 212:271 */     this.dtConjuntoAtributo = dtConjuntoAtributo;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public DataTable getDtAtributo()
/* 216:    */   {
/* 217:280 */     return this.dtAtributo;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setDtAtributo(DataTable dtAtributo)
/* 221:    */   {
/* 222:290 */     this.dtAtributo = dtAtributo;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public List<Atributo> getListaAtributo()
/* 226:    */   {
/* 227:300 */     List<Atributo> lista = new ArrayList();
/* 228:302 */     for (Atributo atributo : getConjuntoAtributo().getListaAtributo()) {
/* 229:303 */       if (!atributo.isEliminado()) {
/* 230:304 */         lista.add(atributo);
/* 231:    */       }
/* 232:    */     }
/* 233:308 */     return lista;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Atributo[] getListaAtributosSeleccionados()
/* 237:    */   {
/* 238:317 */     return this.listaAtributosSeleccionados;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setListaAtributosSeleccionados(Atributo[] listaAtributosSeleccionados)
/* 242:    */   {
/* 243:327 */     this.listaAtributosSeleccionados = listaAtributosSeleccionados;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public List<Atributo> getListaAtributosNoAsignados()
/* 247:    */   {
/* 248:336 */     return this.listaAtributosNoAsignados;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setListaAtributosNoAsignados(List<Atributo> listaAtributosNoAsignados)
/* 252:    */   {
/* 253:346 */     this.listaAtributosNoAsignados = listaAtributosNoAsignados;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public LazyDataModel<ConjuntoAtributo> getListaConjuntoAtributo()
/* 257:    */   {
/* 258:353 */     return this.listaConjuntoAtributo;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setListaConjuntoAtributo(LazyDataModel<ConjuntoAtributo> listaConjuntoAtributo)
/* 262:    */   {
/* 263:361 */     this.listaConjuntoAtributo = listaConjuntoAtributo;
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.ConjuntoAtributoBean
 * JD-Core Version:    0.7.0.1
 */