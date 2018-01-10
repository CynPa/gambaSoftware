/*   1:    */ package com.asinfo.as2.utils.reportes;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioPuntoDeVenta;
/*   4:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioCategoriaEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   7:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.entities.Empresa;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  11:    */ import com.asinfo.as2.entities.Recaudador;
/*  12:    */ import com.asinfo.as2.entities.Subempresa;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.Zona;
/*  15:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  16:    */ import com.asinfo.as2.seguridad.ServicioUsuario;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioRecaudador;
/*  19:    */ import com.asinfo.as2.ventas.configuracion.servicio.ServicioZona;
/*  20:    */ import java.util.ArrayList;
/*  21:    */ import java.util.Collections;
/*  22:    */ import java.util.Comparator;
/*  23:    */ import java.util.Date;
/*  24:    */ import java.util.HashMap;
/*  25:    */ import java.util.List;
/*  26:    */ import java.util.Map;
/*  27:    */ import javax.ejb.EJB;
/*  28:    */ import org.primefaces.event.SelectEvent;
/*  29:    */ 
/*  30:    */ public abstract class AbstractClientReportBean
/*  31:    */   extends AbstractBaseReportBean
/*  32:    */ {
/*  33:    */   private static final long serialVersionUID = 1L;
/*  34:    */   @EJB
/*  35:    */   protected ServicioPuntoDeVenta servicioPuntoDeVenta;
/*  36:    */   @EJB
/*  37:    */   protected ServicioSucursal servicioSucursal;
/*  38:    */   @EJB
/*  39:    */   protected ServicioRecaudador servicioRecaudador;
/*  40:    */   @EJB
/*  41:    */   protected ServicioUsuario servicioUsuario;
/*  42:    */   @EJB
/*  43:    */   protected ServicioEmpresa servicioEmpresa;
/*  44:    */   @EJB
/*  45:    */   protected ServicioZona servicioZona;
/*  46:    */   @EJB
/*  47:    */   protected ServicioCategoriaEmpresa servicioCategoriaEmpresa;
/*  48:    */   protected CategoriaEmpresa categoriaEmpresa;
/*  49:    */   protected Empresa empresa;
/*  50:    */   protected Recaudador recaudador;
/*  51:    */   protected Subempresa subempresa;
/*  52:    */   protected EntidadUsuario agenteComercial;
/*  53:    */   protected Sucursal sucursal;
/*  54:    */   protected PuntoDeVenta puntoVenta;
/*  55:    */   protected boolean indicadorResumen;
/*  56: 81 */   protected Date fechaHasta = new Date();
/*  57: 82 */   protected Date fechaDesde = new Date();
/*  58:    */   protected Zona zona;
/*  59:    */   protected List<Sucursal> listaSucursal;
/*  60:    */   protected List<PuntoDeVenta> listaPuntoVenta;
/*  61:    */   protected List<Empresa> listaClientes;
/*  62:    */   protected List<Subempresa> listaSubempresa;
/*  63:    */   protected List<Recaudador> listaRecaudador;
/*  64:    */   protected List<EntidadUsuario> listaAgenteComercial;
/*  65:    */   protected List<Zona> listaZona;
/*  66:    */   
/*  67:    */   public void actualizarClienteListener(SelectEvent event)
/*  68:    */   {
/*  69:103 */     Empresa empresa = (Empresa)event.getObject();
/*  70:104 */     setEmpresa(empresa);
/*  71:105 */     this.listaSubempresa = this.servicioEmpresa.obtenerListaComboSubEmpresa(empresa.getId(), false);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public List<Empresa> autocompletarClientes(String consulta)
/*  75:    */   {
/*  76:116 */     return this.servicioEmpresa.autocompletarClientes(consulta, false, this.categoriaEmpresa);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void cargarPuntoVenta()
/*  80:    */   {
/*  81:123 */     if (this.sucursal != null)
/*  82:    */     {
/*  83:124 */       Map<String, String> filters = new HashMap();
/*  84:125 */       filters.put("sucursal.idSucursal", String.valueOf(this.sucursal.getId()));
/*  85:126 */       this.listaPuntoVenta = this.servicioPuntoDeVenta.obtenerListaCombo("nombre", true, filters);
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89:128 */       setListaPuntoVenta(new ArrayList());
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void actualizarCategoriaEmpresa()
/*  94:    */   {
/*  95:133 */     this.empresa = null;
/*  96:134 */     this.subempresa = null;
/*  97:135 */     this.listaSubempresa = new ArrayList();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public List<CategoriaEmpresa> getListaCategoriaEmpresa()
/* 101:    */   {
/* 102:139 */     Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 103:140 */     List<CategoriaEmpresa> lista = this.servicioCategoriaEmpresa.obtenerListaCombo("nombre", true, filtros);
/* 104:141 */     return lista;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Empresa getEmpresa()
/* 108:    */   {
/* 109:148 */     return this.empresa;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setEmpresa(Empresa empresa)
/* 113:    */   {
/* 114:156 */     this.empresa = empresa;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Recaudador getRecaudador()
/* 118:    */   {
/* 119:163 */     return this.recaudador;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setRecaudador(Recaudador recaudador)
/* 123:    */   {
/* 124:171 */     this.recaudador = recaudador;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Subempresa getSubempresa()
/* 128:    */   {
/* 129:178 */     return this.subempresa;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setSubempresa(Subempresa subempresa)
/* 133:    */   {
/* 134:186 */     this.subempresa = subempresa;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public EntidadUsuario getAgenteComercial()
/* 138:    */   {
/* 139:193 */     return this.agenteComercial;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setAgenteComercial(EntidadUsuario agenteComercial)
/* 143:    */   {
/* 144:201 */     this.agenteComercial = agenteComercial;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Sucursal getSucursal()
/* 148:    */   {
/* 149:208 */     return this.sucursal;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setSucursal(Sucursal sucursal)
/* 153:    */   {
/* 154:216 */     this.sucursal = sucursal;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public PuntoDeVenta getPuntoVenta()
/* 158:    */   {
/* 159:223 */     return this.puntoVenta;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setPuntoVenta(PuntoDeVenta puntoVenta)
/* 163:    */   {
/* 164:231 */     this.puntoVenta = puntoVenta;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<Sucursal> getListaSucursal()
/* 168:    */   {
/* 169:238 */     if (this.listaSucursal == null)
/* 170:    */     {
/* 171:239 */       Map<String, String> filters = new HashMap();
/* 172:240 */       filters.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getIdOrganizacion()));
/* 173:241 */       this.listaSucursal = this.servicioSucursal.obtenerListaCombo("nombre", true, filters);
/* 174:    */     }
/* 175:244 */     return this.listaSucursal;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setListaSucursal(List<Sucursal> listaSucursal)
/* 179:    */   {
/* 180:252 */     this.listaSucursal = listaSucursal;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public List<PuntoDeVenta> getListaPuntoVenta()
/* 184:    */   {
/* 185:259 */     return this.listaPuntoVenta;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void setListaPuntoVenta(List<PuntoDeVenta> listaPuntoVenta)
/* 189:    */   {
/* 190:267 */     this.listaPuntoVenta = listaPuntoVenta;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public List<Empresa> getListaClientes()
/* 194:    */   {
/* 195:274 */     if (this.listaClientes == null) {
/* 196:275 */       this.listaClientes = this.servicioEmpresa.obtenerClientes();
/* 197:    */     }
/* 198:278 */     return this.listaClientes;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setListaClientes(List<Empresa> listaClientes)
/* 202:    */   {
/* 203:286 */     this.listaClientes = listaClientes;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public List<Subempresa> getListaSubempresa()
/* 207:    */   {
/* 208:293 */     if (this.listaSubempresa == null) {
/* 209:294 */       this.listaSubempresa = new ArrayList();
/* 210:    */     }
/* 211:297 */     return this.listaSubempresa;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void setListaSubempresa(List<Subempresa> listaSubempresa)
/* 215:    */   {
/* 216:305 */     this.listaSubempresa = listaSubempresa;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public List<Recaudador> getListaRecaudador()
/* 220:    */   {
/* 221:312 */     if (this.listaRecaudador == null) {
/* 222:313 */       this.listaRecaudador = this.servicioRecaudador.obtenerListaCombo("nombre", true, null);
/* 223:    */     }
/* 224:316 */     return this.listaRecaudador;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setListaRecaudador(List<Recaudador> listaRecaudador)
/* 228:    */   {
/* 229:324 */     this.listaRecaudador = listaRecaudador;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public List<EntidadUsuario> getListaAgenteComercial()
/* 233:    */   {
/* 234:331 */     this.listaAgenteComercial = new ArrayList();
/* 235:332 */     this.listaAgenteComercial = this.servicioUsuario.getEntidadUsuario(AppUtil.getOrganizacion().getId(), true, AppUtil.getSucursal());
/* 236:333 */     Collections.sort(this.listaAgenteComercial, new Comparator()
/* 237:    */     {
/* 238:    */       public int compare(EntidadUsuario o1, EntidadUsuario o2)
/* 239:    */       {
/* 240:336 */         return o1.getNombre2().compareTo(o2.getNombre2());
/* 241:    */       }
/* 242:339 */     });
/* 243:340 */     return this.listaAgenteComercial;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public void setListaAgenteComercial(List<EntidadUsuario> listaAgenteComercial)
/* 247:    */   {
/* 248:348 */     this.listaAgenteComercial = listaAgenteComercial;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public boolean isIndicadorResumen()
/* 252:    */   {
/* 253:355 */     return this.indicadorResumen;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public void setIndicadorResumen(boolean indicadorResumen)
/* 257:    */   {
/* 258:363 */     this.indicadorResumen = indicadorResumen;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public Date getFechaHasta()
/* 262:    */   {
/* 263:370 */     return this.fechaHasta;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void setFechaHasta(Date fechaHasta)
/* 267:    */   {
/* 268:378 */     this.fechaHasta = fechaHasta;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public Date getFechaDesde()
/* 272:    */   {
/* 273:385 */     return this.fechaDesde;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setFechaDesde(Date fechaDesde)
/* 277:    */   {
/* 278:393 */     this.fechaDesde = fechaDesde;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public Zona getZona()
/* 282:    */   {
/* 283:400 */     return this.zona;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setZona(Zona zona)
/* 287:    */   {
/* 288:408 */     this.zona = zona;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public List<Zona> getListaZona()
/* 292:    */   {
/* 293:415 */     if (null == this.listaZona)
/* 294:    */     {
/* 295:416 */       HashMap<String, String> filtros = new HashMap();
/* 296:417 */       filtros.put("idOrganizacion", String.valueOf(AppUtil.getOrganizacion().getId()));
/* 297:418 */       this.listaZona = this.servicioZona.obtenerListaCombo("nombre", true, filtros);
/* 298:    */     }
/* 299:421 */     return this.listaZona;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public void setListaZona(List<Zona> listaZona)
/* 303:    */   {
/* 304:429 */     this.listaZona = listaZona;
/* 305:    */   }
/* 306:    */   
/* 307:    */   public CategoriaEmpresa getCategoriaEmpresa()
/* 308:    */   {
/* 309:433 */     return this.categoriaEmpresa;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public void setCategoriaEmpresa(CategoriaEmpresa categoriaEmpresa)
/* 313:    */   {
/* 314:437 */     this.categoriaEmpresa = categoriaEmpresa;
/* 315:    */   }
/* 316:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.utils.reportes.AbstractClientReportBean
 * JD-Core Version:    0.7.0.1
 */