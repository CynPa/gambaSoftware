/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.CuentaContable;
/*   6:    */ import com.asinfo.as2.entities.DetalleVariable;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.entities.Variable;
/*  10:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioCuentaContable;
/*  11:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioVariable;
/*  12:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  13:    */ import com.asinfo.as2.util.AppUtil;
/*  14:    */ import java.util.ArrayList;
/*  15:    */ import java.util.List;
/*  16:    */ import java.util.Map;
/*  17:    */ import javax.annotation.PostConstruct;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.faces.bean.ManagedBean;
/*  20:    */ import javax.faces.bean.ManagedProperty;
/*  21:    */ import javax.faces.bean.ViewScoped;
/*  22:    */ import org.apache.log4j.Logger;
/*  23:    */ import org.primefaces.component.datatable.DataTable;
/*  24:    */ import org.primefaces.event.SelectEvent;
/*  25:    */ import org.primefaces.model.LazyDataModel;
/*  26:    */ import org.primefaces.model.SortOrder;
/*  27:    */ 
/*  28:    */ @ManagedBean
/*  29:    */ @ViewScoped
/*  30:    */ public class VariableBean
/*  31:    */   extends PageControllerAS2
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   private ServicioVariable servicioVariable;
/*  36:    */   @EJB
/*  37:    */   private ServicioCuentaContable servicioCuentaContable;
/*  38:    */   @ManagedProperty("#{listaCuentaContableBean}")
/*  39:    */   private ListaCuentaContableBean listaCuentaContableBean;
/*  40:    */   private Variable variable;
/*  41:    */   private CuentaContable cuentaContable;
/*  42: 70 */   private DetalleVariable lineaDetalleVariable = new DetalleVariable();
/*  43:    */   private LazyDataModel<Variable> listaVariable;
/*  44:    */   private DataTable dtVariable;
/*  45:    */   private DataTable dtCuentaContable;
/*  46:    */   private DataTable dtDetalleVariable;
/*  47:    */   private Integer idVariable;
/*  48:    */   
/*  49:    */   @PostConstruct
/*  50:    */   public void init()
/*  51:    */   {
/*  52:    */     try
/*  53:    */     {
/*  54: 93 */       this.listaVariable = new LazyDataModel()
/*  55:    */       {
/*  56:    */         private static final long serialVersionUID = 1L;
/*  57:    */         
/*  58:    */         public List<Variable> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  59:    */         {
/*  60: 98 */           List<Variable> lista = new ArrayList();
/*  61:    */           
/*  62:100 */           boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  63:101 */           lista = VariableBean.this.servicioVariable.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  64:102 */           VariableBean.this.listaVariable.setRowCount(VariableBean.this.servicioVariable.contarPorCriterio(filters));
/*  65:103 */           return lista;
/*  66:    */         }
/*  67:    */       };
/*  68:    */     }
/*  69:    */     catch (Exception e)
/*  70:    */     {
/*  71:108 */       LOG.error("ERROR AL CARGAR DATOS", e);
/*  72:109 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/*  73:    */     }
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String editar()
/*  77:    */   {
/*  78:116 */     if (getVariable().getId() > 0)
/*  79:    */     {
/*  80:117 */       this.variable = this.servicioVariable.cargarDetalle(getVariable().getId());
/*  81:118 */       setEditado(true);
/*  82:    */     }
/*  83:    */     else
/*  84:    */     {
/*  85:120 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  86:    */     }
/*  87:123 */     return "";
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String guardar()
/*  91:    */   {
/*  92:    */     try
/*  93:    */     {
/*  94:129 */       if (this.variable.getNombre().matches("^[A-Za-z_]*$"))
/*  95:    */       {
/*  96:130 */         this.servicioVariable.guardar(this.variable);
/*  97:131 */         addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  98:132 */         limpiar();
/*  99:133 */         setEditado(false);
/* 100:    */       }
/* 101:    */       else
/* 102:    */       {
/* 103:136 */         addErrorMessage(getLanguageController().getMensaje("msg_error_formato_incorrecto"));
/* 104:    */       }
/* 105:    */     }
/* 106:    */     catch (ExcepcionAS2Financiero e)
/* 107:    */     {
/* 108:140 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 109:141 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 110:142 */       e.printStackTrace();
/* 111:    */     }
/* 112:    */     catch (Exception e)
/* 113:    */     {
/* 114:144 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 115:145 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 116:146 */       e.printStackTrace();
/* 117:    */     }
/* 118:148 */     return "";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String eliminar()
/* 122:    */   {
/* 123:    */     try
/* 124:    */     {
/* 125:154 */       this.variable = this.servicioVariable.cargarDetalle(getVariable().getId());
/* 126:155 */       this.variable.setEliminado(true);
/* 127:156 */       this.servicioVariable.eliminar(this.variable);
/* 128:157 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 129:    */     }
/* 130:    */     catch (Exception e)
/* 131:    */     {
/* 132:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 133:160 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 134:    */     }
/* 135:162 */     return "";
/* 136:    */   }
/* 137:    */   
/* 138:    */   public String eliminarDetalle()
/* 139:    */   {
/* 140:166 */     DetalleVariable dv = (DetalleVariable)this.dtDetalleVariable.getRowData();
/* 141:167 */     dv.setEliminado(true);
/* 142:168 */     return "";
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String limpiar()
/* 146:    */   {
/* 147:174 */     this.variable = new Variable();
/* 148:175 */     this.variable.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 149:176 */     this.variable.setIdSucursal(AppUtil.getSucursal().getId());
/* 150:177 */     this.variable.setListaDetalleVariable(new ArrayList());
/* 151:178 */     return "";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public String cargarDatos()
/* 155:    */   {
/* 156:183 */     return "";
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void agregarDetalleVariableListener()
/* 160:    */   {
/* 161:194 */     agregarDetalleVariable(getVariable(), new CuentaContable());
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String agregarDetalleVariable(Variable variable, CuentaContable cuentaContable)
/* 165:    */   {
/* 166:206 */     DetalleVariable detalleVariable = new DetalleVariable();
/* 167:207 */     detalleVariable.setVariable(variable);
/* 168:208 */     detalleVariable.setCuentaContable(cuentaContable);
/* 169:209 */     variable.getListaDetalleVariable().add(detalleVariable);
/* 170:    */     
/* 171:211 */     return "";
/* 172:    */   }
/* 173:    */   
/* 174:    */   public List<DetalleVariable> getListaDetalleVariable()
/* 175:    */   {
/* 176:221 */     List<DetalleVariable> detalle = new ArrayList();
/* 177:222 */     for (DetalleVariable d : getVariable().getListaDetalleVariable()) {
/* 178:224 */       if (!d.isEliminado()) {
/* 179:225 */         detalle.add(d);
/* 180:    */       }
/* 181:    */     }
/* 182:229 */     return detalle;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void buscarCuentaContableListener()
/* 186:    */   {
/* 187:    */     try
/* 188:    */     {
/* 189:239 */       DetalleVariable d = (DetalleVariable)this.dtDetalleVariable.getRowData();
/* 190:240 */       String codigoCuentaContable = d.getCuentaContable().getCodigo();
/* 191:    */       
/* 192:242 */       CuentaContable cuentaContable = this.servicioCuentaContable.buscarPorCodigo(codigoCuentaContable, AppUtil.getOrganizacion()
/* 193:243 */         .getIdOrganizacion());
/* 194:244 */       d.setCuentaContable(cuentaContable);
/* 195:    */     }
/* 196:    */     catch (ExcepcionAS2Financiero e)
/* 197:    */     {
/* 198:247 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 199:    */     }
/* 200:    */     catch (Exception e)
/* 201:    */     {
/* 202:250 */       LOG.error("ERROR AL BUSCAR CUENTA CONTABLE");
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void cargarCuentaContable(SelectEvent event)
/* 207:    */   {
/* 208:260 */     this.cuentaContable = ((CuentaContable)event.getObject());
/* 209:261 */     List<CuentaContable> listaCuentaContable = null;
/* 210:    */     try
/* 211:    */     {
/* 212:263 */       listaCuentaContable = this.servicioCuentaContable.buscarCuentasMovimientoPorCodigo(this.cuentaContable.getCodigo(), AppUtil.getOrganizacion().getId());
/* 213:    */     }
/* 214:    */     catch (ExcepcionAS2Financiero e)
/* 215:    */     {
/* 216:265 */       listaCuentaContable = new ArrayList();
/* 217:    */     }
/* 218:267 */     boolean primero = true;
/* 219:268 */     for (CuentaContable cuentaContableAgregar : listaCuentaContable)
/* 220:    */     {
/* 221:269 */       if (primero) {
/* 222:270 */         this.lineaDetalleVariable.setCuentaContable(cuentaContableAgregar);
/* 223:    */       } else {
/* 224:272 */         agregarDetalleVariable(getVariable(), cuentaContableAgregar);
/* 225:    */       }
/* 226:275 */       primero = false;
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   public ServicioVariable getServicioVariable()
/* 231:    */   {
/* 232:281 */     return this.servicioVariable;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void setServicioVariable(ServicioVariable servicioVariable)
/* 236:    */   {
/* 237:285 */     this.servicioVariable = servicioVariable;
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Variable getVariable()
/* 241:    */   {
/* 242:289 */     return this.variable;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setVariable(Variable variable)
/* 246:    */   {
/* 247:293 */     this.variable = variable;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public LazyDataModel<Variable> getListaVariable()
/* 251:    */   {
/* 252:297 */     return this.listaVariable;
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setListaVariable(LazyDataModel<Variable> listaVariable)
/* 256:    */   {
/* 257:301 */     this.listaVariable = listaVariable;
/* 258:    */   }
/* 259:    */   
/* 260:    */   public DataTable getDtVariable()
/* 261:    */   {
/* 262:305 */     return this.dtVariable;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setDtVariable(DataTable dtVariable)
/* 266:    */   {
/* 267:309 */     this.dtVariable = dtVariable;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public Integer getIdVariable()
/* 271:    */   {
/* 272:313 */     return this.idVariable;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setIdVariable(Integer idVariable)
/* 276:    */   {
/* 277:317 */     this.idVariable = idVariable;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public DataTable getDtDetalleVariable()
/* 281:    */   {
/* 282:321 */     return this.dtDetalleVariable;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setDtDetalleVariable(DataTable dtDetalleVariable)
/* 286:    */   {
/* 287:325 */     this.dtDetalleVariable = dtDetalleVariable;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public ListaCuentaContableBean getListaCuentaContableBean()
/* 291:    */   {
/* 292:329 */     return this.listaCuentaContableBean;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setListaCuentaContableBean(ListaCuentaContableBean listaCuentaContableBean)
/* 296:    */   {
/* 297:334 */     this.listaCuentaContableBean = listaCuentaContableBean;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public CuentaContable getCuentaContable()
/* 301:    */   {
/* 302:338 */     return this.cuentaContable;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setCuentaContable(CuentaContable cuentaContable)
/* 306:    */   {
/* 307:342 */     this.cuentaContable = cuentaContable;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public DetalleVariable getLineaDetalleVariable()
/* 311:    */   {
/* 312:346 */     return this.lineaDetalleVariable;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setLineaDetalleVariable(DetalleVariable lineaDetalleVariable)
/* 316:    */   {
/* 317:350 */     this.lineaDetalleVariable = lineaDetalleVariable;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public DataTable getDtCuentaContable()
/* 321:    */   {
/* 322:354 */     return this.dtCuentaContable;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setDtCuentaContable(DataTable dtCuentaContable)
/* 326:    */   {
/* 327:358 */     this.dtCuentaContable = dtCuentaContable;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public ServicioCuentaContable getServicioCuentaContable()
/* 331:    */   {
/* 332:362 */     return this.servicioCuentaContable;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setServicioCuentaContable(ServicioCuentaContable servicioCuentaContable)
/* 336:    */   {
/* 337:367 */     this.servicioCuentaContable = servicioCuentaContable;
/* 338:    */   }
/* 339:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.VariableBean
 * JD-Core Version:    0.7.0.1
 */