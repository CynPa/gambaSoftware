/*   1:    */ package com.asinfo.as2.ventas.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compronteselectronicos.ServicioDocumentoElectronico;
/*   4:    */ import com.asinfo.as2.compronteselectronicos.ServicioFacturaClienteSRIXML;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioOrganizacion;
/*   6:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioSucursal;
/*   7:    */ import com.asinfo.as2.dao.CuentaPorCobrarDao;
/*   8:    */ import com.asinfo.as2.dao.DetalleFacturaClienteDao;
/*   9:    */ import com.asinfo.as2.dao.FacturaClienteDao;
/*  10:    */ import com.asinfo.as2.dao.ImpuestoProductoFacturaClienteDao;
/*  11:    */ import com.asinfo.as2.dao.sri.FacturaClienteSRIDao;
/*  12:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*  13:    */ import com.asinfo.as2.datosbase.servicio.ServicioSecuencia;
/*  14:    */ import com.asinfo.as2.entities.CuentaPorCobrar;
/*  15:    */ import com.asinfo.as2.entities.DetalleFacturaCliente;
/*  16:    */ import com.asinfo.as2.entities.Documento;
/*  17:    */ import com.asinfo.as2.entities.Empresa;
/*  18:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  19:    */ import com.asinfo.as2.entities.ImpuestoProductoFacturaCliente;
/*  20:    */ import com.asinfo.as2.entities.Subempresa;
/*  21:    */ import com.asinfo.as2.entities.Sucursal;
/*  22:    */ import com.asinfo.as2.entities.Ubicacion;
/*  23:    */ import com.asinfo.as2.entities.Unidad;
/*  24:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  25:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  26:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  27:    */ import com.asinfo.as2.financiero.contabilidad.procesos.servicio.ServicioPeriodo;
/*  28:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  29:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  30:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioVerificadorInventario;
/*  31:    */ import com.asinfo.as2.servicio.AbstractServicioAS2Financiero;
/*  32:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  33:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  34:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  35:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaDebitoCliente;
/*  36:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioVerificadorVentas;
/*  37:    */ import java.io.PrintStream;
/*  38:    */ import java.math.BigDecimal;
/*  39:    */ import java.util.HashMap;
/*  40:    */ import java.util.Iterator;
/*  41:    */ import java.util.List;
/*  42:    */ import javax.ejb.EJB;
/*  43:    */ import javax.ejb.SessionContext;
/*  44:    */ import javax.ejb.Stateless;
/*  45:    */ import javax.ejb.TransactionAttribute;
/*  46:    */ import javax.ejb.TransactionAttributeType;
/*  47:    */ 
/*  48:    */ @Stateless
/*  49:    */ public class ServicioNotaDebitoClienteImpl
/*  50:    */   extends AbstractServicioAS2Financiero
/*  51:    */   implements ServicioNotaDebitoCliente
/*  52:    */ {
/*  53:    */   private static final long serialVersionUID = 1L;
/*  54:    */   @EJB
/*  55:    */   private transient ServicioPeriodo servicioPeriodo;
/*  56:    */   @EJB
/*  57:    */   private transient FacturaClienteDao facturaClienteDao;
/*  58:    */   @EJB
/*  59:    */   private transient ServicioVerificadorVentas servicioVerificadorVentas;
/*  60:    */   @EJB
/*  61:    */   private transient ImpuestoProductoFacturaClienteDao impuestoProductoFacturaClienteDao;
/*  62:    */   @EJB
/*  63:    */   private transient DetalleFacturaClienteDao detalleFacturaClienteDao;
/*  64:    */   @EJB
/*  65:    */   private transient CuentaPorCobrarDao cuentaPorCobrarDao;
/*  66:    */   @EJB
/*  67:    */   private transient ServicioSecuencia servicioSecuencia;
/*  68:    */   @EJB
/*  69:    */   private transient ServicioVerificadorInventario servicioVerificadorInventario;
/*  70:    */   @EJB
/*  71:    */   private transient FacturaClienteSRIDao facturaClienteSRIDao;
/*  72:    */   @EJB
/*  73:    */   private transient ServicioFacturaCliente servicioFacturaCliente;
/*  74:    */   @EJB
/*  75:    */   private ServicioFacturaClienteSRIXML servicioFacturaClienteSRIXML;
/*  76:    */   @EJB
/*  77:    */   private ServicioDocumentoElectronico servicioDocumentoElectronico;
/*  78:    */   @EJB
/*  79:    */   private ServicioEmpresa servicioEmpresa;
/*  80:    */   @EJB
/*  81:    */   private ServicioSucursal servicioSucursal;
/*  82:    */   @EJB
/*  83:    */   private ServicioOrganizacion servicioOrganizacion;
/*  84:    */   
/*  85:    */   @TransactionAttribute(TransactionAttributeType.REQUIRED)
/*  86:    */   public FacturaCliente guardar(FacturaCliente notaDebito)
/*  87:    */     throws ExcepcionAS2Ventas, ExcepcionAS2Inventario, ExcepcionAS2Financiero, ExcepcionAS2
/*  88:    */   {
/*  89:    */     try
/*  90:    */     {
/*  91:108 */       if ((notaDebito.getDocumento() != null) && (notaDebito.getDocumento().isIndicadorDocumentoElectronico()))
/*  92:    */       {
/*  93:110 */         int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(notaDebito.getIdOrganizacion()).booleanValue() ? 2 : 1;
/*  94:111 */         notaDebito.getFacturaClienteSRI().setAmbiente(ambiente);
/*  95:112 */         tipoEmision = 1;
/*  96:113 */         notaDebito.getFacturaClienteSRI().setEmail(notaDebito.getEmail());
/*  97:    */         
/*  98:    */ 
/*  99:116 */         notaDebito.getFacturaClienteSRI().setTipoEmision(tipoEmision);
/* 100:118 */         if (notaDebito.getSucursal() != null)
/* 101:    */         {
/* 102:119 */           Sucursal sucursal = this.servicioSucursal.cargarDetalle(notaDebito.getSucursal().getId());
/* 103:120 */           notaDebito.getFacturaClienteSRI().setDireccionSucursal(sucursal.getUbicacion().getDireccionCompleta());
/* 104:    */         }
/* 105:122 */         if (notaDebito.getIdOrganizacion() != 0)
/* 106:    */         {
/* 107:123 */           String dirMatriz = "";
/* 108:    */           try
/* 109:    */           {
/* 110:125 */             dirMatriz = this.servicioOrganizacion.obtenerDireccionMatriz(notaDebito.getIdOrganizacion());
/* 111:    */           }
/* 112:    */           catch (Exception e)
/* 113:    */           {
/* 114:127 */             dirMatriz = "N/A";
/* 115:    */           }
/* 116:129 */           notaDebito.getFacturaClienteSRI().setDireccionMatriz(dirMatriz);
/* 117:    */         }
/* 118:    */       }
/* 119:141 */       if ((notaDebito.getNumero() == null) || (notaDebito.getNumero().isEmpty()))
/* 120:    */       {
/* 121:142 */         String numero = this.servicioSecuencia.obtenerSecuencia(notaDebito.getDocumento().getSecuencia(), notaDebito.getFecha());
/* 122:143 */         notaDebito.setNumero(numero);
/* 123:    */       }
/* 124:147 */       this.servicioVerificadorInventario.cantidadDetalle(notaDebito.getListaDetalleFacturaCliente());
/* 125:    */       
/* 126:149 */       this.servicioVerificadorInventario.verificarTotalDetalle(notaDebito.getListaDetalleFacturaCliente());
/* 127:    */       
/* 128:151 */       validar(notaDebito);
/* 129:    */       
/* 130:153 */       this.servicioVerificadorVentas.actualizarCreditoUtilizado(notaDebito.getEmpresa().getCliente(), notaDebito.getTotalFactura(), false);
/* 131:    */       
/* 132:155 */       notaDebito = this.servicioFacturaCliente.generarCuentaPorCobrar(notaDebito);
/* 133:    */       
/* 134:157 */       HashMap<String, BigDecimal> mapaResumen = new HashMap();
/* 135:159 */       for (int tipoEmision = notaDebito.getListaDetalleFacturaCliente().iterator(); tipoEmision.hasNext();)
/* 136:    */       {
/* 137:159 */         dfc = (DetalleFacturaCliente)tipoEmision.next();
/* 138:161 */         for (ImpuestoProductoFacturaCliente impuestoProductoFacturaCliente : dfc.getListaImpuestoProductoFacturaCliente()) {
/* 139:162 */           this.impuestoProductoFacturaClienteDao.guardar(impuestoProductoFacturaCliente);
/* 140:    */         }
/* 141:165 */         if (!dfc.isEliminado())
/* 142:    */         {
/* 143:166 */           String unidadVenta = dfc.getUnidadVenta().getNombre();
/* 144:167 */           if (mapaResumen.containsKey(unidadVenta))
/* 145:    */           {
/* 146:168 */             BigDecimal valor = ((BigDecimal)mapaResumen.get(unidadVenta)).add(dfc.getCantidad());
/* 147:169 */             mapaResumen.put(unidadVenta, valor);
/* 148:    */           }
/* 149:    */           else
/* 150:    */           {
/* 151:171 */             mapaResumen.put(unidadVenta, dfc.getCantidad());
/* 152:    */           }
/* 153:    */         }
/* 154:174 */         this.detalleFacturaClienteDao.guardar(dfc);
/* 155:    */       }
/* 156:    */       DetalleFacturaCliente dfc;
/* 157:177 */       StringBuffer resumenUnidad = new StringBuffer();
/* 158:178 */       for (String unidadVenta : mapaResumen.keySet()) {
/* 159:179 */         resumenUnidad.append(unidadVenta).append(": ").append(String.valueOf(mapaResumen.get(unidadVenta))).append(", ");
/* 160:    */       }
/* 161:182 */       if (resumenUnidad.length() > 1) {
/* 162:183 */         notaDebito.setReferencia1(resumenUnidad.substring(0, resumenUnidad.length() - 2));
/* 163:    */       }
/* 164:186 */       for (CuentaPorCobrar cuentaPorCobrar : notaDebito.getListaCuentaPorCobrar())
/* 165:    */       {
/* 166:187 */         cuentaPorCobrar.setSaldo(cuentaPorCobrar.getValor());
/* 167:188 */         this.cuentaPorCobrarDao.guardar(cuentaPorCobrar);
/* 168:    */       }
/* 169:192 */       if (notaDebito.getFacturaClienteSRI() != null) {
/* 170:193 */         this.facturaClienteSRIDao.guardar(notaDebito.getFacturaClienteSRI());
/* 171:    */       }
/* 172:196 */       this.facturaClienteDao.guardar(notaDebito);
/* 173:197 */       this.facturaClienteDao.flush();
/* 174:    */       
/* 175:    */ 
/* 176:200 */       this.servicioSecuencia.actualizarSecuencia(notaDebito.getDocumento().getSecuencia(), notaDebito.getNumero());
/* 177:202 */       if ((notaDebito.getEmail() != null) && (!notaDebito.getEmail().isEmpty()) && 
/* 178:203 */         (!notaDebito.getEmail().equals(notaDebito.getEmpresa().getEmail1())) && 
/* 179:204 */         (notaDebito.getDocumento().isIndicadorDocumentoTributario()))
/* 180:    */       {
/* 181:206 */         Empresa empresa = null;
/* 182:207 */         if (notaDebito.getSubempresa() != null) {
/* 183:208 */           empresa = notaDebito.getSubempresa().getEmpresa();
/* 184:    */         } else {
/* 185:210 */           empresa = notaDebito.getEmpresa();
/* 186:    */         }
/* 187:213 */         this.servicioEmpresa.actualizarMails(empresa, notaDebito.getEmail(), DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 188:    */       }
/* 189:216 */       if ((!notaDebito.isIndicadorSaldoInicial()) && (notaDebito.getDocumento().isIndicadorDocumentoTributario()) && 
/* 190:217 */         (notaDebito.getDocumento().isIndicadorDocumentoElectronico()))
/* 191:    */       {
/* 192:218 */         notaDebito = this.servicioFacturaClienteSRIXML.generarClaveAcceso(null, notaDebito, true);
/* 193:219 */         boolean indicadorEnviarEmail = ParametrosSistema.isComprobantesElectronicosEnviarEmailGuardar(notaDebito.getIdOrganizacion()).booleanValue();
/* 194:220 */         if (indicadorEnviarEmail) {
/* 195:222 */           if (notaDebito.getFacturaClienteSRI().getAmbiente() == 2) {
/* 196:    */             try
/* 197:    */             {
/* 198:224 */               this.servicioFacturaCliente.enviarMail(notaDebito, notaDebito.getDocumentoElectronico(), null);
/* 199:    */             }
/* 200:    */             catch (Exception e)
/* 201:    */             {
/* 202:226 */               e.printStackTrace();
/* 203:    */               
/* 204:228 */               System.out.println("Error en la generacion del reporte y enviar por mail al cliente (Facturacion electronica)" + e
/* 205:229 */                 .getMessage());
/* 206:    */             }
/* 207:    */           }
/* 208:    */         }
/* 209:    */       }
/* 210:235 */       return notaDebito;
/* 211:    */     }
/* 212:    */     catch (ExcepcionAS2Ventas e)
/* 213:    */     {
/* 214:238 */       e.printStackTrace();
/* 215:239 */       this.context.setRollbackOnly();
/* 216:240 */       throw e;
/* 217:    */     }
/* 218:    */     catch (ExcepcionAS2Inventario e)
/* 219:    */     {
/* 220:242 */       e.printStackTrace();
/* 221:243 */       this.context.setRollbackOnly();
/* 222:244 */       throw e;
/* 223:    */     }
/* 224:    */     catch (ExcepcionAS2Financiero e)
/* 225:    */     {
/* 226:246 */       e.printStackTrace();
/* 227:247 */       this.context.setRollbackOnly();
/* 228:248 */       throw e;
/* 229:    */     }
/* 230:    */     catch (Exception e)
/* 231:    */     {
/* 232:250 */       e.printStackTrace();
/* 233:251 */       this.context.setRollbackOnly();
/* 234:252 */       throw new ExcepcionAS2(e);
/* 235:    */     }
/* 236:    */   }
/* 237:    */   
/* 238:    */   private void validar(FacturaCliente notaDebito)
/* 239:    */     throws ExcepcionAS2Ventas, ExcepcionAS2Financiero
/* 240:    */   {
/* 241:259 */     this.servicioPeriodo.buscarPorFecha(notaDebito.getFecha(), notaDebito.getIdOrganizacion(), notaDebito.getDocumento().getDocumentoBase());
/* 242:260 */     if (verificaExistenciaNumero(notaDebito) > 0L) {
/* 243:261 */       throw new ExcepcionAS2Ventas("msg_error_numero_duplicado", " " + notaDebito.getNumero());
/* 244:    */     }
/* 245:265 */     this.servicioVerificadorVentas.verificarCupoCredito(notaDebito.getEmpresa().getCliente(), notaDebito.getTotalFactura());
/* 246:267 */     if (notaDebito.getTotalCuentaPorCobrar().compareTo(notaDebito.getTotalFactura()) != 0) {
/* 247:268 */       throw new ExcepcionAS2Ventas("msg_error_diferencia_forma_pago");
/* 248:    */     }
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void eliminar(FacturaCliente notaDebito)
/* 252:    */     throws ExcepcionAS2
/* 253:    */   {}
/* 254:    */   
/* 255:    */   public long verificaExistenciaNumero(FacturaCliente facturaCliente)
/* 256:    */   {
/* 257:290 */     return this.facturaClienteDao.verificaExistenciaNumero(facturaCliente);
/* 258:    */   }
/* 259:    */   
/* 260:    */   @Deprecated
/* 261:    */   public List getReporteNotaDebitoCliente(int idFacturaCliente)
/* 262:    */   {
/* 263:302 */     return this.facturaClienteDao.getReporteNotaCreditoCliente(idFacturaCliente);
/* 264:    */   }
/* 265:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.servicio.impl.ServicioNotaDebitoClienteImpl
 * JD-Core Version:    0.7.0.1
 */