/*   1:    */ package com.asinfo.as2.finaciero.contabilidad.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.amortizacion.procesos.servicio.ServicioAmortizacion;
/*   4:    */ import com.asinfo.as2.controller.LanguageController;
/*   5:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.entities.Documento;
/*   8:    */ import com.asinfo.as2.entities.InterfazContableProceso;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.amortizacion.DetalleAmortizacion;
/*  11:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  12:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  13:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  14:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  15:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  16:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioInterfazContableProceso;
/*  17:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  20:    */ import java.math.BigDecimal;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.annotation.PostConstruct;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import javax.faces.model.SelectItem;
/*  30:    */ import org.apache.log4j.Logger;
/*  31:    */ import org.primefaces.component.datatable.DataTable;
/*  32:    */ import org.primefaces.model.LazyDataModel;
/*  33:    */ import org.primefaces.model.SortOrder;
/*  34:    */ 
/*  35:    */ @ManagedBean
/*  36:    */ @ViewScoped
/*  37:    */ public class InterfazContableAmortizacionBean
/*  38:    */   extends PageControllerAS2
/*  39:    */ {
/*  40:    */   private static final long serialVersionUID = 1L;
/*  41:    */   @EJB
/*  42:    */   private ServicioInterfazContableProceso servicioInterfazContableProceso;
/*  43:    */   @EJB
/*  44:    */   private ServicioDocumento servicioDocumento;
/*  45:    */   @EJB
/*  46:    */   private ServicioAmortizacion servicioAmortizacion;
/*  47:    */   private InterfazContableProceso interfazContableProceso;
/*  48:    */   private int anio;
/*  49:    */   private int mes;
/*  50:    */   private List<SelectItem> listaMes;
/*  51:    */   private LazyDataModel<InterfazContableProceso> listaInterfazContableProceso;
/*  52:    */   private List<DetalleAmortizacion> listaDetalleAmortizacion;
/*  53:    */   private List<Documento> listaDocumento;
/*  54:    */   private List<SelectItem> listaDocumentoBase;
/*  55:    */   private DataTable dtInterfazContableProceso;
/*  56:    */   private DataTable dtDetalleAmortizacion;
/*  57: 80 */   private AS2Exception exContabilizacion = new AS2Exception("");
/*  58:    */   
/*  59:    */   @PostConstruct
/*  60:    */   public void init()
/*  61:    */   {
/*  62: 84 */     this.mes = FuncionesUtiles.getMes(new Date());
/*  63: 85 */     this.anio = FuncionesUtiles.obtenerAnioActual();
/*  64:    */     
/*  65: 87 */     this.listaInterfazContableProceso = new LazyDataModel()
/*  66:    */     {
/*  67:    */       private static final long serialVersionUID = 1L;
/*  68:    */       
/*  69:    */       public List<InterfazContableProceso> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  70:    */       {
/*  71: 99 */         List<InterfazContableProceso> lista = new ArrayList();
/*  72:    */         
/*  73:101 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  74:102 */         filters.put("documentoBase", String.valueOf(DocumentoBase.AMORTIZACION));
/*  75:    */         
/*  76:104 */         lista = InterfazContableAmortizacionBean.this.servicioInterfazContableProceso.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  77:105 */         InterfazContableAmortizacionBean.this.listaInterfazContableProceso.setRowCount(InterfazContableAmortizacionBean.this.servicioInterfazContableProceso.contarPorCriterio(filters));
/*  78:106 */         return lista;
/*  79:    */       }
/*  80:    */     };
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String crear()
/*  84:    */   {
/*  85:118 */     limpiar();
/*  86:119 */     setEditado(true);
/*  87:    */     
/*  88:121 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String editar()
/*  92:    */   {
/*  93:131 */     addInfoMessage(getLanguageController().getMensaje("msg_accion_no_permitida"));
/*  94:132 */     return "";
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String guardar()
/*  98:    */   {
/*  99:142 */     if (this.listaDetalleAmortizacion.isEmpty()) {
/* 100:143 */       return "";
/* 101:    */     }
/* 102:    */     try
/* 103:    */     {
/* 104:146 */       this.servicioAmortizacion.contabilizarInterfazContable(this.anio, this.mes, this.interfazContableProceso);
/* 105:147 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 106:148 */       limpiar();
/* 107:    */     }
/* 108:    */     catch (ExcepcionAS2Financiero e)
/* 109:    */     {
/* 110:150 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 111:    */     }
/* 112:    */     catch (ExcepcionAS2 e)
/* 113:    */     {
/* 114:152 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 115:    */     }
/* 116:    */     catch (AS2Exception e)
/* 117:    */     {
/* 118:154 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 119:    */     }
/* 120:157 */     return "";
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String eliminar()
/* 124:    */   {
/* 125:    */     try
/* 126:    */     {
/* 127:168 */       this.servicioInterfazContableProceso.anular(this.interfazContableProceso);
/* 128:169 */       addInfoMessage(getLanguageController().getMensaje("msg_info_anular"));
/* 129:170 */       cargarDatos();
/* 130:    */     }
/* 131:    */     catch (ExcepcionAS2Financiero e)
/* 132:    */     {
/* 133:172 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 134:    */     }
/* 135:    */     catch (ExcepcionAS2 e)
/* 136:    */     {
/* 137:174 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 138:175 */       LOG.info(e);
/* 139:    */     }
/* 140:    */     catch (Exception e)
/* 141:    */     {
/* 142:177 */       addErrorMessage(getLanguageController().getMensaje("msg_error_anular"));
/* 143:178 */       LOG.error(e);
/* 144:    */     }
/* 145:181 */     return "";
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String limpiar()
/* 149:    */   {
/* 150:191 */     setEditado(false);
/* 151:192 */     this.interfazContableProceso = new InterfazContableProceso();
/* 152:193 */     this.interfazContableProceso.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 153:194 */     this.interfazContableProceso.setSucursal(AppUtil.getSucursal());
/* 154:195 */     this.interfazContableProceso.setEstado(Estado.ELABORADO);
/* 155:196 */     this.interfazContableProceso.setDocumentoBase(DocumentoBase.AMORTIZACION);
/* 156:197 */     this.interfazContableProceso.setFiltroDocumentoBase(DocumentoBase.AMORTIZACION);
/* 157:198 */     cargarListaDocumento();
/* 158:199 */     this.interfazContableProceso.setDocumento((Documento)this.listaDocumento.get(0));
/* 159:200 */     this.listaDetalleAmortizacion = null;
/* 160:    */     
/* 161:202 */     return "";
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String cargarDatos()
/* 165:    */   {
/* 166:212 */     setEditado(false);
/* 167:213 */     limpiar();
/* 168:    */     
/* 169:215 */     return null;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void cargarListaDocumento()
/* 173:    */   {
/* 174:    */     try
/* 175:    */     {
/* 176:220 */       this.listaDocumento = this.servicioDocumento.buscarPorDocumentoBaseOrganizacion(getInterfazContableProceso().getFiltroDocumentoBase(), 
/* 177:221 */         AppUtil.getOrganizacion().getIdOrganizacion());
/* 178:    */     }
/* 179:    */     catch (ExcepcionAS2 e)
/* 180:    */     {
/* 181:223 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 182:    */     }
/* 183:    */   }
/* 184:    */   
/* 185:    */   public List<Documento> getListaDocumento()
/* 186:    */   {
/* 187:233 */     if (this.listaDocumento == null) {
/* 188:234 */       this.listaDocumento = new ArrayList();
/* 189:    */     }
/* 190:236 */     return this.listaDocumento;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public void setListaDocumento(List<Documento> listaDocumento)
/* 194:    */   {
/* 195:246 */     this.listaDocumento = listaDocumento;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public List<SelectItem> getListaDocumentoBase()
/* 199:    */   {
/* 200:255 */     if (this.listaDocumentoBase == null)
/* 201:    */     {
/* 202:256 */       this.listaDocumentoBase = new ArrayList();
/* 203:257 */       SelectItem item1 = new SelectItem(DocumentoBase.AMORTIZACION, DocumentoBase.AMORTIZACION.getNombre());
/* 204:258 */       this.listaDocumentoBase.add(item1);
/* 205:    */     }
/* 206:261 */     return this.listaDocumentoBase;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void setListaDocumentoBase(List<SelectItem> listaDocumentoBase)
/* 210:    */   {
/* 211:271 */     this.listaDocumentoBase = listaDocumentoBase;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public InterfazContableProceso getInterfazContableProceso()
/* 215:    */   {
/* 216:275 */     return this.interfazContableProceso;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void setInterfazContableProceso(InterfazContableProceso interfazContableProceso)
/* 220:    */   {
/* 221:279 */     this.interfazContableProceso = interfazContableProceso;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public LazyDataModel<InterfazContableProceso> getListaInterfazContableProceso()
/* 225:    */   {
/* 226:283 */     return this.listaInterfazContableProceso;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public void setListaInterfazContableProceso(LazyDataModel<InterfazContableProceso> listaInterfazContableProceso)
/* 230:    */   {
/* 231:287 */     this.listaInterfazContableProceso = listaInterfazContableProceso;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public DataTable getDtInterfazContableProceso()
/* 235:    */   {
/* 236:291 */     return this.dtInterfazContableProceso;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public void setDtInterfazContableProceso(DataTable dtInterfazContableProceso)
/* 240:    */   {
/* 241:295 */     this.dtInterfazContableProceso = dtInterfazContableProceso;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public List<DetalleAmortizacion> getListaDetalleAmortizacion()
/* 245:    */   {
/* 246:302 */     if (null == this.listaDetalleAmortizacion) {
/* 247:303 */       this.listaDetalleAmortizacion = new ArrayList();
/* 248:    */     }
/* 249:306 */     return this.listaDetalleAmortizacion;
/* 250:    */   }
/* 251:    */   
/* 252:    */   public void setListaDetalleAmortizacion(List<DetalleAmortizacion> listaDetalleAmortizacion)
/* 253:    */   {
/* 254:314 */     this.listaDetalleAmortizacion = listaDetalleAmortizacion;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public DataTable getDtDetalleAmortizacion()
/* 258:    */   {
/* 259:321 */     return this.dtDetalleAmortizacion;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void setDtDetalleAmortizacion(DataTable dtDetalleAmortizacion)
/* 263:    */   {
/* 264:329 */     this.dtDetalleAmortizacion = dtDetalleAmortizacion;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public AS2Exception getExContabilizacion()
/* 268:    */   {
/* 269:338 */     return this.exContabilizacion;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void setExContabilizacion(AS2Exception exContabilizacion)
/* 273:    */   {
/* 274:348 */     this.exContabilizacion = exContabilizacion;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public int getAnio()
/* 278:    */   {
/* 279:355 */     return this.anio;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setAnio(int anio)
/* 283:    */   {
/* 284:363 */     this.anio = anio;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public int getMes()
/* 288:    */   {
/* 289:370 */     return this.mes;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setMes(int mes)
/* 293:    */   {
/* 294:378 */     this.mes = mes;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<SelectItem> getListaMes()
/* 298:    */   {
/* 299:382 */     if (this.listaMes == null)
/* 300:    */     {
/* 301:383 */       this.listaMes = new ArrayList();
/* 302:384 */       for (Mes mes : Mes.values())
/* 303:    */       {
/* 304:385 */         SelectItem selectItem = new SelectItem(Integer.valueOf(mes.ordinal() + 1), mes.name());
/* 305:386 */         this.listaMes.add(selectItem);
/* 306:    */       }
/* 307:    */     }
/* 308:390 */     return this.listaMes;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void procesar()
/* 312:    */   {
/* 313:397 */     Date fechaDesde = FuncionesUtiles.getFecha(1, this.mes, this.anio);
/* 314:398 */     Date fechaHasta = FuncionesUtiles.getFechaFinMes(fechaDesde);
/* 315:399 */     this.interfazContableProceso.setFechaDesde(fechaDesde);
/* 316:400 */     this.interfazContableProceso.setFechaHasta(fechaHasta);
/* 317:401 */     this.listaDetalleAmortizacion = this.servicioAmortizacion.getDetalleAmortizacion(0, fechaDesde, fechaHasta, Estado.ELABORADO, AppUtil.getOrganizacion()
/* 318:402 */       .getIdOrganizacion());
/* 319:    */   }
/* 320:    */   
/* 321:    */   public BigDecimal getTotalDetalleAmortizacion()
/* 322:    */   {
/* 323:406 */     BigDecimal total = BigDecimal.ZERO;
/* 324:407 */     for (DetalleAmortizacion detalle : getListaDetalleAmortizacion()) {
/* 325:408 */       total = total.add(detalle.getValor());
/* 326:    */     }
/* 327:411 */     return total;
/* 328:    */   }
/* 329:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.finaciero.contabilidad.configuracion.controller.InterfazContableAmortizacionBean
 * JD-Core Version:    0.7.0.1
 */