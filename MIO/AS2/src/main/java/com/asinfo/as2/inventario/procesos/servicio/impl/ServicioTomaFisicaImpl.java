/*   1:    */ package com.asinfo.as2.inventario.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   4:    */ import com.asinfo.as2.dao.DetalleTomaFisicaDao;
/*   5:    */ import com.asinfo.as2.dao.TomaFisicaDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*   7:    */ import com.asinfo.as2.entities.DetalleMovimientoInventario;
/*   8:    */ import com.asinfo.as2.entities.DetalleTomaFisica;
/*   9:    */ import com.asinfo.as2.entities.Documento;
/*  10:    */ import com.asinfo.as2.entities.InventarioProducto;
/*  11:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*  12:    */ import com.asinfo.as2.entities.Organizacion;
/*  13:    */ import com.asinfo.as2.entities.Producto;
/*  14:    */ import com.asinfo.as2.entities.TomaFisica;
/*  15:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  19:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  20:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  21:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioTomaFisica;
/*  22:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*  23:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  24:    */ import com.asinfo.as2.util.AppUtil;
/*  25:    */ import java.math.BigDecimal;
/*  26:    */ import java.util.List;
/*  27:    */ import java.util.Map;
/*  28:    */ import javax.ejb.EJB;
/*  29:    */ import javax.ejb.SessionContext;
/*  30:    */ import javax.ejb.Stateless;
/*  31:    */ import javax.ejb.TransactionAttribute;
/*  32:    */ import javax.ejb.TransactionAttributeType;
/*  33:    */ 
/*  34:    */ @Stateless
/*  35:    */ public class ServicioTomaFisicaImpl
/*  36:    */   extends AbstractServicioAS2Financiero
/*  37:    */   implements ServicioTomaFisica
/*  38:    */ {
/*  39:    */   private static final long serialVersionUID = 596226475038969038L;
/*  40:    */   @EJB
/*  41:    */   private TomaFisicaDao tomaFisicaDao;
/*  42:    */   @EJB
/*  43:    */   private DetalleTomaFisicaDao detalleTomaFisicaDao;
/*  44:    */   @EJB
/*  45:    */   private ServicioSecuencia servicioSecuencia;
/*  46:    */   @EJB
/*  47:    */   private ServicioMovimientoInventario servicioMovimientoInventario;
/*  48:    */   @EJB
/*  49:    */   private transient ServicioVerificadorInventario servicioVerificadorInventario;
/*  50:    */   @EJB
/*  51:    */   private transient ServicioSucursal servicioSucursal;
/*  52:    */   
/*  53:    */   public void guardar(TomaFisica tomaFisica)
/*  54:    */     throws ExcepcionAS2, ExcepcionAS2Inventario
/*  55:    */   {
/*  56:    */     try
/*  57:    */     {
/*  58: 81 */       this.servicioVerificadorInventario.cantidadDetalle(tomaFisica.getListaDetalleTomaFisica());
/*  59:    */       
/*  60: 83 */       cargarSecuencia(tomaFisica);
/*  61: 84 */       for (DetalleTomaFisica detalleTomaFisica : tomaFisica.getListaDetalleTomaFisica()) {
/*  62: 85 */         this.detalleTomaFisicaDao.guardar(detalleTomaFisica);
/*  63:    */       }
/*  64: 88 */       this.tomaFisicaDao.guardar(tomaFisica);
/*  65:    */     }
/*  66:    */     catch (ExcepcionAS2 e)
/*  67:    */     {
/*  68: 91 */       throw e;
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   private void cargarSecuencia(TomaFisica tomaFisica)
/*  73:    */     throws ExcepcionAS2
/*  74:    */   {
/*  75: 98 */     if (tomaFisica.getNumero().equals(""))
/*  76:    */     {
/*  77: 99 */       String numero = "";
/*  78:100 */       numero = this.servicioSecuencia.obtenerSecuenciaDocumento(tomaFisica.getDocumento().getId(), tomaFisica.getFecha());
/*  79:101 */       tomaFisica.setNumero(numero);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  84:    */   public void finalizar(TomaFisica tomaFisica)
/*  85:    */     throws AS2Exception, ExcepcionAS2
/*  86:    */   {
/*  87:109 */     tomaFisica.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/*  88:110 */     tomaFisica = cargarDetalle(tomaFisica.getId());
/*  89:111 */     validarFinalizacionTomaFisica(tomaFisica);
/*  90:112 */     tomaFisica.setEstado(Estado.APROBADO);
/*  91:    */     
/*  92:114 */     MovimientoInventario movimientoIngreso = null;
/*  93:    */     
/*  94:116 */     MovimientoInventario movimientoEgreso = null;
/*  95:119 */     for (DetalleTomaFisica detalleTomaFisica : tomaFisica.getListaDetalleTomaFisica())
/*  96:    */     {
/*  97:120 */       if (!detalleTomaFisica.isEliminado())
/*  98:    */       {
/*  99:121 */         BigDecimal diferencia = detalleTomaFisica.getCantidadTomaFisica().subtract(detalleTomaFisica.getCantidadSistema());
/* 100:123 */         if (diferencia.compareTo(BigDecimal.ZERO) == 1)
/* 101:    */         {
/* 102:124 */           detalleTomaFisica.setTraMovimientoIngreso(diferencia);
/* 103:125 */           detalleTomaFisica.setTraMovimientoEgreso(BigDecimal.ZERO);
/* 104:    */         }
/* 105:126 */         else if (diferencia.compareTo(BigDecimal.ZERO) == -1)
/* 106:    */         {
/* 107:127 */           detalleTomaFisica.setTraMovimientoIngreso(BigDecimal.ZERO);
/* 108:128 */           detalleTomaFisica.setTraMovimientoEgreso(diferencia.multiply(new BigDecimal(-1)));
/* 109:    */         }
/* 110:    */         else
/* 111:    */         {
/* 112:130 */           detalleTomaFisica.setTraMovimientoIngreso(BigDecimal.ZERO);
/* 113:131 */           detalleTomaFisica.setTraMovimientoEgreso(BigDecimal.ZERO);
/* 114:    */         }
/* 115:    */       }
/* 116:135 */       if (detalleTomaFisica.getTraMovimientoIngreso().compareTo(BigDecimal.ZERO) == 1)
/* 117:    */       {
/* 118:136 */         if (movimientoIngreso == null)
/* 119:    */         {
/* 120:137 */           movimientoIngreso = new MovimientoInventario();
/* 121:138 */           movimientoIngreso.setIdOrganizacion(tomaFisica.getIdOrganizacion());
/* 122:139 */           movimientoIngreso.setSucursal(this.servicioSucursal.buscarPorId(Integer.valueOf(tomaFisica.getIdSucursal())));
/* 123:140 */           movimientoIngreso.setFecha(tomaFisica.getFecha());
/* 124:141 */           movimientoIngreso.setDocumento(tomaFisica.getDocumentoAjusteIngreso());
/* 125:142 */           movimientoIngreso.setDescripcion("");
/* 126:143 */           movimientoIngreso.setEstado(Estado.ELABORADO);
/* 127:144 */           movimientoIngreso.setNumero("");
/* 128:145 */           movimientoIngreso.setMotivoAjusteInventario(tomaFisica.getMotivoAjusteInventario());
/* 129:146 */           movimientoIngreso.setTomaFisica(tomaFisica);
/* 130:    */         }
/* 131:149 */         DetalleMovimientoInventario detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 132:150 */         detalleMovimientoInventario.setMovimientoInventario(movimientoIngreso);
/* 133:151 */         detalleMovimientoInventario.setBodegaOrigen(tomaFisica.getBodega());
/* 134:152 */         detalleMovimientoInventario.setProducto(detalleTomaFisica.getProducto());
/* 135:153 */         detalleMovimientoInventario.setDescripcion("");
/* 136:154 */         detalleMovimientoInventario.setCantidad(detalleTomaFisica.getTraMovimientoIngreso());
/* 137:155 */         detalleMovimientoInventario.setUnidadConversion(detalleTomaFisica.getProducto().getUnidad());
/* 138:156 */         detalleMovimientoInventario.setInventarioProducto(new InventarioProducto());
/* 139:157 */         detalleMovimientoInventario.getInventarioProducto().setLote(detalleTomaFisica.getLote());
/* 140:    */         
/* 141:159 */         movimientoIngreso.getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 142:    */       }
/* 143:160 */       else if (detalleTomaFisica.getTraMovimientoEgreso().compareTo(BigDecimal.ZERO) == 1)
/* 144:    */       {
/* 145:161 */         if (movimientoEgreso == null)
/* 146:    */         {
/* 147:162 */           movimientoEgreso = new MovimientoInventario();
/* 148:163 */           movimientoEgreso.setIdOrganizacion(tomaFisica.getIdOrganizacion());
/* 149:164 */           movimientoEgreso.setSucursal(this.servicioSucursal.buscarPorId(Integer.valueOf(tomaFisica.getIdSucursal())));
/* 150:165 */           movimientoEgreso.setFecha(tomaFisica.getFecha());
/* 151:166 */           movimientoEgreso.setDocumento(tomaFisica.getDocumentoAjusteEgreso());
/* 152:167 */           movimientoEgreso.setDescripcion("");
/* 153:168 */           movimientoEgreso.setEstado(Estado.ELABORADO);
/* 154:169 */           movimientoEgreso.setNumero("");
/* 155:170 */           movimientoEgreso.setMotivoAjusteInventario(tomaFisica.getMotivoAjusteInventario());
/* 156:171 */           movimientoEgreso.setTomaFisica(tomaFisica);
/* 157:    */         }
/* 158:173 */         DetalleMovimientoInventario detalleMovimientoInventario = new DetalleMovimientoInventario();
/* 159:174 */         detalleMovimientoInventario.setMovimientoInventario(movimientoEgreso);
/* 160:175 */         detalleMovimientoInventario.setBodegaOrigen(tomaFisica.getBodega());
/* 161:176 */         detalleMovimientoInventario.setProducto(detalleTomaFisica.getProducto());
/* 162:177 */         detalleMovimientoInventario.setDescripcion("");
/* 163:178 */         detalleMovimientoInventario.setCantidad(detalleTomaFisica.getTraMovimientoEgreso());
/* 164:179 */         detalleMovimientoInventario.setUnidadConversion(detalleTomaFisica.getProducto().getUnidad());
/* 165:180 */         detalleMovimientoInventario.setInventarioProducto(new InventarioProducto());
/* 166:181 */         detalleMovimientoInventario.getInventarioProducto().setLote(detalleTomaFisica.getLote());
/* 167:    */         
/* 168:183 */         movimientoEgreso.getDetalleMovimientosInventario().add(detalleMovimientoInventario);
/* 169:    */       }
/* 170:    */     }
/* 171:    */     try
/* 172:    */     {
/* 173:188 */       if (movimientoIngreso != null) {
/* 174:189 */         this.servicioMovimientoInventario.guardar(movimientoIngreso);
/* 175:    */       }
/* 176:192 */       if (movimientoEgreso != null) {
/* 177:193 */         this.servicioMovimientoInventario.guardar(movimientoEgreso);
/* 178:    */       }
/* 179:    */     }
/* 180:    */     catch (ExcepcionAS2Financiero e)
/* 181:    */     {
/* 182:196 */       this.context.setRollbackOnly();
/* 183:197 */       throw e;
/* 184:    */     }
/* 185:    */     catch (ExcepcionAS2 e)
/* 186:    */     {
/* 187:199 */       this.context.setRollbackOnly();
/* 188:200 */       throw e;
/* 189:    */     }
/* 190:    */     catch (AS2Exception e)
/* 191:    */     {
/* 192:202 */       this.context.setRollbackOnly();
/* 193:203 */       throw e;
/* 194:    */     }
/* 195:    */     catch (Exception e)
/* 196:    */     {
/* 197:205 */       this.context.setRollbackOnly();
/* 198:206 */       throw new ExcepcionAS2(e);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void eliminar(TomaFisica tomaFisica)
/* 203:    */   {
/* 204:217 */     tomaFisica = this.tomaFisicaDao.cargarDetalle(tomaFisica.getId());
/* 205:218 */     if (tomaFisica.getListaDetalleTomaFisica() != null) {
/* 206:219 */       for (DetalleTomaFisica detalle : tomaFisica.getListaDetalleTomaFisica())
/* 207:    */       {
/* 208:220 */         detalle.setEliminado(true);
/* 209:221 */         this.detalleTomaFisicaDao.guardar(detalle);
/* 210:    */       }
/* 211:    */     }
/* 212:224 */     tomaFisica.setEliminado(true);
/* 213:225 */     this.tomaFisicaDao.guardar(tomaFisica);
/* 214:    */   }
/* 215:    */   
/* 216:    */   public TomaFisica buscarPorId(int id)
/* 217:    */   {
/* 218:235 */     return (TomaFisica)this.tomaFisicaDao.buscarPorId(Integer.valueOf(id));
/* 219:    */   }
/* 220:    */   
/* 221:    */   public List<TomaFisica> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 222:    */   {
/* 223:245 */     return this.tomaFisicaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 224:    */   }
/* 225:    */   
/* 226:    */   public List<TomaFisica> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 227:    */   {
/* 228:255 */     return this.tomaFisicaDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 229:    */   }
/* 230:    */   
/* 231:    */   public int contarPorCriterio(Map<String, String> filters)
/* 232:    */   {
/* 233:265 */     return this.tomaFisicaDao.contarPorCriterio(filters);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public TomaFisica cargarDetalle(int idTomaFisica)
/* 237:    */   {
/* 238:275 */     return this.tomaFisicaDao.cargarDetalle(idTomaFisica);
/* 239:    */   }
/* 240:    */   
/* 241:    */   private void validarFinalizacionTomaFisica(TomaFisica tomaFisica)
/* 242:    */     throws AS2Exception
/* 243:    */   {
/* 244:279 */     for (DetalleTomaFisica detalleTomaFisica : tomaFisica.getListaDetalleTomaFisica()) {
/* 245:280 */       if ((!detalleTomaFisica.isEliminado()) && (detalleTomaFisica.getCantidadTomaFisica().compareTo(BigDecimal.ZERO) < 0)) {
/* 246:281 */         throw new AS2Exception("com.asinfo.as2.inventario.procesos.servicio.impl.ServicioTomaFisicaImpl.ERROR_TOMA_FISICA_CANTIDAD_MENOR_CERO", new String[] { "" });
/* 247:    */       }
/* 248:    */     }
/* 249:    */   }
/* 250:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.servicio.impl.ServicioTomaFisicaImpl
 * JD-Core Version:    0.7.0.1
 */