/*   1:    */ package com.asinfo.as2.finaciero.presupuesto.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.entities.presupuesto.NivelPartidaPresupuestaria;
/*   9:    */ import com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria;
/*  10:    */ import com.asinfo.as2.enumeraciones.GrupoCuenta;
/*  11:    */ import com.asinfo.as2.finaciero.contabilidad.configuracion.controller.ListaCuentaContableBean;
/*  12:    */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioNivelPartidaPresupuestaria;
/*  13:    */ import com.asinfo.as2.financiero.presupuesto.configuracion.servicio.ServicioPartidaPresupuestaria;
/*  14:    */ import com.asinfo.as2.util.AppUtil;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.annotation.PostConstruct;
/*  19:    */ import javax.ejb.EJB;
/*  20:    */ import javax.faces.bean.ManagedBean;
/*  21:    */ import javax.faces.bean.ManagedProperty;
/*  22:    */ import javax.faces.bean.ViewScoped;
/*  23:    */ import javax.faces.model.SelectItem;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.primefaces.component.datatable.DataTable;
/*  26:    */ import org.primefaces.event.SelectEvent;
/*  27:    */ import org.primefaces.model.LazyDataModel;
/*  28:    */ import org.primefaces.model.SortOrder;
/*  29:    */ 
/*  30:    */ @ManagedBean
/*  31:    */ @ViewScoped
/*  32:    */ public class PartidaPresupuestariaBean
/*  33:    */   extends PageControllerAS2
/*  34:    */ {
/*  35:    */   private static final long serialVersionUID = -9164828061077758215L;
/*  36:    */   @EJB
/*  37:    */   private transient ServicioPartidaPresupuestaria servicioPartidaPresupuestaria;
/*  38:    */   @EJB
/*  39:    */   private transient ServicioNivelPartidaPresupuestaria servicioNivelPartidaPresupuestaria;
/*  40:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  41:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  42:    */   private PartidaPresupuestaria partidaPresupuestaria;
/*  43:    */   public List<SelectItem> listaGrupoPartidaPresupuestaria;
/*  44:    */   private List<NivelPartidaPresupuestaria> listaNivelPartidaPresupuestaria;
/*  45:    */   private List<PartidaPresupuestaria> listaPartidaPresupuestariaPadre;
/*  46:    */   private CuentaContable cuentaContable;
/*  47:    */   private CuentaContable cuentaContableSeleccionada;
/*  48:    */   private boolean indicadorMovimiento;
/*  49:    */   private LazyDataModel<PartidaPresupuestaria> listaPartidaPresupuestaria;
/*  50:    */   private DataTable dtPartidaPresupuestaria;
/*  51:    */   private DataTable dtCuentaContablePresupuestaria;
/*  52:    */   private DataTable dtCuentaContable;
/*  53:    */   
/*  54:    */   @PostConstruct
/*  55:    */   public void init()
/*  56:    */   {
/*  57: 93 */     this.listaPartidaPresupuestaria = new LazyDataModel()
/*  58:    */     {
/*  59:    */       private static final long serialVersionUID = 1312949801168865877L;
/*  60:    */       
/*  61:    */       public List<PartidaPresupuestaria> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  62:    */       {
/*  63:102 */         List<PartidaPresupuestaria> lista = new ArrayList();
/*  64:    */         
/*  65:104 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  66:    */         
/*  67:106 */         lista = PartidaPresupuestariaBean.this.servicioPartidaPresupuestaria.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  68:    */         
/*  69:108 */         PartidaPresupuestariaBean.this.listaPartidaPresupuestaria.setRowCount(PartidaPresupuestariaBean.this.servicioPartidaPresupuestaria.contarPorCriterio(filters));
/*  70:    */         
/*  71:110 */         return lista;
/*  72:    */       }
/*  73:    */     };
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void crearPartidaPresupuestaria()
/*  77:    */   {
/*  78:124 */     this.partidaPresupuestaria = new PartidaPresupuestaria();
/*  79:125 */     this.partidaPresupuestaria.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  80:126 */     this.partidaPresupuestaria.setIdSucursal(AppUtil.getSucursal().getId());
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String editar()
/*  84:    */   {
/*  85:135 */     if (getPartidaPresupuestaria().getIdPartidaPresupuestaria() > 0)
/*  86:    */     {
/*  87:136 */       this.partidaPresupuestaria = this.servicioPartidaPresupuestaria.cargarDetalle(this.partidaPresupuestaria.getId());
/*  88:137 */       cargarPartidaPresupuestariaPadre();
/*  89:138 */       setEditado(true);
/*  90:    */     }
/*  91:    */     else
/*  92:    */     {
/*  93:140 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  94:    */     }
/*  95:142 */     return "";
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String guardar()
/*  99:    */   {
/* 100:    */     try
/* 101:    */     {
/* 102:152 */       this.servicioPartidaPresupuestaria.guardar(this.partidaPresupuestaria);
/* 103:153 */       limpiar();
/* 104:154 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 105:155 */       setEditado(false);
/* 106:    */     }
/* 107:    */     catch (Exception e)
/* 108:    */     {
/* 109:157 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 110:158 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 111:    */     }
/* 112:160 */     return "";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public String eliminar()
/* 116:    */   {
/* 117:    */     try
/* 118:    */     {
/* 119:170 */       this.servicioPartidaPresupuestaria.eliminar(this.partidaPresupuestaria);
/* 120:171 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 121:172 */       limpiar();
/* 122:    */     }
/* 123:    */     catch (Exception e)
/* 124:    */     {
/* 125:174 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 126:175 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 127:    */     }
/* 128:177 */     return "";
/* 129:    */   }
/* 130:    */   
/* 131:    */   public String cargarDatos()
/* 132:    */   {
/* 133:186 */     return "";
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String limpiar()
/* 137:    */   {
/* 138:195 */     crearPartidaPresupuestaria();
/* 139:196 */     return "";
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void cargarPartidaPresupuestariaPadre()
/* 143:    */   {
/* 144:204 */     this.listaPartidaPresupuestariaPadre = new ArrayList();
/* 145:205 */     List<PartidaPresupuestaria> lista = this.servicioPartidaPresupuestaria.buscarPorGrupoNivelPartidaPresupuestaria(this.partidaPresupuestaria
/* 146:206 */       .getGrupoPartidaPresupuestaria(), this.partidaPresupuestaria.getNivelPartidaPresupuestaria().getCodigo() - 1, AppUtil.getOrganizacion()
/* 147:207 */       .getId());
/* 148:208 */     for (PartidaPresupuestaria partidaPresupuestariaPadre : lista) {
/* 149:209 */       this.listaPartidaPresupuestariaPadre.add(partidaPresupuestariaPadre);
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public String obtenerMascara()
/* 154:    */   {
/* 155:221 */     String mascara = this.servicioNivelPartidaPresupuestaria.getMascara(this.partidaPresupuestaria.getPartidaPresupuestariaPadre()
/* 156:222 */       .getIdPartidaPresupuestaria(), this.partidaPresupuestaria.getNivelPartidaPresupuestaria().getIdNivelPartidaPresupuestaria());
/* 157:223 */     this.partidaPresupuestaria.setMascara(mascara);
/* 158:224 */     return "";
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String seleccionarCuentaContable(SelectEvent event)
/* 162:    */   {
/* 163:234 */     this.cuentaContable = ((CuentaContable)event.getObject());
/* 164:236 */     if (this.cuentaContable.getPartidaPresupuestaria() == null)
/* 165:    */     {
/* 166:237 */       this.cuentaContable.setPartidaPresupuestaria(getPartidaPresupuestaria());
/* 167:238 */       getPartidaPresupuestaria().getListaCuentaContable().add(this.cuentaContable);
/* 168:    */     }
/* 169:241 */     return "";
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String eliminarDetalle()
/* 173:    */   {
/* 174:250 */     this.cuentaContableSeleccionada = ((CuentaContable)this.dtCuentaContablePresupuestaria.getRowData());
/* 175:251 */     this.cuentaContableSeleccionada.setEliminado(true);
/* 176:252 */     this.cuentaContableSeleccionada.setPartidaPresupuestaria(null);
/* 177:253 */     return "";
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setIndicadorMovimientoPartidaPresupuestaria()
/* 181:    */   {
/* 182:258 */     if (!isIndicadorMovimiento()) {
/* 183:259 */       for (CuentaContable cuentaContable : getPartidaPresupuestaria().getListaCuentaContable()) {
/* 184:260 */         cuentaContable.setEliminado(true);
/* 185:    */       }
/* 186:    */     }
/* 187:263 */     getPartidaPresupuestaria().setIndicadorMovimiento(isIndicadorMovimiento());
/* 188:    */   }
/* 189:    */   
/* 190:    */   public PartidaPresupuestaria getPartidaPresupuestaria()
/* 191:    */   {
/* 192:277 */     if (this.partidaPresupuestaria == null) {
/* 193:278 */       crearPartidaPresupuestaria();
/* 194:    */     }
/* 195:280 */     return this.partidaPresupuestaria;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setPartidaPresupuestaria(PartidaPresupuestaria partidaPresupuestaria)
/* 199:    */   {
/* 200:290 */     this.partidaPresupuestaria = partidaPresupuestaria;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public List<SelectItem> getListaGrupoPartidaPresupuestaria()
/* 204:    */   {
/* 205:299 */     if (this.listaGrupoPartidaPresupuestaria == null)
/* 206:    */     {
/* 207:300 */       this.listaGrupoPartidaPresupuestaria = new ArrayList();
/* 208:301 */       for (GrupoCuenta g : GrupoCuenta.values())
/* 209:    */       {
/* 210:302 */         SelectItem item = new SelectItem(g, g.getNombre());
/* 211:303 */         this.listaGrupoPartidaPresupuestaria.add(item);
/* 212:    */       }
/* 213:    */     }
/* 214:306 */     return this.listaGrupoPartidaPresupuestaria;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setListaGrupoPartidaPresupuestaria(List<SelectItem> listaGrupoPartidaPresupuestaria)
/* 218:    */   {
/* 219:316 */     this.listaGrupoPartidaPresupuestaria = listaGrupoPartidaPresupuestaria;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public List<NivelPartidaPresupuestaria> getListaNivelPartidaPresupuestaria()
/* 223:    */   {
/* 224:325 */     if (this.listaNivelPartidaPresupuestaria == null) {
/* 225:326 */       this.listaNivelPartidaPresupuestaria = this.servicioNivelPartidaPresupuestaria.obtenerListaCombo("nombre", true, null);
/* 226:    */     }
/* 227:328 */     return this.listaNivelPartidaPresupuestaria;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setListaNivelPartidaPresupuestaria(List<NivelPartidaPresupuestaria> listaNivelPartidaPresupuestaria)
/* 231:    */   {
/* 232:338 */     this.listaNivelPartidaPresupuestaria = listaNivelPartidaPresupuestaria;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public List<PartidaPresupuestaria> getListaPartidaPresupuestariaPadre()
/* 236:    */   {
/* 237:347 */     return this.listaPartidaPresupuestariaPadre;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public void setListaPartidaPresupuestariaPadre(List<PartidaPresupuestaria> listaPartidaPresupuestariaPadre)
/* 241:    */   {
/* 242:357 */     this.listaPartidaPresupuestariaPadre = listaPartidaPresupuestariaPadre;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public LazyDataModel<PartidaPresupuestaria> getListaPartidaPresupuestaria()
/* 246:    */   {
/* 247:366 */     return this.listaPartidaPresupuestaria;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setListaPartidaPresupuestaria(LazyDataModel<PartidaPresupuestaria> listaPartidaPresupuestaria)
/* 251:    */   {
/* 252:376 */     this.listaPartidaPresupuestaria = listaPartidaPresupuestaria;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public List<CuentaContable> getListaCuentaContable()
/* 256:    */   {
/* 257:385 */     List<CuentaContable> lista = new ArrayList();
/* 258:386 */     for (CuentaContable cuentaContable : getPartidaPresupuestaria().getListaCuentaContable()) {
/* 259:387 */       if (!cuentaContable.isEliminado()) {
/* 260:388 */         lista.add(cuentaContable);
/* 261:    */       }
/* 262:    */     }
/* 263:391 */     return lista;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public CuentaContable getCuentaContableSeleccionada()
/* 267:    */   {
/* 268:400 */     return this.cuentaContableSeleccionada;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public void setCuentaContableSeleccionada(CuentaContable cuentaContableSeleccionada)
/* 272:    */   {
/* 273:410 */     this.cuentaContableSeleccionada = cuentaContableSeleccionada;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public CuentaContable getCuentaContable()
/* 277:    */   {
/* 278:419 */     return this.cuentaContable;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 282:    */   {
/* 283:429 */     this.cuentaContable = cuentaContable;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public DataTable getDtPartidaPresupuestaria()
/* 287:    */   {
/* 288:438 */     return this.dtPartidaPresupuestaria;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setDtPartidaPresupuestaria(DataTable dtPartidaPresupuestaria)
/* 292:    */   {
/* 293:448 */     this.dtPartidaPresupuestaria = dtPartidaPresupuestaria;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public DataTable getDtCuentaContablePresupuestaria()
/* 297:    */   {
/* 298:457 */     return this.dtCuentaContablePresupuestaria;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setDtCuentaContablePresupuestaria(DataTable dtCuentaContablePresupuestaria)
/* 302:    */   {
/* 303:467 */     this.dtCuentaContablePresupuestaria = dtCuentaContablePresupuestaria;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public DataTable getDtCuentaContable()
/* 307:    */   {
/* 308:476 */     return this.dtCuentaContable;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 312:    */   {
/* 313:486 */     this.dtCuentaContable = dtCuentaContable;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public boolean isIndicadorMovimiento()
/* 317:    */   {
/* 318:495 */     return this.indicadorMovimiento;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public void setIndicadorMovimiento(boolean indicadorMovimiento)
/* 322:    */   {
/* 323:505 */     this.indicadorMovimiento = indicadorMovimiento;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 327:    */   {
/* 328:509 */     return this.listaCuentaContableBean;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 332:    */   {
/* 333:514 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 334:    */   }
/* 335:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.presupuesto.configuracion.PartidaPresupuestariaBean
 * JD-Core Version:    0.7.0.1
 */