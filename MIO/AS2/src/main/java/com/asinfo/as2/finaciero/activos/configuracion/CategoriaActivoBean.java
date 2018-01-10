/*   1:    */ package com.asinfo.as2.finaciero.activos.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*   6:    */ import com.asinfo.as2.entities.CuentaContable;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*   9:    */ import com.asinfo.as2.entities.Sucursal;
/*  10:    */ import com.asinfo.as2.financiero.activos.configuracion.servicio.ServicioCategoriaActivo;
/*  11:    */ import com.asinfo.as2.util.AppUtil;
/*  12:    */ import java.util.ArrayList;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ import org.primefaces.model.SortOrder;
/*  23:    */ 
/*  24:    */ @ManagedBean
/*  25:    */ @ViewScoped
/*  26:    */ public class CategoriaActivoBean
/*  27:    */   extends PageControllerAS2
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -2677344347806549267L;
/*  30:    */   @EJB
/*  31:    */   private ServicioCategoriaActivo servicioCategoriaActivo;
/*  32:    */   private CategoriaActivo categoriaActivo;
/*  33:    */   private enumCuentaContableEditada cuentaContableEditada;
/*  34:    */   private CuentaContable cuentaContable;
/*  35:    */   private LazyDataModel<CategoriaActivo> listaCategoriaActivo;
/*  36:    */   private DataTable dtCuentaContable;
/*  37:    */   private DataTable dtCategoriaActivo;
/*  38:    */   private DataTable dtSubcategoriaActivo;
/*  39:    */   
/*  40:    */   private static enum enumCuentaContableEditada
/*  41:    */   {
/*  42: 63 */     CUENTA_CONTABLE_ACTIVO_FIJO,  CUENTA_CONTABLE_DEPRECIACION,  CUENTA_CONTABLE_DEPRECIACION_ACUMULADA,  CUENTA_CONTABLE_ACTIVO_FIJO_FISCAL,  CUENTA_CONTABLE_DEPRECIACION_FISCAL,  CUENTA_CONTABLE_DEPRECIACION_ACUMULADA_FISCAL,  CUENTA_CONTABLE_SUPERVIT_POR_REVALORIZACION,  CUENTA_CONTABLE_DEFICIT_POR_REVALORIZACION;
/*  43:    */     
/*  44:    */     private enumCuentaContableEditada() {}
/*  45:    */   }
/*  46:    */   
/*  47:    */   @PostConstruct
/*  48:    */   public void init()
/*  49:    */   {
/*  50: 87 */     this.listaCategoriaActivo = new LazyDataModel()
/*  51:    */     {
/*  52:    */       private static final long serialVersionUID = 1L;
/*  53:    */       
/*  54:    */       public List<CategoriaActivo> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  55:    */       {
/*  56: 99 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  57:    */         
/*  58:101 */         List<CategoriaActivo> lista = CategoriaActivoBean.this.servicioCategoriaActivo.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  59:    */         
/*  60:103 */         CategoriaActivoBean.this.listaCategoriaActivo.setRowCount(CategoriaActivoBean.this.servicioCategoriaActivo.contarPorCriterio(filters));
/*  61:    */         
/*  62:105 */         return lista;
/*  63:    */       }
/*  64:    */     };
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void crearCategoriaActivo()
/*  68:    */   {
/*  69:119 */     this.categoriaActivo = new CategoriaActivo();
/*  70:120 */     this.categoriaActivo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  71:121 */     this.categoriaActivo.setIdSucursal(AppUtil.getSucursal().getId());
/*  72:122 */     this.categoriaActivo.setActivo(true);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String editar()
/*  76:    */   {
/*  77:131 */     if (getCategoriaActivo().getIdCategoriaActivo() > 0)
/*  78:    */     {
/*  79:132 */       this.categoriaActivo = this.servicioCategoriaActivo.cargarDetalle(getCategoriaActivo().getId());
/*  80:133 */       setEditado(true);
/*  81:    */     }
/*  82:    */     else
/*  83:    */     {
/*  84:135 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  85:    */     }
/*  86:137 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String guardar()
/*  90:    */   {
/*  91:    */     try
/*  92:    */     {
/*  93:147 */       this.servicioCategoriaActivo.guardar(this.categoriaActivo);
/*  94:148 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  95:149 */       setEditado(false);
/*  96:150 */       limpiar();
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:152 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 101:153 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 102:    */     }
/* 103:155 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String eliminar()
/* 107:    */   {
/* 108:    */     try
/* 109:    */     {
/* 110:165 */       this.servicioCategoriaActivo.eliminar(this.categoriaActivo);
/* 111:166 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 112:    */     }
/* 113:    */     catch (Exception e)
/* 114:    */     {
/* 115:168 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 116:169 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 117:    */     }
/* 118:171 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String cargarDatos()
/* 122:    */   {
/* 123:180 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String limpiar()
/* 127:    */   {
/* 128:189 */     crearCategoriaActivo();
/* 129:190 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void cargarCuentaContable()
/* 133:    */   {
/* 134:198 */     this.cuentaContable = ((CuentaContable)this.dtCuentaContable.getRowData());
/* 135:200 */     switch (2.$SwitchMap$com$asinfo$as2$finaciero$activos$configuracion$CategoriaActivoBean$enumCuentaContableEditada[this.cuentaContableEditada.ordinal()])
/* 136:    */     {
/* 137:    */     case 1: 
/* 138:202 */       this.categoriaActivo.setCuentaContableActivoFijo(this.cuentaContable);
/* 139:203 */       break;
/* 140:    */     case 2: 
/* 141:206 */       this.categoriaActivo.setCuentaContableDepreciacion(this.cuentaContable);
/* 142:207 */       break;
/* 143:    */     case 3: 
/* 144:210 */       this.categoriaActivo.setCuentaContableDepreciacionAcumulada(this.cuentaContable);
/* 145:211 */       break;
/* 146:    */     case 4: 
/* 147:214 */       this.categoriaActivo.setCuentaContableSuperavitPorRevalorizacion(this.cuentaContable);
/* 148:215 */       break;
/* 149:    */     case 5: 
/* 150:218 */       this.categoriaActivo.setCuentaContableDeDeficitPorRevalorizacion(this.cuentaContable);
/* 151:219 */       break;
/* 152:    */     }
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void actualizarCuentaContableActivoFijo()
/* 156:    */   {
/* 157:227 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_ACTIVO_FIJO;
/* 158:228 */     this.cuentaContable = this.categoriaActivo.getCuentaContableActivoFijo();
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void actualizarCuentaContableDepreciacion()
/* 162:    */   {
/* 163:232 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_DEPRECIACION;
/* 164:233 */     this.cuentaContable = this.categoriaActivo.getCuentaContableDepreciacion();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void actualizarCuentaContableDepreciacionAcumulada()
/* 168:    */   {
/* 169:237 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_DEPRECIACION_ACUMULADA;
/* 170:238 */     this.cuentaContable = this.categoriaActivo.getCuentaContableDepreciacionAcumulada();
/* 171:    */   }
/* 172:    */   
/* 173:    */   public void actualizarCuentaContableSuperavitPorRevalorizacion()
/* 174:    */   {
/* 175:242 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_SUPERVIT_POR_REVALORIZACION;
/* 176:243 */     this.cuentaContable = this.categoriaActivo.getCuentaContableSuperavitPorRevalorizacion();
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void actualizarCuentaContableDeDeficitPorRevalorizacion()
/* 180:    */   {
/* 181:247 */     this.cuentaContableEditada = enumCuentaContableEditada.CUENTA_CONTABLE_DEFICIT_POR_REVALORIZACION;
/* 182:248 */     this.cuentaContable = this.categoriaActivo.getCuentaContableDeDeficitPorRevalorizacion();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String agregarSubcategoriaActivo()
/* 186:    */   {
/* 187:257 */     SubcategoriaActivo subcategoriaActivo = new SubcategoriaActivo();
/* 188:258 */     subcategoriaActivo.setCategoriaActivo(this.categoriaActivo);
/* 189:259 */     subcategoriaActivo.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/* 190:260 */     subcategoriaActivo.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/* 191:261 */     subcategoriaActivo.setActivo(true);
/* 192:262 */     getCategoriaActivo().getListaSubcategoriaActivo().add(subcategoriaActivo);
/* 193:    */     
/* 194:264 */     return "";
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String eliminarSubcategoriaActivo()
/* 198:    */   {
/* 199:268 */     SubcategoriaActivo subcategoriaActivo = (SubcategoriaActivo)this.dtSubcategoriaActivo.getRowData();
/* 200:269 */     subcategoriaActivo.setEliminado(true);
/* 201:270 */     return "";
/* 202:    */   }
/* 203:    */   
/* 204:    */   public CategoriaActivo getCategoriaActivo()
/* 205:    */   {
/* 206:288 */     if (this.categoriaActivo == null) {
/* 207:289 */       crearCategoriaActivo();
/* 208:    */     }
/* 209:291 */     return this.categoriaActivo;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setCategoriaActivo(CategoriaActivo categoriaActivo)
/* 213:    */   {
/* 214:301 */     this.categoriaActivo = categoriaActivo;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public LazyDataModel<CategoriaActivo> getListaCategoriaActivo()
/* 218:    */   {
/* 219:310 */     return this.listaCategoriaActivo;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setListaCategoriaActivo(LazyDataModel<CategoriaActivo> listaCategoriaActivo)
/* 223:    */   {
/* 224:320 */     this.listaCategoriaActivo = listaCategoriaActivo;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public DataTable getDtCategoriaActivo()
/* 228:    */   {
/* 229:329 */     return this.dtCategoriaActivo;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setDtCategoriaActivo(DataTable dtCategoriaActivo)
/* 233:    */   {
/* 234:339 */     this.dtCategoriaActivo = dtCategoriaActivo;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public enumCuentaContableEditada getCuentaContableEditada()
/* 238:    */   {
/* 239:348 */     return this.cuentaContableEditada;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void setCuentaContableEditada(enumCuentaContableEditada cuentaContableEditada)
/* 243:    */   {
/* 244:358 */     this.cuentaContableEditada = cuentaContableEditada;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public CuentaContable getCuentaContable()
/* 248:    */   {
/* 249:367 */     return this.cuentaContable;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 253:    */   {
/* 254:377 */     this.cuentaContable = cuentaContable;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public DataTable getDtCuentaContable()
/* 258:    */   {
/* 259:386 */     return this.dtCuentaContable;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 263:    */   {
/* 264:396 */     this.dtCuentaContable = dtCuentaContable;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public List<SubcategoriaActivo> getListaSubcategoriaActivo()
/* 268:    */   {
/* 269:405 */     List<SubcategoriaActivo> lista = new ArrayList();
/* 270:406 */     for (SubcategoriaActivo subcategoriaActivo : getCategoriaActivo().getListaSubcategoriaActivo()) {
/* 271:407 */       if (!subcategoriaActivo.isEliminado()) {
/* 272:408 */         lista.add(subcategoriaActivo);
/* 273:    */       }
/* 274:    */     }
/* 275:412 */     return lista;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public DataTable getDtSubcategoriaActivo()
/* 279:    */   {
/* 280:421 */     return this.dtSubcategoriaActivo;
/* 281:    */   }
/* 282:    */   
/* 283:    */   public void setDtSubcategoriaActivo(DataTable dtSubcategoriaActivo)
/* 284:    */   {
/* 285:431 */     this.dtSubcategoriaActivo = dtSubcategoriaActivo;
/* 286:    */   }
/* 287:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.activos.configuracion.CategoriaActivoBean
 * JD-Core Version:    0.7.0.1
 */