/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CargaEmpleado;
/*   6:    */ import com.asinfo.as2.entities.Empleado;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.TipoDiscapacidad;
/*  10:    */ import com.asinfo.as2.enumeraciones.Genero;
/*  11:    */ import com.asinfo.as2.enumeraciones.Parentezco;
/*  12:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioCargaEmpleado;
/*  13:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*  14:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioTipoDiscapacidad;
/*  15:    */ import com.asinfo.as2.util.AppUtil;
/*  16:    */ import java.util.ArrayList;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import javax.annotation.PostConstruct;
/*  20:    */ import javax.ejb.EJB;
/*  21:    */ import javax.faces.bean.ManagedBean;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import javax.faces.model.SelectItem;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.model.LazyDataModel;
/*  27:    */ import org.primefaces.model.SortOrder;
/*  28:    */ 
/*  29:    */ @ManagedBean
/*  30:    */ @ViewScoped
/*  31:    */ public class CargaEmpleadoBean
/*  32:    */   extends PageControllerAS2
/*  33:    */ {
/*  34:    */   private static final long serialVersionUID = -6642782572366933215L;
/*  35:    */   @EJB
/*  36:    */   private ServicioCargaEmpleado servicioCargaEmpleado;
/*  37:    */   @EJB
/*  38:    */   private ServicioTipoDiscapacidad servicioTipoDiscapacidad;
/*  39:    */   @EJB
/*  40:    */   private ServicioEmpleado servicioEmpleado;
/*  41:    */   private Empleado empleado;
/*  42:    */   private CargaEmpleado cargaEmpleado;
/*  43:    */   private LazyDataModel<CargaEmpleado> listaCargaEmpleado;
/*  44:    */   private List<SelectItem> listaGenero;
/*  45:    */   private List<SelectItem> listaParentezco;
/*  46:    */   private List<TipoDiscapacidad> listaTipoDiscapacidad;
/*  47:    */   private DataTable dtEmpleado;
/*  48:    */   private DataTable dtCargaEmpleado;
/*  49:    */   
/*  50:    */   @PostConstruct
/*  51:    */   public void init()
/*  52:    */   {
/*  53: 89 */     this.listaCargaEmpleado = new LazyDataModel()
/*  54:    */     {
/*  55:    */       private static final long serialVersionUID = -1752987002238164010L;
/*  56:    */       
/*  57:    */       public List<CargaEmpleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  58:    */       {
/*  59: 99 */         List<CargaEmpleado> lista = new ArrayList();
/*  60:100 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  61:    */         
/*  62:102 */         lista = CargaEmpleadoBean.this.servicioCargaEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  63:103 */         CargaEmpleadoBean.this.listaCargaEmpleado.setRowCount(CargaEmpleadoBean.this.servicioCargaEmpleado.contarPorCriterio(filters));
/*  64:    */         
/*  65:105 */         return lista;
/*  66:    */       }
/*  67:    */     };
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String crearCargaEmpleado()
/*  71:    */   {
/*  72:118 */     this.empleado = new Empleado();
/*  73:119 */     this.empleado.setListaCargaEmpleado(new ArrayList());
/*  74:    */     
/*  75:121 */     this.cargaEmpleado = new CargaEmpleado();
/*  76:122 */     this.cargaEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  77:123 */     this.cargaEmpleado.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  78:124 */     return "";
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String editar()
/*  82:    */   {
/*  83:133 */     if ((getCargaEmpleado() != null) && (getCargaEmpleado().getEmpleado().getId() != 0))
/*  84:    */     {
/*  85:134 */       setEmpleado(this.servicioEmpleado.cargarDetalle(getCargaEmpleado().getEmpleado().getId()));
/*  86:135 */       setEditado(true);
/*  87:    */     }
/*  88:    */     else
/*  89:    */     {
/*  90:137 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  91:    */     }
/*  92:139 */     return "";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public String guardar()
/*  96:    */   {
/*  97:    */     try
/*  98:    */     {
/*  99:149 */       this.servicioCargaEmpleado.guardar(getEmpleado());
/* 100:150 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 101:151 */       limpiar();
/* 102:152 */       setEditado(false);
/* 103:    */     }
/* 104:    */     catch (Exception e)
/* 105:    */     {
/* 106:154 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 107:155 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 108:    */     }
/* 109:157 */     return "";
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String eliminar()
/* 113:    */   {
/* 114:    */     try
/* 115:    */     {
/* 116:167 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 117:    */     }
/* 118:    */     catch (Exception e)
/* 119:    */     {
/* 120:169 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 121:170 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 122:    */     }
/* 123:172 */     return "";
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String cargarDatos()
/* 127:    */   {
/* 128:181 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String limpiar()
/* 132:    */   {
/* 133:190 */     crearCargaEmpleado();
/* 134:191 */     return "";
/* 135:    */   }
/* 136:    */   
/* 137:    */   public List<CargaEmpleado> getListaCargasEmpleado()
/* 138:    */   {
/* 139:195 */     List<CargaEmpleado> detalle = new ArrayList();
/* 140:196 */     if (getEmpleado() != null) {
/* 141:197 */       for (CargaEmpleado dmc : getEmpleado().getListaCargaEmpleado()) {
/* 142:198 */         if (!dmc.isEliminado()) {
/* 143:199 */           detalle.add(dmc);
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:204 */     return detalle;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<SelectItem> getListaGenero()
/* 151:    */   {
/* 152:213 */     if (this.listaGenero == null)
/* 153:    */     {
/* 154:214 */       this.listaGenero = new ArrayList();
/* 155:215 */       for (Genero genero : Genero.values())
/* 156:    */       {
/* 157:216 */         SelectItem item = new SelectItem(genero, genero.getNombre());
/* 158:217 */         this.listaGenero.add(item);
/* 159:    */       }
/* 160:    */     }
/* 161:220 */     return this.listaGenero;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public List<SelectItem> getlistaParentezco()
/* 165:    */   {
/* 166:229 */     if (this.listaParentezco == null)
/* 167:    */     {
/* 168:230 */       this.listaParentezco = new ArrayList();
/* 169:231 */       for (Parentezco parentezco : Parentezco.values())
/* 170:    */       {
/* 171:232 */         SelectItem item = new SelectItem(parentezco, parentezco.getNombre());
/* 172:233 */         this.listaParentezco.add(item);
/* 173:    */       }
/* 174:    */     }
/* 175:236 */     return this.listaParentezco;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public String agregarDetalle()
/* 179:    */   {
/* 180:250 */     if (getEmpleado() != null)
/* 181:    */     {
/* 182:251 */       CargaEmpleado cargaEmpleado = new CargaEmpleado();
/* 183:    */       
/* 184:    */ 
/* 185:254 */       cargaEmpleado.setEmpleado(getEmpleado());
/* 186:255 */       cargaEmpleado.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 187:256 */       cargaEmpleado.setIdSucursal(AppUtil.getSucursal().getId());
/* 188:257 */       cargaEmpleado.setActivo(true);
/* 189:258 */       cargaEmpleado.setTipoDiscapacidad(new TipoDiscapacidad());
/* 190:259 */       getEmpleado().getListaCargaEmpleado().add(cargaEmpleado);
/* 191:    */     }
/* 192:262 */     return "";
/* 193:    */   }
/* 194:    */   
/* 195:    */   public String eliminarDetalle()
/* 196:    */   {
/* 197:271 */     CargaEmpleado cargaEmpleado = (CargaEmpleado)this.dtCargaEmpleado.getRowData();
/* 198:272 */     cargaEmpleado.setEliminado(true);
/* 199:273 */     return "";
/* 200:    */   }
/* 201:    */   
/* 202:    */   public String cargarEmpleado()
/* 203:    */   {
/* 204:277 */     Empleado empleadoConDetalle = this.servicioEmpleado.cargarDetalle(this.empleado.getIdEmpleado());
/* 205:278 */     setEmpleado(empleadoConDetalle);
/* 206:279 */     this.cargaEmpleado.setEmpleado(getEmpleado());
/* 207:280 */     return "";
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List<TipoDiscapacidad> autocompletarTipoDiscapacidad(String consulta)
/* 211:    */   {
/* 212:291 */     consulta = consulta.toUpperCase();
/* 213:    */     
/* 214:293 */     List<TipoDiscapacidad> lista = new ArrayList();
/* 215:295 */     for (TipoDiscapacidad tipoDiscapacidad : getListaTipoDiscapacidad()) {
/* 216:297 */       if ((tipoDiscapacidad.getCodigo().toUpperCase().contains(consulta)) || (tipoDiscapacidad.getNombre().toUpperCase().contains(consulta))) {
/* 217:298 */         lista.add(tipoDiscapacidad);
/* 218:    */       }
/* 219:    */     }
/* 220:302 */     return lista;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public void activarCargaEmpleado()
/* 224:    */   {
/* 225:309 */     CargaEmpleado cargaEmpleado = (CargaEmpleado)this.dtEmpleado.getRowData();
/* 226:310 */     this.servicioCargaEmpleado.actualizaIndicadorActivo(cargaEmpleado.getIdCargaEmpleado(), true);
/* 227:311 */     actualizarEmpleado(cargaEmpleado);
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void desactivarCargaEmpleado()
/* 231:    */   {
/* 232:315 */     CargaEmpleado cargaEmpleado = (CargaEmpleado)this.dtEmpleado.getRowData();
/* 233:316 */     this.servicioCargaEmpleado.actualizaIndicadorActivo(cargaEmpleado.getIdCargaEmpleado(), false);
/* 234:317 */     actualizarEmpleado(cargaEmpleado);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void actualizarEmpleado(CargaEmpleado cargaEmpleado)
/* 238:    */   {
/* 239:322 */     Empleado emp = this.servicioEmpleado.getEmpleadoPorCargaEmpleado(cargaEmpleado.getIdCargaEmpleado());
/* 240:323 */     emp = this.servicioEmpleado.cargarDetalle(emp.getIdEmpleado());
/* 241:324 */     setEmpleado(emp);
/* 242:325 */     cargaEmpleado.setEmpleado(getEmpleado());
/* 243:326 */     guardar();
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Empleado getEmpleado()
/* 247:    */   {
/* 248:341 */     return this.empleado;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setEmpleado(Empleado empleado)
/* 252:    */   {
/* 253:351 */     this.empleado = empleado;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public DataTable getDtEmpleado()
/* 257:    */   {
/* 258:360 */     return this.dtEmpleado;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setDtEmpleado(DataTable dtEmpleado)
/* 262:    */   {
/* 263:370 */     this.dtEmpleado = dtEmpleado;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public List<TipoDiscapacidad> getListaTipoDiscapacidad()
/* 267:    */   {
/* 268:379 */     if (this.listaTipoDiscapacidad == null) {
/* 269:380 */       this.listaTipoDiscapacidad = this.servicioTipoDiscapacidad.obtenerListaCombo("nombre", true, null);
/* 270:    */     }
/* 271:382 */     return this.listaTipoDiscapacidad;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public DataTable getDtCargaEmpleado()
/* 275:    */   {
/* 276:391 */     return this.dtCargaEmpleado;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setDtCargaEmpleado(DataTable dtCargaEmpleado)
/* 280:    */   {
/* 281:401 */     this.dtCargaEmpleado = dtCargaEmpleado;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public CargaEmpleado getCargaEmpleado()
/* 285:    */   {
/* 286:410 */     return this.cargaEmpleado;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public void setCargaEmpleado(CargaEmpleado cargaEmpleado)
/* 290:    */   {
/* 291:420 */     this.cargaEmpleado = cargaEmpleado;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public LazyDataModel<CargaEmpleado> getListaCargaEmpleado()
/* 295:    */   {
/* 296:429 */     return this.listaCargaEmpleado;
/* 297:    */   }
/* 298:    */   
/* 299:    */   public void setListaCargaEmpleado(LazyDataModel<CargaEmpleado> listaCargaEmpleado)
/* 300:    */   {
/* 301:439 */     this.listaCargaEmpleado = listaCargaEmpleado;
/* 302:    */   }
/* 303:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.CargaEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */