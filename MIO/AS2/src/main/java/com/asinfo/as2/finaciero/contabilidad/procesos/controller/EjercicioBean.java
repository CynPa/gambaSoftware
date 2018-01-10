/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Asiento;
/*   6:    */ import com.asinfo.as2.entities.DetalleAsiento;
/*   7:    */ import com.asinfo.as2.entities.Ejercicio;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Periodo;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  12:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioTipoAsiento;
/*  13:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioAsiento;
/*  14:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioEjercicio;
/*  15:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  16:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import java.math.BigDecimal;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Calendar;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.annotation.PostConstruct;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedBean;
/*  27:    */ import javax.faces.bean.ViewScoped;
/*  28:    */ import org.apache.log4j.Logger;
/*  29:    */ import org.primefaces.component.datatable.DataTable;
/*  30:    */ import org.primefaces.model.LazyDataModel;
/*  31:    */ import org.primefaces.model.SortOrder;
/*  32:    */ 
/*  33:    */ @ManagedBean
/*  34:    */ @ViewScoped
/*  35:    */ public class EjercicioBean
/*  36:    */   extends PageControllerAS2
/*  37:    */ {
/*  38:    */   private static final long serialVersionUID = 1L;
/*  39:    */   @EJB
/*  40:    */   private ServicioEjercicio servicioEjercicio;
/*  41:    */   @EJB
/*  42:    */   private ServicioAsiento servicioAsiento;
/*  43:    */   @EJB
/*  44:    */   private ServicioPeriodo servicioPeriodo;
/*  45:    */   @EJB
/*  46:    */   private ServicioTipoAsiento servicioTipoAsiento;
/*  47:    */   private Ejercicio ejercicio;
/*  48:    */   private Periodo periodo;
/*  49:    */   private LazyDataModel<Ejercicio> listaEjercicio;
/*  50:    */   private DataTable dtEjercicio;
/*  51:    */   private DataTable dtPeriodo;
/*  52:    */   
/*  53:    */   @PostConstruct
/*  54:    */   public void init()
/*  55:    */   {
/*  56: 76 */     this.listaEjercicio = new LazyDataModel()
/*  57:    */     {
/*  58:    */       private static final long serialVersionUID = 1L;
/*  59:    */       
/*  60:    */       public List<Ejercicio> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  61:    */       {
/*  62: 82 */         List<Ejercicio> lista = new ArrayList();
/*  63:    */         
/*  64: 84 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  65:    */         
/*  66: 86 */         lista = EjercicioBean.this.servicioEjercicio.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  67:    */         
/*  68: 88 */         EjercicioBean.this.listaEjercicio.setRowCount(EjercicioBean.this.servicioEjercicio.contarPorCriterio(filters));
/*  69:    */         
/*  70: 90 */         return lista;
/*  71:    */       }
/*  72:    */     };
/*  73:    */   }
/*  74:    */   
/*  75:    */   public String editar()
/*  76:    */   {
/*  77: 97 */     if (getEjercicio().getId() > 0)
/*  78:    */     {
/*  79: 98 */       this.ejercicio = this.servicioEjercicio.recuperaEjercicio(getEjercicio().getId());
/*  80: 99 */       this.ejercicio.setAsignacionAnio(Integer.valueOf(FuncionesUtiles.getAnio(this.ejercicio.getFechaDesde())));
/*  81:100 */       FuncionesUtiles.ordenaLista(this.ejercicio.getPeriodos(), "fechaHasta");
/*  82:101 */       for (Periodo p : this.ejercicio.getPeriodos()) {
/*  83:102 */         actualizarValores(p);
/*  84:    */       }
/*  85:104 */       setEditado(true);
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89:106 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  90:    */     }
/*  91:109 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String guardar()
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:114 */       this.servicioEjercicio.guardar(this.ejercicio);
/*  99:115 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 100:116 */       setEditado(false);
/* 101:117 */       limpiar();
/* 102:    */     }
/* 103:    */     catch (Exception e)
/* 104:    */     {
/* 105:119 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 106:120 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 107:    */     }
/* 108:122 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String eliminar()
/* 112:    */   {
/* 113:    */     try
/* 114:    */     {
/* 115:127 */       this.servicioEjercicio.eliminar(this.ejercicio);
/* 116:128 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/* 117:129 */       cargarDatos();
/* 118:    */     }
/* 119:    */     catch (Exception e)
/* 120:    */     {
/* 121:131 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 122:132 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 123:    */     }
/* 124:134 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String cargarDatos()
/* 128:    */   {
/* 129:138 */     return "";
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String cerrar()
/* 133:    */   {
/* 134:142 */     if (getEjercicio().getId() > 0)
/* 135:    */     {
/* 136:146 */       Asiento asiento = new Asiento();
/* 137:    */       try
/* 138:    */       {
/* 139:148 */         List<DetalleAsiento> detalles = this.servicioEjercicio.cerrarEjercicio(getEjercicio().getId());
/* 140:149 */         asiento.setFecha(getEjercicio().getFechaHasta());
/* 141:150 */         asiento.setConcepto("CIERRE DE EJERCICIO: " + getEjercicio().getNombre());
/* 142:151 */         asiento.setEstado(Estado.APROBADO);
/* 143:152 */         asiento.setNumero("CIERRE1");
/* 144:153 */         asiento.setPeriodo(this.servicioPeriodo.buscarPorId(Integer.valueOf(1)));
/* 145:154 */         asiento.setTipoAsiento(this.servicioTipoAsiento.buscarPorId(Integer.valueOf(1)));
/* 146:    */         
/* 147:156 */         asiento.setListaDetalleAsiento(detalles);
/* 148:157 */         for (DetalleAsiento detalleAsiento : detalles)
/* 149:    */         {
/* 150:159 */           double debe = Double.parseDouble(detalleAsiento.getDebe() + "");
/* 151:160 */           double haber = Double.parseDouble(detalleAsiento.getHaber() + "");
/* 152:161 */           double diferencia = debe - haber;
/* 153:162 */           if (diferencia > 0.0D) {
/* 154:163 */             detalleAsiento.setDebe(BigDecimal.valueOf(diferencia));
/* 155:    */           } else {
/* 156:165 */             detalleAsiento.setHaber(BigDecimal.valueOf(diferencia));
/* 157:    */           }
/* 158:167 */           detalleAsiento.setAsiento(asiento);
/* 159:    */         }
/* 160:169 */         this.servicioAsiento.guardar(asiento);
/* 161:    */       }
/* 162:    */       catch (ExcepcionAS2Financiero e)
/* 163:    */       {
/* 164:171 */         addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 165:172 */         LOG.error("ERROR AL CERRAR EL PERIODO", e);
/* 166:    */       }
/* 167:    */       catch (Exception e)
/* 168:    */       {
/* 169:174 */         addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/* 170:175 */         LOG.error("ERROR AL CERRAR EL PERIODO", e);
/* 171:    */       }
/* 172:    */     }
/* 173:    */     else
/* 174:    */     {
/* 175:178 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 176:    */     }
/* 177:181 */     return "";
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String limpiar()
/* 181:    */   {
/* 182:191 */     crearEjercicio();
/* 183:192 */     return "";
/* 184:    */   }
/* 185:    */   
/* 186:    */   public List<Periodo> getListaPeriodo()
/* 187:    */   {
/* 188:196 */     List<Periodo> periodos = new ArrayList();
/* 189:197 */     for (Periodo periodo : getEjercicio().getPeriodos()) {
/* 190:198 */       if (!periodo.isEliminado()) {
/* 191:199 */         periodos.add(periodo);
/* 192:    */       }
/* 193:    */     }
/* 194:202 */     return periodos;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String agregarPeriodo()
/* 198:    */   {
/* 199:206 */     if ((getEjercicio().getPeriodos() != null) && (!getEjercicio().getPeriodos().isEmpty()))
/* 200:    */     {
/* 201:207 */       Periodo periodo = new Periodo();
/* 202:208 */       periodo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 203:209 */       periodo.setIdSucursal(AppUtil.getSucursal().getId());
/* 204:210 */       periodo.setEjercicio(getEjercicio());
/* 205:211 */       getEjercicio().getPeriodos().add(periodo);
/* 206:    */     }
/* 207:    */     else
/* 208:    */     {
/* 209:214 */       int numeroMeses = 12;
/* 210:215 */       String descripcion = "Ejercicio para el mes de: ";
/* 211:216 */       for (int i = 0; i < numeroMeses; i++)
/* 212:    */       {
/* 213:217 */         Periodo periodo = new Periodo();
/* 214:218 */         periodo.setNombre(FuncionesUtiles.nombreMes(i).toUpperCase());
/* 215:219 */         periodo.setFechaDesde(FuncionesUtiles.getFechaInicioMes(FuncionesUtiles.getFecha(1, i + 1, this.ejercicio.getAsignacionAnio().intValue())));
/* 216:220 */         periodo.setFechaHasta(FuncionesUtiles.getFechaFinMes(this.ejercicio.getAsignacionAnio().intValue(), i + 1));
/* 217:221 */         periodo.setActivo(true);
/* 218:222 */         periodo.setDescripcion(descripcion.concat(FuncionesUtiles.nombreMes(i).toUpperCase()));
/* 219:223 */         periodo.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 220:224 */         periodo.setIdSucursal(AppUtil.getSucursal().getId());
/* 221:225 */         periodo.setEjercicio(getEjercicio());
/* 222:226 */         actualizarPeriodo(periodo);
/* 223:227 */         getEjercicio().getPeriodos().add(periodo);
/* 224:    */       }
/* 225:    */     }
/* 226:230 */     return "";
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void crearEjercicio()
/* 230:    */   {
/* 231:234 */     this.ejercicio = new Ejercicio();
/* 232:235 */     this.ejercicio.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 233:236 */     this.ejercicio.setIdSucursal(AppUtil.getSucursal().getId());
/* 234:237 */     this.ejercicio.setAsignacionAnio(Integer.valueOf(Calendar.getInstance().get(1)));
/* 235:    */   }
/* 236:    */   
/* 237:    */   public String eliminarPeriodo()
/* 238:    */   {
/* 239:241 */     Periodo periodo = (Periodo)this.dtPeriodo.getRowData();
/* 240:242 */     periodo.setEliminado(true);
/* 241:243 */     return "";
/* 242:    */   }
/* 243:    */   
/* 244:    */   public Ejercicio getEjercicio()
/* 245:    */   {
/* 246:252 */     if (this.ejercicio == null) {
/* 247:253 */       limpiar();
/* 248:    */     }
/* 249:255 */     return this.ejercicio;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setEjercicio(Ejercicio ejercicio)
/* 253:    */   {
/* 254:265 */     this.ejercicio = ejercicio;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Periodo getPeriodo()
/* 258:    */   {
/* 259:269 */     if (this.periodo == null) {
/* 260:270 */       this.periodo = new Periodo();
/* 261:    */     }
/* 262:272 */     return this.periodo;
/* 263:    */   }
/* 264:    */   
/* 265:    */   public void setPeriodo(Periodo periodo)
/* 266:    */   {
/* 267:276 */     this.periodo = periodo;
/* 268:    */   }
/* 269:    */   
/* 270:    */   public LazyDataModel<Ejercicio> getListaEjercicio()
/* 271:    */   {
/* 272:280 */     return this.listaEjercicio;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setListaEjercicio(LazyDataModel<Ejercicio> listaEjercicio)
/* 276:    */   {
/* 277:284 */     this.listaEjercicio = listaEjercicio;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public DataTable getDtEjercicio()
/* 281:    */   {
/* 282:293 */     return this.dtEjercicio;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setDtEjercicio(DataTable dtEjercicio)
/* 286:    */   {
/* 287:303 */     this.dtEjercicio = dtEjercicio;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public DataTable getDtPeriodo()
/* 291:    */   {
/* 292:307 */     return this.dtPeriodo;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setDtPeriodo(DataTable dtPeriodo)
/* 296:    */   {
/* 297:311 */     this.dtPeriodo = dtPeriodo;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public void actualizarPeriodo(Periodo periodo)
/* 301:    */   {
/* 302:315 */     periodo.setIndicadorCierreCobros(periodo.isActivo());
/* 303:316 */     periodo.setIndicadorCierrePagos(periodo.isActivo());
/* 304:317 */     periodo.setIndicadorCierreContabilidad(periodo.isActivo());
/* 305:318 */     periodo.setIndicadorCierrePresupuesto(periodo.isActivo());
/* 306:319 */     periodo.setIndicadorCierreActivosFijos(periodo.isActivo());
/* 307:320 */     periodo.setIndicadorCierreCompras(periodo.isActivo());
/* 308:321 */     periodo.setIndicadorCierreVentas(periodo.isActivo());
/* 309:322 */     periodo.setIndicadorCierreProduccion(periodo.isActivo());
/* 310:323 */     periodo.setIndicadorCierreInventario(periodo.isActivo());
/* 311:324 */     periodo.setIndicadorCierreNomina(periodo.isActivo());
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void actualizarValores(Periodo periodo)
/* 315:    */   {
/* 316:328 */     if ((periodo.isIndicadorCierreCobros()) && (periodo.isIndicadorCierrePagos()) && (periodo.isIndicadorCierreContabilidad()) && 
/* 317:329 */       (periodo.isIndicadorCierrePresupuesto()) && (periodo.isIndicadorCierreActivosFijos()) && (periodo.isIndicadorCierreCompras()) && 
/* 318:330 */       (periodo.isIndicadorCierreVentas()) && (periodo.isIndicadorCierreProduccion()) && (periodo.isIndicadorCierreInventario()) && 
/* 319:331 */       (periodo.isIndicadorCierreNomina())) {
/* 320:332 */       periodo.setActivo(true);
/* 321:    */     } else {
/* 322:334 */       periodo.setActivo(false);
/* 323:    */     }
/* 324:    */   }
/* 325:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.procesos.controller.EjercicioBean
 * JD-Core Version:    0.7.0.1
 */