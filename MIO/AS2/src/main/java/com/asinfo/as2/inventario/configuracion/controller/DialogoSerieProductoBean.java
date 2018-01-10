/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageController;
/*   5:    */ import com.asinfo.as2.entities.DetalleDespachoCliente;
/*   6:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*   7:    */ import com.asinfo.as2.entities.DetalleFacturaProveedor;
/*   8:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   9:    */ import com.asinfo.as2.entities.DetalleRecepcionProveedor;
/*  10:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.Producto;
/*  13:    */ import com.asinfo.as2.entities.SerieInventarioProducto;
/*  14:    */ import com.asinfo.as2.entities.SerieProducto;
/*  15:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioSerieProducto;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import com.asinfo.as2.utils.JsfUtil;
/*  19:    */ import java.io.BufferedInputStream;
/*  20:    */ import java.io.IOException;
/*  21:    */ import java.io.Serializable;
/*  22:    */ import java.math.BigDecimal;
/*  23:    */ import java.util.ArrayList;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.Iterator;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.annotation.PostConstruct;
/*  29:    */ import javax.ejb.EJB;
/*  30:    */ import javax.faces.bean.ManagedBean;
/*  31:    */ import javax.faces.bean.SessionScoped;
/*  32:    */ import javax.validation.constraints.Size;
/*  33:    */ import org.primefaces.component.datatable.DataTable;
/*  34:    */ import org.primefaces.context.RequestContext;
/*  35:    */ import org.primefaces.event.FileUploadEvent;
/*  36:    */ import org.primefaces.model.UploadedFile;
/*  37:    */ 
/*  38:    */ @ManagedBean
/*  39:    */ @SessionScoped
/*  40:    */ public class DialogoSerieProductoBean
/*  41:    */   extends PageController
/*  42:    */   implements Serializable
/*  43:    */ {
/*  44:    */   private static final long serialVersionUID = 1L;
/*  45:    */   @EJB
/*  46:    */   private ServicioSerieProducto servicioSerieProducto;
/*  47:    */   private InventarioProducto inventarioProducto;
/*  48:    */   private List<SerieInventarioProducto> listaSerieProducto;
/*  49:    */   private List<SerieInventarioProducto> listaSerieProductoFiltrada;
/*  50:    */   private int operacion;
/*  51:    */   @Size(min=1, max=20)
/*  52:    */   private String codigoSerie;
/*  53:    */   private SerieProducto serieProducto;
/*  54:    */   private DataTable dtListaSerieProducto;
/*  55:    */   
/*  56:    */   @PostConstruct
/*  57:    */   public void init() {}
/*  58:    */   
/*  59:    */   public void abrirDialogo(InventarioProducto inventarioProducto)
/*  60:    */   {
/*  61: 79 */     this.inventarioProducto = inventarioProducto;
/*  62: 80 */     this.listaSerieProducto = null;
/*  63: 81 */     this.operacion = inventarioProducto.getOperacion();
/*  64:    */     
/*  65:    */ 
/*  66: 84 */     Map<String, Object> properties = new HashMap();
/*  67: 85 */     properties.put("modal", Boolean.valueOf(true));
/*  68: 86 */     properties.put("closable", Boolean.valueOf(false));
/*  69: 87 */     properties.put("resizable", Boolean.valueOf(false));
/*  70: 88 */     properties.put("draggable", Boolean.valueOf(true));
/*  71: 89 */     properties.put("contentWidth", Integer.valueOf(650));
/*  72: 90 */     properties.put("contentHeight", Integer.valueOf(430));
/*  73: 91 */     RequestContext.getCurrentInstance().openDialog("/resources/componentes/dialogoSerieProducto", properties, null);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void crearSerieListener()
/*  77:    */   {
/*  78: 95 */     crearSerie(this.codigoSerie);
/*  79: 96 */     totalizarSeries(this.inventarioProducto);
/*  80: 97 */     this.codigoSerie = null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void crearSerie(String serie)
/*  84:    */   {
/*  85:107 */     if ((serie != null) && (serie.length() > 0))
/*  86:    */     {
/*  87:109 */       SerieInventarioProducto serieNew = null;
/*  88:111 */       for (SerieInventarioProducto serieInvPro : this.inventarioProducto.getListaSerieProducto()) {
/*  89:112 */         if (serieInvPro.getSerieProducto().getCodigo().equalsIgnoreCase(serie))
/*  90:    */         {
/*  91:114 */           serieNew = serieInvPro;
/*  92:116 */           if (!serieNew.isEliminado())
/*  93:    */           {
/*  94:117 */             JsfUtil.addInfoMessage(getLanguageController().getMensaje("msg_error_serie_duplicada") + " '" + serie + "'");
/*  95:118 */             return;
/*  96:    */           }
/*  97:120 */           serieNew.setEliminado(false);
/*  98:    */         }
/*  99:    */       }
/* 100:125 */       if (this.servicioSerieProducto.buscarPorCodigo(AppUtil.getOrganizacion().getId(), this.inventarioProducto.getProducto(), serie.trim()) != null)
/* 101:    */       {
/* 102:126 */         JsfUtil.addInfoMessage(getLanguageController().getMensaje("msg_error_serie_duplicada") + " '" + serie + "'");
/* 103:    */       }
/* 104:    */       else
/* 105:    */       {
/* 106:129 */         if (serieNew == null)
/* 107:    */         {
/* 108:131 */           serieNew = new SerieInventarioProducto();
/* 109:132 */           serieNew.setInventarioProducto(this.inventarioProducto);
/* 110:133 */           this.inventarioProducto.getListaSerieProducto().add(serieNew);
/* 111:    */           
/* 112:135 */           SerieProducto serieProductoNew = new SerieProducto(this.inventarioProducto.getProducto(), serie);
/* 113:136 */           serieProductoNew.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 114:    */           
/* 115:138 */           serieNew.setSerieProducto(serieProductoNew);
/* 116:    */         }
/* 117:141 */         this.listaSerieProducto.add(serieNew);
/* 118:    */       }
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void cargarSerieListener()
/* 123:    */   {
/* 124:147 */     cargarSerie(this.serieProducto);
/* 125:148 */     totalizarSeries(this.inventarioProducto);
/* 126:149 */     this.serieProducto = null;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void cargarSerie(SerieProducto serieProductoCarga)
/* 130:    */   {
/* 131:154 */     if ((serieProductoCarga != null) && (serieProductoCarga.getCodigo().length() > 0))
/* 132:    */     {
/* 133:156 */       SerieInventarioProducto serieNew = null;
/* 134:158 */       for (SerieInventarioProducto serieInvPro : this.inventarioProducto.getListaSerieProducto()) {
/* 135:159 */         if (serieInvPro.getSerieProducto().getCodigo().equalsIgnoreCase(serieProductoCarga.getCodigo()))
/* 136:    */         {
/* 137:161 */           serieNew = serieInvPro;
/* 138:163 */           if (!serieNew.isEliminado())
/* 139:    */           {
/* 140:164 */             JsfUtil.addInfoMessage("Serie Duplicada");
/* 141:165 */             return;
/* 142:    */           }
/* 143:167 */           serieNew.setEliminado(false);
/* 144:    */         }
/* 145:    */       }
/* 146:172 */       if (serieNew == null)
/* 147:    */       {
/* 148:173 */         serieNew = new SerieInventarioProducto();
/* 149:174 */         serieNew.setInventarioProducto(this.inventarioProducto);
/* 150:175 */         serieNew.setSerieProducto(serieProductoCarga);
/* 151:176 */         this.inventarioProducto.getListaSerieProducto().add(serieNew);
/* 152:    */       }
/* 153:179 */       this.listaSerieProducto.add(serieNew);
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void eliminarSerieListener(SerieInventarioProducto serie)
/* 158:    */   {
/* 159:186 */     serie.setEliminado(true);
/* 160:    */     
/* 161:188 */     int i = 0;
/* 162:189 */     for (SerieInventarioProducto s : (SerieInventarioProducto[])this.listaSerieProducto.toArray(new SerieInventarioProducto[this.listaSerieProducto.size()]))
/* 163:    */     {
/* 164:190 */       if (s.getRowKey() == serie.getRowKey()) {
/* 165:191 */         this.listaSerieProducto.remove(i);
/* 166:    */       }
/* 167:193 */       i++;
/* 168:    */     }
/* 169:196 */     if (this.listaSerieProductoFiltrada != null)
/* 170:    */     {
/* 171:197 */       i = 0;
/* 172:198 */       for (SerieInventarioProducto s : (SerieInventarioProducto[])this.listaSerieProductoFiltrada.toArray(new SerieInventarioProducto[this.listaSerieProductoFiltrada.size()]))
/* 173:    */       {
/* 174:199 */         if (s.getRowKey() == serie.getRowKey()) {
/* 175:200 */           this.listaSerieProductoFiltrada.remove(i);
/* 176:    */         }
/* 177:202 */         i++;
/* 178:    */       }
/* 179:    */     }
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void totalizarSeries(InventarioProducto inventarioProducto)
/* 183:    */   {
/* 184:209 */     inventarioProducto.setCantidad(new BigDecimal(getListaSerieProducto().size()));
/* 185:211 */     if (inventarioProducto.getDetalleDespachoCliente() != null) {
/* 186:212 */       inventarioProducto.getDetalleDespachoCliente().setCantidad(inventarioProducto.getCantidad());
/* 187:214 */     } else if (inventarioProducto.getDetalleDevolucionCliente() != null) {
/* 188:215 */       inventarioProducto.getDetalleDevolucionCliente().setCantidad(inventarioProducto.getCantidad());
/* 189:217 */     } else if (inventarioProducto.getDetalleDevolucionProveedor() != null) {
/* 190:218 */       inventarioProducto.getDetalleDevolucionProveedor().setCantidad(inventarioProducto.getCantidad());
/* 191:220 */     } else if (inventarioProducto.getDetalleMovimientoInventario() != null) {
/* 192:221 */       inventarioProducto.getDetalleMovimientoInventario().setCantidad(inventarioProducto.getCantidad());
/* 193:223 */     } else if (inventarioProducto.getDetalleRecepcionProveedor() != null) {
/* 194:224 */       inventarioProducto.getDetalleRecepcionProveedor().setCantidad(inventarioProducto.getCantidad());
/* 195:    */     }
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void cargaArchivoListener(FileUploadEvent event)
/* 199:    */   {
/* 200:    */     try
/* 201:    */     {
/* 202:232 */       Map<String, String> hmSeries = new HashMap();
/* 203:233 */       BufferedInputStream input = new BufferedInputStream(event.getFile().getInputstream());
/* 204:234 */       List<String> datosArchivo = FuncionesUtiles.leerArchivoTexto(input);
/* 205:    */       Iterator localIterator;
/* 206:    */       String serie;
/* 207:236 */       if (this.operacion == 1)
/* 208:    */       {
/* 209:237 */         for (localIterator = datosArchivo.iterator(); localIterator.hasNext();)
/* 210:    */         {
/* 211:237 */           serie = (String)localIterator.next();
/* 212:238 */           if ((serie != null) && (serie.length() > 0)) {
/* 213:239 */             crearSerie(serie);
/* 214:    */           }
/* 215:    */         }
/* 216:    */       }
/* 217:    */       else
/* 218:    */       {
/* 219:243 */         Object codigosSeries = new ArrayList();
/* 220:244 */         for (serie = datosArchivo.iterator(); serie.hasNext();)
/* 221:    */         {
/* 222:244 */           serie = (String)serie.next();
/* 223:245 */           ((List)codigosSeries).add(serie);
/* 224:    */         }
/* 225:248 */         List<SerieProducto> listaSerie = this.servicioSerieProducto.getListaSerieProducto(AppUtil.getOrganizacion().getId(), this.inventarioProducto
/* 226:249 */           .getProducto(), (List)codigosSeries);
/* 227:251 */         for (String serie = listaSerie.iterator(); serie.hasNext();)
/* 228:    */         {
/* 229:251 */           serieProducto = (SerieProducto)serie.next();
/* 230:252 */           hmSeries.put(serieProducto.getCodigo(), serieProducto.getCodigo());
/* 231:253 */           cargarSerie(serieProducto);
/* 232:    */         }
/* 233:    */         SerieProducto serieProducto;
/* 234:256 */         if (listaSerie.size() != ((List)codigosSeries).size())
/* 235:    */         {
/* 236:257 */           StringBuffer sbSeries = new StringBuffer(",");
/* 237:258 */           for (String serie : (List)codigosSeries) {
/* 238:259 */             if (!hmSeries.containsKey(serie)) {
/* 239:260 */               sbSeries.append(serie + ", ");
/* 240:    */             }
/* 241:    */           }
/* 242:264 */           addErrorMessage(getLanguageController().getMensaje("msg_error_serie_no_encontrada") + " " + sbSeries.toString().substring(1));
/* 243:    */         }
/* 244:    */       }
/* 245:268 */       totalizarSeries(this.inventarioProducto);
/* 246:    */     }
/* 247:    */     catch (IOException e)
/* 248:    */     {
/* 249:271 */       addErrorMessage(getLanguageController().getMensaje("msg_error_cargar_datos"));
/* 250:    */     }
/* 251:    */   }
/* 252:    */   
/* 253:    */   public List<SerieProducto> autocompletarSerie(String consulta)
/* 254:    */   {
/* 255:277 */     Map<String, String> filtros = new HashMap();
/* 256:278 */     filtros.put("producto.idProducto", String.valueOf(this.inventarioProducto.getProducto().getIdProducto()));
/* 257:279 */     filtros.put("activo", String.valueOf(true));
/* 258:281 */     if ((consulta != null) && (consulta.length() > 0)) {
/* 259:282 */       filtros.put("codigo", consulta);
/* 260:    */     }
/* 261:285 */     List<SerieProducto> lista = this.servicioSerieProducto.obtenerListaCombo("codigo", true, filtros);
/* 262:    */     
/* 263:287 */     return lista;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void cerrarDialogo()
/* 267:    */   {
/* 268:291 */     totalizarSeries(this.inventarioProducto);
/* 269:292 */     RequestContext.getCurrentInstance().closeDialog(this.inventarioProducto);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public InventarioProducto getInventarioProducto()
/* 273:    */   {
/* 274:296 */     return this.inventarioProducto;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public void setInventarioProducto(InventarioProducto inventarioProducto)
/* 278:    */   {
/* 279:300 */     this.inventarioProducto = inventarioProducto;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public List<SerieInventarioProducto> getListaSerieProducto()
/* 283:    */   {
/* 284:305 */     if (this.listaSerieProducto == null)
/* 285:    */     {
/* 286:306 */       this.listaSerieProducto = new ArrayList();
/* 287:308 */       for (SerieInventarioProducto serie : this.inventarioProducto.getListaSerieProducto()) {
/* 288:309 */         if (!serie.isEliminado()) {
/* 289:310 */           this.listaSerieProducto.add(serie);
/* 290:    */         }
/* 291:    */       }
/* 292:    */     }
/* 293:316 */     return this.listaSerieProducto;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setListaSerieProducto(List<SerieInventarioProducto> listaSerieProducto)
/* 297:    */   {
/* 298:320 */     this.listaSerieProducto = listaSerieProducto;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public List<SerieInventarioProducto> getListaSerieProductoFiltrada()
/* 302:    */   {
/* 303:324 */     return this.listaSerieProductoFiltrada;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void setListaSerieProductoFiltrada(List<SerieInventarioProducto> listaSerieProductoFiltrada)
/* 307:    */   {
/* 308:328 */     this.listaSerieProductoFiltrada = listaSerieProductoFiltrada;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public DataTable getDtListaSerieProducto()
/* 312:    */   {
/* 313:332 */     return this.dtListaSerieProducto;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public void setDtListaSerieProducto(DataTable dtListaSerieProducto)
/* 317:    */   {
/* 318:336 */     this.dtListaSerieProducto = dtListaSerieProducto;
/* 319:    */   }
/* 320:    */   
/* 321:    */   public String getCodigoSerie()
/* 322:    */   {
/* 323:340 */     return this.codigoSerie;
/* 324:    */   }
/* 325:    */   
/* 326:    */   public void setCodigoSerie(String codigoSerie)
/* 327:    */   {
/* 328:344 */     this.codigoSerie = codigoSerie;
/* 329:    */   }
/* 330:    */   
/* 331:    */   public int getOperacion()
/* 332:    */   {
/* 333:348 */     return this.operacion;
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void setOperacion(int operacion)
/* 337:    */   {
/* 338:352 */     this.operacion = operacion;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public SerieProducto getSerieProducto()
/* 342:    */   {
/* 343:356 */     return this.serieProducto;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void setSerieProducto(SerieProducto serieProducto)
/* 347:    */   {
/* 348:360 */     this.serieProducto = serieProducto;
/* 349:    */   }
/* 350:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.DialogoSerieProductoBean
 * JD-Core Version:    0.7.0.1
 */