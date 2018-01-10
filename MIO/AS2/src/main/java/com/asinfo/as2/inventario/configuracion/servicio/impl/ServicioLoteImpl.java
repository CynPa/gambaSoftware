/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   4:    */ import com.asinfo.as2.dao.LoteDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.SecuenciaAS2Service;
/*   6:    */ import com.asinfo.as2.entities.Atributo;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.Lote;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioLote;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  15:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  16:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  17:    */ import com.asinfo.as2.util.AppUtil;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import java.util.ArrayList;
/*  20:    */ import java.util.Date;
/*  21:    */ import java.util.HashMap;
/*  22:    */ import java.util.List;
/*  23:    */ import java.util.Map;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.ejb.SessionContext;
/*  26:    */ import javax.ejb.Stateless;
/*  27:    */ import javax.ejb.TransactionAttribute;
/*  28:    */ import javax.ejb.TransactionAttributeType;
/*  29:    */ 
/*  30:    */ @Stateless
/*  31:    */ public class ServicioLoteImpl
/*  32:    */   extends AbstractServicioAS2
/*  33:    */   implements ServicioLote
/*  34:    */ {
/*  35:    */   @EJB
/*  36:    */   private LoteDao loteDao;
/*  37:    */   @EJB
/*  38:    */   private SecuenciaAS2Service secuenciaAS2Service;
/*  39:    */   @EJB
/*  40:    */   private ServicioProducto servicioProducto;
/*  41:    */   @EJB
/*  42:    */   private ServicioAtributo servicioAtributo;
/*  43:    */   @EJB
/*  44:    */   private ServicioOrganizacion servicioOrganizacion;
/*  45:    */   
/*  46:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  47:    */   public void guardar(Lote lote)
/*  48:    */     throws ExcepcionAS2Inventario, AS2Exception
/*  49:    */   {
/*  50:    */     try
/*  51:    */     {
/*  52: 72 */       validar(lote);
/*  53: 73 */       this.loteDao.guardar(lote);
/*  54:    */     }
/*  55:    */     catch (AS2Exception e)
/*  56:    */     {
/*  57: 75 */       this.context.setRollbackOnly();
/*  58: 76 */       throw e;
/*  59:    */     }
/*  60:    */     catch (Exception e)
/*  61:    */     {
/*  62: 78 */       this.context.setRollbackOnly();
/*  63: 79 */       throw new ExcepcionAS2Inventario(e);
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void validar(Lote lote)
/*  68:    */     throws AS2Exception
/*  69:    */   {
/*  70: 84 */     if (lote.isIndicadorMovimientoInterno())
/*  71:    */     {
/*  72: 85 */       lote = this.loteDao.verificarLote(lote.getIdOrganizacion(), lote.getCodigo(), lote.getProducto(), lote.getIdLote());
/*  73: 86 */       if (lote != null) {
/*  74: 87 */         throw new AS2Exception("msg_error_codigo_repetido", new String[] { lote.getCodigo() });
/*  75:    */       }
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void eliminar(Lote lote)
/*  80:    */   {
/*  81: 99 */     this.loteDao.eliminar(lote);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Lote buscarPorId(int idLote)
/*  85:    */   {
/*  86:110 */     return (Lote)this.loteDao.buscarPorId(Integer.valueOf(idLote));
/*  87:    */   }
/*  88:    */   
/*  89:    */   public List<Lote> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  90:    */   {
/*  91:120 */     return this.loteDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  92:    */   }
/*  93:    */   
/*  94:    */   public List<Lote> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  95:    */   {
/*  96:130 */     return this.loteDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int contarPorCriterio(Map<String, String> filters)
/* 100:    */   {
/* 101:140 */     return this.loteDao.contarPorCriterio(filters);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Lote cargarDetalle(int idLote)
/* 105:    */   {
/* 106:150 */     return this.loteDao.cargarDetalle(idLote);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public List<Lote> autocompletarLote(Producto producto, String consulta)
/* 110:    */   {
/* 111:160 */     List<Lote> lista = new ArrayList();
/* 112:161 */     String sortField = "codigo";
/* 113:162 */     Map<String, String> filters = new HashMap();
/* 114:163 */     if (producto != null) {
/* 115:164 */       filters.put("producto.idProducto", String.valueOf(producto.getIdProducto()));
/* 116:    */     }
/* 117:166 */     if (consulta.contains("~LIKE"))
/* 118:    */     {
/* 119:167 */       filters.put("~LIKE", "");
/* 120:168 */       consulta = consulta.replace("~LIKE", "");
/* 121:    */     }
/* 122:170 */     if (consulta.contains("~MAX_RESULT"))
/* 123:    */     {
/* 124:171 */       filters.put("~MAX_RESULT", "");
/* 125:172 */       consulta = consulta.replace("~MAX_RESULT", "");
/* 126:    */     }
/* 127:174 */     filters.put("codigo", consulta.trim());
/* 128:175 */     lista = this.loteDao.obtenerListaCombo(sortField, true, filters);
/* 129:176 */     return lista;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Lote buscarPorCodigo(String codigo)
/* 133:    */     throws ExcepcionAS2
/* 134:    */   {
/* 135:186 */     return this.loteDao.buscarPorCodigo(codigo);
/* 136:    */   }
/* 137:    */   
/* 138:    */   public Lote buscarPorCodigoIndicadorMovimiento(String codigo, boolean indicadorMovimiento)
/* 139:    */     throws ExcepcionAS2
/* 140:    */   {
/* 141:196 */     return this.loteDao.buscarPorCodigoIndicadorMovimiento(codigo, indicadorMovimiento);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public List<Lote> autocompletarLote(String consulta)
/* 145:    */   {
/* 146:206 */     List<Lote> lista = new ArrayList();
/* 147:207 */     String sortField = "codigo";
/* 148:208 */     Map<String, String> filters = new HashMap();
/* 149:209 */     filters.put("codigo", consulta.trim());
/* 150:210 */     lista = this.loteDao.obtenerListaCombo(sortField, true, filters);
/* 151:211 */     return lista;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public Lote buscarPorCodigo(String codigo, Producto producto)
/* 155:    */     throws ExcepcionAS2
/* 156:    */   {
/* 157:221 */     return this.loteDao.buscarPorCodigo(codigo, producto);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Lote buscarPorCodigoProductoProveedor(String codigo, Producto producto, Empresa empresa)
/* 161:    */     throws ExcepcionAS2
/* 162:    */   {
/* 163:231 */     return this.loteDao.buscarPorCodigoProductoProveedor(codigo, producto, empresa);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Lote crearLoteInterno(int idOrganizacion, Producto producto, String codigo, boolean guardarLote)
/* 167:    */     throws ExcepcionAS2, AS2Exception
/* 168:    */   {
/* 169:236 */     return crearLote(idOrganizacion, producto, codigo, true, null, null, null, guardarLote);
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Lote crearLote(int idOrganizacion, Producto producto, String codigo, boolean indicadorMovimientoInterno, Empresa empresa, Date fechaFabricacion, Date fechaCaducidad, boolean guardarLote)
/* 173:    */     throws ExcepcionAS2, AS2Exception
/* 174:    */   {
/* 175:242 */     if (producto.isIndicadorLote())
/* 176:    */     {
/* 177:244 */       Lote lote = new Lote();
/* 178:245 */       lote.setActivo(true);
/* 179:246 */       lote.setIdOrganizacion(idOrganizacion);
/* 180:247 */       lote.setProducto(producto);
/* 181:248 */       lote.setIndicadorMovimientoInterno(indicadorMovimientoInterno);
/* 182:249 */       fechaFabricacion = fechaFabricacion == null ? new Date() : fechaFabricacion;
/* 183:250 */       lote.setFechaCaducidad(fechaFabricacion);
/* 184:251 */       lote.setFechaFabricacion(fechaCaducidad == null ? 
/* 185:252 */         FuncionesUtiles.sumarFechaDiasMeses(fechaFabricacion, producto.getDiasCaducidad().intValue()) : producto.getDiasCaducidad() == null ? fechaFabricacion : fechaCaducidad);
/* 186:253 */       lote.setEmpresa(empresa);
/* 187:255 */       if (codigo == null)
/* 188:    */       {
/* 189:256 */         Integer numeroSerie = this.servicioOrganizacion.actualizarSecuenciaInicioSerie(AppUtil.getOrganizacion());
/* 190:257 */         codigo = producto.getPrefijoLote() + String.valueOf(FuncionesUtiles.completarALaIzquierda('0', 10, numeroSerie.toString()));
/* 191:    */       }
/* 192:259 */       lote.setCodigo(codigo);
/* 193:261 */       if (guardarLote) {
/* 194:262 */         guardar(lote);
/* 195:    */       } else {
/* 196:264 */         actualizarAtributos(lote);
/* 197:    */       }
/* 198:267 */       return lote;
/* 199:    */     }
/* 200:269 */     return null;
/* 201:    */   }
/* 202:    */   
/* 203:    */   private void actualizarAtributos(Lote lote)
/* 204:    */   {
/* 205:275 */     Producto producto = this.servicioProducto.cargarProductoConAtributoInstancia(lote.getProducto().getIdProducto());
/* 206:    */     
/* 207:277 */     Atributo atributo = producto.getAtributoProduccion1();
/* 208:278 */     if (atributo != null)
/* 209:    */     {
/* 210:279 */       lote.setAtributo1(atributo);
/* 211:280 */       lote.setValorAtributo1(atributo.getValorAtributoPredeterminado());
/* 212:    */     }
/* 213:283 */     atributo = producto.getAtributoProduccion2();
/* 214:284 */     if (atributo != null)
/* 215:    */     {
/* 216:285 */       lote.setAtributo2(atributo);
/* 217:286 */       lote.setValorAtributo2(atributo.getValorAtributoPredeterminado());
/* 218:    */     }
/* 219:289 */     atributo = producto.getAtributoProduccion3();
/* 220:290 */     if (atributo != null)
/* 221:    */     {
/* 222:291 */       lote.setAtributo3(atributo);
/* 223:292 */       lote.setValorAtributo3(atributo.getValorAtributoPredeterminado());
/* 224:    */     }
/* 225:295 */     atributo = producto.getAtributoProduccion4();
/* 226:296 */     if (atributo != null)
/* 227:    */     {
/* 228:297 */       lote.setAtributo4(atributo);
/* 229:298 */       lote.setValorAtributo4(atributo.getValorAtributoPredeterminado());
/* 230:    */     }
/* 231:301 */     atributo = producto.getAtributoProduccion5();
/* 232:302 */     if (atributo != null)
/* 233:    */     {
/* 234:303 */       lote.setAtributo5(atributo);
/* 235:304 */       lote.setValorAtributo5(atributo.getValorAtributoPredeterminado());
/* 236:    */     }
/* 237:307 */     atributo = producto.getAtributoProduccion6();
/* 238:308 */     if (atributo != null)
/* 239:    */     {
/* 240:309 */       lote.setAtributo6(atributo);
/* 241:310 */       lote.setValorAtributo6(atributo.getValorAtributoPredeterminado());
/* 242:    */     }
/* 243:313 */     atributo = producto.getAtributoProduccion7();
/* 244:314 */     if (atributo != null)
/* 245:    */     {
/* 246:315 */       lote.setAtributo7(atributo);
/* 247:316 */       lote.setValorAtributo7(atributo.getValorAtributoPredeterminado());
/* 248:    */     }
/* 249:319 */     atributo = producto.getAtributoProduccion8();
/* 250:320 */     if (atributo != null)
/* 251:    */     {
/* 252:321 */       lote.setAtributo8(atributo);
/* 253:322 */       lote.setValorAtributo8(atributo.getValorAtributoPredeterminado());
/* 254:    */     }
/* 255:325 */     atributo = producto.getAtributoProduccion9();
/* 256:326 */     if (atributo != null)
/* 257:    */     {
/* 258:327 */       lote.setAtributo9(atributo);
/* 259:328 */       lote.setValorAtributo9(atributo.getValorAtributoPredeterminado());
/* 260:    */     }
/* 261:331 */     atributo = producto.getAtributoProduccion10();
/* 262:332 */     if (atributo != null)
/* 263:    */     {
/* 264:333 */       lote.setAtributo10(atributo);
/* 265:334 */       lote.setValorAtributo10(atributo.getValorAtributoPredeterminado());
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public Lote getAtributosLote(int idLote, int numeroAtributos)
/* 270:    */   {
/* 271:341 */     return this.loteDao.getAtributosLote(idLote, numeroAtributos);
/* 272:    */   }
/* 273:    */   
/* 274:    */   public void actualizarAtributoEntidad(Lote lote, HashMap<String, Object> campos)
/* 275:    */   {
/* 276:346 */     this.loteDao.actualizarAtributoEntidad(lote, campos);
/* 277:    */   }
/* 278:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioLoteImpl
 * JD-Core Version:    0.7.0.1
 */