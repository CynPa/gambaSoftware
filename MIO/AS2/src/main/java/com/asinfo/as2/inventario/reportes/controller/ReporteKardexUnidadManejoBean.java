/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.DetalleMovimientoUnidadManejo;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Organizacion;
/*   9:    */ import com.asinfo.as2.entities.Subempresa;
/*  10:    */ import com.asinfo.as2.entities.Sucursal;
/*  11:    */ import com.asinfo.as2.entities.Transportista;
/*  12:    */ import com.asinfo.as2.entities.UnidadManejo;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  14:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteMovimientoUnidadManejo;
/*  15:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*  16:    */ import com.asinfo.as2.util.AppUtil;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import java.util.ArrayList;
/*  19:    */ import java.util.Calendar;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.Iterator;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.annotation.PostConstruct;
/*  26:    */ import javax.ejb.EJB;
/*  27:    */ import javax.faces.bean.ManagedBean;
/*  28:    */ import javax.faces.bean.ViewScoped;
/*  29:    */ import org.primefaces.event.SelectEvent;
/*  30:    */ 
/*  31:    */ @ManagedBean
/*  32:    */ @ViewScoped
/*  33:    */ public class ReporteKardexUnidadManejoBean
/*  34:    */   extends PageControllerAS2
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 1L;
/*  37:    */   @EJB
/*  38:    */   private ServicioReporteMovimientoUnidadManejo servicioReporteMovimientoUnidadManejo;
/*  39:    */   @EJB
/*  40:    */   private ServicioEmpresa servicioEmpresa;
/*  41:    */   @EJB
/*  42:    */   private ServicioSucursal servicioSucursal;
/*  43:    */   @EJB
/*  44:    */   private ServicioTransportista servicioTransportista;
/*  45:    */   @EJB
/*  46:    */   private ServicioGenerico<UnidadManejo> servicioUnidadManejo;
/*  47:    */   private Empresa empresa;
/*  48:    */   private Sucursal sucursal;
/*  49:    */   private Transportista transportista;
/*  50:    */   private Subempresa subempresa;
/*  51:    */   private UnidadManejo unidadManejo;
/*  52:    */   protected List<Transportista> listaTransportista;
/*  53:    */   private List<Sucursal> listaSucursal;
/*  54:    */   private List<Subempresa> listaSubempresa;
/*  55:    */   private List<UnidadManejo> listaUnidadManejo;
/*  56:    */   private List<UnidadManejo> listaResult;
/*  57:    */   private Date fechaDesde;
/*  58:    */   private Date fechaHasta;
/*  59:    */   
/*  60:    */   @PostConstruct
/*  61:    */   public void init()
/*  62:    */   {
/*  63: 68 */     Calendar calfechaDesde = Calendar.getInstance();
/*  64: 69 */     calfechaDesde.set(Calendar.getInstance().get(1), Calendar.getInstance().get(2), 1);
/*  65: 70 */     this.fechaDesde = calfechaDesde.getTime();
/*  66: 71 */     this.fechaHasta = FuncionesUtiles.getFechaFinMes(Calendar.getInstance().getTime());
/*  67:    */   }
/*  68:    */   
/*  69:    */   public String procesar()
/*  70:    */   {
/*  71: 75 */     this.listaResult = new ArrayList();
/*  72: 76 */     List<Object[]> listSaldoInicial = this.servicioReporteMovimientoUnidadManejo.getSalgoInicialUnidadManejo(AppUtil.getOrganizacion().getId(), 
/*  73: 77 */       getSucursal(), getTransportista(), getEmpresa(), getSubempresa(), getUnidadManejo(), this.fechaDesde, this.fechaHasta);
/*  74: 78 */     Map<Integer, UnidadManejo> hashUnidadManejo = new HashMap();
/*  75: 79 */     for (Iterator localIterator1 = listSaldoInicial.iterator(); localIterator1.hasNext();)
/*  76:    */     {
/*  77: 79 */       sIni = (Object[])localIterator1.next();
/*  78:    */       
/*  79: 81 */       UnidadManejo um = (UnidadManejo)hashUnidadManejo.get(Integer.valueOf(Integer.parseInt(sIni[2].toString())));
/*  80: 82 */       if (um == null)
/*  81:    */       {
/*  82: 83 */         um = (UnidadManejo)this.servicioUnidadManejo.cargarDetalle(UnidadManejo.class, Integer.parseInt(sIni[2].toString()), null);
/*  83: 84 */         hashUnidadManejo.put(Integer.valueOf(um.getId()), um);
/*  84:    */       }
/*  85: 86 */       DetalleMovimientoUnidadManejo dmum = new DetalleMovimientoUnidadManejo();
/*  86: 87 */       dmum.setSaldo(Integer.parseInt(sIni[0].toString()));
/*  87: 88 */       dmum.setDescripcion(sIni[1].toString());
/*  88: 89 */       dmum.setUnidadManejo(um);
/*  89: 90 */       um.getListaDetalleMovimientoUnidadManejo().add(dmum);
/*  90: 91 */       hashUnidadManejo.put(Integer.valueOf(um.getId()), um);
/*  91:    */     }
/*  92:    */     Object[] sIni;
/*  93: 94 */     Object listDetalles = this.servicioReporteMovimientoUnidadManejo.getReporteKardexUnidadManejo(
/*  94: 95 */       AppUtil.getOrganizacion().getId(), getSucursal(), getTransportista(), getEmpresa(), getSubempresa(), getUnidadManejo(), this.fechaDesde, this.fechaHasta);
/*  95: 97 */     for (DetalleMovimientoUnidadManejo detalleMovimientoUnidadManejo : (List)listDetalles) {
/*  96: 98 */       if (hashUnidadManejo.get(Integer.valueOf(detalleMovimientoUnidadManejo.getUnidadManejo().getId())) != null)
/*  97:    */       {
/*  98:100 */         ((UnidadManejo)hashUnidadManejo.get(Integer.valueOf(detalleMovimientoUnidadManejo.getUnidadManejo().getId()))).getListaDetalleMovimientoUnidadManejo().add(detalleMovimientoUnidadManejo);
/*  99:    */       }
/* 100:    */       else
/* 101:    */       {
/* 102:102 */         hashUnidadManejo.put(Integer.valueOf(detalleMovimientoUnidadManejo.getUnidadManejo().getId()), detalleMovimientoUnidadManejo.getUnidadManejo());
/* 103:103 */         ((UnidadManejo)hashUnidadManejo.get(Integer.valueOf(detalleMovimientoUnidadManejo.getUnidadManejo().getId()))).getListaDetalleMovimientoUnidadManejo()
/* 104:104 */           .add(detalleMovimientoUnidadManejo);
/* 105:    */       }
/* 106:    */     }
/* 107:107 */     for (UnidadManejo um : hashUnidadManejo.values())
/* 108:    */     {
/* 109:108 */       int total = 0;
/* 110:109 */       int totalIngresos = 0;
/* 111:110 */       int totalEgresos = 0;
/* 112:111 */       for (DetalleMovimientoUnidadManejo detalleMovimientoUnidadManejo : um.getListaDetalleMovimientoUnidadManejo())
/* 113:    */       {
/* 114:112 */         if ("Saldo Inicial".equals(detalleMovimientoUnidadManejo.getDescripcion())) {
/* 115:113 */           total += detalleMovimientoUnidadManejo.getSaldo();
/* 116:    */         }
/* 117:115 */         total += detalleMovimientoUnidadManejo.getCantidad() * detalleMovimientoUnidadManejo.getOperacion();
/* 118:116 */         if (!"Saldo Inicial".equals(detalleMovimientoUnidadManejo.getDescripcion()))
/* 119:    */         {
/* 120:117 */           detalleMovimientoUnidadManejo.setSaldo(total);
/* 121:118 */           if (detalleMovimientoUnidadManejo.getOperacion() == 1) {
/* 122:119 */             totalIngresos += detalleMovimientoUnidadManejo.getCantidad();
/* 123:    */           } else {
/* 124:121 */             totalEgresos += detalleMovimientoUnidadManejo.getCantidad();
/* 125:    */           }
/* 126:    */         }
/* 127:    */       }
/* 128:126 */       DetalleMovimientoUnidadManejo dmum = new DetalleMovimientoUnidadManejo();
/* 129:127 */       dmum.setCantidad(totalIngresos);
/* 130:128 */       dmum.setCantidadCliente(totalEgresos);
/* 131:129 */       dmum.setSaldo(total);
/* 132:130 */       dmum.setUnidadManejo(um);
/* 133:131 */       dmum.setDescripcion("Total");
/* 134:132 */       ((UnidadManejo)hashUnidadManejo.get(Integer.valueOf(um.getId()))).getListaDetalleMovimientoUnidadManejo().add(dmum);
/* 135:    */     }
/* 136:134 */     this.listaResult.addAll(hashUnidadManejo.values());
/* 137:135 */     return null;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String editar()
/* 141:    */   {
/* 142:140 */     return null;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String guardar()
/* 146:    */   {
/* 147:145 */     return null;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public String eliminar()
/* 151:    */   {
/* 152:150 */     return null;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public String limpiar()
/* 156:    */   {
/* 157:155 */     return null;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String cargarDatos()
/* 161:    */   {
/* 162:160 */     return null;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Date getFechaDesde()
/* 166:    */   {
/* 167:164 */     return this.fechaDesde;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setFechaDesde(Date fechaDesde)
/* 171:    */   {
/* 172:168 */     this.fechaDesde = fechaDesde;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Date getFechaHasta()
/* 176:    */   {
/* 177:172 */     return this.fechaHasta;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setFechaHasta(Date fechaHasta)
/* 181:    */   {
/* 182:176 */     this.fechaHasta = fechaHasta;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public List<Empresa> autocompletarClientes(String consulta)
/* 186:    */   {
/* 187:180 */     return this.servicioEmpresa.autocompletarClientes(consulta, true);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void actualizarCliente(SelectEvent event)
/* 191:    */   {
/* 192:184 */     setEmpresa((Empresa)event.getObject());
/* 193:185 */     cargarSubempresas();
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void cargarSubempresas()
/* 197:    */   {
/* 198:189 */     if (getEmpresa() != null) {
/* 199:190 */       this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(getEmpresa().getId());
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   public Empresa getEmpresa()
/* 204:    */   {
/* 205:195 */     return this.empresa;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void setEmpresa(Empresa empresa)
/* 209:    */   {
/* 210:199 */     this.empresa = empresa;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public Sucursal getSucursal()
/* 214:    */   {
/* 215:203 */     return this.sucursal;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setSucursal(Sucursal sucursal)
/* 219:    */   {
/* 220:207 */     this.sucursal = sucursal;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Transportista getTransportista()
/* 224:    */   {
/* 225:211 */     return this.transportista;
/* 226:    */   }
/* 227:    */   
/* 228:    */   public void setTransportista(Transportista transportista)
/* 229:    */   {
/* 230:215 */     this.transportista = transportista;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Subempresa getSubempresa()
/* 234:    */   {
/* 235:219 */     return this.subempresa;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public void setSubempresa(Subempresa subempresa)
/* 239:    */   {
/* 240:223 */     this.subempresa = subempresa;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public List<Transportista> getListaTransportista()
/* 244:    */   {
/* 245:228 */     if (this.listaTransportista == null)
/* 246:    */     {
/* 247:229 */       HashMap<String, String> filters = new HashMap();
/* 248:230 */       filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 249:231 */       filters.put("activo", "true");
/* 250:232 */       filters.put("usuario.idUsuario", "!=0");
/* 251:233 */       this.listaTransportista = this.servicioTransportista.obtenerListaCombo("nombre", true, filters);
/* 252:    */     }
/* 253:236 */     return this.listaTransportista;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setListaTransportista(List<Transportista> listaTransportista)
/* 257:    */   {
/* 258:240 */     this.listaTransportista = listaTransportista;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public List<Sucursal> getListaSucursal()
/* 262:    */   {
/* 263:244 */     if (this.listaSucursal == null) {
/* 264:245 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, null);
/* 265:    */     }
/* 266:247 */     return this.listaSucursal;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 270:    */   {
/* 271:251 */     this.listaSucursal = listaSucursal;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public List<Subempresa> getListaSubempresa()
/* 275:    */   {
/* 276:258 */     if (this.listaSubempresa == null) {
/* 277:259 */       this.listaSubempresa = new ArrayList();
/* 278:    */     }
/* 279:261 */     return this.listaSubempresa;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 283:    */   {
/* 284:269 */     this.listaSubempresa = listaSubempresa;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public UnidadManejo getUnidadManejo()
/* 288:    */   {
/* 289:273 */     return this.unidadManejo;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public void setUnidadManejo(UnidadManejo unidadManejo)
/* 293:    */   {
/* 294:277 */     this.unidadManejo = unidadManejo;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public List<UnidadManejo> getListaUnidadManejo()
/* 298:    */   {
/* 299:281 */     if (this.listaUnidadManejo == null) {
/* 300:282 */       this.listaUnidadManejo = this.servicioUnidadManejo.obtenerListaCombo(UnidadManejo.class, "nombre", true, null);
/* 301:    */     }
/* 302:283 */     return this.listaUnidadManejo;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setListaUnidadManejo(List<UnidadManejo> listaUnidadManejo)
/* 306:    */   {
/* 307:287 */     this.listaUnidadManejo = listaUnidadManejo;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public List<UnidadManejo> getListaResult()
/* 311:    */   {
/* 312:291 */     return this.listaResult;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setListaResult(List<UnidadManejo> listaResult)
/* 316:    */   {
/* 317:295 */     this.listaResult = listaResult;
/* 318:    */   }
/* 319:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteKardexUnidadManejoBean
 * JD-Core Version:    0.7.0.1
 */