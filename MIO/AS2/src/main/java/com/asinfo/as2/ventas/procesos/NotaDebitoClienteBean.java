/*   1:    */ package com.asinfo.as2.ventas.procesos;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.compras.excepciones.ExcepcionAS2Compras;
/*   4:    */ import com.asinfo.as2.compronteselectronicos.base.TipoDocumentoElectronicoEnum;
/*   5:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioConfiguracion;
/*   6:    */ import com.asinfo.as2.controller.LanguageController;
/*   7:    */ import com.asinfo.as2.datosbase.servicio.ServicioDocumento;
/*   8:    */ import com.asinfo.as2.entities.Documento;
/*   9:    */ import com.asinfo.as2.entities.Empresa;
/*  10:    */ import com.asinfo.as2.entities.FacturaCliente;
/*  11:    */ import com.asinfo.as2.entities.Organizacion;
/*  12:    */ import com.asinfo.as2.entities.PuntoDeVenta;
/*  13:    */ import com.asinfo.as2.entities.Sucursal;
/*  14:    */ import com.asinfo.as2.entities.Ubicacion;
/*  15:    */ import com.asinfo.as2.entities.seguridad.EntidadUsuario;
/*  16:    */ import com.asinfo.as2.entities.sri.FacturaClienteSRI;
/*  17:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  18:    */ import com.asinfo.as2.enumeraciones.Estado;
/*  19:    */ import com.asinfo.as2.enumeraciones.ExportOption;
/*  20:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  21:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  22:    */ import com.asinfo.as2.financiero.SRI.procesos.servicio.ServicioFacturaClienteSRI;
/*  23:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  24:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  25:    */ import com.asinfo.as2.seguridad.modelo.Usuario;
/*  26:    */ import com.asinfo.as2.util.AppUtil;
/*  27:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  28:    */ import com.asinfo.as2.utils.ParametrosSistema;
/*  29:    */ import com.asinfo.as2.ventas.excepciones.ExcepcionAS2Ventas;
/*  30:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioFacturaCliente;
/*  31:    */ import com.asinfo.as2.ventas.procesos.servicio.ServicioNotaDebitoCliente;
/*  32:    */ import com.asinfo.as2.ventas.reportes.ReporteFacturaClienteBean;
/*  33:    */ import java.io.BufferedInputStream;
/*  34:    */ import java.io.File;
/*  35:    */ import java.io.FileNotFoundException;
/*  36:    */ import java.io.InputStream;
/*  37:    */ import java.util.ArrayList;
/*  38:    */ import java.util.HashMap;
/*  39:    */ import java.util.Iterator;
/*  40:    */ import java.util.List;
/*  41:    */ import java.util.Map;
/*  42:    */ import javax.annotation.PostConstruct;
/*  43:    */ import javax.ejb.EJB;
/*  44:    */ import javax.faces.bean.ManagedBean;
/*  45:    */ import javax.faces.bean.ViewScoped;
/*  46:    */ import org.apache.log4j.Logger;
/*  47:    */ import org.primefaces.event.FileUploadEvent;
/*  48:    */ import org.primefaces.model.StreamedContent;
/*  49:    */ import org.primefaces.model.UploadedFile;
/*  50:    */ 
/*  51:    */ @ManagedBean
/*  52:    */ @ViewScoped
/*  53:    */ public class NotaDebitoClienteBean
/*  54:    */   extends FacturaClienteBaseBean
/*  55:    */ {
/*  56:    */   private static final long serialVersionUID = 9000647050620869378L;
/*  57:    */   @EJB
/*  58:    */   protected transient ServicioNotaDebitoCliente servicioNotaDebitoCliente;
/*  59:    */   @EJB
/*  60:    */   protected transient ServicioFacturaCliente servicioFacturaCliente;
/*  61: 69 */   private List<ErrorCarga> errores = new ArrayList();
/*  62:    */   private String numeroNotaDebito;
/*  63:    */   
/*  64:    */   public void obtenerFiltros(Map<String, String> filters)
/*  65:    */   {
/*  66: 81 */     filters.put("documento.documentoBase", DocumentoBase.NOTA_DEBITO_CLIENTE.toString());
/*  67: 83 */     if (this.idFacturaCliente != null)
/*  68:    */     {
/*  69: 84 */       filters.put("idFacturaCliente", "" + this.idFacturaCliente);
/*  70: 85 */       this.idFacturaCliente = null;
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   @PostConstruct
/*  75:    */   public void init()
/*  76:    */   {
/*  77: 97 */     super.init();
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String cargarNotaDebitoClienteElectronica(FileUploadEvent event)
/*  81:    */   {
/*  82:106 */     List<FacturaCliente> lisFacturaCliente = new ArrayList();
/*  83:    */     try
/*  84:    */     {
/*  85:109 */       String fileName = "migracion_factura_cliente" + event.getFile().getFileName();
/*  86:110 */       InputStream input = new BufferedInputStream(event.getFile().getInputstream());
/*  87:111 */       lisFacturaCliente = this.servicioFacturaCliente.migracionFacturaCliente(AppUtil.getOrganizacion().getId(), fileName, input, 4);
/*  88:112 */       addInfoMessage(getLanguageController().getMensaje("msg_info_carga_datos"));
/*  89:114 */       for (localIterator = lisFacturaCliente.iterator(); localIterator.hasNext();)
/*  90:    */       {
/*  91:114 */         fc = (FacturaCliente)localIterator.next();
/*  92:115 */         if (this.facturaCliente.getAgenteComercial() == null) {
/*  93:116 */           this.facturaCliente.setAgenteComercial(new EntidadUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/*  94:    */         }
/*  95:119 */         this.servicioNotaDebitoCliente.guardar(fc);
/*  96:    */       }
/*  97:    */     }
/*  98:    */     catch (AS2Exception e)
/*  99:    */     {
/* 100:    */       Iterator localIterator;
/* 101:123 */       e.printStackTrace();
/* 102:124 */       List<String> listaMensajes = e.getCodigoMensajes();
/* 103:125 */       int i = 0;
/* 104:126 */       for (String a : listaMensajes)
/* 105:    */       {
/* 106:127 */         i = a.indexOf("*");
/* 107:128 */         a.substring(0, i + 1);
/* 108:129 */         ErrorCarga ec = new ErrorCarga();
/* 109:130 */         ec.setError(getLanguageController().getMensaje(a.substring(0, i)) + " " + a.substring(i + 1, a.length()));
/* 110:131 */         this.errores.add(ec);
/* 111:    */       }
/* 112:133 */       for (String a : e.getMensajes())
/* 113:    */       {
/* 114:134 */         ErrorCarga ec = new ErrorCarga();
/* 115:135 */         ec.setError(a);
/* 116:136 */         this.errores.add(ec);
/* 117:    */       }
/* 118:    */     }
/* 119:    */     catch (ExcepcionAS2Financiero e)
/* 120:    */     {
/* 121:    */       FacturaCliente fc;
/* 122:139 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 123:140 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 124:    */     }
/* 125:    */     catch (ExcepcionAS2Compras e)
/* 126:    */     {
/* 127:143 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 128:144 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 129:    */     }
/* 130:    */     catch (ExcepcionAS2 e)
/* 131:    */     {
/* 132:147 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 133:148 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/* 134:    */     }
/* 135:    */     catch (Exception e)
/* 136:    */     {
/* 137:151 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 138:152 */       e.printStackTrace();
/* 139:    */     }
/* 140:154 */     return null;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public List<Documento> getListaDocumentoCliente()
/* 144:    */   {
/* 145:170 */     if (this.listaDocumentoCliente == null) {
/* 146:    */       try
/* 147:    */       {
/* 148:172 */         this.listaDocumentoCliente = this.servicioDocumento.buscarPorDocumentoBase(DocumentoBase.NOTA_DEBITO_CLIENTE);
/* 149:    */       }
/* 150:    */       catch (ExcepcionAS2 e)
/* 151:    */       {
/* 152:174 */         addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 153:    */       }
/* 154:    */     }
/* 155:177 */     return this.listaDocumentoCliente;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String guardar()
/* 159:    */   {
/* 160:    */     try
/* 161:    */     {
/* 162:188 */       if (this.facturaCliente.getAgenteComercial() == null) {
/* 163:189 */         this.facturaCliente.setAgenteComercial(new EntidadUsuario(AppUtil.getUsuarioEnSesion().getIdUsuario()));
/* 164:    */       }
/* 165:192 */       this.servicioFacturaClienteSRI.actualizarFacturaClienteSRI(this.facturaCliente);
/* 166:    */       
/* 167:194 */       PuntoDeVenta puntoDeVenta = this.servicioFacturaCliente.cargarPuntoVenta(this.facturaCliente);
/* 168:    */       
/* 169:196 */       this.servicioFacturaClienteSRI.actualizarAutorizacionSRI(this.facturaCliente, puntoDeVenta);
/* 170:200 */       if (this.facturaCliente.getFacturaClienteSRI() != null)
/* 171:    */       {
/* 172:201 */         int ambiente = ParametrosSistema.getAmbienteFacturacionElectronica(AppUtil.getOrganizacion().getId()).booleanValue() ? 2 : 1;
/* 173:202 */         this.facturaCliente.getFacturaClienteSRI().setAmbiente(ambiente);
/* 174:203 */         this.facturaCliente.getFacturaClienteSRI().setDireccionMatriz(AppUtil.getDireccionMatriz());
/* 175:204 */         this.facturaCliente.getFacturaClienteSRI().setDireccionSucursal(AppUtil.getSucursal().getUbicacion().getDireccionCompleta());
/* 176:    */       }
/* 177:208 */       this.servicioNotaDebitoCliente.guardar(this.facturaCliente);
/* 178:    */       
/* 179:210 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/* 180:211 */       if (isIndicadorAutoimpresor())
/* 181:    */       {
/* 182:212 */         getReporteFacturaClienteBean().setFacturaCliente(this.facturaCliente);
/* 183:213 */         getReporteFacturaClienteBean().setExportOption(ExportOption.PRINTER);
/* 184:214 */         getReporteFacturaClienteBean().execute();
/* 185:    */       }
/* 186:216 */       limpiar();
/* 187:    */     }
/* 188:    */     catch (ExcepcionAS2Financiero e)
/* 189:    */     {
/* 190:219 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 191:220 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/* 192:    */     }
/* 193:    */     catch (ExcepcionAS2Ventas e)
/* 194:    */     {
/* 195:223 */       actualizarDocumento();
/* 196:224 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 197:225 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/* 198:    */     }
/* 199:    */     catch (ExcepcionAS2Inventario e)
/* 200:    */     {
/* 201:228 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/* 202:229 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/* 203:    */     }
/* 204:    */     catch (ExcepcionAS2 e)
/* 205:    */     {
/* 206:232 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/* 207:233 */       LOG.info("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/* 208:    */     }
/* 209:    */     catch (Exception e)
/* 210:    */     {
/* 211:236 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/* 212:237 */       LOG.error("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/* 213:    */     }
/* 214:239 */     return "";
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void totalizar()
/* 218:    */   {
/* 219:    */     try
/* 220:    */     {
/* 221:250 */       this.facturaCliente = this.servicioFacturaCliente.totalizar(this.facturaCliente);
/* 222:251 */       cargarCuentaPorCobrar();
/* 223:    */     }
/* 224:    */     catch (ExcepcionAS2Ventas e)
/* 225:    */     {
/* 226:253 */       LOG.error(e.getErrorMessage(e));
/* 227:    */     }
/* 228:    */     catch (Exception e)
/* 229:    */     {
/* 230:255 */       LOG.error(e);
/* 231:256 */       e.printStackTrace();
/* 232:    */     }
/* 233:    */   }
/* 234:    */   
/* 235:    */   public void cargarCuentaPorCobrar()
/* 236:    */   {
/* 237:    */     try
/* 238:    */     {
/* 239:268 */       this.facturaCliente = this.servicioFacturaCliente.generarCuentaPorCobrar(this.facturaCliente);
/* 240:    */     }
/* 241:    */     catch (ExcepcionAS2 e)
/* 242:    */     {
/* 243:270 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()));
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   public List<FacturaCliente> autocompletarFacturas(String consulta)
/* 248:    */   {
/* 249:281 */     consulta = consulta.toUpperCase();
/* 250:282 */     List<FacturaCliente> lista = new ArrayList();
/* 251:    */     
/* 252:284 */     Map<String, String> filters = new HashMap();
/* 253:286 */     if (this.facturaCliente.getEmpresa() != null)
/* 254:    */     {
/* 255:287 */       filters.put("empresa.idEmpresa", "" + this.facturaCliente.getEmpresa().getId());
/* 256:288 */       if ((consulta != null) && (!consulta.isEmpty())) {
/* 257:289 */         filters.put("numero", consulta);
/* 258:    */       }
/* 259:292 */       filters.put("documento.documentoBase", DocumentoBase.FACTURA_CLIENTE.toString());
/* 260:293 */       filters.put("facturaClienteSRI.autorizacion", "!=0000000000");
/* 261:294 */       filters.put("estado", "!=" + Estado.ANULADO.toString());
/* 262:    */       
/* 263:296 */       lista = this.servicioFacturaCliente.obtenerListaCombo("fecha", true, filters);
/* 264:    */     }
/* 265:    */     else
/* 266:    */     {
/* 267:298 */       addErrorMessage(getLanguageController().getMensaje("msg_info_escojer_empresa"));
/* 268:    */     }
/* 269:301 */     return lista;
/* 270:    */   }
/* 271:    */   
/* 272:    */   public StreamedContent getXMLSRI()
/* 273:    */   {
/* 274:305 */     if ((this.facturaCliente != null) && (this.facturaCliente.getId() != 0) && (this.facturaCliente.getFacturaClienteSRI() != null))
/* 275:    */     {
/* 276:306 */       String pathSRI = ServicioConfiguracion.AS2_HOME + File.separator + AppUtil.getOrganizacion().getCodigoOrganizacion() + File.separator + "sri";
/* 277:    */       
/* 278:    */ 
/* 279:309 */       String pathDocumento = pathSRI + File.separator + "documentos_electronicos" + File.separator + TipoDocumentoElectronicoEnum.NOTA_DEBITO.toString();
/* 280:310 */       String pathArchivoAutorizado = pathDocumento + File.separator + "autorizado";
/* 281:311 */       String nombreArchivo = this.facturaCliente.getNumero() + "-" + this.facturaCliente.getFacturaClienteSRI().getClaveAcceso() + ".xml";
/* 282:312 */       pathArchivoAutorizado = pathArchivoAutorizado + File.separator + nombreArchivo;
/* 283:    */       try
/* 284:    */       {
/* 285:314 */         return FuncionesUtiles.descargarArchivo(pathArchivoAutorizado, "application/xls", nombreArchivo);
/* 286:    */       }
/* 287:    */       catch (FileNotFoundException e)
/* 288:    */       {
/* 289:316 */         e.printStackTrace();
/* 290:317 */         addInfoMessage(getLanguageController().getMensaje("msg_info_archivo_no_encontrado"));
/* 291:318 */         return null;
/* 292:    */       }
/* 293:    */     }
/* 294:321 */     addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/* 295:322 */     return null;
/* 296:    */   }
/* 297:    */   
/* 298:    */   public List<ErrorCarga> getErrores()
/* 299:    */   {
/* 300:327 */     return this.errores;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public void setErrores(List<ErrorCarga> errores)
/* 304:    */   {
/* 305:331 */     this.errores = errores;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public String getNumeroNotaDebito()
/* 309:    */   {
/* 310:335 */     return this.numeroNotaDebito;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setNumeroNotaDebito(String numeroNotaDebito)
/* 314:    */   {
/* 315:339 */     this.numeroNotaDebito = numeroNotaDebito;
/* 316:    */   }
/* 317:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ventas.procesos.NotaDebitoClienteBean
 * JD-Core Version:    0.7.0.1
 */