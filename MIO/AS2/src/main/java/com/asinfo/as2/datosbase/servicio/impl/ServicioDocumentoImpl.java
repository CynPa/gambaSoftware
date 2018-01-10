/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DocumentoDao;
/*   4:    */ import com.asinfo.as2.dao.DocumentoGastoImportacionDao;
/*   5:    */ import com.asinfo.as2.dao.sri.AutorizacionDocumentoSRIDao;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumentoRemote;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.DocumentoGastoImportacion;
/*  10:    */ import com.asinfo.as2.entities.GastoImportacion;
/*  11:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  12:    */ import com.asinfo.as2.entities.Secuencia;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.sri.AutorizacionDocumentoSRI;
/*  15:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  16:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  17:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  18:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  19:    */ import java.util.Date;
/*  20:    */ import java.util.HashMap;
/*  21:    */ import java.util.List;
/*  22:    */ import java.util.Map;
/*  23:    */ import javax.ejb.EJB;
/*  24:    */ import javax.ejb.Stateless;
/*  25:    */ 
/*  26:    */ @Stateless
/*  27:    */ public class ServicioDocumentoImpl
/*  28:    */   implements ServicioDocumento, ServicioDocumentoRemote
/*  29:    */ {
/*  30:    */   @EJB
/*  31:    */   private DocumentoDao documentoDao;
/*  32:    */   @EJB
/*  33:    */   private DocumentoGastoImportacionDao documentoGastoImportacionDao;
/*  34:    */   @EJB
/*  35:    */   private AutorizacionDocumentoSRIDao autorizacionDocumentoSRIDao;
/*  36:    */   
/*  37:    */   public void guardar(Documento documento)
/*  38:    */     throws AS2Exception
/*  39:    */   {
/*  40: 62 */     validar(documento);
/*  41: 63 */     if (!documento.isIndicadorDocumentoTributario()) {
/*  42: 64 */       documento.setIndicadorDocumentoElectronico(false);
/*  43:    */     }
/*  44: 67 */     if ((documento.getDocumentoBase() == DocumentoBase.FACTURA_CLIENTE) || (documento.getDocumentoBase() == DocumentoBase.NOTA_DEBITO_CLIENTE)) {
/*  45: 68 */       documento.setOperacion((short)1);
/*  46: 69 */     } else if (documento.getDocumentoBase() == DocumentoBase.NOTA_CREDITO_CLIENTE) {
/*  47: 70 */       documento.setOperacion((short)-1);
/*  48:    */     }
/*  49: 76 */     if ((documento.getDocumentoBase() == DocumentoBase.AJUSTE_INVENTARIO) && (documento.getOperacion() == -1)) {
/*  50: 77 */       documento.setIndicadorGeneraCosto(false);
/*  51:    */     }
/*  52: 80 */     for (AutorizacionDocumentoSRI autorizacionDocumentoSRI : documento.getListaAutorizacionDocumentoSRI())
/*  53:    */     {
/*  54: 82 */       if (!documento.isIndicadorDocumentoTributario()) {
/*  55: 83 */         autorizacionDocumentoSRI.setEliminado(true);
/*  56:    */       }
/*  57: 86 */       if (documento.isIndicadorDocumentoElectronico()) {
/*  58: 87 */         autorizacionDocumentoSRI.setAutorizacion(FuncionesUtiles.replicar('0', 10));
/*  59:    */       }
/*  60: 90 */       this.autorizacionDocumentoSRIDao.guardar(autorizacionDocumentoSRI);
/*  61:    */     }
/*  62: 92 */     for (DocumentoGastoImportacion documentoGastoImportacion : documento.getListaDocumentoGastoImportacion()) {
/*  63: 93 */       this.documentoGastoImportacionDao.guardar(documentoGastoImportacion);
/*  64:    */     }
/*  65: 95 */     this.documentoDao.guardar(documento);
/*  66:    */   }
/*  67:    */   
/*  68:    */   private void validar(Documento documento)
/*  69:    */     throws AS2Exception
/*  70:    */   {
/*  71: 99 */     if (DocumentoBase.PEDIDO_IMPORTACION.equals(documento.getDocumentoBase()))
/*  72:    */     {
/*  73:100 */       int numeroGastosFacturaExterior = 0;
/*  74:101 */       for (DocumentoGastoImportacion documentoGastoImportacion : documento.getListaDocumentoGastoImportacion()) {
/*  75:102 */         if ((documentoGastoImportacion.getGastoImportacion().isIndicadorFacturaExterior()) && (!documentoGastoImportacion.isEliminado())) {
/*  76:103 */           numeroGastosFacturaExterior++;
/*  77:    */         }
/*  78:    */       }
/*  79:106 */       if (numeroGastosFacturaExterior == 0) {
/*  80:107 */         throw new AS2Exception("msg_error_ingresar_gasto_factura_exterior", new String[] { "" });
/*  81:    */       }
/*  82:108 */       if (numeroGastosFacturaExterior > 1) {
/*  83:109 */         throw new AS2Exception("msg_error_cantidad_gasto_factura_exterior_excedido", new String[] { "" });
/*  84:    */       }
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void eliminar(Documento entidad)
/*  89:    */   {
/*  90:121 */     this.documentoDao.eliminar(entidad);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public Documento buscarPorId(Integer id)
/*  94:    */   {
/*  95:131 */     return this.documentoDao.buscarPorId(id);
/*  96:    */   }
/*  97:    */   
/*  98:    */   @Deprecated
/*  99:    */   public List<Documento> buscarPorDocumentoBase(DocumentoBase documentoBase)
/* 100:    */     throws ExcepcionAS2
/* 101:    */   {
/* 102:142 */     Map<String, String> filters = new HashMap();
/* 103:143 */     filters.put("documentoBase", documentoBase.toString());
/* 104:144 */     filters.put("activo", "TRUE");
/* 105:145 */     return obtenerListaCombo("predeterminado", false, filters);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public List<Documento> buscarPorDocumentoBaseOrganizacion(DocumentoBase documentoBase, int idOrganizacion)
/* 109:    */     throws ExcepcionAS2
/* 110:    */   {
/* 111:150 */     return buscarPorDocumentoBaseOrganizacion(documentoBase, idOrganizacion, null);
/* 112:    */   }
/* 113:    */   
/* 114:    */   public List<Documento> buscarPorDocumentoBaseOrganizacion(DocumentoBase documentoBase, int idOrganizacion, Integer operacion)
/* 115:    */     throws ExcepcionAS2
/* 116:    */   {
/* 117:155 */     Map<String, String> filters = new HashMap();
/* 118:156 */     filters.put("documentoBase", documentoBase.toString());
/* 119:157 */     filters.put("idOrganizacion", "" + idOrganizacion);
/* 120:158 */     filters.put("activo", "=true");
/* 121:159 */     if (operacion != null) {
/* 122:160 */       filters.put("operacion", "=" + operacion);
/* 123:    */     }
/* 124:162 */     return obtenerListaCombo("predeterminado", false, filters);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<Documento> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 128:    */   {
/* 129:173 */     return this.documentoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<Documento> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 133:    */   {
/* 134:183 */     return this.documentoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int contarPorCriterio(Map<String, String> filters)
/* 138:    */   {
/* 139:193 */     return this.documentoDao.contarPorCriterio(filters);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Documento cargarDetalle(int idDocumento)
/* 143:    */   {
/* 144:203 */     return this.documentoDao.cargarDetalle(idDocumento);
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Documento cargarSecuencia(Documento documento, PuntoDeVenta puntoDeVenta, Date fecha)
/* 148:    */     throws ExcepcionAS2
/* 149:    */   {
/* 150:221 */     if (puntoDeVenta != null)
/* 151:    */     {
/* 152:223 */       documento = this.documentoDao.cargarSecuencia(documento, puntoDeVenta, fecha);
/* 153:    */       
/* 154:    */ 
/* 155:226 */       Secuencia secuencia = documento.getSecuencia();
/* 156:227 */       String prefijo = puntoDeVenta.getSucursal().getCodigo() + "-" + puntoDeVenta.getCodigo() + "-";
/* 157:228 */       secuencia.setPrefijo(prefijo);
/* 158:229 */       secuencia.setLongitud(secuencia.getLongitud() + prefijo.length());
/* 159:    */     }
/* 160:233 */     return documento;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public AutorizacionDocumentoSRI cargarDocumentoConAutorizacion(Documento documento, PuntoDeVenta puntoDeVenta, Date fecha)
/* 164:    */     throws ExcepcionAS2
/* 165:    */   {
/* 166:244 */     AutorizacionDocumentoSRI autorizacionDocumentoSRI = null;
/* 167:246 */     if (puntoDeVenta != null)
/* 168:    */     {
/* 169:247 */       autorizacionDocumentoSRI = this.documentoDao.obtenerAutorizacionConSecuencia(fecha, documento, puntoDeVenta);
/* 170:248 */       Secuencia secuencia = autorizacionDocumentoSRI.getSecuencia();
/* 171:249 */       documento.setSecuencia(secuencia);
/* 172:    */     }
/* 173:252 */     return autorizacionDocumentoSRI;
/* 174:    */   }
/* 175:    */   
/* 176:    */   @Deprecated
/* 177:    */   public List<Documento> buscarPorDocumentoBase(DocumentoBase documentoBase, int operacion)
/* 178:    */     throws ExcepcionAS2
/* 179:    */   {
/* 180:263 */     return this.documentoDao.buscarPorDocumentoBase(documentoBase, operacion);
/* 181:    */   }
/* 182:    */   
/* 183:    */   public AutorizacionDocumentoSRI obtenerAutorizacionConSecuencia(Date fecha, Documento documento, PuntoDeVenta puntoDeVenta)
/* 184:    */     throws ExcepcionAS2
/* 185:    */   {
/* 186:274 */     return this.documentoDao.obtenerAutorizacionConSecuencia(fecha, documento, puntoDeVenta);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public List<AutorizacionDocumentoSRI> obtenerAutorizaciones(Date fecha, int idOrganizacion, boolean activa)
/* 190:    */     throws ExcepcionAS2
/* 191:    */   {
/* 192:284 */     return this.documentoDao.obtenerAutorizaciones(fecha, idOrganizacion, activa);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public List<Documento> buscarPorDocumentoBaseOrganizacionAerolinea(DocumentoBase documentoBase, int idOrganizacion)
/* 196:    */     throws ExcepcionAS2
/* 197:    */   {
/* 198:289 */     Map<String, String> filters = new HashMap();
/* 199:290 */     filters.put("documentoBase", documentoBase.toString());
/* 200:291 */     filters.put("idOrganizacion", "" + idOrganizacion);
/* 201:292 */     filters.put("tipoComprobanteSRI.codigo", "11");
/* 202:293 */     return obtenerListaCombo("nombre", true, filters);
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void detach(Documento documento)
/* 206:    */   {
/* 207:298 */     this.documentoDao.detach(documento);
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioDocumentoImpl
 * JD-Core Version:    0.7.0.1
 */